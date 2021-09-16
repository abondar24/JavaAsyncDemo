package org.abondar.experimental.async.vertx.verticle.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Listener extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(Listener.class);

    @Override
    public void start(){
        EventBus eventBus = vertx.eventBus();
        eventBus.<JsonObject>consumer("message.updates",msg->{
           JsonObject body = msg.body();
           String id = body.getString("id");
           String msgBody = body.getString("body");
           logger.info("Message {} has body {} ",id,msgBody);
        });
    }
}
