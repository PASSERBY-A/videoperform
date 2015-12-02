package com.liqiang;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by qli6 on 2015/12/1.
 */
public class VideoPerform {

    final long CHUNK = 16 * 1024 * 1024;

    private String target ="c:\\avi\\";

    private String src ="C:\\Download\\国产";

    private String appendTxt = "c:\\a.txt";

    private FileInputStream appendInputStream;

    public VideoPerform() throws FileNotFoundException {

        appendInputStream = new FileInputStream(appendTxt);

    }


    public static void main(String[] args) throws Exception {

        VideoPerform v = new VideoPerform();

        File s = new File(v.src);

        List<String> filelist = new ArrayList<String>();

        v.readFileFromfolder(s,filelist);

        for(String file:filelist)
        {

            v.append(new File(file));

        }


    }



    public void readFileFromfolder(File src, List<String> set)
    {

        File[] files = src.listFiles();

        for(File file:files)
        {
            if(file.isDirectory())
            {
                readFileFromfolder(file,set);
                continue;
            }

            set.add(file.getAbsolutePath());
        }


    }


    public void append(File file) throws Exception
    {


        FileChannel appendChannel = appendInputStream.getChannel();

        long time1 = System.currentTimeMillis();

        FileInputStream in = new FileInputStream(file);

        FileOutputStream out = new FileOutputStream(target+UUID.randomUUID().toString()+file.getName().substring(file.getName().lastIndexOf("."), file.getName().length()));

        FileChannel inChannel =  in.getChannel();

        FileChannel outChannel = out.getChannel();


        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.transferTo(0, appendChannel.size(), outChannel);



        IOUtils.closeQuietly(inChannel);
        IOUtils.closeQuietly(outChannel);

        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);


        long time2 = System.currentTimeMillis();

        System.out.println("处理完成:"+file.getAbsolutePath()+"Time taken: "+(time2-time1)+" ms");


    }





}
