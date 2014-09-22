package com.example.projectcircle.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BingDateUtils {

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat simpleDateFormat_ = new SimpleDateFormat(
			"yyyy-MM");

	private static Calendar calendar = Calendar.getInstance();

	@SuppressWarnings("deprecation")
	public static int getMonth(String date) {

		try {
			return simpleDateFormat.parse(date).getMonth() + 1;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;

	}

	public static int getYear(String date) {

		try {
			return simpleDateFormat.parse(date).getYear();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;

	}
	
	public static int getYear_(String date) {

		try {
			return simpleDateFormat_.parse(date).getYear();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;

	}
	

	@SuppressWarnings("deprecation")
	public static int getDay(String date) {

		try {
			return simpleDateFormat.parse(date).getDate();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public static long getTime(String date) {
		try {
			return simpleDateFormat.parse(date).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;

	}

	public static String getDateStr(Date date) {
		return simpleDateFormat.format(date);
	}

	/**
	 * 获得当前月份
	 * 
	 * @return
	 */
	public static String getPhoneMonth() {
		return "" + (calendar.get(Calendar.MONTH) + 1);
	}

	/**
	 * 日
	 * 
	 * @return
	 */
	public static String getPhoneDay() {
		return "" + (calendar.get(Calendar.DATE));
	}
}
