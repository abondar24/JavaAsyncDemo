package org.abondar.experimental.async.javarx.util;

public class LogUtil {
    public static void log(String key,Object msg) {
        System.out.println(key+": " + msg);
    }

    private LogUtil(){}
}
