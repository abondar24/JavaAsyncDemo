package org.abondar.experimental.async.vertx.command;

import io.vertx.core.Vertx;
import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.vertx.verticle.NotificationVerticle;

public class NotificationVerticleCommand implements Command {
    @Override
    public void execute() {
          Vertx vertx = Vertx.vertx();
          vertx.deployVerticle(new NotificationVerticle(vertx));
    }
}
