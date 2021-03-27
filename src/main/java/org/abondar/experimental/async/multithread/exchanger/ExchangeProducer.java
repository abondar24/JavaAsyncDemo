package org.abondar.experimental.async.multithread.exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

public class ExchangeProducer extends Thread {

    private List<Integer> buffer = new ArrayList<>();
    private final Random random = new Random();
    private int curVal = 0;
    private final Exchanger<List<Integer>> exchanger;
    private final int bufferLimit;


    public ExchangeProducer(Exchanger<List<Integer>> exchanger, int bufferLimit) {
        this.exchanger = exchanger;
        this.bufferLimit = bufferLimit;
    }

    @Override
    public void run(){
        while (true){
        try{
            System.out.println("Filling buffer data");

            int sleepTime = random.nextInt(20) +1;
            Thread.sleep(sleepTime*1000);

            fillBuffer();
            System.out.println("Producer has executed: "+bufferLimit);
            System.out.println("Waiting for data exchange");
            buffer = exchanger.exchange(buffer);
        }catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

        }
    }

    private void fillBuffer(){
        for (int i=0;i<bufferLimit;i++){
            buffer.add(curVal++);
        }
    }

}
