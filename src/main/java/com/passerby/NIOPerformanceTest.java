package com.passerby;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOPerformanceTest
{


    public static void main(String[] args) {


        try {
            useFileChannel1();
           // useNormalIO();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void useNormalIO() throws Exception {

        System.out.println("useNormalIO ....");
        File file = new File("C:\\Download\\The.Pursuit.of.Happyness.2006.mkv");
        File oFile = new File("C:\\1.mkv");

        long time1 = System.currentTimeMillis();
        InputStream is = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(oFile);
        byte[] buf = new byte[64 * 10240];
        int len = 0;
        while((len = is.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fos.flush();
        fos.close();
        is.close();
        long time2 = System.currentTimeMillis();
        System.out.println("Time taken: "+(time2-time1)+" ms");
    }

    private static void useFileChannel1() throws IOException {

        File file = new File("C:\\Download\\The.Pursuit.of.Happyness.2006.mkv");
        File oFile = new File("C:\\1.mkv");
        long time1 = System.currentTimeMillis();
        final FileInputStream inputStream = new FileInputStream(file);
        final FileOutputStream outputStream = new FileOutputStream(oFile);
        final FileChannel inChannel = inputStream.getChannel();
        final FileChannel outChannel = outputStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();
        inputStream.close();
        outputStream.close();
        long time2 = System.currentTimeMillis();
        System.out.println("Time taken: "+(time2-time1)+" ms");

    }

    private static void useFileChannel() throws Exception {
        File file = new File("C:\\Download\\The.Pursuit.of.Happyness.2006.mkv");
        File oFile = new File("C:\\1.mkv");

        long time1 = System.currentTimeMillis();
        FileInputStream is = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(oFile);
        FileChannel f = is.getChannel();
        FileChannel f2 = fos.getChannel();

        ByteBuffer buf = ByteBuffer.allocateDirect(64 * 10240);
        long len = 0;
        while((len = f.read(buf)) != -1) {
            buf.flip();
            f2.write(buf);
            buf.clear();
        }

        f2.close();
        f.close();

        long time2 = System.currentTimeMillis();
        System.out.println("Time taken: "+(time2-time1)+" ms");
    }




}