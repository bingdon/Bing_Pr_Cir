package com.example.projectcircle.db.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.projectcircle.bean.GroupData;
import com.example.projectcircle.bean.GroupDataBean;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.interfaces.GroupDateInterface;

public class GroupDatabaseUtils implements GroupDateInterface {

	public GroupDatabaseUtils(Context context) {
		ProJectDatebase.getDatebase(context);
	}

	/**
	 * 删除群组
	 */
	@Override
	public long delete(long id) {
		// TODO Auto-generated method stub
		int del = -1;
		try {
			String where = GroupData._ID + " = ?";
			String[] whereArgs = { String.valueOf(id) };
			del = ProJectDatebase.proDatabase.delete(GroupData.TABLE_NAME,
					where, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return del;
	}

	/**
	 * 根据群组id退群
	 */
	@Override
	public long delete(String id) {
		// TODO Auto-generated method stub
		int del = -1;
		try {
			String where = GroupData.GROUP_ID + " = ?";
			String[] whereArgs = { String.valueOf(id) };
			del = ProJectDatebase.proDatabase.delete(GroupData.TABLE_NAME,
					where, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return del;
	}

	@Override
	public Object queryData() {
		// TODO Auto-generated method stub

		List<GroupDataBean> list = new ArrayList<GroupDataBean>();
		Cursor cursor = null;
		try {
			// 获得数据库对象,如过数据库不存在则创建
			// 查询表中数据,获取游标
			cursor = ProJectDatebase.proDatabase.query(GroupData.TABLE_NAME,
					null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(GroupData._ID);
			int nameIndex = cursor.getColumnIndex(GroupData.GROUP_NAME);
			int headIndex = cursor.getColumnIndex(GroupData.HEAD_IMG);
			int groidIndex = cursor.getColumnIndex(GroupData.GROUP_ID);
			int uidIndex = cursor.getColumnIndex(GroupData.GROUP_UID);

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				GroupDataBean groupDataBean = new GroupDataBean();
				groupDataBean.set_id(cursor.getInt(idIndex));
				groupDataBean.setGname(cursor.getString(nameIndex));
				groupDataBean.setId(cursor.getString(groidIndex));
				groupDataBean.setHeadimage(cursor.getString(headIndex));
				groupDataBean.setUid(cursor.getString(uidIndex));
				list.add(groupDataBean);
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
		List<GroupDataBean> list = new ArrayList<GroupDataBean>();
		Cursor cursor = null;
		try {
			// 获得数据库对象,如过数据库不存在则创建
			// 查询表中数据,获取游标
			cursor = ProJectDatebase.proDatabase.query(GroupData.TABLE_NAME,
					null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(GroupData._ID);
			int nameIndex = cursor.getColumnIndex(GroupData.GROUP_NAME);
			int headIndex = cursor.getColumnIndex(GroupData.HEAD_IMG);
			int groidIndex = cursor.getColumnIndex(GroupData.GROUP_ID);
			int uidIndex = cursor.getColumnIndex(GroupData.GROUP_UID);

			for (cursor.moveToFirst(); !(cursor.isAfterLast())&&cursor.getPosition()<pagersize; cursor
					.moveToNext()) {
				GroupDataBean groupDataBean = new GroupDataBean();
				groupDataBean.set_id(cursor.getInt(idIndex));
				groupDataBean.setGname(cursor.getString(nameIndex));
				groupDataBean.setId(cursor.getString(groidIndex));
				groupDataBean.setHeadimage(cursor.getString(headIndex));
				groupDataBean.setUid(cursor.getString(uidIndex));
				list.add(groupDataBean);
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
		GroupDataBean groupDataBean = new GroupDataBean();
		Cursor cursor = null;
		try {
			String where = GroupData._ID + " = ?";
			String[] whereValue = { String.valueOf(id) };
			cursor = ProJectDatebase.proDatabase.query(GroupData.TABLE_NAME,
					null, where, whereValue, null, null, null);
			
			 if(cursor == null){
	          	 return null;
	          }
	       	  
     	      if(cursor.moveToFirst()){
     	    	  
     	    	 int idIndex = cursor.getColumnIndex(GroupData._ID);
     			int nameIndex = cursor.getColumnIndex(GroupData.GROUP_NAME);
     			int headIndex = cursor.getColumnIndex(GroupData.HEAD_IMG);
     			int groidIndex = cursor.getColumnIndex(GroupData.GROUP_ID);
     			int uidIndex = cursor.getColumnIndex(GroupData.GROUP_UID);
      
     			
				groupDataBean.set_id(cursor.getInt(idIndex));
				groupDataBean.setGname(cursor.getString(nameIndex));
				groupDataBean.setId(cursor.getString(groidIndex));
				groupDataBean.setHeadimage(cursor.getString(headIndex));
				groupDataBean.setUid(cursor.getString(uidIndex));
		        
		    }
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return groupDataBean;
	}

	@Override
	public Object queryDataId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteAll() {
		// TODO Auto-generated method stub
		long del = -1;

		try {
			del = ProJectDatebase.proDatabase.delete(GroupData.TABLE_NAME, null,
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

	@Override
	public long insert(String gname, String headimage, String gaddress,
			String gid, String uid) {
		// TODO Auto-generated method stub

		long id=-1;
    	try {
	    	ContentValues values = new ContentValues();	
	    	values.put(GroupData.GROUP_NAME, gname);
			values.put(GroupData.HEAD_IMG, headimage);
			values.put(GroupData.ADDRESS, gaddress);
			values.put(GroupData.GROUP_ID, gid);
			values.put(GroupData.GROUP_UID, uid);
	    	//调用方法插入数据
	    	id=ProJectDatebase.proDatabase.insert(GroupData.TABLE_NAME, null, values);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
		
	}

	@Override
	public long update(String gname, String headimage, String gaddress,
			String gid, String uid) {
		// TODO Auto-generated method stub

		long updatepostion=0;
    	try {
	    	ContentValues values = new ContentValues();	
	    	values.put(GroupData.GROUP_NAME, gname);
			values.put(GroupData.HEAD_IMG, headimage);
			values.put(GroupData.ADDRESS, gaddress);
			values.put(GroupData.GROUP_ID, gid);
			values.put(GroupData.GROUP_UID, uid);
	    	String where =  GroupData.GROUP_ID+" = ?";
		    String[] whereValue = { String.valueOf(gid) };
	    	//调用方法插入数据
	    	updatepostion=ProJectDatebase.proDatabase.update(GroupData.TABLE_NAME, values, where, whereValue);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;
		
	}

	@Override
	public long update(String gname, String headimage, String gaddress,
			String gid, String uid, long id) {
		// TODO Auto-generated method stub
		
		long updatepostion=0;
    	try {
	    	ContentValues values = new ContentValues();	
	    	values.put(GroupData.GROUP_NAME, gname);
			values.put(GroupData.HEAD_IMG, headimage);
			values.put(GroupData.ADDRESS, gaddress);
			values.put(GroupData.GROUP_ID, gid);
			values.put(GroupData.GROUP_UID, uid);
	    	String where =  GroupData._ID+" = ?";
		    String[] whereValue = { String.valueOf(id) };
	    	//调用方法插入数据
	    	updatepostion=ProJectDatebase.proDatabase.update(GroupData.TABLE_NAME, values, where, whereValue);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;
		

	}

}
