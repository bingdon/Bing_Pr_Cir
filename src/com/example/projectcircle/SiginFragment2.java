package com.example.projectcircle;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.complete.CompleteDriver;
import com.example.projectcircle.complete.CompleteInfo;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.setting.ModifyInfoActivity;
import com.example.projectcircle.util.BingDateUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ˾�� ѡ���豸
 */
public class SiginFragment2 extends Activity implements OnClickListener {
	/**
	 * ѡ���豸
	 */
	LinearLayout d_radio1, d_radio2, d_radio3, d_radio4, d_radio5;
	CheckBox btn1, btn2, btn3, btn4, btn5;
	public static EditText d_content;
	TextView device_name;
	String device_content;
	public static String equipment;
	
	private static final String TAG=SiginFragment2.class.getSimpleName();
	
	TextView driver_time_year, driver_time_month;
	int timeyear, timemonth;
	// �ּ�ʻ�豸
	EditText now_brand_txt, now_type_txt;
	String now_brand, now_type;
	String now_device;
	String device_age;
	String uid;
	String type;
	/**
	 * ����Button
	 */
	Button back, next;
	DatePickerDialog mDialog;

	int year_index, month_index;
	
	private String real_Time="";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_fragment1);
		initDevice();
		initBtn();
		initView();
		if (!ModifyInfoActivity.isModify()) {
			findViewById(R.id.driver).setVisibility(View.GONE);
		}else {
			findViewById(R.id.driver_next).setVisibility(View.GONE);
		}
		initFilter();
		
		inittab0();
		
	}

	/**
	 * ѡ���豸 ��ʼ�� ����ʵ�� ��ѡ
	 */
	private void initDevice() {
		device_name = (TextView) findViewById(R.id.device_name1);
		device_name.setText("���ּ�ʻ�豸");
		d_content = (EditText) findViewById(R.id.device1_sigin_car);
//		// TODO Auto-generated method stub
//		d_radio1 = (LinearLayout) findViewById(R.id.car_radiobtn1);
//		d_radio2 = (LinearLayout) findViewById(R.id.car_radiobtn2);
//		d_radio3 = (LinearLayout) findViewById(R.id.car_radiobtn3);
//		d_radio4 = (LinearLayout) findViewById(R.id.car_radiobtn4);
//		d_radio5 = (LinearLayout) findViewById(R.id.car_radiobtn5);
		
		d_content.addTextChangedListener(watcher);
		
		btn1 = (CheckBox) findViewById(R.id.device1_btn1);
		btn2 = (CheckBox) findViewById(R.id.device1_btn2);
		btn3 = (CheckBox) findViewById(R.id.device1_btn3);
		btn4 = (CheckBox) findViewById(R.id.device1_btn4);
		btn5 = (CheckBox) findViewById(R.id.device1_btn5);	
		btn1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
     
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				 if(btn1.isChecked()){
						btn2.setChecked(false);
						btn3.setChecked(false);
						btn4.setChecked(false);
						btn5.setChecked(false);
						equipment = "�ھ��";
						d_content.setVisibility(View.GONE);
					}
			}
    });
		btn2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		     
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
			 if(btn2.isChecked()){
					btn1.setChecked(false);
					btn3.setChecked(false);
					btn4.setChecked(false);
					btn5.setChecked(false);
					equipment = "��ж��";
					d_content.setVisibility(View.GONE);
				}
			}
	});
		btn3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		     
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				 if(btn3.isChecked()){
						btn1.setChecked(false);
						btn2.setChecked(false);
						btn4.setChecked(false);
						btn5.setChecked(false);
						equipment = "װ�ػ�";
						d_content.setVisibility(View.GONE);
					}
			}
    });
		btn4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		     
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
			 if(btn4.isChecked()){
					btn1.setChecked(false);
					btn2.setChecked(false);
					btn3.setChecked(false);
					btn5.setChecked(false);
					equipment = "ƽ�峵";
					d_content.setVisibility(View.GONE);
				}
			}
	});
		btn5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		     
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
			 if(btn5.isChecked()){
					btn1.setChecked(false);
					btn2.setChecked(false);
					btn3.setChecked(false);
					btn4.setChecked(false);;
					d_content.setVisibility(View.VISIBLE);
					equipment = d_content.getText().toString();
				}
			}
	});
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
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
				submit();
			} else {
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
	
	
	private void initView() {
		// TODO Auto-generated method stub
		// ����
		driver_time_year = (TextView) findViewById(R.id.driver_time_year);
		driver_time_month = (TextView) findViewById(R.id.driver_time_month);

		driver_time_year.setOnClickListener(timelistener);
		driver_time_month.setOnClickListener(timelistener);
		// �ּ�ʻ�豸
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
		type ="˾��";
		uid = SiginActivity.id;
		if (TextUtils.isEmpty(uid)) {
			uid=LoginActivity.id;
		}
		// ������
		// type = "˾��";
		// uid = "25";
		now_device = now_brand + now_type;

		// ���侫ȷ���·� ʵ��ÿ�����
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
		if (TextUtils.isEmpty(device_age)) {
			return;
		}
		if (TextUtils.isEmpty(now_device)) {
			now_device="";
		}
		CompleteDriver1(uid, type, device_age, now_device);
	}

	private void CompleteDriver(String uid, String type,
			String driveryears, String nequ) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				AppLog.i(TAG, "���:"+response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						Toast.makeText(getApplicationContext(), "��ӳɹ���",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), "���ʧ�ܣ�",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		MyHttpClient client = new MyHttpClient();
		client.CompleteDriver(uid, type, driveryears, nequ, res);
	}
	
	
	private void CompleteDriver1(String uid, String type,
			String driveryears, String nequ) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				AppLog.i("jjjjj", "����:"+response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						Toast.makeText(getApplicationContext(), "��ӳɹ���",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), "���ʧ�ܣ�",
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
		showDialog(0);// ���ڵ�����
		int SDKVersion = getSDKVersionNumber();// ��ȡϵͳ�汾
		System.out.println("SDKVersion = " + SDKVersion);
		DatePicker dp = findDatePicker((ViewGroup) mDialog.getWindow()
				.getDecorView());// ���õ���������
		if (dp != null) {
			if (SDKVersion < 11) {
				((ViewGroup) dp.getChildAt(0)).getChildAt(1).setVisibility(
						View.GONE);
			} else if (SDKVersion > 14) {
				// ����getChildAt()��������˭ 0���� 1���� 2����
				((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
						.getChildAt(2).setVisibility(View.GONE);
			}
		}
	}

	
	public int getSDKVersionNumber() {
		int sdkVersion;
		try {
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			sdkVersion = 0;
		}
		return sdkVersion;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) { // ��Ӧ�����showDialog(0);//���ڵ�����
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
	 * �ӵ�ǰDialog�в���DatePicker�ӿؼ�
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
	
	private void inittab0() {
		if (MyApplication.getMyPersonBean()==null) {
			return;
		}
		if (MyApplication.getMyPersonBean().getType().equals("˾��")) {
			if (null != MyApplication.getMyPersonBean().getEquipment()) {
				String devicestrString = MyApplication.getMyPersonBean()
						.getEquipment();
				int length = devicestrString.indexOf(",");
				if (length <0) {
					initDevice(devicestrString);
				} else {
					String device[] = devicestrString.split(",");
					for (int i = 0; i < device.length; i++) {
						initDevice(device[i]);
					}
				}

			}
		}
		
		
		UserDetail(MyApplication.getMyPersonBean().getId());
		
	}

	private void initDevice(String str) {
		switch (str) {
		case "�ھ��":
			btn1.setChecked(true);
			break;
		case "��ж��":
			btn2.setChecked(true);
			break;

		case "װ�ػ�":
			btn3.setChecked(true);
			break;

		case "ƽ�峵":
			btn4.setChecked(true);
			break;

		case "����":
			btn5.setChecked(true);
			break;

		default:
			break;
		}
	}
	
	
	
	private void UserDetail(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "������Ϣ����:" + response);
					parseUserDetail1(response);
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail2(id, "˾��", res);
	}
	
	String driveryearO;
	String nequ;
	String oequ;
	// ������˾��ʱ��Ľ���
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
						AppLog.i(TAG, "ʱ��:"+driver_start);
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
	
		private void countYear(String driver_start) {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int montha = BingDateUtils.getMonth(driver_start);
			int yeara = BingDateUtils.getYear(driver_start);
			int monthdis = 0;
			int yeardis = 0;
			if (month >= montha) {
				monthdis = month - montha;
			} else {
				year = year - 1;
				yeardis = year - yeara;
				monthdis = month + 12 - montha;
			}
			if (yeardis > 0) {
				driveryearO = yeardis + "��" + monthdis + "����";
			} else {
				driveryearO = monthdis + "����";
			}
		}
		
		
		private TextWatcher watcher=new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				equipment=s.toString();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		};
		
}
