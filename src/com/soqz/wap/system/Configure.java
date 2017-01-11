package com.soqz.wap.system;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Configure {

    //产品图片网络相对地址
    private final static String PRODUCTIMAGEURL = "productImgUrl";
    //用户头像存储的绝对地址
    private final static String USERIMAGEPATH = "userImgPath";
    //用户头像存储的相对地址
    private final static String USERIMAGEURL = "userImgUrl";
    //用户反馈存储绝对地址
    private final static String SUGGESTIMAGEPATH = "suggestImgPath";
    //用户反馈存储相对地址
    private final static String SUGGESTIMAGEURL = "suggestImgUrl";
    //用户唯一二维码相对地址
    private final static String QRCODEURL = "qrcodeImgUrl";
    //用户唯一二维码绝对地址
    private final static String QRCODEPATH = "qrcodeImgPath";
    //拼接用下载地址
    private final static String DOWNLOAD = "downloadUrl";
    //广告图片网络相对地址 
    private final static String ADVERTISINGURL = "advertisingUrl";
    //我趣商品查询对接地址
    private final static String WOOQUURL = "wooquUrl";
    //分享用logo图片相对地址
    private final static String LOGOURL = "logoUrl";
    //服务器主域名
    private final static String MAINURL = "mainUrl";
    //我趣商品物流备货确认
    private final static String WOOQUMALLURL = "wooquMallUrl";
    //我趣商品物流识别码
    private final static String TOKEN = "token";
    //积分商城历史兑换分享地址
    private final static String MALLURL = "mallUrl";
    //五折分享地址
    private final static String BUYINGSPREEURL = "buyingSpreeUrl";
    //员工头像绝对地址
    private final static String STAFFIMGPATH = "staffImgPath";
    //员工头像相对地址
    private final static String STAFFIMGURL = "staffImgUrl";
    //移动应用物理地址
    private final static String APPPATH = "app";
    //移动应用网络相对地址
    private final static String APPURL = "appUrl";
    //食品图片物理地址
    private final static String PRODUCTIMAGEPATH = "productImgPath";
    //聊天记录存储地址
    private final static String CHATLOGPATH = "chatlog";
    
    //安装员工下载地址
//    private final static String Staffdownload
    private final static String STAFFDOWNLOAD = "staffDownLoad";
    
    //订单扫描绝对地址
    private final static String BILLQRCODEIMGPATH = "billQrcodeImgPath"; 
    
    //订单扫描相对地址
    private final static String BILLQRCODEIMGURL = "billQrcodeImgUrl";
    

    /**
     * 私有化构造方法
     */
    private Configure() {
    }

    /**
     * 获取对应的键值
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
     * 食品图片物理地址
     *
     * @return
     */
    public static String getProductImagePath() {

        return getValue(PRODUCTIMAGEPATH);

    }

    /**
     * 移动应用存储物理地址
     *
     * @return
     */
    public static String getAppPath() {

        return getValue(APPPATH);

    }

    /**
     * 移动应用存储网络地址
     *
     * @return
     */
    public static String getAppUrl() {

        return getValue(APPURL);

    }

    /**
     * 聊天记录存储地址
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
     * 订单扫描记录地址
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
