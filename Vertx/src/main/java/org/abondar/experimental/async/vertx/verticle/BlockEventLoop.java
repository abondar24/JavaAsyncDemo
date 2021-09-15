package org.abondar.experimental.async.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class BlockEventLoop extends AbstractVerticle {

    private Vertx vertx;

    public BlockEventLoop(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void start(){
        vertx.setTimer(1000,i->{
            while (true);
        });
    }
}
