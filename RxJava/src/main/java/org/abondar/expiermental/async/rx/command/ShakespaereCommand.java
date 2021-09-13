package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import org.apache.commons.lang3.tuple.Pair;
import rx.Observable;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.abondar.experimental.async.rx.util.SleeperUtil.sleep;
import static rx.Observable.just;

public class ShakespaereCommand implements Command {
    @Override
    public void execute() {
        Observable<String> alice = speak(
                "To be, or not to be: that is the question", 110);
        Observable<String> bob = speak(
                "Though this be madness, yet there is method in't", 90);
        Observable<String> jane = speak(
                "There are more things in Heaven and Earth, " +
                        "Horatio, than are dreamt of in your philosophy", 100);

        System.out.println("Merge");
        Observable
                .merge(
                        alice.map(w -> "Alice: " + w),
                        bob.map(w   -> "Bob:   " + w),
                        jane.map(w  -> "Jane:  " + w)
                )
                .subscribe(System.out::println);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

        System.out.println("Concat");
        Observable
                .concat(
                        alice.map(w -> "Alice: " + w),
                        bob.map(w -> "Bob:   " + w),
                        jane.map(w -> "Jane:  " + w)
                )
                .subscribe(System.out::println);


        SleeperUtil.sleep(Duration.ofSeconds(10));
    }

    private Observable<String> speak(String quote, long millisPerChar) {
        String[] tokens = quote.replaceAll("[:,]", "").split(" ");
        Observable<String> words = Observable.from(tokens);
        Observable<Long> absDelay = words
                .map(String::length)
                .map(len -> len * millisPerChar)
                .scan(Long::sum);
        return words
                .zipWith(absDelay.startWith(0L), Pair::of)
                .flatMap(pair -> just(pair.getLeft())
                        .delay(pair.getRight(), MILLISECONDS));
    }

}
