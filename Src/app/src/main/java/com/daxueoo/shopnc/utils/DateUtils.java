package com.daxueoo.shopnc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.text.format.DateFormat;


public class DateUtils {
	
//	"created_at": "Wed Jun 17 14:26:24 +0800 2015"

	public static final long ONE_MINUTE_MILLIONS = 60 * 1000;
	public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;
	public static final long ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS;

	public static String getShortTime(String dateStr) {
		String str = "";

		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
		try {
			Date date = sdf.parse(dateStr);
			Date curDate = new Date();

			long durTime = curDate.getTime() - date.getTime();
			int dayStatus = calculateDayStatus(date, curDate);

			if(durTime <= 10 * ONE_MINUTE_MILLIONS) {
				str = "刚刚";
			} else if(durTime < ONE_HOUR_MILLIONS) {
				str = durTime / ONE_MINUTE_MILLIONS + "分钟前";
			} else if(dayStatus == 0) {
				str = durTime / ONE_HOUR_MILLIONS + "小时前";
			} else if(dayStatus == -1) {
				str = "昨天" + DateFormat.format("HH:mm", date);
			} else if(isSameYear(date, curDate) && dayStatus < -1) {
				str = DateFormat.format("MM-dd", date).toString();
			} else {
				str = DateFormat.format("yyyy-MM", date).toString();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * 将时间戳转为代表"距现在多久之前"的字符串
	 * @param timeStr   时间戳
	 * @return
	 */
	public static String getStandardDate(String timeStr) {

		StringBuffer sb = new StringBuffer();

		long t = Long.parseLong(timeStr);
		long time = System.currentTimeMillis() - (t*1000);
		long mill = (long) Math.ceil(time /1000);//秒前

		long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

		long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

		long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前

		if (day - 1 > 0) {
			sb.append(day + "天");
		} else if (hour - 1 > 0) {
			if (hour >= 24) {
				sb.append("1天");
			} else {
				sb.append(hour + "小时");
			}
		} else if (minute - 1 > 0) {
			if (minute == 60) {
				sb.append("1小时");
			} else {
				sb.append(minute + "分钟");
			}
		} else if (mill - 1 > 0) {
			if (mill == 60) {
				sb.append("1分钟");
			} else {
				sb.append(mill + "秒");
			}
		} else {
			sb.append("刚刚");
		}
		if (!sb.toString().equals("刚刚")) {
			sb.append("前");
		}
		return sb.toString();
	}
	
	public static boolean isSameYear(Date targetTime, Date compareTime) {
		Calendar tarCalendar = Calendar.getInstance();
		tarCalendar.setTime(targetTime);
		int tarYear = tarCalendar.get(Calendar.YEAR);
		
		Calendar compareCalendar = Calendar.getInstance();
		compareCalendar.setTime(compareTime);
		int comYear = compareCalendar.get(Calendar.YEAR);
		
		return tarYear == comYear;
	}
	
	public static int calculateDayStatus(Date targetTime, Date compareTime) {
		Calendar tarCalendar = Calendar.getInstance();
		tarCalendar.setTime(targetTime);
		int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);
		
		Calendar compareCalendar = Calendar.getInstance();
		compareCalendar.setTime(compareTime);
		int comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);
		
		return tarDayOfYear - comDayOfYear;
	}

	public static String date2TimeStamp(String date_str,String format) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			return String.valueOf(simpleDateFormat.parse(date_str).getTime()/1000);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
