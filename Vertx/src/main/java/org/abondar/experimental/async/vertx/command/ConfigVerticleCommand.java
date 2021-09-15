package org.abondar.experimental.async.vertx.command;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.abondar.experimental.async.command.Command;

public class ConfigVerticleCommand implements Command {
    @Override
    public void execute() {
        Vertx vertx = Vertx.vertx();
        for (int i = 0; i < 4; i++) {
            JsonObject config = new JsonObject();
            config.put("verticle", i);

            DeploymentOptions opts = new DeploymentOptions();
            opts.setConfig(config)
                    .setInstances(i);

            vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.ConfigVerticle",opts);
        }
    }
}
