package org.abondar.experimental.async.nio.command.switcher;


import org.abondar.experimental.async.command.CommandSwitcher;
import org.abondar.experimental.async.nio.command.params.NioCommands;
import org.abondar.experimental.async.nio.client.AsyncClient;
import org.abondar.experimental.async.nio.command.BackSlashesCommand;
import org.abondar.experimental.async.nio.command.BufferCharViewCommand;
import org.abondar.experimental.async.nio.command.BufferFillDrainCommand;
import org.abondar.experimental.async.nio.command.ChannelCopyCommand;
import org.abondar.experimental.async.nio.command.ChannelTransferFilesCommand;
import org.abondar.experimental.async.nio.command.CharSeqCommand;
import org.abondar.experimental.async.nio.command.CharsetDecodeCommand;
import org.abondar.experimental.async.nio.command.ComplexGrepCommand;
import org.abondar.experimental.async.nio.command.CustomCharsetCommand;
import org.abondar.experimental.async.nio.command.EmailFinderCommand;
import org.abondar.experimental.async.nio.command.EncodeTextCommand;
import org.abondar.experimental.async.nio.command.FileHoleCommand;
import org.abondar.experimental.async.nio.command.FileLockQueryCommand;
import org.abondar.experimental.async.nio.command.FileLockUpdateCommand;
import org.abondar.experimental.async.nio.command.GatheringWriteCommand;
import org.abondar.experimental.async.nio.command.MapFileCommand;
import org.abondar.experimental.async.nio.server.MappedHttpServer;
import org.abondar.experimental.async.nio.command.PipeCommand;
import org.abondar.experimental.async.nio.command.PoodleCommand;
import org.abondar.experimental.async.nio.command.RegexAppendCommand;
import org.abondar.experimental.async.nio.command.RegexReplaceCommand;
import org.abondar.experimental.async.nio.command.SimpleGrepCommand;
import org.abondar.experimental.async.nio.server.SocketSelectorServer;
import org.abondar.experimental.async.nio.server.SocketSelectorServerThreadPool;
import org.abondar.experimental.async.nio.server.SocketServer;
import org.abondar.experimental.async.nio.client.TimeClient;
import org.abondar.experimental.async.nio.server.TimeServer;

public class NioCommandSwitcher extends CommandSwitcher {
    @Override
    public void executeCommand(String cmd) {
          try {
              switch (NioCommands.valueOf(cmd)){
                  case AC:
                      AsyncClient ac = new AsyncClient();
                      executor.executeCommand(ac);
                      break;

                  case BSC:
                      BackSlashesCommand bsc = new BackSlashesCommand();
                      executor.executeCommand(bsc);
                      break;

                  case BCV:
                      BufferCharViewCommand bcv = new BufferCharViewCommand();
                      executor.executeCommand(bcv);
                      break;

                  case BFD:
                      BufferFillDrainCommand bfd = new BufferFillDrainCommand();
                      executor.executeCommand(bfd);
                      break;

                  case CDC:
                      CharsetDecodeCommand cdc = new CharsetDecodeCommand();
                      executor.executeCommand(cdc);
                      break;

                  case CGC:
                      ComplexGrepCommand cgc = new ComplexGrepCommand();
                      executor.executeCommand(cgc);
                      break;

                  case CHAC:
                      CustomCharsetCommand chac = new CustomCharsetCommand();
                      executor.executeCommand(chac);
                      break;

                  case CHC:
                      ChannelCopyCommand chc = new ChannelCopyCommand();
                      executor.executeCommand(chc);
                      break;

                  case CTF:
                      ChannelTransferFilesCommand ctf = new ChannelTransferFilesCommand();
                      executor.executeCommand(ctf);
                      break;

                  case CSC:
                      CharSeqCommand csc = new CharSeqCommand();
                      executor.executeCommand(csc);
                      break;

                  case EFC:
                      EmailFinderCommand efc = new EmailFinderCommand();
                      executor.executeCommand(efc);
                      break;

                  case ETC:
                      EncodeTextCommand etc = new EncodeTextCommand();
                      executor.executeCommand(etc);
                      break;

                  case FHC:
                      FileHoleCommand fhc = new FileHoleCommand();
                      executor.executeCommand(fhc);
                      break;

                  case FLQ:
                      FileLockQueryCommand flq = new FileLockQueryCommand();
                      executor.executeCommand(flq);
                      break;

                  case FLU:
                      FileLockUpdateCommand flu = new FileLockUpdateCommand();
                      executor.executeCommand(flu);
                      break;

                  case GWC:
                      GatheringWriteCommand gwc = new GatheringWriteCommand();
                      executor.executeCommand(gwc);
                      break;

                  case MFC:
                      MapFileCommand mfc = new MapFileCommand();
                      executor.executeCommand(mfc);
                      break;

                  case MHS:
                      MappedHttpServer mhs = new MappedHttpServer();
                      executor.executeCommand(mhs);
                      break;

                  case PIC:
                      PipeCommand pic = new PipeCommand();
                      executor.executeCommand(pic);
                      break;

                  case POC:
                      PoodleCommand poc = new PoodleCommand();
                      executor.executeCommand(poc);
                      break;

                  case RAC:
                      RegexAppendCommand rac = new RegexAppendCommand();
                      executor.executeCommand(rac);
                      break;

                  case RRC:
                      RegexReplaceCommand rrc = new RegexReplaceCommand();
                      executor.executeCommand(rrc);
                      break;

                  case SES:
                      SocketSelectorServer ses = new SocketSelectorServer();
                      executor.executeCommand(ses);
                      break;

                  case SEST:
                      SocketSelectorServerThreadPool sest = new SocketSelectorServerThreadPool();
                      executor.executeCommand(sest);

                  case SGC:
                      SimpleGrepCommand sgc = new SimpleGrepCommand();
                      executor.executeCommand(sgc);
                      break;

                  case SS:
                      SocketServer ss = new SocketServer();
                      executor.executeCommand(ss);
                      break;

                  case TC:
                      TimeClient tc = new TimeClient();
                      executor.executeCommand(tc);
                      break;

                  case TS:
                      TimeServer ts = new TimeServer();
                      executor.executeCommand(ts);
                      break;
              }
          } catch (IllegalArgumentException ex){
              System.err.println("Unknown argument. Please check documentation.");
              System.exit(1);
          }
    }
}
