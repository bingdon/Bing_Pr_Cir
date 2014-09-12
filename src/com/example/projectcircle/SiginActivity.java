package com.example.projectcircle;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.CompletionInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.complete.CompleteInfo;
import com.example.projectcircle.home.HomeSecActivity;
import com.example.projectcircle.util.ImageUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 注册界面
 * 
 * @author Walii
 * @version 2014.3.18
 */
@SuppressWarnings("deprecation")
public class SiginActivity extends TabActivity {
	private static final String TAG = "SiginActivity";
	UserInfo info;
	String equipment;
	String business;
	public static String id;

	/**
	 * 个人信息
	 */
	EditText account_edit, password_edit, realname_edit;
	TextView hometown_edit, birthday_edit;

	public static String account;
	public static String password;

	String realname, hometown, birthday;

	/**
	 * 选择职业
	 */
	private TabHost tabhost;
	EditText c_content;
	String crr_content;
	// 职业的选项
	public static String type = "机主";
	public static String type_content;
	/**
	 * 接受电话
	 */
	RadioGroup call_group;
	RadioButton call_radio1, call_radio2;
	String accept = "0";
	/**
	 * 其它Button
	 */
	Button back;
	ImageView submit;
	/**
	 * 生日
	 */
	DatePickerDialog dialog;
	SimpleDateFormat format;
	Calendar calendar;

	/**
	 * 选择家乡
	 */

	private ArrayAdapter<String> adapter1;
	private ArrayAdapter<String> adapter2;
	private ArrayAdapter<String> adapter3;
	private static final String[] m = { "北京", "河北", "天津", "上海", "四川" };
	String ht_1, ht_2, ht_3;

	/**
	 * 定位
	 * 
	 */
	double latitude;
	double longitude;
	String rplace;
	private LocationClient mLocationClient;
	private MyBDLocationListener mBDLocationListener;
	/**
	 * 年龄
	 * 
	 */
	int stryear, strmonth, strday;
	String age;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		type = "机主";
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sigin);
		progressDialog = new ProgressDialog(this);
		initInfo();
		initCareer();
		initCall();
		initBtn();
		initLoc();
		initFilter();

	}

	private void initFilter() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish.before.regist.page");
		registerReceiver(msgReceiver, filter);
	}

	private void initLoc() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(this.getApplicationContext());

		mBDLocationListener = new MyBDLocationListener();
		mLocationClient.registerLocationListener(mBDLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps

		option.setAddrType("all");
		// 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
		option.setPoiExtraInfo(true);
		// 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		option.setProdName("通过GPS定位我当前的位置");
		// 禁用启用缓存定位数据
		option.disableCache(true);
		// 坐标系类型，百度手机地图对外接口中的坐标系默认是bd09ll
		option.setCoorType("bd09ll");
		// 设置最多可返回的POI个数，默认值为3。由于POI查询比较耗费流量，设置最多返回的POI个数，以便节省流量。
		option.setPoiNumber(3);
		// 设置定位方式的优先级。
		// 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
		option.setPriority(LocationClientOption.GpsFirst);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tabhost.setCurrentTabByTag("tab1");

	}

	/**
	 * 个人信息 初始化 功能实现
	 */
	private void initInfo() {
		// TODO Auto-generated method stub

		account_edit = (EditText) findViewById(R.id.sigin_username);
		password_edit = (EditText) findViewById(R.id.sigin_pwd_edit);
		realname_edit = (EditText) findViewById(R.id.sigin_realname);
		hometown_edit = (TextView) findViewById(R.id.sigin_hometown);
		birthday_edit = (TextView) findViewById(R.id.sigin_birthday);
		hometown_edit.setOnClickListener(listener);
		hometown_edit.setText(hometown);
		birthday_edit.setOnClickListener(listener);

	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.sigin_hometown:
				Intent intent = new Intent();
				intent.setClass(SiginActivity.this, HomeSecActivity.class);
				startActivityForResult(intent, 10);
				break;
			case R.id.sigin_birthday:
				Datedialog();
				break;
			default:
				break;
			}
		}

	};
	private TextView user_agreement_content;
	private CheckBox user_agreement;

	/**
	 * 选择职业 初始化 功能实现
	 */
	private void initCareer() {
		// TODO Auto-generated method stub
		c_content = (EditText) findViewById(R.id.sigin_carrer);
		tabhost = this.getTabHost();

		TabSpec tabSpec1 = tabhost.newTabSpec("tab1").setIndicator("tab1")
				.setContent(new Intent(this, SiginFragment1.class));
		TabSpec tabSpec2 = tabhost.newTabSpec("tab2").setIndicator("tab2")
				.setContent(new Intent(this, SiginFragment2.class));
		TabSpec tabSpec3 = tabhost.newTabSpec("tab3").setIndicator("tab3")
				.setContent(new Intent(this, SiginFragment3.class));
		TabSpec tabSpec4 = tabhost.newTabSpec("tab4").setIndicator("tab4")
				.setContent(new Intent(this, SiginFragment4.class));

		tabhost.addTab(tabSpec1);
		tabhost.addTab(tabSpec2);
		tabhost.addTab(tabSpec3);
		tabhost.addTab(tabSpec4);

		RadioGroup rg = (RadioGroup) this.findViewById(R.id.sigin_radiogroup);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.register_sigin_radiobtn1:
					type = "机主";
					c_content.setVisibility(View.INVISIBLE);
					tabhost.setCurrentTabByTag("tab1");
					break;
				case R.id.register_sigin_radiobtn2:
					type = "司机";
					c_content.setVisibility(View.INVISIBLE);
					tabhost.setCurrentTabByTag("tab2");
					break;
				case R.id.register_sigin_radiobtn3:
					type = "商家";
					c_content.setVisibility(View.INVISIBLE);
					tabhost.setCurrentTabByTag("tab3");
					break;
				case R.id.register_sigin_radiobtn4:
					c_content.setVisibility(View.VISIBLE);
					tabhost.setCurrentTabByTag("tab4");
					break;
				}
				Log.i("type----", type + "");
			}
		});

	}

	/**
	 * 接受电话 初始化 功能实现
	 */
	private void initCall() {
		// TODO Auto-generated method stub
		call_group = (RadioGroup) findViewById(R.id.call_radiogroup);
		call_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int radioButtonid = group.getCheckedRadioButtonId();
				switch (radioButtonid) {
				case R.id.call_radiobtn1:
					accept = "0";
					break;
				case R.id.call_radiobtn2:
					accept = "1";
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * 其它Button 初始化 功能实现
	 */
	private void initBtn() {

		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.sigin_back);
		submit = (ImageView) findViewById(R.id.sigin_submit_btn1);
		user_agreement = (CheckBox) findViewById(R.id.user_agreement);
		user_agreement_content = (TextView) findViewById(R.id.user_agreement_content);

		myhead = (ImageView) findViewById(R.id.perfect_info_camera);
		myhead.setOnClickListener(Btnlistener);

		back.setOnClickListener(Btnlistener);
		submit.setOnClickListener(Btnlistener);
		user_agreement_content.setOnClickListener(Btnlistener);
	}

	private View.OnClickListener Btnlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.sigin_back:
				finish();
				break;
			case R.id.sigin_submit_btn1:
				submit();
				break;
			case R.id.user_agreement_content:
				Intent intent = new Intent(SiginActivity.this,
						UserAgreement.class);
				startActivity(intent);

				break;

			case R.id.perfect_info_camera:
				MyDialog();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 提交状态
	 */
	private void submit() {
		// TODO Auto-generated method stub
		initData();
		if (myBitmap == null) {
			ToastUtils.showShort(SiginActivity.this, "请添加头像");
			return;
		}

		account = account_edit.getText().toString().trim();
		password = password_edit.getText().toString().trim();
		realname = realname_edit.getText().toString().trim();
		if (TextUtils.isEmpty(account)) {
			ToastUtils.showShort(getApplicationContext(), "帐号不能为空!");
			return;
		}
		if (account_edit.length() != 11) {
			ToastUtils.showShort(getApplicationContext(), "手机号格式不对!");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			ToastUtils.showShort(getApplicationContext(), "密码不能为空!");
			return;
		}
		if (password_edit.length() != 6) {
			ToastUtils.showShort(getApplicationContext(), "密码必须6位数字或字母!");
			return;
		}

		if (TextUtils.isEmpty(realname)) {
			ToastUtils.showShort(getApplicationContext(), "真实姓名不能为空!");
			return;
		}
		if (realname_edit.length() < 2 && realname_edit.length() > 6) {
			ToastUtils.showShort(getApplicationContext(), "真实姓名必须是2-6位汉字!");
			return;
		}
		if (TextUtils.isEmpty(hometown)) {
			ToastUtils.showShort(getApplicationContext(), "家乡不能为空!");
			return;
		}
		if (TextUtils.isEmpty(birthday)) {
			ToastUtils.showShort(getApplicationContext(), "出生日期不能为空!");
			return;
		}
		if (!user_agreement.isChecked()) {
			ToastUtils.showShort(getApplicationContext(), "未同意《用户协议》");
			return;
		}

		if (TextUtils.isEmpty(equipment)) {
			ToastUtils.showShort(SiginActivity.this, "请选择设备");
			return;
		}

		if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)
				&& !TextUtils.isEmpty(realname) && !TextUtils.isEmpty(hometown)
				&& !TextUtils.isEmpty(birthday) && user_agreement.isChecked()) {
			type_content = c_content.getText().toString().trim();
			Log.i("type_content----", type_content + "");

			if (!TextUtils.isEmpty(type_content)) {
				type = type_content;// 其他的职业名字
				business = SiginFragment4.yewu.getText().toString();
				Log.i("business1----", business + "");
				doSigin(account, password, realname, birthday, hometown, type,
						equipment, business, accept, longitude, latitude,
						rplace, age);
			} else {
				Log.i("business2----", business + "");
				doSigin(account, password, realname, birthday, hometown, type,
						equipment, business, accept, longitude, latitude,
						rplace, age);
			}
		}

	}

	private void doSigin(String tel, String password, String username,
			String birthday, String address, String type, String equipment,
			String business, String accept, double lon, double lat,
			String rplace, String age) {

		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				progressDialog.setMessage(getString(R.string.submiting));
				progressDialog.show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();

			}

			public void onSuccess(String response) {
				Log.i("Sign Sucess", "返回:" + response);
				parseUser(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						// parsedoSign
						LoginActivity.parseUserInfo(response,
								SiginActivity.this);
						postHeadImage();
					} else {
						Toast.makeText(getApplicationContext(), "注册失败",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable error, String response) {
				// TODO Auto-generated method stub
				super.onFailure(error, response);
				progressDialog.dismiss();
			}

			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.doSigin(tel, password, username, birthday, address, type,
				equipment, business, accept, lon, lat, rplace, age, res);
	}

	public void parseUser(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");
			id = obj.getString("id");
			Log.i("id==", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新设备数据
	 */
	private void initData() {
		// TODO Auto-generated method stub
		if (type.equals("机主")) {
			String s = "";
			if (SiginFragment1.btn1.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device1;
				} else {
					s = s + "," + SiginFragment1.device1;
				}
			}
			if (SiginFragment1.btn2.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device2;
				} else {
					s = s + "," + SiginFragment1.device2;
				}
			}
			if (SiginFragment1.btn3.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device3;
				} else {
					s = s + "," + SiginFragment1.device3;
				}
			}
			if (SiginFragment1.btn4.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device4;
				} else {
					s = s + "," + SiginFragment1.device4;
				}
			}
			if (SiginFragment1.btn5.isChecked()) {
				SiginFragment1.device5 = SiginFragment1.d_content.getText()
						.toString().trim();
				if (s.isEmpty()) {
					s = s + SiginFragment1.device5;
				} else {
					s = s + "," + SiginFragment1.device5;
				}
			}
			equipment = s;
			Log.i("equipment", equipment);

		} else if (type.equals("司机")) {
			equipment = SiginFragment2.equipment;

		} else if (type.equals("商家")) {
			String b = "";
			if (SiginFragment3.btn1.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device1;
				} else {
					b = b + "," + SiginFragment3.device1;
				}
			}
			if (SiginFragment3.btn2.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device2;
				} else {
					b = b + "," + SiginFragment3.device2;
				}
			}
			if (SiginFragment3.btn3.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device3;
				} else {
					b = b + "," + SiginFragment3.device3;
				}
			}
			if (SiginFragment3.btn4.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device4;
				} else {
					b = b + "," + SiginFragment3.device4;
				}
			}
			if (SiginFragment3.btn5.isChecked()) {
				SiginFragment3.device5 = SiginFragment3.d_content.getText()
						.toString().trim();
				if (b.isEmpty()) {
					b = b + SiginFragment3.device5;
				} else {
					b = b + "," + SiginFragment3.device5;
				}
			}
			equipment = b;
			Log.i("equipment", equipment);

			String c = "";
			if (SiginFragment3.btn_1.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi1;
				} else {
					c = c + "," + SiginFragment3.busi1;
				}
			}
			if (SiginFragment3.btn_2.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi2;
				} else {
					c = c + "," + SiginFragment3.busi2;
				}
			}
			if (SiginFragment3.btn_3.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi3;
				} else {
					c = c + "," + SiginFragment3.busi3;
				}
			}
			if (SiginFragment3.btn_4.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi4;
				} else {
					b = b + "," + SiginFragment3.busi4;
				}
			}
			business = c;
			Log.i("business", business);
		} else {
			business = SiginFragment4.yewu.getText().toString();
			Log.i("business", business);
		}
	}

	/**
	 * 生日dialog
	 */
	private void Datedialog() {
		DatePickerDialog datePickerDialog = new DatePickerDialog(
				SiginActivity.this, new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							final int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						birthday = year + "-" + (monthOfYear + 1) + "-"
								+ dayOfMonth;
						stryear = year;
						strmonth = monthOfYear + 1;
						strday = dayOfMonth;
						birthday_edit.setText(birthday);
						getage();
					}

				}, 1986, 0, 1);
		datePickerDialog.setTitle("设置出生日期");
		datePickerDialog.show();
	}

	private void getage() {
		// TODO Auto-generated method stub
		Time t = new Time();
		t.setToNow();
		int currentyear = t.year;
		Log.i("currentyear", currentyear + "");
		int year_index = currentyear - stryear;
		Log.i("year_index", year_index + "");
		int currentmonth = t.month + year_index * 12;
		Log.i("currentmonth", currentmonth + "");
		int month = currentmonth - strmonth;
		Log.i("month", month + "");
		age = month / 12 + "";
	}

	/**
	 * 家乡Dialog
	 */
	@SuppressWarnings("unused")
	private void HometownDialog() {
		// TODO Auto-generated method stub
		// 布局文件转换为view对象
		LayoutInflater inflaterDl = LayoutInflater.from(this);
		LinearLayout layout = (LinearLayout) inflaterDl.inflate(
				R.layout.hometown, null);

		// 对话框
		final Dialog dialog = new AlertDialog.Builder(SiginActivity.this)
				.create();
		dialog.setTitle("选择家乡");
		dialog.show();
		dialog.getWindow().setContentView(layout);

		Spinner spinner1 = (Spinner) layout
				.findViewById(R.id.hometown_spinner_1);
		Spinner spinner2 = (Spinner) layout
				.findViewById(R.id.hometown_spinner_2);
		Spinner spinner3 = (Spinner) layout
				.findViewById(R.id.hometown_spinner_3);

		adapter1 = new ArrayAdapter<String>(SiginActivity.this,
				android.R.layout.simple_spinner_item, m);
		adapter2 = new ArrayAdapter<String>(SiginActivity.this,
				android.R.layout.simple_spinner_item, m);
		adapter3 = new ArrayAdapter<String>(SiginActivity.this,
				android.R.layout.simple_spinner_item, m);

		spinner1.setAdapter(adapter1);
		spinner2.setAdapter(adapter2);
		spinner3.setAdapter(adapter3);

		spinner1.setOnItemSelectedListener(spinnerlistener);
		spinner2.setOnItemSelectedListener(spinnerlistener);
		spinner3.setOnItemSelectedListener(spinnerlistener);

		// 确定按钮
		Button btnOK = (Button) layout.findViewById(R.id.button_ok);
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hometown = ht_1 + ht_2 + ht_3;
				hometown_edit.setText(hometown);
				dialog.dismiss();
				Datedialog();
				ToastUtils.showShort(getApplicationContext(), "ok");
			}
		});

	}

	private OnItemSelectedListener spinnerlistener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.hometown_spinner_1:
				ht_1 = arg0.getItemAtPosition(arg2).toString();
				break;
			case R.id.hometown_spinner_2:
				ht_2 = arg0.getItemAtPosition(arg2).toString();
				break;
			case R.id.hometown_spinner_3:
				ht_3 = arg0.getItemAtPosition(arg2).toString();
				break;
			default:
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * 编写BDLocationListener接口的实现类，接收异步返回的定位结果和异步返回的POI查询结果。
	 * 
	 */
	final class MyBDLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			latitude = location.getLatitude();
			longitude = location.getLongitude();
			rplace = location.getAddrStr();
			Log.i(TAG, "latitude = " + latitude);
			Log.i(TAG, "longitude = " + longitude);
			Log.i(TAG, "rplace = " + rplace);

			StringBuffer sb = new StringBuffer(256);
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			Log.i(TAG, sb + "");
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {

			// 将在下个版本中去除poi功能
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			Log.i(TAG, sb + "");
		}
	}

	@Override
	protected void onDestroy() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			if (mBDLocationListener != null) {
				mLocationClient.unRegisterLocationListener(mBDLocationListener);
			}

			mLocationClient.stop();
			mLocationClient = null;
		}
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			if (requestCode == 10) {
				Bundle extras = data.getExtras();
				String home = extras.getString("home", "");
				hometown = home;
				hometown_edit.setText(hometown);
			} else if (requestCode == 0) {
				try {
					Uri originalUri = data.getData();
					// 将图片内容解析成字节数组
					byte[] mContent;
					mContent = ImageUtil
							.readStream(getContentResolver().openInputStream(
									Uri.parse(originalUri.toString())));
					// 将字节数组转换为ImageView可调用的Bitmap对象
					myBitmap = ImageUtil.getPicFromBytes(mContent, null);
					myBitmap = CompleteInfo.comp(myBitmap);
					myBitmap = ImageUtil.toRoundCorner(myBitmap, 20);
					myhead.setImageBitmap(myBitmap);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			finish();
		}
	};

	private Bitmap myBitmap;
	ImageView myhead;

	private void postHeadImage() {
		String headimage = "";
		if (myBitmap != null) {
			headimage = URLEncoder.encode(ImageUtil.bitmaptoString(myBitmap));
			headimage = ImageUtil.bitmaptoString(myBitmap);
			// System.out.println(headimage);
			postImg2Jead(headimage);
		} else {
			showRegistSuccess();
		}

	}

	private void postImg2Jead(String headimage) {
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				// System.out.println(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				showRegistSuccess();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.postHeadImage(id, headimage, res);
	}

	private void showRegistSuccess() {
		progressDialog.dismiss();
		Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_LONG)
				.show();
		Intent intent = new Intent(SiginActivity.this, SiginSuccess.class);
		startActivity(intent);
	}

	private static final int REQUEST_CAMERA = 1;

	private void MyDialog() {
		final CharSequence[] items = { "相册", "拍照" };
		AlertDialog dlg = new AlertDialog.Builder(SiginActivity.this)
				.setTitle("选择图片")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 这里item是根据选择的方式，
						// 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
						if (item == 1) {
							Intent getImageByCamera = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							startActivityForResult(getImageByCamera,
									REQUEST_CAMERA);
						} else {
							Intent getImage = new Intent(
									Intent.ACTION_GET_CONTENT);
							getImage.addCategory(Intent.CATEGORY_OPENABLE);
							getImage.setType("image/jpeg");
							startActivityForResult(getImage, 0);
						}
					}
				}).create();
		dlg.show();
	}

}
