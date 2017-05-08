package org.abondar.experimental.javaasyncdemo.nio;


import java.nio.CharBuffer;

public class CharSeqDemo {
    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer("Hi");
        CharBuffer charBuffer = CharBuffer.allocate(20);

        CharSequence charSequence = "Hello World";
        printCharSeq(charSequence);

        charSequence = stringBuffer;
        printCharSeq(charSequence);

        stringBuffer.setLength(0);
        stringBuffer.append("Goodbye world");
        printCharSeq(charSequence);

        charSequence = charBuffer;
        charBuffer.put("xxxxxxxxxxxxxxxxx");
        charBuffer.clear();
        charBuffer.put("Hi Again");
        charBuffer.flip();
        printCharSeq(charSequence);

        charBuffer.clear();
        printCharSeq(charSequence);
    }

    private static void printCharSeq(CharSequence sequence) {
        System.out.println("length="+sequence.length()+
                ", content='"+sequence.toString()+"'");
    }
}
