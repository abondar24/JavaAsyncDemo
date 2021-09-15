package org.abondar.experimental.async.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeployVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(DeployVerticle.class);


    @Override
    public void start() {
        long delay = 1000;
        for (int i = 0; i < 30; i++) {
            vertx.setTimer(delay, id -> deploy());
            delay += 1000;
        }
    }


    public void deploy() {
        vertx.deployVerticle(new EmptyVerticle(), asyncNotif -> {
            if (asyncNotif.succeeded()) {
                String id = asyncNotif.result();
                logger.info("Successfully deployed {}", id);
                vertx.setTimer(3000, timerId -> undeployVerticle(id));
            } else {
                logger.error("Error while deploying", asyncNotif.cause());
            }
        });
    }

    private void undeployVerticle(String id) {
        vertx.undeploy(id, asyncNotif -> {
            if (asyncNotif.succeeded()) {
                logger.error("{} was undeployed", id);
            } else {
                logger.error("{} could not be undeployed", id);
            }
        });
    }
}
