package org.abondar.experimental.async.vertx.verticle.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;
import org.abondar.experimental.async.vertx.service.MessageDataService;

public class MessageDataVerticle extends AbstractVerticle {

    @Override
    public void start(){
      ServiceBinder serviceBinder =  new ServiceBinder(vertx);
      serviceBinder.setAddress("message.data-service")
              .register(MessageDataService.class,MessageDataService.create(vertx));
    }
}
