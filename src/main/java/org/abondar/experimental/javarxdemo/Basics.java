package org.abondar.experimental.javarxdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.*;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.abondar.experimental.javarxdemo.Sound.DAH;
import static org.abondar.experimental.javarxdemo.Sound.DI;
import static rx.Observable.just;

import org.apache.commons.lang3.tuple.Pair;


/**
 * Created by abondar on 2/2/17.
 */
public class Basics {

    public static String SOME_KEY = "SS217";
    private static final Logger log = LoggerFactory.getLogger(Basics.class);

    public static void helloRx() {
        Observable.create(subscriber -> {
            subscriber.onNext("Hello World!");
            subscriber.onCompleted();
        }).subscribe(System.out::println);
    }

    public static void inMemoryData() {
        Observable.create(subscriber -> {
            String fromCahce = getFromCache(SOME_KEY);
            if (fromCahce != null) {
                subscriber.onNext(fromCahce);
                subscriber.onCompleted();
            } else {
                getDataAsynchronously(SOME_KEY)
                        .onResponse(val -> {
                            putInCache(SOME_KEY, val);
                            subscriber.onNext(val);
                            subscriber.onCompleted();
                        }).onFailure(exception -> {
                    subscriber.onError(exception);
                });
            }
        }).subscribe(System.out::println);
    }

    public static void syncComputation() {
        Observable<Integer> observable = Observable.create(subscriber -> {
            subscriber.onNext(1);
            subscriber.onNext(2);
            subscriber.onNext(3);
        });

        observable.map(i -> "Number " + i).subscribe(System.out::println);
    }

    public static void syncAsyncComputation() {
        Observable.<Integer>create(subscriber -> {
            new Thread(() -> subscriber.onNext(42), "Blackjack Thread").start();
        })
                .doOnNext(i -> System.out.println(Thread.currentThread()))
                .filter(i -> i % 2 == 0)
                .map(i -> "Value " + i + " processed on " + Thread.currentThread())
                .subscribe(subscriber -> System.out.println("Some Value -> " + subscriber));
        System.out.println("Values are not emmited yet");
    }

    public static void twoThreads() {
        Observable<String> a = Observable.create(subscriber -> {
            new Thread(() -> {
                subscriber.onNext("one");
                subscriber.onNext("two");
                subscriber.onCompleted();
            }).start();
        });

        Observable<String> b = Observable.create(subscriber -> {
            new Thread(() -> {
                subscriber.onNext("three");
                subscriber.onNext("four");
                subscriber.onCompleted();
            }).start();
        });

        Observable<String> c = Observable.merge(a, b);
        c.subscribe(System.out::println);
    }

    public static void singles() {

        Observable<String> aMergeb = getDataA().mergeWith(getDataB());
        aMergeb.subscribe(System.out::println);
    }

    public static void singles1() {
        Single<String> s1 = getDataAsSingle(1);
        Single<String> s2 = getDataAsSingle(2);

        Observable<String> observable = Single.merge(s1, s2);
        observable.subscribe(System.out::println);
    }

    public static Completable writeToDb(String data) {
        return Completable.create(s -> {
            doAsyncWrite(data,
                    s::onCompleted,
                    s::onError);
        });
    }

    public static void subscribeToNotfications() {
        Observable<Tweet> tweets = Observable.empty();

        tweets.subscribe(System.out::println,
                Throwable::printStackTrace,
                Basics::noMore);
    }

    public static void captureAllNotifications() {
        Observable<Tweet> tweets = Observable.empty();

        Observer<Tweet> observer = new Observer<Tweet>() {

            @Override
            public void onCompleted() {
                noMore();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(Tweet tweet) {
                System.out.println(tweet);
            }
        };

        tweets.subscribe(observer);
    }


    public static void listenerControl() {
        Observable<Tweet> tweets = Observable.empty();

        Subscription subscription = tweets.subscribe(System.out::println);

        subscription.unsubscribe();
    }


    public static void listenerControl1() {
        Observable<Tweet> tweets = Observable.empty();

        Subscriber<Tweet> subscriber = new Subscriber<Tweet>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(Tweet tweet) {
                if (tweet.getText().contains("java")) {
                    unsubscribe();
                }
            }
        };

    }

    public static void masteringObservable() {
        log("Before");
        Observable
                .range(5, 3)
                .subscribe(Basics::log);
        log("After");
    }

    public static void masteringObservableDeeper() {
        Observable<Integer> ints = Observable.create(subscriber -> {
            log("Create");
            subscriber.onNext(5);
            subscriber.onNext(6);
            subscriber.onNext(7);
            log("Completed");
        });

        log("Starting");
        ints.subscribe(i -> log("Element: " + i));
        log("Exit");
    }


    public static void multipleSubscribers() {
        Observable<Integer> ints =
                Observable.<Integer>create(subscriber -> {
                            log("Create");
                            subscriber.onNext(42);
                            subscriber.onCompleted();
                        }
                ).cache();

        log("Starting");
        ints.subscribe(i -> log("Element A: " + i));
        ints.subscribe(i -> log("Element B: " + i));
        log("Exit");
    }


    public static void loopsAndSubscribers() {
        Subscription subscription = naturalNumbers().subscribe(Basics::log);
        subscription.unsubscribe();
    }

    public static <T> Observable<T> delayed(T x) {
        return Observable.create(subscriber -> {
            Runnable r = () -> {
                sleep(10, TimeUnit.SECONDS);
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
        Observable
                .timer(1, TimeUnit.SECONDS)
                .subscribe(Basics::log);
        Sleeper.sleep(Duration.ofSeconds(2));
    }


    public static void interval() {
        Observable
                .interval(1_000_000 / 60, MICROSECONDS)
                .subscribe((Long i) -> log(i));
        Sleeper.sleep(Duration.ofSeconds(2));
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
                return just(DI, DAH);
            case 'b':
                return just(DAH, DI, DI, DI);
            case 'c':
                return just(DAH, DI, DAH, DI);
            case 'd':
                return just(DAH, DI, DI);
            case 'e':
                return just(DI);
            case 'f':
                return just(DI, DI, DAH, DI);
            case 'g':
                return just(DAH, DAH, DI);
            case 'h':
                return just(DI, DI, DI, DI);
            case 'i':
                return just(DI, DI);
            case 'j':
                return just(DI, DAH, DAH, DAH);
            case 'k':
                return just(DAH, DI, DAH);
            case 'l':
                return just(DI, DAH, DI, DI);
            case 'm':
                return just(DAH, DAH);
            case 'n':
                return just(DAH, DI);
            case 'o':
                return just(DAH, DAH, DAH);
            case 'p':
                return just(DI, DAH, DAH, DI);
            case 'q':
                return just(DAH, DAH, DI, DAH);
            case 'r':
                return just(DI, DAH, DI);
            case 's':
                return just(DI, DI, DI);
            case 't':
                return just(DAH);
            case 'u':
                return just(DI, DI, DAH);
            case 'v':
                return just(DI, DI, DI, DAH);
            case 'w':
                return just(DI, DAH, DAH);
            case 'x':
                return just(DAH, DI, DI, DAH);
            case 'y':
                return just(DAH, DI, DAH, DAH);
            case 'z':
                return just(DAH, DAH, DI, DI);
            case '0':
                return just(DAH, DAH, DAH, DAH, DAH);
            case '1':
                return just(DI, DAH, DAH, DAH, DAH);
            case '2':
                return just(DI, DI, DAH, DAH, DAH);
            case '3':
                return just(DI, DI, DI, DAH, DAH);
            case '4':
                return just(DI, DI, DI, DI, DAH);
            case '5':
                return just(DI, DI, DI, DI, DI);
            case '6':
                return just(DAH, DI, DI, DI, DI);
            case '7':
                return just(DAH, DAH, DI, DI, DI);
            case '8':
                return just(DAH, DAH, DAH, DI, DI);
            case '9':
                return just(DAH, DAH, DAH, DAH, DI);
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

    public static void shakespeare() {
        Observable<String> alice = speak(
                "To be, or not to be: that is the question", 110);
        Observable<String> bob = speak(
                "Though this be madness, yet there is method in't", 90);
        Observable<String> jane = speak(
                "There are more things in Heaven and Earth, " +
                        "Horatio, than are dreamt of in your philosophy", 100);
//
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

    private static String getFromCache(String key) {
        return key + ":644";
    }

    private static void putInCache(String key, String val) {
    }

    private static Single<String> getDataA() {
        return Single.<String>create(observer -> {
            observer.onSuccess("DataA");
        }).subscribeOn(Schedulers.io());
    }

    private static Single<String> getDataB() {
        return Single.<String>create(observer -> {
            observer.onSuccess("DataB");
        }).subscribeOn(Schedulers.io());
    }

    private static Single<String> getDataAsSingle(int i) {
        return Single.just("Done: " + i);
    }

    private static void doAsyncWrite(String data, Runnable onSuccess, Consumer<Exception> onError) {
        onSuccess.run();
    }

    private static void noMore() {
    }

    private static void log(Object msg) {
        System.out.println(
                Thread.currentThread().getName() + " " + msg);
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
