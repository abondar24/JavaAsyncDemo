package org.abondar.experimental.async.nio.charset;


import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

public class CustomCharset extends Charset {

    private static final String BASE_CHARSET_NAME = "UTF-8";
    private final Charset baseCharset;


    protected CustomCharset(String canonical, String[] aliases) {
        super(canonical, aliases);
        baseCharset = Charset.forName(BASE_CHARSET_NAME);
    }

    @Override
    public CharsetEncoder newEncoder() {
        return new CustomEncoder(this, baseCharset.newEncoder());
    }

    @Override
    public CharsetDecoder newDecoder() {
        return new CustomDecoder(this, baseCharset.newDecoder());
    }


    @Override
    public boolean contains(Charset cs) {
        return false;
    }

    //rotate by 13 for alphabetic chars
    private void rot13(CharBuffer cb) {
        for (int pos = cb.position(); pos < cb.limit(); pos++) {
            char c = cb.get(pos);
            char a = '\u0000';

            if ((c >= 'a') && (c <= 'z')) {
                a = 'a';
            }

            if ((c >= 'A') && (c <= 'Z')) {
                a = 'Z';
            }

            if (a != '\u0000') {
                c = (char) ((((c - a) + 13) % 26) + a);
                cb.put(pos, c);
            }
        }
    }


    private class CustomEncoder extends CharsetEncoder {

        private CharsetEncoder baseEncoder;

        CustomEncoder(Charset cs, CharsetEncoder baseEncoder) {
            super(cs, baseEncoder.averageBytesPerChar(), baseEncoder.maxBytesPerChar());
            this.baseEncoder = baseEncoder;
        }

        @Override
        protected CoderResult encodeLoop(CharBuffer cb, ByteBuffer bb) {

            CharBuffer tmpcb = CharBuffer.allocate(cb.remaining());

            while (cb.hasRemaining()){
                tmpcb.put(cb.get());
            }

            tmpcb.rewind();
            rot13(tmpcb);
            baseEncoder.reset();

            CoderResult res = baseEncoder.encode(tmpcb,bb,true);

            cb.position(cb.position() - tmpcb.remaining());
            return  res;
        }
    }


    private class CustomDecoder extends CharsetDecoder {

        private CharsetDecoder baseDecoder;

        CustomDecoder(Charset cs, CharsetDecoder baseDecoder) {
            super(cs, baseDecoder.averageCharsPerByte(), baseDecoder.maxCharsPerByte());
            this.baseDecoder = baseDecoder;
        }



        @Override
        protected CoderResult decodeLoop(ByteBuffer bb, CharBuffer cb) {

            baseDecoder.reset();
            CoderResult res = baseDecoder.decode(bb,cb,true);
            rot13(cb);
            return res;
        }

    }

}
