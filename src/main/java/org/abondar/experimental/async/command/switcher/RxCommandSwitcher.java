package org.abondar.experimental.async.command.switcher;

import org.abondar.experimental.async.command.params.RxCommands;
import org.abondar.experimental.async.javarx.IntervalCommand;
import org.abondar.experimental.async.javarx.command.AsyncComputationCommand;
import org.abondar.experimental.async.javarx.command.CompletableCommand;
import org.abondar.experimental.async.javarx.command.DayCommand;
import org.abondar.experimental.async.javarx.command.DelayedObservableCommand;
import org.abondar.experimental.async.javarx.command.FilterCommand;
import org.abondar.experimental.async.javarx.command.HelloCommand;
import org.abondar.experimental.async.javarx.command.InMemoryCommand;
import org.abondar.experimental.async.javarx.command.MorseCommand;
import org.abondar.experimental.async.javarx.command.MultipleSubscribersCommand;
import org.abondar.experimental.async.javarx.command.NaturalNumbersCommand;
import org.abondar.experimental.async.javarx.command.NotificationsCommand;
import org.abondar.experimental.async.javarx.command.ObservableCommand;
import org.abondar.experimental.async.javarx.command.ParallelLoadCommand;
import org.abondar.experimental.async.javarx.command.MergeSinglesCommand;
import org.abondar.experimental.async.javarx.command.SingleCommand;
import org.abondar.experimental.async.javarx.command.SyncComputationCommand;
import org.abondar.experimental.async.javarx.command.TimedObservableCommand;
import org.abondar.experimental.async.javarx.command.TweetCommand;
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

                case DC:
                    DayCommand dc = new DayCommand();
                    executor.executeCommand(dc);
                    break;

                case DOC:
                    DelayedObservableCommand doc = new DelayedObservableCommand();
                    executor.executeCommand(doc);
                    break;

                case FC:
                    FilterCommand fc = new FilterCommand();
                    executor.executeCommand(fc);
                    break;

                case HC:
                    HelloCommand hc = new HelloCommand();
                    executor.executeCommand(hc);
                    break;

                case IC:
                    IntervalCommand ic = new IntervalCommand();
                    executor.executeCommand(ic);
                    break;

                case IMC:
                    InMemoryCommand imc = new InMemoryCommand();
                    executor.executeCommand(imc);
                    break;

                case MC:
                    MorseCommand mc = new MorseCommand();
                    executor.executeCommand(mc);
                    break;

                case MSC:
                    MultipleSubscribersCommand msc = new MultipleSubscribersCommand();
                    executor.executeCommand(msc);
                    break;

                case MSIC:
                    MergeSinglesCommand msic = new MergeSinglesCommand();
                    executor.executeCommand(msic);
                    break;

                case NC:
                    NotificationsCommand nc = new NotificationsCommand();
                    executor.executeCommand(nc);
                    break;

                case NNC:
                    NaturalNumbersCommand nnc = new NaturalNumbersCommand();
                    executor.executeCommand(nnc);
                    break;

                case OC:
                    ObservableCommand oc = new ObservableCommand();
                    executor.executeCommand(oc);
                    break;

                case PLC:
                    ParallelLoadCommand plc = new ParallelLoadCommand();
                    executor.executeCommand(plc);
                    break;

                case SC:
                    SingleCommand sc = new SingleCommand();
                    executor.executeCommand(sc);
                    break;

                case SYC:
                    SyncComputationCommand syc = new SyncComputationCommand();
                    executor.executeCommand(syc);
                    break;

                case TC:
                    TweetCommand tc = new TweetCommand();
                    executor.executeCommand(tc);
                    break;

                case TOC:
                    TimedObservableCommand toc = new TimedObservableCommand();
                    executor.executeCommand(toc);
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
