package com.evsward.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtils {

	protected static final Log log = LogFactory.getLog(DateUtils.class);
	
	/** yyyyMMddHHmmss */
	public static final String PATTERN = "yyyyMMddHHmmss";
	/** yyyy-MM-dd HH:mm:ss */
	public static final String PATTERN1 = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd */
	public static final String PATTERN2 = "yyyy-MM-dd";
	/** hhmmssSSS */
	public static final String PATTERN3 = "hhmmssSSS";
	/** yyyyMMddHHmmssSSS */
	public static final String PATTERN4 = "yyyyMMddHHmmssSSS";
	/** yyyyMMdd */
	public static final String PATTERN5 = "yyyyMMdd";
	/** HHmmss */
	public static final String PATTERN6 = "HHmmss";
	/** HH:mm:ss */
	public static final String PATTERN7 = "HH:mm:ss";
	/** yyyy-MM-dd E */
	public static final String PATTERN8 = "yyyy-MM-dd E";
	
	public static Integer getYear(Date d) {
		if (d == null)
			return null;
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(d);
		return gcal.get(GregorianCalendar.YEAR);
	}
	
	public static Integer getMonth(Date d) {
		if (d == null)
			return null;
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(d);
		return gcal.get(GregorianCalendar.MONTH) +1;
	}
	
	public static Integer getDay(Date d) {
		if (d == null)
			return null;
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(d);
		return gcal.get(GregorianCalendar.DAY_OF_MONTH);
	}
	
	public static Date getFirstDayOfYear(Date d) {		
		if (d == null)
			return null;
		return getFormatDate(getYear(d) + "-01-01");
	}
	
	public static Date getLastDayOfYear(Date d) {		
		if (d == null)
			return null;		
		Date newYearDay = getFormatDate((getYear(d)+1) + "-01-01");
		return dateAddDay(newYearDay,-1);
	}


	/**
	 * 取上周的起始日期和截止日期
	 * 
	 * @param d
	 * @return
	 */
	public static Map<String, Object> getLastWeekFirstday(Date d) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (d == null)
			return null;

		d = getFormatDate(formatDate(d, "yyyy-MM-dd"));
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(d);
		int iweek = gcal.get(GregorianCalendar.DAY_OF_WEEK);

		Date lastweekFirstDay = dateAddDay(d, 0 - iweek - 5);
		Date lastweekLastDay = dateAddDay(lastweekFirstDay, 7);

		map.put("startDate", lastweekFirstDay);
		map.put("endDate", lastweekLastDay);
		return map;
	}
	
	/**
	 * 取星期几
	 * 
	 * @param d
	 * @return
	 */
	public static int getWeekday(Date d) {
		if (d == null){
			return -1;
		}
		d = getFormatDate(formatDate(d, "yyyy-MM-dd"));
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(d);
		int iweek = gcal.get(GregorianCalendar.DAY_OF_WEEK);
		return iweek;
	}
	
	public static String getWeekDayCN(Date d){
		if (d == null){
			return "";
		}
		d = getFormatDate(formatDate(d, "yyyy-MM-dd"));
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(d);
		int iweek = gcal.get(GregorianCalendar.DAY_OF_WEEK);
		switch(iweek){
			case 1:
				return "星期日";
			case 2:
				return "星期一";
			case 3:
				return "星期二";
			case 4:
				return "星期三";
			case 5:
				return "星期四";
			case 6:
				return "星期五";
			case 7:
				return "星期六";
			
		}
		return "";
	}

	public static Date dateAddMonth(Date d, int m) {
		if (d == null)
			return null;
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(d);
		gcal.add(GregorianCalendar.MONTH, m);
		return gcal.getTime();
	}

	public static Date dateAddYear(Date d, int y) {
		if (d == null)
			return null;
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(d);
		gcal.add(GregorianCalendar.YEAR, y);
		return gcal.getTime();
	}

	public static Date dateAddDay(Date d, int day) {
		if (d == null)
			return null;
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(d);
		gcal.add(GregorianCalendar.DATE, day);
		return gcal.getTime();
	}

	/**
	 * 获取某年某月所包含的天数
	 * 
	 * @param y
	 * @param m
	 * @return
	 */
	public static Integer getDaysnumberOfMonth(int y, int m) {
		if (y < 1000 || y > 10000 || m < 1 || m > 12) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		sb.append(y).append("-");
		if (m < 10) {
			sb.append("0");
		}
		sb.append(m).append("-01");
		Date d0 = getFormatDate(sb.toString());
		Date d1 = dateAddMonth(d0, 1);
		long mils = d1.getTime() - d0.getTime();
		int idays = (int) (mils / (24 * 60 * 60 * 1000));
		return idays;
	}

	/**
	 * 获取格式化后的日期
	 * 
	 * @param strdate
	 * @return
	 */
	public static Date getFormatDate(String strdate) {
		if (strdate == null || "".equals(strdate)) {
			return null;
		}
		try {
			if (strdate.length() <= 10) {
				return getFormatDate(strdate, "yyyy-MM-dd");
			} else if (strdate.length() < 19) {
				return getFormatDate(strdate, "yyyy-MM-dd HH:mm");
			} else {
				return getFormatDate(strdate, "yyyy-MM-dd HH:mm:ss");
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据录入的文本，返回日期
	 * 
	 * @param strdate
	 * @param format
	 * @return
	 */
	public static Date getFormatDate(String strdate, String format) {
		Date d = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			d = sdf.parse(strdate);
		} catch (Exception e) {
			log.error("error to parse date::" + strdate);
		}
		return d;
	}

	public static String formatDate(Date d, String format) {
		String strdate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			strdate = sdf.format(d);
		} catch (Exception e) {
			log.error("error to format date::" + d);
		}
		return strdate;
	}
	
	public static void main(String[] args) {
		System.out.println(formatDate(new Date(), PATTERN8));
	}
}
