package org.abondar.experimental.async.nio;

import org.abondar.experimental.async.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PoodleCommand implements Command {


    private static Pattern[] collectPatterns(String[] args) {
        List list = new ArrayList();
        for (int i = 1; i < args.length; i++) {
            list.add(Pattern.compile(args[i]));
        }

        Pattern[] patterns = new Pattern[list.size()];
        list.toArray(patterns);

        return patterns;
    }

    private static void generateTable(String input, Pattern[] patterns, int[] limits) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0'?>\n");
        sb.append("<table>\n");
        sb.append("\t<row>\n");
        sb.append("\t\t<head>Input: ");
        sb.append(input);
        sb.append("</head>\n");

        for (int i = 0; i < patterns.length; i++) {
            Pattern pattern = patterns[i];

            sb.append("\t\t<head>Regex: <value>");
            sb.append(pattern.pattern());
            sb.append("</value></head>\n");
        }

        sb.append("\t<row>\n");

        for (int i = 0; i < limits.length; i++) {
            int limit = limits[i];
            sb.append("\t<row>\n");
            sb.append("\t<entry>Limit:");
            sb.append(limit);
            sb.append("</entry>\n");


            for (int j = 0; j < patterns.length; j++) {
                Pattern pattern = patterns[j];
                String[] tokens = pattern.split(input, limit);
                sb.append("\t<entry>\n");

                for (int k = 0; k < tokens.length; k++) {
                    sb.append("<value>");
                    sb.append(tokens[k]);
                    sb.append("</value>");
                }

                sb.append("</entry>\n");
            }

            sb.append("\t</row>\n");
        }
        sb.append("</table>\n");

        System.out.println(sb.toString());
    }

    @Override
    public void execute() {
        String input = "poodle zoo";
        Pattern space = Pattern.compile(" ");
        Pattern d = Pattern.compile("d");
        Pattern o = Pattern.compile("o");
        Pattern[] patterns = {space, d, o};

        int[] limits = {1, 2, 5, -2, 0};

        generateTable(input, patterns, limits);
    }
}
