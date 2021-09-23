package org.abondar.experimental.async.vertx.verticle.edgeservice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

import java.util.Random;
import java.util.UUID;

public class MessageSource extends AbstractVerticle {

    private final Random random;

    private Message message;

    public MessageSource() {
        this.random = new Random();
        this.message = generateMessage();
    }


    @Override
    public void start() {
        vertx.createHttpServer()
                .requestHandler(this::handleRequest)
                .listen(config().getInteger("http.port", EdgeServiceUtil.SOURCE_PORT));
        scheduleNewMessage();
    }

    private void handleRequest(HttpServerRequest request) {
        JsonObject data = new JsonObject()
                .put("id", message.getId())
                .put("msg", message.getBody());
        request.response()
                .putHeader(EdgeServiceUtil.CONTENT_TYPE_HEADER, EdgeServiceUtil.CONTENT_TYPE_JSON)
                .end(data.encode());
    }

    private void scheduleNewMessage() {
        vertx.setTimer(random.nextInt(5000) + 1000, this::update);
    }

    private void update(Long timer) {
        message = generateMessage();
        scheduleNewMessage();
    }

    private Message generateMessage() {
        String msgId = UUID.randomUUID().toString();
        String msgBody = generateMessageBody();
        return new Message(msgId, msgBody);
    }


    public String generateMessageBody() {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-=+*&^%$#@!&()";
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++)
            sb.append(AB.charAt(random.nextInt(AB.length())));
        return sb.toString();
    }


}
