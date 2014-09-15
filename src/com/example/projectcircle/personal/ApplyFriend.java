package com.example.projectcircle.personal;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.friend.DetailInformation;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ApplyFriend extends Activity {

	TextView check, back, send;
	EditText message;
	String aid;
	String bid;
	private TextView  infor;
	private String info;


	public static String id;
	public static String status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_check);
		aid = LoginActivity.id;
		Intent intent = getIntent();
		bid = intent.getStringExtra("id");
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		//message = (EditText) findViewById(R.id.check_tisi);
		check = (TextView) findViewById(R.id.friend_check_tishi);
		back = (TextView) findViewById(R.id.check_back);
		send = (TextView) findViewById(R.id.check_send);
        infor = (TextView)findViewById(R.id.check_message_verify);       
		back.setOnClickListener(listener);
		send.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.check_back:
//				 Intent intent = new Intent(ApplyFriend.this,
//				 DetailInformation.class);
//				 startActivity(intent);
				finish();
				break;
			case R.id.check_send:
				info = infor.getText().toString();
				applyfriend(aid, bid,info);
				break;
			default:
				break;
			}
		}
	};

	private void applyfriend(String aid, String bid,String info) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回", response);				
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						parseapplyfriend(response);
						ToastUtils.showLong(getApplicationContext(),
								"申请完毕，等待好友通过验证");
						finish();
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"您已申请加对方为好友");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.VerifyFriend(aid, bid, info, res);
	}

	private void parseapplyfriend(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("friends");
			id = obj.getString("id");
			status = obj.getString("status");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
