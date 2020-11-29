package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.javarx.Sleeper;
import rx.Observable;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.abondar.experimental.async.javarx.LogUtil.log;

public class TimedObservableCommand implements Command {
    @Override
    public void execute() {
        Observable
                .timer(1, SECONDS)
                .subscribe(msg->log("TOC",msg));
        Sleeper.sleep(Duration.ofSeconds(2));
    }
}
