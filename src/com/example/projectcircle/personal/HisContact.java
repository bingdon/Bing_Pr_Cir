package com.example.projectcircle.personal;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.adpter.FriendAdapter;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
public class HisContact extends Activity{
	
	private static final String TAG = null;
	private String id;
	private ArrayList<UserInfo> friendList;
    private ArrayList<HashMap<String, Object>> listItem  = new ArrayList<HashMap<String, Object>>();
    private ArrayList<HashMap<String, Object>> listItem2  = new ArrayList<HashMap<String, Object>>();
	private FriendAdapter myAdapter;
	ListView listview ;
	private Button back;
	private String biaoji;
	private TextView textView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.his_contact);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		biaoji = intent.getStringExtra("biaoji");
	    listview = (ListView) findViewById(R.id.his_contact_listView);
		//标记是0，找他的好友
		if(biaoji.equals("0")){
		findFriend(id);
		}
		//标记是1，找共同好友
		else if(biaoji.equals("1")){
		//共同好友列出来
		textView1 =(TextView)findViewById(R.id.textView1);
		textView1.setText("共同好友");
		ListCommFri();
		}
		back();
	}
	//共同好友列出来
	private void ListCommFri() {
		// TODO Auto-generated method stub
	
	    listItem2 =	PersonalPage.commfrihead;//要明白Staitc对象的含义
		myAdapter=new FriendAdapter(listItem2, HisContact.this);
		myAdapter.notifyDataSetChanged();
		listview.setAdapter(myAdapter);
	   // PersonalPage.commfrihead= new ArrayList <HashMap<String, Object>>() ;
		listview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HisContact.this,PersonalPage.class);
				intent.putExtra("id", listItem2.get(arg2).get("id")+"");	
				intent.putExtra("type",listItem2.get(arg2).get("type")+"");			
				intent.putExtra("ccid",listItem2.get(arg2).get("ccid")+"");
				intent.putExtra("lat",listItem2.get(arg2).get("commercialLat")+"");
				intent.putExtra("lon",listItem2.get(arg2).get("commercialLon")+"");
				startActivity(intent);
				finish();
			}
		});
	}

	private void back() {
		// TODO Auto-generated method stub
		back = (Button)findViewById(R.id.maybe_friend_left);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	
	}

	private void findFriend(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", response);
				parsefindfriend(response);
                initHisFriend();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.findfriend(id, res);
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
				try {
					 user.setEquipment(obj.getString("equipment"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				user.setSign(objo.getString("sign"));
				user.setHeadimage(objo.getString("headimage"));
				user.setAccept(objo.getString("accept"));
				user.setLat(Double.valueOf(objo.getString("commercialLat")));
				user.setLon(Double.valueOf(objo.getString("commercialLon")));
				try {
					user.setLastlogintime(objo.getString("reflashtime"));
				} catch (Exception e) {
					// TODO: handle exception
				}
//				user.setLastlogintime(objo.getString("lastlogintime"));		
				friendList.add(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	protected void initHisFriend() {
		// TODO Auto-generated method stub	
		listItem.clear();
		listItem=getList_friend();
	
		myAdapter=new FriendAdapter(listItem, HisContact.this);
		myAdapter.notifyDataSetChanged();
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HisContact.this,HisPerson.class);
				intent.putExtra("id", friendList.get(arg2).getId());	
				intent.putExtra("type", friendList.get(arg2).getType());	
				startActivity(intent);
				finish();
			}
		});

		}
	private ArrayList<HashMap<String, Object>> getList_friend() {
		listItem = new ArrayList<HashMap<String, Object>>();

		// TODO Auto-generated method stub
		Log.i("返回:friendList", "返回:friendList" + friendList);
		if (friendList != null) {
			for (int i = 0; i < friendList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("friendID", friendList.get(i).getId());
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
				map.put("equipment", friendList.get(i).getEquipment());
				listItem.add(map);
			}
		}
		return listItem;
	}
}
