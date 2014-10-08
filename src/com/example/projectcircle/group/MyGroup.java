package com.example.projectcircle.group;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.OperationCallback.ErrorCode;
import io.rong.imlib.RongIMClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.MyGroupAdapter;
import com.example.projectcircle.bean.GroupDataBean;
import com.example.projectcircle.bean.GroupInfo;
import com.example.projectcircle.db.utils.GroupDatabaseUtils;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MyGroup extends Activity {
	private static final String TAG = "GroupPage";
	public static boolean isMyGroup = false;
	ListView listview;
	MyGroupAdapter myAdapter;
	Button back;
	private String uid;
	private ArrayList<GroupInfo> groupList;
	private double glatitude;
	private double glongtitude;
	Double mylat = HomeActivity.latitude;
	Double mylon = HomeActivity.longitude;

	public static List<GroupDataBean> groupDataBeans = new ArrayList<GroupDataBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_list);
		initBtn();
		uid = LoginActivity.id;
		isMyGroup = true;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		findGroup(uid);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isMyGroup = false;
	}

	private void findGroup(String uid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("我的群组列表", "返回:" + response);
				parseMyGroupList(response);
				initList();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.findGroup(uid, res);
	}

	private void parseMyGroupList(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("groups");
			groupList = new ArrayList<GroupInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			Log.i("我的群组列表", "JSONArray:" + json);
			for (int i = 0; i < length; i++) {
				GroupInfo group = new GroupInfo();
				JSONObject objo = json.getJSONObject(i);
				Log.i(TAG, "objo:" + objo);
				group.setId(objo.getString("id"));
				group.setGname(objo.getString("gname"));
				group.setGaddress(objo.getString("gaddress"));
				group.setHeadimage(objo.getString("headimage"));
				group.setContent(objo.getString("content"));
				group = new Gson().fromJson(objo.toString(), GroupInfo.class);
				group.setLat(Double.parseDouble(objo.getString("commercialLat")));
				group.setLon(Double.parseDouble(objo.getString("commercialLon")));

				saveFriendinfo(group.getGname(), group.getHeadimage(),
						group.getGaddress(), group.getId(), group.getUid());

				LoginActivity.setTag("g" + group.getId(), this);

				groupList.add(group);
			}
			System.out.println("my group list==" + groupList);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.g_list_left);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyGroup.this.setResult(RESULT_OK, getIntent());
				MyGroup.this.finish();
				finish();
			}
		});
	}

	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.g_list_listView);
		myAdapter = new MyGroupAdapter(getList(), this);
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(), "123",
				// Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MyGroup.this, GroupDetail.class);
				intent.putExtra("id", groupList.get(arg2).getId());
				startActivity(intent);
			}
		});
	}

	private ArrayList<HashMap<String, Object>> getList() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		List<RongIMClient.Group> groups = new ArrayList<>();
		// TODO Auto-generated method stub
		if (groupList != null) {
			for (int i = 0; i < groupList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("gname", groupList.get(i).getGname());
				map.put("gaddress", groupList.get(i).getGaddress());
				map.put("headimage", groupList.get(i).getHeadimage());
				map.put("content", groupList.get(i).getContent());
				map.put("commercialLat", groupList.get(i).getLat());
				map.put("commercialLon", groupList.get(i).getLon());
				try {
					if (groupList.get(i).getUid().equals(LoginActivity.id)) {
						map.put("ishost", true);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				glatitude = groupList.get(i).getLat();
				glongtitude = groupList.get(i).getLon();
				double distance = DistentsUtil.getDistance(glongtitude,
						glatitude, mylon, mylat) / 1000;
				distance = DistentsUtil.changep2(distance);
				map.put("distance", distance);
				map.put("count", groupList.get(i).getCount());
				RongIMClient.Group group = new RongIMClient.Group(groupList
						.get(i).getId(), groupList.get(i).getGname(), MyHttpClient.IMAGE_URL+groupList
						.get(i).getHeadimage());
				groups.add(group);
				listItem.add(map);
			}
			
//			sysnGroups(groups);
			
		}
		return listItem;
	}

	@SuppressWarnings("unchecked")
	public static void getGroupInfo(Context context) {
		groupDataBeans = (List<GroupDataBean>) new GroupDatabaseUtils(context)
				.queryData();
	}

	GroupDatabaseUtils groupDatabaseUtils = null;

	private void saveFriendinfo(String gname, String headimage,
			String gaddress, String gid, String uid) {
		if (groupDatabaseUtils == null) {
			groupDatabaseUtils = new GroupDatabaseUtils(this);
		}

		long m = groupDatabaseUtils
				.update(gname, headimage, gaddress, gid, uid);
		Log.i(TAG, "更新:" + m);
		if (m < 1) {
			m = groupDatabaseUtils.insert(gname, headimage, gaddress, gid, uid);
			Log.i(TAG, "插入:" + m);
		}

	}

	/**
	 * 查询群组名称
	 * 
	 * @param gid
	 *            群组ID
	 * @return
	 */
	public static String getGroupname(String gid) {
		String name = "工程圈";

		if (null == groupDataBeans) {
			return name;
		}
		for (int i = 0; i < groupDataBeans.size(); i++) {
			Log.i(TAG, "名字:" + groupDataBeans.get(i).getGname());
			Log.i(TAG, "群组ID:" + groupDataBeans.get(i).getId() + ":当前:" + gid);
			if (groupDataBeans.get(i).getId().equals(gid)) {
				name = groupDataBeans.get(i).getGname();
				i = groupDataBeans.size();
			}
		}
		return name;

	}

	public static GroupDataBean getGroupData(String gid) {
		GroupDataBean groupDataBean = new GroupDataBean();
		if (null == groupDataBeans) {
			return groupDataBean;
		}
		for (int i = 0; i < groupDataBeans.size(); i++) {
			if (groupDataBeans.get(i).getId().equals(gid)) {
				groupDataBean = groupDataBeans.get(i);
				break;
			}
		}
		return groupDataBean;

	}
	
	
	private void sysnGroups(List<RongIMClient.Group> groups){
		RongIM.getInstance().syncGroup(groups, new RongIM.OperationCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				AppLog.i(TAG, "成功");
			}
			
			@Override
			public void onError(ErrorCode arg0) {
				// TODO Auto-generated method stub
				AppLog.i(TAG, "失败:"+arg0);
			}
		});
	}
	

}
