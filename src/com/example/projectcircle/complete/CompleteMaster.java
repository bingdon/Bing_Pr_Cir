package com.example.projectcircle.complete;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.MasterDviAdapter;
import com.example.projectcircle.bean.EquInfo;
import com.example.projectcircle.setting.ModifyInfoActivity;
import com.example.projectcircle.util.ListUtilty;
import com.example.projectcircle.util.LoadImageUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 完善机主
 * 
 * @author Walii
 * @version 2014.3.18
 */

@SuppressWarnings("deprecation")
public class CompleteMaster extends TabActivity implements OnClickListener {
	public static ListView listview;
	public static MasterDviAdapter myAdapter;
	/**
	 * Btn
	 */
	Button back, next;
	/**
	 * 选择设备
	 */
	private TabHost tabhost;
	Button d_radio1, d_radio2, d_radio3, d_radio4, d_radio5;
	public static EditText d_content;

	public static String type = "挖掘机";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_master);
		if (!ModifyInfoActivity.isModify()) {
			findViewById(R.id.linearLayout1).setVisibility(View.VISIBLE);
		}
		initFilter();
		initTab();
		initBtn();
		tabhost.setCurrentTabByTag("tab1");
		d_radio1.setBackgroundResource(R.drawable.onechoice_press);
		initList();
		EquDetail(LoginActivity.id);
	}

	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.master_listview);
		listview.setDividerHeight(0);// 将listView分割线去掉
		myAdapter = new MasterDviAdapter(getApplicationContext(), getList());
		listview.setAdapter(myAdapter);
		registerForContextMenu(listview);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	private ArrayList<HashMap<String, Object>> getList() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		return listItem;
	}

	private void initTab() {
		// TODO Auto-generated method stub
		d_content = (EditText) findViewById(R.id.sigin_car);
		tabhost = this.getTabHost();

		TabSpec tabSpec1 = tabhost.newTabSpec("tab1").setIndicator("tab1")
				.setContent(new Intent(this, FootActivity1.class));
		TabSpec tabSpec2 = tabhost.newTabSpec("tab2").setIndicator("tab2")
				.setContent(new Intent(this, FootActivity2.class));
		TabSpec tabSpec3 = tabhost.newTabSpec("tab3").setIndicator("tab3")
				.setContent(new Intent(this, FootActivity3.class));
		TabSpec tabSpec4 = tabhost.newTabSpec("tab4").setIndicator("tab4")
				.setContent(new Intent(this, FootActivity4.class));
		TabSpec tabSpec5 = tabhost.newTabSpec("tab5").setIndicator("tab5")
				.setContent(new Intent(this, FootActivity5.class));
		tabhost.addTab(tabSpec1);
		tabhost.addTab(tabSpec2);
		tabhost.addTab(tabSpec3);
		tabhost.addTab(tabSpec4);
		tabhost.addTab(tabSpec5);

		d_radio1 = (Button) findViewById(R.id.car_radiobtn1);
		d_radio2 = (Button) findViewById(R.id.car_radiobtn2);
		d_radio3 = (Button) findViewById(R.id.car_radiobtn3);
		d_radio4 = (Button) findViewById(R.id.car_radiobtn4);
		d_radio5 = (Button) findViewById(R.id.car_radiobtn5);

		d_radio1.setOnClickListener(this);
		d_radio2.setOnClickListener(this);
		d_radio3.setOnClickListener(this);
		d_radio4.setOnClickListener(this);
		d_radio5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.car_radiobtn1) {
			d_radio1.setBackgroundResource(R.drawable.onechoice_press);
			d_radio2.setBackgroundResource(R.drawable.onechoice);
			d_radio3.setBackgroundResource(R.drawable.onechoice);
			d_radio4.setBackgroundResource(R.drawable.onechoice);
			d_radio5.setBackgroundResource(R.drawable.onechoice);
			d_content.setVisibility(View.INVISIBLE);
			tabhost.setCurrentTabByTag("tab1");
			type = "挖掘机";
			next.setVisibility(View.VISIBLE);
		}
		if (v.getId() == R.id.car_radiobtn2) {
			d_radio1.setBackgroundResource(R.drawable.onechoice);
			d_radio2.setBackgroundResource(R.drawable.onechoice_press);
			d_radio3.setBackgroundResource(R.drawable.onechoice);
			d_radio4.setBackgroundResource(R.drawable.onechoice);
			d_radio5.setBackgroundResource(R.drawable.onechoice);
			d_content.setVisibility(View.INVISIBLE);
			tabhost.setCurrentTabByTag("tab2");
			next.setVisibility(View.VISIBLE);
			type = "自卸车";
		}
		if (v.getId() == R.id.car_radiobtn3) {
			d_radio1.setBackgroundResource(R.drawable.onechoice);
			d_radio2.setBackgroundResource(R.drawable.onechoice);
			d_radio3.setBackgroundResource(R.drawable.onechoice_press);
			d_radio4.setBackgroundResource(R.drawable.onechoice);
			d_radio5.setBackgroundResource(R.drawable.onechoice);
			d_content.setVisibility(View.INVISIBLE);
			tabhost.setCurrentTabByTag("tab3");
			type = "装载机";
			next.setVisibility(View.VISIBLE);
		}
		if (v.getId() == R.id.car_radiobtn4) {
			d_radio1.setBackgroundResource(R.drawable.onechoice);
			d_radio2.setBackgroundResource(R.drawable.onechoice);
			d_radio3.setBackgroundResource(R.drawable.onechoice);
			d_radio4.setBackgroundResource(R.drawable.onechoice_press);
			d_radio5.setBackgroundResource(R.drawable.onechoice);
			d_content.setVisibility(View.INVISIBLE);
			tabhost.setCurrentTabByTag("tab4");
			next.setVisibility(View.VISIBLE);
			type = "平板车";
		}
		if (v.getId() == R.id.car_radiobtn5) {
			d_radio1.setBackgroundResource(R.drawable.onechoice);
			d_radio2.setBackgroundResource(R.drawable.onechoice);
			d_radio3.setBackgroundResource(R.drawable.onechoice);
			d_radio4.setBackgroundResource(R.drawable.onechoice);
			d_radio5.setBackgroundResource(R.drawable.onechoice_press);
			d_content.setVisibility(View.VISIBLE);
			tabhost.setCurrentTabByTag("tab5");
			next.setVisibility(View.VISIBLE);
			type = "其它";
		}
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.perfect_master_left);
		next = (Button) findViewById(R.id.master_next);
		back.setOnClickListener(listener);
		next.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.perfect_master_left:
				// Intent intent = new Intent(getApplicationContext(),
				// LoginActivity.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.master_next:
				Intent intent2 = new Intent(getApplicationContext(),
						CompleteInfo.class);
				startActivity(intent2);
				// finish();
				break;
			default:
				break;
			}
		}
	};

	private void initFilter() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish.before.regist.page");
		registerReceiver(msgReceiver, filter);
	}

	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			finish();
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}

	private ArrayList<EquInfo> deviceList = new ArrayList<>();

	private void EquDetail(String uid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
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

	private void initHisDevice() {
		// TODO Auto-generated method stub
		if (deviceList != null) {
		}

		CompleteMaster.myAdapter = new MasterDviAdapter(
				getApplicationContext(), getDeviceList());
		CompleteMaster.myAdapter.notifyDataSetChanged();
		CompleteMaster.listview.setAdapter(CompleteMaster.myAdapter);
		CompleteMaster.listview
				.setSelection(CompleteMaster.listview.getCount() - 1);
	}

	private ArrayList<HashMap<String, Object>> getDeviceList() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
		if (deviceList != null) {
			for (int i = 0; i < deviceList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				EquInfo equInfo = deviceList.get(i);
				map.put("id", equInfo.getId());
				map.put("ename", equInfo.getEname());
				map.put("ebrand", equInfo.getEbrand());
				map.put("emodel", equInfo.getEmodel());
				map.put("etype", equInfo.getEstyle());
				map.put("enumber", equInfo.getEnumber());
				map.put("select_num", i + 1);
				map.put("equipment", equInfo.getEname());
				listItem.add(map);
				Log.i("listItem", listItem + "");
			}

		}

		return listItem;
	}
}
