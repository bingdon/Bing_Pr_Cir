package com.example.projectcircle.db.interfaces;

public interface GroupChatInterface extends FriendChatAbs{
	public long insert( String uid,String name,String headimg,String content,String time, String showtime,int iscom);
}
