package org.abondar.experimental.async.nio;


import java.nio.CharBuffer;

public class BufferFillDrain {

    private static int index = 0;

    private static String[] strings = {
            "A random string value",
            "I like burgers",
            "Let's race",
            "I believe I can fly",
            "Who's an engineer?"
    };

    public static void main(String[] args) throws Exception {
        CharBuffer buffer = CharBuffer.allocate(100);

        while (fillBuffer(buffer)) {
            buffer.flip();
            drainBuffer(buffer);
            buffer.clear();
        }
    }

    private static void drainBuffer(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println("");
    }

    private static boolean fillBuffer(CharBuffer buffer) {
        if (index >= strings.length) {
            return false;
        }

        String string = strings[index++];
        for (int i = 0; i<string.length();i++){
            buffer.put(string.charAt(i));
        }

        return true;
    }


}
