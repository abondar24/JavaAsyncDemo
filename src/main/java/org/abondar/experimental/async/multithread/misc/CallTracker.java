package org.abondar.experimental.async.multithread.misc;

public class CallTracker {

    private static final ThreadLocal<Integer> th = ThreadLocal.withInitial(()->100);

    public static void call(){
        Integer counterObj = th.get();

        int counter =1;
        if (counterObj!=null){
            counter = counterObj+1;
        }

        th.set(counter);
        System.out.printf("Call counted for thread %s with counter %d\n",Thread.currentThread().getName(),counter);
    }
}
