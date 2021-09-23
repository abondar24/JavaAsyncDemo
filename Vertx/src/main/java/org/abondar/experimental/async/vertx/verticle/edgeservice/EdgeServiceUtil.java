package org.abondar.experimental.async.vertx.verticle.edgeservice;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class EdgeServiceUtil {


    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String SERVER_HOST = "localhost";
    public static final String PATH = "/";

    public static final int LOGGER_PORT = 8040;

    public static final int SOURCE_PORT = 8000;

    public static final int COLLECTOR_PORT = 8080;


    public static void deployEdgeService(EdgeService edgeService){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.edgeservice.MessageSource",
                new DeploymentOptions().setConfig(
                        new JsonObject().put("http.port",8000)
                ));

        vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.edgeservice.MessageSource",
                new DeploymentOptions().setConfig(
                        new JsonObject().put("http.port",8001)
                ));

        vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.edgeservice.MessageSource",
                new DeploymentOptions().setConfig(
                        new JsonObject().put("http.port",8002)
                ));

        vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.edgeservice.MessageLogger");

        switch (edgeService){
            case Callback:
                vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.edgeservice.MessageCollectorCallbackService");
                break;
            case Future:
                vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.edgeservice.MessageCollectorFutureService");
                break;
            case Reactive:
                vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.edgeservice.MessageCollectorReactiveService");
                break;
        }

    }


    public enum EdgeService{
        Callback,
        Future,
        Reactive
    }



}
