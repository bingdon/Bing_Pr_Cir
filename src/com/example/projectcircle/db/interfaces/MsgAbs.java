package com.example.projectcircle.db.interfaces;

public interface MsgAbs {

	public long update(String name, String frid,String content,String headimg,String time,int unreadnum,int type,long id);
	
	public long update(String name, String frid,String content,String headimg,String time,int unreadnum,int type);
	
	public long delete(long id);
	
	public long delete(String id);
	
	public long insert(String name, String frid,String content,String headimg,String time,int unreadnum,int type);
	
	public Object queryData();
	
	public Object queryData(int pagersize);
	
	public Object queryDataId(long id);
	
	public Object queryDataId(int frid);
	
	public Object deleteAll();
	
	public void close();
	
}
