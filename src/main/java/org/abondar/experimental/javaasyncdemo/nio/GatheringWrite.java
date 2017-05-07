package org.abondar.experimental.javaasyncdemo.nio;


import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GatheringWrite {

    private static final String RESFILE = "res.txt";

    private static String [] col1 = {
            "Aggregate", "Enable", "Leverage",
            "Facilitate", "Synergize", "Repurpose",
            "Strategize", "Reinvent", "Harness"
    };

    private static String [] col2 = {
            "cross-platform", "best-of-breed", "frictionless",
            "ubiquitous", "extensible", "compelling",
            "mission-critical", "collaborative", "integrated"
    };

    private static String [] col3 = {
            "methodologies", "infomediaries", "platforms",
            "schemas", "mindshare", "paradigms",
            "functionalities", "web services", "infrastructures"
    };

    private static String newline = System.getProperty ("line.separator");

    private static Random rand = new Random();

    public static void main(String[] args) throws Exception {
        int reps = 10;
        if (args.length > 0) {
            reps = Integer.parseInt(args[0]);
        }

        FileOutputStream fos = new FileOutputStream(RESFILE);
        GatheringByteChannel gatherChannel = fos.getChannel();

        ByteBuffer[] bs = utterBS(reps);

        while (gatherChannel.write(bs) >0){
            //loop until write returns zero
        }

        System.out.println("Added everything to "+RESFILE);
        fos.close();
    }

    private static ByteBuffer[] utterBS(int reps) throws Exception {
        List<ByteBuffer> list = new ArrayList<>();

        for (int i=0; i<reps;i++){
            list.add(pickRandom(col1," "));
            list.add(pickRandom(col2," "));
            list.add(pickRandom(col3,newline));
        }

        ByteBuffer []buffers = new ByteBuffer[list.size()];
        return list.toArray(buffers);
    }

    private static ByteBuffer pickRandom(String[] strings, String suffix) throws Exception {
        String string = strings[rand.nextInt(strings.length)];
        int total = string.length() + suffix.length();
        ByteBuffer buffer = ByteBuffer.allocate(total);

        buffer.put(string.getBytes("UTF-8"));
        buffer.put(suffix.getBytes("UTF-8"));
        buffer.flip();
        return buffer;
    }
}
