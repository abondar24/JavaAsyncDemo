package org.abondar.experimental.async.javarx;

import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;

/**
 * Created by alexabon on 2/9/2017.
 */
public class RxNettyHttpServer {
    private static final Observable<String> RESPONSE_OK = Observable.just("OK, Bro");

    public static void main(String[] args) {
        HttpServer
                .newServer(8086)
                .start((req,resp)-> resp.setHeader(CONTENT_LENGTH,2)
                        .writeStringAndFlushOnEach(RESPONSE_OK)
                ).awaitShutdown();
    }
}
