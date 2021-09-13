package org.abondar.experimental.async.nio.command;


import org.abondar.experimental.async.command.Command;

import java.nio.CharBuffer;

public class CharSeqCommand implements Command {

    private static void printCharSeq(CharSequence sequence) {
        System.out.println("length="+sequence.length()+
                ", content='"+sequence.toString()+"'");
    }

    @Override
    public void execute() {
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
}
