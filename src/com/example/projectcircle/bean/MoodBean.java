package com.example.projectcircle.bean;

import java.io.Serializable;
import java.util.List;

public class MoodBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -996427224045979832L;

	private String context;
	
	private int commentcount;
	
	private String createtime;
	
	private String id;
	
	private int laudcount;
	
	private String userid;
	
	private MyPersonBean user;
	
	private List<CommentBean> comment;
	
	private List<String> img;
	
	private String cn_time;

	public String getCn_time() {
		return ""+cn_time;
	}

	public void setCn_time(String cn_time) {
		this.cn_time = cn_time;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public int getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLaudcount() {
		return laudcount;
	}

	public void setLaudcount(int laudcount) {
		this.laudcount = laudcount;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public MyPersonBean getUser() {
		return user;
	}

	public void setUser(MyPersonBean user) {
		this.user = user;
	}

	public List<CommentBean> getComment() {
		return comment;
	}

	public void setComment(List<CommentBean> comment) {
		this.comment = comment;
	}

	public List<String> getImg() {
		return img;
	}

	public void setImg(List<String> img) {
		this.img = img;
	}
	
	
	
}
