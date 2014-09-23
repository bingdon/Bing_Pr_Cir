package com.example.projectcircle.util;

import java.math.BigDecimal;

public class DistentsUtil {
	private static final double EARTH_RADIUS = 6378137;
	private static double EARTH_RADIUS1 = 6378.137;//地球半径
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return 距离：单位为米
	 */
	public static double DistanceOfTwoPoints(double lat1, double lng1,
			double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	/**
	 * 计算距离方法
	 * @param lng1 经度0
	 * @param lat1 纬度0
	 * @param lng2 经度1
	 * @param lat2 纬度1
	 * @return
	 */
	public static double getDistance(double lng1, double lat1, double lng2, double lat2)
	{
		double radLat1 = lat1 * 0.0174532925199433D;
	    double radLat2 = lat2 * 0.0174532925199433D;
	    double a = radLat1 - radLat2;
	    double b = (lng1 - lng2) * 0.0174532925199433D;
	    double s = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0D), 2.0D) + 
	    		Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0D), 2.0D)));
	    s *= 6378137.0D;
	    s = Math.round(s * 10000.0D) / 10000L;
	    return s;
	  }

	public static double changep2(double i) {
		// TODO Auto-generated method stub
		BigDecimal b = new BigDecimal(i);
		 int saveBitNum = 2;
		 double c = b.setScale(saveBitNum , BigDecimal.ROUND_HALF_UP).doubleValue();
		 return c;
	}


	public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);

	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS1;
	   s = Math.round(s * 10000) / 10000;
	   return s;
	}
	
}
