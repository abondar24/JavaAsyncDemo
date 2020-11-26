package org.abondar.experimental.async.nio;


import org.abondar.experimental.async.command.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailFinderCommand implements Command {

    @Override
    public void execute() {
        String[] emails = new String[]{
                "abondar1992@gmail.com",
                "dummy-email.com"
        };
        Pattern pattern = Pattern.compile(
                "([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]"
                        + "{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))"
                        + "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)",Pattern.MULTILINE
        );

        Matcher matcher = pattern.matcher("");

        for (String arg : emails) {
            boolean matched = false;

            System.out.println("Looking at " + arg + " ...");
            matcher.reset(arg);

            while (matcher.find()) {
                System.out.println("\t" + matcher.group());
                matched = true;
            }

            if (!matched){
                System.out.println("\tNo email addresses found");
            }
        }
    }
}
