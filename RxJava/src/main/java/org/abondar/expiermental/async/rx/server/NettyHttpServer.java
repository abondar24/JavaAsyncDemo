package org.abondar.expiermental.async.rx.server;

import io.reactivex.netty.protocol.http.server.HttpServer;
import org.abondar.experimental.async.command.Command;
import rx.Observable;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;

/**
 * Created by alexabon on 2/9/2017.
 */
public class NettyHttpServer implements Command {
    private static final Observable<String> RESPONSE_OK = Observable.just("OK, Bro");

    @Override
    public void execute() {
        HttpServer
                .newServer(8086)
                .start((req,resp)-> resp.setHeader(CONTENT_LENGTH,2)
                        .writeStringAndFlushOnEach(RESPONSE_OK))
                .awaitShutdown();
    }
}
