package org.abondar.experimental.async.vertx.verticle.eventbus;



import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.RxHelper;
import org.abondar.experimental.async.vertx.service.reactivex.MessageDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class MessageProxyClient extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(MessageProxyClient.class);

    @Override
    public void start(){
        MessageDataService service = MessageDataService.createProxy(vertx,"message.data-service");

        service.rxSpecialMessage()
                .delaySubscription(5, TimeUnit.SECONDS, RxHelper.scheduler(vertx))
                .repeat()
                .map(data -> "Special message: " + data.getString("special"))
                .subscribe(logger::info);
    }
}
