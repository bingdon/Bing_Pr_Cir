package com.example.projectcircle;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.projectcircle.complete.CompleteCommercial;
import com.example.projectcircle.complete.CompleteDriver;
import com.example.projectcircle.complete.CompleteInfo;
import com.example.projectcircle.complete.CompleteMaster;
import com.example.projectcircle.db.utils.MsgDataUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class SiginSuccess extends Activity {
	Button go_project, go_info;
	String career;
	String tel, pwd;
	private MsgDataUtils msgDataUtils = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sigin_success);
		career = SiginActivity.type;
	    Log.i("career----type", career+"");
		tel = SiginActivity.account;
		pwd = SiginActivity.password;
		Log.i("tel+pwd", tel + "+" + pwd);
		initBtn();
		initFilter();
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

	private void initBtn() {
		// TODO Auto-generated method stub
		go_project = (Button) findViewById(R.id.go_project);
		go_project.setOnClickListener(listener);

		go_info = (Button) findViewById(R.id.go_baseinfo);
		go_info.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.go_project:
				// Intent intent = new Intent(SiginSuccess.this,
				// MainActivity.class);
				// startActivity(intent);
				// finish();
				doLogin(tel, pwd);
				break;
			case R.id.go_baseinfo:
				if (career.equals("机主")) {
					Intent intent2 = new Intent(SiginSuccess.this,
							CompleteMaster.class);
					startActivity(intent2);
				} else if (career.equals("司机")) {
					Intent intent3 = new Intent(SiginSuccess.this,
							CompleteDriver.class);
					startActivity(intent3);
				} else if (career.equals("商家")) {
					Intent intent4 = new Intent(SiginSuccess.this,
							CompleteCommercial.class);
					startActivity(intent4);
				} else {
					Intent intent5 = new Intent(SiginSuccess.this,
							CompleteInfo.class);
					startActivity(intent5);
				}

				break;
			default:
				break;
			}
		}
	};

	private void doLogin(String username, String password) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
		
			public void onSuccess(String response) {
				System.out.println(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						JSONObject  sucess = obj.getJSONObject("user");
						String id = sucess.getString("id");
						ToastUtils.showShort(getApplicationContext(),
								"欢迎进入工程圈！");
						LoginActivity.id = id;
						//让Message中的消息列表消失
						if (null == msgDataUtils) {
							msgDataUtils = new MsgDataUtils(SiginSuccess.this);
						}
						msgDataUtils.deleteAll();
						Intent intent1 = new Intent(SiginSuccess.this,
								MainActivity.class);
						startActivity(intent1);
						
						finish();
					} else {
						// ToastUtils.showShort(getApplicationContext(),
						// "帐号或密码不正确，请重新输入！");
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
			}
		};
		MyHttpClient myhttpclient = new MyHttpClient();
		myhttpclient.doLogin(username, password, res);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}
}
