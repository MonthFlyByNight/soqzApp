package com.soqz.wap.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Time {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws Exception {

        // String time = formatDateTime("2014-12-20 15:41:11");
        // // String time = formatDateTime("2014-12-02");
        // System.out.println("time:"+time);
        // time = formatDateTime("2014-12-19 15:45:11");
        // // time = formatDateTime("2014-12-03");
        // System.out.println("time:"+time);
        // time = formatDateTime("2014-12-18 15:43:11");
        // // time = formatDateTime("2014-12-04");
        // System.out.println("time:"+time);
        // 测试周几
//		int i = Time.dayForWeek("2014-12-21");
//		System.out.println(i);
        Long date1 = System.currentTimeMillis();

//		Thread.sleep(1000*60);
//		date:1421313992751
//		oldDate:1421313989108
        Long date2 = System.currentTimeMillis();

        Long result = Time.differenceDate(1419501322784l, 1421314245499l);

        System.out.println(Time.formatMillsToDate(date2, "HH:mm"));
//		System.out.println(date1);

    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    private static String formatDateTime(String time) {
        SimpleDateFormat format = new java.text.SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat format = new
        // java.text.SimpleDateFormat("yyyy-MM-dd");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {

            date = format.parse(time);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance(); // 今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); // 昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,
                current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {

            return "今天 " + time.split(" ")[1];
            // return "今天 ";
        } else if (current.before(today) && current.after(yesterday)) {

            return "昨天 " + time.split(" ")[1];
            // return "昨天 ";
        } else {

            int index = time.indexOf("-") + 1;
            System.out.println(time);
            time = time.substring(0, time.lastIndexOf("-") + 3);
            System.out.println(time);
            return time.substring(0, time.lastIndexOf("-") + 3);
        }
    }

    /**
     * 获取当前系统时间是周几
     *
     * 时间:2014-11-26
     *
     * 作者:HMH
     *
     * 修改时间:2014-12-23
     *
     * 修改内容:增加周日参数的判断,周日在架包中值为1，故需对周日进行特殊处理
     *
     * 修改人:HMH
     *
     * @return
     */
    public static int getweek() {

        int dayForWeek = 0;

//		try {
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

//			c.setTime(format.parse(pTime));
        c.setTimeInMillis(System.currentTimeMillis());

        if (c.get(Calendar.DAY_OF_WEEK) == 1) {

            dayForWeek = 7;

        } else {

            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

        }
//		} catch (Exception e) {
//
//			log.info(ToolsUtil.getDayTime() + "星期数获取异常" + e.toString());
//
//		}
        return dayForWeek;

    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int dayForWeek(String pTime)  {

        int dayForWeek = 0;

        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Calendar c = Calendar.getInstance();

            c.setTime(format.parse(pTime));

            if (c.get(Calendar.DAY_OF_WEEK) == 1) {

                dayForWeek = 7;

            } else {

                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                
            }
        } catch (Exception e) {

        	Logger.getLogger(Time.class.getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
//        	Logger.getLogger();

        }

        return dayForWeek;

    }

    /**
     * 求两个时间差(Long 类型)
     *
     * 时间:2015-01-22
     *
     * 作者:HMH
     *
     * @param date1 oldtime
     *
     * @param date2 newtime
     *
     * @return 以分钟进行计算
     */
    public static Long differenceDate(Long date1, Long date2) {

        Long difference = date2 - date1;

//		 System.out.println(difference);
        //以分钟算
        Long result = difference / (1000 * 60);

        return result;

    }

    /**
     * 将长整型转换为时间
     *
     * @param mills
     * @param format 时间格式化
     * @return
     */
    public static String formatMillsToDate(Long mills, String format) {

        String result = "";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        try {

            result = simpleDateFormat.format(mills);

        } catch (Exception ex) {

            Logger.getLogger(Time.class.getName()).log(Level.SEVERE, null, ex);

        }

        return result;

    }

    /**
     * 将原有时间格式化为要求的形式
     *
     * @param date 原有时间
     * @param original 原有格式
     * @param format 目标格式
     * @return
     */
    public static String formatDate(String date, String original, String format) {

        String result = null;

        SimpleDateFormat originalDateFormat = new SimpleDateFormat(original);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        
//        System.out.println(date);

        try {
        	
        	
        	Date d = originalDateFormat.parse(date);
        	
        	result = simpleDateFormat.format(d);
        	
//        	System.out.println(d);
        	
//        	Long time=new Long(d.getTime());
        	
        	
        	
//            result = simpleDateFormat.format(originalDateFormat.parse(date).getTime());

        } catch (ParseException ex) {

            Logger.getLogger(Time.class.getName()).log(Level.SEVERE, null, ex);

        }

        return result;

    }
    
    
    /**
     * 获取当前系统日期 00000000
     *
     * 适用范围:时间获取
     *
     * 作者：HMH
     *
     * 时间：2015-12-11
     *
     * @param date 相隔天数
     * @return
     */
    public static String getDay(int date) {

        Calendar calendar = Calendar.getInstance();

        //指定相隔日期
        calendar.add(calendar.DATE, date);
        //指定格式
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");

        formater.setLenient(false);

        String datetime = formater.format(calendar.getTime());

        return datetime;
    }

}
