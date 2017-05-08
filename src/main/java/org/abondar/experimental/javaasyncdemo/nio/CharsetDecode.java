package org.abondar.experimental.javaasyncdemo.nio;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.*;

public class CharsetDecode {

    public static void main(String[] args) throws IOException {
        String charsetName = "UTF-8";

        if (args.length > 0) {
            charsetName = args[0];
        }
        decodeChannel(Channels.newChannel(System.in), new OutputStreamWriter(System.out),
                Charset.forName(charsetName));
    }

    private static void decodeChannel(ReadableByteChannel src, OutputStreamWriter writer, Charset charset)
            throws IOException, UnsupportedCharsetException {

        CharsetDecoder decoder = charset.newDecoder();

        decoder.onMalformedInput(CodingErrorAction.REPLACE);
        decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(16 * 1024);
        CharBuffer charBuffer = CharBuffer.allocate(57);

        CoderResult result = CoderResult.UNDERFLOW;
        boolean eof = false;

        while (!eof) {
            if (result == CoderResult.UNDERFLOW) {
                byteBuffer.clear();

                eof = (src.read(byteBuffer) == -1);
                byteBuffer.flip();
            }

            result = decoder.decode(byteBuffer, charBuffer, eof);

            if (result == CoderResult.OVERFLOW) {
                drainCharBuf(charBuffer, writer);
            }
        }

        while (decoder.flush(charBuffer) == CoderResult.OVERFLOW) {
            drainCharBuf(charBuffer, writer);
        }

        drainCharBuf(charBuffer, writer);

        src.close();
        writer.flush();
    }

    private static void drainCharBuf(CharBuffer charBuffer, OutputStreamWriter writer) throws IOException {
        charBuffer.flip();
        if (charBuffer.hasRemaining()) {
            writer.write(charBuffer.toString());
        }
        charBuffer.clear();
    }
}
