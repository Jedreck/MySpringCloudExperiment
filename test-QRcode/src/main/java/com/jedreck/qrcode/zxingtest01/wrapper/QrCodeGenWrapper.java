package com.jedreck.qrcode.zxingtest01.wrapper;

import cn.hutool.core.img.ImgUtil;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jedreck.qrcode.zxingtest01.constants.MediaType;
import com.jedreck.qrcode.zxingtest01.constants.QuickQrFont;
import com.jedreck.qrcode.zxingtest01.helper.QrCodeGenerateHelper;
import com.jedreck.qrcode.zxingtest01.utils.Base64Util;
import com.jedreck.qrcode.zxingtest01.utils.ColorUtil;
import com.jedreck.qrcode.zxingtest01.utils.FileReadUtil;
import com.jedreck.qrcode.zxingtest01.utils.FileWriteUtil;
import com.jedreck.qrcode.zxingtest01.utils.ImageLoadUtil;
import com.jedreck.qrcode.zxingtest01.utils.IoUtil;
import com.jedreck.qrcode.zxingtest01.utils.gif.GifDecoder;
import com.jedreck.qrcode.zxingtest01.utils.gif.GifHelper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QrCodeGenWrapper {
    private QrCodeGenWrapper() {
    }

    public static Builder of(String content) {
        return new Builder().setMsg(content);
    }

    public static class Builder {
        private static Logger log = LoggerFactory.getLogger(Builder.class);

        /**
         * 生成二维码的字符串
         */
        private String msg;

        /**
         * qrcode image width
         */
        private Integer w;

        /**
         * qrcode image height
         */
        private Integer h;

        /**
         * 默认字符编码, default UTF-8
         */
        private String code = StandardCharsets.UTF_8.toString();

        /**
         * 二维码与边框间距 0 - 4
         */
        private Integer padding;

        /**
         * 容错级别
         */
        private ErrorCorrectionLevel errorCorrection = ErrorCorrectionLevel.H;

        /**
         * 输出二维码图片格式，默认为 png
         */
        private String picType = MediaType.ImagePng.getExt();

        private QrCodeOptions.BgImgOptions.BgImgOptionsBuilder bgImgOptions;

        private QrCodeOptions.LogoOptions.LogoOptionsBuilder logoOptions;

        private QrCodeOptions.DrawOptions.DrawOptionsBuilder drawOptions;

        private QrCodeOptions.DetectOptions.DetectOptionsBuilder detectOptions;

        public Builder() {
            // 背景图默认采用覆盖方式
            bgImgOptions =
                    QrCodeOptions.BgImgOptions.builder().bgImgStyle(QrCodeOptions.BgImgStyle.OVERRIDE).opacity(0.85f);

            // 默认采用普通格式的logo， 无边框
            logoOptions = QrCodeOptions.LogoOptions.builder().logoStyle(QrCodeOptions.LogoStyle.NORMAL).border(false)
                    .rate(12);

            // 绘制信息，默认黑白方块
            drawOptions =
                    QrCodeOptions.DrawOptions.builder().drawStyle(QrCodeOptions.DrawStyle.RECT).bgColor(Color.WHITE)
                            .preColor(Color.BLACK).diaphaneityFill(false).enableScale(false);

            // 探测图形
            detectOptions = QrCodeOptions.DetectOptions.builder();
        }

        public String getMsg() {
            return msg;
        }

        /**
         * 生成二维码的字符串
         */
        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Integer getW() {
            return w == null ? (h == null ? 200 : h) : w;
        }

        /**
         * 宽
         */
        public Builder setW(Integer w) {
            if (w != null && w <= 0) {
                throw new IllegalArgumentException("生成二维码的宽必须大于0");
            }
            this.w = w;
            return this;
        }

        public Integer getH() {
            return h == null ? (w == null ? 200 : w) : h;
        }

        /**
         * 高
         */
        public Builder setH(Integer h) {
            if (h != null && h <= 0) {
                throw new IllegalArgumentException("生成功能二维码的搞必须大于0");
            }
            this.h = h;
            return this;
        }

        /**
         * 二维码字符编码
         */
        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Integer getPadding() {
            if (padding == null) {
                // 默认为 2
                return 2;
            }

            if (padding < 0) {
                return 0;
            }

            if (padding > 4) {
                return 4;
            }

            return padding;
        }

        /**
         * 二维码与边框间距 0 - 4
         */
        public Builder setPadding(Integer padding) {
            this.padding = padding;
            return this;
        }

        /**
         * 二维码图片格式
         */
        public Builder setPicType(String picType) {
            this.picType = picType;
            return this;
        }

        /**
         * 设置二维码容错级别
         */
        public Builder setErrorCorrection(ErrorCorrectionLevel errorCorrection) {
            this.errorCorrection = errorCorrection;
            return this;
        }

        /////////////// 1 -- logo 相关配置 ///////////////

        /**
         * 设置logo路径
         *
         * @param logo 本地路径 or 网络地址
         */
        public Builder setLogo(String logo) throws IOException {
            try {
                return setLogo(ImageLoadUtil.getImageByPath(logo));
            } catch (IOException e) {
                log.error("load logo error! e:{}", e);
                throw new IOException("load logo error!", e);
            }
        }

        /**
         * logo
         */
        public Builder setLogo(InputStream inputStream) throws IOException {
            try {
                return setLogo(ImageIO.read(inputStream));
            } catch (IOException e) {
                log.error("load backgroundImg error! e:{}", e);
                throw new IOException("load backgroundImg error!", e);
            }
        }

        /**
         * 设置文字logo
         *
         * @param logoStr logo文字
         * @param font    字体
         * @param color   颜色
         * @return
         */
        public Builder setLogoStr(String[] logoStr, Font font, Color color, Color bgColor) {
            BufferedImage image = StringPicture.createImage(logoStr, font, color, bgColor);
            logoOptions.logo(image);
            return this;
        }

        /**
         * logo图片
         */
        public Builder setLogo(BufferedImage img) {
            logoOptions.logo(img);
            return this;
        }

        /**
         * logo样式
         */
        public Builder setLogoStyle(QrCodeOptions.LogoStyle logoStyle) {
            logoOptions.logoStyle(logoStyle);
            return this;
        }

        /**
         * logo背景颜色
         *
         * @param color 0xffffffff  前两位为透明读， 三四位 R， 五六位 G， 七八位 B
         */
        public Builder setLogoBgColor(Integer color) {
            if (color == null) {
                return this;
            }

            return setLogoBgColor(ColorUtil.int2color(color));
        }

        /**
         * logo 背景颜色
         */
        public Builder setLogoBgColor(Color color) {
            logoOptions.border(true);
            logoOptions.borderColor(color);
            return this;
        }

        /**
         * logo 外层边框颜色
         *
         * @param color 0xffffffff  前两位为透明读， 三四位 R， 五六位 G， 七八位 B
         */
        public Builder setLogoBorderBgColor(Integer color) {
            if (color == null) {
                return this;
            }
            return setLogoBorderBgColor(ColorUtil.int2color(color));
        }

        /**
         * logo 外层边框颜色
         *
         * @param color
         * @return
         */

        public Builder setLogoBorderBgColor(Color color) {
            logoOptions.border(true);
            logoOptions.outerBorderColor(color);
            return this;
        }

        /**
         * logo 是否有边框
         */
        public Builder setLogoBorder(boolean border) {
            logoOptions.border(border);
            return this;
        }

        /**
         * logo 大小
         *
         * @param rate 二维码大小的百分比
         */
        public Builder setLogoRate(int rate) {
            logoOptions.rate(rate);
            return this;
        }

        /**
         * logo透明度，数字越小越透明
         *
         * @param opacity 0.xx的小数
         */
        public Builder setLogoOpacity(float opacity) {
            logoOptions.opacity(opacity);
            return this;
        }

        public Builder setLogoPosition(QrCodeOptions.LogoPosition logoPosition) {
            logoOptions.logoPosition(logoPosition);
            return this;
        }

        ///////////////// logo配置结束 ///////////////

        // ------------------------------------------

        /////////////// 2 -- 背景 相关配置 ///////////////

        /**
         * 设置背景图片路径
         *
         * @param path @param logo 本地路径 or 网络地址
         */
        public Builder setBgImg(String path) throws IOException {
            try {
                return setBgImg(FileReadUtil.getStreamByFileName(path));
            } catch (IOException e) {
                log.error("load backgroundImg error! e:{}", e);
                throw new IOException("load backgroundImg error!", e);
            }
        }

        /**
         * 设置背景图片路径
         */
        public Builder setBgImg(InputStream inputStream) throws IOException {
            try {
                ByteArrayInputStream target = IoUtil.toByteArrayInputStream(inputStream);
                MediaType media = MediaType.typeOfMagicNum(FileReadUtil.getMagicNum(target));
                if (media == MediaType.ImageGif) {
                    GifDecoder gifDecoder = new GifDecoder();
                    gifDecoder.read(target);
                    bgImgOptions.gifDecoder(gifDecoder);
                    return this;
                } else {
                    return setBgImg(ImageIO.read(target));
                }
            } catch (IOException e) {
                log.error("load backgroundImg error! e:{}", e);
                throw new IOException("load backgroundImg error!", e);
            }
        }

        /**
         * 设置背景图片路径
         */
        public Builder setBgImg(BufferedImage bufferedImage) {
            bgImgOptions.bgImg(bufferedImage);
            return this;
        }

        /**
         * 背景图样式
         *
         * @param bgImgStyle {@link QrCodeOptions.BgImgStyle}
         */
        public Builder setBgStyle(QrCodeOptions.BgImgStyle bgImgStyle) {
            bgImgOptions.bgImgStyle(bgImgStyle);
            return this;
        }

        /**
         * 背景图宽
         */
        public Builder setBgW(int w) {
            bgImgOptions.bgW(w);
            return this;
        }

        /**
         * 背景图高
         */
        public Builder setBgH(int h) {
            bgImgOptions.bgH(h);
            return this;
        }

        /**
         * 用于设置二维码的透明度
         * 前提是 bgImgStyle ==  {@link QrCodeOptions.BgImgStyle#OVERRIDE}
         */
        public Builder setBgOpacity(float opacity) {
            bgImgOptions.opacity(opacity);
            return this;
        }

        /**
         * 用于设置二维码的绘制在背景图上的X坐标
         * 前提是 bgImgStyle ==  {@link QrCodeOptions.BgImgStyle#FILL}
         */
        public Builder setBgStartX(int startX) {
            bgImgOptions.startX(startX);
            return this;
        }

        /**
         * 用于设置二维码的绘制在背景图上的Y坐标
         * 前提是 bgImgStyle ==  {@link QrCodeOptions.BgImgStyle#FILL}
         */
        public Builder setBgStartY(int startY) {
            bgImgOptions.startY(startY);
            return this;
        }

        /////////////// 背景 配置结束 ///////////////

        // ------------------------------------------

        /////////////// 3 -- 探测图形 相关配置 ///////////////
        public Builder setDetectImg(String detectImg) throws IOException {
            try {
                return setDetectImg(ImageLoadUtil.getImageByPath(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        public Builder setDetectImg(InputStream detectImg) throws IOException {
            try {
                return setDetectImg(ImageIO.read(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        public Builder setDetectImg(BufferedImage detectImg) {
            detectOptions.detectImg(detectImg);
            detectOptions.special(true);
            return this;
        }

        /**
         * 探测图形方案
         *
         * @param detectPatterning {@link QrCodeOptions.DetectPatterning}
         */
        public Builder setDetectPatterning(QrCodeOptions.DetectPatterning detectPatterning) {
            detectOptions.detectPatterning(detectPatterning);
            return this;
        }

        /**
         * 左上角探测图形
         */
        public Builder setLTDetectImg(String detectImg) throws IOException {
            try {
                return setLTDetectImg(ImageLoadUtil.getImageByPath(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 左上角探测图形
         */
        public Builder setLTDetectImg(InputStream detectImg) throws IOException {
            try {
                return setLTDetectImg(ImageIO.read(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 左上角探测图形
         */
        public Builder setLTDetectImg(BufferedImage detectImg) {
            detectOptions.detectImgLT(detectImg);
            detectOptions.special(true);
            return this;
        }

        /**
         * 右上角探测图形
         */
        public Builder setRTDetectImg(String detectImg) throws IOException {
            try {
                return setRTDetectImg(ImageLoadUtil.getImageByPath(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 右上角探测图形
         */
        public Builder setRTDetectImg(InputStream detectImg) throws IOException {
            try {
                return setRTDetectImg(ImageIO.read(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 右上角探测图形
         */
        public Builder setRTDetectImg(BufferedImage detectImg) {
            detectOptions.detectImgRT(detectImg);
            detectOptions.special(true);
            return this;
        }

        /**
         * 左下角探测图形
         */
        public Builder setLDDetectImg(String detectImg) throws IOException {
            try {
                return setLDDetectImg(ImageLoadUtil.getImageByPath(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 左下角探测图形
         */
        public Builder setLDDetectImg(InputStream detectImg) throws IOException {
            try {
                return setLDDetectImg(ImageIO.read(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 左下角探测图形
         */
        public Builder setLDDetectImg(BufferedImage detectImg) {
            detectOptions.detectImgLD(detectImg);
            detectOptions.special(true);
            return this;
        }

        public Builder setDetectOutColor(Integer outColor) {
            if (outColor == null) {
                return this;
            }

            return setDetectOutColor(ColorUtil.int2color(outColor));
        }

        public Builder setDetectOutColor(Color outColor) {
            detectOptions.outColor(outColor);
            return this;
        }

        public Builder setDetectInColor(Integer inColor) {
            if (inColor == null) {
                return this;
            }

            return setDetectInColor(ColorUtil.int2color(inColor));
        }

        public Builder setDetectInColor(Color inColor) {
            detectOptions.inColor(inColor);
            return this;
        }

        /**
         * 设置探测图形样式，不跟随二维码主样式
         *
         * @return
         */
        public Builder setDetectSpecial() {
            detectOptions.special(true);
            return this;
        }

        /////////////// 探测图形 配置结束 ///////////////

        // ------------------------------------------

        /////////////// 4 -- 二维码绘制 相关配置 ///////////////

        /**
         * 二维码绘制样式
         */
        public Builder setDrawStyle(QrCodeOptions.DrawStyle drawStyle) {
            drawOptions.drawStyle(drawStyle);
            return this;
        }

        /**
         * 透明度填充，如绘制二维码的图片中存在透明区域
         *
         * @param fill true，则会用bgColor填充透明的区域；false，则透明区域依旧是透明的
         */
        public Builder setDiaphaneityFill(boolean fill) {
            drawOptions.diaphaneityFill(fill);
            return this;
        }

        /**
         * 二维码矩阵中 1对应的着色颜色
         *
         * @param color 0xffffffff  前两位为透明读， 三四位 R， 五六位 G， 七八位 B
         */
        public Builder setDrawPreColor(int color) {
            return setDrawPreColor(ColorUtil.int2color(color));
        }

        /**
         * 二维码矩阵中 1对应的着色颜色
         *
         * @param color 0xffffffff  前两位为透明读， 三四位 R， 五六位 G， 七八位 B
         */
        public Builder setDrawPreColor(Color color) {
            drawOptions.preColor(color);
            return this;
        }

        /**
         * 二维码矩阵中 0对应的背景颜色
         */
        public Builder setDrawBgColor(int color) {
            return setDrawBgColor(ColorUtil.int2color(color));
        }

        /**
         * 二维码矩阵中 0对应的背景颜色
         */
        public Builder setDrawBgColor(Color color) {
            drawOptions.bgColor(color);
            return this;
        }

        public Builder setDrawBgImg(String img) throws IOException {
            try {
                return setDrawBgImg(ImageLoadUtil.getImageByPath(img));
            } catch (IOException e) {
                log.error("load drawBgImg error! e:{}", e);
                throw new IOException("load drawBgImg error!", e);
            }
        }

        public Builder setDrawBgImg(InputStream img) throws IOException {
            try {
                return setDrawBgImg(ImageIO.read(img));
            } catch (IOException e) {
                log.error("load drawBgImg error! e:{}", e);
                throw new IOException("load drawBgImg error!", e);
            }
        }

        /**
         * 二维码矩阵中，0点对应绘制的背景图片， 1点对应绘制的图片在 imgMapper 中
         */
        public Builder setDrawBgImg(BufferedImage img) {
            drawOptions.bgImg(img);
            return this;
        }

        /**
         * true 时表示支持对相邻的着色点进行合并处理 （即用一个大图来绘制相邻的两个着色点）
         * 说明： 三角形样式关闭该选项，因为留白过多，对识别有影响
         */
        public Builder setDrawEnableScale(boolean enable) {
            drawOptions.enableScale(enable);
            return this;
        }

        public Builder setDrawImg(String img) throws IOException {
            try {
                return setDrawImg(ImageLoadUtil.getImageByPath(img));
            } catch (IOException e) {
                log.error("load draw img error! e: {}", e);
                throw new IOException("load draw img error!", e);
            }
        }

        public Builder setDrawImg(InputStream input) throws IOException {
            try {
                return setDrawImg(ImageIO.read(input));
            } catch (IOException e) {
                log.error("load draw img error! e: {}", e);
                throw new IOException("load draw img error!", e);
            }
        }

        public Builder setDrawImg(BufferedImage img) {
            addImg(1, 1, img);
            return this;
        }

        public Builder addImg(int row, int col, BufferedImage img) {
            if (img == null) {
                return this;
            }
            drawOptions.enableScale(true);
            drawOptions.drawImg(row, col, img);
            return this;
        }

        public Builder addImg(int row, int col, String img) throws IOException {
            try {
                return addImg(row, col, ImageLoadUtil.getImageByPath(img));
            } catch (IOException e) {
                log.error("load draw size4img error! e: {}", e);
                throw new IOException("load draw row:" + row + ", col:" + col + " img error!", e);
            }
        }

        public Builder addImg(int row, int col, InputStream img) throws IOException {
            try {
                return addImg(row, col, ImageIO.read(img));
            } catch (IOException e) {
                log.error("load draw size4img error! e: {}", e);
                throw new IOException("load draw row:" + row + ", col:" + col + " img error!", e);
            }
        }

        /**
         * 渲染文字二维码的字符串
         *
         * @param text
         */
        public Builder setQrText(String text) {
            drawOptions.text(text);
            return this;
        }

        public Builder setQrTxtMode(QrCodeOptions.TxtMode txtMode) {
            drawOptions.txtMode(txtMode);
            return this;
        }

        /**
         * 字体名
         */
        public Builder setQrDotFontName(String fontName) {
            drawOptions.fontName(fontName);
            return this;
        }

        /**
         * 字体样式
         *
         * @param fontStyle 0 {@link Font#PLAIN} 1 {@link Font#BOLD} 2 {@link Font#ITALIC}
         */
        public Builder setQrDotFontStyle(int fontStyle) {
            drawOptions.fontStyle(fontStyle);
            return this;
        }

        /////////////// 二维码绘制 配置结束 ///////////////

        private void validate() {
            if (msg == null || msg.length() == 0) {
                throw new IllegalArgumentException("生成二维码的内容不能为空!");
            }
        }

        private QrCodeOptions build() {
            this.validate();

            QrCodeOptions qrCodeConfig = new QrCodeOptions();
            qrCodeConfig.setMsg(getMsg());
            qrCodeConfig.setH(getH());
            qrCodeConfig.setW(getW());// 设置背景信息
            QrCodeOptions.BgImgOptions bgOp = bgImgOptions.build();
            if (bgOp.getBgImg() == null && bgOp.getGifDecoder() == null) {
                qrCodeConfig.setBgImgOptions(null);
            } else {
                qrCodeConfig.setBgImgOptions(bgOp);
            }

            // 设置logo信息
            QrCodeOptions.LogoOptions logoOp = logoOptions.build();
            if (logoOp.getLogo() == null) {
                qrCodeConfig.setLogoOptions(null);
            } else {
                qrCodeConfig.setLogoOptions(logoOp);
            }

            // 绘制信息
            QrCodeOptions.DrawOptions drawOp = drawOptions.build();
            qrCodeConfig.setDrawOptions(drawOp);

            // 设置detect绘制信息
            QrCodeOptions.DetectOptions detectOp = detectOptions.build();
            if (detectOp.getOutColor() == null && detectOp.getInColor() == null) {
                detectOp.setInColor(drawOp.getPreColor());
                detectOp.setOutColor(drawOp.getPreColor());
            } else if (detectOp.getOutColor() == null) {
                detectOp.setOutColor(detectOp.getOutColor());
            } else if (detectOp.getInColor() == null) {
                detectOp.setInColor(detectOp.getInColor());
            }
            if (null == detectOp.getDetectPatterning()) {
                detectOp.setDetectPatterning(QrCodeOptions.DetectPatterning.RECT);
            }
            qrCodeConfig.setDetectOptions(detectOp);

            if (qrCodeConfig.getBgImgOptions() != null &&
                    qrCodeConfig.getBgImgOptions().getBgImgStyle() == QrCodeOptions.BgImgStyle.PENETRATE) {
                // 透传，用背景图颜色进行绘制时
                drawOp.setPreColor(ColorUtil.OPACITY);
                qrCodeConfig.getBgImgOptions().setOpacity(1);
                qrCodeConfig.getDetectOptions().setInColor(ColorUtil.OPACITY);
                qrCodeConfig.getDetectOptions().setOutColor(ColorUtil.OPACITY);
            }

            // 设置输出图片格式
            qrCodeConfig.setPicType(picType);

            // 设置精度参数
            Map<EncodeHintType, Object> hints = new HashMap<>(3);
            hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrection);
            hints.put(EncodeHintType.CHARACTER_SET, code);
            hints.put(EncodeHintType.MARGIN, this.getPadding());
            qrCodeConfig.setHints(hints);

            return qrCodeConfig;
        }

        public String asString() throws IOException, WriterException {
            return QrCodeGenWrapper.asString(build());
        }

        public BufferedImage asBufferedImage() throws IOException, WriterException {
            return QrCodeGenWrapper.asBufferedImage(build());
        }

        public ByteArrayOutputStream asStream() throws WriterException, IOException {
            QrCodeOptions options = build();
            if (options.gifQrCode()) {
                return QrCodeGenWrapper.asGif(options);
            } else {
                BufferedImage img = QrCodeGenWrapper.asBufferedImage(options);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(img, options.getPicType(), outputStream);
                return outputStream;
            }
        }

        public boolean asFile(String absFileName) throws IOException, WriterException {
            return QrCodeGenWrapper.asFile(build(), absFileName);
        }

        public void writeBufferedImage2Stream(OutputStream out) throws IOException, WriterException {
            BufferedImage image = QrCodeGenWrapper.asBufferedImage(build());
            ImgUtil.write(image, picType, out);
        }

        @Override
        public String toString() {
            return "Builder{" + "msg='" + msg + '\'' + ", w=" + w + ", h=" + h + ", code='" + code + '\'' +
                    ", padding=" + padding + ", errorCorrection=" + errorCorrection + ", picType='" + picType + '\'' +
                    ", bgImgOptions=" + bgImgOptions + ", logoOptions=" + logoOptions + ", drawOptions=" + drawOptions +
                    ", detectOptions=" + detectOptions + '}';
        }
    }

    private static ByteArrayOutputStream asGif(QrCodeOptions qrCodeOptions) throws WriterException {
        try {
            BitMatrixEx bitMatrix = QrCodeGenerateHelper.encode(qrCodeOptions);
            List<ImmutablePair<BufferedImage, Integer>> list =
                    QrCodeGenerateHelper.toGifImages(qrCodeOptions, bitMatrix);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            GifHelper.saveGif(list, outputStream);
            return outputStream;
        } finally {
            QuickQrFont.clear();
        }
    }

    private static BufferedImage asBufferedImage(QrCodeOptions qrCodeOptions) throws WriterException, IOException {
        try {
            BitMatrixEx bitMatrix = QrCodeGenerateHelper.encode(qrCodeOptions);
            return QrCodeGenerateHelper.toBufferedImage(qrCodeOptions, bitMatrix);
        } finally {
            QuickQrFont.clear();
        }
    }

    private static String asString(QrCodeOptions qrCodeOptions) throws WriterException, IOException {
        if (qrCodeOptions.gifQrCode()) {
            // 动态二维码生成
            try (ByteArrayOutputStream outputStream = asGif(qrCodeOptions)) {
                return Base64Util.encode(outputStream);
            }
        }

        // 普通二维码，直接输出图
        BufferedImage bufferedImage = asBufferedImage(qrCodeOptions);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, qrCodeOptions.getPicType(), outputStream);
            return Base64Util.encode(outputStream);
        }
    }

    private static boolean asFile(QrCodeOptions qrCodeOptions, String absFileName) throws WriterException, IOException {
        File file = new File(absFileName);
        FileWriteUtil.mkDir(file.getParentFile());

        if (qrCodeOptions.gifQrCode()) {
            // 保存动态二维码
            try (ByteArrayOutputStream output = asGif(qrCodeOptions)) {
                FileOutputStream out = new FileOutputStream(file);
                out.write(output.toByteArray());
                out.flush();
                out.close();
            }

            return true;
        }

        BufferedImage bufferedImage = asBufferedImage(qrCodeOptions);
        if (!ImageIO.write(bufferedImage, qrCodeOptions.getPicType(), file)) {
            throw new IOException("save QrCode image to: " + absFileName + " error!");
        }

        return true;
    }
}
