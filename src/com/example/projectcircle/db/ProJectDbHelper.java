package com.example.projectcircle.db;

import com.example.projectcircle.bean.FriendDate;
import com.example.projectcircle.bean.GroupData;
import com.example.projectcircle.bean.IsOrNotAddFriend;
import com.example.projectcircle.bean.MsgData;
import com.example.projectcircle.db.table.ContactsTable;
import com.example.projectcircle.db.upgrade.UpgradeUtilty;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract.CommonDataKinds.Contactables;

public class ProJectDbHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "pro.db";

	private static final int VERSION = 1;

	private static final String CREATE_FRIEND_DB = "CREATE TABLE "
			+ FriendDate.TAB_NAME + "(" + FriendDate._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ FriendDate.FRI_ID + " TEXT," + FriendDate.TEL + " VCHAR,"
			+ FriendDate.FRI_NAME + " TEXT," + FriendDate.HEAD_IMG + " TEXT"
			+ ")";

	private static final String CREATE_MSG_DB = "CREATE TABLE "
			+ MsgData.TABLE_NAME + "(" + MsgData._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + MsgData.FRIID
			+ " TEXT," + MsgData.CONTENT + " TEXT," + MsgData.NAME + " TEXT,"
			+ MsgData.HEAD_IMG_ + " TEXT," + MsgData.TIME + " TEXT,"
			+ MsgData.UNREAD_NUM + " INTEGER," + MsgData.TYPE + " INTEGER"
			+ ")";

	private static final String CREATE_GROUP_DB = "CREATE TABLE "
			+ GroupData.TABLE_NAME + "(" + GroupData._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ GroupData.GROUP_ID + " TEXT," + GroupData.GROUP_NAME + " TEXT,"
			+ GroupData.ADDRESS + " TEXT," + GroupData.HEAD_IMG + " TEXT,"
			+ GroupData.GROUP_UID + " TEXT" + ")";
	private static final String CREATE_IS_OR_NOT_ADD_FRI_DB = "CREATE TABLE "
			+ IsOrNotAddFriend.TAB_NAME + "(" + FriendDate._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ IsOrNotAddFriend.FRI_ID + " TEXT," + IsOrNotAddFriend.TEL
			+ " VCHAR," + IsOrNotAddFriend.FRI_NAME + " TEXT,"
			+ IsOrNotAddFriend.HEAD_IMG + " TEXT" + ")";

	public static final String CREATE_NEW_CONSTACT_DB = "CREATE TABLE "
			+ ContactsTable.TABLE_NAME + "(" + ContactsTable._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ ContactsTable.UID + " TEXT," + ContactsTable.TEL + " VCHAR,"
			+ ContactsTable.NAME + " TEXT," + ContactsTable.HEAD_IMAG
			+ " TEXT," + ContactsTable.TIME + " TEXT," + ContactsTable.CID
			+ " TEXT," + ContactsTable.TYPE + " INTEGER," + ContactsTable.STATE
			+ " INTEGER" + ")";

	public ProJectDbHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createtab(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion <= 1) {
			UpgradeUtilty.upgrade1to2(db);
		}
		
		if (oldVersion<=2) {
			UpgradeUtilty.upgrade2to3(db);
		}
		
	}

	private void createtab(SQLiteDatabase db) {
		db.execSQL(CREATE_FRIEND_DB);
		db.execSQL(CREATE_MSG_DB);
		db.execSQL(CREATE_GROUP_DB);
		db.execSQL(CREATE_IS_OR_NOT_ADD_FRI_DB);
		db.execSQL(CREATE_NEW_CONSTACT_DB);
	}

}
