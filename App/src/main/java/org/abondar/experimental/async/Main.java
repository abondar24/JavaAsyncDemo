package org.abondar.experimental.async;

import org.abondar.experimental.async.command.CommandSwitcher;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
         if (args.length==0){
             System.err.println("No arguments set. Please check documentation");
             System.exit(0);
         }

        if (args.length==1){
            System.err.println("Missing argument. Please check documentation");
            System.exit(1);
        }

        CommandSwitcherFactory factory = new CommandSwitcherFactory();

        String sw = args[0].toUpperCase();
        Optional<CommandSwitcher> switcher = factory.getSwitcher(sw);

        if (!switcher.isPresent()){
            System.err.println("Unknown module");
            System.exit(1);
        }

        String cmd = args[1].toUpperCase();
        switcher.get().executeCommand(cmd);

    }
}
