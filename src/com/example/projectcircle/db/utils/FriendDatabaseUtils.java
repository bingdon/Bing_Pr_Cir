package com.example.projectcircle.db.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.projectcircle.bean.FriendDataBean;
import com.example.projectcircle.bean.FriendDate;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.interfaces.FriendDatabasePle;

public class FriendDatabaseUtils implements FriendDatabasePle {

	public FriendDatabaseUtils(Context context) {
		ProJectDatebase.getDatebase(context);
	}
	@Override
	public long update(String content, String time, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 删除好友
	 */
	@Override
	public Object delete(long id) {
		// TODO Auto-generated method stub

		int del = -1;
		try {
			String where = FriendDate._ID + " = ?";
			String[] whereArgs = { String.valueOf(id) };
			del = ProJectDatebase.proDatabase.delete(FriendDate.TAB_NAME,
					where, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return del;

	}
	
	public Object delete(String id) {
		// TODO Auto-generated method stub

		int del = -1;
		try {
			String where = FriendDate.FRI_ID + " = ?";
			String[] whereArgs = { String.valueOf(id) };
			del = ProJectDatebase.proDatabase.delete(FriendDate.TAB_NAME,
					where, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return del;

	}
	
	

	@Override
	public long insert(String name, String friid, String headimg) {
		// TODO Auto-generated method stub

		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(FriendDate.FRI_NAME, name);
			values.put(FriendDate.FRI_ID, friid);
			values.put(FriendDate.HEAD_IMG, headimg);

			id = ProJectDatebase.proDatabase.insert(FriendDate.TAB_NAME, null,
					values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
	}

	@Override
	public long insert(String content, String time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object queryData() {
		// TODO Auto-generated method stub
		List<FriendDataBean> list = new ArrayList<FriendDataBean>();
		Cursor cursor = null;
		try {
			// 获得数据库对象,如过数据库不存在则创建
			// 查询表中数据,获取游标
			cursor = ProJectDatebase.proDatabase.query(FriendDate.TAB_NAME,
					null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(FriendDate._ID);
			int nameIndex = cursor.getColumnIndex(FriendDate.FRI_NAME);
			int headIndex = cursor.getColumnIndex(FriendDate.HEAD_IMG);
			int friid = cursor.getColumnIndex(FriendDate.FRI_ID);

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				FriendDataBean friendDataBean = new FriendDataBean();
				friendDataBean.set_ID(cursor.getInt(idIndex));
				friendDataBean.setFriendname(cursor.getString(nameIndex));
				friendDataBean.setFriendid(cursor.getString(friid));
				friendDataBean.setFriendhead(cursor.getString(headIndex));
				list.add(friendDataBean);
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
		FriendDataBean friendDataBean = new FriendDataBean();
		Cursor cursor = null;
		try {
			String where = FriendDate._ID + " = ?";
			String[] whereValue = { String.valueOf(id) };
			cursor = ProJectDatebase.proDatabase.query(FriendDate.TAB_NAME,
					null, where, whereValue, null, null, null);
			
			 if(cursor == null){
	          	 return null;
	          }
	       	  
     	      if(cursor.moveToFirst()){
     	    	  
     	    	 int idIndex = cursor.getColumnIndex(FriendDate._ID);
     			int nameIndex = cursor.getColumnIndex(FriendDate.FRI_NAME);
     			int headIndex = cursor.getColumnIndex(FriendDate.HEAD_IMG);
     			int friid = cursor.getColumnIndex(FriendDate.FRI_ID); 
      
     			friendDataBean.set_ID(cursor.getInt(idIndex));
				friendDataBean.setFriendname(cursor.getString(nameIndex));
				friendDataBean.setFriendid(cursor.getString(friid));
				friendDataBean.setFriendhead(cursor.getString(headIndex));
		        
		    }
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return friendDataBean;
	}

	@Override
	public Object queryDataId(int frid) {
		// TODO Auto-generated method stub
		FriendDataBean friendDataBean = new FriendDataBean();
		Cursor cursor = null;
		try {
			String where = FriendDate.FRI_ID + " = ?";
			String[] whereValue = { String.valueOf(frid) };
			cursor = ProJectDatebase.proDatabase.query(FriendDate.TAB_NAME,
					null, where, whereValue, null, null, null);
			
			 if(cursor == null){
	          	 return null;
	          }
	       	  
     	      if(cursor.moveToFirst()){
     	    	  
     	    	 int idIndex = cursor.getColumnIndex(FriendDate._ID);
     			int nameIndex = cursor.getColumnIndex(FriendDate.FRI_NAME);
     			int headIndex = cursor.getColumnIndex(FriendDate.HEAD_IMG);
     			int friid = cursor.getColumnIndex(FriendDate.FRI_ID); 
      
     			friendDataBean.set_ID(cursor.getInt(idIndex));
				friendDataBean.setFriendname(cursor.getString(nameIndex));
				friendDataBean.setFriendid(cursor.getString(friid));
				friendDataBean.setFriendhead(cursor.getString(headIndex));
		        
		    }
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return friendDataBean;
	}

	@Override
	public Object deleteAll() {
		// TODO Auto-generated method stub
		long del = -1;

		try {
			del = ProJectDatebase.proDatabase.delete(FriendDate.TAB_NAME, null,
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
	public Object update(String name, String friid, String headimg, long id) {
		// TODO Auto-generated method stub
		long updatepostion=0;
    	try {
	    	//使用execSQL方法向表中插入数据
	    	//db.execSQL("INSERT INTO USER(NAME) VALUES('"+name+"')");
	
	    	//使用insert方法向表中插入数据
	    	//创建ContentValues对象存储"列名-列值"映射
	    	ContentValues values = new ContentValues();	
	    	values.put(FriendDate.FRI_NAME, name);
			values.put(FriendDate.FRI_ID, friid);
			values.put(FriendDate.HEAD_IMG, headimg);
	    	String where =  FriendDate._ID+" = ?";
		    String[] whereValue = { String.valueOf(id) };
	    	//调用方法插入数据
	    	updatepostion=ProJectDatebase.proDatabase.update(FriendDate.TAB_NAME, values, where, whereValue);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;

	}
	
	
	
	public long update(String name, String friid, String headimg, String id) {
		// TODO Auto-generated method stub
		long updatepostion=0;
    	try {
	    	//使用execSQL方法向表中插入数据
	    	//db.execSQL("INSERT INTO USER(NAME) VALUES('"+name+"')");
	
	    	//使用insert方法向表中插入数据
	    	//创建ContentValues对象存储"列名-列值"映射
	    	ContentValues values = new ContentValues();	
	    	values.put(FriendDate.FRI_NAME, name);
			values.put(FriendDate.FRI_ID, friid);
			values.put(FriendDate.HEAD_IMG, headimg);
	    	String where =  FriendDate.FRI_ID+" = ?";//修改条件
		    String[] whereValue = { String.valueOf(id) };//修改条件的参数
	    	//调用方法插入数据
		    //向数据库的表中插入记录时，需要先将数据包含在一个ContentValues中，向该对象当中插入键值对，其中键是列名，
		   // 值是希望插入到这一列的值，值必须和数据库当中的数据类型一致。
		   // 接着调用Databasehelper的getWritableDatabase方法来获得可以写入的Databasehelper对象，再向其中insert记录。注意调用DatabaseHelper对象的insert,update或者query方法的参数的传递。
		    
	    	updatepostion=ProJectDatebase.proDatabase.update(FriendDate.TAB_NAME, values, where, whereValue);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;

	}
	

	@Override
	public Object queryData(int pagersize) {
		// TODO Auto-generated method stub
		return null;
	}

}
