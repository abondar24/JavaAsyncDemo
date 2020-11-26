package org.abondar.experimental.async.nio;

import org.abondar.experimental.async.command.Command;

import java.io.IOException;

/**
 * Complex grep.  Features: instances tied to a specific regex and can be used many times
 * and they are thread safe
 */
public class ComplexGrepCommand implements Command {

    @Override
    public void execute() {

        String regex= "Dependency";
        String[] fileNames = new String[]{"pom.xml","README.md","dummy_text.txt"};

        try {
            System.out.println("Grepping Word: "+regex);
            ComplexGrepper grepper = new ComplexGrepper(regex, false);
            System.out.println("Ignore case disabled, One By One Disabled");
            grepper.doGrep(false,fileNames);

            grepper = new ComplexGrepper(regex, true);
            System.out.println("Ignore case Enabled, One By One Enabled");
            grepper.doGrep(true,fileNames);

        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }


}

