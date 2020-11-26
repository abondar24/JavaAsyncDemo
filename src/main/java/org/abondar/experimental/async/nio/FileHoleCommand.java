package org.abondar.experimental.async.nio;


import org.abondar.experimental.async.command.Command;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileHoleCommand implements Command {

    private static void putData(int pos, ByteBuffer buffer, FileChannel channel) throws IOException{
        String string = "*<-- location " +pos;

        buffer.clear();
        buffer.put(string.getBytes(StandardCharsets.UTF_8));
        buffer.flip();

        channel.position(pos);
        channel.write(buffer);
    }

    @Override
    public void execute() {
        try {
            File temp = File.createTempFile("holy",null);
            RandomAccessFile file = new RandomAccessFile(temp,"rw");
            FileChannel fileChannel = file.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
            putData(0, byteBuffer, fileChannel);
            putData(5000000, byteBuffer, fileChannel);
            putData(50000, byteBuffer, fileChannel);

            System.out.printf("Wrote temp file '%s', size=%d\n",temp.getPath(),fileChannel.size());
            fileChannel.close();
            file.close();

        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}
