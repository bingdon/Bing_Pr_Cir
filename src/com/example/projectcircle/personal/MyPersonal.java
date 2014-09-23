package com.example.projectcircle.personal;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.ImageFetcher;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.example.projectcircle.view.SmartImageView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * 修改个人资料
 * 
 * @author Walii
 * @version 2014.3.18
 */
public class MyPersonal extends Activity {
	private static final String TAG = "MyPersonal";
	/**
	 * 控制修改信息
	 * 
	 */
	// 控制修改
	Button back;
	LinearLayout device_right;
	// 修改的文本
	TextView mycity_txt, age_txt, device_txt, intro_txt;

	/**
	 * Call
	 * 
	 */
	RadioGroup call_group;
	RadioButton call_radio1, call_radio2;
	/**
	 * 读取信息的控件
	 * 
	 */
	ImageView headbg, headimg;
	TextView name, career, content;
	TextView project_num;
	TextView device_detail;
	TextView info_txt;
	// 修改摁钮
	ImageView xiu1, xiu2, xiu3, xiu4;
	String id;
	String uname, utype, ucity, udevice, ucontent, uheadimg, accept, info, uid,
			age;
	// 图片缓存
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private ImageFetcher foodpicFetcher;
	private RadioButton radiobutton1;
	private TextView accept_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypesonal);
		id = LoginActivity.id;
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
//		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
//				.cacheInMemory(true)// 是否緩存都內存中
//				.cacheOnDisc(true)// 是否緩存到sd卡上
//				.build();
		this.foodpicFetcher = new ImageFetcher(getBaseContext(), 40);
		this.foodpicFetcher.setLoadingImage(R.drawable.camera);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UserDetail(id);
	}

	/**
	 * 个人信息 请求和解析
	 * 
	 */
	private void UserDetail(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "返回:" + response);
				parseUserDetail(response);
				initBtn();
				initCall();
				initView();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail(id, res);
	}

	public void parseUserDetail(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");
			uid = obj.getString("id");
			uname = obj.getString("username");
			utype = obj.getString("type");
			ucity = obj.getString("address");
			udevice = obj.getString("equipment");
			ucontent = obj.getString("sign");
			uheadimg = obj.getString("headimage");
			accept = obj.getString("accept");
			Log.i("accept", accept);
			info = obj.getString("info");
			age = obj.getString("age");
			// Log.i("read", uname);
			// Log.i("read", utype);
			// Log.i("read", ucity);
			// Log.i("read", udevice);
			// Log.i("read", uheadimg);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读取信息
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		headbg = (ImageView) findViewById(R.id.my_home_page_background);
		headimg = (SmartImageView) findViewById(R.id.my_home_page_headimg);
		name = (TextView) findViewById(R.id.my_name);
		career = (TextView) findViewById(R.id.my_career);
		content = (TextView) findViewById(R.id.my_content);
		project_num = (TextView) findViewById(R.id.my_project_number);
		device_detail = (TextView) findViewById(R.id.my_home_device_detail);
		mycity_txt = (TextView) findViewById(R.id.my_home_city);
		age_txt = (TextView) findViewById(R.id.my_home_age);
		device_txt = (TextView) findViewById(R.id.my_home_device);
		info_txt = (TextView) findViewById(R.id.my_home_introduce);		
//		ImageLoader imageLoader = ImageLoader.getInstance();
//		imageLoader.displayImage(MyHttpClient.IMAGE_URL + uheadimg, headbg,
//				options, animateFirstListener);
//		imageLoader.displayImage(MyHttpClient.IMAGE_URL + uheadimg, headimg,
//				options, animateFirstListener);
		 String myheadimg =MyHttpClient.IMAGE_URL+uheadimg;
		 foodpicFetcher.loadImage( myheadimg,headimg);
		name.setText(uname);
		career.setText(utype);
		content.setText(ucontent);
		project_num.setText("工程号:   " + uid);
		device_detail.setText(udevice);
		mycity_txt.setText(ucity);
		device_txt.setText(udevice);
		age_txt.setText(age + "岁");
		info_txt.setText(info);
	}

	/**
	 * 接受电话 初始化 功能实现
	 */
	private void initCall() {
		// TODO Auto-generated method stub
	 accept_text = (TextView)findViewById(R.id.accept_or_not_here);
	 if (TextUtils.isEmpty(accept)) {
		return;
	}
		if (accept.equals("0")) {
			accept_text.setText("隐藏电话");
		} else if (accept.equals("1")) {
			accept_text.setText("接受圈内电话");
		}
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.my_home_page_back);
//		xiu2 = (ImageView) findViewById(R.id.xiu2);
//		xiu3 = (ImageView) findViewById(R.id.xiu3);
//		xiu4 = (ImageView) findViewById(R.id.xiu4);
		xiu1 = (ImageView) findViewById(R.id.modify_my_message);
		device_right = (LinearLayout) findViewById(R.id.my_home_device_detail_layout);
		intro_txt = (TextView) findViewById(R.id.my_home_introduce);

		back.setOnClickListener(listener);
		xiu1.setOnClickListener(listener);
//		xiu2.setOnClickListener(listener);
//		xiu3.setOnClickListener(listener);
//		xiu4.setOnClickListener(listener);
		device_right.setOnClickListener(listener);
		intro_txt.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.my_home_page_back:
				finish();
				break;		
			case R.id.modify_my_message:
				Intent intent1 = new Intent(MyPersonal.this, ModifyMySelf.class);
				startActivity(intent1);
				break;
			}
		}
	};

}
