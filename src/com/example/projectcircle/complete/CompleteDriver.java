package com.example.projectcircle.complete;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage.SubmitPdu;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.setting.ModifyInfoActivity;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CompleteDriver extends Activity {
	
	
	private static final String TAG=CompleteMaster.class.getSimpleName();
	
	/**
	 * EditText 司机驾驶信息
	 */
	// 驾驶起始
	TextView driver_time_year, driver_time_month;
	int timeyear, timemonth;
	// 现驾驶设备
	EditText now_brand_txt, now_type_txt;
	String now_brand, now_type;
	String now_device;
	String device_age;
	String uid;
	String type;
	/**
	 * 其它Button
	 */
	Button back, next;
	DatePickerDialog mDialog;

	int year_index, month_index;
	private String real_Time="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_driver);
			findViewById(R.id.linearLayout1).setVisibility(View.VISIBLE);
		
		initBtn();
		initView();
		initFilter();
		try {
			UserDetail(MyApplication.getMyPersonBean().getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		// 驾龄
		driver_time_year = (TextView) findViewById(R.id.driver_time_year);
		driver_time_month = (TextView) findViewById(R.id.driver_time_month);

		driver_time_year.setOnClickListener(timelistener);
		driver_time_month.setOnClickListener(timelistener);
		// 现驾驶设备
		now_brand_txt = (EditText) findViewById(R.id.now_driver_brand);
		now_type_txt = (EditText) findViewById(R.id.now_driver_type);

	}

	private View.OnClickListener timelistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.driver_time_year:
			case R.id.driver_time_month:
				TimeDialog();
				break;

			default:
				break;
			}
		}

	};

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.perfect_driver_left);
		next = (Button) findViewById(R.id.driver_next);
		back.setOnClickListener(listener);
		next.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.perfect_driver_left:
				// Intent intent = new Intent(CompleteDriver.this,
				// CompleteInfo.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.driver_next:
				submit();
				break;
			default:
				break;
			}
		}

	
	};

	
	private void submit() {
		// TODO Auto-generated method stub
		now_brand = now_brand_txt.getText().toString().trim();
		now_type = now_type_txt.getText().toString().trim();
		type ="司机";
		uid = LoginActivity.id;
		// 测试用
		// type = "司机";
		// uid = "25";
		now_device = now_brand + now_type;

		// 驾龄精确到月份 实现每年递增
		Time t = new Time();
		t.setToNow();
		int currentyear = t.year;
		Log.i("currentyear", currentyear + "");
		year_index = currentyear - timeyear;
		Log.i("year_index", year_index + "");
		month_index = year_index * 12;
		Log.i("month_index", month_index + "");
		int currentmonth = t.month + month_index;
		Log.i("currentmonth", currentmonth + "");
		int month = currentmonth - timemonth;
		Log.i("month", month + "");
		device_age = month / 12 + "";

		// System.out.println(device_age);
		CompleteDriver1(uid, type, device_age, now_device);
	}

	private void CompleteDriver1(String uid, String type,
			String driveryears, String nequ) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				AppLog.i("jjjjj", "返回:"+response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						Toast.makeText(getApplicationContext(), "添加成功！",
								Toast.LENGTH_LONG).show();
						Intent intent1 = new Intent(CompleteDriver.this,
								CompleteInfo.class);
						startActivity(intent1);
					} else {
						Toast.makeText(getApplicationContext(), "添加失败！",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		MyHttpClient client = new MyHttpClient();
		client.CompleteDriver2(uid, type, driveryears, now_type,now_brand,real_Time, res);
	}
	
	@SuppressWarnings("deprecation")
	private void TimeDialog() {
		// TODO Auto-generated method stub
		showDialog(0);// 日期弹出框
		int SDKVersion = CompleteDriver.this.getSDKVersionNumber();// 获取系统版本
		System.out.println("SDKVersion = " + SDKVersion);
		DatePicker dp = findDatePicker((ViewGroup) mDialog.getWindow()
				.getDecorView());// 设置弹出年月日
		if (dp != null) {
			if (SDKVersion < 11) {
				((ViewGroup) dp.getChildAt(0)).getChildAt(1).setVisibility(
						View.GONE);
			} else if (SDKVersion > 14) {
				// 最后的getChildAt()控制隐藏谁 0：年 1：月 2：日
				((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
						.getChildAt(2).setVisibility(View.GONE);
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) { // 对应上面的showDialog(0);//日期弹出框
		mDialog = null;
		switch (id) {
		case 0:
			Calendar calendar = Calendar.getInstance();
			mDialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							driver_time_year.setText(year + "");
							driver_time_month.setText(monthOfYear + 1 + "");
							timeyear = year;
							timemonth = monthOfYear + 1;
							real_Time=year+"-"+(monthOfYear + 1);
						}
					}, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			break;
		}
		return mDialog;
	}

	/**
	 * 从当前Dialog中查找DatePicker子控件
	 * 
	 * @param group
	 * @return
	 */
	private DatePicker findDatePicker(ViewGroup group) {
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				View child = group.getChildAt(i);
				if (child instanceof DatePicker) {
					return (DatePicker) child;
				} else if (child instanceof ViewGroup) {
					DatePicker result = findDatePicker((ViewGroup) child);
					if (result != null)
						return result;
				}
			}
		}
		return null;
	}

	/**
	 * 获取系统SDK版本
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int getSDKVersionNumber() {
		int sdkVersion;
		try {
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			sdkVersion = 0;
		}
		return sdkVersion;
	}

	private void initFilter() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish.before.regist.page");
		filter.addAction(ContantS.ACTION_SEND_SUBMIT);
		registerReceiver(msgReceiver, filter);
	}

	
	private void subMitW(){
	}
	
	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals(ContantS.ACTION_SEND_SUBMIT)) {
				submit();
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
	
	
	private void UserDetail(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "个人信息返回:" + response);
					parseUserDetail1(response);
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail2(id, "司机", res);
	}
	
	String driveryearO;
	String nequ;
	String oequ;
	// 类型是司机时候的解析
		private void parseUserDetail1(String response) {
			// TODO Auto-generated method stub
			try {
				JSONObject result = new JSONObject(response);
				Log.i("user detail response", response + "");
				if (result.getInt("result") == 1) {
					if (result.getInt("type") == 1) {
						JSONArray arr = result.getJSONArray("driver");
						JSONArray arr1 = arr.getJSONArray(0);
						JSONObject equobj = arr1.getJSONObject(0);
						driveryearO = equobj.getString("driveryear");
						String driver_start=equobj.getString("dbegin");
						AppLog.i(TAG, "时间:"+driver_start);
						nequ = equobj.getString("nequ");
						oequ=equobj.getString("oequ");
						if (!TextUtils.isEmpty(driver_start)) {
							String times[] = driver_start.split("-");
							if (times!=null&times.length==2) {
								driver_time_year.setText(""+times[0]);
								driver_time_month.setText(""+times[1]);
							}
							
						}
						
						if (!TextUtils.isEmpty(oequ)) {
							now_brand_txt.setText(oequ);
						}
						
						if (!TextUtils.isEmpty(nequ)) {
							now_type_txt.setText(nequ);
						}
						
					} 
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	
}
