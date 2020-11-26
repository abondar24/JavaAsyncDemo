package org.abondar.experimental.async.nio;

import org.abondar.experimental.async.command.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class CustomCharsetCommand  implements Command {
    @Override
    public void execute() {
        try {
            System.out.println("Enter something: ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintStream out = new PrintStream(System.out,false,"X-SS13");

            String s;

            while ((s=in.readLine())!=null){
                out.println(s);
            }

            out.flush();
        }catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}
