package com.jedreck.qrcode.zxingtest01.wrapper;

import com.jedreck.qrcode.zxingtest01.utils.ColorUtil;
import com.jedreck.qrcode.zxingtest01.utils.GraphicUtil;
import org.apache.commons.lang3.ObjectUtils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class StringPicture {
    private StringPicture() {
    }

    /**
     * 文字转图片
     */
    public static BufferedImage createImage(String[] texts, Font font, Color color, Color bgColor) {
        if (ObjectUtils.isEmpty(texts)) {
            return null;
        }
        font = font == null ? new Font("宋体", Font.BOLD, 300) : font;
        color = color == null ? Color.BLACK : color;
        bgColor = bgColor == null ? ColorUtil.OPACITY : bgColor;

        // 获取font的样式应用在输出内容上整个的宽高
        int[] arr = getMaxWidthAndHeight(texts, font);
        int width = arr[0];
        int height = arr[1];
        // 创建图片 创建图片画布
        int imgH = height * texts.length;
        BufferedImage image = new BufferedImage(width, imgH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = GraphicUtil.getG2d(image);
        g.setComposite(AlphaComposite.Src);
        g.setColor(bgColor);
        //画出矩形区域，以便于在矩形区域内写入文字
        g.fillRect(0, 0, width, imgH);
        /*
         * 然后输出文字，达到透明背景效果，最后选择了，createCompatibleImage Transparency.TRANSLUCENT来创建。
         */
        g.setColor(color);
        // 设置画笔字体
        g.setFont(font);

        for (int i = 0; i < texts.length; ) {
            // 画出i行字符串
            g.drawString(texts[i], 0, ++i * font.getSize());
        }
        //执行处理
        g.dispose();

        return image;
    }

    /**
     * 文字转图片
     *
     * @param color        字颜色
     * @param outlineColor 描边颜色
     */
    public static BufferedImage createImageOutline(String[] texts, Font font, Color color, Color outlineColor, Integer outlineWidth) {
        if (ObjectUtils.isEmpty(texts)) {
            return null;
        }
        font = font == null ? new Font("宋体", Font.BOLD, 100) : font;
        color = color == null ? Color.BLACK : color;
        outlineWidth = outlineWidth == null ? ((font.getSize() / 10) + 1) : outlineWidth;

        // 获取font的样式应用在输出内容上整个的宽高
        int[] arr = getMaxWidthAndHeight(texts, font);
        int width = arr[0];
        int height = arr[1];
        // 创建图片 创建图片画布
        int imgH = height * texts.length;
        BufferedImage image = new BufferedImage(width, imgH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = GraphicUtil.getG2d(image);

        g.setComposite(AlphaComposite.Src);
        g.setFont(font);
        FontRenderContext frc = g.getFontRenderContext();
        for (int i = 0; i < texts.length; ) {
            // 画出i行字符串
            TextLayout tl = new TextLayout(texts[i], font, frc);
            Shape shape = tl.getOutline(AffineTransform.getTranslateInstance(3, ++i * font.getSize()));

            if (outlineColor != null) {
                // 描边色
                g.setColor(outlineColor);
                g.setStroke(new BasicStroke(outlineWidth));
                g.draw(shape);
            }

            //字体色
            g.setColor(color);
            g.fill(shape);
        }
        //执行处理
        g.dispose();

        return image;
    }

    /**
     * 根据str,font的样式计算最大宽高
     */
    private static int[] getMaxWidthAndHeight(String[] texts, Font font) {
        // 获取单行最大高度宽度
        int maxHeight = 0;
        int maxWidth = 0;
        for (String t : texts) {
            Rectangle2D r = font.getStringBounds(t, new FontRenderContext(
                    AffineTransform.getScaleInstance(1, 1), false, false));
            int rHeight = (int) Math.floor(r.getHeight());
            // 获取整个str用了font样式的宽度这里用四舍五入后+3保证宽度绝对能容纳这个字符串作为图片的宽度
            int rWidth = (int) Math.round(r.getWidth()) + 3;
            if (rHeight > maxHeight) {
                maxHeight = rHeight;
            }
            if (rWidth > maxWidth) {
                maxWidth = rWidth;
            }
        }
        // 把单个字符的高度+4保证高度绝对能容纳字符串作为图片的高度
        return new int[]{maxWidth + 5, maxHeight + 3};
    }

}
