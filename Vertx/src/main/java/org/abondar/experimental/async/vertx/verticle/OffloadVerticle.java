package org.abondar.experimental.async.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OffloadVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(OffloadVerticle.class);

    @Override
    public void start(){
        vertx.setPeriodic(3000,id->{
           logger.info("Tick");
           vertx.executeBlocking(this::blockCode,this::resultHandler);
        });
    }

    private  void blockCode(Promise<String> promise) {
        logger.info("Blocking code");
        try {
            Thread.sleep(4000);
            logger.info("Done!");
            promise.complete("Ok");
        } catch (InterruptedException ex){
            promise.fail(ex.getMessage());
        }
    }

    private  void resultHandler(AsyncResult<String> asyncResult) {
        if (asyncResult.succeeded()){
            logger.info("Blocking code result: {}",asyncResult.result());
        } else {
            logger.error("Error: ",asyncResult.cause());
        }
    }


}
