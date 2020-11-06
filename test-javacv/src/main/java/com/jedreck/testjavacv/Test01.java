package com.jedreck.testjavacv;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;

import java.io.File;

public class Test01 {

    public static void main(String[] args) throws FrameGrabber.Exception {
        test01();
    }

    public static void test01() throws FrameGrabber.Exception {
        File source = new File("D:\\Desktop\\VID_20201105_163804.mp4");
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(source);
        ff.start();
        String rotate_old = ff.getVideoMetadata("rotate");//视频旋转角度，可能是null
        System.out.println(rotate_old);
        ff.close();
    }
}
