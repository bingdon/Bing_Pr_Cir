package com.example.projectcircle.group;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ApplyGroup extends Activity {

	TextView back, send;
	private EditText mEditText;
	String uid;
	String gid;
	private static final String TAG=ApplyGroup.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_add);
		uid=LoginActivity.id;
		gid=getIntent().getStringExtra("gid");
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (TextView) findViewById(R.id.apply_back);
		send = (TextView) findViewById(R.id.apply_send);
		mEditText=(EditText)findViewById(R.id.apply_content);
		mEditText.setText(getString(R.string.i_am)+MyApplication.getMyPersonBean().getUsername());

		back.setOnClickListener(listener);
		send.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.apply_back:
				finish();
				break;
			case R.id.apply_send:
				if (!TextUtils.isEmpty(gid)) {
					MyHttpClient.askAddGroup(gid, uid,mEditText.getText().toString() ,mHandler);
				}
			
				finish();
				break;
			default:
				break;
			}
		}
	};
	
	
	private AsyncHttpResponseHandler mHandler=new AsyncHttpResponseHandler(){

		@Override
		public void onFailure(Throwable error, String content) {
			// TODO Auto-generated method stub
			super.onFailure(error, content);
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			super.onFinish();
			ToastUtils.showLong(getApplicationContext(), "申请完毕，等待群主通过验证");
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
		}

		@Override
		public void onSuccess(String content) {
			// TODO Auto-generated method stub
			super.onSuccess(content);
			Log.i(TAG, "返回:"+content);
		}

		
	};
	
}
