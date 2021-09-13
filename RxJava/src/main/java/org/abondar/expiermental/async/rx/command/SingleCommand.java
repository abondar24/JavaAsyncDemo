package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import rx.Single;
import rx.schedulers.Schedulers;

import java.time.Instant;

public class SingleCommand  implements Command {
    @Override
    public void execute() {
        Single<String> single = Single.just("Hiiii");
        single.subscribe(System.out::println);

        Single<Instant> error = Single.error(new RuntimeException("Ooops!"));
        error.observeOn(Schedulers.io())
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace);
    }
}
