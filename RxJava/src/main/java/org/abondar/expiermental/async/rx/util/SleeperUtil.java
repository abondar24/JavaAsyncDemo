package org.abondar.expiermental.async.rx.util;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by abondar on 2/2/17.
 */
public class SleeperUtil {
    private static final Logger log = LoggerFactory.getLogger(SleeperUtil.class);
    public static final Random RAND = new Random();

    public static void sleep(Duration duration, Duration stdDev) {
        double randMillis = Math.max(0, duration.toMillis() + RAND.nextGaussian() * stdDev.toMillis());
        sleep(Duration.ofMillis((long) randMillis));
    }

    public static void sleep(Duration duration) {
        try {
            TimeUnit.MILLISECONDS.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            log.error("Sleep interrupted", e);
        }
    }

   public static void sleep(int timeout, TimeUnit unit) {
        try {
            unit.sleep(timeout);
        } catch (InterruptedException e) {
            log.error("Sleep interrupted", e);
        }
    }

    private SleeperUtil(){}
}
