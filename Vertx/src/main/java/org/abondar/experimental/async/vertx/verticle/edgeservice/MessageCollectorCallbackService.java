package org.abondar.experimental.async.vertx.verticle.edgeservice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageCollectorCallbackService extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(MessageCollectorCallbackService.class);

    private WebClient webClient;

    @Override
    public void start(){
        webClient = WebClient.create(vertx);
        vertx.createHttpServer()
                .requestHandler(this::handleRequest)
                .listen(EdgeServiceUtil.COLLECTOR_PORT);
    }

    private void handleRequest(HttpServerRequest request) {
        List<JsonObject> responses = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);
        for (int i=0;i<3;i++){
            webClient
                    .get(EdgeServiceUtil.SOURCE_PORT +i,EdgeServiceUtil.SERVER_HOST,EdgeServiceUtil.PATH)
                    .expect(ResponsePredicate.SC_SUCCESS)
                    .as(BodyCodec.jsonObject())
                    .send(asyncResult ->{
                        if (asyncResult.succeeded()){
                            responses.add(asyncResult.result().body());
                        } else {
                            logger.error("Message source down",asyncResult.cause());
                        }

                        if (counter.incrementAndGet()==3){
                            JsonObject data = new JsonObject();
                            data.put("data", new JsonArray(responses));
                            sendToLogger(request,data);
                        }
                    });
        }
    }

    private void sendToLogger(HttpServerRequest request, JsonObject data) {
        webClient
                .post(EdgeServiceUtil.LOGGER_PORT,EdgeServiceUtil.SERVER_HOST,EdgeServiceUtil.PATH)
                .expect(ResponsePredicate.SC_SUCCESS)
                .sendJsonObject(data,asyncResult ->{
                    if (asyncResult.succeeded()){
                        sendResponse(request,data);
                    } else {
                        logger.error("Message logger down", asyncResult.cause());
                        request.response().setStatusCode(500).end();
                    }
                });
    }

    private void sendResponse(HttpServerRequest request, JsonObject data) {
        request.response()
                .putHeader(EdgeServiceUtil.CONTENT_TYPE_HEADER,EdgeServiceUtil.CONTENT_TYPE_JSON)
                .end(data.encode());
    }
}
