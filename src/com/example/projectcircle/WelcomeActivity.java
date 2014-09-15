package com.example.projectcircle;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.example.projectcircle.adpter.Utils;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.friend.FriendPage;
import com.example.projectcircle.group.MyGroup;
import com.example.projectcircle.util.UserInfoUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class WelcomeActivity extends Activity {
	boolean isFirstIn = true;
	private static final int GO_HOME = 1000;
	private static final int GO_LOGIN = 1001;
	// 延迟3秒
	private static final long SPLASH_DELAY_MILLIS = 3000;
	/**
	 * Handler:跳转到不同界面
	 * 多个消息可以使用同一个handler，
                 通过what不同区分不同的消息来源， 从而获取消息内容
	 */

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_LOGIN:
				goLogin();
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
//		if (!Utils.hasBind(getApplicationContext())) {
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY,
					Utils.getMetaValue(WelcomeActivity.this, "api_key"));
			// Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
			PushManager.enableLbs(getApplicationContext());
		
//		}//设定你自己的id
//		List<String> tags = new ArrayList();
//		tags.add("guojianchao");
//		PushManager.setTags(getApplicationContext(), tags);
		initView();
		
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		// 读取SharedPreferences中需要的数据
		// 使用SharedPreferences来记录程序的使用次数

		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值

		SharedPreferences sharedPreferences = getSharedPreferences("Login",
				Context.MODE_PRIVATE);
		isFirstIn = sharedPreferences.getBoolean("isFirstIn", true);
		// LoginActivity.id = sharedPreferences.getString("id", "");
		// LoginActivity.phone = sharedPreferences.getString("tel", "");
		// LoginActivity.pwd = sharedPreferences.getString("password", "");
		// System.out.println("tag" + sharedPreferences.getString("tel", "")
		// + sharedPreferences.getString("password", "") + "id"
		// + sharedPreferences.getString("id", ""));

		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {
			// 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
			Log.i("enter", "home");
		} else {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, SPLASH_DELAY_MILLIS);
			Log.i("enter", "Login");
		}
	}


	private void goHome() {
		// TODO Auto-generated method stub		
		 Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
		 startActivity(intent);
		 finish();
	}

	private void goLogin() {
		
		try {
			FriendPage.getFriInfo(this);
			MyGroup.getGroupInfo(this);
			UserInfoUtils.getPersonInfo(WelcomeActivity.this);
			if (MyApplication.getMyPersonBean()!=null) {
				LoginActivity.id=MyApplication.getMyPersonBean().getId();
				Log.i("用户ID:", ""+LoginActivity.id);
				startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
				finish();
			}else {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		
		
	}
}
