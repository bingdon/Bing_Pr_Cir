package com.example.projectcircle.manage;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.bean.GroupInfo;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FeedBackActivity extends Activity implements OnClickListener {

	EditText content;
	Button back, submit;
	String feedback;
	private String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		content = (EditText) findViewById(R.id.feedback_content);		
		uid = LoginActivity.id;
		back = (Button) findViewById(R.id.feedback_left);
		submit = (Button) findViewById(R.id.feedback_submit);
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.feedback_left:
			finish();
			break;
		case R.id.feedback_submit:		
			feedback = content.getText().toString();
			Log.i("feedback", feedback);
			if (feedback.equals("") || TextUtils.isEmpty(feedback)) {
		       ToastUtils.showShort(getApplicationContext(), "请输入建议~");
			}
			else{
				postOpinion(uid,feedback);
			}
			break;
		default:
			break;
		}
	}

	private void postOpinion(String uid, String feedback) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {	

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
             Log.i("response", response);
             ToastUtils.showShort(getApplicationContext(), "提交成功！");
             finish();
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1, arg2, arg3);
			ToastUtils.showShort(getApplicationContext(), "提交失败鸟~");
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.doPostOpinion(uid, feedback,res);
	}
}
