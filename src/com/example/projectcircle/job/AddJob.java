package com.example.projectcircle.job;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 添加工程作业和司机需求
 */
public class AddJob extends Activity {
	public static final String TAG = null;
	EditText address_txt, title_txt, detail_txt, contact_txt, tel_txt;
	String address, title, detail, contact, tel;
	String type;
	String device1, device2, device3, device4, device5;
	Button back, submit;
	TextView text_title;
	String phone, name;
	String uid;
	//定位
	private LocationClient mLocationClient;
//	private MyBDLocationListener mBDLocationListener;
	String province;
	String city;
	String district;
    String rplace = HomeActivity.rplace;
	private Double jlat = HomeActivity.latitude;
	private Double jlon = HomeActivity.longitude;
	private TextView main_title;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_job_lay);
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		phone = MyApplication.getMyPersonBean().getTel();
		name = MyApplication.getMyPersonBean().getUsername();
		uid = LoginActivity.id;
		//initLocation();
		initView();
		initBtn();
		mProgressDialog=new ProgressDialog(this);
		mProgressDialog.setMessage(getString(R.string.submiting_));
	}

	private void initView() {
		// TODO Auto-generated method stub
		
		main_title = (TextView) findViewById(R.id.textView1);
		address_txt = (EditText) findViewById(R.id.add_job_address);
		title_txt = (EditText) findViewById(R.id.add_job_title);
		detail_txt = (EditText) findViewById(R.id.add_job_detail);
		contact_txt = (EditText) findViewById(R.id.add_job_contact);
		tel_txt = (EditText) findViewById(R.id.add_job_tel);
		text_title = (TextView) findViewById(R.id.text_title);
		if (type.equals("司机需求")) {
			main_title.setText("司机需求信息");
			text_title.setText("职位名称");
			title_txt.setHint("例：招大挖驾驶员");
			detail_txt.setHint("请描述您的要求");
		}
		tel_txt.setText(phone);
		contact_txt.setText(name);
		address_txt.setText(rplace+"");
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.add_job_left);
		submit = (Button) findViewById(R.id.add_job_submit);

		back.setOnClickListener(listener);
		submit.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.add_job_left:
				// Intent intent = new Intent(AddJob.this, ProjectJob.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.add_job_submit:
				postInfo();
				break;
			default:
				break;
			}
		}
	};


	private void postInfo() {
		// TODO Auto-generated method stub
		address = address_txt.getText().toString();
		title = title_txt.getText().toString();
		detail = detail_txt.getText().toString();
		contact = contact_txt.getText().toString();
		tel = tel_txt.getText().toString();
		jlat = HomeActivity.latitude;
		jlon = HomeActivity.longitude;
		if (TextUtils.isEmpty(address)) {
			ToastUtils.showShort(getApplicationContext(), "地址不能为空!");
			return;
		}

		if (TextUtils.isEmpty(title)) {
			ToastUtils.showShort(getApplicationContext(), "标题不能为空!");
			return;
		}

		if (TextUtils.isEmpty(detail)) {
			ToastUtils.showShort(getApplicationContext(), "详情不能为空!");
			return;
		}
		if (TextUtils.isEmpty(contact)) {
			ToastUtils.showShort(getApplicationContext(), "联系人不能为空!");
			return;
		}
		if (TextUtils.isEmpty(tel)) {
			ToastUtils.showShort(getApplicationContext(), "联系电话不能为空!");
			return;
		}
		if (tel.length() != 11) {
			ToastUtils.showShort(getApplicationContext(), "联系电话的号码格式不对!");
			return;
		}
		PostJob(uid, title, address, type, detail, contact, tel);
	
	}

	private void PostJob(String uid, String title, String address, String type,
			String detail, String contact, String tel) {

		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				mProgressDialog.dismiss();
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						Toast.makeText(getApplicationContext(), "发布成功！",
								Toast.LENGTH_LONG).show();
						// Intent intent = new Intent(AddJob.this,
						// ProjectJob.class);
						// startActivity(intent);
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "发布失败",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				finish();
				
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error, String response) {
				// TODO Auto-generated method stub
				super.onFailure(error, response);
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}
			
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				mProgressDialog.show();
			}
			
		};
		MyHttpClient client = new MyHttpClient();
		client.PostJob(uid, title, address, type, detail, contact, tel, jlat, jlon, res);
	}


}
