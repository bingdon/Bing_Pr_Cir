package com.example.projectcircle.bean;

public class NewConstactBean extends MyPersonBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8715103871285036601L;

	private int type_;
	
	private int _id;
	
	private int isAccpet;
	
	private String cid;

	public String getCid() {
		return ""+cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getIsAccpet() {
		return isAccpet;
	}

	public void setIsAccpet(int isAccpet) {
		this.isAccpet = isAccpet;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getType_() {
		return type_;
	}

	public void setType_(int type_) {
		this.type_ = type_;
	}

}
