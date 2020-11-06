package com.jedreck.testjave;

import org.apache.commons.lang3.StringUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.AudioInfo;
import ws.schild.jave.info.VideoInfo;
import ws.schild.jave.info.VideoSize;

import java.io.File;
import java.util.Arrays;

public class TestVideo {
    public static void main(String[] args) throws EncoderException, FrameGrabber.Exception {
        test001();
    }

    public static void test002() throws EncoderException {
        // 视频
        Encoder encoder = new Encoder();
        String[] audioDecoders = encoder.getAudioDecoders();
        System.out.println("--------------------------audioDecoders--------------------------");
        Arrays.stream(audioDecoders).forEach(System.out::println);

        String[] audioEncoders = encoder.getAudioEncoders();
        System.out.println("--------------------------audioEncoders--------------------------");
        Arrays.stream(audioEncoders).forEach(System.out::println);

        String[] videoEncoders = encoder.getVideoEncoders();
        System.out.println("--------------------------videoEncoders--------------------------");
        Arrays.stream(videoEncoders).forEach(System.out::println);

        String[] videoDecoders = encoder.getVideoDecoders();
        System.out.println("--------------------------videoDecoders--------------------------");
        Arrays.stream(videoDecoders).forEach(System.out::println);

        String[] supportedEncodingFormats = encoder.getSupportedEncodingFormats();
        System.out.println("--------------------------supportedEncodingFormats--------------------------");
        Arrays.stream(supportedEncodingFormats).forEach(System.out::println);

        String[] supportedDecodingFormats = encoder.getSupportedDecodingFormats();
        System.out.println("--------------------------supportedDecodingFormats--------------------------");
        Arrays.stream(supportedDecodingFormats).forEach(System.out::println);
    }

    public static void test001() throws EncoderException, FrameGrabber.Exception {
        //源avi格式视频
        File source = new File("D:\\Desktop\\966.MOV");
//        File source = new File("D:\\Desktop\\VID_20201105_163804.mp4");
//        File source = new File("D:\\Desktop\\VID_20201105_174135.mp4");
        //转换后的mp4格式视频
//        File target = new File("D:\\Desktop\\222.mp4");
        File target = new File("D:\\Desktop\\9662.MOV");
        File target2 = new File("D:\\Desktop\\aaa.jpg");
        //获取手机是否翻转,反转多少度
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(source);
        ff.start();
        String rotate = ff.getVideoMetadata("rotate");//视频旋转角度，可能是null
        ff.close();
        // 获取视频信息
        MultimediaObject multimediaObject = new MultimediaObject(source);
        VideoInfo videoInfo = multimediaObject.getInfo().getVideo();
        AudioInfo audioInfo = multimediaObject.getInfo().getAudio();
        // 截图
//        VideoAttributes pic = new VideoAttributes();
//        pic.setCodec("png");
//        if (StringUtils.isBlank(rotate) || "180".equals(rotate)) {
//            pic.setSize(videoInfo.getSize());
//        } else {
//            pic.setSize(new VideoSize(videoInfo.getSize().getHeight(), videoInfo.getSize().getWidth()));
//        }
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setOutputFormat("image2");
//        attrs.setOffset(0f);//设置偏移位置，即开始转码位置（3秒）
//        attrs.setDuration(0.01f);//设置转码持续时间（1秒）
//        attrs.setVideoAttributes(pic);
//        Encoder encoder = new Encoder();
//        encoder.encode(multimediaObject, target2, attrs);

        // 转码
        // 视频设置
        VideoAttributes video = new VideoAttributes();
//        video.setCodec("h264");
        video.setBitRate(8000_000);
        video.setFrameRate(videoInfo.getFrameRate() > 25 ? 25 : (int) videoInfo.getFrameRate());
        video.setFaststart(true);
        // 尺寸设置
        int videoW;
        int videoH;
        if (StringUtils.isBlank(rotate) || "180".equals(rotate)) {
            videoW = videoInfo.getSize().getWidth();
            videoH = videoInfo.getSize().getHeight();
            if (videoW > 1920) {
                float rat = (float) videoW / 1920;
                video.setSize(new VideoSize(1920, (int) (videoH / rat)));
            } else {
                video.setSize(new VideoSize(videoW, videoH));
            }
        } else {
            videoW = videoInfo.getSize().getHeight();
            videoH = videoInfo.getSize().getWidth();
            if (videoH > 1920) {
                float rat = (float) videoH / 1920;
                video.setSize(new VideoSize((int) (videoW / rat), 1920));
            } else {
                video.setSize(new VideoSize(videoW, videoH));
            }
        }

        // 音频设置
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("aac");
        audio.setBitRate(audioInfo.getBitRate());
        audio.setSamplingRate(audioInfo.getSamplingRate());
        audio.setChannels(2);

        //渲染
        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setOutputFormat("MOV");
        encodingAttributes.setVideoAttributes(video);
        encodingAttributes.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(multimediaObject, target, encodingAttributes);
    }

    public static void test01() throws EncoderException {
        //源avi格式视频
        File source = new File("D:\\Study\\RabbitMQ\\Rabbit入门到精通\\00-今日大纲.avi");
        //转换后的mp4格式视频
        File target = new File("D:\\Study\\RabbitMQ\\Rabbit入门到精通\\00-今日大纲.mp4");
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("aac"); //音频编码格式
        audio.setBitRate(64000);
        audio.setChannels(1);
        audio.setSamplingRate(22000);
        VideoAttributes video = new VideoAttributes();
        video.setCodec("h264");//视频编码格式
        video.setBitRate(180000);
        video.setFrameRate(1);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("mp4");
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        MultimediaObject multimediaObject = new MultimediaObject(source);
        encoder.encode(multimediaObject, target, attrs);//转换开始。。。
    }

    public static void test02() throws EncoderException {
        //源avi格式视频
        File source = new File("D:\\Study\\【千锋Java】ElasticSearch6入门教程（62集）\\千锋Java教程：62.在Java应用中实现集群管理.mp4");
        //转换后的mp4格式视频
        File target = new File("D:\\Study\\【千锋Java】ElasticSearch6入门教程（62集）\\千锋Java教程：62.在Java应用中实现集群管理2.mp4");
        long time = System.currentTimeMillis();
        MultimediaObject object = new MultimediaObject(source);
        AudioInfo audioInfo = object.getInfo().getAudio();
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("aac");
        if (audioInfo.getBitRate() > 128000) {
            audio.setBitRate(128000);
        }
        audio.setChannels(audioInfo.getChannels());
        if (audioInfo.getSamplingRate() > 48050) {
            audio.setSamplingRate(48050);
        }
        VideoInfo videoInfo = object.getInfo().getVideo();
        VideoAttributes video = new VideoAttributes();
        video.setCodec("h264");
        if (videoInfo.getBitRate() > 800000) {
            video.setBitRate(800000);
        }
        if (videoInfo.getFrameRate() > 20) {
            video.setFrameRate(20);
        }
        int width = videoInfo.getSize().getWidth();
        int height = videoInfo.getSize().getHeight();
        if (width > 1280) {
            float rat = (float) width / 1280;
            video.setSize(new VideoSize(1280, (int) (height / rat)));
        }
        EncodingAttributes attr = new EncodingAttributes();
        attr.setOutputFormat("mp4");
        attr.setAudioAttributes(audio);
        attr.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        encoder.encode(object, target, attr);
        System.out.println("耗时：" + (System.currentTimeMillis() - time) / 1000);
    }

    public static void test03() {
        long times = System.currentTimeMillis();
        File source = new File("D:\\Desktop\\千锋Java教程：62.在Java应用中实现集群管理.mp4");
        File target = new File("D:\\Desktop\\aaa.png");
        MultimediaObject object = new MultimediaObject(source);
        try {
            VideoInfo videoInfo = object.getInfo().getVideo();
            VideoAttributes video = new VideoAttributes();
            video.setCodec("png");
            video.setSize(videoInfo.getSize());
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("image2");
            attrs.setOffset(0f);//设置偏移位置，即开始转码位置（3秒）
            attrs.setDuration(0.01f);//设置转码持续时间（1秒）
            attrs.setVideoAttributes(video);
            Encoder encoder = new Encoder();
            encoder.encode(object, target, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - times));
    }
}
