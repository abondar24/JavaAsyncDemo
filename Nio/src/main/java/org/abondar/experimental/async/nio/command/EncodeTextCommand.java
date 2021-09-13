package org.abondar.experimental.async.nio.command;


import org.abondar.experimental.async.command.Command;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class EncodeTextCommand implements Command {


    private static void doEncode(Charset charsetName, String input) {
        ByteBuffer bb = charsetName.encode(input);

        System.out.println("Charset: " + charsetName.name());
        System.out.println("  Input: " + input);
        System.out.println("Encoded: ");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; bb.hasRemaining(); i++) {
            int b = bb.get();
            int ival = ((int) b) & 0xff;
            char c = (char) ival;

            //keep tabular alligment
            if (i < 10) sb.append(" ");

            String indexString = "index: " + i + " ";
            sb.append(indexString);

            if (ival < 16) sb.append("0");

            sb.append(Integer.toHexString(ival));

            //if byte is a val of printable char we can print it
            if (Character.isWhitespace(c) || Character.isISOControl(c)) {
                sb.append("\n");
            } else {
                String charString = "(" + c + ")";
                sb.append(charString);
            }
        }

        System.out.println(sb.toString());
    }

    @Override
    public void execute() {
        String input = "\u00bfMa\u00f1ana?";

        String[] charsetNames = {"US-ASCII", "UTF-8", "UTF-16BE", "UTF-16"};

        System.out.println("Encoding input: " + input);

        Arrays.asList(charsetNames).forEach(charsetName -> doEncode(Charset.forName(charsetName), input));
    }
}
