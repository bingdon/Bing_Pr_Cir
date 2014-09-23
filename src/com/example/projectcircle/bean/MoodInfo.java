package com.example.projectcircle.bean;

import java.util.List;

public class MoodInfo {
   private String moodid;//心情列表的id；
   private String context;//发表心情的内容；
   private String username;//用户名
   private List<String> imgs; //图片地址
   private String uheadimg;//用户头像
   private String commentcount;//评论数量
   private String laudcount;//点赞数
   private String comment;//评论
   private String time;
   private String commentid;//评论内容的id
	@Override
public String toString() {
	return "MoodInfo [moodid=" + moodid + ", context=" + context
			+ ", username=" + username + ", imgs=" + imgs + ", uheadimg="
			+ uheadimg + ", commentcount=" + commentcount + ", laudcount="
			+ laudcount + ", comment=" + comment + ", time=" + time
			+ ", commentid=" + commentid + "]";
}
	public String getMoodid() {
		return moodid;
	}
	public void setMoodid(String moodid) {
		this.moodid = moodid;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUheadimg() {
		return uheadimg;
	}
	public void setUheadimg(String uheadimg) {
		this.uheadimg = uheadimg;
	}
	public String getCommentcount() {
		return commentcount;
	}
	public void setCommentcount(String commentcount) {
		this.commentcount = commentcount;
	}
	public String getLaudcount() {
		return laudcount;
	}
	public void setLaudcount(String laudcount) {
		this.laudcount = laudcount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<String> getImgs() {
		return imgs;
	}
	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}
	public String getCommentid() {
		return commentid;
	}
	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}
}
