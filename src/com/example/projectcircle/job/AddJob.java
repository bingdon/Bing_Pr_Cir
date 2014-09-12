package com.example.projectcircle.job;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_job_lay);
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		phone = LoginActivity.phone;
		name = LoginActivity.name;
		uid = LoginActivity.id;
		//initLocation();
		initView();
		initBtn();
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
		};
		MyHttpClient client = new MyHttpClient();
		client.PostJob(uid, title, address, type, detail, contact, tel, jlat, jlon, res);
	}

//	/**
//	 * 初始化定位
//	 * 
//	 */
//	private void initLocation() {
//		// TODO Auto-generated method stub
//		mLocationClient = new LocationClient(this.getApplicationContext());
//
//		mBDLocationListener = new MyBDLocationListener();
//		////注册位置监听器
//		mLocationClient.registerLocationListener(mBDLocationListener);
//
//		LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true);// 打开gps
//		option.setScanSpan(60000);// 定位的时间间隔，单位：ms
//		// 需要地址信息，设置为其他任何值（string类型，且不能为null）时，都表示无地址信息。
//		option.setAddrType("all");
//
//		// 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
//		option.setPoiExtraInfo(true);
//
//		// 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
//		option.setProdName("通过GPS定位我当前的位置");
//
//		// 查询范围，默认值为500，即以当前定位位置为中心的半径大小。
//		option.setPoiDistance(600);
//		// 禁用启用缓存定位数据
//		option.disableCache(true);
//		// 坐标系类型，百度手机地图对外接口中的坐标系默认是bd09ll
//		option.setCoorType("bd09ll");
//		// 设置最多可返回的POI个数，默认值为3。由于POI查询比较耗费流量，设置最多返回的POI个数，以便节省流量。
//		option.setPoiNumber(3);
//		// 设置定位方式的优先级。
//		// 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
//		option.setPriority(LocationClientOption.GpsFirst);
//		mLocationClient.setLocOption(option);
//		mLocationClient.start();
//
//	}
//
//	/**
//	 * 编写BDLocationListener接口的实现类，接收异步返回的定位结果和异步返回的POI查询结果。
//	 * 
//	 */
//	final class MyBDLocationListener implements BDLocationListener {
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location == null)
//				return;
//			province = location.getProvince(); // 获取省份信息
//			city = location.getCity(); // 获取城市信息
//			district = location.getDistrict(); // 获取区县信息
//			rplace = location.getAddrStr();// 全地址
//			Log.i(TAG, "province = " + province);
//			Log.i(TAG, "city = " + city);
//			Log.i(TAG, "district = " + district);
//			latitude = location.getLatitude();
//			longitude = location.getLongitude();
//			radius = (int) location.getRadius();
//			Log.i(TAG, "latitude = " + latitude);
//			Log.i(TAG, "longitude = " + longitude);
//			Log.i(TAG, "radius = " + radius);
//
//
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("time : ");
//			sb.append(location.getTime());
//			sb.append("\nerror code : ");
//			sb.append(location.getLocType());
//			sb.append("\nProvince : ");
//			sb.append(location.getProvince());
//			sb.append("\ncity : ");
//			sb.append(location.getCity());
//			sb.append("\nDistrict : ");
//			sb.append(location.getDistrict());
//			sb.append("\nlatitude : ");
//			sb.append(location.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(location.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(location.getRadius());
//			if (location.getLocType() == BDLocation.TypeGpsLocation) {
//				sb.append("\nspeed : ");
//				sb.append(location.getSpeed());
//				sb.append("\nsatellite : ");
//				sb.append(location.getSatelliteNumber());
//			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//				sb.append("\naddr : ");
//				sb.append(location.getAddrStr());
//			}
//			Log.i(TAG, sb + "");
//
//			jobAddress(rplace);	
//		}
//
//		@Override
//		public void onReceivePoi(BDLocation poiLocation) {
//
//			// 将在下个版本中去除poi功能
//			if (poiLocation == null) {
//				return;
//			}
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("Poi time : ");
//			sb.append(poiLocation.getTime());
//			sb.append("\nerror code : ");
//			sb.append(poiLocation.getLocType());
//			sb.append("\nProvince : ");
//			sb.append(poiLocation.getProvince());
//			sb.append("\ncity : ");
//			sb.append(poiLocation.getCity());
//			sb.append("\nDistrict : ");
//			sb.append(poiLocation.getDistrict());
//			sb.append("\nlatitude : ");
//			sb.append(poiLocation.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(poiLocation.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(poiLocation.getRadius());
//			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//				sb.append("\naddr : ");
//				sb.append(poiLocation.getAddrStr());
//			}
//			if (poiLocation.hasPoi()) {
//				sb.append("\nPoi:");
//				sb.append(poiLocation.getPoi());
//			} else {
//				sb.append("noPoi information");
//			}
//			Log.i(TAG, sb + "");
//		}
//	}

}
