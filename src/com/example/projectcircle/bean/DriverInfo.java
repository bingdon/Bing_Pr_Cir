package com.example.projectcircle.bean;

public class DriverInfo {
	// id
	private String id;
	// 对应的用户id
	private String uid;
	// 驾龄
	private String driveryears;
	// 现驾驶的设备
	private String nequ;
	// 曾驾驶的设备
	private String oequ;
	// 设备头像
	private String equimage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDriveryears() {
		return driveryears;
	}

	public void setDriveryears(String driveryears) {
		this.driveryears = driveryears;
	}

	public String getEquimage() {
		return equimage;
	}

	public void setEquimage(String equimage) {
		this.equimage = equimage;
	}

	public String getNequ() {
		return nequ;
	}

	public void setNequ(String nequ) {
		this.nequ = nequ;
	}

	public String getOequ() {
		return oequ;
	}

	public void setOequ(String oequ) {
		this.oequ = oequ;
	}

	@Override
	public String toString() {
		return "DriverInfo [id=" + id + ", uid=" + uid + ", driveryears="
				+ driveryears + ", nequ=" + nequ + ", oequ=" + oequ
				+ ", equimage=" + equimage + "]";
	}

}
