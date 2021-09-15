package org.abondar.experimental.async.vertx.command;

import io.vertx.core.Vertx;
import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.vertx.verticle.DeployVerticle;

public class DeployVerticleCommand implements Command {
    @Override
    public void execute() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new DeployVerticle());
    }
}
