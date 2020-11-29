package org.abondar.experimental.async.javarx.command.server;

import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.reactivex.netty.protocol.tcp.server.TcpServer;
import org.abondar.experimental.async.command.Command;
import rx.Observable;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by alexabon on 2/9/2017.
 */
public class NettyTcpServer implements Command {

    public static final Observable<String> RESPONSE = Observable.just(
            "HTTP/1.1 200 OK\r\n" +
                    "Content-length: 2\r\n" +
                    "\r\n" +
                    "OK");



    @Override
    public void execute() {
        TcpServer.newServer(8084)
                .<String,String>pipelineConfigurator(pipeline->{
                    pipeline.addLast(new LineBasedFrameDecoder(128));
                    pipeline.addLast(new StringDecoder(UTF_8));
                })
                .start(connection ->{
                    Observable<String> output = connection
                            .getInput()
                            .flatMap(line ->{
                                if (line.isEmpty()){
                                    return RESPONSE;
                                } else{
                                    return Observable.empty();
                                }
                            });
                    return connection.writeAndFlushOnEach(output);
                })
                .awaitShutdown();
    }
}
