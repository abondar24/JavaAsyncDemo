package org.abondar.experimental.async.nio;


import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketSelectorServer {

    public static int PORT_NUMBER = 1234;
    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    public static void main(String[] args) throws Exception {
        new SocketSelectorServer().go(args);
    }

    public void go(String[] args) throws Exception {
        int port = PORT_NUMBER;

        if (args.length > 1) {
            port = Integer.parseInt(args[0]);
        }

        System.out.println("Listening on port " + port);

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        Selector selector = Selector.open();

        serverSocket.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int n = selector.select();

            if (n == 0) {
                continue;
            }

            Iterator it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();

                    registerChannel(selector, channel, SelectionKey.OP_READ);
                    sayHello(channel);
                }

                if (key.isReadable()) {
                    readDataFromSocket(key);
                }

                it.remove();
            }
        }

    }

    private void sayHello(SocketChannel channel) throws Exception {
        buffer.clear();
        buffer.put("Hi there!\r\n".getBytes());
        buffer.flip();

        channel.write(buffer);

    }

    private void readDataFromSocket(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        buffer.clear();

        while ((count = socketChannel.read(buffer)) > 0) {
            buffer.flip();

            //bad loop. very bad
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }

            buffer.clear();
        }

        if (count < 0) {
            socketChannel.close();
        }
    }

    private void registerChannel(Selector selector, SocketChannel channel, int ops) throws Exception {
        if (channel == null) {
            return;
        }

        channel.configureBlocking(false);
        channel.register(selector, ops);
    }


}
