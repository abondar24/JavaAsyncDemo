package org.abondar.experimental.async.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(CountVerticle.class);
    private long counter = 1;


    @Override
    public void start(){
        vertx.setPeriodic(5000,t-> logger.info("PING"));

        vertx.createHttpServer()
                .requestHandler(req->{
                    logger.info("Request #{} from {}",counter++,req.remoteAddress().host());
                })
                .listen(8024);
        logger.info("Open http://localhost:8024");
    }

}
