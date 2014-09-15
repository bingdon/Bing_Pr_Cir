package com.example.projectcircle.bean;

public class GroupInfo {
	// 群组id
	private String id;
	// 群主id
	private String uid;
	// 群组名称
	private String gname;
	// 头像
	private String headimage;
	// 群地址
	private String gaddress;
	// 群限制人数
	private String glimit;
	// 创建时间
	private String createtime;
	// 经度
	private double lon;
	// 纬度
	private double lat;
	// 群简介
	private String content;
	
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getHeadimage() {
		return headimage;
	}

	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}

	public String getGaddress() {
		return gaddress;
	}

	public void setGaddress(String gaddress) {
		this.gaddress = gaddress;
	}

	public String getGlimit() {
		return glimit;
	}

	public void setGlimit(String glimit) {
		this.glimit = glimit;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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

	@Override
	public String toString() {
		return "GroupInfo [id=" + id + ", uid=" + uid + ", gname=" + gname
				+ ", headimage=" + headimage + ", gaddress=" + gaddress
				+ ", glimit=" + glimit + ", createtime=" + createtime
				+ ", lon=" + lon + ", lat=" + lat + ", content=" + content
				+ "]";
	}

}
