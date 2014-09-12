package com.example.projectcircle.bean;

public class FriendInfo {
	// id
	private String id;
	// 请求添加好友的用户id：Aid
	private String aid;
	// 需要验证好友请求的用户id：Bid
	private String bid;
	// 状态：status(成为好友1，待成为好友0，不是好友就没有记录)
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FriendInfo [id=" + id + ", aid=" + aid + ", bid=" + bid
				+ ", status=" + status + "]";
	}

}
