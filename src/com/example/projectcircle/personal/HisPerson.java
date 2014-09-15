package com.example.projectcircle.personal;

import java.util.ArrayList;
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

import com.example.projectcircle.R;
import com.example.projectcircle.adpter.GroupGallaryAdapter;
import com.example.projectcircle.adpter.HisCreateFriendGridAdapter;
import com.example.projectcircle.adpter.HisCreateGroupGridAdapter;
import com.example.projectcircle.bean.EquInfo;
import com.example.projectcircle.bean.FriendDataBean;
import com.example.projectcircle.bean.GroupInfo;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.db.utils.FriendDatabaseUtils;
import com.example.projectcircle.friend.FriendPage;
import com.example.projectcircle.friend.MaybeFriend;
import com.example.projectcircle.group.GroupDetail;
import com.example.projectcircle.other.Chat;
import com.example.projectcircle.util.MsgUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class HisPerson extends Activity {

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
	TextView device;

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
	ImageView[] c_friendsImageViews = new ImageView[5];

	String id;
	String uname, utype, ucity, udevice, ucontent, uheadimg, accept, info, uid,
			age, group, gname, singn, rplace,udriverYear;

	// 图片缓存
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	Context context;

	// 好友动态
	String status;
	private ArrayList<GroupInfo> groupList;
	private String gcontent;
	private String gheadImage;
	private HisCreateGroupGridAdapter myAdapter;
	private GridView his_group_gridview;
	private ArrayList<HashMap<String, Object>> listItem;
	private HisCreateGroupGridAdapter gridAdapter;
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

	ArrayList<String> ename = new ArrayList<String>();;//设备名称

	ArrayList<String> emodel = new ArrayList<String>();;//设备型号

	ArrayList<String> enumber = new ArrayList<String>();;//设备数量

	private String driveryear;

	private String brand_model;

	private LinearLayout equipment_details;
	StringBuffer strb=new StringBuffer();

	private String ch;

	private String driveryearO;

	private String nequ;

	private TextView my_hobby;

	private LinearLayout ye_wu_jian_jie_lay;

	private TextView equipment_detail_txt;

	private String ccid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_home_page);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		type = intent.getStringExtra("type");
		ccid = intent.getStringExtra("ccid");
		initBtn();
		dectRelationship();
		UserDetail(id,type);
        //获得好友信息
	    getFriInfo(HisPerson.this);
	    //判断是否为好友
	    if(isFriend(id)){
	    	addfriend.setText("取消好友");	    	
	    }else{
	    	addfriend.setText("加为好友");	
	    };
		findCreatGroup(id);
		findEquienment(id);
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.build();
	}
	@SuppressWarnings("unchecked")
	public static void getFriInfo(Context context){
		friendDataBeans=(List<FriendDataBean>) new FriendDatabaseUtils(context).queryData();
	}
	
	/**
	 * 判断是否为好友
	 * @param id
	 * @return
	 */
	private static boolean isFriend(String id){
		boolean a=false;
		if (null==friendDataBeans) {
			return a;
		}
		for (int i = 0; i < friendDataBeans.size(); i++) {
			if (friendDataBeans.get(i).getFriendid().equals(id)) {
				a=true;				
				break;
			}
		}		
		return a;
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
				Log.i(TAG, "返回:" + response);
				parsefindCreateGroup(response);
				// 他的群组头像列出来
				initHisGroup();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.findCreatGroup(id, res);
	}

	// 他的群组头像
	private void initHisGroup() {
		// TODO Auto-generated method stub
		his_group_gridview = (GridView) findViewById(R.id.his_group_gridview);
		gridAdapter = new HisCreateGroupGridAdapter(get_list_group_headimage(),
				this);
		his_group_gridview.setAdapter(gridAdapter);
		//点击进入他的联系人列表
		his_group_gridview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HisPerson.this,HisContact.class);
				intent.putExtra("id", id);			
				startActivity(intent);
				finish();
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
			e.printStackTrace();
		}

	}

	/**
	 * 个人信息 请求和解析
	 * 
	 */
	private void UserDetail(String id,final String type) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "个人信息返回:" + response);
				if(type.equals("司机")){
				parseUserDetail1(response);
				}else if(type.equals("机主")){
				parseUserDetail2(response);
				}else if(type.equals("商家")){
				parseUserDetail3(response);
				}
				else{
					parseUserDetail4(response);
				}
				initCall();
				initView();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail2(id,type, res);
	}
	//机主的解析OK
	public void parseUserDetail2(String response) {
		try {
			JSONObject result = new JSONObject(response);
			Log.i("机主的 response",response+"");
			JSONObject json = result.getJSONObject("equ");
			if(json.has("resultlist")){
			JSONArray obja = json.getJSONArray("resultlist");
			int length = obja.length();
			for(int i = 0;i< length;i++){		
			JSONArray objo = obja.getJSONArray(i);
			if(objo.length()>1){
			for(int j= 0;j< objo.length();j++){
				JSONObject obj = objo.getJSONObject(j);
				if(j == 0){
				ename.add(obj.getString("ename"));
				emodel.add(obj.getString("emodel"));
				enumber.add(obj.getString("enumber"));}
				else{
					uid = obj.getString("id");
					uname = obj.getString("username");
					utype = obj.getString("type");
					ucity = obj.getString("address");
					udevice = obj.getString("equipment");
					ucontent = obj.getString("sign");
					uheadimg = obj.getString("headimage");
					accept = obj.getString("accept");
					hobby = obj.getString("hobby");//兴趣爱好
					// Log.i("accept", accept);
					info = obj.getString("info");
					age = obj.getString("age");
					singn = obj.getString("sign");	
			    }
		
			}
			}
			}
			}
			else{
				uid = json.getString("id");
				uname = json.getString("username");
				utype = json.getString("type");
				ucity = json.getString("address");
				udevice = json.getString("equipment");
				ucontent = json.getString("sign");
				uheadimg = json.getString("headimage");
				accept = json.getString("accept");
				hobby = json.getString("hobby");//兴趣爱好
				// Log.i("accept", accept);
				info = json.getString("info");
				age = json.getString("age");
				singn = json.getString("sign");	
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	//商家的解析
	public void parseUserDetail3(String response) {
		try {
			JSONObject result = new JSONObject(response);
			Log.i("user detail response",response+"");
			JSONObject obj = result.getJSONObject("user");
			uid = obj.getString("id");
			uname = obj.getString("username");
			utype = obj.getString("type");
			ucity = obj.getString("address");
			udevice = obj.getString("equipment");
			ucontent = obj.getString("sign");
			uheadimg = obj.getString("headimage");
			accept = obj.getString("accept");
			hobby = obj.getString("hobby");//兴趣爱好
			companyname = obj.getString("companyname");//
			// Log.i("accept", accept)；
			business=obj.getString("business");
			age = obj.getString("age");
			singn = obj.getString("sign");

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	//其他的解析
	public void parseUserDetail4(String response) {
		try {
			JSONObject result = new JSONObject(response);
			Log.i("user detail response",response+"");
			JSONObject obj = result.getJSONObject("user");
			uid = obj.getString("id");
			uname = obj.getString("username");
			utype = obj.getString("type");
			ucity = obj.getString("address");
			udevice = obj.getString("equipment");
			ucontent = obj.getString("sign");
			uheadimg = obj.getString("headimage");
			accept = obj.getString("accept");
			hobby = obj.getString("hobby");//兴趣爱好
			// Log.i("accept", accept);
			info = obj.getString("info");
			age = obj.getString("age");
			singn = obj.getString("sign");

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
     //类型是司机时候的解析
	private void parseUserDetail1(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			Log.i("user detail response",response+"");		
      if(result.getInt("result") == 1){
    	  if(result.getInt("type") ==1){
    		  JSONArray arr = result.getJSONArray("driver");
    		  JSONArray  arr1 = arr.getJSONArray(0);
    		  JSONObject equobj = arr1.getJSONObject(0);
    		  driveryearO = equobj.getString("driveryear");
    		  nequ = equobj.getString("nequ");
    		  JSONObject personal = arr1.getJSONObject(1);
    			uid = personal.getString("id");
    			uname = personal.getString("username");
    			utype = personal.getString("type");
    			ucity = personal.getString("address");
    			udevice = personal.getString("equipment");
    			ucontent = personal.getString("sign");
    			uheadimg = personal.getString("headimage");
    			accept = personal.getString("accept");
    			hobby = personal.getString("hobby");//兴趣爱好
    			// Log.i("accept", accept);
    			info = personal.getString("info");
    			age = personal.getString("age");
    			singn = personal.getString("sign");
    	  }else if(result.getInt("type") ==2){
    		  JSONObject obj =result.getJSONObject("driver");
    			uid = obj.getString("id");
    			uname = obj.getString("username");
    			utype = obj.getString("type");
    			ucity = obj.getString("address");
    			udevice = obj.getString("equipment");
    			ucontent = obj.getString("sign");
    			uheadimg = obj.getString("headimage");
    			accept = obj.getString("accept");
    			hobby = obj.getString("hobby");//兴趣爱好
    			// Log.i("accept", accept);
    			info = obj.getString("info");
    			age = obj.getString("age");
    			singn = obj.getString("sign");
    	  }
      }
		
		
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 设备详细，他的群组，共同好友 功能实现
	 */
	private void initView() {
		// TODO Auto-generated method stub
		// 头像栏
		headbg = (ImageView) findViewById(R.id.p_home_page_background);
		headimg = (ImageView) findViewById(R.id.p_home_page_headimg);
		name = (TextView) findViewById(R.id.main_name);
		career = (TextView) findViewById(R.id.main_career);
		content = (TextView) findViewById(R.id.main_content);
		// 工号
		project_num = (TextView) findViewById(R.id.project_number);
		// 祖籍
		mycity = (TextView) findViewById(R.id.p_home_city);
		// 年龄
		age_txt = (TextView) findViewById(R.id.p_home_age);		
		//司机时候显示驾龄
		driver_txt = (TextView) findViewById(R.id.p_driver_year);	
		// 自我介绍
		intro = (TextView) findViewById(R.id.p_home_introduce);
		// 业务简介
		ye_wu_jian_jie_lay = (LinearLayout) findViewById(R.id.ye_wu_jian_jie_lay);
        //公司，仅商家显示		
		company_lay = (LinearLayout) findViewById(R.id.p_company);
		company_txt = (TextView)findViewById(R.id.p_company_name);
		//业务范围，商家、其他显示
	    business_scope_lay = (LinearLayout) findViewById(R.id.business_scope_lay);//业务范围
		equipment_details = (LinearLayout) findViewById(R.id.equipment_details);
		//设备详情
		equipment_detail_txt= (TextView)findViewById(R.id.equipment_lay);
		my_hobby = (TextView)findViewById(R.id.my_hobby);
	    my_hobby.setText(hobby);
		// 基本信息读取
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL + uheadimg, headbg,
				options, animateFirstListener);
		imageLoader.displayImage(MyHttpClient.IMAGE_URL + uheadimg, headimg,
				options, animateFirstListener);
		name.setText(uname);
		career.setText(utype);
		content.setText(ucontent);
		project_num.setText("工程号:   " + uid);

		mycity.setText(ucity);
		age_txt.setText(age + "岁");
		intro.setText(info);

		persingnTextView.setText("" + singn);
		recentplaceTextView.setText("" + rplace);
		//type是司机的时候显示驾龄
          driver_layout = (LinearLayout)findViewById(R.id.p_driver_age);
          if(type.equals("司机")){        	
        	  company_lay.setVisibility(View.GONE);
//        	  device_detail_lay.setVisibility(View.GONE);
        	  business_scope_lay.setVisibility(View.GONE);
        	 
        	  if(!TextUtils.isEmpty(udevice) && !TextUtils.isEmpty(brand_model)){
        	  equipment_details.setVisibility(View.VISIBLE);
        	  equipment_detail_txt.setText(udevice+"      "+brand_model);        	 
        	  }else{
        		  equipment_details.setVisibility(View.GONE);  
        	  }
        	  if(!driveryearO.equals("")){
        	  driver_layout.setVisibility(View.VISIBLE);
        	  driver_txt.setText(driveryearO);
        	  }else{
        	  driver_layout.setVisibility(View.GONE);
        	  }
          }
          else if(type.equals("机主")){
        	  driver_layout.setVisibility(View.GONE);
        	  company_lay.setVisibility(View.GONE);
//        	  device_detail_lay.setVisibility(View.GONE);
        	  business_scope_lay.setVisibility(View.GONE);
        
        	  for(int i = 0;i<ename.size();i++){
        		ch = ename.get(i)+"    "+ emodel.get(i)+"    "+enumber.get(i)+"台"+"\n";
        		strb.append(ch);
        	  }
        	  if(!TextUtils.isEmpty(strb)){
        	  equipment_detail_txt.setText(strb);
        	  equipment_details.setVisibility(View.VISIBLE);
        	  }else{
        		  equipment_details.setVisibility(View.GONE);
        	  }
        	 
         }  
          else if(type.equals("商家")){
      	  driver_layout.setVisibility(View.GONE);      	
      	  if(!TextUtils.isEmpty(companyname)){
      	  company_txt.setText(companyname);
      	  company_lay.setVisibility(View.VISIBLE);
      	  }else{
      	  company_lay.setVisibility(View.GONE);
      	  }
//          device_detail_lay.setVisibility(View.VISIBLE);
          business_scope_lay.setVisibility(View.VISIBLE);//业务范围
          equipment_details.setVisibility(View.VISIBLE);
          if(!TextUtils.isEmpty(business)){
          device.setText(business);//业务简介
          ye_wu_jian_jie_lay.setVisibility(View.VISIBLE);
          }else{
          ye_wu_jian_jie_lay.setVisibility(View.GONE);  
          }
         }
		// 共同好友
		common_friend = (LinearLayout) findViewById(R.id.common_friend_lay);

//		device_detail_lay.setOnClickListener(listener);
		// group_lay.setOnClickListener(listener);
		common_friend.setOnClickListener(listener);

	}

	private ArrayList<HashMap<String, Object>> get_list_group_headimage() {
		// TODO Auto-generated method stub
		listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		Log.i(TAG, "返回:friendList" + groupList);
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
		Log.i("groupGridAdapter的返回listItem", listItem + "");
		return listItem;
	}

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
				Intent intent2 = new Intent(HisPerson.this,
						GroupDetail.class);
				startActivity(intent2);
				// finish();
				break;
			case R.id.common_friend_lay:
				Intent intent3 = new Intent(HisPerson.this,
						MaybeFriend.class);
				startActivity(intent3);
				// finish();
				break;
			default:
				break;
			}
		}
	};

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
		//他的联系人那一行不让他显示
		LinearLayout friend_lay = (LinearLayout)findViewById(R.id.he_friend_lay);
		friend_lay.setVisibility(View.GONE);
		
		addfriend=(Button)findViewById(R.id.addfriden_btn);
		
//		mGallery = (Gallery) findViewById(R.id.device_pic);
//		gallaryAdapter = new GroupGallaryAdapter(this, device_picList);
//		mGallery.setAdapter(gallaryAdapter);

		// 关系
		relation = (TextView) findViewById(R.id.p_home_relation);//
	
      
		  //业务简介
		ye_wu_jian_jie_lay = (LinearLayout)findViewById(R.id.ye_wu_jian_jie_lay);
		device = (TextView) findViewById(R.id.brief_introduction_of_business);
		
		persingnTextView = (TextView) findViewById(R.id.per_sign);
		recentplaceTextView = (TextView) findViewById(R.id.per_rplace);
		back = (Button) findViewById(R.id.p_home_page_back);	
		back.setOnClickListener(btnlistener);	
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
			case R.id.sigin_submit:

				if (TextUtils.isEmpty(uid)) {
					return;
				}

				if (!MsgUtils.isExitMsgList(uid, HisPerson.this)) {
					MsgUtils.saveMsgList(uname, " ", "" + Chat.getDate(), uid,
							uheadimg, 0,0, HisPerson.this);
				}
				Intent intent2 = new Intent(HisPerson.this, Chat.class);
				intent2.putExtra("id", uid);
				intent2.putExtra("username", "" + uname);
				intent2.putExtra("headimg", "" + uheadimg);
				startActivity(intent2);
				// finish();
				break;
				
				
			case R.id.addfriden_btn:
				if(addfriend.getText().toString().equals("加为好友")){
					Intent intent3 = new Intent(HisPerson.this,
							ApplyFriend.class);
					intent3.putExtra("id", uid);
					startActivity(intent3);
					}
					else{
						deleteDialog();
					}
				break;
				
			default:
				break;
			}
		}
	};

	  //删除好友
		protected void deleteDialog() {
			// TODO Auto-generated method stub
			new AlertDialog.Builder(HisPerson.this)
			.setTitle("确认要删除此好友吗？")
			.setIcon(android.R.drawable.ic_dialog_info)
			.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							// 按确定后删除
							denyfriend(ccid);
						}

					
					}).setNegativeButton("取消", null).show();
	           
		}
		private void denyfriend(String ccid) {
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
							Toast.makeText(HisPerson.this, "删除成功！",
									Toast.LENGTH_SHORT).show();
							// //删除成功后就把相应的listItem删掉
							//
							Intent intent2 = new Intent();  
							intent2.setAction("shan.chu.hao.you");  
							HisPerson.this.sendBroadcast(intent2);
							finish();
						} else {
							Toast.makeText(HisPerson.this, "删除失败！", Toast.LENGTH_SHORT)
									.show();

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
				ParseEq(content);
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
		//gallaryAdapter.notifyDataSetChanged();
		device.setText("" + udevice);

	}

	
	private void dectRelationship(){
		if (TextUtils.isEmpty(id)) {
			return;
		}
		
		
		if (isexit(id)) {
			relation.setText(R.string.isfriend);
			addfriend.setVisibility(View.GONE);
		}else {
			relation.setText(R.string.nofriend);
			addfriend.setVisibility(View.VISIBLE);
		}
		
	}
	
}
