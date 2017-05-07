package org.abondar.experimental.javaasyncdemo.nio;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelCopy {

    public static void main(String[] args) throws IOException {
        ReadableByteChannel src = Channels.newChannel(System.in);
        WritableByteChannel dst = Channels.newChannel(System.out);


        switch (args[0]) {
            case "1":
                channelCopy1(src, dst);
            case "2":
                channelCopy2(src, dst);
        }

        src.close();
        dst.close();
    }


    private static void channelCopy1(ReadableByteChannel src, WritableByteChannel dst) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);

        while (src.read(buffer) != -1) {
            buffer.flip();
            dst.write(buffer);
            buffer.compact();
        }

        buffer.flip();
        while (buffer.hasRemaining()) {
            dst.write(buffer);
        }
    }

    private static void channelCopy2(ReadableByteChannel src, WritableByteChannel dst) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);

        while (src.read(buffer) != -1) {
            buffer.flip();

            while (buffer.hasRemaining()) {
                dst.write(buffer);
            }

            buffer.clear();
        }

    }

}
