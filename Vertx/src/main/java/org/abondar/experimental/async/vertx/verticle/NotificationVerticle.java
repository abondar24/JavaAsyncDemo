package org.abondar.experimental.async.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class NotificationVerticle extends AbstractVerticle {

    private Vertx vertx;

    public NotificationVerticle(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void start(Promise<Void> promise){
        vertx.createHttpServer()
                .requestHandler(req -> req.response().end("Ok"))
                .listen(8040,not->{
                   if (not.succeeded()){
                       promise.complete();;
                   } else {
                       promise.fail(not.cause());
                   }
                });
    }
}
