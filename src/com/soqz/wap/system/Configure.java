package com.soqz.wap.system;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Configure {

    //��ƷͼƬ�������Ե�ַ
    private final static String PRODUCTIMAGEURL = "productImgUrl";
    //�û�ͷ���洢�ľ��Ե�ַ
    private final static String USERIMAGEPATH = "userImgPath";
    //�û�ͷ���洢�����Ե�ַ
    private final static String USERIMAGEURL = "userImgUrl";
    //�û������洢���Ե�ַ
    private final static String SUGGESTIMAGEPATH = "suggestImgPath";
    //�û������洢���Ե�ַ
    private final static String SUGGESTIMAGEURL = "suggestImgUrl";
    //�û�Ψһ��ά�����Ե�ַ
    private final static String QRCODEURL = "qrcodeImgUrl";
    //�û�Ψһ��ά�����Ե�ַ
    private final static String QRCODEPATH = "qrcodeImgPath";
    //ƴ�������ص�ַ
    private final static String DOWNLOAD = "downloadUrl";
    //����ͼƬ�������Ե�ַ
    private final static String ADVERTISINGURL = "advertisingUrl";
    //��Ȥ��Ʒ��ѯ�Խӵ�ַ
    private final static String WOOQUURL = "wooquUrl";
    //������logoͼƬ���Ե�ַ
    private final static String LOGOURL = "logoUrl";
    //������������
    private final static String MAINURL = "mainUrl";
    //��Ȥ��Ʒ��������ȷ��
    private final static String WOOQUMALLURL = "wooquMallUrl";
    //��Ȥ��Ʒ����ʶ����
    private final static String TOKEN = "token";
    //�����̳���ʷ�һ�������ַ
    private final static String MALLURL = "mallUrl";
    //���۷�����ַ
    private final static String BUYINGSPREEURL = "buyingSpreeUrl";
    //Ա��ͷ�����Ե�ַ
    private final static String STAFFIMGPATH = "staffImgPath";
    //Ա��ͷ�����Ե�ַ
    private final static String STAFFIMGURL = "staffImgUrl";
    //�ƶ�Ӧ��������ַ
    private final static String APPPATH = "app";
    //�ƶ�Ӧ���������Ե�ַ
    private final static String APPURL = "appUrl";
    //ʳƷͼƬ������ַ
    private final static String PRODUCTIMAGEPATH = "productImgPath";
    //������¼�洢��ַ
    private final static String CHATLOGPATH = "chatlog";

    //��װԱ�����ص�ַ
//    private final static String Staffdownload
    private final static String STAFFDOWNLOAD = "staffDownLoad";

    //����ɨ�����Ե�ַ
    private final static String BILLQRCODEIMGPATH = "billQrcodeImgPath";

    //����ɨ�����Ե�ַ
    private final static String BILLQRCODEIMGURL = "billQrcodeImgUrl";


    /**
     * ˽�л����췽��
     */
    private Configure() {
    }

    /**
     * ��ȡ��Ӧ�ļ�ֵ
     *
     * @param key
     * @return
     */
    private static String getValue(String key) {

        String result = null;

        try {

            result = ConFile.getString(key);

        } catch (Exception ex) {

            Logger.getLogger(Configure.class.getName()).log(Level.SEVERE, null,
                    ex);

        }

        return result;

    }

    public static String getProductimageurl() {
        return getValue(PRODUCTIMAGEURL);
    }

    public static String getUserimagepath() {
        return getValue(USERIMAGEPATH);
    }

    public static String getUserimageurl() {
        return getValue(USERIMAGEURL);
    }

    public static String getSuggestimagepath() {
        return getValue(SUGGESTIMAGEPATH);
    }

    public static String getSuggestimageurl() {
        return getValue(SUGGESTIMAGEURL);
    }

    public static String getQrcodeurl() {
        return getValue(QRCODEURL);
    }

    public static String getQrcodepath() {
        return getValue(QRCODEPATH);
    }

    public static String getDownload() {
        return getValue(DOWNLOAD);
    }

    public static String getAdvertisingurl() {
        return getValue(ADVERTISINGURL);
    }

    public static String getWooquurl() {
        return getValue(WOOQUURL);
    }

    public static String getLogourl() {
        return getValue(LOGOURL);
    }

    public static String getMainurl() {
        return getValue(MAINURL);
    }

    public static String getWooqumallurl() {
        return getValue(WOOQUMALLURL);
    }

    public static String getToken() {
        return getValue(TOKEN);
    }

    public static String getMallurl() {
        return getValue(MALLURL);
    }

    public static String getBuyingspreeurl() {
        return getValue(BUYINGSPREEURL);
    }

    public static String getStaffimgpath() {
        return getValue(STAFFIMGPATH);
    }

    public static String getStaffimgurl() {
        return getValue(STAFFIMGURL);
    }

    /**
     * ʳƷͼƬ������ַ
     *
     * @return
     */
    public static String getProductImagePath() {

        return getValue(PRODUCTIMAGEPATH);

    }

    /**
     * �ƶ�Ӧ�ô洢������ַ
     *
     * @return
     */
    public static String getAppPath() {

        return getValue(APPPATH);

    }

    /**
     * �ƶ�Ӧ�ô洢������ַ
     *
     * @return
     */
    public static String getAppUrl() {

        return getValue(APPURL);

    }

    /**
     * ������¼�洢��ַ
     *
     * @return
     */
    public static String getChatLogPath() {

        return getValue(CHATLOGPATH);

    }

    public static String getStaffdownload() {
    	return getValue(STAFFDOWNLOAD);

	}

    /**
     * ����ɨ����¼��ַ
     */
	public static String getBillqrcodeimgpath() {
    	return getValue(BILLQRCODEIMGPATH);
	}

	public static String getBillqrcodeimgurl() {
		return getValue(BILLQRCODEIMGURL);
	}

	public static void main(String[] args) {
//		String info=Configure.getProductimageurl();
        String info = Configure.getWooquurl();
        System.out.println(info);
    }

}
