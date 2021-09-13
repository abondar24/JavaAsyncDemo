package org.abondar.experimental.async.nio.command;


import org.abondar.experimental.async.command.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackSlashesCommand implements Command  {

    @Override
    public void execute() {

        // substitute a\b for XYZ or ABC in input
        String rep = "a\\\\b";
        String input = "> XYZ <=> ABC <";
        Pattern pattern = Pattern.compile("ABC|XYZ");
        Matcher matcher = pattern.matcher(input);

        System.out.println(matcher.replaceFirst(rep));
        System.out.println(matcher.replaceAll(rep));

        // change all newlines in input tp escaped, DOS-like CR/LF
        rep = "\\\\r\\\\n";
        input = "line 1\nline 2\nline 3\n";
        pattern = Pattern.compile("\\n");
        matcher = pattern.matcher(input);

        System.out.println("");
        System.out.println("Before:");
        System.out.println(input);
        System.out.println("After (dos-ified, escaped):");
        System.out.println(matcher.replaceAll(rep));
    }
}
