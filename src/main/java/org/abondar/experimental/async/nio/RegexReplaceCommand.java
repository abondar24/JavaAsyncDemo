package org.abondar.experimental.async.nio;


import org.abondar.experimental.async.command.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexReplaceCommand implements Command {


    @Override
    public void execute() {

        String regex = "[A-Z]";
        String replace = "fsadAA";
        String[] input = new String[]{
                "AAAA",
                "dsfs",
                "dsfgdgdDDSD",
                "KLOP",
                "####MMZZ"
        };

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("");

        System.out.println("        regex: '" + regex + "'");
        System.out.println("  replacement: '" + replace + "'");

        for (int i = 2; i < input.length; i++) {
            System.out.println("-------------------------");
            matcher.reset(input[i]);

            System.out.println("        input: '" + input[i] + "'");
            System.out.println("replaceFirst(): '" + matcher.replaceFirst(replace) + "'");
            System.out.println("  replaceAll(): '" + matcher.replaceAll(replace) + "'");


        }
    }
}
