package org.abondar.experimental.async.javarx;

import org.abondar.experimental.async.javarx.data.Sound;
import org.abondar.experimental.async.javarx.util.SleeperUtil;
import rx.*;
import rx.schedulers.Schedulers;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.just;
import org.apache.commons.lang3.tuple.Pair;


/**
 * Created by abondar on 2/2/17.
 */
public class Basics {



    public static Observable<String> loadRecordsFor(DayOfWeek dow) {
        switch (dow) {
            case SUNDAY:
                return Observable
                        .interval(90, MILLISECONDS)
                        .take(5)
                        .map(i -> "Sun-" + i);
            case MONDAY:
                return Observable
                        .interval(65, MILLISECONDS)
                        .take(5)
                        .map(i -> "Mon-" + i);
            default:
                throw new IllegalArgumentException("Illegal: " + dow);
        }
    }

    //TODO: split to two commands
    public static void shakespeare() {
        Observable<String> alice = speak(
                "To be, or not to be: that is the question", 110);
        Observable<String> bob = speak(
                "Though this be madness, yet there is method in't", 90);
        Observable<String> jane = speak(
                "There are more things in Heaven and Earth, " +
                        "Horatio, than are dreamt of in your philosophy", 100);

//        Observable
//                .merge(
//                        alice.map(w -> "Alice: " + w),
//                        bob.map(w   -> "Bob:   " + w),
//                        jane.map(w  -> "Jane:  " + w)
//                )
//                .subscribe(System.out::println);
//


        Observable
                .concat(
                        alice.map(w -> "Alice: " + w),
                        bob.map(w -> "Bob:   " + w),
                        jane.map(w -> "Jane:  " + w)
                )
                .subscribe(System.out::println);


        SleeperUtil.sleep(Duration.ofSeconds(10));
    }

    public static void trueFalse() {
        Observable<Boolean> trueFalse = Observable.just(true, false).repeat();
        Observable<Integer> upstream = Observable.range(30, 8);
        Observable<Integer> downstream = upstream
                .zipWith(trueFalse, Pair::of)
                .filter(Pair::getRight)
                .map(Pair::getLeft);

        downstream.subscribe(System.out::println);

    }


    public static void scheduler1() {
        Scheduler scheduler = Schedulers.immediate();
        //Scheduler scheduler = Schedulers.trampoline();
        Scheduler.Worker worker = scheduler.createWorker();
        System.out.println("Main start");
        worker.schedule(() -> {
            System.out.println("Outer start");
            SleeperUtil.sleep(Duration.ofSeconds(1));
            worker.schedule(() -> {
                System.out.println("Middle start");
                SleeperUtil.sleep(Duration.ofSeconds(1));
                worker.schedule(() -> {
                    System.out.println("Inner start");
                    SleeperUtil.sleep(Duration.ofSeconds(1));
                    System.out.println("Inner end");
                });
                System.out.println("Middle end");
            });

            System.out.println("Outer End");

        });
        System.out.println("Main end");
        worker.unsubscribe();
    }

    public static void singleDemo() {
        Single<String> single = Single.just("Hiiii");
        single.subscribe(System.out::println);

        Single<Instant> error = Single.error(new RuntimeException("Ooops!"));
        error.observeOn(Schedulers.io())
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace);
    }

    public static void delays() {
        long startTime = System.currentTimeMillis();
        Observable
                .interval(7, TimeUnit.MILLISECONDS)
                .timestamp()
                .sample(1, SECONDS)
                .map(ts -> ts.getTimestampMillis() - startTime + "ms: " + ts.getValue())
                .take(5)
                .subscribe(System.out::println);
    }

    public static void delayedNamesSample() {
        Observable<String> delayedNames = delayedNames();
        delayedNames.sample(1, SECONDS)
                .subscribe(System.out::println);
    }

    public static void delayedNamesConcatWith() {
        Observable<String> delayedNames = delayedNames();

        delayedNames
                .concatWith(Observable.<String>empty().delay(1, SECONDS))
                .sample(1, SECONDS)
                .subscribe(System.out::println);
    }

    public static void delayedNamesThrottleFirst() {
        Observable<String> delayedNames = delayedNames();

        delayedNames
                .throttleFirst(1, SECONDS)
                .subscribe(System.out::println);
    }

    public static void listBuffer(){
        Observable.range(1,7)
                .buffer(3)
                .subscribe(Basics::listOP);
    }

    public static void listBufferAvg(){
        Random random = new Random();
        Observable.defer(()->just(random.nextGaussian()))
                .repeat(1000)
                .buffer(100,1)
                .map(Basics::averageOfList)
                .subscribe(System.out::println);
    }

    public static void delayedNamesBuffered(){
        Observable<String> names =
                just("Mary", "Patricia", "Linda",
                        "Barbara",
                        "Elizabeth", "Jennifer", "Maria", "Susan",
                        "Margaret", "Dorothy");

        Observable<Long> absDelayMillis =
                just(0.1, 0.6, 0.9,
                        1.1,
                        3.3, 3.4, 3.5, 3.6,
                        4.4, 4.8)
                        .map(d -> (long) (d * 1_000));

        final Observable<String> delayedNames =Observable
                .zip(names,absDelayMillis,
                        (n, d) -> just(n).delay(d, MILLISECONDS))
                .flatMap(o -> o);

        delayedNames.buffer(1,SECONDS)
                .subscribe(System.out::println);

    }

    private static double averageOfList(List<Double> list){
        return list.stream().collect(Collectors.averagingDouble(x->x));
    }
    private static void listOP(List<Integer> list){
        list.forEach(System.out::println);
    }


    private static Observable<String> delayedNames() {
        Observable<String> names =
                just("Mary", "Patricia", "Linda",
                        "Barbara",
                        "Elizabeth", "Jennifer", "Maria", "Susan",
                        "Margaret", "Dorothy");

        Observable<Long> absDelayMillis =
                just(0.1, 0.6, 0.9,
                        1.1,
                        3.3, 3.4, 3.5, 3.6,
                        4.4, 4.8)
                        .map(d -> (long) (d * 1_000));

        final Observable<String> delayedNames = names
                .zipWith(absDelayMillis,
                        (n, d) -> just(n).delay(d, MILLISECONDS))
                .flatMap(o -> o);

        return delayedNames;
    }

    private static Observable<String> speak(String quote, long millisPerChar) {
        String[] tokens = quote.replaceAll("[:,]", "").split(" ");
        Observable<String> words = Observable.from(tokens);
        Observable<Long> absDelay = words
                .map(String::length)
                .map(len -> len * millisPerChar)
                .scan((total, currernt) -> total + currernt);
        return words
                .zipWith(absDelay.startWith(0L), Pair::of)
                .flatMap(pair -> just(pair.getLeft())
                        .delay(pair.getRight(), MILLISECONDS));
    }

    private static Callback getDataAsynchronously(String key) {
        final Callback callback = new Callback();
        new Thread(() -> {
            SleeperUtil.sleep(Duration.ofSeconds(1));
            callback.getOnResponse().accept(key + ":123");
        }).start();
        return callback;
    }
}
