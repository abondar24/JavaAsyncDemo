package org.abondar.experimental.async.vertx.verticle.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

public class MessageSource extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(MessageSource.class);

    private final Random random;

    public MessageSource() {
        this.random = new Random();
    }


    @Override
    public void start() {
        scheduleNewMessage();
    }

    private void scheduleNewMessage() {
        vertx.setTimer(random.nextInt(5000) + 1000, this::update);
    }

    private void update(Long timer) {
        String msgId = UUID.randomUUID().toString();
        String msgBody = generateMessageBody();

        JsonObject payload = new JsonObject()
                .put("id",msgId)
                .put("body",msgBody);

       logger.info("Sending message: {}",payload.toString());
        vertx.eventBus().publish("message.updates",payload);
        scheduleNewMessage();
    }

    public String generateMessageBody() {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-=+*&^%$#@!&()";
        StringBuilder sb = new StringBuilder(10);
        for(int i = 0; i < 10; i++)
            sb.append(AB.charAt(random.nextInt(AB.length())));
        return sb.toString();
    }


}
