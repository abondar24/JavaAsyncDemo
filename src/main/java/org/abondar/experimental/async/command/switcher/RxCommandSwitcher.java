package org.abondar.experimental.async.command.switcher;

import org.abondar.experimental.async.command.params.RxCommands;
import org.abondar.experimental.async.javarx.command.IntervalCommand;
import org.abondar.experimental.async.javarx.command.AsyncComputationCommand;
import org.abondar.experimental.async.javarx.command.BackPressureCommand;
import org.abondar.experimental.async.javarx.command.CompletableCommand;
import org.abondar.experimental.async.javarx.command.DayCommand;
import org.abondar.experimental.async.javarx.command.DelayCommand;
import org.abondar.experimental.async.javarx.command.DelayNamesCommand;
import org.abondar.experimental.async.javarx.command.DelayedObservableCommand;
import org.abondar.experimental.async.javarx.command.FilterCommand;
import org.abondar.experimental.async.javarx.command.HelloCommand;
import org.abondar.experimental.async.javarx.command.InMemoryCommand;
import org.abondar.experimental.async.javarx.command.ListBufferCommand;
import org.abondar.experimental.async.javarx.command.MorseCommand;
import org.abondar.experimental.async.javarx.command.MultipleSubscribersCommand;
import org.abondar.experimental.async.javarx.command.NaturalNumbersCommand;
import org.abondar.experimental.async.javarx.command.NotificationsCommand;
import org.abondar.experimental.async.javarx.command.ObservableCommand;
import org.abondar.experimental.async.javarx.command.ParallelLoadCommand;
import org.abondar.experimental.async.javarx.command.MergeSinglesCommand;
import org.abondar.experimental.async.javarx.command.SchedulerComboCommand;
import org.abondar.experimental.async.javarx.command.SchedulerObserveCommand;
import org.abondar.experimental.async.javarx.command.SchedulerSubscribeCommand;
import org.abondar.experimental.async.javarx.command.SchedulerWorkerCommand;
import org.abondar.experimental.async.javarx.command.ShakespaereCommand;
import org.abondar.experimental.async.javarx.command.SingleCommand;
import org.abondar.experimental.async.javarx.command.SyncComputationCommand;
import org.abondar.experimental.async.javarx.command.TimedObservableCommand;
import org.abondar.experimental.async.javarx.command.TrueFalseCommand;
import org.abondar.experimental.async.javarx.command.TweetCommand;
import org.abondar.experimental.async.javarx.command.TwoThreadsCommand;
import org.abondar.experimental.async.javarx.command.server.NettyHttpServer;
import org.abondar.experimental.async.javarx.command.server.NettyTcpServer;
import org.abondar.experimental.async.javarx.command.server.RestCurrencyServer;
import org.abondar.experimental.async.javarx.command.server.TcpCurrencyServer;

public class RxCommandSwitcher extends CommandSwitcher{
    @Override
    public void executeCommand(String cmd) {
        try {
            switch (RxCommands.valueOf(cmd)){

                case ASYC:
                    AsyncComputationCommand asc = new AsyncComputationCommand();
                    executor.executeCommand(asc);
                    break;

                case BPC:
                    BackPressureCommand bpc = new BackPressureCommand();
                    executor.executeCommand(bpc);
                    break;

                case CC:
                    CompletableCommand cc = new CompletableCommand();
                    executor.executeCommand(cc);
                    break;

                case DC:
                    DayCommand dc = new DayCommand();
                    executor.executeCommand(dc);
                    break;

                case DEC:
                    DelayCommand dec = new DelayCommand();
                    executor.executeCommand(dec);
                    break;

                case DNC:
                    DelayNamesCommand dnc = new DelayNamesCommand();
                    executor.executeCommand(dnc);
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

                case LBC:
                    ListBufferCommand lbc = new ListBufferCommand();
                    executor.executeCommand(lbc);
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

                case NHS:
                    NettyHttpServer nhs = new NettyHttpServer();
                    executor.executeCommand(nhs);
                    break;

                case NNC:
                    NaturalNumbersCommand nnc = new NaturalNumbersCommand();
                    executor.executeCommand(nnc);
                    break;

                case NTS:
                    NettyTcpServer nts = new NettyTcpServer();
                    executor.executeCommand(nts);
                    break;

                case OC:
                    ObservableCommand oc = new ObservableCommand();
                    executor.executeCommand(oc);
                    break;

                case PLC:
                    ParallelLoadCommand plc = new ParallelLoadCommand();
                    executor.executeCommand(plc);
                    break;

                case RCS:
                    RestCurrencyServer rcs = new RestCurrencyServer();
                    executor.executeCommand(rcs);
                    break;

                case SC:
                    SingleCommand sc = new SingleCommand();
                    executor.executeCommand(sc);
                    break;

                case SCC:
                    SchedulerComboCommand scc = new SchedulerComboCommand();
                    executor.executeCommand(scc);
                    break;

                case SHC:
                    ShakespaereCommand shc = new ShakespaereCommand();
                    executor.executeCommand(shc);
                    break;

                case SOC:
                    SchedulerObserveCommand sos = new SchedulerObserveCommand();
                    executor.executeCommand(sos);
                    break;

                case SSC:
                    SchedulerSubscribeCommand ssc = new SchedulerSubscribeCommand();
                    executor.executeCommand(ssc);
                    break;

                case SWC:
                    SchedulerWorkerCommand swc = new SchedulerWorkerCommand();
                    executor.executeCommand(swc);
                    break;

                case SYC:
                    SyncComputationCommand syc = new SyncComputationCommand();
                    executor.executeCommand(syc);
                    break;

                case TC:
                    TweetCommand tc = new TweetCommand();
                    executor.executeCommand(tc);
                    break;

                case TCS:
                    TcpCurrencyServer tcs = new TcpCurrencyServer();
                    executor.executeCommand(tcs);
                    break;

                case TFC:
                    TrueFalseCommand tfc = new TrueFalseCommand();
                    executor.executeCommand(tfc);
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
