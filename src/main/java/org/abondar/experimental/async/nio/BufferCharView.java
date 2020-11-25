package org.abondar.experimental.async.nio;


import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

public class BufferCharView {
    public static void main(String[] args) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);
        CharBuffer charBuffer = byteBuffer.asCharBuffer();

        byteBuffer.put(0, (byte) 0);
        byteBuffer.put(1, (byte) 'H');
        byteBuffer.put(2, (byte) 0);
        byteBuffer.put(3, (byte) 'i');
        byteBuffer.put(4, (byte) 0);
        byteBuffer.put(5, (byte) '!');
        byteBuffer.put(6, (byte) 0);

        printBuffer(byteBuffer);
        printBuffer(charBuffer);

    }

    private static void printBuffer(Buffer buffer) {
        System.out.printf("pos=%d, limit=%d, capacity=%d: '%s'\n",
                buffer.position(),buffer.limit(),buffer.capacity(),buffer.toString());

    }
}
