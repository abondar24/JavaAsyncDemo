package org.abondar.experimental.async.vertx.command;

import io.vertx.core.Vertx;
import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.vertx.verticle.backpressure.BackpresControlVerticle;
import org.abondar.experimental.async.vertx.verticle.backpressure.BackpressureVerticle;

public class BackPressureCommand implements Command {
    @Override
    public void execute() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new BackpressureVerticle());
        vertx.deployVerticle(new BackpresControlVerticle());
    }
}
