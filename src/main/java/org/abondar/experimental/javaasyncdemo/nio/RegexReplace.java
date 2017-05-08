package org.abondar.experimental.javaasyncdemo.nio;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexReplace {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: regex replacement input [ ... ]");
            System.exit(1);
        }

        String regex = args[0];
        String replace = args[1];

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("");

        System.out.println("        regex: '" + regex + "'");
        System.out.println("  replacement: '" + replace + "'");

        for (int i = 2; i < args.length; i++) {
            System.out.println("-------------------------");
            matcher.reset(args[i]);

            System.out.println("        input: '" + args[i] + "'");
            System.out.println("replaceFirst(): '" + matcher.replaceFirst(replace) + "'");
            System.out.println("  replaceAll(): '" + matcher.replaceAll(replace) + "'");


        }
    }
}
