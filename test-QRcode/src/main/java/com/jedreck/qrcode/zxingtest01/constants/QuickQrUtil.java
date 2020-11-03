package com.jedreck.qrcode.zxingtest01.constants;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 从 https://github.com/liuyueyi/quick-media 抄的，谨慎使用
 * 使用参考 https://liuyueyi.github.io/quick-media/#/
 */
public class QuickQrUtil {

    public static String DEFAULT_QR_TXT =
            "啊波次的呃佛个和一级特了膜呢后面不记得了";

    public static Font DEFAULT_FONT;
    public static String DEFAULT_FONT_NAME = "宋体";
    public static int DEFAULT_FONT_STYLE = Font.BOLD;

    private static Map<Triple<String, Integer, Integer>, Font> fontCache = new ConcurrentHashMap<>();

    static {
        DEFAULT_FONT = new Font(DEFAULT_FONT_NAME, DEFAULT_FONT_STYLE, 5);
        fontCache.put(Triple.of(DEFAULT_FONT_NAME, DEFAULT_FONT_STYLE, 5), DEFAULT_FONT);
    }

    public static Font font(String name, int style, int fontSize) {
        Triple<String, Integer, Integer> triple = Triple.of(name, style, fontSize);
        return fontCache.computeIfAbsent(triple, (k) -> new Font(k.getLeft(), k.getMiddle(), k.getRight()));
    }


    private static Random RANDOM = new Random();

    public static ThreadLocal<AtomicInteger> TXT_INDEX_LOCAL = new ThreadLocal<>();

    private static int getIndex() {
        // 实际上是否加锁，对业务影响并不大
        AtomicInteger integer = TXT_INDEX_LOCAL.get();
        if (integer == null) {
            synchronized (QuickQrUtil.class) {
                integer = TXT_INDEX_LOCAL.get();
                if (integer == null) {
                    integer = new AtomicInteger();
                    TXT_INDEX_LOCAL.set(integer);
                }
            }
        }
        return integer.getAndIncrement();
    }

    public static void clear() {
        TXT_INDEX_LOCAL.remove();
    }

    public static String qrTxt(String txt, boolean random) {
        if (StringUtils.isBlank(txt)) {
            return "";
        }

        int index;
        if (random) {
            index = RANDOM.nextInt(txt.length());
        } else {
            index = getIndex() % txt.length();
        }

        return txt.substring(index, index + 1);
    }
}
