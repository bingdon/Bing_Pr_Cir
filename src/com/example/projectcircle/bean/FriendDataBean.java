package com.example.projectcircle.bean;


public class FriendDataBean {

	//好友昵称
	private String friendname;
	//好友id
	private String friendid;
	//好友头像
	private String friendhead;
	
	private int _ID;
	
	public int get_ID() {
		return _ID;
	}
	public void set_ID(int _ID) {
		this._ID = _ID;
	}
	public String getFriendname() {
		return friendname;
	}
	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}
	public String getFriendid() {
		return friendid;
	}
	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}
	public String getFriendhead() {
		return friendhead;
	}
	public void setFriendhead(String friendhead) {
		this.friendhead = friendhead;
	}
	
	
	
}
