package org.abondar.experimental.async.nio.command;


import org.abondar.experimental.async.command.Command;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelCopyCommand implements Command {


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

    @Override
    public void execute() {
        try {
            System.out.println("Enter something:");
            ReadableByteChannel src = Channels.newChannel(System.in);
            WritableByteChannel dst = Channels.newChannel(System.out);


            channelCopy1(src, dst);
            channelCopy2(src, dst);

            src.close();
            dst.close();
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }


    }
}
