package org.abondar.experimental.async.vertx.command;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.vertx.verticle.eventbus.MessageDataVerticle;
import org.abondar.experimental.async.vertx.verticle.eventbus.MessageProxyClient;

public class ProxyCommand implements Command {
    @Override
    public void execute() {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.MessageSource",
                new DeploymentOptions().setInstances(10));
        vertx.deployVerticle(new MessageDataVerticle());
        vertx.deployVerticle(new MessageProxyClient());
    }
}
