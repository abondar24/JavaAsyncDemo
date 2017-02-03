package org.abondar.experimental.javarxdemo;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

import java.time.Duration;
import java.util.function.Consumer;

/**
 * Created by abondar on 2/2/17.
 */
public class Basics {

    public static String SOME_KEY = "SS217";

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
}
