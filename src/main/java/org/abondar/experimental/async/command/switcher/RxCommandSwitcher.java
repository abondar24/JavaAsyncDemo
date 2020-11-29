package org.abondar.experimental.async.command.switcher;

import org.abondar.experimental.async.command.params.RxCommands;
import org.abondar.experimental.async.javarx.command.AsyncComputationCommand;
import org.abondar.experimental.async.javarx.command.CompletableCommand;
import org.abondar.experimental.async.javarx.command.HelloCommand;
import org.abondar.experimental.async.javarx.command.InMemoryCommand;
import org.abondar.experimental.async.javarx.command.MultipleSubscribersCommand;
import org.abondar.experimental.async.javarx.command.NotificationsCommand;
import org.abondar.experimental.async.javarx.command.ObservableCommand;
import org.abondar.experimental.async.javarx.command.SinglesCommand;
import org.abondar.experimental.async.javarx.command.SyncComputationCommand;
import org.abondar.experimental.async.javarx.command.TwoThreadsCommand;

public class RxCommandSwitcher extends CommandSwitcher{
    @Override
    public void executeCommand(String cmd) {
        try {
            switch (RxCommands.valueOf(cmd)){

                case ASYC:
                    AsyncComputationCommand asc = new AsyncComputationCommand();
                    executor.executeCommand(asc);
                    break;

                case CC:
                    CompletableCommand cc = new CompletableCommand();
                    executor.executeCommand(cc);
                    break;

                case HC:
                    HelloCommand hc = new HelloCommand();
                    executor.executeCommand(hc);
                    break;

                case IMC:
                    InMemoryCommand imc = new InMemoryCommand();
                    executor.executeCommand(imc);
                    break;

                case MSC:
                    MultipleSubscribersCommand msc = new MultipleSubscribersCommand();
                    executor.executeCommand(msc);
                    break;

                case NC:
                    NotificationsCommand nc = new NotificationsCommand();
                    executor.executeCommand(nc);
                    break;

                case OC:
                    ObservableCommand oc = new ObservableCommand();
                    executor.executeCommand(oc);
                    break;

                case SC:
                    SinglesCommand sc = new SinglesCommand();
                    executor.executeCommand(sc);
                    break;

                case SYC:
                    SyncComputationCommand syc = new SyncComputationCommand();
                    executor.executeCommand(syc);
                    break;

                case TTC:
                    TwoThreadsCommand ttc = new TwoThreadsCommand();
                    executor.executeCommand(ttc);
                    break;
            }
        } catch (IllegalArgumentException ex){
            System.err.println("Unknown argument. Please check documentation.");
            System.exit(1);
        }
    }
}
