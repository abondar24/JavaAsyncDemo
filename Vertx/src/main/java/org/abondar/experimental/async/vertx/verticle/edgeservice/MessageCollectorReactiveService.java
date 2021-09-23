package org.abondar.experimental.async.vertx.verticle.edgeservice;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;
import io.vertx.reactivex.ext.web.client.predicate.ResponsePredicate;
import io.vertx.reactivex.ext.web.codec.BodyCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageCollectorReactiveService extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(MessageCollectorCallbackService.class);

    private WebClient webClient;


    @Override
    public Completable rxStart() {
        webClient = WebClient.create(vertx);

        return vertx.createHttpServer()
                .requestHandler(this::requestHandler)
                .rxListen(EdgeServiceUtil.COLLECTOR_PORT)
                .ignoreElement();
    }

    private void requestHandler(HttpServerRequest request) {
        Single<JsonObject> data = collectMessages();
        sendToLogger(data).subscribe(json->{
            request.response()
                    .putHeader(EdgeServiceUtil.CONTENT_TYPE_HEADER,EdgeServiceUtil.CONTENT_TYPE_JSON)
                    .end(json.encode());
        },err->{
           logger.error("Error", err);
           request.response()
                   .setStatusCode(500)
                   .end();
        });
    }

    private Single<JsonObject> sendToLogger(Single<JsonObject> data) {
        return data.flatMap(json -> webClient
                .post(EdgeServiceUtil.LOGGER_PORT,EdgeServiceUtil.SERVER_HOST,"")
                .expect(ResponsePredicate.SC_SUCCESS)
                .rxSendJsonObject(json)
                .flatMap(resp -> Single.just(json)));
    }

    private Single<JsonObject> collectMessages() {
        Single<HttpResponse<JsonObject>> s1 = fetchMessage(EdgeServiceUtil.SOURCE_PORT);
        Single<HttpResponse<JsonObject>> s2 = fetchMessage(EdgeServiceUtil.SOURCE_PORT + 1);
        Single<HttpResponse<JsonObject>> s3 = fetchMessage(EdgeServiceUtil.SOURCE_PORT + 2);

        return Single.zip(s1, s2, s3, (j1, j2, j3) -> {
            JsonArray array = new JsonArray();
            array.add(j1.body());
            array.add(j2.body());
            array.add(j3.body());

            JsonObject data = new JsonObject();
            data.put("data",array);
            return  data;
        });

    }

    private Single<HttpResponse<JsonObject>> fetchMessage(int port) {
        return webClient
                .get(port, EdgeServiceUtil.SERVER_HOST, EdgeServiceUtil.PATH)
                .expect(ResponsePredicate.SC_SUCCESS)
                .as(BodyCodec.jsonObject())
                .rxSend();
    }


}
