package com.example.projectcircle.db.interfaces;

public interface FriendChatAbs extends FriendDatabasePle {

	public Object update( String content,String time, String showtime,int iscom,long id);
	
	public long insert( String content,String time, String showtime,int iscom);
	
	
}
