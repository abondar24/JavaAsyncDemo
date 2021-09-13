package org.abondar.experimental.async.nio.command;


import org.abondar.experimental.async.command.Command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;


public class FileLockUpdateCommand implements Command {

    private static final int SIZEOF_INT = 4;
    private static final int INDEX_START = 0;
    private static final int INDEX_COUNT = 10;
    private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;

    private final ByteBuffer buffer = ByteBuffer.allocate(INDEX_SIZE);
    private final IntBuffer indexBuffer = buffer.asIntBuffer();
    private final Random rand = new Random();
    private int idxVal = 1;
    private int lastLineLen = 0;


    //update queries
    private void doUpdates(FileChannel fc) throws IOException,InterruptedException {
        while (true) {
            doPrint("trying for exclusive lock...");
            FileLock lock = fc.lock(INDEX_START, INDEX_SIZE, false);
            updateIndex(fc);

            lock.release();
            doPrint("<sleeping>");
            Thread.sleep(rand.nextInt(2000) + 500);
        }

    }

    private void updateIndex(FileChannel fc) throws IOException,InterruptedException {
        indexBuffer.clear();

        for (int i = 0; i < INDEX_COUNT; i++) {
            idxVal++;
            doPrint("Updating index " + i + "=" + idxVal);
            indexBuffer.put(idxVal);
            Thread.sleep(500);
        }
        buffer.clear();
        fc.write(buffer, INDEX_START);

    }

    private void doPrint(String msg) {

        StringBuilder sb = new StringBuilder();
        sb.append("\r ");
        sb.append(msg);

        for (int i = msg.length(); i < lastLineLen; i++) {
            sb.append(" ");
        }

        sb.append("\r");
        System.out.println(sb.toString());
        System.out.flush();
        lastLineLen = msg.length();
    }

    @Override
    public void execute() {
        String filename = "dummy_text.txt";

        try {
            RandomAccessFile rwRaf = new RandomAccessFile(filename,"rw");
            FileChannel fc = rwRaf.getChannel();
            doUpdates(fc);
//            RandomAccessFile rRaf = new RandomAccessFile(filename,"r");
//            FileChannel fc1 = rRaf.getChannel();
//            doQueries(fc1);
//


        }catch (IOException | InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }


    }


}
