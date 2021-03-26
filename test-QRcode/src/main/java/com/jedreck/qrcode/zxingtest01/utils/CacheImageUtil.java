package com.jedreck.qrcode.zxingtest01.utils;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class CacheImageUtil {
    private static final FIFOCache<String, BufferedImage> CACHE_UTIL = CacheUtil.newFIFOCache(100);

    private CacheImageUtil() {
    }

    public static synchronized BufferedImage get(String s) {
        return clone(CACHE_UTIL.get(s));
    }

    public static synchronized void put(String s, BufferedImage img) {
        BufferedImage gmi = clone(img);
        CACHE_UTIL.put(s, gmi);
    }

    private static BufferedImage clone(BufferedImage img) {
        if (img == null) {
            return null;
        }
        ColorModel cm = img.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = img.copyData(img.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
