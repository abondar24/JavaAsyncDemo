package org.abondar.experimental.async.nio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TimeClient {
    private static final int DEFAULT_TIME_PORT = 37;
    private static final long DIFF_1900 = 2208988800L;
    protected int port = DEFAULT_TIME_PORT;
    protected List remoteHosts;
    protected DatagramChannel channel;

    public TimeClient(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: [-p port ] host ...");
            System.exit(1);
        }

        parseArgs(args);
        this.channel = DatagramChannel.open();
    }


    protected InetSocketAddress recievePacket(DatagramChannel channel, ByteBuffer buffer) throws Exception {
        buffer.clear();

        return (InetSocketAddress) channel.receive(buffer);
    }

    protected void sendRequests() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        Iterator it = remoteHosts.iterator();

        while (it.hasNext()) {
            InetSocketAddress sa = (InetSocketAddress) it.next();

            System.out.println("Requesting time from " + sa.getHostName() + ":" + sa.getPort());

            buffer.clear().flip();
            channel.send(buffer, sa);
        }
    }


    protected void getReplies() throws Exception {
        ByteBuffer longBuffer = ByteBuffer.allocate(8);

        longBuffer.order(ByteOrder.BIG_ENDIAN);
        longBuffer.putLong(0, 0);

        // pos of 1st pyte of low-order 32 bits
        longBuffer.position(4);

        //get view of low-order 32 bits
        ByteBuffer buffer = longBuffer.slice();
        int expect = remoteHosts.size();
        int replies = 0;

        System.out.println("");
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

    private void parseArgs(String[] args) {

        remoteHosts = new ArrayList();

        for(int i=0; i<args.length;i++){
            String arg = args[i];

            if (arg.equals("-p")){
                i++;
                this.port = Integer.parseInt(args[i]);
                continue;
            }

            InetSocketAddress sa = new InetSocketAddress(arg, port);
            if (sa.getAddress()==null){
                System.out.println("Cannot resolve address: "+ arg);
                continue;
            }

            remoteHosts.add(sa);
        }
    }

    public static void main(String[] args) throws Exception {
        TimeClient client = new TimeClient(args);
        client.sendRequests();
        client.getReplies();

    }
}

