package org.abondar.experimental.async.javarx.command;

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
public class SchedulerAbstractCommand {

    protected final Scheduler schedulerA;
    protected final Scheduler schedulerB;
    protected final Scheduler schedulerC;

    protected final long start;


    public SchedulerAbstractCommand() {
        ExecutorService poolA = newFixedThreadPool(10, threadFactory("Sched-A-%d"));
        schedulerA = Schedulers.from(poolA);

        ExecutorService poolB = newFixedThreadPool(10, threadFactory("Sched-B-%d"));
        schedulerB = Schedulers.from(poolB);


        ExecutorService poolC = newFixedThreadPool(10, threadFactory("Sched-C-%d"));
        schedulerC = Schedulers.from(poolC);


        start = System.currentTimeMillis();


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

    protected Observable<String> simple() {
        return Observable.create(subscriber -> {
            log("Subscribed");
            subscriber.onNext("A");
            subscriber.onNext("B");
            subscriber.onCompleted();
        });
    }


    protected void log(Object label) {
        System.out.println(
                System.currentTimeMillis() - start + "\t| " +
                        Thread.currentThread().getName() + "\t| " +
                        label);
    }

    protected ThreadFactory threadFactory(String pattern) {
        return new ThreadFactoryBuilder()
                .setNameFormat(pattern)
                .build();
    }

    protected Observable<UUID> store(String s) {
        return Observable.create(subscriber -> {
            log("Storing " + s);
            subscriber.onNext(UUID.randomUUID());
            subscriber.onCompleted();
        });
    }

}
