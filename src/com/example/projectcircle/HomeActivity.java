package com.example.projectcircle;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.projectcircle.adpter.HomeAdapter;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.complete.CompleteCommercial;
import com.example.projectcircle.complete.CompleteDriver;
import com.example.projectcircle.complete.CompleteInfo;
import com.example.projectcircle.complete.CompleteMaster;
import com.example.projectcircle.group.GroupPage;
import com.example.projectcircle.personal.PersonalPage;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;


/**
 * 主界面附近查询
 * 
 * @author Walii
 * @version 2014.3.18
 */
@SuppressLint("CutPasteId")
public class HomeActivity extends Activity {
	String id;
	/**
	 * 个人信息
	 * 
	 */
	LinearLayout myinfo;
	TextView uname_txt, utype_txt, ucity_txt, udevice_txt, uaddress_txt,
			ucontent_txt;
	ImageView uheadimage;
	String uname, uheadimg,  ucity, udevice, ucontent;
	public static String utype;
	/**
	 * 群组信息
	 * 
	 */
	// 附近群组和跨区域群组
	LinearLayout neargroup, areagroup;
	public static double latitude;
	public static double longitude;
	public static int radius;// 半径
	public static String rplace;// 定位全地址
	// 附近群组信息
	TextView ng_nearnum_txt, ng_total_txt, ng_address_txt;
	int ng_nearnum;
	String ng_total;
	// 跨区域群组信息
	TextView area_num_txt;
	String area_num;
	/**
	 * 附近人信息
	 * 
	 */
	public ArrayList<UserInfo> usersList;
	/**
	 * ListView
	 * 
	 */
	private static final String TAG = "HomeActivity";
	ListView listview;
	HomeAdapter myAdapter;
	View headview;

	/**
	 * 顶部Button
	 * 
	 */

	/**
	 * 定位
	 * 
	 */
	private LocationClient mLocationClient;
	private MyBDLocationListener mBDLocationListener;
	String province;
	String city;
	String district;

	// 图片缓存
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	Context context;
	SharedPreferences sharedPreferences;
	private String hobby;
	private String info;
	private String userlocation;
	private TextView search;
//	private PopupWindow mPop;
	private CheckBox all_user,fellow_villager,owner,businesses,a_driver,all_machine,a_machine_lord,a_dump_truck,a_loader,a_scooter;
	private Button sure,cancel;;
	private CheckBox professional;
	private String addr,type,equ;
	 AlertDialog show ;
	//找老乡需要的我的籍贯
	private String myaddr;
	
	public static String myUserhaedurl="";
	private double commercialLat;
	private double commercialLon;
	private Object distance;
	private static String lat="";
	public static String getLat() {
		return lat;
	}



	private static String lon="";

	public static String getLon() {
		return lon;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// listview初始化 增加头部view 放在这里 是为了避免 在更新定位的时候 无限重复启动
		listview = (ListView) findViewById(R.id.main_listview);
		headview = this.getLayoutInflater().inflate(R.layout.headview, null);
		listview.addHeaderView(headview);
		initFilter();
		initView();
		
		initLocation();
		
		id = LoginActivity.id;
		getUserInfo();
		UserDetail(id);
		select();
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.build();
	}

	private void initFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("yi.jing.wan.shan");			
		registerReceiver(msgReceiver, filter);
	}


	private void select() {
		// TODO Auto-generated method stub
		search = (TextView) findViewById(R.id.circle_right);
		search.setOnClickListener(new OnClickListener() {		

			public void onClick(View v) {
				LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

//				ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
//						R.layout.near_select_pop_window, null, true);
//				mPop = new PopupWindow(menuView,
//						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT,
//						true);
//				mPop.setAnimationStyle(R.style.AnimationPreview);
//				mPop.setOutsideTouchable(true);
//				mPop.setFocusable(false);
////				mPop.setWidth(android.widget.RelativeLayout.LayoutParams.MATCH_PARENT);
////				mPop.setHeight(600);
//				Drawable dr = getResources().getDrawable(R.drawable.bg2); // 取得图片资源
//				
//				mPop.setBackgroundDrawable(dr);			
////				mPop.showAtLocation(findViewById(R.id.linearLayout1), Gravity.CENTER, 0, 0);
////				mPop.showAsDropDown(findViewById(R.id.linearLayout1), 10, 10);
//				mPop.showAtLocation(search, Gravity.CENTER, 0, 0);
////				RelativeLayout upfriend = (RelativeLayout) findViewById(R.id.add_friend_Layout1);
////				mPop.showAsDropDown(upfriend, 420, 0);// 设置显示PopupWindow的位置位于View的左下方，x,y表示坐标偏移量
////				mPop.showAtLocation(findViewById(R.id.textView1), Gravity.LEFT,
////						0, 0);// （以某个View为参考）,表示弹出窗口以parent组件为参考，位于左侧，偏移-90。
				LayoutInflater inflater = getLayoutInflater();
				  View menuView = inflater.inflate(R.layout.near_select_pop_window,
				    null);
				 show = new AlertDialog.Builder(HomeActivity.this).setView(menuView).show();
//				     .setPositiveButton("确定", pListener());
//				    .setNegativeButton("取消", null)
						

                all_user = (CheckBox) menuView
						.findViewById(R.id.all_user);
				fellow_villager = (CheckBox) menuView
						.findViewById(R.id.fellow_villager);
				professional = (CheckBox) menuView
				        .findViewById(R.id.professional);
				owner = (CheckBox) menuView
						.findViewById(R.id.owner);
				a_driver = (CheckBox) menuView
						.findViewById(R.id.a_driver);
				businesses = (CheckBox) menuView
						.findViewById(R.id.businesses);			
				all_machine = (CheckBox) menuView
						.findViewById(R.id.all_machine);
				a_machine_lord= (CheckBox) menuView
						.findViewById(R.id.a_machine_lord);
				a_dump_truck = (CheckBox) menuView
						.findViewById(R.id.a_dump_truck);
				a_loader = (CheckBox) menuView
						.findViewById(R.id.a_loader);
				a_scooter = (CheckBox) menuView
						.findViewById(R.id.a_scooter);	
				sure = (Button)menuView.findViewById(R.id.sure);
				cancel = (Button)menuView.findViewById(R.id.cancel);
				all_user.setOnClickListener(click_listener);//全部按钮选中的时候老乡按钮不能选
				professional.setOnClickListener(click_listener);//全部按钮选中的时候机主、司机、商家按钮不能选
				all_machine.setOnClickListener(click_listener);//全部按钮选中时，挖掘机、自卸车、装载机、板车按钮不能选
				sure.setOnClickListener(click_listener);
				cancel.setOnClickListener(click_listener);
			}
		});

	}



	private View.OnClickListener click_listener = new OnClickListener() {


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.all_user:
				if(all_user.isChecked()){
					fellow_villager.setEnabled(false);
				}else{
					fellow_villager.setEnabled(true);
				}
					break;
		    case R.id.professional:
		    	if(professional.isChecked()){
	                   owner.setEnabled(false);
	                   a_driver.setEnabled(false);
	                   businesses.setEnabled(false);
					}else{
						owner.setEnabled(true);
		                a_driver.setEnabled(true);
		                businesses.setEnabled(true);
					}
					break;
		    case R.id.all_machine:
		    	if(all_machine.isChecked()){
					a_machine_lord.setEnabled(false);
					a_dump_truck.setEnabled(false);
					a_loader.setEnabled(false);
					a_scooter.setEnabled(false);
				}else{
					a_machine_lord.setEnabled(true);
					a_dump_truck.setEnabled(true);
					a_loader.setEnabled(true);
					a_scooter.setEnabled(true);
				}
				break;
	        case R.id.sure:
	        	if(all_user.isChecked()) {
	        		addr= "全部";
	        		}
	        	else if(fellow_villager.isChecked()){
	        			addr= ucity ;//我籍贯的老乡
	        		};
	        if(professional.isChecked()){
	        	type= "机主,司机,商家";
	        }else{
	        	if(owner.isChecked() && (!a_driver.isChecked()) &&(!businesses.isChecked()) ){
	        		type= "机主";
	        	}else if(owner.isChecked() && a_driver.isChecked()&&(!businesses.isChecked())){
	        		type= "机主,司机";
	        	}else if(owner.isChecked() && a_driver.isChecked()&&businesses.isChecked()){
	        		type= "机主,司机,商家";
	        	}else if((!owner.isChecked()) && a_driver.isChecked()&&businesses.isChecked()){
	        		type= "司机,商家";
	        	}else if((!owner.isChecked()) && (!a_driver.isChecked())&&businesses.isChecked()){
	        		type= "商家";
	        	}else if((!owner.isChecked()) && a_driver.isChecked()&&(!businesses.isChecked())){
	        		type= "司机";
	        	}else if(a_machine_lord.isChecked() && (!a_driver.isChecked())&&businesses.isChecked()){
	        		type= "机主,商家";
	        	}
	        	
	        };
	        if(all_machine.isChecked()){
	        	equ= "挖掘机,自卸车,装载机,板车";
	        }else{
	        	if(a_machine_lord.isChecked() && a_dump_truck.isChecked() && a_loader.isChecked() && a_scooter.isChecked()){
	        		equ= "挖掘机,自卸车,装载机,板车";
	        	}else if((!a_machine_lord.isChecked()) && a_dump_truck.isChecked() && a_loader.isChecked() && a_scooter.isChecked()){
	        		equ= "自卸车,装载机,板车";
		        }else if(a_machine_lord.isChecked() && (!a_dump_truck.isChecked()) && a_loader.isChecked() && a_scooter.isChecked()){
		        	equ= "挖掘机,装载机,板车";
		        }else if(a_machine_lord.isChecked() && a_dump_truck.isChecked() && (!a_loader.isChecked()) && a_scooter.isChecked()){
		        	equ= "挖掘机,自卸车,板车";
		        }else if(a_machine_lord.isChecked() && a_dump_truck.isChecked() && a_loader.isChecked() && (!a_scooter.isChecked())){
		        	equ= "挖掘机,自卸车,装载机";
		        }else if((!a_machine_lord.isChecked()) && (!a_dump_truck.isChecked()) && a_loader.isChecked() && (!a_scooter.isChecked())){
		        	equ= "装载机,板车";
		        }else if(a_machine_lord.isChecked() && (!a_dump_truck.isChecked()) && (!a_loader.isChecked()) && a_scooter.isChecked()){
		        	equ= "挖掘机,板车";
		        }else if(a_machine_lord.isChecked() && a_dump_truck.isChecked() && (!a_loader.isChecked()) && (!a_scooter.isChecked())){
		        	equ= "挖掘机,自卸车";
		        }else if((!a_machine_lord.isChecked()) && a_dump_truck.isChecked() && (!a_loader.isChecked()) && (!a_scooter.isChecked())){
		        	equ= "自卸车,板车";
		        }else if(a_machine_lord.isChecked() && (!a_dump_truck.isChecked()) && a_loader.isChecked() && (!a_scooter.isChecked())){
		        	equ= "挖掘机,装载机";
		        }else if((!a_machine_lord.isChecked()) && a_dump_truck.isChecked() && a_loader.isChecked() && (!a_scooter.isChecked())){
		        	equ= "自卸车,装载机";
		        }else if(a_machine_lord.isChecked() && (!a_dump_truck.isChecked()) && (!a_loader.isChecked()) && (!a_scooter.isChecked())){
		        	equ= "挖掘机";
		        }else if((!a_machine_lord.isChecked()) && a_dump_truck.isChecked() && (!a_loader.isChecked()) && (!a_scooter.isChecked())){
		        	equ= "自卸车";
		        }else if((!a_machine_lord.isChecked()) && (!a_dump_truck.isChecked()) && a_loader.isChecked() && (!a_scooter.isChecked())){
		        	equ= "装载机";
		        }else if((!a_machine_lord.isChecked()) && (!a_dump_truck.isChecked()) && (!a_loader.isChecked()) && a_scooter.isChecked()){
		        	equ= "板车";
		        }
	        };
				userFilter(addr.substring(0, 2), type, equ);
				//再清一遍，发现如果不清，再次筛选还保留 上次筛选的痕迹
				addr = "全部";
				type = "全部";
				equ = "全部";
				show.dismiss();
				break;
	        case R.id.cancel:
	        	show.dismiss();
				break;
			default:
				break;
			}
		}
	};
	private String uid;
   //筛选出想看到的用户，职业，设备

	private void userFilter(String address,String type,String equ) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				Log.i(TAG, "筛选:"+response);
				System.out.println(response);	
				try {
					JSONObject	obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {						
						parseuserFilter(response);

						Log.i(TAG, "附近更新:"+getList());
						myAdapter = new HomeAdapter(getList(), HomeActivity.this);
						listview.setAdapter(myAdapter);					
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"搜索无效！");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}
		};
		MyHttpClient myhttpclient = new MyHttpClient();
		myhttpclient.UserFilter(address,type,equ,latitude,longitude, res);
	}
  //对筛选出想看到的用户，职业，设备，返回response的解析
	private void parseuserFilter(String response) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject result = new JSONObject(response);
			Log.i("parseuserFilter response",response+"");
			JSONObject obj = result.getJSONObject("users");
			usersList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				UserInfo user = new UserInfo();
				obj = json.getJSONObject(i);
				user.setId(obj.getString("id"));
				user.setTel(obj.getString("tel"));
				user.setUsername(obj.getString("username"));
				user.setType(obj.getString("type"));
				user.setAddress(obj.getString("address"));
				user.setEquipment(obj.getString("equipment"));
				user.setSign(obj.getString("sign"));
				user.setHeadimage(obj.getString("headimage"));
				user.setAccept(obj.getString("accept"));
				try {
					user.setLat(obj.getDouble("commercialLat"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					user.setLon(obj.getDouble("commercialLon"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				usersList.add(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.w(TAG, "附近解析异常");
		}
	}
	/**
	 * 初始化定位
	 * 
	 */
	private void initLocation() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(this.getApplicationContext());

		mBDLocationListener = new MyBDLocationListener();
		////注册位置监听器
		mLocationClient.registerLocationListener(mBDLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式,//Hight_Accuracy高精度、Battery_Saving低功耗、Device_Sensors仅设备(GPS)
		option.setOpenGps(true);// 打开gps,设置是否打开gps，使用gps前提是用户硬件打开gps。默认是不打开gps的。
		option.setScanSpan(600000);// 定位的时间间隔，单位：ms
		// 需要地址信息，设置为其他任何值（string类型，且不能为null）时，都表示无地址信息。
		option.setAddrType("all");

		// 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
		option.setPoiExtraInfo(true);

		// 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		option.setProdName("通过GPS定位我当前的位置");

		// 查询范围，默认值为500，即以当前定位位置为中心的半径大小。
		option.setPoiDistance(600);
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
		//NearUser(id, radius, latitude, longitude);
	}

	private void initView() {
		// TODO Auto-generated method stub

		
		myinfo = (LinearLayout) findViewById(R.id.myinfo_layout);
		uname_txt = (TextView) findViewById(R.id.main_my_name);
		utype_txt = (TextView) findViewById(R.id.main_my_career);
		ucity_txt = (TextView) findViewById(R.id.main_my_native);
		udevice_txt = (TextView) findViewById(R.id.main_my_device);
		//uaddress_txt = (TextView) findViewById(R.id.main_my_time);
		ucontent_txt = (TextView) findViewById(R.id.main_my_content);
		uheadimage = (ImageView) findViewById(R.id.main_my_head_img);
		
		
		neargroup = (LinearLayout) findViewById(R.id.neargroup_layout);		
		neargroup.setOnClickListener(listener);

		ng_nearnum_txt = (TextView) findViewById(R.id.ng_nearnum);
		ng_total_txt = (TextView) findViewById(R.id.ng_total);
		ng_address_txt = (TextView) findViewById(R.id.ng_address);
		
		myinfo.setOnClickListener(listener);
		
		myAdapter = new HomeAdapter(getList(), this);
		//刷新
		myAdapter.notifyDataSetChanged();
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this,
						PersonalPage.class);
				intent.putExtra("id", usersList.get(arg2 - 1).getId());
				intent.putExtra("type", usersList.get(arg2 - 1).getType());
				intent.putExtra("time",usersList.get(arg2 - 1).getLastlogintime());
				intent.putExtra("lat",usersList.get(arg2 - 1).getLat());
				intent.putExtra("lon",usersList.get(arg2 - 1).getLon());
				startActivity(intent);
				// finish();
			}
		});
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
				LoginActivity.parseUserInfo(response, HomeActivity.this);
				//判断头部的“当前资料不完整，完善后有助于交友”这行字是否显示
				WhetherDisplayPrompt();
				initUser();
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
			ucontent = obj.getString("sign");//个性签名
			uheadimg = obj.getString("headimage");
			rplace = obj.getString("rplace");//常出没的地方
			userlocation = obj.getString("place");//常出没的地方
			hobby = obj.getString("hobby");//兴趣爱好
		    info = obj.getString("info");//个人信息

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取信息
	 */
	private void getUserInfo(){
		MyPersonBean myPersonBean=MyApplication.getMyPersonBean();
		if (myPersonBean==null) {
			return;
		}
		
		uname = myPersonBean.getUsername();
		utype = myPersonBean.getType();
		ucity = myPersonBean.getAddress();
		udevice = myPersonBean.getEquipment();
		ucontent = myPersonBean.getSign();
		uheadimg = myPersonBean.getHeadimage();
		rplace = myPersonBean.getRplace();//常出没的地方
		userlocation = myPersonBean.getPlace();//常出没的地方
		hobby = myPersonBean.getHobby();//兴趣爱好
	    info = myPersonBean.getInfo();//个人信息
	    WhetherDisplayPrompt();
		initUser();
		
	}
	
	/**
	 * 群组信息 请求和解析
	 * 
	 */
	private void NearGroup(int radius, double lat, double lon) {
		// TODO Auto-generated method stub

		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "返回:" + response);
				parseNearGroup(response);
				initGroup();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.NearGroup(radius, lat, lon, res);
	}

	public void parseNearGroup(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("nearGroup");
			JSONArray json = obj.getJSONArray("resultlist");
			Log.i("parseNearGroup json---",json+"");			
			ng_nearnum = json.length();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 某区域的群组数 请求和解析
	 * 
	 */
	private void CountGroup(String key) {
		// TODO Auto-generated method stub
		
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "返回:" + response);
				parseCountGroup(response);
				initGroup();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.CountGroup(key, res);
	}

	public void parseCountGroup(String response) {
		try {
			JSONObject result = new JSONObject(response);
			ng_total = result.getString("groupcount");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 跨区域群组 请求和解析
	 * 
	 */
	private void GroupList() {
		// TODO Auto-generated method stub

		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "返回:" + response);
				parseGroupList(response);
				initGroup();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.GroupList(res);

	}

	public void parseGroupList(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("groups");
			area_num = obj.getString("totalrecord");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 附近人信息 请求和解析
	 * 
	 */
	private void NearUser(String id, int radius, double lat, double lon) {
		// TODO Auto-generated method stub

		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("NearUser的response", "附近:" + response);
				parseNearUser(response);
				
				
				
//				initView();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.NearUser(id, radius, lat, lon, res);

	}

	public void parseNearUser(String response) {
		
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("nearuser");
			Log.i("nearuser的response", "附近的好友"+obj);
			usersList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				if(!(json.getJSONObject(i).getString("id").equals(LoginActivity.id))){
				UserInfo user = new UserInfo();
				obj = json.getJSONObject(i);
				user.setId(obj.getString("id"));
				user.setTel(obj.getString("tel"));
				user.setUsername(obj.getString("username"));
				user.setSign(obj.getString("sign"));
				user.setType(obj.getString("type"));				
				user.setAddress(obj.getString("address"));
				user.setEquipment(obj.getString("equipment"));
				user.setSign(obj.getString("sign"));
				user.setHeadimage(obj.getString("headimage"));
				user.setAccept(obj.getString("accept"));
				user.setLastlogintime(obj.getString("lastlogintime"));
				try {
					user.setLat(obj.getDouble("commercialLat"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					user.setLon(obj.getDouble("commercialLon"));
				} catch (Exception e) {
					// TODO: handle exception
				}				
				usersList.add(user);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.w(TAG, "附近解析异常");
		}

		Log.i(TAG, "附近更新:"+getList());
		myAdapter = new HomeAdapter(getList(), HomeActivity.this);
		listview.setAdapter(myAdapter);
		
	}

	/**
	 * 个人信息 初始化
	 * 
	 */
	private void initUser() {
		// TODO Auto-generated method stub
		

		uname_txt.setText(uname);
		utype_txt.setText(utype);
		//地址只截取省，因为省市是由空格分开的，所以截取第一个空格前的内容即为省
		if(!ucity.equals("null")){
		String arrays[] = ucity.split(" ");	
		ucity_txt.setText(arrays[0]);}
		udevice_txt.setText(udevice);
//		uaddress_txt.setText(rplace);
		ucontent_txt.setText( ucontent + "");
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL + uheadimg, uheadimage,
				options, animateFirstListener);
		
		myUserhaedurl=MyHttpClient.IMAGE_URL + uheadimg;

		
	}

	/**
	 * 群组信息 初始化
	 * 
	 */
	private void initGroup() {
		// TODO Auto-generated method stub
		

		ng_nearnum_txt.setText("您附近有" + ng_nearnum + "个群组");
		ng_total_txt.setText("北京约有" + ng_total + "个群组");
		ng_address_txt.setText(rplace);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.myinfo_layout:
				Intent intent = new Intent(HomeActivity.this, PersonalPage.class);
				intent.putExtra("id", id);
				intent.putExtra("type", utype);
//				intent.putExtra("time",);
				intent.putExtra("lat",latitude);
				intent.putExtra("lon",longitude);
				startActivity(intent);
				// finish();
				break;
			case R.id.neargroup_layout:
				Intent intent2 = new Intent(HomeActivity.this, GroupPage.class);
				startActivity(intent2);
				// finish();
				break;
		
			}
		}
	};


	private ArrayList<HashMap<String, Object>> getList() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		if(usersList != null){
		for (int i = 0; i < usersList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", usersList.get(i).getId());
			map.put("tel", usersList.get(i).getTel());
			map.put("name", usersList.get(i).getUsername());
			map.put("type", usersList.get(i).getType());
			map.put("address", usersList.get(i).getAddress());
			map.put("equipment", usersList.get(i).getEquipment());
			map.put("sign", usersList.get(i).getSign());
			map.put("headimage", usersList.get(i).getHeadimage());
			map.put("lastlogintime", usersList.get(i).getLastlogintime());
			map.put("accept", usersList.get(i).getAccept());
			commercialLat = usersList.get(i).getLat();
			commercialLon = usersList.get(i).getLon();
			//计算我和好友之间的距离
			distance = DistentsUtil.GetDistance(commercialLat,commercialLon,
					latitude,longitude);
			map.put("distance", distance);
			listItem.add(map);
		}
		}
//		   Collections.sort(listItem, new Comparator<HashMap<String,Object>>(){  
//			   
//	           @Override  
//	           public int compare(HashMap<String, Object> arg0,HashMap<String, Object> arg1) {  
//	               // TODO Auto-generated method stub  
//	               return ((String) arg0.get("name")).compareTo((String) arg1.get("name"));  
//	           }  
//	       }); 
		return listItem;
	}

	/**
	 * 编写BDLocationListener接口的实现类，接收异步返回的定位结果和异步返回的POI查询结果。
	 * 
	 */
	final class MyBDLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			province = location.getProvince(); // 获取省份信息
			city = location.getCity(); // 获取城市信息
			district = location.getDistrict(); // 获取区县信息
			rplace = location.getAddrStr();// 全地址
			Log.i(TAG, "province = " + province);
			Log.i(TAG, "city = " + city);
			Log.i(TAG, "district = " + district);

			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
			lat=""+latitude;
			lon=""+longitude;
			//radius = (int) location.getRadius();
			Log.i(TAG, "latitude = " + latitude);
			Log.i(TAG, "longitude = " + longitude);
			Log.i(TAG, "radius = " + radius);

			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nProvince : ");
			sb.append(location.getProvince());
			sb.append("\ncity : ");
			sb.append(location.getCity());
			sb.append("\nDistrict : ");
			sb.append(location.getDistrict());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
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
			// 这两个是头部view 放在后面
			//UserDetail(id);
			NearUser(id, radius, latitude, longitude);// 这是列表 一定要放在前面
			NearGroup(radius, latitude, longitude);
			CountGroup(city);
			GroupList();	
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {

			// 将在下个版本中去除poi功能
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nProvince : ");
			sb.append(poiLocation.getProvince());
			sb.append("\ncity : ");
			sb.append(poiLocation.getCity());
			sb.append("\nDistrict : ");
			sb.append(poiLocation.getDistrict());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
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
	//判断头部的“当前资料不完整，完善后有助于交友”这行字是否显示
	private void WhetherDisplayPrompt() {
		// TODO Auto-generated method stub
		RelativeLayout visableOrNot = (RelativeLayout)findViewById(R.id.bujuxianshi);
		if((userlocation == null ||userlocation.equals("")) && (hobby == null|| hobby.equals(""))&& (info == null|| info.equals("")) && (ucontent == null ||ucontent.equals(""))){			
			visableOrNot.setVisibility(View.VISIBLE);//显示
			visableOrNot.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (utype.equals("机主")) {
						Intent intent2 = new Intent(HomeActivity.this,
								CompleteMaster.class);
						intent2.putExtra("uid", uid);
						startActivity(intent2);
//						finish();
					} else if (utype.equals("司机")) {
						Intent intent3 = new Intent(HomeActivity.this,
								CompleteDriver.class);
						intent3.putExtra("uid", uid);
						startActivity(intent3);
//						finish();
					} else if (utype.equals("商家")) {
						Intent intent4 = new Intent(HomeActivity.this,
								CompleteCommercial.class);
						intent4.putExtra("uid", uid);
						startActivity(intent4);
//						finish();
					} else {
						Intent intent5 = new Intent(HomeActivity.this,
								CompleteInfo.class);
						intent5.putExtra("uid", uid);
						startActivity(intent5);
//						finish();
					}
				}
			});
		}else{
			visableOrNot.setVisibility(View.GONE);//隐藏 ，并且不占用界面空间
		}
	}
	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			UserDetail(id);
		}
	};

	@Override
	protected void onDestroy() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			if (mBDLocationListener != null) {
				mLocationClient.unRegisterLocationListener(mBDLocationListener);
			}

			mLocationClient.stop();
			mLocationClient = null;
		}
		unregisterReceiver(msgReceiver);
		super.onDestroy();
	}

}
