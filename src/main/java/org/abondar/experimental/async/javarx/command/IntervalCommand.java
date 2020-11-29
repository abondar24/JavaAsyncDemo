package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static org.abondar.experimental.async.javarx.util.LogUtil.log;
import static org.abondar.experimental.async.javarx.util.SleeperUtil.sleep;

public class IntervalCommand implements Command {
    @Override
    public void execute() {
        Observable
                .interval(1_000_000 / 60, MICROSECONDS)
                .subscribe((Long i) -> log("IC",i));
        sleep(Duration.ofSeconds(2));
    }
}
