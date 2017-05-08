package org.abondar.experimental.javaasyncdemo.nio;


import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.HashSet;
import java.util.Iterator;

public class SS13CharsetProvider  extends CharsetProvider{

    private static String CHARSET_NAME = "X-SS13";
    private Charset ss13 = null;

    public SS13CharsetProvider(){
        this.ss13 = new SS13Charset(CHARSET_NAME,new String[0]);
    }

    @Override
    public Iterator<Charset> charsets() {
        HashSet set = new HashSet(1);
        set.add(ss13);
        return set.iterator();
    }

    @Override
    public Charset charsetForName(String charsetName) {
        if (charsetName.equalsIgnoreCase(CHARSET_NAME)){
            return ss13;
        }

        return null;
    }
}
