package org.abondar.experimental.async.nio;


import org.abondar.experimental.async.command.Command;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class AsyncClient implements Command {


    @Override
    public void execute() {
        String host = "localhost";
        int port = 80;

        try {
            InetSocketAddress addr = new InetSocketAddress(host,port);
            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(false);
            System.out.println("Initiating connection");
            sc.connect(addr);
            while (!sc.finishConnect()){
                System.out.println("Useful message");
            }
            System.out.println("Connection established");
            sc.close();
            System.out.println("Connection closed");
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}
