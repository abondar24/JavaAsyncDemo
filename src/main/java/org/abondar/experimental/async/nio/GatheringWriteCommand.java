package org.abondar.experimental.async.nio;


import org.abondar.experimental.async.command.Command;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GatheringWriteCommand implements Command {

    private  final String RESFILE = "res.txt";

    private final String [] col1 = {
            "Aggregate", "Enable", "Leverage",
            "Facilitate", "Synergize", "Repurpose",
            "Strategize", "Reinvent", "Harness"
    };

    private final String [] col2 = {
            "cross-platform", "best-of-breed", "frictionless",
            "ubiquitous", "extensible", "compelling",
            "mission-critical", "collaborative", "integrated"
    };

    private  final String [] col3 = {
            "methodologies", "infomediaries", "platforms",
            "schemas", "mindshare", "paradigms",
            "functionalities", "web services", "infrastructures"
    };

    private static final String newline = System.getProperty ("line.separator");

    private static final Random rand = new Random();



    private ByteBuffer[] utterBS(int reps)  {
        List<ByteBuffer> list = new ArrayList<>();

        for (int i=0; i<reps;i++){
            list.add(pickRandom(col1," "));
            list.add(pickRandom(col2," "));
            list.add(pickRandom(col3,newline));
        }

        ByteBuffer []buffers = new ByteBuffer[list.size()];
        return list.toArray(buffers);
    }

    private  ByteBuffer pickRandom(String[] strings, String suffix) {
        String string = strings[rand.nextInt(strings.length)];
        int total = string.length() + suffix.length();
        ByteBuffer buffer = ByteBuffer.allocate(total);

        buffer.put(string.getBytes(StandardCharsets.UTF_8));
        buffer.put(suffix.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        return buffer;
    }

    @Override
    public void execute() {
        int reps = 10;

        try {
            FileOutputStream fos = new FileOutputStream(RESFILE);
            GatheringByteChannel gatherChannel = fos.getChannel();

            ByteBuffer[] bs = utterBS(reps);

            while (gatherChannel.write(bs) >0){
                //loop until write returns zero
            }

            System.out.println("Added everything to "+RESFILE);
            fos.close();
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }


    }
}
