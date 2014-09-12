package com.example.projectcircle.other;

import java.io.Serializable;

public class ChatMsgEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String date;

	private String text;

	private boolean isComMeg = true;
	
	private boolean send_state=false;
	
	private String headimgString;
	
	private long time;
	
	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getHeadimgString() {
		return headimgString;
	}

	public void setHeadimgString(String headimgString) {
		this.headimgString = headimgString;
	}

	public boolean isSend_state() {
		return send_state;
	}

	public void setSend_state(boolean send_state) {
		this.send_state = send_state;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean getMsgType() {
		return isComMeg;
	}

	public void setMsgType(boolean isComMsg) {
		isComMeg = isComMsg;
	}

	public ChatMsgEntity() {
	}

	public ChatMsgEntity(String date, String text, boolean isComMsg) {
		super();
		this.date = date;
		this.text = text;
		this.isComMeg = isComMsg;
	}
}
