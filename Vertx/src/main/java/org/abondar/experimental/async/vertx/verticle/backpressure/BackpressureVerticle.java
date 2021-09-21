package org.abondar.experimental.async.vertx.verticle.backpressure;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class BackpressureVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(BackpressureVerticle.class);
    private final Queue<String> playlist = new ArrayDeque<>();
    private final Set<HttpServerResponse> streamers = new HashSet<>();
    private State currentState = State.PAUSE;

    private AsyncFile currentFile;

    private long filePos;

    @Override
    public void start() {
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer("bp.list", this::list);
        eventBus.consumer("bp.schedule", this::schedule);
        eventBus.consumer("bp.play", this::play);
        eventBus.consumer("bp.pause", this::pause);


        vertx.createHttpServer()
                .requestHandler(this::httpHandler)
                .listen(8080);

        vertx.setPeriodic(100,this::streamAudioChunk);
    }

    private void play(Message<?> request) {
        currentState = State.PLAY;
    }

    private void pause(Message<?> request) {
        currentState = State.PAUSE;
    }

    private void schedule(Message<JsonObject> request) {
        String file = request.body().getString("file");
        if (playlist.isEmpty() && currentState == State.PAUSE) {
            currentState = State.PLAY;
        }

        playlist.offer(file);
    }

    private void list(Message<?> request) {
        vertx.fileSystem().readDir("tracks", ".*mp3$", asyncResult -> {
            if (asyncResult.succeeded()) {
                List<String> files = asyncResult.result()
                        .stream()
                        .map(File::new)
                        .map(File::getName)
                        .collect(Collectors.toList());

                JsonObject json = new JsonObject();
                json.put("files", new JsonArray(files));
                request.reply(json);
            } else {
                logger.error("readDir failed", asyncResult.cause());
                request.fail(500, asyncResult.cause().getMessage());
            }
        });
    }

    private void httpHandler(HttpServerRequest request) {
        if ("/".equals(request.path())) {
            openAudioStream(request);
        }

        if (request.path().startsWith("/download/")) {
            String sanitizedPath = request.path()
                    .substring(10)
                    .replaceAll("/", "");
            download(sanitizedPath, request);
        }
    }

    private void openAudioStream(HttpServerRequest request) {
        HttpServerResponse response = request.response()
                .putHeader("Content-Type", "audio/mpeg")
                .setChunked(true);

        streamers.add(response);
        response.endHandler(v -> {
            streamers.remove(response);
            logger.info("A streamer left");
        });
    }

    private void download(String path, HttpServerRequest request) {
        String file = "tracks/" + path;

        if (!vertx.fileSystem().existsBlocking(file)) {
            request.response()
                    .setStatusCode(404)
                    .end();
        }

        OpenOptions opts = new OpenOptions();
        opts.setRead(true);
        vertx.fileSystem().open(file, opts, asyncResult -> {
            if (asyncResult.succeeded()) {
                downloadFile(asyncResult.result(), request);
            } else {
                logger.error("Read failed", asyncResult.cause());
                request.response()
                        .setStatusCode(500)
                        .end();
            }

        });
    }

    private void downloadFile(AsyncFile file, HttpServerRequest request) {
          HttpServerResponse response = request.response();
          response.setStatusCode(200)
                  .putHeader("Content-Type","audio/mpeg")
                  .setChunked(true);

          file.handler(buffer -> {
              response.write(buffer);
              if (response.writeQueueFull()){
                  file.pause();
                  response.drainHandler(v->file.resume());
              }
          });


          file.endHandler(v->response.end());
    }

    private void streamAudioChunk(long id) {
        if (currentState == State.PAUSE){
            return;
        }

        if (currentFile == null && playlist.isEmpty()){
            currentState = State.PAUSE;
            return;
        }

        if (currentFile == null ){
            openNextFile();
        }

        currentFile.read(Buffer.buffer(4096),0,filePos,4096,asyncRes ->{
           if (asyncRes.succeeded()){
               processReadBuffer(asyncRes.result());
           } else {
               logger.error("Read Failed", asyncRes.cause());
               closeCurrentFile();
           }
        });
    }

    private void openNextFile() {
        OpenOptions options = new OpenOptions();
        options.setRead(true);
        currentFile = vertx.fileSystem()
                .openBlocking("tracks/"+playlist.poll(),options);
        filePos = 0;
    }

    private void processReadBuffer(Buffer buffer) {
        filePos += buffer.length();
        if (buffer.length()==0){
            closeCurrentFile();
            return;
        }

        for (HttpServerResponse streamer : streamers){
            if (!streamer.writeQueueFull()){
                streamer.write(buffer.copy());
            }
        }
    }

    private void closeCurrentFile() {
        filePos=0;
        currentFile.close();
        currentFile = null;
    }


    private enum State {
        PLAY,
        PAUSE
    }

}
