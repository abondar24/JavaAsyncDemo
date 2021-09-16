package org.abondar.experimental.async.vertx.command;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.abondar.experimental.async.command.Command;

public class EventBusCommand implements Command {
    @Override
    public void execute() {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.MessageSource",
                new DeploymentOptions().setInstances(2));

        vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.Listener");
        vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.MessageObserver");
        vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.HttpServer");

    }
}
