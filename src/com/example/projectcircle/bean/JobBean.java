package com.example.projectcircle.bean;

import java.io.Serializable;

public class JobBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4845554776357135644L;
	
	private String uid;
	
	private String id;
	
	private String title;
	
	private double jlat;
	
	private double ulat;
	
	private double ulon;
	
	private double jlon;
	
	private String date;

	public String getUid() {
		return ""+uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getId() {
		return ""+id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return ""+title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getJlat() {
		return jlat;
	}

	public void setJlat(double jlat) {
		this.jlat = jlat;
	}

	public double getUlat() {
		return ulat;
	}

	public void setUlat(double ulat) {
		this.ulat = ulat;
	}

	public double getUlon() {
		return ulon;
	}

	public void setUlon(double ulon) {
		this.ulon = ulon;
	}

	public double getJlon() {
		return jlon;
	}

	public void setJlon(double jlon) {
		this.jlon = jlon;
	}

	public String getDate() {
		return ""+date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	

}
