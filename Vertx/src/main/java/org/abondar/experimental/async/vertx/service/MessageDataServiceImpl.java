package org.abondar.experimental.async.vertx.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MessageDataServiceImpl implements MessageDataService{

    private final Map<String,String> lastMessages = new HashMap<>();

    MessageDataServiceImpl(Vertx vertx){
        vertx.eventBus().<JsonObject>consumer("message.updates", message -> {
            JsonObject json = message.body();
            lastMessages.put(json.getString("id"),json.getString("body"));
        });
    }


    @Override
    public void valueFor(String messageId, Handler<AsyncResult<JsonObject>> handler) {
            if (lastMessages.containsKey(messageId)){
                JsonObject data = new JsonObject();
                data.put("id",messageId);
                data.put("body",lastMessages.get(messageId));
            } else {
                handler.handle(Future.failedFuture("No value for: "+messageId));
            }
    }

    @Override
    public void specialMessage(Handler<AsyncResult<JsonObject>> handler) {
        Optional<String> special =  lastMessages.values()
                .stream()
                .filter(v->v.contains("-"))
                .findFirst();

        if (special.isPresent()){
            JsonObject data = new JsonObject();
            data.put("special",special.get());
            handler.handle(Future.succeededFuture(data));
        }
    }
}
