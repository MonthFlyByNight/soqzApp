package com.soqz.wap.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.imageio.ImageReadParam;
import javax.imageio.stream.ImageInputStream;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

/**
 *
 * @author Dsmart
 */
public class PicResize {

    /**
     *imagePreTreatment ͼƬԤ����,���ΪRGB��ɫ�ռ��ͼƬ��ֱ����������ΪCMYK��ɫ�ռ��ͼƬ��ת��������� 
     *@param  filename  type:String   �ļ���·��
     */
    public void imagePreTreatment(String filename) throws IOException {
        File file = new File(filename);
        ImageInputStream input = ImageIO.createImageInputStream(file);
        Iterator readers = ImageIO.getImageReaders(input);
        if (readers == null || !readers.hasNext()) {
            throw new RuntimeException(" No ImageReaders found");
        }

        ImageReader reader = (ImageReader) readers.next();
        reader.setInput(input);


        String format = reader.getFormatName();
        BufferedImage image;

        if ("JPEG".equalsIgnoreCase(format) || "JPG".equalsIgnoreCase(format)) {

            try {
                // ���Զ�ȡͼƬ (������ɫ��ת��).   
                image = reader.read(0);

            } catch (IIOException e) {
                // ��ȡRaster (û����ɫ��ת��).   
                Raster raster = reader.readRaster(0, null);
                image = createJPEG4(raster);
            }

            image.getGraphics().drawImage(image, 0, 0, null);
            FileOutputStream out = new FileOutputStream(file);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            image.flush();
            input.flush();
            out.flush();
            out.close();
            input.close();

        }

    }

    /**
     * creatJPEG4 ����ɫ�ռ�ΪCMYK��ͼƬ���ػ�ΪRGB��ɫ�ռ�JPEG��ʽ��ͼƬ
     * 
     */
    private static BufferedImage createJPEG4(Raster raster) {
        int w = raster.getWidth();
        int h = raster.getHeight();
        byte[] rgb = new byte[w * h * 3];



        //��ɫ�ռ�ת��        
        float[] Y = raster.getSamples(0, 0, w, h, 0, (float[]) null);
        float[] Cb = raster.getSamples(0, 0, w, h, 1, (float[]) null);
        float[] Cr = raster.getSamples(0, 0, w, h, 2, (float[]) null);
        float[] K = raster.getSamples(0, 0, w, h, 3, (float[]) null);

        for (int i = 0, imax = Y.length, base = 0; i < imax; i++, base += 3) {
            float k = 220 - K[i], y = 255 - Y[i], cb = 255 - Cb[i],
                    cr = 255 - Cr[i];

            double val = y + 1.402 * (cr - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
                    : (byte) (val + 0.5);

            val = y - 0.34414 * (cb - 128) - 0.71414 * (cr - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base + 1] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
                    : (byte) (val + 0.5);

            val = y + 1.772 * (cb - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base + 2] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
                    : (byte) (val + 0.5);
        }


        raster = Raster.createInterleavedRaster(new DataBufferByte(rgb, rgb.length), w, h, w * 3, 3, new int[]{0, 1, 2}, null);

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ColorModel cm = new ComponentColorModel(cs, false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        return new BufferedImage(cm, (WritableRaster) raster, true, null);
    }

    /**
     * cutImage �ü�ͼƬ
     * @param oldFile type;String ԭʼͼƬ·��
     * @param newFile type:String ��ͼƬ·��
     * @param x       type:double �и���ʼ��ĺ�����
     * @param y       type:double �и���ʼ���������
     * @param width   type:double �и��
     * @param height  type:double �и�߶�
     */
    public void cutImage(String oldFile, String newFile, double x, double y, double width, double height) throws Exception {


        try {

            //ԭͼƬ�ļ�
            File srcfile = new File(oldFile);

            //ԭͼƬ�����ڵ�ʱ�� �˳�
            if (!srcfile.exists()) {

                return;

            }

            // ȡ��ͼƬ������ 

            Iterator readers = ImageIO.getImageReadersByFormatName("jpg");

            ImageReader reader = (ImageReader) readers.next();



            // ȡ��ͼƬ������ 

            InputStream source = new FileInputStream(oldFile);

            ImageInputStream iis = ImageIO.createImageInputStream(source);

            reader.setInput(iis, true);

            // �и�ͼƬ���� 

            ImageReadParam param = reader.getDefaultReadParam();

            Rectangle rect = new Rectangle();

            rect.setRect(x, y, width, height);

            param.setSourceRegion(rect);

            BufferedImage bi = reader.read(0, param);

            ImageIO.write(bi, "jpg", new File(newFile));

            bi.flush();

            iis.close();

            source.close();

        } catch (Exception ex) {

            Logger.getLogger(PicResize.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /** 
     * reduceImage  ѹ��ͼƬ
     * @param oldFile    type:String   ԭʼͼƬ·��
     * @param suffix     type:String   ��ͼƬ����׺
     * @param widthdist  type:int      ��ͼƬ����
     * @param heightdist type:int      ��ͼƬ�߶�
     * @param percentage type:boolean  �Ƿ�ͬ����ѹ��
     *   
     */
    public void reduceImage(String oldFile, String suffix, int widthdist, int heightdist, boolean percentage) {
        String newFile = "";

        try {

            File srcfile = new File(oldFile);

            if (!srcfile.exists()) {

                return;

            }

            imagePreTreatment(oldFile);

            Image src = null;

            try {

                src = javax.imageio.ImageIO.read(srcfile);

            } catch (Exception ex) {

                Logger.getLogger(PicResize.class.getName()).log(Level.SEVERE, null, ex);

            }


            if (percentage) {//ͬ����ѹ��

                double rate = 0.0;

                if (src.getWidth(null) >= src.getHeight(null)) {

                    rate = ((double) src.getHeight(null)) / (double) heightdist;

                } else {

                    rate = ((double) src.getWidth(null)) / (double) widthdist;

                }

                // Ϊ�ȱ�ѹ����������Ŀ��
                int new_w = (int) (((double) src.getWidth(null)) / rate);

                int new_h = (int) (((double) src.getHeight(null)) / rate);

                // �趨���
                BufferedImage tag = new BufferedImage(new_w, new_h,
                        BufferedImage.TYPE_INT_RGB);

                // �趨�ļ���չ��
                String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.'));

                if (" ".equals(suffix)) {

                    newFile = filePrex + ".jpg";

                } else {

                    newFile = filePrex + suffix + ".jpg";//oldFile.substring(filePrex.length());

                }
                // ����ͼƬ
                // ���ַ���,Ч������������ͬ,Ч�ʲ��

                //tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_AREA_AVERAGING), 0, 0, null);
                tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(newFile);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                tag.flush();
                src.flush();
                out.flush();
                out.close();
            } else {//��ͬ����ѹ��
                // �趨���
                BufferedImage tag = new BufferedImage(widthdist, heightdist,
                        BufferedImage.TYPE_INT_RGB);

                // �趨�ļ���չ��
                String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.'));
                newFile = filePrex + suffix + oldFile.substring(filePrex.length());
                // ����ͼƬ
                // ���ַ���,Ч������������ͬ,�ڶ���Ч�ʱȵ�һ�ָ�,Լһ��
                // tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);
                tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_AREA_AVERAGING), 0, 0, null);
                FileOutputStream out = new FileOutputStream(newFile);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                tag.flush();
                src.flush();
                out.flush();
                out.close();
            }
            if (!oldFile.substring(oldFile.lastIndexOf('.') + 1).toLowerCase().equals("jpg") && !oldFile.substring(oldFile.lastIndexOf('.') + 1).toLowerCase().equals("jpeg")) {
//                srcfile.delete();//ɾ����jpg��ʽ���ļ��ļ�                
            }
        } catch (IOException ex) {

            Logger.getLogger(PicResize.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     * ר�����ڴ����ƷͼƬ�ķ���
     * 
     * ���󣺴����ƷͼƬ�ķ��� �����Ʒͼ ѹ����800*800  ��ͼ ѹ������*800
     * 
     * ���÷�Χ����ƷͼƬ���ϴ�
     * 
     * ����:Dsmart
     * 
     * ʱ�䣺2014.06.13
     * 
     * @param oldFile
     * @param suffix
     * @param widthdist
     * @param heightdist
     * @param percentage
     * @param map 
     */
    public void reduceImage(String oldFile, String suffix, int widthdist, Map map) {

        String newFile = null;

        try {

            File srcfile = new File(oldFile);

            if (!srcfile.exists()) {

                return;

            }

            imagePreTreatment(oldFile);

            Image src = null;

            try {

                src = javax.imageio.ImageIO.read(srcfile);

            } catch (Exception ex) {

                Logger.getLogger(PicResize.class.getName()).log(Level.SEVERE, null, ex);

            }

            //ͼƬ�Ŀ��
            int width = src.getWidth(null);

            //ͼƬ�ĳ���
            int height = src.getHeight(null);

            //ͼƬ����
            //0:�����ƷͼƬ
            //1����Ʒ��ͼ
            int type = 1;

            String typeName = "";

            if (true) {//ͬ����ѹ��

                double rate = 0.0;

                //��ͼ
                if (height > (width * 2)) {

                    type = 2;

                    typeName = "longimage";

                } else {//��Ʒͼ

                    type = 1;

                    typeName = "productimage";

                }

                //ѹ������
                rate = ((double) src.getWidth(null)) / (double) widthdist;

                // Ϊ�ȱ�ѹ����������Ŀ��
                int new_w = (int) (((double) src.getWidth(null)) / rate);

                int new_h = height;

                //�����ƷͼƬ
                if (type == 1) {

                    height = (int) (((double) src.getHeight(null)) / rate);

                }

                // �趨���
                BufferedImage tag = new BufferedImage(new_w, new_h,
                        BufferedImage.TYPE_INT_RGB);

                // �趨�ļ���չ��
                String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.'));

                if (" ".equals(suffix)) {

                    newFile = filePrex + ".jpg";

                } else {

                    newFile = filePrex + suffix + ".jpg";//oldFile.substring(filePrex.length());

                }
                // ����ͼƬ
                // ���ַ���,Ч������������ͬ,Ч�ʲ��

                //tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_AREA_AVERAGING), 0, 0, null);

                tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);

                FileOutputStream out = new FileOutputStream(newFile);

                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

                encoder.encode(tag);

                tag.flush();

                src.flush();

                out.flush();

                out.close();



                if (newFile != null) {

                    map.put(typeName, newFile);

                }
            }

        } catch (IOException ex) {

            Logger.getLogger(PicResize.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void reduceImage(String oldFile, String suffix, int widthdist, int heightdist, boolean percentage, int minSize) {
        String newFile = "";
        try {
            File srcfile = new File(oldFile);
            imagePreTreatment(oldFile);
            if (!srcfile.exists()) {
                return;
            }

            Image src = null;
            try {
                src = javax.imageio.ImageIO.read(srcfile);
            } catch (Exception e) {
                System.out.println(e);
            }


            if (percentage) {//ͬ����ѹ��
                double rate = 0.0;
                if (src.getWidth(null) >= src.getHeight(null)) {
                    if (src.getWidth(null) > minSize) {
                        rate = ((double) src.getHeight(null)) / (double) heightdist;
                    } else {
                        rate = 1.0;
                    }
                } else {
                    if (src.getHeight(null) > minSize) {
                        rate = ((double) src.getWidth(null)) / (double) widthdist;
                    } else {
                        rate = 1.0;
                    }
                }
                // Ϊ�ȱ�ѹ����������Ŀ��
                int new_w = (int) (((double) src.getWidth(null)) / rate);
                int new_h = (int) (((double) src.getHeight(null)) / rate);

                // �趨���
                BufferedImage tag = new BufferedImage(new_w, new_h,
                        BufferedImage.TYPE_INT_RGB);

                // �趨�ļ���չ��
                String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.'));
                if (" ".equals(suffix)) {
                    newFile = filePrex + ".jpg";
                } else {
                    newFile = filePrex + suffix + ".jpg";//oldFile.substring(filePrex.length());
                }
                // ����ͼƬ
                // ���ַ���,Ч������������ͬ,Ч�ʲ��
                tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
                // tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_AREA_AVERAGING), 0, 0, null);
                FileOutputStream out = new FileOutputStream(newFile);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                out.flush();
                out.close();
            } else {//��ͬ����ѹ��
                // �趨���
                BufferedImage tag = new BufferedImage(widthdist, heightdist,
                        BufferedImage.TYPE_INT_RGB);

                // �趨�ļ���չ��
                String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.'));
                newFile = filePrex + suffix + oldFile.substring(filePrex.length());
                // ����ͼƬ
                // ���ַ���,Ч������������ͬ,�ڶ���Ч�ʱȵ�һ�ָ�,Լһ��
                tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);
                //tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_AREA_AVERAGING), 0, 0, null);
                FileOutputStream out = new FileOutputStream(newFile);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                out.flush();
                out.close();
            }
            if (!oldFile.substring(oldFile.lastIndexOf('.') + 1).toLowerCase().equals("jpg") && !oldFile.substring(oldFile.lastIndexOf('.') + 1).toLowerCase().equals("jpeg")) {
                srcfile.delete();//ɾ����jpg��ʽ���ļ��ļ�
                System.out.println("ɾ��" + srcfile.getAbsolutePath());
            }
        } catch (IOException ex) {
            System.out.println("ͼƬѹ����" + ex.toString());
        }
    }

    public void deleteImage(String path) throws Exception {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return;
            } else {
                file.delete();
            }
        } catch (Exception e) {
            System.out.println("ͼƬɾ����" + e.toString());
        }
    }

    public static void main(String[] args) throws Exception {

//        String appPath = "g:\\test\\test.jpg";
    	  String appPath = "D:/FTP/20.jpg";

        String targerPath = "D:\\FTP\\test_800.jpg";

        String cutPath = "D:\\FTP\\test_800_cut.jpg";

        //String appDownImagePath = ToolKits.formatFileSuffix(ToolKits.getNewFilePath(appPath, "_100"), "jpg");

        PicTool.convert(appPath, "jpg");

        PicResize picResize = new PicResize();

        picResize.reduceImage(appPath, "_800", 800, 800, true);

        picResize.cutImage(targerPath, cutPath, 0, 0, 800, 800);
//        File srcfile = new File(appPath);
//        System.out.println(appPath);
    }
}
