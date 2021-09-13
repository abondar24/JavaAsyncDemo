package org.abondar.experimental.async.nio.client;


import org.abondar.experimental.async.command.Command;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeClient implements Command {
    private static final int DEFAULT_TIME_PORT = 1037;
    private static final long DIFF_1900 = 2208988800L;
    protected int port = DEFAULT_TIME_PORT;

    private List<InetSocketAddress> remoteHosts;

    private DatagramChannel channel;


    protected InetSocketAddress recievePacket(DatagramChannel channel, ByteBuffer buffer) throws IOException {
        buffer.clear();

        return (InetSocketAddress) channel.receive(buffer);
    }

    private void sendRequests() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1);

        for (InetSocketAddress sa : remoteHosts) {
            System.out.println("Requesting time from " + sa.getHostName() + ":" + sa.getPort());

            buffer.clear().flip();
            channel.send(buffer, sa);
        }
    }


    private void getReplies() throws IOException {
        ByteBuffer longBuffer = ByteBuffer.allocate(8);

        longBuffer.order(ByteOrder.BIG_ENDIAN);
        longBuffer.putLong(0, 0);

        // pos of 1st pyte of low-order 32 bits
        longBuffer.position(4);

        //get view of low-order 32 bits
        ByteBuffer buffer = longBuffer.slice();
        int expect = remoteHosts.size();
        int replies = 0;

        System.out.println();
        System.out.println("Waiting for replies...");

        while (true) {
            InetSocketAddress sa;

            sa = recievePacket(channel, buffer);
            buffer.flip();
            replies++;

            printTime(longBuffer.getLong(0), sa);

            if (replies == expect) {
                System.out.println("All packets answered");
                break;
            }

            System.out.println("Recieved " + replies + " of " + expect + " replies");
        }
    }

    private void printTime(long remote1900, InetSocketAddress sa) {
        long local = System.currentTimeMillis() / 1000;
        long remote = remote1900 - DIFF_1900;
        Date remoteDate = new Date(remote * 1000);
        Date localDate = new Date(local * 1000);
        long skew = remote - local;

        System.out.println("Reply from " + sa.getHostName() + ":" + sa.getPort());
        System.out.println("   there: " + remoteDate);
        System.out.println("    here: " + localDate);
        System.out.print("   skew: ");

        if (skew == 0) {
            System.out.println("none");
        } else if (skew > 0) {
            System.out.println(skew + " seconds ahead");
        } else {
            System.out.println((-skew) + " seconds behind");
        }
    }


    @Override
    public void execute() {
        String[] hosts = new String[]{"localhost", "google.com", "vk.com"};


        remoteHosts = new ArrayList<>();

        for (String host : hosts) {

            InetSocketAddress sa = new InetSocketAddress(host, port);
            if (sa.getAddress() == null) {
                System.out.println("Cannot resolve address: " + host);
                continue;
            }

            remoteHosts.add(sa);
        }

        try {
            this.channel = DatagramChannel.open();
            sendRequests();
            getReplies();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}

