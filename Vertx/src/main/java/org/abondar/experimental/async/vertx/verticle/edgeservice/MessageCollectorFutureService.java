package org.abondar.experimental.async.vertx.verticle.edgeservice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageCollectorFutureService extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(MessageCollectorFutureService.class);

    private WebClient webClient;

    @Override
    public void start(Promise<Void> promise) {
        webClient = WebClient.create(vertx);
        vertx.createHttpServer()
                .requestHandler(this::handleRequest)
                .listen(EdgeServiceUtil.COLLECTOR_PORT)
                .onFailure(promise::fail)
                .onSuccess(ok -> {
                    logger.info("Server is up on http://localhost:8080/");
                    promise.complete();
                });
    }

    private void handleRequest(HttpServerRequest request) {
        CompositeFuture.all(
                        fetchMessage(EdgeServiceUtil.SOURCE_PORT),
                        fetchMessage(EdgeServiceUtil.SOURCE_PORT+1),
                        fetchMessage(EdgeServiceUtil.SOURCE_PORT+2))
                .flatMap(this::sendToLogger)
                .onSuccess(data -> request.response()
                        .putHeader(EdgeServiceUtil.CONTENT_TYPE_HEADER,EdgeServiceUtil.CONTENT_TYPE_JSON)
                        .end(data.encode()))
                .onFailure(err ->{
                    logger.error("Error ",err);
                    request.response()
                            .setStatusCode(500)
                            .end();
                });
    }

    private Future<JsonObject> sendToLogger(CompositeFuture compositeFuture) {
        List<JsonObject> messages = compositeFuture.list();

        JsonArray messageData = new JsonArray();
        messageData.add(messages.get(0));
        messageData.add(messages.get(1));
        messageData.add(messages.get(2));

        JsonObject data = new JsonObject();
        data.put("data",messageData);

        return webClient.post(EdgeServiceUtil.LOGGER_PORT,EdgeServiceUtil.SERVER_HOST,EdgeServiceUtil.PATH)
                .expect(ResponsePredicate.SC_SUCCESS)
                .sendJson(data)
                .map(response -> data);
    }

    private Future<JsonObject> fetchMessage(int port) {
        return webClient
                .get(port, EdgeServiceUtil.SERVER_HOST,EdgeServiceUtil.PATH)
                .expect(ResponsePredicate.SC_SUCCESS)
                .as(BodyCodec.jsonObject())
                .send()
                .map(HttpResponse::body);
    }
}
