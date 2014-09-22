package com.example.projectcircle.manage;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.complete.CompleteCommercial;
import com.example.projectcircle.complete.CompleteDriver;
import com.example.projectcircle.complete.CompleteInfo;
import com.example.projectcircle.complete.CompleteMaster;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.other.MessagePage;
import com.example.projectcircle.personal.ModifyMySelf;
import com.example.projectcircle.personal.MyPersonal;
import com.example.projectcircle.setting.ModifyInfoActivity;
import com.example.projectcircle.setting.MsgSettingActivity;
import com.example.projectcircle.setting.SettingActivity;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.UserInfoUtils;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * 管理界面
 */
public class ManagePage extends Activity {
	
	private static final String TAG = "MyPersonal";
	/**
	 * 列表Button
	 */
	LinearLayout right1, right2, right3, right4, right6;
	ImageView right5;
	TextView name_txt, type_txt, content_txt, projectNum_txt;
	ImageView myhead;
	Button exit;
	String id;
	String uname, utype, ucity, udevice, ucontent, uheadimg, accept, info, uid,
			age;
	// 图片缓存
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	Context context;
	private ImageView edit_btn1;
	private ImageView edit_btn2;
	private ImageView edit_btn3;
	private ImageView edit_btn4;
	private ImageView edit_btn1_complete;
	private String persign;
	private String address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage);
		id = LoginActivity.id;
		UserDetail(id);
		initBtn();
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.build();
		initFilter();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
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
			persign = obj.getString("sign");
			address = obj.getString("address");
			Log.i("accept", accept);
			info = obj.getString("info");
			age = obj.getString("age");
			 Log.i("read", uname);
			 Log.i("read", utype);
			 Log.i("read", ucity);
			 Log.i("read", udevice);
			 Log.i("read", uheadimg);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		name_txt = (TextView) findViewById(R.id.manage_name);
		type_txt = (TextView) findViewById(R.id.manage_career);
		content_txt = (TextView) findViewById(R.id.manage_content);
		projectNum_txt = (TextView) findViewById(R.id.m_project_number);
		myhead = (ImageView) findViewById(R.id.manage_head_img);
		name_txt.setText(uname);
		type_txt.setText(utype);
		content_txt.setText(ucontent);
		projectNum_txt.setText("工程号:   " + uid);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL + uheadimg, myhead,
				options, animateFirstListener);
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		edit_btn1 = (ImageView) findViewById(R.id.edit_btn1);
		edit_btn1_complete = (ImageView) findViewById(R.id.edit_btn1_complete);
		edit_btn2 = (ImageView) findViewById(R.id.edit_btn2);
		edit_btn3 = (ImageView) findViewById(R.id.edit_btn3);
		edit_btn4 = (ImageView) findViewById(R.id.edit_btn4);
		right5 =(ImageView) findViewById(R.id.m_right2);
		exit = (Button) findViewById(R.id.exit);
		
		findViewById(R.id.m_right5_layout).setOnClickListener(listener);//编辑个人注册信息
		findViewById(R.id.m_right7_layout).setOnClickListener(listener);//编辑个人完善信息
		findViewById(R.id.m_right7_layout).setVisibility(View.GONE);
		findViewById(R.id.m_right6_layout).setOnClickListener(listener);
		findViewById(R.id.m_right4_layout).setOnClickListener(listener);
		findViewById(R.id.m_right3_layout).setOnClickListener(listener);
		findViewById(R.id.m_right2_layout).setOnClickListener(listener);
		findViewById(R.id.m_logout_layout).setOnClickListener(listener);
//		findViewById(R.id.m_right1_layout).setOnClickListener(listener);
		
		edit_btn1.setOnClickListener(listener);
		edit_btn1_complete.setOnClickListener(listener);
		edit_btn2.setOnClickListener(listener);
		edit_btn3.setOnClickListener(listener);
		edit_btn4.setOnClickListener(listener);
		right5.setOnClickListener(listener);		
		exit.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.edit_btn1:
			case R.id.m_right5_layout:	
				Intent intent1 = new Intent(ManagePage.this, ModifyInfoActivity.class);
				intent1.putExtra("uname", uname);
				intent1.putExtra("utype", utype);
				intent1.putExtra("ucity", ucity);
				intent1.putExtra("info", info);
				intent1.putExtra("uheadimg", uheadimg);
				intent1.putExtra("age", age);
				intent1.putExtra("accept", accept);
				intent1.putExtra("persign", persign);
				intent1.putExtra("address", address);
				startActivity(intent1);

				break;
			case R.id.edit_btn1_complete:
			case R.id.m_right7_layout:
				if(utype.equals("机主")){
				Intent intent11 = new Intent(ManagePage.this, CompleteMaster.class);	
				startActivity(intent11);}
				else if(utype.equals("司机")){			
				Intent intent11 = new Intent(ManagePage.this, CompleteDriver.class);	
				startActivity(intent11);
				}
				else if(utype.equals("商家")){			
					Intent intent11 = new Intent(ManagePage.this, CompleteCommercial.class);	
					startActivity(intent11);
				}
				else if(utype.equals("其他")){			
					Intent intent11 = new Intent(ManagePage.this, CompleteInfo.class);	
					startActivity(intent11);
				}
				break;
			case R.id.edit_btn2:
			case R.id.m_right6_layout:	
				Intent intent2 = new Intent(ManagePage.this, MsgSettingActivity.class);	
				startActivity(intent2);
				break;
			case R.id.edit_btn3:
			case R.id.m_right4_layout:	
				Intent intent3 = new Intent(ManagePage.this,						
						ModifyPassword.class);
				startActivity(intent3);
				break;
			case R.id.edit_btn4:
			case R.id.m_right3_layout:	
				Intent intent4 = new Intent(ManagePage.this,
						FeedBackActivity.class);
				startActivity(intent4);
				break;
//			case R.id.m_right1_layout:
//
//				break;
			case R.id.m_right2:
			case R.id.m_right2_layout:	
				Intent intent5 = new Intent(ManagePage.this, About.class);			
				startActivity(intent5);
				break;
	
			case R.id.exit:
				Intent intent6 = new Intent(ManagePage.this,
						LoginActivity.class);
				startActivity(intent6);
				finish();
				break;
				
				
			case R.id.m_logout_layout:
				finishDialog();
				break;
				
			default:
				break;
			}
		}
	};

	private void finishDialog(){
		final CharSequence[] items = { "退出程序", "注销登录" };
		AlertDialog dlg = new AlertDialog.Builder(ManagePage.this)
				.setTitle("退出")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 这里item是根据选择的方式，
						// 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
						if (item == 1) {
							UserInfoUtils.delPersonInfo(ManagePage.this);
							Intent intent = new Intent(ManagePage.this,LoginActivity.class);
							startActivity(intent);
							finish();
							System.exit(0);
						} else {
					        finish();
							System.exit(0);
						}
					}
				}).create();
		dlg.show();
	}
	private void initFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ContantS.ACTION_GET_USER_INFO);
		registerReceiver(msgReceiver, filter);
	}
	
	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			UserDetail(id);
		}
	};
	
}
