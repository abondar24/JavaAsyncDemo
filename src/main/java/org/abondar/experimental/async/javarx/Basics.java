package org.abondar.experimental.async.javarx;

import org.abondar.experimental.async.javarx.data.Data;
import org.abondar.experimental.async.javarx.data.Sound;
import rx.*;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.just;

import org.apache.commons.lang3.tuple.Pair;


/**
 * Created by abondar on 2/2/17.
 */
public class Basics {

    public static void multipleSubscribers() {
//        Observable<Integer> ints =
//                Observable.<Integer>create(subscriber -> {
//                            log("Create");
//                            subscriber.onNext(42);
//                            subscriber.onCompleted();
//                        }
//                ).cache();
//
//        log("Starting");
//        ints.subscribe(i -> log("Element A: " + i));
//        ints.subscribe(i -> log("Element B: " + i));
//        log("Exit");
    }


    public static void loopsAndSubscribers() {
//        Subscription subscription = naturalNumbers().subscribe(Basics::log);
//        subscription.unsubscribe();
    }

    public static <T> Observable<T> delayed(T x) {
        return Observable.create(subscriber -> {
            Runnable r = () -> {
                sleep(10, SECONDS);
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(x);
                    subscriber.onCompleted();
                }
            };
            final Thread thread = new Thread(r);
            thread.start();
            subscriber.add(Subscriptions.create(thread::interrupt));
        });
    }


    //parrallelloading of data
    public static Observable<Data> rxLoad(int id) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(load(id));
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    public static Observable<Data> rxLoad1(int id) {
        return Observable.fromCallable(() -> load(id));
    }


    //eq to thread sleep
    public static void observableByTimer() {
//        Observable
//                .timer(1, SECONDS)
//                .subscribe(Basics::log);
//        Sleeper.sleep(Duration.ofSeconds(2));
    }


    public static void interval() {
//        Observable
//                .interval(1_000_000 / 60, MICROSECONDS)
//                .subscribe((Long i) -> log(i));
//        Sleeper.sleep(Duration.ofSeconds(2));
    }

    public static void simpleFilter() {
        Observable<String> strings = Observable.empty();
        Observable<String> filtered = strings.filter(s -> s.startsWith("#"));
        filtered.subscribe(System.out::println);
    }


    public static void simpleFilterWithMap() {
        just(8, 9, 10)
                .doOnNext(i -> System.out.println("A: " + i))
                .filter(i -> i % 3 > 0)
                .doOnNext(i -> System.out.println("B: " + i))
                .map(i -> "#" + i * 10)
                .doOnNext(s -> System.out.println("C: " + s))
                .filter(s -> s.length() < 4)
                .subscribe(s -> System.out.println("D: " + s));
    }


    public static void numbersFlatMap() {
        Observable<Integer> numbers = just(1, 2, 3, 4);


        numbers.flatMap(x -> just(x * 2));
        numbers.flatMap(x -> (x != 10) ? just(x) : Observable.create(System.out::println));
    }


    public static Observable<Sound> toMorseCode(char ch) {
        switch (ch) {
            case 'a':
                return just(Sound.DI, Sound.DAH);
            case 'b':
                return just(Sound.DAH, Sound.DI, Sound.DI, Sound.DI);
            case 'c':
                return just(Sound.DAH, Sound.DI, Sound.DAH, Sound.DI);
            case 'd':
                return just(Sound.DAH, Sound.DI, Sound.DI);
            case 'e':
                return just(Sound.DI);
            case 'f':
                return just(Sound.DI, Sound.DI, Sound.DAH, Sound.DI);
            case 'g':
                return just(Sound.DAH, Sound.DAH, Sound.DI);
            case 'h':
                return just(Sound.DI, Sound.DI, Sound.DI, Sound.DI);
            case 'i':
                return just(Sound.DI, Sound.DI);
            case 'j':
                return just(Sound.DI, Sound.DAH, Sound.DAH, Sound.DAH);
            case 'k':
                return just(Sound.DAH, Sound.DI, Sound.DAH);
            case 'l':
                return just(Sound.DI, Sound.DAH, Sound.DI, Sound.DI);
            case 'm':
                return just(Sound.DAH, Sound.DAH);
            case 'n':
                return just(Sound.DAH, Sound.DI);
            case 'o':
                return just(Sound.DAH, Sound.DAH, Sound.DAH);
            case 'p':
                return just(Sound.DI, Sound.DAH, Sound.DAH, Sound.DI);
            case 'q':
                return just(Sound.DAH, Sound.DAH, Sound.DI, Sound.DAH);
            case 'r':
                return just(Sound.DI, Sound.DAH, Sound.DI);
            case 's':
                return just(Sound.DI, Sound.DI, Sound.DI);
            case 't':
                return just(Sound.DAH);
            case 'u':
                return just(Sound.DI, Sound.DI, Sound.DAH);
            case 'v':
                return just(Sound.DI, Sound.DI, Sound.DI, Sound.DAH);
            case 'w':
                return just(Sound.DI, Sound.DAH, Sound.DAH);
            case 'x':
                return just(Sound.DAH, Sound.DI, Sound.DI, Sound.DAH);
            case 'y':
                return just(Sound.DAH, Sound.DI, Sound.DAH, Sound.DAH);
            case 'z':
                return just(Sound.DAH, Sound.DAH, Sound.DI, Sound.DI);
            case '0':
                return just(Sound.DAH, Sound.DAH, Sound.DAH, Sound.DAH, Sound.DAH);
            case '1':
                return just(Sound.DI, Sound.DAH, Sound.DAH, Sound.DAH, Sound.DAH);
            case '2':
                return just(Sound.DI, Sound.DI, Sound.DAH, Sound.DAH, Sound.DAH);
            case '3':
                return just(Sound.DI, Sound.DI, Sound.DI, Sound.DAH, Sound.DAH);
            case '4':
                return just(Sound.DI, Sound.DI, Sound.DI, Sound.DI, Sound.DAH);
            case '5':
                return just(Sound.DI, Sound.DI, Sound.DI, Sound.DI, Sound.DI);
            case '6':
                return just(Sound.DAH, Sound.DI, Sound.DI, Sound.DI, Sound.DI);
            case '7':
                return just(Sound.DAH, Sound.DAH, Sound.DI, Sound.DI, Sound.DI);
            case '8':
                return just(Sound.DAH, Sound.DAH, Sound.DAH, Sound.DI, Sound.DI);
            case '9':
                return just(Sound.DAH, Sound.DAH, Sound.DAH, Sound.DAH, Sound.DI);
            default:
                return Observable.empty();
        }
    }

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


        Sleeper.sleep(Duration.ofSeconds(10));
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
            Sleeper.sleep(Duration.ofSeconds(1));
            worker.schedule(() -> {
                System.out.println("Middle start");
                Sleeper.sleep(Duration.ofSeconds(1));
                worker.schedule(() -> {
                    System.out.println("Inner start");
                    Sleeper.sleep(Duration.ofSeconds(1));
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
            Sleeper.sleep(Duration.ofSeconds(1));
            callback.getOnResponse().accept(key + ":123");
        }).start();
        return callback;
    }





    private static Observable<BigInteger> naturalNumbers() {
        Observable<BigInteger> naturalNumbers = Observable.create(
                subscriber -> {
                    Runnable r = () -> {
                        BigInteger i = ZERO;
                        while (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(i);
                            i = i.add(ONE);
                        }
                    };
                    new Thread(r).start();
                });
        return naturalNumbers;
    }

    private static void sleep(int timeout, TimeUnit unit) {
        try {
            unit.sleep(timeout);
        } catch (InterruptedException ignored) {
            //intentionally ignored
        }
    }

    private static Data load(Integer id) {
        return new Data();
    }
}
