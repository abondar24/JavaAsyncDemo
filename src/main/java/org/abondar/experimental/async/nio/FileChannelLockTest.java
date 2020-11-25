package org.abondar.experimental.async.nio;


import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;


//to see how locks work run two copies: one with -w /tmp/test.dat , another -r /tmp/test.dat
public class FileChannelLockTest {

    private static final int SIZEOF_INT = 4;
    private static final int INDEX_START = 0;
    private static final int INDEX_COUNT = 10;
    private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;

    private ByteBuffer buffer = ByteBuffer.allocate(INDEX_SIZE);
    private IntBuffer indexBuffer = buffer.asIntBuffer();
    private Random rand = new Random();
    private int idxVal = 1;
    private int lastLineLen = 0;

    public static void main(String[] args) throws Exception {
        boolean writer = false;
        String filename;

        if (args.length != 2) {
            System.out.println("Usage: [-r | -w] filename");
            return;
        }

        writer = args[0].equals("-w");
        filename = args[1];

        RandomAccessFile raf = new RandomAccessFile(filename, (writer) ? "rw" : "r");
        FileChannel fc = raf.getChannel();
        FileChannelLockTest test = new FileChannelLockTest();

        if (writer) {
            test.doUpdates(fc);
        } else {
            test.doQueries(fc);
        }
    }

    //read-only queries
    private void doQueries(FileChannel fc) throws Exception {

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


    //update queries
    private void doUpdates(FileChannel fc) throws Exception {
        while (true) {
            doPrint("trying for exclusive lock...");
            FileLock lock = fc.lock(INDEX_START, INDEX_SIZE, false);
            updateIndex(fc);

            lock.release();
            doPrint("<sleeping>");
            Thread.sleep(rand.nextInt(2000) + 500);
        }

    }

    private void updateIndex(FileChannel fc) throws Exception {
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

}
