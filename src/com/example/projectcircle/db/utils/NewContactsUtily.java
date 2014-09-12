package com.example.projectcircle.db.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.projectcircle.bean.NewConstactBean;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.interfaces.NewContactsInterface;
import com.example.projectcircle.db.table.ContactsTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class NewContactsUtily implements NewContactsInterface {

	public NewContactsUtily(Context context) {
		ProJectDatebase.getDatebase(context);
	}

	@Override
	public long delete(NewConstactBean newConstactBean) {
		// TODO Auto-generated method stub
		int del = -1;
		try {
			String where = ContactsTable._ID + " = ?";
			String[] whereArgs = { String.valueOf(newConstactBean.get_id()) };
			del = ProJectDatebase.proDatabase.delete(ContactsTable.TABLE_NAME,
					where, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return del;
	}

	@Override
	public List<NewConstactBean> queryData() {
		// TODO Auto-generated method stub
		List<NewConstactBean> list = new ArrayList<NewConstactBean>();
		Cursor cursor = null;
		try {
			// 获得数据库对象,如过数据库不存在则创建
			// 查询表中数据,获取游标
			cursor = ProJectDatebase.proDatabase.query(
					ContactsTable.TABLE_NAME, null, null, null, null, null,
					"_ID desc");
			int idIndex = cursor.getColumnIndex(ContactsTable._ID);
			int teltIndex = cursor.getColumnIndex(ContactsTable.TEL);
			int timeIndex = cursor.getColumnIndex(ContactsTable.TIME);
			int headIndex = cursor.getColumnIndex(ContactsTable.HEAD_IMAG);
			int nameIndex = cursor.getColumnIndex(ContactsTable.NAME);
			int typeIndex = cursor.getColumnIndex(ContactsTable.TYPE);
			int uidIndex = cursor.getColumnIndexOrThrow(ContactsTable.UID);
			int stateIndex = cursor.getColumnIndexOrThrow(ContactsTable.STATE);
			int cidIndex = cursor.getColumnIndexOrThrow(ContactsTable.CID);

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				NewConstactBean constactBean = new NewConstactBean();
				constactBean.set_id(cursor.getInt(idIndex));
				constactBean.setTel(cursor.getString(teltIndex));
				constactBean.setCreatetime(cursor.getString(timeIndex));
				constactBean.setHeadimage(cursor.getString(headIndex));
				constactBean.setType_(cursor.getInt(typeIndex));
				constactBean.setId(cursor.getString(uidIndex));
				constactBean.setUsername(cursor.getString(nameIndex));
				constactBean.setIsAccpet(cursor.getInt(stateIndex));
				constactBean.setCid(cursor.getString(cidIndex));
				list.add(constactBean);
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
	public List<NewConstactBean> queryData(int pagersize) {
		// TODO Auto-generated method stub
		List<NewConstactBean> list = new ArrayList<NewConstactBean>();
		Cursor cursor = null;
		try {
			cursor = ProJectDatebase.proDatabase.query(
					ContactsTable.TABLE_NAME, null, null, null, null, null,
					"_ID desc");
			int idIndex = cursor.getColumnIndex(ContactsTable._ID);
			int teltIndex = cursor.getColumnIndex(ContactsTable.TEL);
			int timeIndex = cursor.getColumnIndex(ContactsTable.TIME);
			int headIndex = cursor.getColumnIndex(ContactsTable.HEAD_IMAG);
			int nameIndex = cursor.getColumnIndex(ContactsTable.NAME);
			int typeIndex = cursor.getColumnIndex(ContactsTable.TYPE);
			int uidIndex = cursor.getColumnIndexOrThrow(ContactsTable.UID);
			int stateIndex = cursor.getColumnIndexOrThrow(ContactsTable.STATE);
			int cidIndex = cursor.getColumnIndexOrThrow(ContactsTable.CID);

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				NewConstactBean constactBean = new NewConstactBean();
				constactBean.set_id(cursor.getInt(idIndex));
				constactBean.setTel(cursor.getString(teltIndex));
				constactBean.setCreatetime(cursor.getString(timeIndex));
				constactBean.setHeadimage(cursor.getString(headIndex));
				constactBean.setType_(cursor.getInt(typeIndex));
				constactBean.setId(cursor.getString(uidIndex));
				constactBean.setUsername(cursor.getString(nameIndex));
				constactBean.setIsAccpet(cursor.getInt(stateIndex));
				constactBean.setCid(cursor.getString(cidIndex));
				list.add(constactBean);
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
	public List<NewConstactBean> queryDataId(NewConstactBean newConstactBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(NewConstactBean newConstactBean) {
		// TODO Auto-generated method stub
		long updatepostion = 0;
		try {
			ContentValues values = new ContentValues();
			values.put(ContactsTable.NAME, newConstactBean.getUsername());
			values.put(ContactsTable.UID, newConstactBean.getId());
			values.put(ContactsTable.HEAD_IMAG, newConstactBean.getHeadimage());
			values.put(ContactsTable.TEL, newConstactBean.getTel());
			values.put(ContactsTable.TIME, newConstactBean.getCreatetime());
			values.put(ContactsTable.TYPE, newConstactBean.getType_());
			values.put(ContactsTable.STATE, newConstactBean.getIsAccpet());
			values.put(ContactsTable.CID, newConstactBean.getCid());
			String where = ContactsTable.TEL + " = ?";
			String[] whereValue = { String.valueOf(newConstactBean.getTel()) };
			// 调用方法插入数据
			updatepostion = ProJectDatebase.proDatabase.update(
					ContactsTable.TABLE_NAME, values, where, whereValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (updatepostion < 1) {
			insert(newConstactBean);
		}

		return updatepostion;
	}

	@Override
	public long deleteAll() {
		// TODO Auto-generated method stub
		long del = -1;

		try {
			del = ProJectDatebase.proDatabase.delete(ContactsTable.TABLE_NAME,
					null, null);
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
	public long insert(NewConstactBean newConstactBean) {
		// TODO Auto-generated method stub
		long id = -1;
		try {
			ContentValues values = new ContentValues();
			values.put(ContactsTable.NAME, newConstactBean.getUsername());
			values.put(ContactsTable.UID, newConstactBean.getId());
			values.put(ContactsTable.HEAD_IMAG, newConstactBean.getHeadimage());
			values.put(ContactsTable.TEL, newConstactBean.getTel());
			values.put(ContactsTable.TIME, newConstactBean.getCreatetime());
			values.put(ContactsTable.TYPE, newConstactBean.getType_());
			values.put(ContactsTable.STATE, newConstactBean.getIsAccpet());
			values.put(ContactsTable.CID, newConstactBean.getCid());

			id = ProJectDatebase.proDatabase.insert(ContactsTable.TABLE_NAME,
					null, values);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return id;
	}

}
