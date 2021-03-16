package com.jedreck.qrcode.zxingtest01.wrapper;

import com.google.zxing.EncodeHintType;
import com.jedreck.qrcode.zxingtest01.constants.QuickQrFont;
import com.jedreck.qrcode.zxingtest01.entity.DotSize;
import com.jedreck.qrcode.zxingtest01.helper.QrCodeRenderHelper;
import com.jedreck.qrcode.zxingtest01.utils.gif.GifDecoder;
import org.apache.commons.lang3.BooleanUtils;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 文字信息选项
     */
    private NoteOptions noteOptions;

    private Map<EncodeHintType, Object> hints;

    /**
     * 生成二维码图片的格式 png, jpg
     */
    private String picType;

    /**
     * true 表示生成的是动图
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

    public NoteOptions getNoteOptions() {
        return noteOptions;
    }

    public void setNoteOptions(NoteOptions noteOptions) {
        this.noteOptions = noteOptions;
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
                Objects.equals(picType, options.picType) && Objects.equals(noteOptions, options.noteOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg, w, h, drawOptions, bgImgOptions, logoOptions, detectOptions, hints, picType, noteOptions);
    }

    @Override
    public String toString() {
        return "QrCodeOptions{" + "msg='" + msg + '\'' + ", w=" + w + ", h=" + h + ", drawOptions=" + drawOptions +
                ", bgImgOptions=" + bgImgOptions + ", logoOptions=" + logoOptions + ", detectOptions=" + detectOptions +
                ", hints=" + hints + ", picType='" + picType + '\'' + ", noteOptions='" + noteOptions + '\'' + '}';
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

        /**
         * logo位置，中间或右下
         */
        private LogoPosition logoPosition;

        public LogoOptions() {
        }

        public LogoOptions(BufferedImage logo, LogoStyle logoStyle, int rate, boolean border, Color borderColor,
                           Color outerBorderColor, Float opacity, LogoPosition logoPosition) {
            this.logo = logo;
            this.logoStyle = logoStyle;
            this.rate = rate;
            this.border = border;
            this.borderColor = borderColor;
            this.outerBorderColor = outerBorderColor;
            this.opacity = opacity;
            this.logoPosition = logoPosition;
        }

        public static LogoOptionsBuilder builder() {
            return new LogoOptionsBuilder();
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

        public LogoPosition getLogoPosition() {
            return logoPosition == null ? LogoPosition.MIDDLE : logoPosition;
        }

        public void setLogoPosition(LogoPosition logoPosition) {
            this.logoPosition = logoPosition;
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
                    Objects.equals(outerBorderColor, that.outerBorderColor) && Objects.equals(opacity, that.opacity)
                    && logoPosition == that.logoPosition;
        }

        @Override
        public int hashCode() {

            return Objects.hash(logo, logoStyle, rate, border, borderColor, outerBorderColor, opacity, logoPosition);
        }

        @Override
        public String toString() {
            return "LogoOptions{" + "logo=" + logo + ", logoStyle=" + logoStyle + ", rate=" + rate + ", border=" +
                    border + ", borderColor=" + borderColor + ", outerBorderColor=" + outerBorderColor + ", opacity=" +
                    opacity + ", logoPosition= " + logoPosition + "}";
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

            /**
             * logo位置，中间或右下
             */
            private LogoPosition logoPosition;

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

            public LogoOptionsBuilder logoPosition(LogoPosition logoPosition) {
                this.logoPosition = logoPosition;
                return this;
            }

            public LogoOptions build() {
                return new LogoOptions(logo, logoStyle, rate, border, borderColor, outerBorderColor, opacity, logoPosition);
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
         * 外框图
         * <p>
         * 这个参数是解决 既有 外框作为背景图{@link QrCodeOptions.BgImgStyle}FILL 又有 码点透明覆盖作为背景图{@link QrCodeOptions.BgImgStyle}PENETRATE 的场景用
         * <p>
         * 先绘制 透明码点作为背景 再调用此参数 覆盖 bgImg和BgImgStyle 再绘制一遍东西
         */
        private BufferedImage bgOutImg;
        private Integer bgOutImgW;
        private Integer bgOutImgH;

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
         * if {@link #bgImgStyle} ==  {@link BgImgStyle#OVERRIDE}
         * 用于设置二维码的透明度
         */
        private float opacity;

        /**
         * if {@link #bgImgStyle} ==  {@link BgImgStyle#FILL}
         * 用于设置二维码的绘制在背景图上的x坐标
         */
        private int startX;

        /**
         * if {@link #bgImgStyle} ==  {@link BgImgStyle#FILL}
         * 用于设置二维码的绘制在背景图上的y坐标
         */
        private int startY;

        public BgImgOptions() {
        }

        public BgImgOptions(BufferedImage bgImg, BufferedImage bgOutImg,
                            Integer bgOutImgW, Integer bgOutImgH, GifDecoder gifDecoder,
                            int bgW, int bgH, BgImgStyle bgImgStyle, float opacity,
                            int startX, int startY) {
            this.bgImg = bgImg;
            this.bgOutImg = bgOutImg;
            this.bgOutImgW = bgOutImgW;
            this.bgOutImgH = bgOutImgH;
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

        public BufferedImage getBgOutImg() {
            return bgOutImg;
        }

        public void setBgOutImg(BufferedImage bgOutImg) {
            this.bgOutImg = bgOutImg;
        }

        public Integer getBgOutImgW() {
            return bgOutImgW;
        }

        public void setBgOutImgW(Integer bgOutImgW) {
            this.bgOutImgW = bgOutImgW;
        }

        public Integer getBgOutImgH() {
            return bgOutImgH;
        }

        public void setBgOutImgH(Integer bgOutImgH) {
            this.bgOutImgH = bgOutImgH;
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
                    Objects.equals(gifDecoder, that.gifDecoder) && bgImgStyle == that.bgImgStyle
                    && Objects.equals(bgOutImg, that.bgOutImg) && Objects.equals(bgOutImgH, that.bgOutImgH)
                    && Objects.equals(bgOutImgW, that.bgOutImgW);
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
             * if {@link #bgImgStyle} ==  {@link BgImgStyle#OVERRIDE}
             * 用于设置二维码的透明度
             */
            private float opacity;


            /**
             * if {@link #bgImgStyle} ==  {@link BgImgStyle#FILL}
             * 用于设置二维码的绘制在背景图上的x坐标
             */
            private int startX;


            /**
             * if {@link #bgImgStyle} ==  {@link BgImgStyle#FILL}
             * 用于设置二维码的绘制在背景图上的y坐标
             */
            private int startY;

            private BufferedImage bgOutImg;
            private Integer bgOutImgW;
            private Integer bgOutImgH;

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

            public BgImgOptionsBuilder bgOutImg(BufferedImage bgOutImg) {
                this.bgOutImg = bgOutImg;
                return this;
            }

            public BgImgOptionsBuilder bgOutImgW(Integer bgOutImgW) {
                this.bgOutImgW = bgOutImgW;
                return this;
            }

            public BgImgOptionsBuilder bgOutImgH(Integer bgOutImgH) {
                this.bgOutImgH = bgOutImgH;
                return this;
            }

            public BgImgOptions build() {
                return new BgImgOptions(bgImg, bgOutImg, bgOutImgW, bgOutImgH, gifDecoder,
                        bgW, bgH, bgImgStyle, opacity, startX, startY);
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
            return QuickQrFont.qrTxt(text, txtMode != null && txtMode == TxtMode.RANDOM);
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
             * 二维码矩阵中 1对应的着色颜色
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
                drawOptions.setText(text == null ? QuickQrFont.DEFAULT_QR_TXT : text);
                drawOptions.setTxtMode(txtMode == null ? TxtMode.ORDER : txtMode);
                drawOptions.setFontName(fontName == null ? QuickQrFont.DEFAULT_FONT_NAME : fontName);
                drawOptions.setFontStyle(fontStyle == null ? QuickQrFont.DEFAULT_FONT_STYLE : fontStyle);
                return drawOptions;
            }
        }
    }

    /**
     * 文字信息
     */
    public static class NoteOptions {
        /**
         * 文字
         */
        private List<String> notes;

        /**
         * 位置
         */
        private NotePosition notePosition;

        /**
         * 字体
         */
        private Font font;

        /**
         * 字体颜色
         */
        private Color fontColor;

        /**
         * 描边颜色
         */
        private Color outlineColor;

        public NoteOptions() {
        }

        public NoteOptions(List<String> notes, NotePosition notePosition, Font font, Color fontColor, Color outlineColor) {
            this.notes = notes;
            this.notePosition = notePosition;
            this.font = font;
            this.fontColor = fontColor;
            this.outlineColor = outlineColor;
        }

        public List<String> getNotes() {
            return notes;
        }

        public void setNotes(List<String> notes) {
            this.notes = notes;
        }

        public NotePosition getNotePosition() {
            return notePosition;
        }

        public void setNotePosition(NotePosition notePosition) {
            this.notePosition = notePosition;
        }

        public Font getFont() {
            return font;
        }

        public void setFont(Font font) {
            this.font = font;
        }

        public Color getFontColor() {
            return fontColor;
        }

        public void setFontColor(Color fontColor) {
            this.fontColor = fontColor;
        }

        public Color getOutlineColor() {
            return outlineColor;
        }

        public void setOutlineColor(Color outlineColor) {
            this.outlineColor = outlineColor;
        }

        public static final class NoteOptionsBuilder {
            private List<String> notes;
            private QrCodeOptions.NotePosition notePosition;
            private Font font;
            private Color fontColor;
            private Color outlineColor;

            private NoteOptionsBuilder() {
            }

            public static NoteOptionsBuilder builder() {
                return new NoteOptionsBuilder();
            }

            public NoteOptionsBuilder notes(List<String> notes) {
                this.notes = notes;
                return this;
            }

            public NoteOptionsBuilder notePosition(QrCodeOptions.NotePosition notePosition) {
                this.notePosition = notePosition;
                return this;
            }

            public NoteOptionsBuilder font(Font font) {
                this.font = font;
                return this;
            }

            public NoteOptionsBuilder fontColor(Color fontColor) {
                this.fontColor = fontColor;
                return this;
            }

            public NoteOptionsBuilder outlineColor(Color outlineColor) {
                this.outlineColor = outlineColor;
                return this;
            }

            public NoteOptions build() {
                return new NoteOptions(notes, notePosition, font, fontColor, outlineColor);
            }
        }
    }

    /**
     * 探测点图形
     */
    public interface DetectPatterning {
        /**
         * 矩形
         */
        DetectPatterning RECT = new DetectPatterning() {
            // todo 以后加上静态形状就不用每次都绘制了
            @Override
            public void drawLT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                int W = 600;
                int s = 100;
                int sw = 200;
                int sww = 400;

                BufferedImage img = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = img.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setComposite(AlphaComposite.Src);
                g.setColor(outColor);
                g.fillRect(0, 0, W, W);

                g.setColor(bgColor);
                g.fillRect(s, s, sww, sww);

                g.setColor(inColor);
                g.fillRect(sw, sw, sw, sw);

                g2.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            @Override
            public void drawRT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }

            @Override
            public void drawLD(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }
        };
        /**
         * 圆角矩形
         */
        DetectPatterning ROUND_RECT = new DetectPatterning() {
            @Override
            public void drawLT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                float s = (float) Math.max(w, h) / 6;
                float sw = s * 4;
                float sww = s * 2;
                g2.setColor(outColor);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fill(new RoundRectangle2D.Float(x, y, w, w, sww, sww));

                g2.setComposite(AlphaComposite.Src);
                g2.setColor(bgColor);
                g2.fill(new RoundRectangle2D.Float(x + s, y + s, sw, sw, s * 1.5f, s * 1.5f));

                g2.setColor(inColor);
                g2.setComposite(AlphaComposite.Src);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fill(new RoundRectangle2D.Float(x + sww, y + sww, sww, sww, s, s));

            }

            @Override
            public void drawRT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }

            @Override
            public void drawLD(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }
        };

        /**
         * 圆角矩形 内套 圆
         */
        DetectPatterning ROUND_RECT_CIRCLE = new DetectPatterning() {
            @Override
            public void drawLT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                int s = Math.max(w, h) / 7;
                int sw = s * 2;
                int sww = s * 3;
                g2.setColor(outColor);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fill(new RoundRectangle2D.Float(x, y, w, w, sw, sw));

                g2.setComposite(AlphaComposite.Src);
                g2.setColor(bgColor);
                g2.fill(new RoundRectangle2D.Float(x + s, y + s, sw + sww, sw + sww, sw, sw));

                g2.setColor(inColor);
                g2.setComposite(AlphaComposite.Src);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillOval(x + sw, y + sw, sww, sww);
            }

            @Override
            public void drawRT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }

            @Override
            public void drawLD(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }
        };

        /**
         * 同心圆
         */
        DetectPatterning CIRCLE = new DetectPatterning() {
            @Override
            public void drawLT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                int s = Math.max(w, h) / 7;
                int sw = s * 2;
                int sww = s * 3;
                g2.setColor(outColor);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillOval(x, y, w, w);

                g2.setComposite(AlphaComposite.Src);
                g2.setColor(bgColor);
                g2.fillOval(x + s, y + s, sw + sww, sw + sww);

                g2.setColor(inColor);
                g2.setComposite(AlphaComposite.Src);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillOval(x + sw, y + sw, sww, sww);
            }

            @Override
            public void drawRT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }

            @Override
            public void drawLD(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }
        };

        /**
         * 同心圆
         */
        DetectPatterning MONEY = new DetectPatterning() {
            @Override
            public void drawLT(Graphics2D g, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                int W = 1000;
                double s = W / 7.0;
                double sw = s * 2;
                double sww = s * 3;

                BufferedImage img = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = img.createGraphics();
                g2.setComposite(AlphaComposite.Src);
                g2.setColor(bgColor);
                g2.fillRect(0, 0, W, W);

                g2.setColor(outColor);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillOval(0, 0, W, W);

                g2.setColor(bgColor);
                g2.fillOval((int) s, (int) s, (int) (sw + sww), (int) (sw + sww));

                //限制范围
                g2.setClip((int) sw, (int) sw, (int) sww, (int) sww);
                g2.setColor(inColor);
                g2.fillRect((int) sw, (int) sw, (int) sww, (int) sww);
                // 切割
                g2.setColor(bgColor);
                int circleStartA = (int) ((-(2 + 3 * Math.sqrt(3)) / 2) * s);
                int circleStartB = (int) (s / 2);
                int circleStartC = (int) (((4 + 3 * Math.sqrt(3)) / 2) * s);
                int diameter = (int) s * 6;
                // 左
                g2.fillOval(circleStartA, circleStartB, diameter, diameter);
                // 上
                g2.fillOval(circleStartB, circleStartA, diameter, diameter);
                // 下
                g2.fillOval(circleStartB, circleStartC, diameter, diameter);
                // 右
                g2.fillOval(circleStartC, circleStartB, diameter, diameter);

                g.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            @Override
            public void drawRT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }

            @Override
            public void drawLD(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }
        };

        /**
         * 圆角矩形 左上直角
         */
        DetectPatterning ROUND_RECT_ONE_RIGHT_ANGLE = new DetectPatterning() {
            @Override
            public void drawLT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                BufferedImage img = drawRaw(90, inColor, outColor, bgColor);
                g2.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            @Override
            public void drawRT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                BufferedImage img = drawRaw(0, inColor, outColor, bgColor);
                g2.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            @Override
            public void drawLD(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                BufferedImage img = drawRaw(180, inColor, outColor, bgColor);
                g2.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            private BufferedImage drawRaw(double rotate, Color inColor, Color outColor, Color bgColor) {
                int W = 1000;
                double s = W / 7.0;
                double s2 = s * 2;
                double s3 = s * 3;
                double s4 = s * 4;
                double s5 = s * 5;

                BufferedImage img = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = img.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(AlphaComposite.Src);
                g2.setColor(bgColor);
                g2.fillRect(0, 0, W, W);

                g2.rotate(Math.toRadians(rotate), W >> 1, W >> 1);

                g2.setColor(outColor);
                g2.fillRoundRect(0, 0, W, W, (int) s5, (int) s5);
                g2.setColor(bgColor);
                g2.fillRoundRect((int) s, (int) s, (int) s5, (int) s5, (int) s4, (int) s4);
                g2.setColor(inColor);
                g2.fillRoundRect((int) s2, (int) s2, (int) s3, (int) s3, (int) s2, (int) s2);

                //剪切
                g2.setClip(0, 0, W >> 1, W >> 1);

                g2.setColor(bgColor);
                g2.fillRect(0, 0, W, W);

                g2.setColor(outColor);
                g2.fillRect(0, 0, W, W);
                g2.setColor(bgColor);
                g2.fillRect((int) s, (int) s, (int) s5, (int) s5);
                g2.setColor(inColor);
                g2.fillRect((int) s2, (int) s2, (int) s3, (int) s3);

                return img;
            }
        };

        /**
         * 矩形 一圆角
         */
        DetectPatterning RECT_ONE_ROUND_ANGLE = new DetectPatterning() {
            @Override
            public void drawLT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                BufferedImage img = drawRaw(0, inColor, outColor, bgColor);
                g2.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            @Override
            public void drawRT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                BufferedImage img = drawRaw(90, inColor, outColor, bgColor);
                g2.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            @Override
            public void drawLD(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                BufferedImage img = drawRaw(-90, inColor, outColor, bgColor);
                g2.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            private BufferedImage drawRaw(double rotate, Color inColor, Color outColor, Color bgColor) {
                int W = 1000;
                double s = W / 7.0;
                double s2 = s * 2;
                double s3 = s * 3;
                double s4 = s * 4;
                double s5 = s * 5;

                BufferedImage img = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = img.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(AlphaComposite.Src);
                g2.setColor(bgColor);
                g2.fillRect(0, 0, W, W);
                g2.rotate(Math.toRadians(rotate), W >> 1, W >> 1);

                g2.setColor(outColor);
                g2.fillRect(0, 0, W, W);
                g2.setColor(bgColor);
                g2.fillRect((int) s, (int) s, (int) s5, (int) s5);
                g2.setColor(inColor);
                g2.fillRect((int) s2, (int) s2, (int) s3, (int) s3);

                //限制区域
                g2.setClip(0, 0, W >> 1, W >> 1);
                g2.setColor(bgColor);
                g2.fillRect(0, 0, W, W);

                g2.setColor(outColor);
                g2.fillRoundRect(0, 0, W, W, (int) s5, (int) s5);
                g2.setColor(bgColor);
                g2.fillRoundRect((int) s, (int) s, (int) s5, (int) s5, (int) s4, (int) s4);
                g2.setColor(inColor);
                g2.fillRoundRect((int) s2, (int) s2, (int) s3, (int) s3, (int) s2, (int) s2);

                return img;
            }
        };

        /**
         * 矩形 两个圆角 圆角左上右下
         */
        DetectPatterning RECT_TWO_ROUND_ANGLE_LT = new DetectPatterning() {
            @Override
            public void drawLT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                BufferedImage img = drawRaw(0, inColor, outColor, bgColor);
                g2.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            @Override
            public void drawRT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                BufferedImage img = drawRaw(90, inColor, outColor, bgColor);
                g2.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            @Override
            public void drawLD(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                BufferedImage img = drawRaw(-90, inColor, outColor, bgColor);
                g2.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            private BufferedImage drawRaw(double rotate, Color inColor, Color outColor, Color bgColor) {
                int W = 1000;
                double s = W / 7.0;
                double s2 = s * 2;
                double s3 = s * 3;
                double s4 = s * 4;
                double s5 = s * 5;

                BufferedImage img = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = img.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(AlphaComposite.Src);
                g2.setColor(bgColor);
                g2.fillRect(0, 0, W, W);
                g2.rotate(Math.toRadians(rotate), W >> 1, W >> 1);

                g2.setColor(outColor);
                g2.fillRect(0, 0, W, W);
                g2.setColor(bgColor);
                g2.fillRect((int) s, (int) s, (int) s5, (int) s5);
                g2.setColor(inColor);
                g2.fillRect((int) s2, (int) s2, (int) s3, (int) s3);

                //限制区域
                int i = W >> 1;
                int[][] area = {{0, i}, {i, i}};
                for (int[] a : area) {
                    g2.setClip(a[0], a[0], a[1], a[1]);
                    g2.setColor(bgColor);
                    g2.fillRect(0, 0, W, W);

                    g2.setColor(outColor);
                    g2.fillRoundRect(0, 0, W, W, (int) s5, (int) s5);
                    g2.setColor(bgColor);
                    g2.fillRoundRect((int) s, (int) s, (int) s5, (int) s5, (int) s4, (int) s4);
                    g2.setColor(inColor);
                    g2.fillRoundRect((int) s2, (int) s2, (int) s3, (int) s3, (int) s2, (int) s2);
                }
                return img;
            }
        };

        /**
         * 矩形 有两个圆角 左下右上
         */
        DetectPatterning RECT_TWO_ROUND_ANGLE_LD = new DetectPatterning() {
            @Override
            public void drawLT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                RECT_TWO_ROUND_ANGLE_LT.drawRT(g2, x, y, w, h, inColor, outColor, bgColor);
            }

            @Override
            public void drawRT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                RECT_TWO_ROUND_ANGLE_LT.drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }

            @Override
            public void drawLD(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor) {
                RECT_TWO_ROUND_ANGLE_LT.drawLT(g2, x, y, w, h, inColor, outColor, bgColor);
            }
        };

        /**
         * 画出左上图形
         *
         * @param g2 原图
         * @param x  起始X位置
         * @param y  起始Y位置
         * @param w  探测图形宽
         * @param h  探测图形高
         */
        void drawLT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor);

        /**
         * 画出右上图形
         *
         * @param g2 原图
         * @param x  起始X位置
         * @param y  起始Y位置
         * @param w  探测图形宽
         * @param h  探测图形高
         */
        void drawRT(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor);

        /**
         * 画出左下图形
         *
         * @param g2 原图
         * @param x  起始X位置
         * @param y  起始Y位置
         * @param w  探测图形宽
         * @param h  探测图形高
         */
        void drawLD(Graphics2D g2, int x, int y, int w, int h, Color inColor, Color outColor, Color bgColor);
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
     * logo位置
     */
    public enum LogoPosition {
        /**
         * 居中
         */
        MIDDLE,
        /**
         * 右下
         */
        RIGHT_DOWN;

        public static LogoPosition getPosition(String name) {
            return LogoPosition.valueOf(name.toUpperCase());
        }
    }

    public enum NotePosition {
        /**
         * 居中
         */
        MIDDLE,
        /**
         * 底部
         */
        DOWN;

        public static NotePosition getNotePosition(String name) {
            return NotePosition.valueOf(name.toUpperCase());
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
    public interface DrawStyle {
        /**
         * 矩形
         */
        DrawStyle RECT = new DrawStyle() {
            @Override
            public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt) {
                g2d.fillRect(x, y, w, h);
            }

            @Override
            public boolean expand(DotSize dotSize) {
                return dotSize.getRow() == dotSize.getCol();
            }
        };

        /**
         * 圆点
         */
        DrawStyle CIRCLE = new DrawStyle() {
            @Override
            public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt) {
                g2d.fill(new Ellipse2D.Float(x, y, w, h));
            }

            @Override
            public boolean expand(DotSize dotSize) {
                return dotSize.getRow() == dotSize.getCol();
            }
        };

        /**
         * 三角形
         */
        DrawStyle TRIANGLE = new DrawStyle() {
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
        };

        /**
         * 五边形-钻石
         */
        DrawStyle DIAMOND = new DrawStyle() {
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
        };

        /**
         * 六边形
         */
        DrawStyle SEXANGLE = new DrawStyle() {
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
        };

        /**
         * 八边形
         */
        DrawStyle OCTAGON = new DrawStyle() {
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
        };

        /**
         * 自定义图片
         */
        DrawStyle IMAGE = new DrawStyle() {
            @Override
            public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt) {
                g2d.drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }

            @Override
            public boolean expand(DotSize expandType) {
                return true;
            }
        };

        /**
         * 文字绘制
         */
        DrawStyle TXT = new DrawStyle() {
            @Override
            public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt) {
                Font oldFont = g2d.getFont();
                if (oldFont.getSize() != w) {
                    Font newFont = QuickQrFont.font(oldFont.getName(), oldFont.getStyle(), w);
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

        DrawStyle Liquid1 = new DrawStyle() {
            @Override
            public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt) {

            }

            @Override
            public boolean expand(DotSize dotSize) {
                return false;
            }
        };

        void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img, String txt);

        /**
         * 返回是否支持绘制自定义图形的扩展
         */
        boolean expand(DotSize dotSize);
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

    /**
     * 探测图形的配置信息
     */
    public static class DetectOptions {

        private Color outColor;

        private Color inColor;

        /**
         * 默认探测图形，优先级高于颜色的定制和图形方案（即存在图片时，用图片绘制探测图形）
         */
        private BufferedImage detectImg;

        /**
         * 探测图形方案
         */
        private DetectPatterning detectPatterning;

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

        public DetectOptions() {
        }

        public DetectOptions(Color outColor, Color inColor, BufferedImage detectImg, DetectPatterning detectPatterning,
                             BufferedImage detectImgLT, BufferedImage detectImgRT, BufferedImage detectImgLD, Boolean special) {
            this.outColor = outColor;
            this.inColor = inColor;
            this.detectImg = detectImg;
            this.detectPatterning = detectPatterning;
            this.detectImgLT = detectImgLT;
            this.detectImgRT = detectImgRT;
            this.detectImgLD = detectImgLD;
            this.special = special;
        }

        public static DetectOptionsBuilder builder() {
            return new DetectOptionsBuilder();
        }

        public Boolean getSpecial() {
            return BooleanUtils.isTrue(special);
        }

        public void setSpecial(Boolean special) {
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

        public DetectPatterning getDetectPatterning() {
            return detectPatterning;
        }

        public void setDetectPatterning(DetectPatterning detectPatterning) {
            this.detectPatterning = detectPatterning;
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
                    Objects.equals(special, that.special) && Objects.equals(detectPatterning, that.detectPatterning);
        }

        @Override
        public int hashCode() {

            return Objects.hash(outColor, inColor, detectImg, detectPatterning, detectImgLT, detectImgRT, detectImgLD, special);
        }

        @Override
        public String toString() {
            return "DetectOptions{" + "outColor=" + outColor + ", inColor=" + inColor + ", detectImg=" + detectImg +
                    ", detectImgLT=" + detectImgLT + ", detectImgRT=" + detectImgRT + ", detectImgLD=" + detectImgLD +
                    ", special=" + special + '}';
        }

        public static class DetectOptionsBuilder {
            private Color outColor;

            private Color inColor;

            private BufferedImage detectImg;

            private DetectPatterning detectPatterning;

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

            public DetectOptionsBuilder detectPatterning(DetectPatterning detectPatterning) {
                this.detectPatterning = detectPatterning;
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
                return new DetectOptions(outColor, inColor, detectImg, detectPatterning, detectImgLT, detectImgRT, detectImgLD, special);
            }
        }
    }
}
