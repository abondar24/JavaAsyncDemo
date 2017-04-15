package org.abondar.experimental.javaasyncdemo.javarx;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import static java.util.concurrent.Executors.newFixedThreadPool;


/**
 * Created by abondar on 2/8/17.
 */
public class SchedulersDemo {

    private ExecutorService poolA;
    private ExecutorService poolB;
    private ExecutorService poolC;

    private Scheduler schedulerA;
    private Scheduler schedulerB;
    private Scheduler schedulerC;

    private final long start;


    public SchedulersDemo() {
        poolA = newFixedThreadPool(10, threadFactory("Sched-A-%d"));
        schedulerA = Schedulers.from(poolA);

        poolB = newFixedThreadPool(10, threadFactory("Sched-B-%d"));
        schedulerB = Schedulers.from(poolB);


        poolC = newFixedThreadPool(10, threadFactory("Sched-C-%d"));
        schedulerC = Schedulers.from(poolC);


        start = System.currentTimeMillis();


    }

    public void subscribeOnDemo() {
        log("Starting");
        Observable<String> observable = simple();
        log("Created");
        observable.subscribeOn(schedulerA)
                .subscribeOn(schedulerB)
                .subscribe(x -> log("Got " + x),
                        Throwable::printStackTrace,
                        () -> log("Completed"));
        log("Exiting");
    }

    public void observeOnDemo(){
        log("Starting");
        final Observable<String> observable = simple();
        log("created");
        observable
                .doOnNext(x -> log("Found 1: " +x))
                .observeOn(schedulerA)
                .doOnNext(x -> log("Found 2: " +x))
                .subscribe(
                        x->log("Got 1: "+x),
                        Throwable::printStackTrace,
                        ()->log("Completed"));
        log("Exiting");
    }


    public void subcribeOnObserveOnDemo(){
        log("Starting");
        Observable<String> observable = Observable.create(subscriber -> {
            log("Subscribed");
            subscriber.onNext("A");
            subscriber.onNext("B");
            subscriber.onNext("C");
            subscriber.onNext("D");
            subscriber.onCompleted();
        });
        log("Created");
        observable.subscribeOn(schedulerA)
                .flatMap(record -> store(record).subscribeOn(schedulerB))
                .observeOn(schedulerC)
                .subscribe(x -> log("Got: "+x),
                        Throwable::printStackTrace,
                        ()->log("Completed"));
        log("Exiting");
    }

    private Observable<String> simple() {
        return Observable.create(subscriber -> {
            log("Subscribed");
            subscriber.onNext("A");
            subscriber.onNext("B");
            subscriber.onCompleted();
        });
    }


    private void log(Object label) {
        System.out.println(
                System.currentTimeMillis() - start + "\t| " +
                        Thread.currentThread().getName() + "\t| " +
                        label);
    }

    private ThreadFactory threadFactory(String pattern) {
        return new ThreadFactoryBuilder()
                .setNameFormat(pattern)
                .build();
    }

    Observable<UUID> store(String s) {
        return Observable.create(subscriber -> {
            log("Storing " + s);
            subscriber.onNext(UUID.randomUUID());
            subscriber.onCompleted();
        });
    }

}
