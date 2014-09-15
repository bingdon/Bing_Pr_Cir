package com.example.projectcircle.group;

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

import com.example.projectcircle.R;
import com.example.projectcircle.adpter.NearGpAdapter;
import com.example.projectcircle.bean.GroupInfo;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class AreaGroup extends Activity {
	private static final String TAG = "AreaGroup";
	ListView listview;
	NearGpAdapter myAdapter;
	Button back, add;
	public ArrayList<GroupInfo> groupList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.area_group);
		initBtn();
		GroupList();
	}

	private void GroupList() {
		// TODO Auto-generated method stub

		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "·µ»Ø:" + response);
				parseGroupList(response);
				initList();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.GroupList(res);

	}

	public void parseGroupList(String response) {
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

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.area_group_left);
		add = (Button) findViewById(R.id.area_group_right);
		back.setOnClickListener(listener);
		add.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.area_group_left:
				// Intent intent = new Intent(GroupPage.this,
				// MainActivity.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.area_group_right:
				Intent intent1 = new Intent(AreaGroup.this, CreateGroup.class);
				startActivity(intent1);
				break;
			default:
				break;
			}
		}
	};

	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.area_group_listView);
		myAdapter = new NearGpAdapter(getList(), this);
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(), "123",
				// Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(AreaGroup.this, GroupDetail.class);
				intent.putExtra("id", groupList.get(arg2).getId());
				startActivity(intent);
			}
		});
	}

	private ArrayList<HashMap<String, Object>> getList() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		for (int i = 0; i < groupList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("gname", groupList.get(i).getGname());
			map.put("gaddress", groupList.get(i).getGaddress());
			map.put("content", groupList.get(i).getContent());
			map.put("headimage", groupList.get(i).getHeadimage());
			listItem.add(map);
		}
		return listItem;
	}
}
