package com.example.projectcircle.db;

import com.example.projectcircle.bean.FriendChat;
import com.example.projectcircle.bean.GroupChatData;
import com.example.projectcircle.bean.GroupData;
import com.example.projectcircle.friend.GroupChat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ProJectDatebase {

	public static SQLiteDatabase proDatabase;

	private static final String CREATE_FRI_INFO_SQL_HEAD = "CREATE TABLE IF NOT EXISTS "
			+ FriendChat.TABLE_NAME;

	private static final String CREATE_FRI_INFO_TALE = "(" + FriendChat._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + FriendChat.TIME
			+ " TEXT," + FriendChat.CONTENT + " TEXT," + FriendChat.TITLE
			+ " TEXT," + FriendChat.HEAD_IMAGE + " TEXT,"+ FriendChat.UID + " TEXT,"
			+ FriendChat.SHOW_TIME + " VARCHAR(15) NOT NULL,"
			+ FriendChat.IS_COM + " INTEGER" + ");";

	private static final String CREATE_GROUP_INFO_SQL_HEAD = "CREATE TABLE IF NOT EXISTS "
			+ GroupChatData.TABLE_NAME;

	private static final String CREATE_GROUP_INFO_TALE = "("
			+ GroupChatData._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ GroupChatData.TIME + " TEXT," + GroupChatData.CONTENT + " TEXT,"
			+ GroupChatData.NAME + " TEXT," + GroupChatData.UID + " TEXT,"
			+ GroupChatData.HEAD_IMAG + " TEXT," + GroupChatData.SHOW_TIME
			+ " VARCHAR(15) NOT NULL," + GroupChatData.IS_COM + " INTEGER"
			+ ");";

	public static void getDatebase(Context context) {
		if (null == proDatabase) {
			ProJectDbHelper proJectDbHelper = new ProJectDbHelper(context);
			proDatabase = proJectDbHelper.getWritableDatabase();
		}

	}

	/**
	 * 新建好友消息信息表
	 * 
	 * @param id
	 */
	public static void createFriTab(String id, Context context) {
		if (null == proDatabase) {
			getDatebase(context);
		}
		proDatabase.execSQL(CREATE_FRI_INFO_SQL_HEAD + id
				+ CREATE_FRI_INFO_TALE);
	}

	/**
	 * 新建群组消息表
	 * 
	 * @param id
	 *            群组ID
	 * @param context
	 *            上下文
	 */
	public static void createGrouTable(String id, Context context) {
		if (null == proDatabase) {
			getDatebase(context);
		}

		proDatabase.execSQL(CREATE_GROUP_INFO_SQL_HEAD + id
				+ CREATE_GROUP_INFO_TALE);

	}

}
