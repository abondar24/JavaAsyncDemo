package org.abondar.experimental.async.vertx.verticle.edgeservice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageLogger extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(MessageLogger.class);

    @Override
    public void start(){
        vertx.createHttpServer()
                .requestHandler(request -> {
                    if (badRequest(request)){
                        request.response()
                                .setStatusCode(400)
                                .end();
                    }

                    request.bodyHandler(buffer -> {
                        logger.info("Latest messages: {}",buffer.toJsonObject()
                                .encodePrettily());
                        request.response().end();
                    });
                })
                .listen(config().getInteger("http.port",EdgeServiceUtil.LOGGER_PORT));
    }

    private boolean badRequest(HttpServerRequest request) {
        return !request.method().equals(HttpMethod.POST) ||
                !EdgeServiceUtil.CONTENT_TYPE_JSON.equals(request.getHeader(EdgeServiceUtil.CONTENT_TYPE_HEADER));
    }
}
