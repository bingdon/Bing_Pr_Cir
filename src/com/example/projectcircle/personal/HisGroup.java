package com.example.projectcircle.personal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.NearGpAdapter;
import com.example.projectcircle.bean.GroupInfo;
import com.example.projectcircle.group.GroupDetail;
import com.example.projectcircle.group.GroupPage;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class HisGroup  extends Activity {
	private static final String TAG = null;
	private String id;
	public ArrayList<GroupInfo> groupList;
	String gid;
	String people_num;
	Double mylat = HomeActivity.latitude;
	Double mylon = HomeActivity.longitude;
	private double glatitude;
	private double commercialLon;
	private double glongtitude;
	private ArrayList<GroupInfo> GroupList;
	private ListView listview;
	private ListAdapter myAdapter;
	private Button back;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.near_group);
		TextView edittext = (TextView)findViewById(R.id.textView1);
		edittext.setText("加入的群组");
		Button add_group =(Button)findViewById(R.id.n_group_right);
		add_group.setVisibility(View.GONE);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		findCreatGroup(id);
		back();
		}
	private void back() {
		// TODO Auto-generated method stub
		back = (Button)findViewById(R.id.n_group_left);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	
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
				Log.i("返回:findCreateGroup的response", "返回:" + response);
				parsefindCreateGroup(response);
				initList();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.findCreatGroup(id, res);
	}
	private void parsefindCreateGroup(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("groups");	
			JSONArray json = obj.getJSONArray("resultlist");
			groupList = new ArrayList<GroupInfo>();
			int length = json.length();
			System.out.println("length==" + length);
			Log.i(TAG, "JSONArray:" + json);
			for (int i = 0; i < length; i++) {
				GroupInfo group = new GroupInfo();
				JSONObject objo = json.getJSONObject(i);
				Log.i(TAG, "obj:" + objo);
				gid = objo.getString("id");
				group.setId(objo.getString("id"));
				group.setGname(objo.getString("gname"));
				group.setContent(objo.getString("content"));
				group.setHeadimage(objo.getString("headimage"));
				group.setGaddress(objo.getString("gaddress"));
				group.setLat(Double.parseDouble(objo.getString("commercialLat")));
				group.setLon( Double.parseDouble(objo.getString("commercialLon")));			
				groupList.add(group);	
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
		private void initList() {
			// TODO Auto-generated method stub
			listview = (ListView) findViewById(R.id.n_group_listView);
			myAdapter = new NearGpAdapter(getList(), this);
			listview.setAdapter(myAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					// Toast.makeText(getApplicationContext(), "123",
					// Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(HisGroup.this, GroupDetail.class);
					intent.putExtra("id", groupList.get(arg2).getId());
					startActivity(intent);
				}
			});
		}
		private ArrayList<HashMap<String, Object>> getList() {
			ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
			// TODO Auto-generated method stub
			Log.i(TAG, "返回:groupList" + groupList);
			if(groupList != null){
			for (int i = 0; i < groupList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("gname", groupList.get(i).getGname());
				map.put("content", groupList.get(i).getContent());
				map.put("headimage", groupList.get(i).getHeadimage());
				map.put("gaddress", groupList.get(i).getGaddress());
				map.put("people", people_num);
				map.put("commercialLat", groupList.get(i).getLat());
				map.put("commercialLon", groupList.get(i).getLon());
				map.put("people", people_num);
				glatitude = groupList.get(i).getLat();
				glongtitude =groupList.get(i).getLon();
				double distance = DistentsUtil.getDistance(glongtitude,glatitude,
						 mylon,mylat) / 1000;
				distance = DistentsUtil.changep2(distance / 1000);
				map.put("distance", distance);
				listItem.add(map);
			}
			}
			//对含有hashmap的arraylist排序
			 Collections.sort(listItem, new Comparator<HashMap<String,Object>>(){ 
	             @Override 
	             public int compare(HashMap<String, Object> arg0,HashMap<String, Object> arg1) { 
	                 return ( (arg1.get("distance"))+"").compareTo(""+ arg0.get("distance")); 
	             } 
	         }); 
			return listItem;
			
		}

}
