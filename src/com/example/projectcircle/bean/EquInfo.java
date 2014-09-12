package com.example.projectcircle.bean;

public class EquInfo {
	// id
	private String id;
	// 用户id
	private String uid;
	// 设备名称
	private String ename;
	// 出售设备的类型（整机还零件等）业务范围
	private String etype;
	// 品牌
	private String ebrand;
	// 型号
	private String emodel;
	// 样式 自卸车
	private String estyle;
	// 重量 吨位
	private String eweight;
	// 数量
	private String enumber;
	// 载重
	private String eload;
	
	private String headimage;
	// 发布日期
//	private String createtime;

	public String getHeadimage() {
		return headimage;
	}

	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}

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

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getEtype() {
		return etype;
	}

	public void setEtype(String etype) {
		this.etype = etype;
	}

	public String getEbrand() {
		return ebrand;
	}

	public void setEbrand(String ebrand) {
		this.ebrand = ebrand;
	}

	public String getEmodel() {
		return emodel;
	}

	public void setEmodel(String emodel) {
		this.emodel = emodel;
	}

	public String getEstyle() {
		return estyle;
	}

	public void setEstyle(String estyle) {
		this.estyle = estyle;
	}

	public String getEweight() {
		return eweight;
	}

	public void setEweight(String eweight) {
		this.eweight = eweight;
	}

	public String getEnumber() {
		return enumber;
	}

	public void setEnumber(String enumber) {
		this.enumber = enumber;
	}

	public String getEload() {
		return eload;
	}

	public void setEload(String eload) {
		this.eload = eload;
	}

//	public String getCreatetime() {
//		return createtime;
//	}
//
//	public void setCreatetime(String createtime) {
//		this.createtime = createtime;
//	}

	@Override
	public String toString() {
		return "EquInfo [id=" + id + ", uid=" + uid + ", ename=" + ename
				+ ", etype=" + etype + ", ebrand=" + ebrand + ", emodel="
				+ emodel + ", estyle=" + estyle + ", eweight=" + eweight
				+ ", enumber=" + enumber + ", eload=" + eload + ", createtime="
				+ /*createtime + */"]";
	}

}
