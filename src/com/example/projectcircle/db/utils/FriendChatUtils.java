package com.example.projectcircle.db.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.projectcircle.bean.FriendChat;
import com.example.projectcircle.bean.FriendChatBean;
import com.example.projectcircle.bean.FriendDate;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.interfaces.FriendChatAbs;
import com.example.projectcircle.other.ChatMsgEntity;

public class FriendChatUtils implements FriendChatAbs {

	private String friendid = "";

	public FriendChatUtils(Context context, String friendid) {
		this.friendid = friendid;
		ProJectDatebase.getDatebase(context);
	}

	@Override
	public Object update(String content, String time, String showtime, long id) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public long update(String content, String time, int id) {
		// TODO Auto-generated method stub
		long updatepostion = 0;
		try {
			ContentValues values = new ContentValues();
			values.put(FriendChat.CONTENT, content);
			values.put(FriendChat.TIME, time);
			String where = FriendChat._ID + " = ?";
			String[] whereValue = { String.valueOf(id) };
			// 调用方法插入数据
			updatepostion = ProJectDatebase.proDatabase
					.update(FriendChat.TABLE_NAME + friendid, values, where,
							whereValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;
	}

	@Override
	public Object delete(long id) {
		// TODO Auto-generated method stub
		int del = -1;
		try {
			String where = FriendDate._ID + " = ?";
			String[] whereArgs = { String.valueOf(id) };
			del = ProJectDatebase.proDatabase.delete(FriendChat.TABLE_NAME
					+ friendid, where, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return del;
	}

	@Override
	public long insert(String content, String time, String showtime) {
		// TODO Auto-generated method stub

		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(FriendChat.CONTENT, content);
			values.put(FriendChat.TIME, time);
			values.put(FriendChat.SHOW_TIME, showtime);

			id = ProJectDatebase.proDatabase.insert(FriendChat.TABLE_NAME
					+ friendid, null, values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;

	}

	@Override
	public long insert(String content, String time) {
		// TODO Auto-generated method stub
		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(FriendChat.CONTENT, content);
			values.put(FriendChat.TIME, time);

			id = ProJectDatebase.proDatabase.insert(FriendChat.TABLE_NAME
					+ friendid, null, values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
	}

	@Override
	public Object queryData() {
		// TODO Auto-generated method stub
		List<FriendChatBean> list = new ArrayList<FriendChatBean>();
		Cursor cursor = null;
		try {
			// 获得数据库对象,如过数据库不存在则创建
			// 查询表中数据,获取游标
			cursor = ProJectDatebase.proDatabase.query(FriendChat.TABLE_NAME
					+ friendid, null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(FriendChat._ID);
			int contentIndex = cursor.getColumnIndex(FriendChat.CONTENT);
			int timeIndex = cursor.getColumnIndex(FriendChat.TIME);
			int showtIndex = cursor.getColumnIndex(FriendChat.SHOW_TIME);
			int iscomIndex = cursor.getColumnIndex(FriendChat.IS_COM);
			int headIndex = cursor.getColumnIndex(FriendChat.HEAD_IMAGE);
			int nameIndex = cursor.getColumnIndex(FriendChat.TITLE);
			int uidIndex = 0;
			try {
				uidIndex = cursor.getColumnIndexOrThrow(FriendChat.UID);
			} catch (Exception e) {
				// TODO: handle exception
			}

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				FriendChatBean friendChatBean = new FriendChatBean();
				friendChatBean.set_id(cursor.getInt(idIndex));
				friendChatBean.setContent(cursor.getString(contentIndex));
				friendChatBean.setRealtime(cursor.getString(timeIndex));
				friendChatBean.setShowTime(cursor.getString(showtIndex));
				friendChatBean.setIscom(cursor.getInt(iscomIndex));
				friendChatBean.setHeadimg(cursor.getString(headIndex));
				friendChatBean.setName(cursor.getString(nameIndex));
				try {
					friendChatBean.setUid(cursor.getString(uidIndex));
				} catch (Exception e) {
					// TODO: handle exception
				}

				list.add(friendChatBean);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				// 关闭游标
				cursor.close();
			}

		}

		return list;
	}

	@Override
	public Object queryDataId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object queryDataId(int frid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteAll() {
		// TODO Auto-generated method stub
		long del = -1;

		try {
			del = ProJectDatebase.proDatabase.delete(FriendChat.TABLE_NAME
					+ friendid, null, null);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return del;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (ProJectDatebase.proDatabase != null
				&& ProJectDatebase.proDatabase.isOpen()) {
			// 关闭数据库对象
			ProJectDatebase.proDatabase.close();
			ProJectDatebase.proDatabase = null;
		}
	}

	@Override
	public Object queryData(int pagersize) {
		// TODO Auto-generated method stub
		List<FriendChatBean> list = new ArrayList<FriendChatBean>();
		Cursor cursor = null;
		try {
			// 获得数据库对象,如过数据库不存在则创建
			// 查询表中数据,获取游标
			cursor = ProJectDatebase.proDatabase.query(FriendChat.TABLE_NAME
					+ friendid, null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(FriendChat._ID);
			int contentIndex = cursor.getColumnIndex(FriendChat.CONTENT);
			int timeIndex = cursor.getColumnIndex(FriendChat.TIME);
			int showtIndex = cursor.getColumnIndex(FriendChat.SHOW_TIME);
			int iscomIndex = cursor.getColumnIndex(FriendChat.IS_COM);

			for (cursor.moveToFirst(); !(cursor.isAfterLast())
					&& cursor.getPosition() < pagersize; cursor.moveToNext()) {
				FriendChatBean friendChatBean = new FriendChatBean();
				friendChatBean.set_id(cursor.getInt(idIndex));
				friendChatBean.setContent(cursor.getString(contentIndex));
				friendChatBean.setRealtime(cursor.getString(timeIndex));
				friendChatBean.setShowTime(cursor.getString(showtIndex));
				friendChatBean.setIscom(cursor.getInt(iscomIndex));
				list.add(friendChatBean);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				// 关闭游标
				cursor.close();
			}

		}

		return list;
	}

	@Override
	public Object update(String content, String time, String showtime,
			int iscom, long id) {
		// TODO Auto-generated method stub
		long updatepostion = 0;
		try {
			ContentValues values = new ContentValues();
			values.put(FriendChat.CONTENT, content);
			values.put(FriendChat.TIME, time);
			values.put(FriendChat.SHOW_TIME, showtime);
			values.put(FriendChat.IS_COM, iscom);
			String where = FriendChat._ID + " = ?";
			String[] whereValue = { String.valueOf(id) };
			// 调用方法插入数据
			updatepostion = ProJectDatebase.proDatabase
					.update(FriendChat.TABLE_NAME + friendid, values, where,
							whereValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;
	}

	/**
	 * @deprecated 此方法已过期 请使用{@link #insert(ChatMsgEntity)}
	 */
	@Override
	public long insert(String content, String time, String showtime, int iscom) {
		// TODO Auto-generated method stub
		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(FriendChat.CONTENT, content);
			values.put(FriendChat.TIME, time);
			values.put(FriendChat.SHOW_TIME, showtime);
			values.put(FriendChat.IS_COM, iscom);

			id = ProJectDatebase.proDatabase.insert(FriendChat.TABLE_NAME
					+ friendid, null, values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
	}

	public long insert(ChatMsgEntity chatMsgEntity) {
		// TODO Auto-generated method stub
		long id = -1;
		try {
			int iscom = 0;
			if (chatMsgEntity.getMsgType()) {
				iscom = 1;
			}
			ContentValues values = new ContentValues();
			values.put(FriendChat.CONTENT, chatMsgEntity.getText());
			values.put(FriendChat.TIME, chatMsgEntity.getDate());
			values.put(FriendChat.SHOW_TIME, chatMsgEntity.getDate());
			values.put(FriendChat.HEAD_IMAGE, chatMsgEntity.getHeadimgString());
			values.put(FriendChat.TITLE, chatMsgEntity.getName());
			try {
				values.put(FriendChat.UID, chatMsgEntity.getUid());
			} catch (Exception e) {
				// TODO: handle exception
			}

			values.put(FriendChat.IS_COM, iscom);

			id = ProJectDatebase.proDatabase.insert(FriendChat.TABLE_NAME
					+ friendid, null, values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
	}

}
