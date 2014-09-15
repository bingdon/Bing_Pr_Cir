package com.example.projectcircle.complete;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.MainActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CompleteFinish extends Activity {
	Button go_project;
	String tel, pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_finish);
		tel = SiginActivity.account;
		pwd = SiginActivity.password;
		go_project = (Button) findViewById(R.id.perfect_go_project);
		go_project.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				doLogin(tel, pwd);
			}
		});
	}

	private void doLogin(String username, String password) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				System.out.println(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						JSONObject success = obj.getJSONObject("user");
						String id = success.getString("id");
						LoginActivity.id = id;
						ToastUtils.showShort(getApplicationContext(),
								"欢迎进入工程圈！");
						Intent intent = new Intent(CompleteFinish.this,
								MainActivity.class);
						startActivity(intent);
						//
						Intent intent2 = new Intent();  
						intent2.setAction("yi.jing.wan.shan");  
						CompleteFinish.this.sendBroadcast(intent2);
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
}
