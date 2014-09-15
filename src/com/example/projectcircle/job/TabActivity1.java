package com.example.projectcircle.job;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.TabAdapter;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.friend.FriendPage;
import com.example.projectcircle.personal.PersonalPage;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 商家列表 全部
 */
public class TabActivity1 extends Activity {
	private static final String TAG = "TabActivity1";
	ListView listview;
	TabAdapter myAdapter;
	public ArrayList<UserInfo> busiList;
	String equ= "全部";
	private CheckBox the_machine;//整机
	private CheckBox spare_parts;//零配件
	private CheckBox repair;//维修
	private CheckBox other;//其它
	private String business;

	private double latitude = HomeActivity.latitude;
	private double longitude = HomeActivity.longitude;


	private TabBroadCast receiver;
	private ArrayList<UserInfo> usersList;	
	private LinearLayout businesslayout;
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	boolean is = true;//防止看完个人信息详情返回本activity界面后还刷新
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_tab_list);

		checkBoxButton();
		// 注册广播
		IntentFilter filter = new IntentFilter();
		filter.addAction("TabActivity1");
		receiver = new TabBroadCast();
		registerReceiver(receiver, filter);	
	}
    
   //Activity从后台重新回到前台时被调用  
    @Override  
	  protected void onRestart() {  
	      super.onRestart();  
	      Log.i(TAG, "onRestart called.");  
	  }  
	    
	  //Activity创建或者从被覆盖、后台重新回到前台时被调用  
	  @Override  
	  protected void onResume() {  
	      super.onResume();  
	      Log.i(TAG, "onResume called."); 
	  	//选择整机、零配件、维修、其它
	  	businesslayout.setVisibility(View.VISIBLE); 
	  	business ="";
	  	Log.i("------is------", is+"");
	  	if(is){
		userFilterByBusEqu(equ,business,latitude,longitude);
	  	}else{
	  		is = true;
	  	}
	  	
	  }  

	private void checkBoxButton() {
		// TODO Auto-generated method stub
		 listview = (ListView) findViewById(R.id.tab_list);
		 businesslayout = (LinearLayout)findViewById(R.id.linearLayout_moveshop_2);
		 the_machine = (CheckBox)findViewById(R.id.busi_btn_moveshop_1);
		 spare_parts = (CheckBox)findViewById(R.id.busi_btn_moveshop_2);
		 repair = (CheckBox)findViewById(R.id.busi_btn_moveshop_3);
		 other = (CheckBox)findViewById(R.id.busi_btn_moveshop_4);
	}

	private void InitCheckBox() {
		// TODO Auto-generated method stub
//		 getList();	
		 if(the_machine.isChecked() && !spare_parts.isChecked() && !repair.isChecked() && !other.isChecked()){
			 business = "整机";			
		 } else if(!the_machine.isChecked() && spare_parts.isChecked() && !repair.isChecked() && !other.isChecked()){
			 business = "零配件";			
		 } else if(!the_machine.isChecked() && !spare_parts.isChecked() && repair.isChecked() && !other.isChecked()){
			 business = "维修";			
		 } else if(!the_machine.isChecked() && !spare_parts.isChecked() && !repair.isChecked() && other.isChecked()){
			 business = "其它";		
		 }else if(the_machine.isChecked() && spare_parts.isChecked() && !repair.isChecked() && other.isChecked()){
			 business = "整机,零配件";			
		 }else if(the_machine.isChecked() && !spare_parts.isChecked() && repair.isChecked() && !other.isChecked()){
			 business = "整机,维修";		
		 }else if(the_machine.isChecked() && !spare_parts.isChecked() && !repair.isChecked() && other.isChecked()){
			 business = "整机,其它";		
		 }else if(!the_machine.isChecked() && spare_parts.isChecked() && repair.isChecked() && !other.isChecked()){
			 business = "零配件,维修";		
		 }else if(!the_machine.isChecked() && spare_parts.isChecked() && !repair.isChecked() && other.isChecked()){
			 business = "零配件,其它";
		 }else if(!the_machine.isChecked() && !spare_parts.isChecked() && repair.isChecked() && other.isChecked()){
			 business = "维修,其它";			
		 }else if(the_machine.isChecked() && spare_parts.isChecked() && repair.isChecked() && !other.isChecked()){
			 business = "整机,零配件,维修";		
		 }else if(the_machine.isChecked() && spare_parts.isChecked() && !repair.isChecked() && other.isChecked()){
			 business = "整机,零配件,其它";			
		 }else if(!the_machine.isChecked() && spare_parts.isChecked() && repair.isChecked() && other.isChecked()){
			 business = "零配件,维修,其它";
			 userFilterByBusEqu(equ,business,latitude,longitude);
		 }else if(the_machine.isChecked() && !spare_parts.isChecked() && repair.isChecked() && other.isChecked()){
			 business = "整机,维修,其它";	
		 }else if(the_machine.isChecked() && spare_parts.isChecked() && repair.isChecked() && other.isChecked()){
			 business = "整机,零配件,维修,其它";		
		 }
		 
	}

	//筛选相应条件的商家
		private void userFilterByBusEqu(String equ, String business,
				double latitude, double longitude) {
			// TODO Auto-generated method stub
			AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					// TODO Auto-generated method stub
					Log.i("移动商家筛选结果response", "返回:" + response);
					JSONObject obj;
					try {
						obj = new JSONObject(response);
						if (obj.getInt("result") == 1) {
							parseuserFilterByBusEqu(response);	
							//解析后显示出来，启动相应的activity
							listItem.clear();
							Log.i("清空后的listItem",listItem+"");
							listItem=getList();
							Log.i("新的listItem",listItem+"");
							myAdapter=new TabAdapter(listItem, TabActivity1.this);						
//							myAdapter.notifyDataSetChanged();				
							listview.setAdapter(myAdapter);
							myAdapter.notifyDataSetChanged();
							listview.setOnItemClickListener(new OnItemClickListener() {
							

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(TabActivity1.this, PersonalPage.class);
									intent.putExtra("id", usersList.get(arg2).getId());
									intent.putExtra("type",  usersList.get(arg2).getType());
									intent.putExtra("time",usersList.get(arg2).getLastlogintime());
									intent.putExtra("lat",usersList.get(arg2).getLat());
									intent.putExtra("lon",usersList.get(arg2).getLon());
									startActivity(intent);
									is = false;
								}
							});
							Log.i(TAG, "更新:"+myAdapter.getCount());
							}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
		
			};
			MyHttpClient client = new MyHttpClient();
			client.userFilterByBusEqu(equ,business,latitude,longitude,res);
		}

		private void parseuserFilterByBusEqu(String response) {
			// TODO Auto-generated method stub
			try {
				JSONObject result = new JSONObject(response);
				JSONObject obj = result.getJSONObject("users");			   
		        Log.i("移动商家的obj",obj+"");
				usersList = new ArrayList<UserInfo>();
				JSONArray json = obj.getJSONArray("resultlist");
				int length = json.length();
				System.out.println("length==" + length);
				for (int i = 0; i < length; i++) {				
					obj = json.getJSONObject(i);
					UserInfo user = null;
					//机主和司机的去除，移动商家这块只要商家
					if(!obj.getString("type").equals("机主") && !obj.getString("type").equals("司机")){
					user = new UserInfo();
					user.setId(obj.getString("id"));
					user.setBusiness(obj.getString("business"));//业务
					user.setUsername(obj.getString("username"));
					user.setType(obj.getString("type"));
					user.setAddress(obj.getString("address"));
					user.setEquipment(obj.getString("equipment"));
					user.setSign(obj.getString("sign"));
					user.setLastlogintime(obj.getString("lastlogintime"));
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
					}
					if(user != null){
					usersList.add(user);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				
			}
			Log.i("parseuserFilterByBusEqu,usersList----",usersList+"");
		}	
		// 广播
		private class TabBroadCast extends BroadcastReceiver {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				// 重新发一次请求，好更新friend 列表
				Log.i("看看广播有没有成功啦", "msg:" + equ);
				//选择整机、零配件、维修、其它			
				businesslayout.setVisibility(View.GONE); 
				InitCheckBox();
			    userFilterByBusEqu(equ,business,latitude,longitude);
				
			}

		}
		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			unregisterReceiver(receiver);

		}

		private ArrayList<HashMap<String, Object>> getList() {		
			// TODO Auto-generated method stub
			Log.i(TAG, "返回:usersList" + usersList);		
			if (usersList != null) {
								
				for (int i = 0; i < usersList.size(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
//					map.put("id", usersList.get(i).getId());
					map.put("address", usersList.get(i).getAddress());
					map.put("type", usersList.get(i).getType());
					map.put("headimage", usersList.get(i).getHeadimage());
					map.put("username", usersList.get(i).getUsername());
					map.put("tel", usersList.get(i).getTel());
					map.put("accept", usersList.get(i).getAccept());
					map.put("equipment", usersList.get(i).getEquipment());
					map.put("sign", usersList.get(i).getSign());
					map.put("lastlogintime", usersList.get(i).getLastlogintime());
					map.put("commercialLat", usersList.get(i).getLat());
					map.put("commercialLon", usersList.get(i).getLon());
					listItem.add(map);
				}
				Log.i(" getList() ,listItem----",listItem+"");
				
			}
			
			return listItem;
		}
}
