package org.abondar.experimental.async.multithread.command.switcher;

import org.abondar.experimental.async.command.CommandSwitcher;
import org.abondar.experimental.async.multithread.ReadWriteLockCommand;
import org.abondar.experimental.async.multithread.command.EventLoopCommand;
import org.abondar.experimental.async.multithread.command.params.MtCommands;
import org.abondar.experimental.async.multithread.command.BarrierCommand;
import org.abondar.experimental.async.multithread.command.CallableFutureCommand;
import org.abondar.experimental.async.multithread.command.CountdownLatchCommand;
import org.abondar.experimental.async.multithread.command.DaemonThreadCommand;
import org.abondar.experimental.async.multithread.command.ExchangerCommand;
import org.abondar.experimental.async.multithread.command.ExplicitLockCommand;
import org.abondar.experimental.async.multithread.command.ForkJoinCommand;
import org.abondar.experimental.async.multithread.command.LockCommand;
import org.abondar.experimental.async.multithread.command.LowLevelWaitCommand;
import org.abondar.experimental.async.multithread.command.PhaserCommand;
import org.abondar.experimental.async.multithread.command.ProducerConsumerCommand;
import org.abondar.experimental.async.multithread.command.ReentrantLockCommand;
import org.abondar.experimental.async.multithread.command.ScheduledCommand;
import org.abondar.experimental.async.multithread.command.SemaphoreCommand;
import org.abondar.experimental.async.multithread.command.SynchronizedCommand;
import org.abondar.experimental.async.multithread.command.ThreadCommand;
import org.abondar.experimental.async.multithread.command.ThreadGroupCommand;
import org.abondar.experimental.async.multithread.command.ThreadInterruptionCommand;
import org.abondar.experimental.async.multithread.command.ThreadLocalCommand;
import org.abondar.experimental.async.multithread.command.ThreadPoolCommand;
import org.abondar.experimental.async.multithread.command.ThreadSyncCommand;
import org.abondar.experimental.async.multithread.command.TryLockCommand;
import org.abondar.experimental.async.multithread.command.VolatileCommand;
import org.abondar.experimental.async.multithread.command.WaitNotifyCommand;

public class MultiThreadCommandSwitcher extends CommandSwitcher {

    @Override
    public void executeCommand(String cmd) {
       try {
            switch (MtCommands.valueOf(cmd)){

                case BC:
                    BarrierCommand bc = new BarrierCommand();
                    executor.executeCommand(bc);
                    break;

                case CFC:
                    CallableFutureCommand cfc = new CallableFutureCommand();
                    executor.executeCommand(cfc);
                    break;

                case CLC:
                    CountdownLatchCommand clc = new CountdownLatchCommand();
                    executor.executeCommand(clc);
                    break;

                case DC:
                    DaemonThreadCommand dc = new DaemonThreadCommand();
                    executor.executeCommand(dc);
                    break;

                case EC:
                    ExchangerCommand ec = new ExchangerCommand();
                    executor.executeCommand(ec);
                    break;

                case ELC:
                    ExplicitLockCommand elc = new ExplicitLockCommand();
                    executor.executeCommand(elc);
                    break;

                case EVC:
                    EventLoopCommand evc = new EventLoopCommand();
                    executor.executeCommand(evc);
                    break;

                case FJC:
                    ForkJoinCommand fjc = new ForkJoinCommand();
                    executor.executeCommand(fjc);
                    break;

                case LC:
                    LockCommand lc = new LockCommand();
                    executor.executeCommand(lc);
                    break;

                case LWC:
                    LowLevelWaitCommand lwc = new LowLevelWaitCommand();
                    executor.executeCommand(lwc);
                    break;

                case PC:
                    PhaserCommand pc = new PhaserCommand();
                    executor.executeCommand(pc);
                    break;

                case PCC:
                    ProducerConsumerCommand pcc = new ProducerConsumerCommand();
                    executor.executeCommand(pcc);
                    break;

                case RLC:
                    ReentrantLockCommand rlc = new ReentrantLockCommand();
                    executor.executeCommand(rlc);
                    break;

                case RWLC:
                    ReadWriteLockCommand rwlc = new ReadWriteLockCommand();
                    executor.executeCommand(rwlc);
                    break;

                case SC:
                    SynchronizedCommand sc = new SynchronizedCommand();
                    executor.executeCommand(sc);
                    break;

                case SEC:
                    SemaphoreCommand sec = new SemaphoreCommand();
                    executor.executeCommand(sec);
                    break;

                case SHC:
                    ScheduledCommand shc = new ScheduledCommand();
                    executor.executeCommand(shc);
                    break;

                case TC:
                    ThreadCommand tc = new ThreadCommand();
                    executor.executeCommand(tc);
                    break;

                case TG:
                    ThreadGroupCommand tgc = new ThreadGroupCommand();
                    executor.executeCommand(tgc);
                    break;

                case THLC:
                    ThreadLocalCommand thlc = new ThreadLocalCommand();
                    executor.executeCommand(thlc);
                    break;

                case TIC:
                    ThreadInterruptionCommand tic = new ThreadInterruptionCommand();
                    executor.executeCommand(tic);
                    break;

                case TLC:
                    TryLockCommand tlc = new TryLockCommand();
                    executor.executeCommand(tlc);
                    break;

                case TPC:
                    ThreadPoolCommand tpc = new ThreadPoolCommand();
                    executor.executeCommand(tpc);
                    break;

                case TSC:
                    ThreadSyncCommand tsc = new ThreadSyncCommand();
                    executor.executeCommand(tsc);
                    break;

                case VC:
                    VolatileCommand vc = new VolatileCommand();
                    executor.executeCommand(vc);
                    break;

                case WNC:
                    WaitNotifyCommand wnc = new WaitNotifyCommand();
                    executor.executeCommand(wnc);
                    break;

            }
       } catch (IllegalArgumentException ex){
           System.err.println("Unknown argument. Please check documentation.");
           System.exit(1);
       }
    }
}
