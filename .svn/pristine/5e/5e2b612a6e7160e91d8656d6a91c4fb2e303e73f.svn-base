/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soqz.wap.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 图片合成类
 * @author Dsmart <dsmart at 30buy email:yuliujian@30buy.com>
 */
public class PicTool {

    /**
     * 将多张图片合并成一张
     * @param images 图片地址数组
     * @param target 目标图片地址
     * @param format 目标图片格式
     * @param type  合并方式
     * 
     * @return 
     */
    public static boolean combineImages(String[] pics, String target, String format, int type) {

        boolean flag = false;

        int len = pics.length;

        if (len > 0) {

            File[] src = new File[len];

            BufferedImage[] images = new BufferedImage[len];

            int[][] ImageArrays = new int[len][];

            for (int i = 0; i < len; i++) {

                try {

                    src[i] = new File(pics[i]);

                    images[i] = ImageIO.read(src[i]);

                } catch (IOException ex) {

                    Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

                    return false;
                }

                int width = images[i].getWidth();

                int height = images[i].getHeight();

                ImageArrays[i] = new int[width * height];

                ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);

            }




            try {

                int padding = 1;

                int dst_width = images[0].getWidth() + images[5].getWidth() + 2 * padding;

                int dst_height = images[0].getHeight() + images[3].getHeight() + 2 * padding;

                BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);

                int rgb = new Color(255, 255, 255).getRGB();

                int count = 0;

                for (int i = 0; i < dst_width; i++) {

                    for (int j = 0; j < dst_height; j++) {



//                        if (i / 8 % 2 == 1 || j / 8 % 2 == 1) {
//
//                            rgb = new Color(255, 255, 255).getRGB();
//
//
//                        } else {


                        rgb = new Color(255, 255, 255).getRGB();

//                        }

                        ImageNew.setRGB(i, j, rgb);

                        count++;

                    }

                }




                ImageNew.setRGB(0, 0, images[0].getWidth(), images[0].getHeight(), ImageArrays[0], 0, images[0].getWidth());

                ImageNew.setRGB(images[0].getWidth() + padding * 2, 0, images[1].getWidth(), images[1].getHeight(), ImageArrays[1], 0, images[1].getWidth());

                ImageNew.setRGB(images[0].getWidth() + padding * 2, images[1].getHeight() + padding, images[2].getWidth(), images[2].getHeight(), ImageArrays[2], 0, images[2].getWidth());

                ImageNew.setRGB(0, images[0].getHeight() + padding * 2, images[3].getWidth(), images[3].getHeight(), ImageArrays[3], 0, images[3].getWidth());

                ImageNew.setRGB(images[3].getWidth() + padding, images[0].getHeight() + padding * 2, images[4].getWidth(), images[4].getHeight(), ImageArrays[4], 0, images[4].getWidth());

                ImageNew.setRGB(images[0].getWidth() + padding * 2, images[0].getHeight() + padding * 2, images[5].getWidth(), images[5].getHeight(), ImageArrays[5], 0, images[5].getWidth());

                File outFile = new File(target);

                ImageIO.write(ImageNew, format, outFile);


                flag = true;

            } catch (Exception ex) {

                Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

                return false;

            }

        }

        return flag;

    }

    /**
     * 切割长图
     * @param file
     * @param 切割长度
     */
    public static String cutLongImage(String file) {

        String result = null;

        try {

            File srcFile = new File(file);

            if (!srcFile.exists()) {

                throw new Exception("原始图片不存在");

            }

            Image image = ImageIO.read(srcFile);

            //获取长图的高度
            int height = image.getHeight(null);

            int segment = image.getWidth(null);

            if (segment > height) {

                result = srcFile.getName();

                throw new Exception("啊哈 不是长图 不用切割");

            }

            int times = height / segment + (height % segment > 0 ? 1 : 0);

            String fileBasePath = file.substring(0, file.replaceAll("\\\\", "/").lastIndexOf("/") + 1);

            String fileBaseName = file.substring(file.indexOf(fileBasePath) + fileBasePath.length(), file.lastIndexOf("."));

            StringBuilder nameBuilder = new StringBuilder();

            String name = null;

            int i = 0;

            PicResize picResize = new PicResize();

            while (times > i) {

                name = fileBaseName + "_" + i + ".jpg";

                double x = 0;

                double y = i * segment;

                int w = segment;

                int h = segment;

                String newFile = fileBasePath + name;

                picResize.cutImage(file, newFile, x, y, w, h);

                nameBuilder.append(name).append(",");

                i++;

            }

//            nameBuilder.append(srcFile.getName());

            srcFile.delete();

            result = nameBuilder.toString();

        } catch (Exception ex) {

            Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

        }

        return result;

    }

    /**
     * 给图片添加水印
     * @param iconPath 水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public static void markImageByIcon(String iconPath, String srcImgPath,
            String targerPath, int x, int y, Float alpha) {
        markImageByIcon(iconPath, srcImgPath, targerPath, x, y, null, alpha);
    }

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     * @param iconPath 水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param degree 水印图片旋转角度
     */
    public static void markImageByIcon(String iconPath, String srcImgPath,
            String targerPath, Integer x, Integer y, Integer degree, Float alpha) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }

            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);

            // 得到Image对象。
            Image img = imgIcon.getImage();

            if (alpha == null) {

                alpha = 1f;

            }

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            if (x == null) {

                x = 0;

            }

            if (y == null) {

                y = 0;

            }

            // 表示水印图片的位置
            g.drawImage(img, x, y, null);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targerPath);

            // 生成图片
            ImageIO.write(buffImg, "JPG", os);

        } catch (Exception ex) {
            Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * 转换图片格式  主要是将图片转换为jpg格式
     * 
     * 需求：将上传的图片转换为jpg格式
     * 
     * 适用范围：统一格式化上传图片
     * 
     * 作者:Dsmart
     * 
     * 时间:2014.01.15
     * 
     * @param sourceFile
     * @param formatName
     * @param destFile 
     * @return 
     */
    public static String convert(String sourceFile, String formatName) {

        String destFile = formatFileSuffix(sourceFile, formatName);

        //图片需要转换
        if (!destFile.equalsIgnoreCase(sourceFile)) {

            //直接转换 
            try {

                File f = new File(sourceFile);
//                System.out.println("sourceFile:"+sourceFile);
//                System.out.println("formatName:"+formatName);
                f.canRead();

                BufferedImage src = ImageIO.read(f);
//                System.out.println("src:"+src);
                ImageIO.write(src, formatName, new File(destFile));

                if (f.exists()) {

                    f.delete();

                }

                src.flush();

            } catch (Exception ex) {//bmp转jpg

                //格式化为jpg
                if (formatName.equalsIgnoreCase("jpg") || formatName.equalsIgnoreCase("jpeg")) {

                    bmpTojpg(sourceFile, destFile);

                } else {

                    Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

                    destFile = sourceFile;

                }

            }

        }

        return destFile;

    }

    /**
     * bmp格式的图片转换为jpg
     * 
     * 需求：统一格式化为jpg格式
     * 
     * 适用范围：格式化图片
     * 
     * 作者:http://blog.csdn.net/yw2325/article/details/639150
     * 
     * 时间:2014.01.15
     * 
     * @param file
     * @param dstFile 
     */
    public static void bmpTojpg(String file, String dstFile) {

        try {

            FileInputStream in = new FileInputStream(file);

            Image TheImage = read(in);

            int wideth = TheImage.getWidth(null);

            int height = TheImage.getHeight(null);

            BufferedImage tag = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(TheImage, 0, 0, wideth, height, null);

            FileOutputStream out = new FileOutputStream(dstFile);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

            encoder.encode(tag);

            out.close();

            File f = new File(file);

            if (f.exists()) {

                f.delete();

            }

        } catch (Exception ex) {

            Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public static int constructInt(byte[] in, int offset) {
        int ret = ((int) in[offset + 3] & 0xff);
        ret = (ret << 8) | ((int) in[offset + 2] & 0xff);
        ret = (ret << 8) | ((int) in[offset + 1] & 0xff);
        ret = (ret << 8) | ((int) in[offset + 0] & 0xff);
        return (ret);
    }

    public static int constructInt3(byte[] in, int offset) {
        int ret = 0xff;
        ret = (ret << 8) | ((int) in[offset + 2] & 0xff);
        ret = (ret << 8) | ((int) in[offset + 1] & 0xff);
        ret = (ret << 8) | ((int) in[offset + 0] & 0xff);
        return (ret);
    }

    public static long constructLong(byte[] in, int offset) {
        long ret = ((long) in[offset + 7] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 6] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 5] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 4] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 3] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 2] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 1] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 0] & 0xff);
        return (ret);
    }

    public static double constructDouble(byte[] in, int offset) {
        long ret = constructLong(in, offset);
        return (Double.longBitsToDouble(ret));
    }

    public static short constructShort(byte[] in, int offset) {
        short ret = (short) ((short) in[offset + 1] & 0xff);
        ret = (short) ((ret << 8) | (short) ((short) in[offset + 0] & 0xff));
        return (ret);
    }

    static class BitmapHeader {

        public int iSize, ibiSize, iWidth, iHeight, iPlanes, iBitcount, iCompression, iSizeimage, iXpm, iYpm, iClrused, iClrimp;
        // 读取bmp文件头信息

        public void read(FileInputStream fs) throws IOException {
            final int bflen = 14;
            byte bf[] = new byte[bflen];
            fs.read(bf, 0, bflen);
            final int bilen = 40;
            byte bi[] = new byte[bilen];
            fs.read(bi, 0, bilen);
            iSize = constructInt(bf, 2);
            ibiSize = constructInt(bi, 2);
            iWidth = constructInt(bi, 4);
            iHeight = constructInt(bi, 8);
            iPlanes = constructShort(bi, 12);
            iBitcount = constructShort(bi, 14);
            iCompression = constructInt(bi, 16);
            iSizeimage = constructInt(bi, 20);
            iXpm = constructInt(bi, 24);
            iYpm = constructInt(bi, 28);
            iClrused = constructInt(bi, 32);
            iClrimp = constructInt(bi, 36);
        }
    }

    public static Image read(FileInputStream fs) {
        try {
            BitmapHeader bh = new BitmapHeader();
            bh.read(fs);
            if (bh.iBitcount == 24) {
                return (readImage24(fs, bh));
            }
            if (bh.iBitcount == 32) {
                return (readImage32(fs, bh));
            }
            fs.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return (null);
    }
    //24位

    private static Image readImage24(FileInputStream fs, BitmapHeader bh) throws IOException {
        Image image;
        if (bh.iSizeimage == 0) {
            bh.iSizeimage = ((((bh.iWidth * bh.iBitcount) + 31) & ~31) >> 3);
            bh.iSizeimage *= bh.iHeight;
        }
        int npad = (bh.iSizeimage / bh.iHeight) - bh.iWidth * 3;
        int ndata[] = new int[bh.iHeight * bh.iWidth];
        byte brgb[] = new byte[(bh.iWidth + npad) * 3 * bh.iHeight];
        fs.read(brgb, 0, (bh.iWidth + npad) * 3 * bh.iHeight);
        int nindex = 0;
        for (int j = 0; j < bh.iHeight; j++) {
            for (int i = 0; i < bh.iWidth; i++) {
                ndata[bh.iWidth * (bh.iHeight - j - 1) + i] = constructInt3(brgb, nindex);
                nindex += 3;
            }
            nindex += npad;
        }
        image = Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(bh.iWidth, bh.iHeight, ndata, 0, bh.iWidth));
        fs.close();
        return (image);
    }
    //32位

    private static Image readImage32(FileInputStream fs, BitmapHeader bh) throws IOException {
        Image image;
        int ndata[] = new int[bh.iHeight * bh.iWidth];
        byte brgb[] = new byte[bh.iWidth * 4 * bh.iHeight];
        fs.read(brgb, 0, bh.iWidth * 4 * bh.iHeight);
        int nindex = 0;
        for (int j = 0; j < bh.iHeight; j++) {
            for (int i = 0; i < bh.iWidth; i++) {
                ndata[bh.iWidth * (bh.iHeight - j - 1) + i] = constructInt3(brgb, nindex);
                nindex += 4;
            }
        }
        image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(bh.iWidth, bh.iHeight, ndata, 0, bh.iWidth));
        fs.close();
        return (image);
    }

    /**
     * 格式化文件后缀
     * @param sourceFile
     * @param format
     * @return 
     */
    public static String formatFileSuffix(String sourceFile, String format) {

        String result = null;

        try {
//        	System.out.println("sourceFile:"+sourceFile);
            File file = new File(sourceFile);

            if (file.exists()) {

                String path = file.getPath();
            
                String filename = file.getName();
            
                path = path.substring(0, path.indexOf(filename));
            
                //不是目标格式的文件
                if (!filename.toLowerCase().contains(format.toLowerCase())) {


                    filename = filename.substring(0, filename.lastIndexOf(".") + 1) + format;

                    result = path + filename;

                } else {//已经是目标格式的文件

                    result = sourceFile;

                }

            } else {

                result = sourceFile;

            }

        } catch (Exception ex) {

            Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

            result = sourceFile;

        }

        return result;

    }

    /**
     * 合成任务分享图片
     * @param demoPath 模版图片地址
     * @param user_name 用户名
     * @param imagePath 用户头像地址
     * @param integral 奖励拍点
     * @param targetPath 合成图片地址
     * @return 
     */
    public static boolean combineTaskShareImage(String demoPath, String user_name, String imagePath, String integral, String appDownImagePath, String targetPath) {

        boolean flag = false;

        OutputStream os = null;

        try {

            Image srcImg = ImageIO.read(new File(demoPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            g.setFont(new Font("宋体", 1, 40));
            g.setColor(new Color(255, 255, 255));

            int width = 40 * user_name.length();
            int height = 40;

            int x = 0;
            int y = 0;

            x = 270 - width / 2;
            y = 120 + 3 * height / 8;

            //将用户名加入到模版中
            g.drawString(user_name, x, y);


            //将用户头像加入到模版中
            //用户头像
            ImageIcon userImage = new ImageIcon(imagePath);

            // 得到Image对象
            Image img = userImage.getImage();

            //
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    1f));

            // 用户头像位置
            g.drawImage(img, 197, 168, null);

            //将该用户的推广二维码加入到模版中
            ImageIcon qcodeImage = new ImageIcon(appDownImagePath);

            Image qcode = qcodeImage.getImage();

            //
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    1f));

            // 二维码位置
            g.drawImage(qcode, 284, 742, null);

            g.setFont(new Font("impact", 1, 100));
            g.setColor(new Color(255, 0, 0));

            width = 100 * integral.length();
            height = 100;

            x = 270 - width * 3 / 10;
            y = 470 + 3 * height / 8;

            //将拍点加入到模版中
            g.drawString(integral, x, y);


            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targetPath);

            //生成jpg格式的图片
            ImageIO.write(buffImg, "png", os);

            flag = true;

        } catch (Exception ex) {

            Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            try {

                if (null != os) {

                    os.close();

                }

            } catch (Exception ex) {

                Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

        return flag;

    }

    /**
     * 合成晒图分享图片
     * @param demoPath 模版路径
     * @param userImagePath 用户头像地址
     * @param userName 用户名
     * @param date 晒图审核通过时间
     * @param showMark 晒图感言
     * @param combineImagePath 晒图9宫格图片
     * @param target 目标图片路径
     * @return 
     */
    public static boolean combineShowPhotosShareImage(String demoPath, String userImagePath, String userName, String date,
            String showMark, String integral, String[] combineImagePath, String appDownImagePath, String targetPath) {

        boolean flag = false;

        OutputStream os = null;

        try {

            Image srcImg = ImageIO.read(new File(demoPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            g.setFont(new Font("impact", 1, 80));
            g.setColor(new Color(255, 0, 0));

            int width = 80 * integral.length();
            int height = 80;

            int x = 0;
            int y = 0;

            x = 270 - width * 3 / 10;
            y = 265 + 3 * height / 8;

            //将奖励拍点加入到模版中
            g.drawString(integral, x, y);

            //将用户头像加入到模版中
            //用户头像
            ImageIcon image = new ImageIcon(userImagePath);

            // 得到Image对象
            Image img = image.getImage();

            //
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    1f));

            // 用户头像位置
            g.drawImage(img, 60, 610, null);

            int count = 0;

            //将产品图片以及用户晒图信息加入到模版中
            for (String combinePath : combineImagePath) {


                //将用户晒图的图片加入到模版中
                image = new ImageIcon(combinePath);

                img = image.getImage();


                //
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                        1f));

                // 晒图
                g.drawImage(img, 57 + (count % 3) * 143, 300 + (count / 3) * 143, null);

                count++;


            }

            //将该用户的推广二维码加入到模版中
            ImageIcon qcodeImage = new ImageIcon(appDownImagePath);

            Image qcode = qcodeImage.getImage();

            //
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    1f));

            // 二维码位置
            g.drawImage(qcode, 283, 830, null);

            g.setFont(new Font("宋体", 1, 20));
            g.setColor(new Color(0, 0, 0));

            width = 20 * userName.length();
            height = 20;

            x = 170;
            y = 620 + height * 3 / 8;

            //将用户信息加入到模版中
            g.drawString(userName, x, y);

            g.setFont(new Font("宋体", 0, 14));
            //拍中时间
            g.drawString(date, x, y + 20);

            g.setFont(new Font("宋体", 0, 12));

            count = 0;

            //感言换行处理
            while (count <= showMark.length() / 23) {

                int max = (count + 1) * 23;

                max = max > showMark.length() ? showMark.length() : max;

                g.drawString(showMark.substring(count * 23, max), x, y + 44 + count * 15);

                count++;

            }

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targetPath);

            //生成jpg格式的图片
            ImageIO.write(buffImg, "png", os);

            flag = true;

        } catch (Exception ex) {

            Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            try {

                if (null != os) {

                    os.close();

                }

            } catch (Exception ex) {

                Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

        return flag;

    }

    /**
     * 合成拍中商品后的分享图片
     * 
     * 需求：生成分享图片
     * 
     * 适用范围：拍中的产品的分享图片
     * 
     * 作者:Dsmart
     * 
     * 时间:2014.01.25
     * 
     * @param demoPath 模版路径
     * @param imagePath 用户头像路径
     * @param user_name 用户名称
     * @param productImagePath 产品图片路径
     * @param productName 产品名称
     * @param buyPrice 成交价格
     * @param marketPrice 市场价格
     * @param appDownImagePath 二维码下载地址
     * @param targetPath 合成图地址
     * @return 
     */
    public static boolean combineGainProductImage(String demoPath, String imagePath, String user_name, String productImagePath,
            String productName, String buyPrice, String marketPrice, String appDownImagePath, String targetPath) {

        boolean flag = false;

        OutputStream os = null;

        try {

            Image srcImg = ImageIO.read(new File(demoPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            //将用户头像加入到模版中
            //用户头像
            ImageIcon userImage = new ImageIcon(imagePath);

            // 得到Image对象
            Image img = userImage.getImage();

            //
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    1f));

            // 用户头像位置
            g.drawImage(img, 220, 196, null);


            g.setFont(new Font("宋体", 1, 16));
            g.setColor(new Color(0, 0, 0));

            int width = 16 * user_name.length();
            int height = 16;

            int x = 0;
            int y = 0;

            x = 270 - width / 2;
            y = 326 + 3 * height / 8;

            //将用户名加入到模版中
            g.drawString(user_name, x, y);


            //将产品图片加入到模版中
            //产品图片
            ImageIcon productImage = new ImageIcon(productImagePath);

            // 得到Image对象
            Image productImg = productImage.getImage();

            //
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    1f));

            // 用户头像位置
            g.drawImage(productImg, 117, 387, null);

            if (productName.length() > 20) {

                productName = productName.substring(0, 20) + "...";

            }

            //将产品名称添加到模版中
            g.setFont(new Font("宋体", 1, 20));
            g.setColor(new Color(102, 102, 102));

            width = 20 * productName.length();
            height = 20;

            x = 270 - width * 1 / 2;
            y = 720 + 3 * height / 8;

            //产品名称加入到模版中
            g.drawString(productName, x, y);

            String title = "拍中价格:￥";

            //将成交价格加入到模版中
            g.setFont(new Font("宋体", 0, 20));
            g.setColor(new Color(102, 102, 102));

            width = 20 * title.length();
            height = 20;

            x = 135 - width * 3 / 10;
            y = 770 + 3 * height / 8;

            //将拍点加入到模版中
            g.drawString(title, x, y);

            g.setFont(new Font("impact", 1, 35));
            g.setColor(new Color(255, 0, 0));

            width = 35 * buyPrice.length();
            height = 35;

            x = 270 - width * 3 / 10;
            y = 770 + 3 * height / 8;

            //将拍点加入到模版中
            g.drawString(buyPrice, x, y);


            //将市场价格加入到模版中
            g.setFont(new Font("宋体", 0, 20));
            g.setColor(new Color(102, 102, 102));

            width = 20 * marketPrice.length();
            height = 20;

            x = 405 - width * 3 / 10;
            y = 770 + 3 * height / 8;

            //将拍点加入到模版中
            g.drawString(marketPrice, x, y);



            //将该用户的推广二维码加入到模版中
            ImageIcon qcodeImage = new ImageIcon(appDownImagePath);

            Image qcode = qcodeImage.getImage();

            //
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    1f));

            // 二维码位置
            g.drawImage(qcode, 138, 804, null);



            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targetPath);

            //生成jpg格式的图片
            ImageIO.write(buffImg, "png", os);

            flag = true;

        } catch (Exception ex) {

            Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            try {

                if (null != os) {

                    os.close();

                }

            } catch (Exception ex) {

                Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

            }

        }


        return flag;

    }

    /**
     * 生成刮刮卡中奖后的分享图片
     * 
     * 需求：用户刮完奖后进入图片分享界面
     * 
     * 适用范围：刮奖后进入图片分享界面
     * 
     * 作者:Dsmart
     * 
     * 时间:2014.04.28
     * 
     * @param demoPath
     * @param imagePath 298*298
     * @param title
     * @param reward
     * @param appDownImagePath
     * @param targetPath
     * @return 
     */
    public static boolean combineScratchShareImage(String demoPath, String imagePath, String title, String reward, String appDownImagePath, String targetPath) {

        boolean flag = false;

        OutputStream os = null;

        try {

            Image srcImg = ImageIO.read(new File(demoPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            int width = 0;

            int height = 0;

            int x = 0;

            int y = 0;

            //标题非空的时候
            if (title != null) {

                g.setFont(new Font("楷体", 1, 20));
                g.setColor(new Color(0, 0, 0));

                width = 20 * title.length();
                height = 20;

                x = 0;
                y = 0;

                x = 275 - width / 2;
                y = 700 + 3 * height / 8;

                //将用户名加入到模版中
                g.drawString(title, x, y);

            }


            //产品图片非空的时候
            if (imagePath != null) {

                //将用户头像加入到模版中
                //用户头像
                ImageIcon userImage = new ImageIcon(imagePath);

                // 得到Image对象
                Image img = userImage.getImage();

                //
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                        1f));

                // 用户头像位置
                g.drawImage(img, 128, 369, null);

            }

            //将该用户的推广二维码加入到模版中
            ImageIcon qcodeImage = new ImageIcon(appDownImagePath);

            Image qcode = qcodeImage.getImage();

            //
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    1f));

            if (imagePath == null) {

                x = 362;

                y = 844;

            } else {

                x = 369;

                y = 847;

            }

            // 二维码位置
            g.drawImage(qcode, x, y, null);

            if (reward != null) {

                g.setFont(new Font("隶书", 3, 30));
                g.setColor(new Color(255, 0, 0));

                width = 30 * reward.length();
                height = 30;

                x = 270 - width * 3 / 10;
                y = 735 + 3 * height / 8;

                //将拍点加入到模版中
                g.drawString(reward, x, y);

            }


            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targetPath);

            //生成jpg格式的图片
            ImageIO.write(buffImg, "png", os);

            flag = true;

        } catch (Exception ex) {

            Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            try {

                if (null != os) {

                    os.close();

                }

            } catch (Exception ex) {

                Logger.getLogger(PicTool.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

        return flag;

    }

    public static void main(String[] as) throws SQLException {

        String path = "g://test/scratch//";

        String demoPath = path + "scratch_thanks_demo.jpg";

        String imagePath = null;

        String title = null;

        String reward = null;

        String appPath = path + "100.png";

        String targetPath = path + "combine_thanks.png";

        PicTool.combineScratchShareImage(demoPath, imagePath, title, reward, appPath, targetPath);


    }
}
