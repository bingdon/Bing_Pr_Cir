package com.example.projectcircle.db.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.projectcircle.bean.FriendChatBean;
import com.example.projectcircle.bean.GroupChatBean;
import com.example.projectcircle.bean.GroupChatData;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.interfaces.FriendChatAbs;
import com.example.projectcircle.db.interfaces.GroupChatInterface;

public class GroupChatUtils implements GroupChatInterface {
	private String gid = "";

	public GroupChatUtils(Context context, String gid) {
		this.gid = gid;
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
			values.put(GroupChatData.CONTENT, content);
			values.put(GroupChatData.TIME, time);
			String where = GroupChatData._ID + " = ?";
			String[] whereValue = { String.valueOf(id) };
			// 调用方法插入数据
			updatepostion = ProJectDatebase.proDatabase.update(
					GroupChatData.TABLE_NAME + gid, values, where, whereValue);
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
			String where = GroupChatData._ID + " = ?";
			String[] whereArgs = { String.valueOf(id) };
			del = ProJectDatebase.proDatabase.delete(GroupChatData.TABLE_NAME
					+ gid, where, whereArgs);
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
			values.put(GroupChatData.CONTENT, content);
			values.put(GroupChatData.TIME, time);
			values.put(GroupChatData.SHOW_TIME, showtime);

			id = ProJectDatebase.proDatabase.insert(GroupChatData.TABLE_NAME
					+ gid, null, values);

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
			values.put(GroupChatData.CONTENT, content);
			values.put(GroupChatData.TIME, time);

			id = ProJectDatebase.proDatabase.insert(GroupChatData.TABLE_NAME
					+ gid, null, values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
	}

	@Override
	public Object queryData() {
		// TODO Auto-generated method stub
		List<GroupChatBean> list = new ArrayList<GroupChatBean>();
		Cursor cursor = null;
		try {
			// 获得数据库对象,如过数据库不存在则创建
			// 查询表中数据,获取游标
			cursor = ProJectDatebase.proDatabase.query(GroupChatData.TABLE_NAME
					+ gid, null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(GroupChatData._ID);
			int contentIndex = cursor.getColumnIndex(GroupChatData.CONTENT);
			int timeIndex = cursor.getColumnIndex(GroupChatData.TIME);
			int showtIndex = cursor.getColumnIndex(GroupChatData.SHOW_TIME);
			int iscomIndex = cursor.getColumnIndex(GroupChatData.IS_COM);
			int nameIndex = cursor.getColumnIndex(GroupChatData.NAME);
			int headIndex = cursor.getColumnIndex(GroupChatData.HEAD_IMAG);
			int uidIndex = cursor.getColumnIndex(GroupChatData.UID);

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				GroupChatBean groupChatBean = new GroupChatBean();
				groupChatBean.set_id(cursor.getInt(idIndex));
				groupChatBean.setContent(cursor.getString(contentIndex));
				groupChatBean.setRealtime(cursor.getString(timeIndex));
				groupChatBean.setShowTime(cursor.getString(showtIndex));
				groupChatBean.setIscom(cursor.getInt(iscomIndex));
				groupChatBean.setHeadimg(cursor.getString(headIndex));
				groupChatBean.setName(cursor.getString(nameIndex));
				groupChatBean.setUid(cursor.getString(uidIndex));
				list.add(groupChatBean);
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
			del = ProJectDatebase.proDatabase.delete(GroupChatData.TABLE_NAME
					+ gid, null, null);
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
		List<GroupChatBean> list = new ArrayList<GroupChatBean>();
		Cursor cursor = null;
		try {
			// 获得数据库对象,如过数据库不存在则创建
			// 查询表中数据,获取游标
			cursor = ProJectDatebase.proDatabase.query(GroupChatData.TABLE_NAME
					+ gid, null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(GroupChatData._ID);
			int contentIndex = cursor.getColumnIndex(GroupChatData.CONTENT);
			int timeIndex = cursor.getColumnIndex(GroupChatData.TIME);
			int showtIndex = cursor.getColumnIndex(GroupChatData.SHOW_TIME);
			int iscomIndex = cursor.getColumnIndex(GroupChatData.IS_COM);
			int nameIndex = cursor.getColumnIndex(GroupChatData.NAME);
			int headIndex = cursor.getColumnIndex(GroupChatData.HEAD_IMAG);
			int uidIndex = cursor.getColumnIndex(GroupChatData.UID);

			for (cursor.moveToFirst(); !(cursor.isAfterLast())
					&& cursor.getPosition() < pagersize; cursor.moveToNext()) {
				GroupChatBean groupChatBean = new GroupChatBean();
				groupChatBean.set_id(cursor.getInt(idIndex));
				groupChatBean.setContent(cursor.getString(contentIndex));
				groupChatBean.setRealtime(cursor.getString(timeIndex));
				groupChatBean.setShowTime(cursor.getString(showtIndex));
				groupChatBean.setIscom(cursor.getInt(iscomIndex));
				groupChatBean.setHeadimg(cursor.getString(headIndex));
				groupChatBean.setName(cursor.getString(nameIndex));
				groupChatBean.setUid(cursor.getString(uidIndex));
				list.add(groupChatBean);
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
			values.put(GroupChatData.CONTENT, content);
			values.put(GroupChatData.TIME, time);
			values.put(GroupChatData.SHOW_TIME, showtime);
			values.put(GroupChatData.IS_COM, iscom);
			String where = GroupChatData._ID + " = ?";
			String[] whereValue = { String.valueOf(id) };
			// 调用方法插入数据
			updatepostion = ProJectDatebase.proDatabase.update(
					GroupChatData.TABLE_NAME + gid, values, where, whereValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;
	}

	@Override
	public long insert(String content, String time, String showtime, int iscom) {
		// TODO Auto-generated method stub
		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(GroupChatData.CONTENT, content);
			values.put(GroupChatData.TIME, time);
			values.put(GroupChatData.SHOW_TIME, showtime);
			values.put(GroupChatData.IS_COM, iscom);

			id = ProJectDatebase.proDatabase.insert(GroupChatData.TABLE_NAME
					+ gid, null, values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
	}

	@Override
	public long insert(String uid, String name, String headimg, String content,
			String time, String showtime, int iscom) {
		// TODO Auto-generated method stub

		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(GroupChatData.CONTENT, content);
			values.put(GroupChatData.TIME, time);
			values.put(GroupChatData.SHOW_TIME, showtime);
			values.put(GroupChatData.IS_COM, iscom);
			values.put(GroupChatData.UID, uid);
			values.put(GroupChatData.NAME, name);
			values.put(GroupChatData.HEAD_IMAG, headimg);

			id = ProJectDatebase.proDatabase.insert(GroupChatData.TABLE_NAME
					+ gid, null, values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;

	}
}
