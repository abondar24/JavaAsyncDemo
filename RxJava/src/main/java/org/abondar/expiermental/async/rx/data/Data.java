package org.abondar.expiermental.async.rx.data;

import java.util.UUID;

/**
 * Created by abondar on 2/3/17.
 */
public class Data {
    private final String val;

    public Data(){
        val = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Data{" +
                "val='" + val + '\'' +
                '}';
    }
}
