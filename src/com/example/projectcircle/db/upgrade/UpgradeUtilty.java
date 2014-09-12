package com.example.projectcircle.db.upgrade;

import com.example.projectcircle.db.ProJectDbHelper;
import com.example.projectcircle.db.table.ContactsTable;

import android.database.sqlite.SQLiteDatabase;

public class UpgradeUtilty {

	public static void upgrade1to2(SQLiteDatabase db) {
		db.execSQL(ProJectDbHelper.CREATE_NEW_CONSTACT_DB);
	}
	
	public static void upgrade2to3(SQLiteDatabase db) {
		try {
			db.delete(ContactsTable.TABLE_NAME, null, null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		db.execSQL(ProJectDbHelper.CREATE_NEW_CONSTACT_DB);
	}
	

}
