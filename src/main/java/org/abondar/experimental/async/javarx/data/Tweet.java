package org.abondar.experimental.async.javarx.data;

/**
 * Created by abondar on 2/2/17.
 */
public class Tweet {


    private final String text;

    Tweet(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
