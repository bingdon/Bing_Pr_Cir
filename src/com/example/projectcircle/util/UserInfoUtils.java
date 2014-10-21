package com.example.projectcircle.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.db.utils.FriendDatabaseUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;

public class UserInfoUtils {

	/**
	 * 保存信息
	 * 
	 * @param info
	 * @param context
	 */
	public static void setPersonInfo(MyPersonBean info, Context context) {
		SharedPreferences preferences = context.getSharedPreferences("Login",
				Context.MODE_PRIVATE);

		Editor editor = preferences.edit();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream infoStream = new ObjectOutputStream(baos);
			infoStream.writeObject(info);
			String infobase64 = Base64.encodeToString(baos.toByteArray(),
					Base64.DEFAULT);
			editor.putString("personinfo", infobase64);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 提交修改
		editor.commit();
	}

	/**
	 * 获取信息
	 */
	public static void getPersonInfo(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Login", Context.MODE_PRIVATE);
		String personstr = sharedPreferences.getString("personinfo", "");////getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
		if (!TextUtils.isEmpty(personstr)) {
			byte[] bytepersonbase64 = Base64.decode(personstr.getBytes(),
					Base64.DEFAULT);
			ByteArrayInputStream bis = new ByteArrayInputStream(
					bytepersonbase64);
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(bis);
				try {
					MyPersonBean myPersonBean = (MyPersonBean) ois.readObject();
					MyApplication.setMyPersonBean(myPersonBean);

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 注销信息
	 * 
	 * @param context
	 */
	public static void delPersonInfo(Context context) {
		
		boolean a=context.getSharedPreferences("Login", Context.MODE_PRIVATE).edit()
				.clear().commit();//SharedPreferences是一个接口，程序是无法创建SharedPreferences实例的，可以通过Context.getSharedPreferences(String name,int mode)来得到一个SharedPreferences实例
		//name：是指文件名称，不需要加后缀.xml，系统会自动为我们添加上。一般这个文件存储在/data/data/<package name>/shared_prefs下(这个面试常问到)
//		boolean b=context.getSharedPreferences("LoginActivity",
//				Context.MODE_PRIVATE).edit().clear().commit();
		
		new FriendDatabaseUtils(context).deleteAll();
//		new GroupDatabaseUtils(context).deleteAll();
//		new MsgDataUtils(context).deleteAll();
//		new NewContactsUtily(context).deleteAll();
		
		
//		Log.i("删除", "删除A:"+a);
	}

}
