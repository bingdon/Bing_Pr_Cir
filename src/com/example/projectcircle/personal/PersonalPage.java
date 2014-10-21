package com.example.projectcircle.personal;

import io.rong.imkit.RongIM;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.GroupGallaryAdapter;
import com.example.projectcircle.adpter.HisCreateFriendGridAdapter;
import com.example.projectcircle.adpter.HisCreateGroupGridAdapter;
import com.example.projectcircle.adpter.HisDeviceGridAdapter;
import com.example.projectcircle.bean.EquInfo;
import com.example.projectcircle.bean.FriendDataBean;
import com.example.projectcircle.bean.GroupInfo;
import com.example.projectcircle.bean.MoodInfo;
import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.bean.StatusInfo;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.db.utils.FriendDatabaseUtils;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.friend.FriendPage;
import com.example.projectcircle.friend.MaybeFriend;
import com.example.projectcircle.group.GroupDetail;
import com.example.projectcircle.job.PhotoPagerActivity;
import com.example.projectcircle.job.projectCircle;
import com.example.projectcircle.other.Chat;
import com.example.projectcircle.util.BingDateUtils;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MsgUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class PersonalPage extends Activity {

	private static final String TAG = "PersonalPage";

	private static List<FriendDataBean> friendDataBeans;

	/**
	 * 其它Button
	 */
	Button back, send;
	/**
	 * 头像栏 所有TextView
	 */
	ImageView headbg, headimg;

	TextView name, career, content;
	// 工号
	TextView project_num;
	// 祖籍
	TextView mycity;
	// 年龄
	TextView age_txt;
	// 自我介绍
	TextView intro;
	// 关系
	TextView relation;
	/**
	 * Call-Phone
	 */
	RelativeLayout call_phone;

	/**
	 * 设备详细
	 */
	LinearLayout device_detail_lay;
	// 设备详情
	TextView equipment_detail_txt;

	/**
	 * 他的群组
	 */
	LinearLayout group_lay;
	// 他的群组头像
	ImageView group_headimg;
	// 他的群组名称和群介绍
	TextView group_name, group_content;

	/**
	 * 共同好友
	 */
	LinearLayout common_friend;
	String id;
	String uname, utype, ucity, udevice, ucontent, uheadimg, accept, info, uid,
			age, group, gname, singn, rplace, udriverYear, driver_start;

	private LinearLayout qian_ming_lay;
	private LinearLayout he_friend_lay;// 他的好友的layout
	// 图片缓存
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	// 好友动态
	String status;
	private ArrayList<GroupInfo> groupList;
	private String gcontent;
	private String gheadImage;
	private HisCreateGroupGridAdapter myAdapter;
	private GridView his_group_gridview;
	private ArrayList<HashMap<String, Object>> listItem;
	private ArrayList<HashMap<String, Object>> listItem2;
	private HisCreateGroupGridAdapter gridAdapter;
	private HisDeviceGridAdapter devgridAdapter;
	private HisCreateFriendGridAdapter friendgridAdapter;
	private TextView persingnTextView;
	private TextView recentplaceTextView;

	private ImageLoader imageLoader = ImageLoader.getInstance();
	private Gallery mGallery;
	GroupGallaryAdapter gallaryAdapter;
	private List<String> device_picList = new ArrayList<String>();

	private Button addfriend;
	private GridView his_friend_gridview;

	private String hobby;

	private String type;

	private TextView driver_txt;

	private LinearLayout driver_layout;

	private String companyname;

	private LinearLayout company_lay;

	private TextView company_txt;

	private String business;

	private LinearLayout business_scope_lay;

	ArrayList<String> ename = new ArrayList<String>();// 设备名称
	ArrayList<String> ebrand = new ArrayList<String>();
	ArrayList<String> emodel = new ArrayList<String>();// 设备型号
	ArrayList<String> eweight = new ArrayList<String>();
	ArrayList<String> enumber = new ArrayList<String>();// 设备数量

	private String driveryear;

	private String brand_model;

	private LinearLayout equipment_details;

	private TextView equipment_lay;
	StringBuffer strb = new StringBuffer();

	private String ch;

	private String driveryearO;

	private String nequ, oequ;

	private TextView my_hobby;
	List<UserInfo> friendList;

	private String usualplace;

	private String businessinfo;

	private TextView brief_introduction_of_business;

	private LinearLayout ye_wu_jian_jie_lay;

	private GridView his_device_gridview;
	private ArrayList<EquInfo> deviceList = new ArrayList<>();
	private ArrayList<StatusInfo> mymoodList;
	private GridView his_photo_gridview;
	public static ArrayList<HashMap<String, Object>> commfrihead = new ArrayList<HashMap<String, Object>>();
	private ArrayList<HashMap<String, Object>> mood_listItem = new ArrayList<HashMap<String, Object>>();
	private LinearLayout chu_mo_di_dian_lay;
	private GridView common_friend_gridView;
	private ArrayList<MoodInfo> usermoodsList = new ArrayList<MoodInfo>();
	private HisCreateFriendGridAdapter commonfrigridAdapter;

	private String ccid;
	private double mylat = HomeActivity.latitude;
	private double mylon = HomeActivity.longitude;
	private double lat;// 他个人的纬度
	private double lon;// 他个人的经度
	private LinearLayout relationship_lay;
	private TextView distance_txt;// 我和好友之间的距离的TextView

	private String time;// 用户的上次登录时间

	private TextView time_txt;// 时间的textView

	private double distance;

	private String lastlogintime_new;

	private String timecompute;

	private TextView business_scope_txt;

	private LinearLayout she_bei_tu_pian_lay;

	private LinearLayout he_group_lay;

	private LinearLayout common_friend_lay;// 共同好友的layout

	private LinearLayout self_introduction_lay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_home_page);
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.build();
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		type = intent.getStringExtra("type");
		time = intent.getStringExtra("time");
		ccid = intent.getStringExtra("ccid");
		lat = intent.getDoubleExtra("lat", 0);
		lon = intent.getDoubleExtra("lon", 0);
		getFriInfo(PersonalPage.this);
		initBtn();
		dectRelationship();
		// 设备详情照片
		EquDetail(id);
		if (TextUtils.isEmpty(type)) {
			UserDetail00(id, type);
		} else {
			UserDetail(id, type);
		}

		// 判断是否为好友
		if (isFriend(id)) {
			addfriend.setText("取消好友");
			addfriend.setVisibility(View.VISIBLE);
		} else {
			addfriend.setText("加为好友");
			addfriend.setVisibility(View.VISIBLE);
		}
		;

		findfriend(id);
		findCreatGroup(id);
		// 为了得到个人动态的头像
		// doListMyMood();
		initHisPhoto();

	}

	// 得到共同好友
	private void commonFriend() {
		// TODO Auto-generated method stub
		// 共同好友的layout
		common_friend_lay = (LinearLayout) findViewById(R.id.common_friend_lay);
		// Log.i("返回:friendList", "返回:friendList:common" + friendList.size());
		commfrihead.clear();
		if (friendList == null) {
			return;
		}
		for (int i = 0; i < friendList.size(); i++) {
			String friid = friendList.get(i).getId();
			if (isFriend(friid)) {
				// commfrihead.add(friendList.get(i).getHeadimage());
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", friendList.get(i).getId());
				map.put("address", friendList.get(i).getAddress());
				map.put("type", friendList.get(i).getType());
				map.put("headimage", friendList.get(i).getHeadimage());
				map.put("username", friendList.get(i).getUsername());
				map.put("tel", friendList.get(i).getTel());
				map.put("accept", friendList.get(i).getAccept());
				map.put("ccid", friendList.get(i).getCcid());
				map.put("sign", friendList.get(i).getSign());
				map.put("lastlogintime", friendList.get(i).getLastlogintime());
				map.put("commercialLat", friendList.get(i).getLat());
				map.put("commercialLon", friendList.get(i).getLon());
				commfrihead.add(map);
			}
		}
		if (commfrihead.size() == 0) {
			common_friend_lay.setVisibility(View.GONE);
		}
		common_friend_gridView = (GridView) findViewById(R.id.common_friend);
		commonfrigridAdapter = new HisCreateFriendGridAdapter(commfrihead, this);
		common_friend_gridView.setAdapter(commonfrigridAdapter);
		// 点击进入他的联系人列表
		common_friend_gridView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(PersonalPage.this,
								HisContact.class);
						intent.putExtra("id", id);
						intent.putExtra("biaoji", "1");// 共同好友的标记
						startActivity(intent);
					}
				});
	}

	// 设备照片
	private void EquDetail(String uid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "个人设备信息返回:" + response);
				try {
					deviceList = new ArrayList<>();
					JSONObject result = new JSONObject(response);
					JSONObject equ = result.getJSONObject("equ");
					JSONArray array = equ.getJSONArray("resultlist");
					for (int i = 0; i < array.length(); i++) {
						EquInfo device = new EquInfo();
						JSONArray array1 = array.getJSONArray(i);
						JSONObject obj = array1.getJSONObject(0);
						device = new Gson().fromJson(obj.toString(),
								EquInfo.class);
						// device.setHeadimage(obj.getString("headimage"));
						// Log.i(TAG, "设备头像:" + obj.getString("headimage"));
						deviceList.add(device);
					}
					initHisDevice();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.EquDetail(uid, res);
	}

	/**
	 * 监视申请好友状态 如果对方通过了 发送消息 控件消失
	 * 
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@SuppressWarnings("unchecked")
	public static void getFriInfo(Context context) {
		friendDataBeans = (List<FriendDataBean>) new FriendDatabaseUtils(
				context).queryData();
	}

	/**
	 * 判断是否为好友
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isFriend(String id) {
		boolean a = false;
		if (null == friendDataBeans) {
			return false;
		}
		for (int i = 0; i < friendDataBeans.size(); i++) {
			if (friendDataBeans.get(i).getFriendid().equals(id)) {
				a = true;
				break;
			}
		}
		return a;
	}

	/**
	 * 他的群组 请求和解析
	 * 
	 */
	private void findCreatGroup(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				AppLog.i(TAG, "群组返回0:" + response);
				parsefindCreateGroup(response);
				// 他的群组头像列出来
				initHisGroup();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.findGroup(id, res);
	}

	// 他的群组头像
	private void initHisGroup() {
		// TODO Auto-generated method stub
		he_group_lay = (LinearLayout) findViewById(R.id.he_group_lay);
		if (groupList == null) {
			return;
		}
		AppLog.i(TAG, "群组个数:" + groupList.size());
		if (groupList.size() == 0) {
			he_group_lay.setVisibility(View.GONE);
		}
		his_group_gridview = (GridView) findViewById(R.id.his_group_gridview);
		gridAdapter = new HisCreateGroupGridAdapter(get_list_group_headimage(),
				this);
		his_group_gridview.setAdapter(gridAdapter);
		// 点击进入他的联系人列表
		his_group_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonalPage.this, HisGroup.class);
				intent.putExtra("id", id);
				startActivity(intent);
				// finish();
			}
		});

	}

	// 他的设备图片
	private void initHisDevice() {
		// TODO Auto-generated method stub
		// 设备图片的layout
		she_bei_tu_pian_lay = (LinearLayout) findViewById(R.id.she_bei_tu_pian_lay);

		his_device_gridview = (GridView) findViewById(R.id.his_device_gridview);
		devgridAdapter = new HisDeviceGridAdapter(get_list_device_image(), this);
		his_device_gridview.setAdapter(devgridAdapter);
		if (deviceList.size() == 0 || listItem2.size() == 0) {
			she_bei_tu_pian_lay.setVisibility(View.GONE);
		} else {
			she_bei_tu_pian_lay.setVisibility(View.VISIBLE);
		}
		// 点击进入他的联系人列表
		his_device_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonalPage.this, PictureShow.class);
				intent.putExtra("image", deviceList.get(arg2).getHeadimage());
				startActivity(intent);
			}
		});

	}

	private void parsefindCreateGroup(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("groups");
			groupList = new ArrayList<GroupInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();

			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				GroupInfo group = new GroupInfo();
				obj = json.getJSONObject(i);
				group.setId(obj.getString("id"));
				group.setGname(obj.getString("gname"));
				group.setContent(obj.getString("content"));
				group.setHeadimage(obj.getString("headimage"));
				group.setGaddress(obj.getString("gaddress"));
				groupList.add(group);
			}
		} catch (JSONException e) {
			AppLog.w(TAG, "群组返回:" + e.getMessage());
		}

	}

	/**
	 * 个人信息 请求和解析
	 * 
	 */
	private void UserDetail(String id, final String type) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "个人信息返回:" + response);

				if (TextUtils.isEmpty(type)) {
					parseUserDetail5(response);
					Log.i(TAG, "个人信息返回:" + typess);
					if (typess.equals("司机")) {
						parseUserDetail1(response);
						Log.i(TAG, "个人信息返回:" + typess);
					} else if (typess.equals("机主")) {
						parseUserDetail2(response);
						Log.i(TAG, "个人信息返回:" + typess);
					} else if (typess.equals("商家")) {
						parseUserDetail3(response);
						Log.i(TAG, "个人信息返回:" + typess);
					} else {
						Log.i(TAG, "个人信息返回:" + "其他");
						parseUserDetail4(response);
					}
				} else {
					if (type.equals("司机")) {
						parseUserDetail1(response);
					} else if (type.equals("机主")) {
						parseUserDetail2(response);
					} else if (type.equals("商家")) {
						parseUserDetail3(response);
					} else {
						parseUserDetail4(response);
					}
				}

				// initCall();
				initView();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail2(id, type, res);
	}

	private void UserDetail00(final String id, final String type) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "个人信息返回:" + response);
				parseUserDetail5(response);
				UserDetail(id, typess);
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail2(id, type, res);
	}

	// 机主的解析OK
	public void parseUserDetail2(String response) {
		try {
			JSONObject result = new JSONObject(response);
			Log.i("机主的 response", response + "");
			JSONObject json = result.getJSONObject("equ");
			if (json.has("resultlist")) {
				JSONArray obja = json.getJSONArray("resultlist");
				int length = obja.length();
				for (int i = 0; i < length; i++) {
					JSONArray objo = obja.getJSONArray(i);
					if (objo.length() > 1) {
						for (int j = 0; j < objo.length(); j++) {
							JSONObject obj = objo.getJSONObject(j);
							if (j == 0) {
								ename.add(obj.getString("ename"));
								ebrand.add(obj.getString("ebrand"));
								emodel.add(obj.getString("emodel"));
								eweight.add(obj.getString("eweight"));
								enumber.add(obj.getString("enumber"));
							} else {
								uid = obj.getString("id");
								uname = obj.getString("username");
								utype = obj.getString("type");
								ucity = obj.getString("address");
								udevice = obj.getString("equipment");
								ucontent = obj.getString("sign");
								uheadimg = obj.getString("headimage");
								accept = obj.getString("accept");
								hobby = obj.getString("hobby");// 兴趣爱好
								// Log.i("accept", accept);
								info = obj.getString("info");// 个人简介、自我介绍
								age = obj.getString("age");
								singn = obj.getString("sign");
								usualplace = obj.getString("place");// 常出没地点
							}

						}
					}
				}
			} else {
				uid = json.getString("id");
				uname = json.getString("username");
				utype = json.getString("type");
				ucity = json.getString("address");
				udevice = json.getString("equipment");
				ucontent = json.getString("sign");
				uheadimg = json.getString("headimage");
				accept = json.getString("accept");
				hobby = json.getString("hobby");// 兴趣爱好
				// Log.i("accept", accept);
				info = json.getString("info");// 个人简介、自我介绍
				age = json.getString("age");
				singn = json.getString("sign");
				usualplace = json.getString("place");// 常出没地点
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 商家的解析
	public void parseUserDetail3(String response) {
		try {
			JSONObject result = new JSONObject(response);
			Log.i("user detail response", response + "");
			JSONObject obj = result.getJSONObject("user");
			uid = obj.getString("id");
			uname = obj.getString("username");
			utype = obj.getString("type");
			ucity = obj.getString("address");
			udevice = obj.getString("equipment");
			ucontent = obj.getString("sign");
			uheadimg = obj.getString("headimage");
			accept = obj.getString("accept");
			hobby = obj.getString("hobby");// 兴趣爱好
			companyname = obj.getString("companyname");//
			info = obj.getString("info");// 个人简介，自我介绍
			// Log.i("accept", accept)；
			usualplace = obj.getString("place");// 常出没地点
			business = obj.getString("business");// 商家的业务范围
			businessinfo = obj.getString("businessinfo");// 商家的业务简介
			age = obj.getString("age");
			singn = obj.getString("sign");

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 其他的解析
	public void parseUserDetail4(String response) {
		try {
			JSONObject result = new JSONObject(response);
			Log.i("其它的response", response + "");
			JSONObject obj = result.getJSONObject("userO");
			uid = obj.getString("id");
			uname = obj.getString("username");
			utype = obj.getString("type");
			ucity = obj.getString("address");
			udevice = obj.getString("equipment");
			ucontent = obj.getString("sign");
			uheadimg = obj.getString("headimage");
			business = obj.getString("business");
			accept = obj.getString("accept");
			hobby = obj.getString("hobby");// 兴趣爱好
			// Log.i("accept", accept);
			info = obj.getString("info");
			age = obj.getString("age");
			singn = obj.getString("sign");
			usualplace = obj.getString("place");// 常出没地点

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private String typess = "";

	public void parseUserDetail5(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("userO");
			MyPersonBean myPersonBean = new Gson().fromJson(obj.toString(),
					MyPersonBean.class);
			typess = myPersonBean.getType();
			type = typess;
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 类型是司机时候的解析
	private void parseUserDetail1(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			Log.i("user detail response", "个人解析:" + response);
			if (result.getInt("result") == 1) {
				if (result.getInt("type") == 1) {
					JSONArray arr = result.getJSONArray("driver");
					JSONArray arr1 = arr.getJSONArray(0);
					JSONObject equobj = arr1.getJSONObject(0);
					driveryearO = equobj.getString("driveryear");
					nequ = equobj.getString("nequ");
					oequ = equobj.getString("oequ");
					driver_start = equobj.getString("dbegin");
					JSONObject personal = arr1.getJSONObject(1);
					uid = personal.getString("id");
					uname = personal.getString("username");
					utype = personal.getString("type");
					ucity = personal.getString("address");
					udevice = personal.getString("equipment");
					ucontent = personal.getString("sign");
					uheadimg = personal.getString("headimage");
					accept = personal.getString("accept");
					hobby = personal.getString("hobby");// 兴趣爱好
					// Log.i("accept", accept);
					info = personal.getString("info");// 个人简介、自我介绍
					age = personal.getString("age");
					singn = personal.getString("sign");
					usualplace = personal.getString("place");// 常出没地点
				} else if (result.getInt("type") == 2) {
					JSONObject obj = result.getJSONObject("driver");
					uid = obj.getString("id");
					uname = obj.getString("username");
					utype = obj.getString("type");
					ucity = obj.getString("address");
					udevice = obj.getString("equipment");
					ucontent = obj.getString("sign");
					uheadimg = obj.getString("headimage");
					accept = obj.getString("accept");
					hobby = obj.getString("hobby");// 兴趣爱好
					// Log.i("accept", accept);
					info = obj.getString("info");
					age = obj.getString("age");
					singn = obj.getString("sign");
					usualplace = obj.getString("place");// 常出没地点
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 他的个人信息
	 */
	private void initView() {
		// TODO Auto-generated method stub
		// 头像栏
		headbg = (ImageView) findViewById(R.id.p_home_page_background);
		headimg = (ImageView) findViewById(R.id.p_home_page_headimg);
		name = (TextView) findViewById(R.id.main_name);
		career = (TextView) findViewById(R.id.main_career);
		content = (TextView) findViewById(R.id.main_content);
		content.setVisibility(View.GONE);// 和下面的重复，要求去掉
		// 工号
		project_num = (TextView) findViewById(R.id.project_number_txt);
		// 祖籍
		mycity = (TextView) findViewById(R.id.p_home_city);
		// 年龄
		age_txt = (TextView) findViewById(R.id.p_home_age);
		// 司机时候显示驾龄
		driver_txt = (TextView) findViewById(R.id.p_driver_year);
		self_introduction_lay = (LinearLayout) findViewById(R.id.self_introduction_lay);
		// 自我介绍
		intro = (TextView) findViewById(R.id.p_home_introduce);// 自我介绍的内容
		// 业务简介
		ye_wu_jian_jie_lay = (LinearLayout) findViewById(R.id.ye_wu_jian_jie_lay);
		// 公司，仅商家显示
		company_lay = (LinearLayout) findViewById(R.id.p_company);
		company_txt = (TextView) findViewById(R.id.p_company_name);
		// 业务范围，商家、其他显示
		business_scope_lay = (LinearLayout) findViewById(R.id.business_scope_lay);
		business_scope_txt = (TextView) findViewById(R.id.business_scope);
		equipment_details = (LinearLayout) findViewById(R.id.equipment_details);
		brief_introduction_of_business = (TextView) findViewById(R.id.brief_introduction_of_business);
		// 设备详情
		equipment_detail_txt = (TextView) findViewById(R.id.equipment_lay);
		// 兴趣爱好的lay
		he_hobby_lay = (LinearLayout) findViewById(R.id.he_hobby_lay);
		my_hobby = (TextView) findViewById(R.id.my_hobby);
		// my_hobby.setText(hobby);
		// 基本信息读取
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL + uheadimg, headimg,
				options, animateFirstListener);
		name.setText(uname);
		career.setText(utype);
		content.setText(ucontent);
		project_num.setText("" + uid);

		mycity.setText(ucity);
		age_txt.setText(age + "岁");
		intro.setText(info);
		if (!TextUtils.isEmpty(singn)) {
			qian_ming_lay.setVisibility(View.VISIBLE);
			persingnTextView.setText("" + singn);
		} else {
			qian_ming_lay.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(usualplace)) {
			recentplaceTextView.setText("" + usualplace);
			chu_mo_di_dian_lay.setVisibility(View.VISIBLE);
		} else {
			chu_mo_di_dian_lay.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(hobby)) {
			he_hobby_lay.setVisibility(View.VISIBLE);
			my_hobby.setText("" + hobby);

		} else {
			he_hobby_lay.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(info)) {
			intro.setText("" + info);
			self_introduction_lay.setVisibility(View.VISIBLE);
		} else {
			self_introduction_lay.setVisibility(View.GONE);
		}

		// type是司机的时候显示驾龄
		driver_layout = (LinearLayout) findViewById(R.id.p_driver_age);
		if (type.equals("司机")) {
			company_lay.setVisibility(View.GONE);
			ye_wu_jian_jie_lay.setVisibility(View.GONE);
			business_scope_lay.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(driveryearO)) {
				// int jialing = 2014 -
				// Integer.parseInt(driveryearO);//2014减去驾龄起点，就是驾龄几年
				driver_layout.setVisibility(View.VISIBLE);
				countYear();
				driver_txt.setText(driveryearO);
			} else {
				driver_layout.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(udevice) || !TextUtils.isEmpty(nequ)) {
				equipment_details.setVisibility(View.VISIBLE);
				equipment_detail_txt.setText(udevice + "      " + oequ + " "
						+ nequ);
			} else {
				equipment_details.setVisibility(View.GONE);
			}
		} else if (type.equals("机主")) {
			driver_layout.setVisibility(View.GONE);
			company_lay.setVisibility(View.GONE);
			ye_wu_jian_jie_lay.setVisibility(View.GONE);
			business_scope_lay.setVisibility(View.GONE);
			for (int i = 0; i < ename.size(); i++) {
				if (!enumber.get(i).equals("0")) {
					if (eweight.get(i).equals("履带")) {
						ch = ename.get(i) + "    " + ebrand.get(i) + "    "
								+ emodel.get(i) + "    " + enumber.get(i) + "台"
								+ "\n";
						strb.append(ch);
					} else {
						ch = ename.get(i) + "    " + ebrand.get(i) + "    "
								+ emodel.get(i) + "    " + eweight.get(i)
								+ "    " + enumber.get(i) + "台" + "\n";
						strb.append(ch);
					}
				}
			}
			if (!TextUtils.isEmpty(strb)) {
				equipment_detail_txt.setText(strb);
				equipment_details.setVisibility(View.VISIBLE);
			} else {
				equipment_details.setVisibility(View.GONE);
			}

		} else if (type.equals("商家")) {
			equipment_details.setVisibility(View.GONE);
			driver_layout.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(companyname)) {
				company_txt.setText(companyname);
				company_lay.setVisibility(View.VISIBLE);
			} else {
				company_lay.setVisibility(View.GONE);
			}

			business_scope_lay.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(business) && !TextUtils.isEmpty(udevice)) {
				business_scope_txt.setText(udevice + "\n " + business);
				business_scope_lay.setVisibility(View.VISIBLE);
			} else {
				business_scope_lay.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(businessinfo)) {
				brief_introduction_of_business.setText(businessinfo);
				ye_wu_jian_jie_lay.setVisibility(View.VISIBLE);
			} else {
				ye_wu_jian_jie_lay.setVisibility(View.GONE);
			}
		} else {
			if (!TextUtils.isEmpty(business)) {
				business_scope_txt.setText(business);
				business_scope_lay.setVisibility(View.VISIBLE);
			} else {
				business_scope_lay.setVisibility(View.GONE);
			}
			ye_wu_jian_jie_lay.setVisibility(View.GONE);
			company_lay.setVisibility(View.GONE);
			driver_layout.setVisibility(View.GONE);
			equipment_details.setVisibility(View.GONE);
		}

		headimg.setOnClickListener(listener);

	}

	private ArrayList<HashMap<String, Object>> get_list_group_headimage() {
		// TODO Auto-generated method stub
		listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		AppLog.i(TAG, "群组返回:groupList" + groupList);
		if (groupList != null) {
			for (int i = 0; i < groupList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("friendID", friendList.get(i).getId());
				map.put("id", groupList.get(i).getId());
				map.put("gname", groupList.get(i).getGname());
				map.put("content", groupList.get(i).getContent());
				map.put("headimage", groupList.get(i).getHeadimage());
				map.put("gaddress", groupList.get(i).getGaddress());
				listItem.add(map);
			}
		}
		return listItem;
	}

	private ArrayList<HashMap<String, Object>> get_list_device_image() {
		// TODO Auto-generated method stub
		listItem2 = new ArrayList<HashMap<String, Object>>();
		Log.i("返回:deviceList", "返回:deviceList" + deviceList);

		if (deviceList != null) {
			for (int i = 0; i < deviceList.size(); i++) {
				String imgurl = deviceList.get(i).getHeadimage();
				if (!TextUtils.isEmpty(imgurl)) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("headimage", deviceList.get(i).getHeadimage());
					listItem2.add(map);
				}
			}
		}

		// TODO Auto-generated method stub
		// if (deviceList != null) {
		// if (deviceList.size() <= 3) {
		// for (int i = 0; i < deviceList.size(); i++) {
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// // map.put("friendID", friendList.get(i).getId());
		// if (!TextUtils.isEmpty(deviceList.get(i).getHeadimage())
		// && !(deviceList.get(i).getHeadimage().equals(""))
		// && !(deviceList.get(i).getHeadimage()
		// .equals("null"))) {
		// map.put("headimage", deviceList.get(i).getHeadimage());
		// listItem2.add(map);
		// }
		// }
		// } else if (deviceList.size() > 3) {
		// for (int i = 0; i < 3; i++) {
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// // map.put("friendID", friendList.get(i).getId());
		// if (!TextUtils.isEmpty(deviceList.get(i).getHeadimage())
		// && !(deviceList.get(i).getHeadimage().equals(""))
		// && !(deviceList.get(i).getHeadimage()
		// .equals("null"))) {
		// map.put("headimage", deviceList.get(i).getHeadimage());
		// listItem2.add(map);
		// }
		// }
		// }
		// }
		Log.i("deviceGridAdapter的返回listItem", "图片大小" + listItem2.size() + "");
		return listItem2;
	}

	// 获得个人状态的头像
	// private ArrayList<HashMap<String, Object>> get_list_mood_image() {
	// // TODO Auto-generated method stub
	// Log.i("返回:mymoodList", "返回:mymoodList" + mymoodList);
	// // TODO Auto-generated method stub
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// if (mymoodList != null ){
	// for (int i = 0; i < usermoodsList.size(); i++) {
	// // map.put("friendID", friendList.get(i).getId());
	// if(!TextUtils.isEmpty(usermoodsList.get(i).getMheadimage()) &&
	// (mymoodList.get(i).getMheadimage())!=null){
	// map.put("uheadimage", usermoodsList.get(i).getUheadimage());
	// map.put("headimage", usermoodsList.get(i).getMheadimage());
	// mood_listItem.add(map);
	// }
	// }
	// if(mood_listItem.size() == 0){
	// // map.put("headimage", mymoodList.get(0).getUheadimage());
	// }else if(mood_listItem.size() > 3){
	// mood_listItem.subList(0, 3);
	// }
	// }
	// Log.i("mood_listItem", mood_listItem+"");
	// return mood_listItem;
	// }
	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ye_wu_jian_jie_lay:
				// Intent intent1 = new Intent(PersonalPage.this,
				// CompleteMaster.class);
				// startActivity(intent1);
				break;
			case R.id.he_group_lay:
				Intent intent2 = new Intent(PersonalPage.this,
						GroupDetail.class);
				startActivity(intent2);
				// finish();
				break;
			// case R.id.common_friend_lay:
			// Intent intent3 = new Intent(PersonalPage.this,
			// MaybeFriend.class);
			// startActivity(intent3);
			// finish();
			// break;

			case R.id.p_home_page_headimg:
				showHead();
				break;
			default:
				break;
			}
		}
	};

	private LinearLayout he_hobby_lay;

	/**
	 * 拨打电话功能实现
	 */
	private void initCall() {
		// TODO Auto-generated method stub
		call_phone = (RelativeLayout) findViewById(R.id.call_phone_lay);
		call_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 其它Button初始化 功能实现
	 */
	private void initBtn() {
		// TODO Auto-generated method stub

		addfriend = (Button) findViewById(R.id.addfriden_btn);

		relationship_lay = (LinearLayout) findViewById(R.id.relationship_lay);
		// 关系
		relation = (TextView) findViewById(R.id.p_home_relation);//

		qian_ming_lay = (LinearLayout) findViewById(R.id.qian_ming_lay);// 个性签名的布局
		persingnTextView = (TextView) findViewById(R.id.per_sign);
		chu_mo_di_dian_lay = (LinearLayout) findViewById(R.id.chu_mo_di_dian_lay);
		recentplaceTextView = (TextView) findViewById(R.id.per_rplace);
		distance_txt = (TextView) findViewById(R.id.distance);
		time_txt = (TextView) findViewById(R.id.time);
		// 距离和时间计算
		// 计算我和好友之间的距离
		distance = DistentsUtil.GetDistance(lat, lon, mylat, mylon);
		distance_txt.setText(distance + "km");
		// 获取当前时间
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日   HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String now = formatter.format(curDate);
		if (time != null && !time.equals("null")) {
			try { // 时间转换成格林尼治时间
				lastlogintime_new = time.substring(0, 4) + "年"
						+ time.substring(5, 7) + "月" + time.substring(8, 10)
						+ "日   " + time.substring(11, time.length());
				Log.i("lastlogintime_new", lastlogintime_new);
				java.util.Date d1 = (formatter).parse(now);
				java.util.Date d2 = (formatter).parse(lastlogintime_new);
				long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
				long days = diff / (1000 * 60 * 60 * 24);
				long hours = (diff - days * (1000 * 60 * 60 * 24))
						/ (1000 * 60 * 60);
				long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
						* (1000 * 60 * 60))
						/ (1000 * 60);
				System.out.println("" + days + "天" + hours + "小时" + minutes
						+ "分");
				if (days >= 1) {
					timecompute = "1天前";
				} else if (hours >= 1) {
					timecompute = hours + "小时前";
				} else {
					timecompute = minutes + "分钟前";
				}

			} catch (Exception e) {
			}
			time_txt.setText(timecompute + "");
		} else {
			// 距离和上次登录的时间，显示到控件上
			time_txt.setText("无");
		}
		back = (Button) findViewById(R.id.p_home_page_back);
		send = (Button) findViewById(R.id.send_message);
		back.setOnClickListener(btnlistener);
		send.setOnClickListener(btnlistener);
		addfriend.setOnClickListener(btnlistener);
	}

	private View.OnClickListener btnlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.p_home_page_back:
				// Intent intent1 = new Intent(PersonalPage.this,
				// MainActivity.class);
				// startActivity(intent1);
				finish();
				break;
			case R.id.send_message:

				if (TextUtils.isEmpty(uid)) {
					return;
				}

				// if (!MsgUtils.isExitMsgList(uid, PersonalPage.this)) {
				// MsgUtils.saveMsgList(uname, " ", "" + Chat.getDate(), uid,
				// uheadimg, 0, 0, PersonalPage.this);
				// }
				// Intent intent2 = new Intent(PersonalPage.this, Chat.class);
				// intent2.putExtra("id", uid);
				// intent2.putExtra("username", "" + uname);
				// intent2.putExtra("headimg", "" + uheadimg);
				// startActivity(intent2);
				RongIM.getInstance().startPrivateChat(PersonalPage.this, uid,
						uname);
				// finish();
				break;

			case R.id.addfriden_btn:
				if (addfriend.getText().toString().equals("加为好友")) {
					Intent intent3 = new Intent(PersonalPage.this,
							ApplyFriend.class);
					intent3.putExtra("id", uid);
					startActivity(intent3);
				} else {
					deleteDialog();
				}
				break;

			default:
				break;
			}
		}
	};

	private void findfriend(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", "好友:" + response);
				parsefindfriend(response);
				initHisFriend();
				// 得到共同好友
				commonFriend();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.findfriend(id, res);
	}

	// 删除好友
	protected void deleteDialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(PersonalPage.this).setTitle("确认要删除此好友吗？")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// 按确定后删除
						denyfriend(ccid);
					}

				}).setNegativeButton("取消", null).show();

	}

	private void denyfriend(final String ccid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", response);
				// parseFriendRequestList(response);
				// initList();
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						Toast.makeText(PersonalPage.this, "删除成功！",
								Toast.LENGTH_SHORT).show();
						// //删除成功后就把相应的listItem删掉
						//
						Intent intent2 = new Intent();
						intent2.setAction("shan.chu.hao.you");
						PersonalPage.this.sendBroadcast(intent2);
						new FriendDatabaseUtils(PersonalPage.this).delete(id);
						finish();
					} else {
						Toast.makeText(PersonalPage.this, "删除失败！",
								Toast.LENGTH_SHORT).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		MyHttpClient client = new MyHttpClient();
		client.denyFriends(ccid, res);
	}

	protected void initHisFriend() {
		// TODO Auto-generated method stub
		he_friend_lay = (LinearLayout) findViewById(R.id.he_friend_lay);
		if (friendList == null) {
			return;
		}
		AppLog.i(TAG, "好友数量:" + friendList.size());
		if (friendList.size() == 0) {
			he_friend_lay.setVisibility(View.GONE);
		}
		his_friend_gridview = (GridView) findViewById(R.id.his_friend_gridview);
		friendgridAdapter = new HisCreateFriendGridAdapter(
				get_list_friend_headimage(), this);
		his_friend_gridview.setAdapter(friendgridAdapter);
		// 点击进入他的联系人列表
		his_friend_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (isFriend(id)) {
					Intent intent = new Intent(PersonalPage.this,
							HisContact.class);
					intent.putExtra("id", id);
					intent.putExtra("biaoji", "0");// 他的联系人标记
					startActivity(intent);
				}

				// finish();
			}
		});

	}

	private ArrayList<HashMap<String, Object>> get_list_friend_headimage() {
		// TODO Auto-generated method stub
		listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		Log.i("返回:friendList", "返回:friendList" + friendList.size());
		if (friendList != null) {
			for (int i = 0; i < friendList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("friendID", friendList.get(i).getId());
				map.put("id", friendList.get(i).getId());
				map.put("username", friendList.get(i).getUsername());
				map.put("headimage", friendList.get(i).getHeadimage());
				listItem.add(map);
			}
		}
		Log.i("friendList的返回listItem", listItem + "");
		return listItem;
	}

	private void parsefindfriend(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("friends");
			friendList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			Log.i(TAG, "JSONArray:" + json);
			for (int i = 0; i < length; i++) {
				UserInfo user = new UserInfo();
				JSONObject objo = json.getJSONObject(i);
				Log.i(TAG, "objo:" + objo);
				user.setId(objo.getString("id"));
				user.setTel(objo.getString("tel"));
				user.setUsername(objo.getString("username"));
				user.setType(objo.getString("type"));
				user.setAddress(objo.getString("address"));
				user.setCcid(objo.getString("ccid"));
				// user.setEquipment(obj.getString("equipment"));
				user.setSign(objo.getString("sign"));
				user.setHeadimage(objo.getString("headimage"));
				user.setAccept(objo.getString("accept"));
				user.setLat(Double.valueOf(objo.getString("commercialLat")));
				user.setLon(Double.valueOf(objo.getString("commercialLon")));
				// user.setLastlogintime(objo.getString("lastlogintime"));
				// if (isexit(user.getId())) {
				friendList.add(user);
				// }

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private boolean isexit(String id) {
		if (TextUtils.isEmpty(id)) {
			return false;
		}
		if (null != FriendPage.friendDataBeans) {
			int length = FriendPage.friendDataBeans.size();
			for (int i = 0; i < length; i++) {
				if (id.equals(FriendPage.friendDataBeans.get(i).getFriendid()
						.toString())) {
					return true;
				}
			}
		}

		return false;

	}

	private void findEquienment(String id) {
		MyHttpClient.findUserEqu4User(id, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				Log.i(TAG, "设备简介:" + content);
				// ParseEq(content);
			}

			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

		});
	}

	private List<EquInfo> equInfos = new ArrayList<EquInfo>();

	private HisDeviceGridAdapter phogridAdapter;

	private RelativeLayout ge_ren_dong_tai;

	private String pingluncontent;

	private void ParseEq(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);

			String result = jsonObject.getString("result");
			Log.i(TAG, "设备列表:" + result);
			// if (result!="1") {
			// return ;
			// }
			JSONObject jsonObject2 = jsonObject.getJSONObject("equ");
			Log.i(TAG, "设备列表数据:" + jsonObject2);
			JSONArray resultlist = jsonObject2.getJSONArray("resultlist");
			Log.i(TAG, "设备列表:" + resultlist);
			for (int i = 0; i < resultlist.length(); i++) {

				EquInfo equInfo = null;
				try {
					Gson gson = new Gson();
					equInfo = gson.fromJson(resultlist.get(i).toString(),
							EquInfo.class);
					equInfos.add(equInfo);
					Log.i(TAG, "设备简介:" + equInfo);
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "设备解析错误");
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < equInfos.size(); i++) {
			if (i != 0) {
				udevice = "\n" + udevice + equInfos.get(i).getEname() + ":"
						+ "   品牌:" + equInfos.get(i).getEbrand() + "  " + "数量:"
						+ equInfos.get(i).getEnumber();
			} else {
				udevice = equInfos.get(i).getEname() + ":" + "   品牌:"
						+ equInfos.get(i).getEbrand() + "   数量:"
						+ equInfos.get(i).getEnumber();
			}

			device_picList.add("" + equInfos.get(i).getHeadimage());

		}
		gallaryAdapter.notifyDataSetChanged();
		equipment_detail_txt.setText("" + udevice);

	}

	private void dectRelationship() {
		if (TextUtils.isEmpty(id)) {
			return;
		}

		if (!id.equals(LoginActivity.id)) {
			if (isexit(id)) {
				relation.setText(R.string.isfriend);
				addfriend.setVisibility(View.GONE);
			} else {
				relation.setText(R.string.nofriend);
				addfriend.setVisibility(View.VISIBLE);
			}
		} else {
			relationship_lay.setVisibility(View.GONE);
		}
	}

	protected void initHisPhoto() {
		// TODO Auto-generated method stub
		// his_photo_gridview = (GridView)
		// findViewById(R.id.his_photo_gridview);
		// mood_listItem.clear();
		// mood_listItem =get_list_mood_image();
		// phogridAdapter = new HisDeviceGridAdapter(mood_listItem,
		// this);
		// his_photo_gridview.setAdapter(phogridAdapter);
		ge_ren_dong_tai = (RelativeLayout) findViewById(R.id.ge_ren_dong_tai);
		// 点击进入他的联系人列表
		ge_ren_dong_tai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent4 = new Intent(PersonalPage.this,
						projectCircle.class);
				intent4.putExtra("id", id);
				intent4.putExtra("username", "" + uname);
				startActivity(intent4);
			}
		});
	}

	private void countYear() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String times[] = driver_start.split("-");
		if (times != null && times.length == 2) {
			try {
				int montha = Integer.valueOf(times[1]);
				int yeara = Integer.valueOf(times[0]);
				int monthdis = 0;
				int yeardis = 0;
				if (month >= montha) {
					monthdis = month - montha;
					yeardis = year - yeara;
				} else {
					year = year - 1;
					yeardis = year - yeara;
					monthdis = month + 12 - montha;
				}
				if (yeardis > 0) {
					driveryearO = yeardis + "年" + monthdis + "个月";
				} else {
					driveryearO = monthdis + "个月";
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}

	private void showHead() {
		List<String> list0 = new ArrayList<>();
		list0.add("" + uheadimg);
		Intent intent = new Intent();
		intent.putStringArrayListExtra("imgurls", (ArrayList<String>) list0);
		intent.putExtra("postion", 1);
		intent.setClass(PersonalPage.this, PhotoPagerActivity.class);
		startActivity(intent);
	}

}
