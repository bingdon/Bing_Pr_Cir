package com.example.projectcircle.db.interfaces;

public interface FriendDatabasePle {

	public Object update(String name, String friid,String headimg,long id);
	
	
	
	public long update(String content,String time ,int id);
	
	/**
	 * 删除
	 * @param id 
	 * @return
	 */
	public Object delete(long id);
	/**
	 * 
	 * @param name 好友名称
	 * @param friid 好友ID
	 * @param headimg 好友头像
	 * @return
	 */
	public long insert(String name, String friid,String headimg);
	/**
	 * 
	 * @param content 聊天内容
	 * @param time 时间
	 * @return 
	 */
	public long insert(String content,String time);
	/**
	 * 请求数据
	 * @return
	 */
	public Object queryData();
	
	public Object queryData(int pagersize);
	
	public Object queryDataId(long id);
	
	public Object queryDataId(int frid);
	
	public Object deleteAll();
	
	public void close();
	
}
