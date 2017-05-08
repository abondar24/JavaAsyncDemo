package org.abondar.experimental.javaasyncdemo.nio;


import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

public class PipeDemo {

    public static void main(String[] args) throws Exception {
        WritableByteChannel out = Channels.newChannel(System.out);
        ReadableByteChannel workerChannel = startWorker(10);
        ByteBuffer buffer = ByteBuffer.allocate(1000);

        while (workerChannel.read(buffer) >= 0) {
            buffer.flip();
            out.write(buffer);
            buffer.clear();
        }
    }

    private static ReadableByteChannel startWorker(int reps) throws Exception {
        Pipe pipe = Pipe.open();
        Worker worker = new Worker(pipe.sink(), reps);
        worker.start();
        return pipe.source();
    }

    private static class Worker extends Thread {
        WritableByteChannel channel;
        private int reps;


        private String[] products = {
                "To be or not to be",
                "What is the sense of life?",
                "My karma ran over my dogma"
        };

        private Random random = new Random();


        Worker(WritableByteChannel channel, int reps) {
            this.channel = channel;
            this.reps = reps;
        }


        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(1000);

            try {
                for (int i = 0; i < this.reps; i++) {
                    doWork(buffer);

                    while (channel.write(buffer) > 0) {

                    }
                }
                this.channel.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }

        private void doWork(ByteBuffer buffer) {
            int product = random.nextInt(products.length);
            buffer.clear();
            buffer.put(products[product].getBytes());
            buffer.put("\r\n".getBytes());
            buffer.flip();
        }
    }

}
