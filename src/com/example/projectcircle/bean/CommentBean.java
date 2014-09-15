package com.example.projectcircle.bean;

import java.io.Serializable;

public class CommentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1145944309831274859L;
	
	private String id;
	
	private String content;
	
	private String createtime;
	
	private MyPersonBean user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return ""+content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public MyPersonBean getUser() {
		return user;
	}

	public void setUser(MyPersonBean user) {
		this.user = user;
	}
	
	
	
	

}
