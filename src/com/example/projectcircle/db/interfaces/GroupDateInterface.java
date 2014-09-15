package com.example.projectcircle.db.interfaces;

public interface GroupDateInterface extends DataInterface{

	public long insert(String gname,String headimage,String gaddress,String gid,String uid);
	
	public long update(String gname,String headimage,String gaddress,String gid,String uid);
	
	public long update(String gname,String headimage,String gaddress,String gid,String uid,long id);
	
}
