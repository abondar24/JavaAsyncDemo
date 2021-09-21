package org.abondar.experimental.async.vertx.verticle.backpressure;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackpresControlVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(BackpresControlVerticle.class);

    @Override
    public void start(){
        vertx.createNetServer()
                .connectHandler(this::handleClient)
                .listen(3000);
    }

    private void handleClient(NetSocket netSocket) {
        RecordParser.newDelimited("\n",netSocket)
                .handler(buffer -> handleBuffer(netSocket,buffer))
                .endHandler(v-> logger.info("Connection ended"));
    }

    private void handleBuffer(NetSocket netSocket, Buffer buffer) {
        String cmd = buffer.toString();

        switch (cmd){
            case "/list":
                listCommand(netSocket);
                break;

            case "/play":
                vertx.eventBus()
                        .send("bp.play","");
                break;

            case "/pause":
                vertx.eventBus()
                        .send("bp.pause","");
                break;

            default:
                if (cmd.startsWith("/schedule ")){
                    schedule(cmd);
                } else {
                    netSocket.write("Unknown command\n");
                }
        }
    }

    private void listCommand(NetSocket netSocket) {
        vertx.eventBus()
                .request("bp.list","",reply ->{
                   if (reply.succeeded()){
                       JsonObject data = (JsonObject) reply.result().body();
                       data.getJsonArray("files")
                               .stream().forEach(name -> netSocket.write(name + "\n"));
                   } else {
                       logger.error("/list error", reply.cause());
                   }
                });
    }

    private void schedule(String cmd) {
        String track = cmd.substring(10);

        JsonObject json = new JsonObject();
        json.put("file",track);

        vertx.eventBus()
                .send("bp.schedule",json);
    }
}
