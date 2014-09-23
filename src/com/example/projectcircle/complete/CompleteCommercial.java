package com.example.projectcircle.complete;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.setting.ModifyInfoActivity;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CompleteCommercial extends Activity {
	/**
	 * EditText 公司信息
	 */
	EditText companyname_txt, business_intro_txt;
	String companyname, business_intro;
	/**
	 * 其它Button
	 */
	Button back, next;

	 String uid = LoginActivity.id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_commercial);
		
	
		if (!ModifyInfoActivity.isModify()) {
			findViewById(R.id.linearLayout1).setVisibility(View.VISIBLE);
			
		}else {
			
		}
		
		initBtn();
		initView();
		initFilter();
	}

	private void initView() {
		// TODO Auto-generated method stub
		companyname_txt = (EditText) findViewById(R.id.company_name);
		business_intro_txt = (EditText) findViewById(R.id.business_jianjie);
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.comercial_left);
		next = (Button) findViewById(R.id.commercial_next);
		back.setOnClickListener(listener);
		next.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.comercial_left:
				// Intent intent = new Intent(CompleteCommercial.this,
				// CompleteInfo.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.commercial_next:
				subimt();
				break;
			default:
				break;
			}
		}
	};

	private void subimt() {
		// TODO Auto-generated method stub
		companyname = companyname_txt.getText().toString();
		business_intro = business_intro_txt.getText().toString();
		// id = SiginActivity.id;
		CompleteCompany(uid, companyname, business_intro);

	}

	private void CompleteCompany(String id, String companyname, String business) {

		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						Toast.makeText(getApplicationContext(), "添加成功！",
								Toast.LENGTH_LONG).show();
						Intent intent1 = new Intent(CompleteCommercial.this,
								CompleteInfo.class);
						startActivity(intent1);
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "添加失败！",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error, String response) {
				// TODO Auto-generated method stub
				super.onFailure(error, response);
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.CompleteCompany(id, companyname, business, res);
	}
	private void initFilter() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish.before.regist.page");	
		filter.addAction(ContantS.ACTION_SEND_SUBMIT);
		registerReceiver(msgReceiver, filter);
	}
	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub		
			String action=intent.getAction();
			if (action.equals(ContantS.ACTION_SEND_SUBMIT)) {
				subimt();
			} else {
				finish();	
			}
				
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}
}
