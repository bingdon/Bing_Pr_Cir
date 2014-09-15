package com.example.projectcircle.friend;

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
import com.example.projectcircle.adpter.MayBeAdapter;
import com.example.projectcircle.personal.PersonalPage;

public class MaybeFriend extends Activity {
	private static final String TAG = "MaybeFriend";
	ListView listview;
	MayBeAdapter myAdapter;
	Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maybe_friend);
		initBtn();
		initList();
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.maybe_friend_left);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(MaybeFriend.this,
				// MainActivity.class);
				// startActivity(intent);
				finish();
			}
		});
	}

	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.maybe_friend_listView);
		myAdapter = new MayBeAdapter(getList(), this);
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MaybeFriend.this, PersonalPage.class);
				startActivity(intent);
			}
		});
	}

	private ArrayList<HashMap<String, Object>> getList() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", "’≈—Û");
			listItem.add(map);
		}
		return listItem;
	}
}
