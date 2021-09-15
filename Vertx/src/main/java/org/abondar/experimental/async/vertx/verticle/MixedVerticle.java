package org.abondar.experimental.async.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class MixedVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(MixedVerticle.class);

    @Override
    public void start(){
        Context ctx = vertx.getOrCreateContext();

        new Thread(()->{
            try {
                run(ctx);
            } catch (InterruptedException e){
                logger.error(e.getMessage());
            }
        }).start();
    }

    private void run(Context ctx) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        logger.info("Non Vert.x thread");
        ctx.runOnContext(c->{
            logger.info("Event loop entered");
            vertx.setTimer(100,id->{
                logger.info("Countdown");
                latch.countDown();;
            });

        });
        logger.info("Waiting latch");
        latch.await();
        logger.info("Stop");
    }
}
