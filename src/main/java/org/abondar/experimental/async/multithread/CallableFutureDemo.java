package org.abondar.experimental.async.multithread;


import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

public class CallableFutureDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        //callable used
      Future<Integer> future = executorService.submit(() -> {
            Random random = new Random();
            int duration = random.nextInt(4000);

            if (duration>2000){
                throw new IOException("Too much sleeping");
            }

            System.out.println("Starting");

            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Finished");

            return duration;
        });


        executorService.shutdown();


        try {
            System.out.println("Result: "+ future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
