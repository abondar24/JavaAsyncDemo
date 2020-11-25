package org.abondar.experimental.async.nio;


import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class AsyncClient {
    public static void main(String[] args) throws Exception{
        String host = "localhost";
        int port = 80;

        if (args.length==2){
            host = args[0];
            port = Integer.parseInt(args[1]);
        }

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
    }
}
