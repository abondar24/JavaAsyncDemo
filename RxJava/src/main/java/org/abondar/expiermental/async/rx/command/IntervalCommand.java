package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.expiermental.async.rx.util.LogUtil;
import org.abondar.expiermental.async.rx.util.SleeperUtil;
import rx.Observable;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.MICROSECONDS;


public class IntervalCommand implements Command {
    @Override
    public void execute() {
        Observable
                .interval(1_000_000 / 60, MICROSECONDS)
                .subscribe((Long i) -> LogUtil.log("IC",i));
        SleeperUtil.sleep(Duration.ofSeconds(2));
    }
}
