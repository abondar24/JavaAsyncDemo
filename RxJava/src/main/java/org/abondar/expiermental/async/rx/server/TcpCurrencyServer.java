package org.abondar.expiermental.async.rx.server;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.reactivex.netty.protocol.tcp.server.TcpServer;
import org.abondar.experimental.async.command.Command;
import rx.Observable;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by alexabon on 2/9/2017.
 */
public class TcpCurrencyServer implements Command {

    private static final BigDecimal RATE = new BigDecimal("1.06555");

    @Override
    public void execute() {
        TcpServer.newServer(8080)
                .<String,String>pipelineConfigurator(pipeline->{
                    pipeline.addLast(new LineBasedFrameDecoder(1024));
                    pipeline.addLast(new StringDecoder(UTF_8));
                })
                .start(connection ->{
                    Observable<String> output = connection
                            .getInput()
                            .map(BigDecimal::new)
                            .flatMap(TcpCurrencyServer::eurToUsd);
                    return connection.writeAndFlushOnEach(output);
                })
                .awaitShutdown();
    }

    private static Observable<String> eurToUsd(BigDecimal eur){
        return Observable.just(eur.multiply(RATE))
                .map(amount-> eur + " EUR is " +amount+ " USD\n")
                .delay(1, TimeUnit.SECONDS);
    }
}
