package org.abondar.experimental.async.javarx;

/**
 * Created by abondar on 2/2/17.
 */
public class Tweet {


    private final String text;

    Tweet(String text) {
        this.text = text;
    }

    String getText() {
        return text;
    }
}
