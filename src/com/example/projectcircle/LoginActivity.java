package com.example.projectcircle;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.baidu.android.pushservice.PushManager;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.util.UserInfoUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 登录界面
 * 
 * @author Walii
 * @version 2014.3.18
 */
public class LoginActivity extends Activity implements OnCheckedChangeListener {
	private static final String TAG = LoginActivity.class.getSimpleName();
	EditText accounts, password;
	String tel, psw;
	Button login, register;
	public static String id;
	public static String phone, pwd;
	public static String name;
	private CheckBox isAutoLoginBox;
	private boolean isauto = false;
	private ProgressDialog progressDialog;
	private String lastAccount = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		initFilter();
		initView();
	}

	private void initFilter() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish.before.regist.page");
		registerReceiver(msgReceiver, filter);
	}

	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			finish();
		}
	};

	private void initView() {
		// TODO Auto-generated method stub
		accounts = (EditText) findViewById(R.id.login_user_edit);
		password = (EditText) findViewById(R.id.login_lock_edit);
		login = (Button) findViewById(R.id.login_login);
		register = (Button) findViewById(R.id.login_sigin);
		// isAutoLoginBox=(CheckBox)findViewById(R.id.auto_login_checkBox);

		getcountacode();

		login.setOnClickListener(listener);
		register.setOnClickListener(listener);

		// isAutoLoginBox.setOnCheckedChangeListener(this);
		progressDialog = new ProgressDialog(this);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.login_login:

				GoLogin();
				break;
			case R.id.login_sigin:
				delTag(id);
				Intent intent = new Intent(LoginActivity.this,
						SiginActivity.class);
				startActivity(intent);
				break;

			}
		}
	};
	private ConnectivityManager manager;

	private void GoLogin() {
		// TODO Auto-generated method stub
		tel = accounts.getText().toString();
		psw = password.getText().toString();
		if (!lastAccount.equals(tel)) {
			delTag(id);
		}
		// tel = "15110140952";
		// psw = "123456";
		if (TextUtils.isEmpty(tel)) {
			ToastUtils.showShort(getApplicationContext(), "帐号不能为空");
			return;
		}
		if (TextUtils.isEmpty(psw)) {
			ToastUtils.showShort(getApplicationContext(), "密码不能为空");
			return;
		}
		if (!TextUtils.isEmpty(tel) && !TextUtils.isEmpty(psw)) {
			doLogin(tel, psw);
		}
	}

	private void doLogin(final String tel, final String password) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				progressDialog.setMessage(getString(R.string.logining));
				progressDialog.show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				progressDialog.dismiss();
			}

			public void onSuccess(String response) {
				AppLog.i(TAG, "登陆返回:"+response);
				parseUser(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {

						savecountacode(tel, password, isauto);

						ToastUtils.showShort(getApplicationContext(), "登录成功！");
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						startActivity(intent);
						finish();
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"帐号或密码不正确，请重新输入！");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
				Log.i(TAG, "====" + error);
			}
		};
		MyHttpClient myhttpclient = new MyHttpClient();
		myhttpclient.doLogin(tel, password, res);
	}

	public void parseUser(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");

			MyPersonBean myPersonBean = null;

			try {
				myPersonBean = new Gson().fromJson(obj.toString(),
						MyPersonBean.class);
				if (!TextUtils.isEmpty(myPersonBean.getToken())&&myPersonBean.getToken().length()>20) {
					AppLog.i(TAG, "保存Token:"+myPersonBean.getToken()+"长度:"+myPersonBean.getToken().length());
					saveToken(LoginActivity.this, myPersonBean.getToken());
				}
				MyApplication.setMyPersonBean(myPersonBean);
				UserInfoUtils.setPersonInfo(myPersonBean, LoginActivity.this);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "解析错误");
			}

			id = obj.getString("id");
			name = obj.getString("username");
			Log.i("response  id==", id);

			setTag(id, this);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void parseUserInfo(String response,Context context) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");

			MyPersonBean myPersonBean = null;

			try {
				myPersonBean = new Gson().fromJson(obj.toString(),
						MyPersonBean.class);
				if (!TextUtils.isEmpty(myPersonBean.getToken())&&myPersonBean.getToken().length()>20) {
					saveToken(context, myPersonBean.getToken());
				}
				MyApplication.setMyPersonBean(myPersonBean);
				AppLog.i(TAG, "Bean返回:"+myPersonBean.getAddress());
				AppLog.i(TAG, "Bean返回:"+myPersonBean.getPlace());
				UserInfoUtils.setPersonInfo(myPersonBean, context);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "解析错误");
			}

			id = obj.getString("id");
			name = obj.getString("username");
			Log.i("response  id==", id);

			setTag(id, context);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	

	private void savecountacode(String phone, String password, boolean isauto) {
		SharedPreferences sharedPreferences = getSharedPreferences(TAG,
				Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("phone", phone);
		editor.putString("password", password);
		editor.putBoolean("isauto", isauto);
		editor.putString("userid", id);
		editor.commit();
	}

	/**
	 * 保存密码
	 * 
	 * @author lyl
	 * @since 2014.08.26
	 * @param password
	 * @param context
	 */
	public static void savePwd(String password, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,
				Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("password", password);
		editor.commit();
	}

	private void getcountacode() {
		SharedPreferences sharedPreferences = getSharedPreferences(TAG,
				Context.MODE_PRIVATE);
		// isauto=sharedPreferences.getBoolean("isauto", false);
		// isAutoLoginBox.setChecked(isauto);
		id = sharedPreferences.getString("userid", "");
		lastAccount = sharedPreferences.getString("phone", "");
		accounts.setText(sharedPreferences.getString("phone", ""));
		password.setText(sharedPreferences.getString("password", ""));

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		isauto = isChecked;
	}

	public static void setTag(String userid, Context context) {
		List<String> tags = new ArrayList<String>();
		tags.add(userid);
		PushManager.setTags(context, tags);
	}

	private void delTag(String userid) {
		if (TextUtils.isEmpty(userid)) {
			return;
		}
		List<String> tags = new ArrayList<String>();
		tags.add(userid);
		PushManager.delTags(getApplicationContext(), tags);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}

	
	private static void saveToken(Context context,String token){
		SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,
				Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putString("token", token);
		editor.commit();
	}
	
	public static String getToken(Context context){
		String token="";
		SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,
				Context.MODE_PRIVATE);
		token=sharedPreferences.getString("token", "");
		return token;
		
	}
	
	
}
