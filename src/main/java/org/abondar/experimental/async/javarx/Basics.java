package org.abondar.experimental.async.javarx;

import org.abondar.experimental.async.javarx.util.SleeperUtil;
import rx.*;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.just;

/**
 * Created by abondar on 2/2/17.
 */
public class Basics {

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


    private static Callback getDataAsynchronously(String key) {
        final Callback callback = new Callback();
        new Thread(() -> {
            SleeperUtil.sleep(Duration.ofSeconds(1));
            callback.getOnResponse().accept(key + ":123");
        }).start();
        return callback;
    }
}
