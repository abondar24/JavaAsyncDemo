package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

import java.time.DayOfWeek;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class DayCommand  implements Command {
    @Override
    public void execute() {
        Observable
                .just(DayOfWeek.SUNDAY,DayOfWeek.MONDAY)
                .concatMap(DayCommand::loadRecordsForDay)
                .toList()
                .doOnNext(System.out::println)
                .subscribe();
    }

    public static Observable<String> loadRecordsForDay(DayOfWeek dow) {
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
                return Observable.empty();
        }
    }
}
