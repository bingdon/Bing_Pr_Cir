package com.example.projectcircle.job;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.projectcircle.R;
import com.example.projectcircle.adpter.JobDetailAdapter;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.personal.PersonalPage;

/**
 * 商家所有商品
 */
public class JobDetail extends Activity {
	private static final String TAG = "JobDetail";
	ListView listview;
	JobDetailAdapter myAdapter;
	Button back;
	String uid;
	public ArrayList<UserInfo> busiList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_detail);
		Intent intent = getIntent();
		uid = intent.getStringExtra("uid");
		initBtn();
		initList();
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.job_detail_left);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.job_detail_list);
		myAdapter = new JobDetailAdapter(getList(), this);
		listview.setAdapter(myAdapter);
		// listview.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(JobDetail.this, PersonalPage.class);
		// startActivity(intent);
		// }
		// });
	}

	private ArrayList<HashMap<String, Object>> getList() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", "车体维修");

			listItem.add(map);
		}
		return listItem;
	}
}
