package com.jedreck.qrcode.zxingtest01.utils;

import com.jedreck.qrcode.zxingtest01.constants.MediaType;

public class ImageExtensionUtil {
    /**
     * PNG识别符
     */
    private static final byte[] MARK_BUF_PNG = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
    /**
     * JPEG开始符
     */
    private static final byte[] MARK_BUF_JPEG_HEAD = {(byte) 0xff, (byte) 0xd8};
    /**
     * JPEG结束符
     */
    private static final byte[] MARK_BUF_JPEG_FOOT = {(byte) 0xff, (byte) 0xd9};

    public static boolean isPNG(byte[] img) {
        return compare(img, MARK_BUF_PNG);
    }

    public static boolean isJpeg(byte[] img) {
        return compare(img, MARK_BUF_JPEG_HEAD) || compare(img, MARK_BUF_JPEG_FOOT);
    }

    public static MediaType getExtension(byte[] imageBytes) {
        if (compare(imageBytes, MARK_BUF_PNG)) {
            return MediaType.ImagePng;
        } else if (compare(imageBytes, MARK_BUF_JPEG_HEAD) || compare(imageBytes, MARK_BUF_JPEG_FOOT)) {
            return MediaType.ImageJpg;
        } else {
            return null;
        }
    }

    /**
     * 标示一致性比较
     *
     * @param buf     待检测标示
     * @param markBuf 标识符字节数组
     * @return 返回false标示标示不匹配
     */
    private static boolean compare(byte[] buf, byte[] markBuf) {
        if (buf.length < markBuf.length) {
            return false;
        }
        for (int i = 0; i < markBuf.length; i++) {
            byte b = markBuf[i];
            byte a = buf[i];

            if (a != b) {
                return false;
            }
        }
        return true;
    }
}
