package org.abondar.experimental.javaasyncdemo.nio;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexAppend {

    public static void main(String[] args) {
        String input = "Thanks, thanks a lot";
        String regex = "([Tt])hanks";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()){
            if (matcher.group(1).equals("T")){
                matcher.appendReplacement(sb,"Thank you");
            } else {
                matcher.appendReplacement(sb,"thank you");
            }
        }

        matcher.appendTail(sb);

        System.out.println(sb.toString());
        sb.setLength(0);
        matcher.reset();

        String replacement = "$1hank you";

        while (matcher.find()){
            matcher.appendReplacement(sb,replacement);
        }

        matcher.appendTail(sb);

        System.out.println(sb.toString());
        System.out.println(matcher.replaceAll(replacement));
        System.out.println(input.replaceAll(regex,replacement));
    }
}
