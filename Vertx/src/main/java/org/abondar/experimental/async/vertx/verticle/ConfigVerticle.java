package org.abondar.experimental.async.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigVerticle extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(ConfigVerticle.class);

    @Override
    public void start(){
        logger.info("verticle - {}",config().getInteger("verticle", -1));
    }

}
