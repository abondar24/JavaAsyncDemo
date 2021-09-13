package org.abondar.experimental.async.nio.command;


import org.abondar.experimental.async.command.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleGrepCommand implements Command {


    @Override
    public void execute() {
        String[] input = new String[]{"pom.xml","README.md","dummy_text.txt"};


        Pattern pattern = Pattern.compile("dependency");
        Matcher matcher = pattern.matcher("");

        try {
            for (String file : input) {
                BufferedReader br;
                String line;

                try {
                    br = new BufferedReader(new FileReader(file));
                } catch (IOException e) {
                    System.err.println("Cannot read '" + file + "': " + e.getMessage());
                    continue;
                }

                while ((line = br.readLine()) != null) {
                    matcher.reset(line);

                    if (matcher.find()) {
                        System.out.println(file + ": " + line);
                    }
                }
                br.close();
            }
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}
