package org.abondar.experimental.async.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(WorkerVerticle.class);

    @Override
    public void start(){
        vertx.setPeriodic(3_000,id->{
           try {
               logger.info("Sleep");
               Thread.sleep(2000);
               logger.info("Active");
           } catch (InterruptedException ex){
               logger.error(ex.getMessage());
               System.exit(2);
           }
        });
    }
}
