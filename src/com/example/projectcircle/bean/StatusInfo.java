package com.example.projectcircle.bean;

public class StatusInfo {
	// id
	private String id;
	// 电话
	private String tel;
	// 年龄
	private String age;
	// 用户名
	private String username;
	// 密码
	
	// 头像
	private String uheadimage;
	// 工程圈照片
	private String mheadimage;
	// 经度
	private double lon;
	// 纬度
	private double lat;
	// 全地址
	private String rplace;
	// ?
	private String displayType;
	// 个性签名
	private String sign;
	// 个人简介
	private String info;
	// 注册时间
	private String createtime;
	// 公司名称
	String companyname;
	// 常出没的地点
	String place;
	// 兴趣爱好
	String hobby;

	public String getRplace() {
		return rplace;
	}

	public void setRplace(String rplace) {
		this.rplace = rplace;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}



	@Override
	public String toString() {
		return "StatusInfo [id=" + id + ", tel=" + tel + ", age=" + age
				+ ", username=" + username + ", uheadimage=" + uheadimage
				+ ", mheadimage=" + mheadimage + ", lon=" + lon + ", lat="
				+ lat + ", rplace=" + rplace + ", displayType=" + displayType
				+ ", sign=" + sign + ", info=" + info + ", createtime="
				+ createtime + ", companyname=" + companyname + ", place="
				+ place + ", hobby=" + hobby + "]";
	}

	public String getUheadimage() {
		return uheadimage;
	}

	public void setUheadimage(String uheadimage) {
		this.uheadimage = uheadimage;
	}

	public String getMheadimage() {
		return mheadimage;
	}

	public void setMheadimage(String mheadimage) {
		this.mheadimage = mheadimage;
	}

}
