package com.jedreck.qrcode.zxingtest01.wrapper;

import com.google.zxing.EncodeHintType;
import com.jedreck.qrcode.zxingtest01.base.gif.GifDecoder;
import com.jedreck.qrcode.zxingtest01.constants.QuickQrUtil;
import com.jedreck.qrcode.zxingtest01.entity.DotSize;
import com.jedreck.qrcode.zxingtest01.helper.QrCodeRenderHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QrCodeOptions {
    /**
     * 塞入二维码的信息
     */
    private String msg;

    /**
     * 生成二维码的宽
     */
    private Integer w;


    /**
     * 生成二维码的高
     */
    private Integer h;


    /**
     * 二维码信息(即传统二维码中的黑色方块) 绘制选项
     */
    private DrawOptions drawOptions;


    /**
     * 背景图样式选项
     */
    private BgImgOptions bgImgOptions;

    /**
     * logo 样式选项
     */
    private LogoOptions logoOptions;


    /**
     * 三个探测图形的样式选项
     */
    private DetectOptions detectOptions;


    private Map<EncodeHintType, Object> hints;


    /**
     * 生成二维码图片的格式 png, jpg
     */
    private String picType;


    /**
     * true 表示生成的是动图
     *
     * @return
     */
    public boolean gifQrCode() {
        return bgImgOptions != null && bgImgOptions.getGifDecoder() != null;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public DrawOptions getDrawOptions() {
        return drawOptions;
    }

    public void setDrawOptions(DrawOptions drawOptions) {
        this.drawOptions = drawOptions;
    }

    public BgImgOptions getBgImgOptions() {
        return bgImgOptions;
    }

    public void setBgImgOptions(BgImgOptions bgImgOptions) {
        this.bgImgOptions = bgImgOptions;
    }

    public LogoOptions getLogoOptions() {
        return logoOptions;
    }

    public void setLogoOptions(LogoOptions logoOptions) {
        this.logoOptions = logoOptions;
    }

    public DetectOptions getDetectOptions() {
        return detectOptions;
    }

    public void setDetectOptions(DetectOptions detectOptions) {
        this.detectOptions = detectOptions;
    }

    public Map<EncodeHintType, Object> getHints() {
        return hints;
    }

    public void setHints(Map<EncodeHintType, Object> hints) {
        this.hints = hints;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QrCodeOptions options = (QrCodeOptions) o;
        return Objects.equals(msg, options.msg) && Objects.equals(w, options.w) && Objects.equals(h, options.h) &&
                Objects.equals(drawOptions, options.drawOptions) &&
                Objects.equals(bgImgOptions, options.bgImgOptions) &&
                Objects.equals(logoOptions, options.logoOptions) &&
                Objects.equals(detectOptions, options.detectOptions) && Objects.equals(hints, options.hints) &&
                Objects.equals(picType, options.picType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg, w, h, drawOptions, bgImgOptions, logoOptions, detectOptions, hints, picType);
    }

    @Override
    public String toString() {
        return "QrCodeOptions{" + "msg='" + msg + '\'' + ", w=" + w + ", h=" + h + ", drawOptions=" + drawOptions +
                ", bgImgOptions=" + bgImgOptions + ", logoOptions=" + logoOptions + ", detectOptions=" + detectOptions +
                ", hints=" + hints + ", picType='" + picType + '\'' + '}';
    }

    /**
     * logo 的配置信息
     */
    public static class LogoOptions {

        /**
         * logo 图片
         */
        private BufferedImage logo;

        /**
         * logo 样式
         */
        private LogoStyle logoStyle;

        /**
         * logo 占二维码的比例
         */
        private int rate;

        /**
         * true 表示有边框，
         * false 表示无边框
         */
        private boolean border;

        /**
         * 边框颜色
         */
        private Color borderColor;

        /**
         * 外围边框颜色
         */
        private Color outerBorderColor;

        /**
         * 用于设置logo的透明度
         */
        private Float opacity;

        public LogoOptions() {
        }

        public LogoOptions(BufferedImage logo, LogoStyle logoStyle, int rate, boolean border, Color borderColor,
                           Color outerBorderColor, Float opacity) {
            this.logo = logo;
            this.logoStyle = logoStyle;
            this.rate = rate;
            this.border = border;
            this.borderColor = borderColor;
            this.outerBorderColor = outerBorderColor;
            this.opacity = opacity;
        }

        public BufferedImage getLogo() {
            return logo;
        }

        public void setLogo(BufferedImage logo) {
            this.logo = logo;
        }

        public LogoStyle getLogoStyle() {
            return logoStyle;
        }

        public void setLogoStyle(LogoStyle logoStyle) {
            this.logoStyle = logoStyle;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public boolean isBorder() {
            return border;
        }

        public void setBorder(boolean border) {
            this.border = border;
        }

        public Color getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(Color borderColor) {
            this.borderColor = borderColor;
        }

        public Color getOuterBorderColor() {
            return outerBorderColor;
        }

        public void setOuterBorderColor(Color outerBorderColor) {
            this.outerBorderColor = outerBorderColor;
        }

        public Float getOpacity() {
            return opacity;
        }

        public void setOpacity(Float opacity) {
            this.opacity = opacity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            LogoOptions that = (LogoOptions) o;
            return rate == that.rate && border == that.border && Objects.equals(logo, that.logo) &&
                    logoStyle == that.logoStyle && Objects.equals(borderColor, that.borderColor) &&
                    Objects.equals(outerBorderColor, that.outerBorderColor) && Objects.equals(opacity, that.opacity);
        }

        @Override
        public int hashCode() {

            return Objects.hash(logo, logoStyle, rate, border, borderColor, outerBorderColor, opacity);
        }

        @Override
        public String toString() {
            return "LogoOptions{" + "logo=" + logo + ", logoStyle=" + logoStyle + ", rate=" + rate + ", border=" +
                    border + ", borderColor=" + borderColor + ", outerBorderColor=" + outerBorderColor + ", opacity=" +
                    opacity + '}';
        }

        public static LogoOptionsBuilder builder() {
            return new LogoOptionsBuilder();
        }

        public static class LogoOptionsBuilder {
            /**
             * logo 图片
             */
            private BufferedImage logo;

            /**
             * logo 样式
             */
            private LogoStyle logoStyle;

            /**
             * logo 占二维码的比例
             */
            private int rate;

            /**
             * true 表示有边框，
             * false 表示无边框
             */
            private boolean border;

            /**
             * 边框颜色
             */
            private Color borderColor;

            /**
             * 外围边框颜色
             */
            private Color outerBorderColor;

            /**
             * 用于设置logo的透明度
             */
            private Float opacity;

            public LogoOptionsBuilder logo(BufferedImage logo) {
                this.logo = logo;
                return this;
            }

            public LogoOptionsBuilder logoStyle(LogoStyle logoStyle) {
                this.logoStyle = logoStyle;
                return this;
            }

            public LogoOptionsBuilder rate(int rate) {
                this.rate = rate;
                return this;
            }

            public LogoOptionsBuilder border(boolean border) {
                this.border = border;
                return this;
            }

            public LogoOptionsBuilder borderColor(Color borderColor) {
                this.borderColor = borderColor;
                return this;
            }

            public LogoOptionsBuilder outerBorderColor(Color outerBorderColor) {
                this.outerBorderColor = outerBorderColor;
                return this;
            }

            public LogoOptionsBuilder opacity(Float opacity) {
                this.opacity = opacity;
                return this;
            }

            public LogoOptions build() {
                return new LogoOptions(logo, logoStyle, rate, border, borderColor, outerBorderColor, opacity);
            }
        }
    }


    /**
     * 背景图的配置信息
     */
    public static class BgImgOptions {
        /**
         * 背景图
         */
        private BufferedImage bgImg;

        /**
         * 动态背景图
         */
        private GifDecoder gifDecoder;

        /**
         * 背景图宽
         */
        private int bgW;

        /**
         * 背景图高
         */
        private int bgH;

        /**
         * 背景图样式
         */
        private BgImgStyle bgImgStyle;

        /**
         * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.OVERRIDE，
         * 用于设置二维码的透明度
         */
        private float opacity;


        /**
         * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.FILL
         * <p>
         * 用于设置二维码的绘制在背景图上的x坐标
         */
        private int startX;


        /**
         * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.FILL
         * <p>
         * 用于设置二维码的绘制在背景图上的y坐标
         */
        private int startY;

        public BgImgOptions() {
        }

        public BgImgOptions(BufferedImage bgImg, GifDecoder gifDecoder, int bgW, int bgH, BgImgStyle bgImgStyle,
                            float opacity, int startX, int startY) {
            this.bgImg = bgImg;
            this.gifDecoder = gifDecoder;
            this.bgW = bgW;
            this.bgH = bgH;
            this.bgImgStyle = bgImgStyle;
            this.opacity = opacity;
            this.startX = startX;
            this.startY = startY;
        }

        public int getBgW() {
            if (bgImgStyle == BgImgStyle.FILL && bgW == 0) {
                if (bgImg != null) {
                    return bgImg.getWidth();
                } else {
                    return gifDecoder.getFrame(0).getWidth();
                }
            }
            return bgW;
        }

        public int getBgH() {
            if (bgImgStyle == BgImgStyle.FILL && bgH == 0) {
                if (bgImg != null) {
                    return bgImg.getHeight();
                } else {
                    return gifDecoder.getFrame(0).getHeight();
                }
            }
            return bgH;
        }

        public BufferedImage getBgImg() {
            return bgImg;
        }

        public void setBgImg(BufferedImage bgImg) {
            this.bgImg = bgImg;
        }

        public GifDecoder getGifDecoder() {
            return gifDecoder;
        }

        public void setGifDecoder(GifDecoder gifDecoder) {
            this.gifDecoder = gifDecoder;
        }

        public void setBgW(int bgW) {
            this.bgW = bgW;
        }

        public void setBgH(int bgH) {
            this.bgH = bgH;
        }

        public BgImgStyle getBgImgStyle() {
            return bgImgStyle;
        }

        public void setBgImgStyle(BgImgStyle bgImgStyle) {
            this.bgImgStyle = bgImgStyle;
        }

        public float getOpacity() {
            return opacity;
        }

        public void setOpacity(float opacity) {
            this.opacity = opacity;
        }

        public int getStartX() {
            return startX;
        }

        public void setStartX(int startX) {
            this.startX = startX;
        }

        public int getStartY() {
            return startY;
        }

        public void setStartY(int startY) {
            this.startY = startY;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BgImgOptions that = (BgImgOptions) o;
            return bgW == that.bgW && bgH == that.bgH && Float.compare(that.opacity, opacity) == 0 &&
                    startX == that.startX && startY == that.startY && Objects.equals(bgImg, that.bgImg) &&
                    Objects.equals(gifDecoder, that.gifDecoder) && bgImgStyle == that.bgImgStyle;
        }

        @Override
        public int hashCode() {

            return Objects.hash(bgImg, gifDecoder, bgW, bgH, bgImgStyle, opacity, startX, startY);
        }

        @Override
        public String toString() {
            return "BgImgOptions{" + "bgImg=" + bgImg + ", gifDecoder=" + gifDecoder + ", bgW=" + bgW + ", bgH=" + bgH +
                    ", bgImgStyle=" + bgImgStyle + ", opacity=" + opacity + ", startX=" + startX + ", startY=" +
                    startY + '}';
        }

        public static BgImgOptionsBuilder builder() {
            return new BgImgOptionsBuilder();
        }

        public static class BgImgOptionsBuilder {
            /**
             * 背景图
             */
            private BufferedImage bgImg;

            /**
             * 动态背景图
             */
            private GifDecoder gifDecoder;

            /**
             * 背景图宽
             */
            private int bgW;

            /**
             * 背景图高
             */
            private int bgH;

            /**
             * 背景图样式
             */
            private BgImgStyle bgImgStyle;

            /**
             * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.OVERRIDE，
             * 用于设置二维码的透明度
             */
            private float opacity;


            /**
             * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.FILL
             * <p>
             * 用于设置二维码的绘制在背景图上的x坐标
             */
            private int startX;


            /**
             * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.FILL
             * <p>
             * 用于设置二维码的绘制在背景图上的y坐标
             */
            private int startY;

            public BgImgOptionsBuilder bgImg(BufferedImage bgImg) {
                this.bgImg = bgImg;
                return this;
            }

            public BgImgOptionsBuilder gifDecoder(GifDecoder gifDecoder) {
                this.gifDecoder = gifDecoder;
                return this;
            }

            public BgImgOptionsBuilder bgW(int bgW) {
                this.bgW = bgW;
                return this;
            }

            public BgImgOptionsBuilder bgH(int bgH) {
                this.bgH = bgH;
                return this;
            }

            public BgImgOptionsBuilder bgImgStyle(BgImgStyle bgImgStyle) {
                this.bgImgStyle = bgImgStyle;
                return this;
            }

            public BgImgOptionsBuilder opacity(float opacity) {
                this.opacity = opacity;
                return this;
            }

            public BgImgOptionsBuilder startX(int startX) {
                this.startX = startX;
                return this;
            }

            public BgImgOptionsBuilder startY(int startY) {
                this.startY = startY;
                return this;
            }

            public BgImgOptions build() {
                return new BgImgOptions(bgImg, gifDecoder, bgW, bgH, bgImgStyle, opacity, startX, startY);
            }
        }
    }


    /**
     * 探测图形的配置信息
     */
    public static class DetectOptions {
        private Color outColor;

        private Color inColor;

        /**
         * 默认探测图形，优先级高于颜色的定制（即存在图片时，用图片绘制探测图形）
         */
        private BufferedImage detectImg;

        /**
         * 左上角的探测图形
         */
        private BufferedImage detectImgLT;

        /**
         * 右上角的探测图形
         */
        private BufferedImage detectImgRT;

        /**
         * 左下角的探测图形
         */
        private BufferedImage detectImgLD;

        /**
         * true 表示探测图形单独处理
         * false 表示探测图形的样式更随二维码的主样式
         */
        private Boolean special;

        public Boolean getSpecial() {
            return BooleanUtils.isTrue(special);
        }

        public DetectOptions() {
        }

        public DetectOptions(Color outColor, Color inColor, BufferedImage detectImg, BufferedImage detectImgLT,
                             BufferedImage detectImgRT, BufferedImage detectImgLD, Boolean special) {
            this.outColor = outColor;
            this.inColor = inColor;
            this.detectImg = detectImg;
            this.detectImgLT = detectImgLT;
            this.detectImgRT = detectImgRT;
            this.detectImgLD = detectImgLD;
            this.special = special;
        }

        public Color getOutColor() {
            return outColor;
        }

        public void setOutColor(Color outColor) {
            this.outColor = outColor;
        }

        public Color getInColor() {
            return inColor;
        }

        public void setInColor(Color inColor) {
            this.inColor = inColor;
        }

        public BufferedImage getDetectImg() {
            return detectImg;
        }

        public void setDetectImg(BufferedImage detectImg) {
            this.detectImg = detectImg;
        }

        public BufferedImage getDetectImgLT() {
            return detectImgLT;
        }

        public void setDetectImgLT(BufferedImage detectImgLT) {
            this.detectImgLT = detectImgLT;
        }

        public BufferedImage getDetectImgRT() {
            return detectImgRT;
        }

        public void setDetectImgRT(BufferedImage detectImgRT) {
            this.detectImgRT = detectImgRT;
        }

        public BufferedImage getDetectImgLD() {
            return detectImgLD;
        }

        public void setDetectImgLD(BufferedImage detectImgLD) {
            this.detectImgLD = detectImgLD;
        }

        public void setSpecial(Boolean special) {
            this.special = special;
        }

        public BufferedImage chooseDetectedImg(QrCodeRenderHelper.DetectLocation detectLocation) {
            switch (detectLocation) {
                case LD:
                    return detectImgLD == null ? detectImg : detectImgLD;
                case LT:
                    return detectImgLT == null ? detectImg : detectImgLT;
                case RT:
                    return detectImgRT == null ? detectImg : detectImgRT;
                default:
                    return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            DetectOptions that = (DetectOptions) o;
            return Objects.equals(outColor, that.outColor) && Objects.equals(inColor, that.inColor) &&
                    Objects.equals(detectImg, that.detectImg) && Objects.equals(detectImgLT, that.detectImgLT) &&
                    Objects.equals(detectImgRT, that.detectImgRT) && Objects.equals(detectImgLD, that.detectImgLD) &&
                    Objects.equals(special, that.special);
        }

        @Override
        public int hashCode() {

            return Objects.hash(outColor, inColor, detectImg, detectImgLT, detectImgRT, detectImgLD, special);
        }

        @Override
        public String toString() {
            return "DetectOptions{" + "outColor=" + outColor + ", inColor=" + inColor + ", detectImg=" + detectImg +
                    ", detectImgLT=" + detectImgLT + ", detectImgRT=" + detectImgRT + ", detectImgLD=" + detectImgLD +
                    ", special=" + special + '}';
        }

        public static DetectOptionsBuilder builder() {
            return new DetectOptionsBuilder();
        }

        public static class DetectOptionsBuilder {
            private Color outColor;

            private Color inColor;

            private BufferedImage detectImg;

            private BufferedImage detectImgLT;

            private BufferedImage detectImgRT;

            private BufferedImage detectImgLD;

            private Boolean special;

            public DetectOptionsBuilder outColor(Color outColor) {
                this.outColor = outColor;
                return this;
            }

            public DetectOptionsBuilder inColor(Color inColor) {
                this.inColor = inColor;
                return this;
            }

            public DetectOptionsBuilder detectImg(BufferedImage detectImg) {
                this.detectImg = detectImg;
                return this;
            }

            public DetectOptionsBuilder detectImgLT(BufferedImage detectImgLT) {
                this.detectImgLT = detectImgLT;
                return this;
            }

            public DetectOptionsBuilder detectImgRT(BufferedImage detectImgRT) {
                this.detectImgRT = detectImgRT;
                return this;
            }

            public DetectOptionsBuilder detectImgLD(BufferedImage detectImgLD) {
                this.detectImgLD = detectImgLD;
                return this;
            }

            public DetectOptionsBuilder special(Boolean special) {
                this.special = special;
                return this;
            }

            public DetectOptions build() {
                return new DetectOptions(outColor, inColor, detectImg, detectImgLT, detectImgRT, detectImgLD, special);
            }
        }
    }


    /**
     * 绘制二维码的配置信息
     */
    public static class DrawOptions {
        /**
         * 着色颜色
         */
        private Color preColor;

        /**
         * 背景颜色
         */
        private Color bgColor;

        /**
         * 背景图
         */
        private BufferedImage bgImg;

        /**
         * 绘制样式
         */
        private DrawStyle drawStyle;

        /**
         * 生成文字二维码时的候字符池
         */
        private String text;

        /**
         * 生成文字二维码时的字体
         */
        private String fontName;

        /**
         * 文字二维码渲染模式
         */
        private TxtMode txtMode;

        /**
         * 字体样式
         * <p>
         * {@link Font#PLAIN} 0
         * {@link Font#BOLD}  1
         * {@link Font#ITALIC} 2
         */
        private int fontStyle;

        /**
         * true 时表示支持对相邻的着色点进行合并处理 （即用一个大图来绘制相邻的两个着色点）
         * <p>
         * 说明： 三角形样式关闭该选项，因为留白过多，对识别有影响
         */
        private boolean enableScale;

        /**
         * 图片透明处填充，true则表示透明处用bgColor填充； false则透明处依旧透明
         */
        private boolean diaphaneityFill;

        /**
         * 渲染图
         */
        private Map<DotSize, BufferedImage> imgMapper;

        public BufferedImage getImage(int row, int col) {
            return getImage(DotSize.create(row, col));
        }

        public BufferedImage getImage(DotSize dotSize) {
            return imgMapper.get(dotSize);
        }

        /**
         * 获取二维码绘制的文字
         *
         * @return
         */
        public String getDrawQrTxt() {
            return QuickQrUtil.qrTxt(text, txtMode != null && txtMode == TxtMode.RANDOM);
        }

        public Color getPreColor() {
            return preColor;
        }

        public void setPreColor(Color preColor) {
            this.preColor = preColor;
        }

        public Color getBgColor() {
            return bgColor;
        }

        public void setBgColor(Color bgColor) {
            this.bgColor = bgColor;
        }

        public BufferedImage getBgImg() {
            return bgImg;
        }

        public void setBgImg(BufferedImage bgImg) {
            this.bgImg = bgImg;
        }

        public DrawStyle getDrawStyle() {
            return drawStyle;
        }

        public void setDrawStyle(DrawStyle drawStyle) {
            this.drawStyle = drawStyle;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getFontName() {
            return fontName;
        }

        public void setFontName(String fontName) {
            this.fontName = fontName;
        }

        public TxtMode getTxtMode() {
            return txtMode;
        }

        public void setTxtMode(TxtMode txtMode) {
            this.txtMode = txtMode;
        }

        public int getFontStyle() {
            return fontStyle;
        }

        public void setFontStyle(int fontStyle) {
            this.fontStyle = fontStyle;
        }

        public boolean isEnableScale() {
            return enableScale;
        }

        public void setEnableScale(boolean enableScale) {
            this.enableScale = enableScale;
        }

        public boolean isDiaphaneityFill() {
            return diaphaneityFill;
        }

        public void setDiaphaneityFill(boolean diaphaneityFill) {
            this.diaphaneityFill = diaphaneityFill;
        }

        public Map<DotSize, BufferedImage> getImgMapper() {
            return imgMapper;
        }

        public void setImgMapper(Map<DotSize, BufferedImage> imgMapper) {
            this.imgMapper = imgMapper;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            DrawOptions that = (DrawOptions) o;
            return fontStyle == that.fontStyle && enableScale == that.enableScale &&
                    diaphaneityFill == that.diaphaneityFill && Objects.equals(preColor, that.preColor) &&
                    Objects.equals(bgColor, that.bgColor) && Objects.equals(bgImg, that.bgImg) &&
                    drawStyle == that.drawStyle && Objects.equals(text, that.text) &&
                    Objects.equals(fontName, that.fontName) && txtMode == that.txtMode &&
                    Objects.equals(imgMapper, that.imgMapper);
        }

        @Override
        public int hashCode() {

            return Objects.hash(preColor, bgColor, bgImg, drawStyle, text, fontName, txtMode, fontStyle, enableScale,
                    diaphaneityFill, imgMapper);
        }

        @Override
        public String toString() {
            return "DrawOptions{" + "preColor=" + preColor + ", bgColor=" + bgColor + ", bgImg=" + bgImg +
                    ", drawStyle=" + drawStyle + ", text='" + text + '\'' + ", fontName='" + fontName + '\'' +
                    ", txtMode=" + txtMode + ", fontStyle=" + fontStyle + ", enableScale=" + enableScale +
                    ", diaphaneityFill=" + diaphaneityFill + ", imgMapper=" + imgMapper + '}';
        }

        public static DrawOptionsBuilder builder() {
            return new DrawOptionsBuilder();
        }

        public static class DrawOptionsBuilder {
            /**
             * 二维码居中 1对应的着色颜色
             */
            private Color preColor;

            /**
             * 二维码矩阵中 0对应的背景颜色
             */
            private Color bgColor;

            /**
             * 透明度填充，如绘制二维码的图片中存在透明区域，若这个参数为true，则会用bgColor填充透明的区域；若为false，则透明区域依旧是透明的
             */
            private boolean diaphaneityFill;

            /**
             * 文字二维码中，用于渲染的文字库，支持按字符顺序or随机两种展现方式（说明：英文不友好）
             */
            private String text;

            /**
             * 文字二维码，渲染模式
             */
            private TxtMode txtMode;

            /**
             * 文字二维码，字体名
             */
            private String fontName;

            /**
             * 字体样式
             * <p>
             * {@link Font#PLAIN} 0
             * {@link Font#BOLD}  1
             * {@link Font#ITALIC} 2
             */
            private Integer fontStyle;

            /**
             * 二维码矩阵中，0点对应绘制的背景图片， 1点对应绘制的图片在 imgMapper 中
             */
            private BufferedImage bgImg;

            /**
             * 二维码绘制样式
             */
            private DrawStyle drawStyle;


            /**
             * true 时表示支持对相邻的着色点进行合并处理 （即用一个大图来绘制相邻的两个着色点）
             * <p>
             * 说明： 三角形样式关闭该选项，因为留白过多，对识别有影响
             */
            private boolean enableScale;

            /**
             * 渲染图
             */
            private Map<DotSize, BufferedImage> imgMapper;

            public DrawOptionsBuilder() {
                imgMapper = new HashMap<>();
            }

            public DrawOptionsBuilder preColor(Color preColor) {
                this.preColor = preColor;
                return this;
            }

            public DrawOptionsBuilder bgColor(Color bgColor) {
                this.bgColor = bgColor;
                return this;
            }

            public DrawOptionsBuilder diaphaneityFill(boolean fill) {
                this.diaphaneityFill = fill;
                return this;
            }

            public DrawOptionsBuilder bgImg(BufferedImage image) {
                this.bgImg = image;
                return this;
            }

            public DrawOptionsBuilder drawStyle(DrawStyle drawStyle) {
                this.drawStyle = drawStyle;
                return this;
            }

            public DrawOptionsBuilder text(String text) {
                this.text = text;
                return this;
            }

            public DrawOptionsBuilder txtMode(TxtMode txtMode) {
                this.txtMode = txtMode;
                return this;
            }

            public DrawOptionsBuilder fontName(String fontName) {
                this.fontName = fontName;
                return this;
            }

            public DrawOptionsBuilder fontStyle(int fontStyle) {
                this.fontStyle = fontStyle;
                return this;
            }

            public DrawOptionsBuilder enableScale(boolean enableScale) {
                this.enableScale = enableScale;
                return this;
            }

            public DrawOptionsBuilder drawImg(int row, int column, BufferedImage image) {
                imgMapper.put(new DotSize(row, column), image);
                return this;
            }

            public DrawOptions build() {
                DrawOptions drawOptions = new DrawOptions();
                drawOptions.setBgColor(this.bgColor);
                drawOptions.setBgImg(this.bgImg);
                drawOptions.setPreColor(this.preColor);
                drawOptions.setDrawStyle(this.drawStyle);
                drawOptions.setEnableScale(this.enableScale);
                drawOptions.setImgMapper(this.imgMapper);
                drawOptions.setDiaphaneityFill(this.diaphaneityFill);
                drawOptions.setText(text == null ? QuickQrUtil.DEFAULT_QR_TXT : text);
                drawOptions.setTxtMode(txtMode == null ? TxtMode.ORDER : txtMode);
                drawOptions.setFontName(fontName == null ? QuickQrUtil.DEFAULT_FONT_NAME : fontName);
                drawOptions.setFontStyle(fontStyle == null ? QuickQrUtil.DEFAULT_FONT_STYLE : fontStyle);
                return drawOptions;
            }
        }
    }


    /**
     * logo的样式
     */
    public enum LogoStyle {
        ROUND, NORMAL, CIRCLE;

        public static LogoStyle getStyle(String name) {
            return LogoStyle.valueOf(name.toUpperCase());
        }
    }


    /**
     * 背景图样式
     */
    public enum BgImgStyle {
        /**
         * 设置二维码透明度，然后全覆盖背景图
         */
        OVERRIDE,

        /**
         * 将二维码填充在背景图的指定位置
         */
        FILL,


        /**
         * 背景图穿透显示, 即二维码主题色为透明，由背景图的颜色进行填充
         */
        PENETRATE,
        ;

        public static BgImgStyle getStyle(String name) {
            return "fill".equalsIgnoreCase(name) ? FILL : OVERRIDE;
        }
    }


    /**
     * 绘制二维码信息的样式
     */
    public enum DrawStyle {
        RECT { // 矩形

            @Override
            public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt) {
                g2d.fillRect(x, y, w, h);
            }

            @Override
            public boolean expand(DotSize dotSize) {
                return dotSize.getRow() == dotSize.getCol();
            }
        }, CIRCLE { // 圆点

            @Override
            public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt) {
                g2d.fill(new Ellipse2D.Float(x, y, w, h));
            }

            @Override
            public boolean expand(DotSize dotSize) {
                return dotSize.getRow() == dotSize.getCol();
            }
        }, TRIANGLE { // 三角形

            @Override
            public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt) {
                int px[] = {x, x + (w >> 1), x + w};
                int py[] = {y + w, y, y + w};
                g2d.fillPolygon(px, py, 3);
            }

            @Override
            public boolean expand(DotSize expandType) {
                return false;
            }
        }, DIAMOND { // 五边形-钻石

            @Override
            public void draw(Graphics2D g2d, int x, int y, int size, int h, BufferedImage img, String txt) {
                int cell4 = size >> 2;
                int cell2 = size >> 1;
                int px[] = {x + cell4, x + size - cell4, x + size, x + cell2, x};
                int py[] = {y, y, y + cell2, y + size, y + cell2};
                g2d.fillPolygon(px, py, 5);
            }

            @Override
            public boolean expand(DotSize dotSize) {
                return dotSize.getRow() == dotSize.getCol();
            }
        }, SEXANGLE { // 六边形

            @Override
            public void draw(Graphics2D g2d, int x, int y, int size, int h, BufferedImage img, String txt) {
                int add = size >> 2;
                int px[] = {x + add, x + size - add, x + size, x + size - add, x + add, x};
                int py[] = {y, y, y + add + add, y + size, y + size, y + add + add};
                g2d.fillPolygon(px, py, 6);
            }

            @Override
            public boolean expand(DotSize dotSize) {
                return dotSize.getRow() == dotSize.getCol();
            }
        }, OCTAGON { // 八边形

            @Override
            public void draw(Graphics2D g2d, int x, int y, int size, int h, BufferedImage img, String txt) {
                int add = size / 3;
                int px[] = {x + add, x + size - add, x + size, x + size, x + size - add, x + add, x, x};
                int py[] = {y, y, y + add, y + size - add, y + size, y + size, y + size - add, y + add};
                g2d.fillPolygon(px, py, 8);
            }

            @Override
            public boolean expand(DotSize dotSize) {
                return dotSize.getRow() == dotSize.getCol();
            }
        }, IMAGE { // 自定义图片

            @Override
            public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt) {
                g2d.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            @Override
            public boolean expand(DotSize expandType) {
                return true;
            }
        },

        TXT { // 文字绘制

            @Override
            public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt) {
                Font oldFont = g2d.getFont();
                if (oldFont.getSize() != w) {
                    Font newFont = QuickQrUtil.font(oldFont.getName(), oldFont.getStyle(), w);
                    g2d.setFont(newFont);
                }
                g2d.drawString(txt, x, y + w);
                g2d.setFont(oldFont);
            }

            @Override
            public boolean expand(DotSize dotSize) {
                return dotSize.getRow() == dotSize.getCol();
            }
        };

        private static Map<String, DrawStyle> map;

        static {
            map = new HashMap<>(10);
            for (DrawStyle style : DrawStyle.values()) {
                map.put(style.name(), style);
            }
        }

        public static DrawStyle getDrawStyle(String name) {
            if (StringUtils.isBlank(name)) { // 默认返回矩形
                return RECT;
            }


            DrawStyle style = map.get(name.toUpperCase());
            return style == null ? RECT : style;
        }


        public abstract void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt);


        /**
         * 返回是否支持绘制自定义图形的扩展
         *
         * @param dotSize
         * @return
         */
        public abstract boolean expand(DotSize dotSize);
    }


    public enum TxtMode {
        /***
         * 文字二维码，随机模式
         */
        RANDOM,
        /**
         * 文字二维码，顺序模式
         */
        ORDER;
    }
}
