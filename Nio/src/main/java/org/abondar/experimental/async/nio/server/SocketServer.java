package org.abondar.experimental.async.nio.server;


import org.abondar.experimental.async.command.Command;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketServer implements Command {

    public static final String GREETING = "You must be joking.\r\n";


    @Override
    public void execute() {
        int port = 1818;

       try {
           ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());

           ServerSocketChannel ssc = ServerSocketChannel.open();
           ssc.socket().bind(new InetSocketAddress(port));
           ssc.configureBlocking(false);

           while (true){
               System.out.println("Waiting for connections");

               SocketChannel sc = ssc.accept();
               if(sc == null){
                   Thread.sleep(2000);
               } else {
                   System.out.println("Incoming connection from: "+sc.socket().getRemoteSocketAddress());

                   buffer.rewind();
                   sc.write(buffer);
                   sc.close();
               }
           }
       } catch (IOException | InterruptedException ex){
           System.err.println(ex.getMessage());
           System.exit(2);
       }


    }
}
