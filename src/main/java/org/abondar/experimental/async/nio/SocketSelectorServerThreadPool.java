package org.abondar.experimental.async.nio;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class SocketSelectorServerThreadPool extends SocketSelectorServer {
    private static final int MAX_THREADS = 5;
    private final ThreadPool pool = new ThreadPool(MAX_THREADS);



    protected void readDataFromSocket(SelectionKey key)  {
        WorkerThread worker = pool.getWorker();

        if (worker == null) {
            return;
        }

        worker.serviceChannel(key);
    }

    private class ThreadPool {
        List idle = new ArrayList();

        ThreadPool(int poolSize) {
            for (int i = 0; i < poolSize; i++) {
                WorkerThread thread = new WorkerThread(this);

                thread.setName("Worker" + (i + 1));
                thread.start();

                idle.add(thread);
            }
        }

        WorkerThread getWorker() {
            WorkerThread worker = null;

            synchronized (idle) {
                if (idle.size() > 0) {
                    worker = (WorkerThread) idle.remove(0);
                }
            }

            return worker;
        }

        void returnWorker(WorkerThread worker) {
            synchronized (idle) {
                idle.add(worker);
            }
        }
    }


    private class WorkerThread extends Thread {
        private ByteBuffer buffer = ByteBuffer.allocate(1024);
        private ThreadPool pool;
        private SelectionKey key;

        WorkerThread(ThreadPool pool) {
            this.pool = pool;
        }

        //wait for work
        public synchronized void run() {
            System.out.println(this.getName() + " is ready");

            while (true) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                    this.isInterrupted();
                }

                if (key == null) {
                    continue;
                }

                System.out.println(this.getName() + " has been awakened");

                try {
                    drainChannel(key);
                } catch (Exception e) {
                    System.err.println("Caught '" + e + "' closing channel");

                    try {
                        key.channel().close();
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }

                    key.selector().wakeup();
                }
                key = null;

                this.pool.returnWorker(this);
            }
        }

        //inits a unit of work by worker
        synchronized void serviceChannel(SelectionKey key) {
            this.key = key;

            key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
            this.notify();
        }


        void drainChannel(SelectionKey key) throws Exception {
            SocketChannel channel = (SocketChannel) key.channel();
            int count;
            buffer.clear();

            //bad loop causes bad trip
            while ((count = channel.read(buffer)) > 0) {
                buffer.flip(); //make readable
                while (buffer.hasRemaining()) {
                    channel.write(buffer);
                }

                buffer.clear();
            }

            if (count < 0) {
                channel.close();
                return;
            }

            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
            key.selector().wakeup();
        }
    }
}
