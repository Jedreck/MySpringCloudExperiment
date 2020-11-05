package com.jedreck.qrcode.zxingtest01.wrapper;

import com.jedreck.qrcode.zxingtest01.utils.ColorUtil;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class StringPicture {
    private StringPicture() {
    }

    /**
     * 文字转图片
     */
    public static BufferedImage createImage(String text, Font font, Color color, Color bgColor) {
        font = font == null ? new Font("宋体", Font.BOLD, 300) : font;
        color = color == null ? Color.BLACK : color;
        bgColor = bgColor == null ? ColorUtil.OPACITY : bgColor;

        // 获取font的样式应用在输出内容上整个的宽高
        int[] arr = getWidthAndHeight(text, font);
        int width = arr[0];
        int height = arr[1];
        // 创建图片 创建图片画布
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = image.createGraphics();
        //透明背景  the begin
        image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g = image.createGraphics();
        //透明背景  the end
        /*
         如果你需要白色背景或者其他颜色背景可以直接这么设置，其实就是满屏输出的颜色
         我这里上面设置了透明颜色，这里就不用了
        */
        g.setColor(bgColor);
        //画出矩形区域，以便于在矩形区域内写入文字
        g.fillRect(0, 0, width, height);
        /*
         * 然后输出文字，达到透明背景效果，最后选择了，createCompatibleImage Transparency.TRANSLUCENT来创建。
         */
        g.setColor(color);
        // 设置画笔字体
        g.setFont(font);
        // 画出一行字符串
        g.drawString(text, 0, font.getSize());
        // 画出第二行字符串，注意y轴坐标需要变动
        g.drawString(text, 0, 2 * font.getSize());
        //执行处理
        g.dispose();

        return image;
    }

    /**
     * 根据str,font的样式计算宽高
     */
    private static int[] getWidthAndHeight(String text, Font font) {
        Rectangle2D r = font.getStringBounds(text, new FontRenderContext(
                AffineTransform.getScaleInstance(1, 1), false, false));
        int unitHeight = (int) Math.floor(r.getHeight());//
        // 获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
        int width = (int) Math.round(r.getWidth()) + 1;
        // 把单个字符的高度+3保证高度绝对能容纳字符串作为图片的高度
        int height = unitHeight + 3;
        return new int[]{width, height};
    }
}
