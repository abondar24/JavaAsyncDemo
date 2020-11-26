package org.abondar.experimental.async.nio;


import org.abondar.experimental.async.command.Command;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

public class PipeCommand implements Command {



    private static ReadableByteChannel startWorker(int reps) throws IOException {
        Pipe pipe = Pipe.open();
        Worker worker = new Worker(pipe.sink(), reps);
        worker.start();
        return pipe.source();
    }

    @Override
    public void execute() {
        try {
            WritableByteChannel out = Channels.newChannel(System.out);
            ReadableByteChannel workerChannel = startWorker(10);
            ByteBuffer buffer = ByteBuffer.allocate(1000);

            while (workerChannel.read(buffer) >= 0) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }

    private static class Worker extends Thread {
        WritableByteChannel channel;
        private final int reps;


        private final String[] products = {
                "To be or not to be",
                "What is the sense of life?",
                "My karma ran over my dogma"
        };

        private final Random random = new Random();


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
