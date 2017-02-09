package org.abondar.experimental.javarxdemo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.math.BigDecimal;
import java.util.Observer;
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

    private RxGroceries rxGroceries;

    public SchedulersDemo() {
        poolA = newFixedThreadPool(10, threadFactory("Sched-A-%d"));
        schedulerA = Schedulers.from(poolA);

        poolB = newFixedThreadPool(10, threadFactory("Sched-B-%d"));
        schedulerB = Schedulers.from(poolB);


        poolC = newFixedThreadPool(10, threadFactory("Sched-C-%d"));
        schedulerC = Schedulers.from(poolC);


        start = System.currentTimeMillis();

        rxGroceries = new RxGroceries();

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

    public void purchase1(){
        Observable<BigDecimal> totalPrice = Observable
                .just("bread","butter","milk","tomato","cheese")
                .subscribeOn(schedulerA)
                .map(prod ->rxGroceries.doPurchase(prod,1))
                .single();
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


    public class RxGroceries {
        public Observable<BigDecimal> purchase(String productName, int quantity){
            return Observable.fromCallable(()->doPurchase(productName,quantity));

        }

        private BigDecimal doPurchase(String productName, int quantity){
            log("Purchasing " + quantity + " " + productName);
            log("Done " + quantity + " " + productName);
            BigDecimal priceForProduct = BigDecimal.ONE;
            return priceForProduct;
        }

    }
}
