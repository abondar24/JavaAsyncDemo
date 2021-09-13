package org.abondar.experimental.async.nio.charset;


import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.HashSet;
import java.util.Iterator;

public class CustomCharsetProvider extends CharsetProvider{

    private static final String CHARSET_NAME = "X-SS13";
    private final Charset charset;

    public CustomCharsetProvider(){
        this.charset = new CustomCharset(CHARSET_NAME,new String[0]);
    }

    @Override
    public Iterator<Charset> charsets() {
        HashSet set = new HashSet(1);
        set.add(charset);
        return set.iterator();
    }

    @Override
    public Charset charsetForName(String charsetName) {
        if (charsetName.equalsIgnoreCase(CHARSET_NAME)){
            return charset;
        }

        return null;
    }
}
