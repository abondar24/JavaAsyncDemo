package org.abondar.experimental.async.vertx.cluster.eventbus;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusAdditionalInstance {

    private static final Logger logger = LoggerFactory.getLogger(EventBusAdditionalInstance.class);

    public void runInstance(){
        Vertx.clusteredVertx(new VertxOptions(), asyncResult -> {
            if (asyncResult.succeeded()){
                logger.info("Additional Instance Launched");

                Vertx vertx = asyncResult.result();
                vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.MessageSource",
                        new DeploymentOptions().setInstances(2));
                vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.Listener");
                vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.MessageObserver");

                JsonObject conf = new JsonObject().put("port",8081);
                vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.HttpServer",
                        new DeploymentOptions().setConfig(conf));
            } else {
                logger.error("Couldn't start", asyncResult.cause());
            }
        });
    }

}
