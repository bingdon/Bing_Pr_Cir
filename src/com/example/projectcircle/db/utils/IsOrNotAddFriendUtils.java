package com.example.projectcircle.db.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.projectcircle.bean.IsOrNotAddFriend;
import com.example.projectcircle.bean.MsgData;
import com.example.projectcircle.bean.MsgDataBean;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.interfaces.MsgAbs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class IsOrNotAddFriendUtils implements MsgAbs{

	public IsOrNotAddFriendUtils(Context context){
		ProJectDatebase.getDatebase(context);
	}

	@Override
	public long update(String name, String frid, String content,
			String headimg, String time,int unreadnum, int type ,long id) {
		// TODO Auto-generated method stub
		long updatepostion=0;
    	try {
	    	ContentValues values = new ContentValues();	
	    	values.put(IsOrNotAddFriend.TAB_NAME, name);
			values.put(IsOrNotAddFriend.FRI_ID, frid);
	    	String where =  IsOrNotAddFriend._ID+" = ?";
		    String[] whereValue = { String.valueOf(id) };
	    	//调用方法插入数据
	    	updatepostion=ProJectDatebase.proDatabase.update(IsOrNotAddFriend.TAB_NAME, values, where, whereValue);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;
	}

	@Override
	public long update(String name, String frid, String content,
			String headimg, String time,int type,int unreadnum) {
		// TODO Auto-generated method stub
		long updatepostion=0;
    	try {
	    	ContentValues values = new ContentValues();	
	    	values.put(MsgData.NAME, name);
			values.put(MsgData.FRIID, frid);
			values.put(MsgData.HEAD_IMG_, headimg);
			values.put(MsgData.CONTENT, content);
			values.put(MsgData.TIME, time);
			values.put(MsgData.UNREAD_NUM, unreadnum);
			values.put(MsgData.TYPE, type);
	    	String where =  MsgData.FRIID+" = ?";
		    String[] whereValue = { String.valueOf(frid) };
	    	//调用方法插入数据
	    	updatepostion=ProJectDatebase.proDatabase.update(MsgData.TABLE_NAME, values, where, whereValue);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;
	}

	@Override
	public long delete(long id) {
		// TODO Auto-generated method stub
		
		int del = -1;
		try {
			String where = MsgData._ID + " = ?";
			String[] whereArgs = { String.valueOf(id) };
			del = ProJectDatebase.proDatabase.delete(MsgData.TABLE_NAME,
					where, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return del;
		
	}

	@Override
	public long delete(String id) {
		// TODO Auto-generated method stub
		int del = -1;
		try {
			String where = MsgData.FRIID + " = ?";
			String[] whereArgs = { String.valueOf(id) };
			del = ProJectDatebase.proDatabase.delete(MsgData.TABLE_NAME,
					where, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return del;
	}

	@Override
	public long insert(String name, String frid, String content,
			String headimg, String time,int type,int unreadnum) {
		// TODO Auto-generated method stub
		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(MsgData.NAME, name);
			values.put(MsgData.FRIID, frid);
			values.put(MsgData.HEAD_IMG_, headimg);
			values.put(MsgData.CONTENT, content);
			values.put(MsgData.TIME, time);
			values.put(MsgData.UNREAD_NUM, unreadnum);
			values.put(MsgData.TYPE, type);

			id = ProJectDatebase.proDatabase.insert(MsgData.TABLE_NAME, null,
					values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
	}

	@Override
	public Object queryData() {
		// TODO Auto-generated method stub
		List<MsgDataBean> list = new ArrayList<MsgDataBean>();
		Cursor cursor = null;
		try {
			// 获得数据库对象,如过数据库不存在则创建
			// 查询表中数据,获取游标
			cursor = ProJectDatebase.proDatabase.query(MsgData.TABLE_NAME,
					null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(MsgData._ID);
			int friidIndex=cursor.getColumnIndex(MsgData.FRIID);
			int contentIndex = cursor.getColumnIndex(MsgData.CONTENT);
			int timeIndex = cursor.getColumnIndex(MsgData.TIME);
			int headIndex=cursor.getColumnIndex(MsgData.HEAD_IMG_);
			int nameIndex=cursor.getColumnIndex(MsgData.NAME);
			int unreadIndex=cursor.getColumnIndex(MsgData.UNREAD_NUM);
			int typeIndex=cursor.getColumnIndex(MsgData.TYPE);

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				MsgDataBean msgDataBean = new MsgDataBean();
				msgDataBean.setFriend_id(cursor.getString(friidIndex));
				msgDataBean.set_id(cursor.getInt(idIndex));
				msgDataBean.setContent(cursor.getString(contentIndex));
				msgDataBean.setHead_img(cursor.getString(headIndex));
				msgDataBean.setName(cursor.getString(nameIndex));
				msgDataBean.setTime(cursor.getString(timeIndex));
				msgDataBean.setUnreadnum(cursor.getInt(unreadIndex));
				msgDataBean.setType(cursor.getInt(typeIndex));
				list.add(msgDataBean);
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
	public Object queryData(int pagersize) {
		// TODO Auto-generated method stub
		List<MsgDataBean> list = new ArrayList<MsgDataBean>();
		Cursor cursor = null;
		try {
			// 获得数据库对象,如过数据库不存在则创建
			// 查询表中数据,获取游标
			cursor = ProJectDatebase.proDatabase.query(MsgData.TABLE_NAME,
					null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(MsgData._ID);
			int friidIndex=cursor.getColumnIndex(MsgData.FRIID);
			int contentIndex = cursor.getColumnIndex(MsgData.CONTENT);
			int timeIndex = cursor.getColumnIndex(MsgData.TIME);
			int headIndex=cursor.getColumnIndex(MsgData.HEAD_IMG_);
			int nameIndex=cursor.getColumnIndex(MsgData.NAME);
			int unreadIndex=cursor.getColumnIndex(MsgData.UNREAD_NUM);
			int typeIndex=cursor.getColumnIndex(MsgData.TYPE);

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				MsgDataBean msgDataBean = new MsgDataBean();
				msgDataBean.setFriend_id(cursor.getString(friidIndex));
				msgDataBean.set_id(cursor.getInt(idIndex));
				msgDataBean.setContent(cursor.getString(contentIndex));
				msgDataBean.setHead_img(cursor.getString(headIndex));
				msgDataBean.setName(cursor.getString(nameIndex));
				msgDataBean.setTime(cursor.getString(timeIndex));
				msgDataBean.setUnreadnum(cursor.getInt(unreadIndex));
				msgDataBean.setType(cursor.getInt(typeIndex));
				list.add(msgDataBean);
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
			del = ProJectDatebase.proDatabase.delete(MsgData.TABLE_NAME, null,
					null);
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
	
	
	
}
