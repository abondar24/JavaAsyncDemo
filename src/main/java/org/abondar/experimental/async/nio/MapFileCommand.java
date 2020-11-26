package org.abondar.experimental.async.nio;


import org.abondar.experimental.async.command.Command;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

//need to fix positions
public class MapFileCommand implements Command {


    private static void showBuffers(ByteBuffer ro, ByteBuffer rw, ByteBuffer cow) {
        dumpBuffers("R/O", ro);
        dumpBuffers("R/W", rw);
        dumpBuffers("COW", cow);
        System.out.println("");

    }

    private static void dumpBuffers(String prefix, ByteBuffer buffer) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(": '");

        int nulls = 0;
        int limit = buffer.limit();
        for (int i = 0; i < limit; i++) {
            char c = (char) buffer.get(i);

            if (c == '\u0000') {
                nulls++;
                continue;
            }

            if (nulls != 0) {
                String nullsStr = "|[" + nulls + "nulls]|";
                sb.append(nullsStr);
                nulls = 0;
            }
            sb.append(c);
        }
        sb.append("'");
        System.out.println(sb.toString());
    }


    @Override
    public void execute() {
        try {
            File tempFile = File.createTempFile("mmaptest", null);
            RandomAccessFile file = new RandomAccessFile(tempFile, "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer temp = ByteBuffer.allocate(100);

            temp.put("File content".getBytes());
            temp.flip();
            channel.write(temp, 0);

            //put more on another page(after 8192)
            temp.clear();
            temp.put("More content".getBytes());
            temp.flip();
            channel.write(temp, 8192);

            MappedByteBuffer ro = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            MappedByteBuffer rw = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
            MappedByteBuffer cow = channel.map(FileChannel.MapMode.PRIVATE, 0, channel.size());

            System.out.println("Begin");
            showBuffers(ro, rw, cow);

            cow.position(8);
            cow.put("COW".getBytes());

            System.out.println("Change to COW buffer");
            showBuffers(ro, rw, cow);

            rw.position(9);
            rw.put(" R/W ".getBytes());
            rw.position(8194);
            rw.put(" R/W ".getBytes());
            rw.force();

            System.out.println("Change to R/W buffer");
            showBuffers(ro, rw, cow);

            //write to file via channel
            temp.clear();
            temp.put("Channel write".getBytes());
            temp.flip();
            channel.write(temp, 0);
            temp.rewind();
            channel.write(temp, 8202);

            System.out.println("Write on channel");
            showBuffers(ro, rw, cow);

            cow.position(8207);
            cow.put(" COW2 ".getBytes());

            System.out.println("Second change to COW buffer");
            showBuffers(ro, rw, cow);

            rw.position(0);
            rw.put(" R/W2 ".getBytes());
            rw.position(8210);
            rw.put(" R/W2".getBytes());
            rw.force();

            System.out.println("Second change to R/W buffer");
            showBuffers(ro, rw, cow);

            channel.close();
            file.close();
            tempFile.delete();
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}
