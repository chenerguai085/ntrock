package com.rh.netlock.util;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *remark:
 *@author:chenj
 *@date: 2019/11/20
 *@return
 */
public final class DateHelper {

    /**
     * 日期常用格式
     */
    static String[] datePatterns = {"yyyy-MM-dd", "yyyyMMdd"};
    /**
     * 日期时间常用格式
     */
    static String[] dateTimePatterns = {"yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"};

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    public static String getDate() {
        LocalDate localDate = LocalDate.now();

        return localDate.toString();
    }

    /**
     * 获取当前日期时间
     *
     * @return yyyyMMddHHmmss
     */
    public static String getDateTime() {
        return getDateTime(dateTimePatterns[0]);
    }


    /**
     * 获取当前日期时间
     *
     * @param pattern - 日期时间格式
     * @return 指定格式的日期时间
     */
    public static String getDateTime(String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime datetime = LocalDateTime.now();

            return datetime.format(formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 转换Str为Date
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parseDate(String dateStr) {
        try {
            return DateUtils.parseDate(dateStr, datePatterns);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 转换Str为DateTme
     *
     * @param datetimeStr 日期时间字符串
     * @return 日期
     */
    public static Date parseDateTime(String datetimeStr) {
        try {
            return DateUtils.parseDate(datetimeStr, dateTimePatterns);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 格式化指定日期
     *
     * @param date 日期
     * @return yyyy-MM-dd
     */
    public static String formatDate(Date date) {
        return formatDate(date, datePatterns[0]);
    }

    /**
     * 格式化指定日期
     *
     * @param date    日期
     * @param pattern 格式
     * @return yyyy-MM-dd
     */
    public static String formatDate(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 判断给定的字符串是否是给定格式
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return true/false
     */
    public static Boolean validateFormat(String dateStr, String pattern) {
        try {
            DateUtils.parseDate(dateStr, pattern);

            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 获取当前日期是星期几
     *
     * @return String - 返回星期几
     */
    public static Integer getWeek() {
        Date date = new Date();

        return calcWeek(date);
    }

    /**
     * 获取指定日期是星期几
     *
     * @param dateStr 日期字符串
     * @return - 返回当前星期几
     */
    public static Integer getWeek(String dateStr) {
        try {
            Date date = DateUtils.parseDate(dateStr, datePatterns);

            return calcWeek(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * 计算某个日期是星期几
     *
     * @param date 日期
     * @return 0-6，0为星期天
     */
    public static Integer calcWeek(Date date) {
        int posOfWeek;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        posOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        posOfWeek--;

        return posOfWeek;
    }

    /**
     * 获取当月第一天字符串
     * yyyy-MM-dd
     *
     * @return
     */
    public static String getFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);

        return DateFormatUtils.format(cal, datePatterns[0]);
    }

    /**
     * 获取当月最后一天字符串
     * yyyy-MM-dd
     *
     * @return
     */
    public static String getLastDay() {
        Calendar cal = Calendar.getInstance();
        Calendar f = (Calendar) cal.clone();
        f.clear();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);

        return DateFormatUtils.format(cal, datePatterns[0]);
    }

    /**
     * 获取当前月天数
     *
     * @return
     */
    public static int getMonthDays() {
        Calendar cal = new GregorianCalendar();

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取时间差天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getDiffDays(Date startDate, Date endDate) {
        long ei = getDiffMilliSeconds(startDate, endDate);

        return (int) (ei / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取时间差毫秒
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getDiffMilliSeconds(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            Date cal = startDate;
            startDate = endDate;
            endDate = cal;
        }
        long sl = startDate.getTime();
        long el = endDate.getTime();

        return el - sl;
    }

    /**
     * 计算当前时间到某一时间的秒数
     *
     * @param endDate
     * @return
     */
    public static Long getRemainSeconds(Date endDate) {
        return getRemainSeconds(endDate.getTime());
    }

    /**
     * 计算当前时间到某一时间的秒数
     *
     * @param endTime
     * @return
     */
    public static Long getRemainSeconds(Long endTime) {
        return getRemainMilliSeconds(endTime) / 1000;
    }

    /**
     * 计算当前时间到某一时间的毫秒数
     *
     * @param endDate
     * @return
     */
    public static Long getRemainMilliSeconds(Date endDate) {
        return getRemainMilliSeconds(endDate.getTime());
    }

    /**
     * 计算当前时间到某一时间的毫秒数
     * 某时间-当前时间
     *
     * @param endTime
     * @return
     */
    public static Long getRemainMilliSeconds(Long endTime) {
        long currentTime = System.currentTimeMillis();

        return endTime - currentTime;
    }

    /**
     *remark: gmt
     *@author:chenj
     *@date: 2019/11/21
     *@return java.util.Date
     */
    public static Date gmtToDate(String gmtDateStr){
        Date time = new Date(gmtDateStr);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeFormat = sdf.format(time);

            Date parseDate = sdf.parse(timeFormat);

            return parseDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     *remark: Sun Nov 24 14:31:17 CST 2019 格式车该字符串时间 转成日期
     *@author:chenj
     *@date: 2019/11/22
     *@return java.util.Date
     */
    public  static Date strToDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }


    /**
     * dateStr 转成秒
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Long parseSecond(String dateStr) {
        long time = 0;
        try {
            time = DateUtils.parseDate(dateStr, dateTimePatterns[1]).getTime()/1000;

            return time;
        } catch (ParseException e) {
            e.printStackTrace();


        }

        return time;
    }

    /**
     *remark:时间戳秒转 日期时间
     *@author:chenj
     *@date: 2019/12/4
     *@return java.util.Date
     */
    public  static Date secondToDate(Long second){
        Date date  = new Date(second * 1000);

        return date;
    }


    /**
     *remark: 获取指定相差n天的日期
     *@author:chenj
     *@date: 2019/12/12
     *@return java.util.Date
     */

    public static Date getDiffDate(Integer count){
        Date d = new Date();
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, count);// count为增加的天数，可以改变的
        d = ca.getTime();

        return  d;
    }

}
