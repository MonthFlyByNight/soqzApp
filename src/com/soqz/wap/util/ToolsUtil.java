package com.soqz.wap.util;

import java.io.File;
import java.io.UnsupportedEncodingException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.soqz.wap.soqzservlet.process.ISoqzProcess;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ToolsUtil {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(ToolsUtil.class.getName());

//    private static ISoqzProcess iResultProcess = null;

    /**
     * ���������
     *
     * ���÷�Χ��ͨ��
     *
     * ʱ��:2014-06-16
     *
     * ����:HMH parameter i:��ȡλ��
     */
    public static String randomNum(int num) {

        Random rand = new Random();

        String randStr = "";

        for (int i = 0; i < num; i++) {

            randStr += rand.nextInt(10);

        }

        return randStr;
    }

    /**
     * �ļ�ӳ�䴦��
     *
     * ���÷�Χ:�ֻ����ݴ���
     *
     * ʱ��:2014-06-19
     *
     * ����:HMH
     *
     * @param key
     * @return
     */
//    public static ISoqzProcess processGroup(String key) {
//        try {
//            iResultProcess = (ISoqzProcess) Class.forName(key).newInstance();
//        } catch (Exception e) {
//            // TODO: handle exception
//            log.info("No Found Methods" + e);
//        }
//        return iResultProcess;
//    }

    /**
     * ��ȡ��ǰϵͳ���ں�ϵͳʱ�� 0000-00-00 00:00:00
     *
     * ���÷�Χ:ʱ���ȡ
     *
     * ���ߣ�HMH
     *
     * ʱ�䣺2014-06-19
     *
     * @return
     */
    public static String getDayTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formater.setLenient(false);
        String datetime = formater.format(calendar.getTime());
        return datetime;
    }

    /**
     * ��ȡ��ǰϵͳ���� 0000-00-00
     *
     * ���÷�Χ:ʱ���ȡ
     *
     * ���ߣ�HMH
     *
     * ʱ�䣺2014-06-19
     *
     * @param date �������
     * @return
     */
    public static String getDay(int date) {

        Calendar calendar = Calendar.getInstance();

        //ָ���������
        calendar.add(calendar.DATE, date);
        //ָ����ʽ
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        formater.setLenient(false);

        String datetime = formater.format(calendar.getTime());

        return datetime;
    }

    /**
     * ����ϵͳ������ʱ��
     *
     * @return
     */
    public static Long getCurrentTimeMillis() {

        return System.currentTimeMillis();

    }

    /**
     * ����limit������ͼƬ
     *
     * �����û�ɹͼʱ ���ֻ�ܱ������µ�5��ͼƬ
     *
     * ���÷�Χ����Ҫ����ͼƬ����
     *
     * ����:Dsmart
     *
     * ʱ��:2013.12.15
     *
     * @param images ����ͼƬ���շָ���ƴ����ɵ��ַ���
     * @param separator �ָ���
     * @param path ͼƬ��Ŀ¼
     * @param limit ���Ƶ�����
     * @return
     */
    public static String saveLimitImages(String images, String separator, String path, Integer limit) {

        String result = null;

        if (images != null) {

            limit = limit == null ? 5 : limit;

            //ͼƬ����
            String[] imgs = images.split(separator);

            //ͼƬ����������������ʱ
            if (imgs.length <= limit) {

                //����ԭ����
                result = images;

            } else {//ͼƬ������������������ʱ��

                StringBuilder saveimgs = new StringBuilder("");

                for (int i = 0; i < imgs.length; i++) {

                    if (i < limit) {//���������ڵ�ͼƬ ��ԭ�и�ʽƴ������ַ���

                        saveimgs.append(imgs[i]).append(separator);

                    } else {//ɾ��ͼƬ ������������ͼƬ

                        ToolsUtil.deleteDocs(path + imgs[i]);

                    }

                }

                result = saveimgs.toString();

            }

        } else {

            result = "";

        }

        return result;

    }

    /**
     * ��ȡ�����������ͼƬ����
     *
     * �����û�ɹͼ�������޸�ͼƬ��Ϣ ��Ҫ�����ڵ�ͼƬ�����
     *
     * ���÷�Χ�����˲�ɾ������Ч��ͼƬ
     *
     * ����:Dsmart
     *
     * ʱ��:2013.12.15
     *
     * @param images ԭ��ͼƬ����ƴ���ַ���
     * @param separator �ָ���
     * @param keys ��Ҫ������ͼƬ����
     * @param path ͼƬ��Ŀ¼
     * @return
     */
    public static String filterInvaildImages(String images, String separator, String[] keys, String path) {

        String result = "";

        //ԭ��ͼƬ���ݲ�Ϊ��ʱ  ����
        if (!StringUtil.isBlank(images)) {

            result = images;

            //Ҫ�����ͼƬ���鲻Ϊ��
            if (keys != null && keys.length > 0) {

                //��Ҫ�����
                StringBuilder save = new StringBuilder();

                for (String key : keys) {

                    //ԭ��ͼƬ�д���Ҫ�����ͼƬ
                    if (images.contains(key)) {

                        images = images.replaceAll(key, "");

                        save.append(key).append(separator);

                    }

                }

                //����Ҫ�����ͼƬ�����Ϊ��ʱ  ���ʾkeys�е�ֵԭ��������images��  �༴��ʾkeys�е�ֵ����ȷ��
                if (!StringUtil.isBlank(save.toString())) {

                    //������Ҫɾ������ͼƬ
                    images = images.replaceAll(separator + "+", separator);

                    String[] deletes = images.split(separator);

                    //ɾ��������ͼƬ
                    for (String del : deletes) {

                        ToolsUtil.deleteDocs(path + del);

                    }

                    //�����Ҫ�����ͼƬ
                    result = save.toString();

                }//����ԭͼƬ���ݲ���

            }

        }

        return result;

    }

    /**
     *
     * @param image ����ͼƬ
     * @param separator �ָ���
     * @param invalidImages ��Ҫȥ����ͼƬ
     * @param path ͼƬ�洢��Ŀ¼
     * @return
     */
    public static String saveValidImages(String image, String separator, String[] invalidImages, String path) {

        String result = null;

        //ԭ��ͼƬ�ǿ�
        if (!StringUtil.isBlank(image)) {

            if (invalidImages != null && invalidImages.length > 0) {

                for (String invalidImage : invalidImages) {

                    //ȥ������Ҫɾ����ͼƬ
                    image = image.replaceAll(invalidImage, "");

                    //����ɾ�� ��Ҫɾ����ͼƬ
                    ToolsUtil.deleteDocs(path + invalidImage);

                }

                image = image.replaceAll(separator + "+", separator);

            }

            result = image;

        } else {

            result = image;

        }

        return result;

    }

    /**
     * ɾ���ļ�
     *
     * ����ɾ�������õ��ļ�
     *
     * ���÷�Χ��ɾ���������ļ�
     *
     * ���ߣ�Dsmart
     *
     * ʱ�䣺2013.10.08
     *
     * @param path
     */
    public static void deleteDocs(String path) {

        File file = new File(path);

        if (file.exists()) {

            file.delete();

        }

    }

    /**
     * ���ݺ�������ȡʱ�� 0000-00-00 00:00:00
     *
     * ���󣺻�ȡ��ǰʱ��֮��һ��������ʱ��
     *
     * ���÷�Χ����ȡ�ض�ʱ��Ĺ涨��ʽ��ʱ��
     *
     * ���ߣ�Dsmart
     *
     * ʱ�䣺2013.07.30
     *
     * @param time
     * @return
     */
    public static String getDate(Long time) {

        String result = null;

        result = formatDate(new Date(time));

        return result;

    }

    /**
     * ��ʽ������ 0000-00-00 00:00:00
     *
     * ���󣺽����ڸ�ʽ��
     *
     * ���÷�Χ����ʽ������
     *
     * ���ߣ�Dsmart
     *
     * ʱ�䣺2013.07.30
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {

        String result = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        result = simpleDateFormat.format(date);

        return result;

    }

    /**
     * ��resultset �����ת����list list����Ԫ�ض���Ϊmap
     *
     * ���󣺽�resultsetת����list���ں��洦��
     *
     * ���÷�Χ������
     *
     * ���ߣ�Dsmart
     *
     * ʱ��:2013.09.08
     *
     * @param rs
     * @return
     */
    public static List<String> resultSetToList(ResultSet rs) {

        List list = null;

        //Map map = null;
        if (rs != null) {

            try {

                list = new ArrayList();

                //��ȡ������Ľṹ��Ϣ
                ResultSetMetaData metaData = rs.getMetaData();

                while (rs.next()) {

                    // map = new HashMap();
                    //����������
                    for (int i = 1; i < metaData.getColumnCount() + 1; i++) {

                        //ת����Сд
                        String col = metaData.getColumnLabel(i).toLowerCase();//metaData.getColumnName(i).toLowerCase();

                        list.add(rs.getString(col));

                    }

                }

            } catch (Exception ex) {

                Logger.getLogger(ToolsUtil.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

        return list;

    }

    /**
     * ��resultset �����ת����list list����Ԫ�ض���Ϊmap
     *
     * ���󣺽�resultsetת����list���ں��洦��
     *
     * ���÷�Χ������
     *
     * ���ߣ�Dsmart
     *
     * ʱ��:2013.09.08
     *
     * @param rs
     * @return
     */
    public static List<Map> parseResultSetToList(ResultSet rs) {

        List list = null;

        Map map = null;

        if (rs != null) {

            try {

                list = new ArrayList();

                //��ȡ������Ľṹ��Ϣ
                ResultSetMetaData metaData = rs.getMetaData();

                while (rs.next()) {

                    map = new HashMap();

                    //����������
                    for (int i = 1; i < metaData.getColumnCount() + 1; i++) {

                        //ת����Сд
                        String col = metaData.getColumnLabel(i).toLowerCase();//metaData.getColumnName(i).toLowerCase();

                        map.put(col, rs.getString(col));

                    }

                    list.add(map);

                }

            } catch (Exception ex) {

                Logger.getLogger(ToolsUtil.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

        return list;

    }

    /**
     * ȥ���ַ�����β��0������0β����������
     *
     * @param info
     * @return
     */
    public static String clearZeroToNum(String info) {

        boolean b = StringUtil.isDouble(info);

        if (b) {
            int i = 0;
            while (info.endsWith("0")) {
            	
                info = info.substring(0, info.lastIndexOf("0"));
                
                i++;
            }
            if (info.endsWith(".")) {

                info = info.substring(0, info.lastIndexOf("."));
            }

        }

        return info;
    }

    /**
     * ������Ϣ�������봦��
     *
     * @param info ����������
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String InfoProcess(String info) throws UnsupportedEncodingException {

        String result = "";

        if (StringUtil.isBlank(info)) {

            result = null;

        } else {

//			result = new String(info.getBytes("ISO-8859-1"),"utf-8");
            result = info;

        }

        return result;
    }

    /**
     * ��ʽ��ʱ��,�жϽ��죬���죬������
     *
     * ���÷�Χ:3����ʱ���ж�
     *
     * ʱ��:2014-10-24
     *
     * ����:HMH
     *
     * @param time ������ʱ��
     * @return
     */
    public static String formatDateTime(String time) {

        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (time == null || "".equals(time)) {

            return "";

        }

        Date date = null;

        try {

            date = format.parse(time);

        } catch (ParseException e) {

            Logger.getLogger(ToolsUtil.class.getName()).log(Level.SEVERE, null, e);

        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();	//����

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));

        today.set(Calendar.MONTH, current.get(Calendar.MONTH));

        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));

        //  Calendar.HOUR����12Сʱ�Ƶ�Сʱ�� Calendar.HOUR_OF_DAY����24Сʱ�Ƶ�Сʱ��
        today.set(Calendar.HOUR_OF_DAY, 0);

        today.set(Calendar.MINUTE, 0);

        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();	//����

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));

        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));

        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);

        yesterday.set(Calendar.HOUR_OF_DAY, 0);

        yesterday.set(Calendar.MINUTE, 0);

        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {

            return "���� ";
//			return "���� "+time.split(" ")[1];

        } else if (current.before(today) && current.after(yesterday)) {

            return "���� ";

//			return "���� "+time.split(" ")[1];
        } else {

//			int index = time.indexOf("-")+3;
            return time.substring(0, time.lastIndexOf("-") + 3);
        }
    }

    /**
     * �ֻ������м�4λ�ӹ�
     *
     * ���÷�Χ:ͨ��
     *
     * ʱ��:2014-10-24
     *
     * ����:HMH
     *
     * @param phone
     *
     * @return
     */
    public static String phoneProcess(String phone) {

        phone = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());

        return phone;

    }

    /**
     * ת�����ڸ�ʽyyyyMMddHHmss
     *
     * ʱ��:2015-02-26
     *
     * ����:HMH
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatDate(String date) throws ParseException {

        String result = null;

//        Date d = new Date(DateUtil.getTime(date));
//        Date d = new Date(DateUtil.getTime(date));
        Date d = new Date(getDayTime());

        SimpleDateFormat data = new SimpleDateFormat("yyyyMMddHHmmss");

        result = data.format(d);

        return result;
    }

    //���Է���
//    public static ISoqzProcess processGroup1(String key) {
//        ISoqzProcess iResultProcess1 = null;
//        try {
//
//            iResultProcess1 = (ISoqzProcess) Class.forName("com.soqz.wap.servlet.process." + key).newInstance();
//        } catch (Exception e) {
//            // TODO: handle exception
//            log.info("No Found Methods" + e);
//        }
//        return iResultProcess1;
//    }

    /**
     * ��ȡָ����ua��Ϣ
     *
     * @param userAgent
     * @return
     */
    public static String getValue(String userAgent) {

        if (userAgent != null) {

            if (userAgent.indexOf(")") > 0) {

                userAgent = userAgent.substring(0, userAgent.indexOf(")"));

                if (userAgent.indexOf(";") > 0) {

                    String[] arrs = userAgent.split(";");

                    userAgent = arrs[arrs.length - 1];
                }

            }

        }

//	        if (!ToolKits.isEmpty(userAgent)) {
        if (!StringUtil.isBlank(userAgent)) {

            userAgent = userAgent.trim().replaceAll(" +", "_");

        }

        return userAgent;

    }

    /**
     * ������ҳ�뻻��
     *
     * ʱ��:2015-02-26
     *
     * ����:HMH
     *
     * @param page
     *
     * @param num
     *
     * @return int[0] = page int[1] = num;
     */
    public static int getPageAndNum(String page, int num) {

        int p = 0;

        if (!StringUtil.isBlank(page)) {

            p = Integer.parseInt(page);

        }

        p = (p - 1) * num;

        return p;

    }
    
    public static String getPageAndNum(Integer page, Integer num) {

        int p = page;

        p = (p - 1) * num;

        return p+","+num;

    }
    

    /**
     * �жϵ�ǰ����ʱ���������
     *
     * ʱ��:2015-02-26
     *
     * ����:HMH
     *
     * @param start ��ʼʱ��
     *
     * @param end ����ʱ��
     *
     * @return
     */
    public static int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }

    /**
     * ��ȡ��ҳ���������
     *
     * ���󣺷�ҳ��ʾ����
     *
     * ���÷�Χ���б�չʾ����
     *
     * ���ߣ�Dsmart
     *
     * ʱ�䣺2013.09.16
     *
     * @param page
     * @param pageSize
     * @return
     */
    public static String getLimitSql(int page, int pageSize) {

        page = page == 0 ? 1 : page;

        return " limit " + (page - 1) * pageSize + "," + pageSize + " ";

    }

    /**
     * ��ȡ��ҳ���������
     *
     * ���󣺷�ҳ��ʾ����
     *
     * ���÷�Χ���б�չʾ����
     *
     * ���ߣ�Dsmart
     *
     * ʱ�䣺2013.09.16
     *
     * @param page
     *
     * @return
     */
    public static String getLimitSql(int page) {

        return getLimitSql(page, 10);

    }

    /**
     * ����ַ����Ƿ�Ϊ��
     *
     * ���󣺼���ַ����Ƿ�Ϊ��
     *
     * ���÷�Χ������ַ����Ƿ�Ϊ��
     *
     * ���ߣ�Dsmart
     *
     * ʱ�䣺2013.08.21
     *
     * @param src
     * @return
     */
    public static Boolean isEmpty(String src) {

        return src == null || src.trim().isEmpty();

    }

    /**
     * ����ⲿ����ֵ�ֻ����Ƿ�Ϸ�
     *
     * ���󣺼���ⲿ������ֻ����Ƿ�Ϸ�
     *
     * ���÷�Χ�����û������ֻ��ŵ�ʱ�����ֻ����Ƿ�Ϸ�
     *
     * ����:Dsmart
     *
     * ʱ��:2014.05.10
     *
     * @param phone
     * @return
     */
    public static Boolean validInputPhone(String phone) {

        boolean flag = false;

        try {

            String check = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$";

            Pattern regex = Pattern.compile(check);

            Matcher matcher = regex.matcher(phone);

            flag = matcher.matches(); 

        } catch (Exception ex) {

            flag = false;

        }

        return flag;

    }

    /**
     * ������ʽ��Ϊ��2λС������ʽ
     *
     * @param price
     * @return
     */
    public static Double formatDecimal(Double price) {

        try {
            if (price != null) {

                DecimalFormat decimalFormat = new DecimalFormat("#.00");

                price = Double.parseDouble(decimalFormat.format(price));

            } else {

                price = 0.00d;

            }
        } catch (Exception ex) {

        }

        return price;

    }

    /**
     * ��ʽ������Ϊ��2ΪС������ʽ
     *
     * @param price
     * @return
     */
    public static Object formatDecimal(Object price) {

//        try {
        if (price != null) {

            DecimalFormat decimalFormat = new DecimalFormat("#.00");

            price = decimalFormat.format(Double.parseDouble(price.toString()));

        } else {

            price = "0.00";

        }

//        } catch (Exception ex) {
//        }
        return price;

    }
    
    /**
     * ת��ʱ���ʽΪ MMDD HH:mm
     * 
     * ʱ��:2015-09-09
     * 
     * ����:HMH
     * 
     * @param time ʱ��
     * 
     * @return
     */
    public static String conversionTimeToMMDDHHmm(String time){
    	
    	String result = "";
    	
    	if(!StringUtil.isBlank(time)){
    		
    		result = time.substring(5, time.lastIndexOf(":"));

    		if(result.startsWith("0")){
    			
    			//ȥ����λ��0
    			result = result.substring(1, result.length());
    			
    		}
    		
    	}
    	
    	return result;
    	
    }


    public static void main(String[] args) throws ParseException {
//		String[] info=StringUtil.split("1,2,3,4", ",");
//		System.out.println(Arrays.toString(info));
//		String info ="295";
//		boolean b=StringUtil.isDouble(info);
//		if(b){
////			System.out.println(info);
//			while(info.endsWith("0")){
//				
//				info=info.substring(0, info.lastIndexOf("0"));
//			}
//			if(info.endsWith(".")){
//				
//				info = info.substring(0, info.lastIndexOf("."));
//			}
//			
//		}
//		String i=clearZeroToNum("295");
//		System.out.println(info);
//		System.out.println(i);

//		System.out.println(info);
//		String info=getDay(0);
//		System.out.println(info);
//        String info = ToolsUtil.formatDate("2014-11-24 15:24:24");
//
//        String day1 = ToolsUtil.getDay(7);
//
//        String day2 = ToolsUtil.getDay(0);
////		System.out.println(day);
//
////		Date date = new Date();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
////		date.after(day);
//        Date dt1 = df.parse(day1);
//        Date dt2 = df.parse(day2);
//
//        System.out.println(dt1.getTime() > dt2.getTime());
//        String price = "2";

        System.out.println(ToolsUtil.conversionTimeToMMDDHHmm("2015-12-30 11:31:53"));
    }

}
