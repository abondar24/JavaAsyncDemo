package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;

import org.abondar.expiermental.async.rx.util.LogUtil;
import org.abondar.expiermental.async.rx.util.SleeperUtil;
import rx.Observable;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

public class TimedObservableCommand implements Command {
    @Override
    public void execute() {
        Observable
                .timer(1, SECONDS)
                .subscribe(msg-> LogUtil.log("TOC",msg));
        SleeperUtil.sleep(Duration.ofSeconds(2));
    }
}
