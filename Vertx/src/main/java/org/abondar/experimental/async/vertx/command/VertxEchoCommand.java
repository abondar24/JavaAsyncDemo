package org.abondar.experimental.async.vertx.command;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;
import org.abondar.experimental.async.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertxEchoCommand implements Command {

    private static Logger logger = LoggerFactory.getLogger(VertxEchoCommand.class);

    private static int connections = 0;
    @Override
    public void execute() {
        Vertx vertx = Vertx.vertx();


        vertx.createNetServer()
                .connectHandler(VertxEchoCommand::echoClient)
                .listen(3000);

        vertx.setPeriodic(5000,id-> logger.info(numberConnections()));

        vertx.createHttpServer()
                .requestHandler(req->req.response().end(numberConnections()))
                .listen(8080);

    }

    private static void echoClient(NetSocket netSocket) {
        connections++;

        netSocket.handler(buffer -> {
            netSocket.write(buffer);
            if (buffer.toString().endsWith("/quit\n")){
                netSocket.close();
            }
        });

        netSocket.closeHandler(c-> connections--);
    }

    private String numberConnections(){
        return String.format("Currently connected %d\n",connections);
    }

}
