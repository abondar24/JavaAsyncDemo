package org.abondar.experimental.javaasyncdemo.nio;


import java.nio.ByteBuffer;

public class UnsignedUtils {

    public static short getUnsignedByte(ByteBuffer bb) {
        return (short)(bb.get() & 0xff);
    }

    public static short getUnsignedByte(ByteBuffer bb, int pos) {
        return (short)(bb.get(pos) & (short)0xff);
    }

    public static void putUnsignedByte(ByteBuffer bb, int val) {
        bb.put((byte)(val & 0xff));
    }

    public static void putUnsignedByte(ByteBuffer bb, int val, int pos) {
        bb.put(pos,(byte)(val & 0xff));
    }

    public static int getUnsignedShort(ByteBuffer bb) {
        return bb.getShort() & 0xffff;
    }

    public static int getUnsignedShort(ByteBuffer bb, int pos) {
        return bb.get(pos) &0xffff;
    }

    public static void putUnsignedShort(ByteBuffer bb, int val) {
        bb.putShort((short)(val & 0xfffff));
    }

    public static void putUnsignedShort(ByteBuffer bb, int val, int pos) {
        bb.putShort(pos,(short) (val & 0xffff));
    }


    public static long getUnsignedInt(ByteBuffer bb) {
        return (long) bb.getInt() & 0xffffffffL;
    }

    public static long getUnsignedInt(ByteBuffer bb, int pos) {
        return (long)bb.getInt(pos) &0xffffffffL;
    }

    public static void putUnsignedInt(ByteBuffer bb, int val) {
        bb.putInt((int) (val & 0xffffffffL));
    }

    public static void putUnsignedInt(ByteBuffer bb, int val, int pos) {
        bb.putInt(pos,(int) (val & 0xffffffffL));
    }

}
