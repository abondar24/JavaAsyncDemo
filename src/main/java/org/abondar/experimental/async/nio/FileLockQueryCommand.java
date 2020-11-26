package org.abondar.experimental.async.nio;

import org.abondar.experimental.async.command.Command;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;

public class FileLockQueryCommand implements Command {

    private static final int SIZEOF_INT = 4;
    private static final int INDEX_START = 0;
    private static final int INDEX_COUNT = 10;
    private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;

    private final ByteBuffer buffer = ByteBuffer.allocate(INDEX_SIZE);
    private final IntBuffer indexBuffer = buffer.asIntBuffer();
    private final Random rand = new Random();
    private int lastLineLen = 0;

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


    //read-only queries
    private void doQueries(FileChannel fc) throws IOException,InterruptedException {

        while (true) {
            doPrint("trying for shared lock...");
            FileLock lock = fc.lock(INDEX_START, INDEX_SIZE, true);
            int reps = rand.nextInt(60) + 20;

            for (int i = 0; i < reps; i++) {
                int n = rand.nextInt(INDEX_COUNT);
                int pos = INDEX_START + (n * SIZEOF_INT);

                buffer.clear();
                fc.read(buffer, pos);
                int value = indexBuffer.get(n);

                doPrint("Index entry " + n + "=" + value);
                Thread.sleep(100);
            }
            lock.release();
            doPrint("<sleepeing>");
            Thread.sleep(rand.nextInt(3000) + 500);
        }
    }

    @Override
    public void execute() {
        String filename = "dummy_text.txt";

        try {

            RandomAccessFile rRaf = new RandomAccessFile(filename,"r");
            FileChannel fc = rRaf.getChannel();
            doQueries(fc);


        }catch (IOException | InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}
