package org.abondar.experimental.async.vertx.command;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.vertx.verticle.WorkerVerticle;

public class WorkerVerticleCommand implements Command {

    @Override
    public void execute() {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setInstances(2);
        deploymentOptions.setWorker(true);

        vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.WorkerVerticle",deploymentOptions);
    }
}
