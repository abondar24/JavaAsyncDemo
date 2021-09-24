package org.abondar.experimental.async.vertx.service;

import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
@VertxGen
public interface MessageDataService {

    static MessageDataService create(Vertx vertx){
        return new MessageDataServiceImpl(vertx);
    }

    static MessageDataService createProxy(Vertx vertx, String address){
        return new MessageDataServiceVertxEBProxy(vertx,address);
    }


    void valueFor(String messageId, Handler<AsyncResult<JsonObject>> handler);

    void specialMessage(Handler<AsyncResult<JsonObject>> handler);

}
