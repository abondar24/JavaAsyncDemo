package org.abondar.experimental.async.multithread.exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

public class ExchangeConsumer extends Thread{

    private final Exchanger<List<Integer>> exchanger;
    private List<Integer> buffer = new ArrayList<>();
    private final Random random = new Random();

    public ExchangeConsumer(Exchanger<List<Integer>> exchanger){
        this.exchanger = exchanger;
    }

    @Override
    public void run(){
        while (true){
            try {
                System.out.println("Waiting for data exchange");

                buffer = exchanger.exchange(buffer);
                System.out.println("Data received: "+buffer);
                System.out.println("Emptying buffer...");

                int sleepTime = random.nextInt(20)+1;

                Thread.sleep(sleepTime*1000);
                emptyBuffer();
            } catch (InterruptedException ex){
                System.err.println(ex.getMessage());
                System.exit(2);
            }
        }
    }

    private void emptyBuffer(){
        buffer.clear();
    }
}
