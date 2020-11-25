package org.abondar.experimental.async.nio;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleGrep {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: regex file [ ... ]");
            System.exit(1);
        }

        Pattern pattern = Pattern.compile(args[0]);
        Matcher matcher = pattern.matcher("");

        for (int i = 1; i < args.length; i++) {
            String file = args[i];
            BufferedReader br = null;
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
    }
}
