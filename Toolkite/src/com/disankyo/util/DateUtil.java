package com.disankyo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期操作的常見方法
 * @version 1.00 2010-12-24 15:17:16
 * @since 1.6
 * @author disankyo
 */
public class DateUtil {

    private DateUtil(){
    }
    
    /**
     * 获得当前的日期，忽略时间
     * @return 返回当前的日期
     */
    public static Date getToday() {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, 0);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);

        return time.getTime();
    }

    /**
     * 把给定的日期包装成Date的对象，如果日期出错，则对应的时间为当前时间
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date packageDate(int year, int month, int day) {

        /*
         * 内部日期月份表示0-11
         */
        int actionYear = year;
        int actionMonth = month;
        int actionDay = day;

        Calendar now = Calendar.getInstance();

        if (actionYear <= 0) {
            actionYear = now.get(Calendar.YEAR);
        }

        if (actionMonth <= 0 || actionMonth > 12) {
            actionMonth = now.get(Calendar.MONTH);
        }

        if (actionDay <= 0 || actionDay > 31) {
            actionDay = now.get(Calendar.DATE);
        }

        now = null;
        Calendar calendar = new GregorianCalendar(
        		actionYear, actionMonth, actionDay);
        return calendar.getTime();
    }

    /**
     * 判断指定的日期时间是否在指定的开始时间和结束时间之间。
     * 判断规则是 startDate <= sourceDate <= endDate
     * @param sourceDate 要判断的日期
     * @param startDate 指定日期的开始日期
     * @param endDate 指定日期的结束日期
     * @return 给定时间是否在指定时间范围内，在指定时间范围返回 true;否则返回 false
     */
    public static Boolean isContainDate(Date sourceDate, Date startDate,
            Date endDate) {
        if (sourceDate != null && startDate != null && endDate != null) {
            boolean startFlag = false;
            boolean endFlag = false;
            if (sourceDate.compareTo(startDate) >= 0) {
                startFlag = true;
            }
            if (sourceDate.compareTo(endDate) <= 0) {
                endFlag = true;
            }

            return (startFlag && endFlag) ? true : false;
        }

        return false;
    }

    /**
     * 判断两个日期对像代表的日期是否相等,此方法忽略时间只比较日期.
     * @param firstDate 第一个日期.
     * @param secondDate 第二个日期.
     * @return 是否相等.相等返回 true,不相等返回false.
     */
    public static Boolean isEqual(Date firstDate, Date secondDate) {
        Calendar firstTime = Calendar.getInstance();
        firstTime.setTime(firstDate);

        Calendar secondTime = Calendar.getInstance();
        secondTime.setTime(secondDate);

        if (firstTime.get(Calendar.YEAR) == secondTime.get(Calendar.YEAR)) {
            if (firstTime.get(Calendar.MONTH) == secondTime.get(Calendar.MONTH)) {
                if (firstTime.get(Calendar.DAY_OF_MONTH) == secondTime.get(
                        Calendar.DAY_OF_MONTH)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据给定日期时间解析出需要的值的字符串表示。
     * @param date 需要解析的日期时间对象。
     * @param type Calendar的静态变量值，表示关注的字段。
     * @return 相应的关注时间字段的字符串表示。
     */
    public static String findDateAndTimeStr(Date date, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int filed = cal.get(type);
        return Integer.toString(filed);
    }

    /**
     * 找出指定日期星期一的日期时间。从0:0:0开始。
     * @param time 时间。
     * @return 星期一的时间。
     */
    public static Date findMondayWeekDate(Date date){
        Calendar time = Calendar.getInstance();
        time.setFirstDayOfWeek(Calendar.MONDAY);
        time.set(Calendar.DAY_OF_WEEK, time.getFirstDayOfWeek());
        time.set(Calendar.HOUR, 0);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        
        return time.getTime();
    }

    /**
     * 找到指定时间所在的星期的星期日的日期。时间为23:59:59。
     * @param time 指定的时间。
     * @return 星期日的时间。
     */
    public static Date findLastWeekDate(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    
    /**
     * 找出指定时间所在月的第一天日期。时间为0:0:0.
     * @param time 时间。
     * @return 月第一天的时间。
     */
    public static Date findMonthFirstDay(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 找出指定日期所在月的最后一天。时间设为23:59:59.
     * @param time 日期时间。
     * @return 月最后一天日期。
     */
    public static Date findMonthLastDay(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 返回两个时间的毫秒的差距，算法为第一个时间减去第二个时间。
     * @param first 时间。
     * @param second 时间。
     * @return 两者相关的毫秒。
     */
    public static Long timeGap(Date first, Date second) {
        return Long.valueOf(first.getTime() - second.getTime());
    }

    /**
     * 比较两个时间，忽略日期，判断第一个时间是否在第二个时间之后
     * @param firstDate 第一个时间
     * @param secondDate 第二个时间
     * @return 判断第一个时间是否在第二个时间之后
     */
    public static boolean afterIgnoreDate(Date firstDate, Date secondDate) {
        Calendar first = Calendar.getInstance();
        first.setTime(firstDate);

        Calendar second = Calendar.getInstance();
        second.setTime(secondDate);

        first.set(Calendar.DAY_OF_YEAR, 1);
        first.set(Calendar.YEAR, 1970);

        second.set(Calendar.DAY_OF_YEAR, 1);
        second.set(Calendar.YEAR, 1970);

        return first.after(second);
    }
    
    /**
	 * 获取1970年到现在的秒数
	 * @author haojian
	 * Apr 2, 2013 2:17:28 PM
	 * @return
	 */
	public static int currentTimeSecond(){
		int time = (int)(System.currentTimeMillis()/1000);
		return time;
	}
	
	/**
	 * 根据日期的格式（2013.6.3|12:00:00）获取 时间的秒数
	 * @param dateStr
	 * @param formatStr 日期的字符串的格式:'yyyy.MM.dd|HH:mm:ss'
	 * @return 
	 * @throws ParseException 
	 */
	public static int getSecondByFormatDate(String dateStr, String formatStr){
		
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return (int)(date.getTime() / 1000);
	}
	
	/**
	 * 多少毫秒后到 第一个 该小时，分，秒
	 * @param hour 小时
	 * @param minute 分
	 * @param second 秒
	 * @param milliSecond 毫秒
	 * @return
	 */
	public static long getNextTimeMillis(int hour,int minute,int second,int milliSecond){
		if(hour<0||hour>24){
			throw new RuntimeException("hour is error!");
		}
		Calendar cal = Calendar.getInstance();
		
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, milliSecond);
        long time = cal.getTimeInMillis();
        long currentTime = System.currentTimeMillis();
        if(currentTime<=time){
        	return time - currentTime;
        }else{
        	return time - currentTime + 24*3600*1000;
        }
		
	}
}
