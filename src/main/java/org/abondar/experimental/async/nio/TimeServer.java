package org.abondar.experimental.async.nio;


import org.abondar.experimental.async.command.Command;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

public class TimeServer implements Command {
    private static final int DEFAULT_TIME_PORT = 1037;
    private static final long DIFF_1900 = 2208988800L;
    protected DatagramChannel channel;



    public void listen() throws IOException {
        ByteBuffer longBuffer = ByteBuffer.allocate(8);
        longBuffer.order(ByteOrder.BIG_ENDIAN);
        longBuffer.putLong(0, 0);
        // pos of 1st pyte of low-order 32 bits
        longBuffer.position(4);

        ByteBuffer buffer = longBuffer.slice();

        while (true) {
            buffer.clear();
            SocketAddress sa = this.channel.receive(buffer);

            if (sa == null) {
                continue;
            }

            System.out.println("Time request from " + sa);
            buffer.clear();

            longBuffer.putLong(0, (System.currentTimeMillis() / 1000) + DIFF_1900);

            this.channel.send(buffer, sa);
        }
    }



    @Override
    public void execute() {
        int port = DEFAULT_TIME_PORT;

        try {
            this.channel = DatagramChannel.open();
            this.channel.socket().bind(new InetSocketAddress(port));

            System.out.println("Listening on port " + port + " for time requests");
            listen();
        } catch (SocketException ex){
            System.err.printf("Can't bind to port %d\n",port);
            System.exit(3);
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }
    }
}
