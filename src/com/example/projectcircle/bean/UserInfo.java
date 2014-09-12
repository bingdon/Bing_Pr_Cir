package com.example.projectcircle.bean;

import java.io.Serializable;


public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// id
	private String id;
	// 电话
	private String tel;
	// 年龄
	private String age;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 用户类型
	private String type;
	// 头像
	private String headimage;
	// 所在地
	private String address;
	// 设备
	private String equipment;
	// 出生日期
	private String birthday;
	// 业务
	private String business;
	// 是否在圈里显示电话（1默认为显示）
	private String accept;
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
	// 删除好友时用到的id
	private String ccid;
	//上次登录的时间
	private String lastlogintime;	
	// 请求加入群组的id
	private String gid;
	public String getRplace() {
		return rplace;
	}

	public void setRplace(String rplace) {
		this.rplace = rplace;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double d) {
		this.lon = d;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double d) {
		this.lat = d;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHeadimage() {
		return headimage;
	}

	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
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

	public String getCcid() {
		return ccid;
	}

	public void setCcid(String ccid) {
		this.ccid = ccid;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", tel=" + tel + ", age=" + age
				+ ", username=" + username + ", password=" + password
				+ ", type=" + type + ", headimage=" + headimage + ", address="
				+ address + ", equipment=" + equipment + ", birthday="
				+ birthday + ", business=" + business + ", accept=" + accept
				+ ", lon=" + lon + ", lat=" + lat + ", rplace=" + rplace
				+ ", displayType=" + displayType + ", sign=" + sign + ", info="
				+ info + ", createtime=" + createtime + ", companyname="
				+ companyname + ", place=" + place + ", hobby=" + hobby + "]";
	}

	public String getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}




}
