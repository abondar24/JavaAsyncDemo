package org.abondar.experimental.javarxdemo;

import rx.Completable;

/**
 * Created by abondar on 2/2/17.
 */
public class Main {

    public static void main(String[] args) {
        Basics.helloRx();
        Basics.inMemoryData();
        Basics.syncComputation();
        Basics.syncAsyncComputation();
        Basics.twoThreads();
        Basics.singles();
        Basics.singles1();

        Completable completable = Basics.writeToDb("salo");


    }
}
