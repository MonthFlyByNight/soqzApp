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
     * 随机码生成
     *
     * 适用范围：通用
     *
     * 时间:2014-06-16
     *
     * 作者:HMH parameter i:获取位数
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
     * 文件映射处理
     *
     * 适用范围:手机数据处理
     *
     * 时间:2014-06-19
     *
     * 作者:HMH
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
     * 获取当前系统日期和系统时间 0000-00-00 00:00:00
     *
     * 适用范围:时间获取
     *
     * 作者：HMH
     *
     * 时间：2014-06-19
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
     * 获取当前系统日期 0000-00-00
     *
     * 适用范围:时间获取
     *
     * 作者：HMH
     *
     * 时间：2014-06-19
     *
     * @param date 相隔天数
     * @return
     */
    public static String getDay(int date) {

        Calendar calendar = Calendar.getInstance();

        //指定相隔日期
        calendar.add(calendar.DATE, date);
        //指定格式
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        formater.setLenient(false);

        String datetime = formater.format(calendar.getTime());

        return datetime;
    }

    /**
     * 返回系统长整型时间
     *
     * @return
     */
    public static Long getCurrentTimeMillis() {

        return System.currentTimeMillis();

    }

    /**
     * 保存limit数量的图片
     *
     * 需求：用户晒图时 最多只能保存最新的5张图片
     *
     * 适用范围：需要限制图片数量
     *
     * 作者:Dsmart
     *
     * 时间:2013.12.15
     *
     * @param images 多张图片按照分隔符拼接组成的字符串
     * @param separator 分隔符
     * @param path 图片根目录
     * @param limit 限制的数量
     * @return
     */
    public static String saveLimitImages(String images, String separator, String path, Integer limit) {

        String result = null;

        if (images != null) {

            limit = limit == null ? 5 : limit;

            //图片数组
            String[] imgs = images.split(separator);

            //图片数不超过限制数量时
            if (imgs.length <= limit) {

                //返回原数据
                result = images;

            } else {//图片数量超过限制数量的时候

                StringBuilder saveimgs = new StringBuilder("");

                for (int i = 0; i < imgs.length; i++) {

                    if (i < limit) {//数量限制内的图片 按原有格式拼接组成字符串

                        saveimgs.append(imgs[i]).append(separator);

                    } else {//删除图片 超过的数量的图片

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
     * 获取最后保留下来的图片数据
     *
     * 需求：用户晒图过程中修改图片信息 需要将过期的图片处理掉
     *
     * 适用范围：过滤并删除掉无效的图片
     *
     * 作者:Dsmart
     *
     * 时间:2013.12.15
     *
     * @param images 原有图片数据拼接字符串
     * @param separator 分隔符
     * @param keys 需要保留的图片数组
     * @param path 图片根目录
     * @return
     */
    public static String filterInvaildImages(String images, String separator, String[] keys, String path) {

        String result = "";

        //原有图片数据不为空时  进行
        if (!StringUtil.isBlank(images)) {

            result = images;

            //要保存的图片数组不为空
            if (keys != null && keys.length > 0) {

                //需要保存的
                StringBuilder save = new StringBuilder();

                for (String key : keys) {

                    //原有图片中存在要保存的图片
                    if (images.contains(key)) {

                        images = images.replaceAll(key, "");

                        save.append(key).append(separator);

                    }

                }

                //当需要保存的图片结果不为空时  则表示keys中的值原本存在于images中  亦即表示keys中的值是正确的
                if (!StringUtil.isBlank(save.toString())) {

                    //这是需要删除掉的图片
                    images = images.replaceAll(separator + "+", separator);

                    String[] deletes = images.split(separator);

                    //删除放弃的图片
                    for (String del : deletes) {

                        ToolsUtil.deleteDocs(path + del);

                    }

                    //最后需要保存的图片
                    result = save.toString();

                }//否则原图片数据不变

            }

        }

        return result;

    }

    /**
     *
     * @param image 所有图片
     * @param separator 分隔符
     * @param invalidImages 需要去掉的图片
     * @param path 图片存储根目录
     * @return
     */
    public static String saveValidImages(String image, String separator, String[] invalidImages, String path) {

        String result = null;

        //原有图片非空
        if (!StringUtil.isBlank(image)) {

            if (invalidImages != null && invalidImages.length > 0) {

                for (String invalidImage : invalidImages) {

                    //去除掉需要删除的图片
                    image = image.replaceAll(invalidImage, "");

                    //物理删除 需要删除的图片
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
     * 删除文件
     *
     * 需求：删除掉不用的文件
     *
     * 适用范围：删除掉无用文件
     *
     * 作者：Dsmart
     *
     * 时间：2013.10.08
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
     * 根据毫秒数获取时间 0000-00-00 00:00:00
     *
     * 需求：获取当前时间之后一定天数的时间
     *
     * 适用范围：获取特定时间的规定格式的时间
     *
     * 作者：Dsmart
     *
     * 时间：2013.07.30
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
     * 格式化日期 0000-00-00 00:00:00
     *
     * 需求：将日期格式化
     *
     * 适用范围：格式化日期
     *
     * 作者：Dsmart
     *
     * 时间：2013.07.30
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
     * 将resultset 结果集转换成list list里面元素对象为map
     *
     * 需求：将resultset转换成list便于后面处理
     *
     * 适用范围：所有
     *
     * 作者：Dsmart
     *
     * 时间:2013.09.08
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

                //获取结果集的结构信息
                ResultSetMetaData metaData = rs.getMetaData();

                while (rs.next()) {

                    // map = new HashMap();
                    //遍历所有列
                    for (int i = 1; i < metaData.getColumnCount() + 1; i++) {

                        //转换成小写
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
     * 将resultset 结果集转换成list list里面元素对象为map
     *
     * 需求：将resultset转换成list便于后面处理
     *
     * 适用范围：所有
     *
     * 作者：Dsmart
     *
     * 时间:2013.09.08
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

                //获取结果集的结构信息
                ResultSetMetaData metaData = rs.getMetaData();

                while (rs.next()) {

                    map = new HashMap();

                    //遍历所有列
                    for (int i = 1; i < metaData.getColumnCount() + 1; i++) {

                        //转换成小写
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
     * 去除字符串的尾数0生成无0尾数的新数字
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
     * 请求信息中文乱码处理
     *
     * @param info 待处理数据
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
     * 格式化时间,判断今天，昨天，和日期
     *
     * 适用范围:3天内时间判断
     *
     * 时间:2014-10-24
     *
     * 作者:HMH
     *
     * @param time 待处理时间
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

        Calendar today = Calendar.getInstance();	//今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));

        today.set(Calendar.MONTH, current.get(Calendar.MONTH));

        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));

        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);

        today.set(Calendar.MINUTE, 0);

        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();	//昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));

        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));

        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);

        yesterday.set(Calendar.HOUR_OF_DAY, 0);

        yesterday.set(Calendar.MINUTE, 0);

        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {

            return "今天 ";
//			return "今天 "+time.split(" ")[1];

        } else if (current.before(today) && current.after(yesterday)) {

            return "昨天 ";

//			return "昨天 "+time.split(" ")[1];
        } else {

//			int index = time.indexOf("-")+3;
            return time.substring(0, time.lastIndexOf("-") + 3);
        }
    }

    /**
     * 手机号码中间4位加工
     *
     * 适用范围:通用
     *
     * 时间:2014-10-24
     *
     * 作者:HMH
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
     * 转换日期格式yyyyMMddHHmss
     *
     * 时间:2015-02-26
     *
     * 作者:HMH
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

    //测试方法
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
     * 获取指定的ua信息
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
     * 测试用页码换算
     *
     * 时间:2015-02-26
     *
     * 作者:HMH
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
     * 判断当前两个时间段相差几个月
     *
     * 时间:2015-02-26
     *
     * 作者:HMH
     *
     * @param start 开始时间
     *
     * @param end 结束时间
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
     * 获取分页的限制语句
     *
     * 需求：分页显示数据
     *
     * 适用范围：列表展示数据
     *
     * 作者：Dsmart
     *
     * 时间：2013.09.16
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
     * 获取分页的限制语句
     *
     * 需求：分页显示数据
     *
     * 适用范围：列表展示数据
     *
     * 作者：Dsmart
     *
     * 时间：2013.09.16
     *
     * @param page
     *
     * @return
     */
    public static String getLimitSql(int page) {

        return getLimitSql(page, 10);

    }

    /**
     * 检测字符串是否为空
     *
     * 需求：检测字符串是否为空
     *
     * 适用范围：检测字符串是否为空
     *
     * 作者：Dsmart
     *
     * 时间：2013.08.21
     *
     * @param src
     * @return
     */
    public static Boolean isEmpty(String src) {

        return src == null || src.trim().isEmpty();

    }

    /**
     * 检测外部输入值手机号是否合法
     *
     * 需求：检测外部输入的手机号是否合法
     *
     * 适用范围：在用户更换手机号的时候检测手机号是否合法
     *
     * 作者:Dsmart
     *
     * 时间:2014.05.10
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
     * 将金额格式化为带2位小数的形式
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
     * 格式化数据为带2为小数的形式
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
     * 转换时间格式为 MMDD HH:mm
     * 
     * 时间:2015-09-09
     * 
     * 作者:HMH
     * 
     * @param time 时间
     * 
     * @return
     */
    public static String conversionTimeToMMDDHHmm(String time){
    	
    	String result = "";
    	
    	if(!StringUtil.isBlank(time)){
    		
    		result = time.substring(5, time.lastIndexOf(":"));

    		if(result.startsWith("0")){
    			
    			//去除首位的0
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
