package org.abondar.experimental.async.vertx.verticle.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Optional;

public class MessageObserver extends AbstractVerticle {
    private final HashMap<String,String> storage;

    public MessageObserver(){
        this.storage = new HashMap<>();
    }

    @Override
    public void start(){
        EventBus bus = vertx.eventBus();
        bus.consumer("message.updates",this::update);
        bus.consumer("message.special",this::special);
    }

    private void update(Message<JsonObject> message){
        JsonObject jsonObject = message.body();
        storage.put(jsonObject.getString("id"),jsonObject.getString("body"));
    }

    public void special(Message<JsonObject> message){
       Optional<String> special =  storage.values()
               .stream()
               .filter(v->v.contains("-"))
               .findFirst();

       if (special.isPresent()){
           JsonObject json = new JsonObject();
           json.put("special",special.get());
           message.reply(json);
       }

    }


}
