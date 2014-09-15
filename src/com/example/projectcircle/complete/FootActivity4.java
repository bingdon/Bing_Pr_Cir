package com.example.projectcircle.complete;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.adpter.MasterDviAdapter;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FootActivity4 extends Activity implements OnClickListener {

	EditText weight;
	TextView number;
	Button sub, add, sure;
	int num = 0;
	String eload;
	String enumber;
	// 职业
	String type;
	// 设备名
	String ename;
	// userid
	String uid;
	String equipment = "平板车";	
	public static String equid;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foot_layout4);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		weight = (EditText) findViewById(R.id.flat_car_weigh);
		number = (TextView) findViewById(R.id.flat_car_num);
		sub = (Button) findViewById(R.id.flat_car_sub);
		add = (Button) findViewById(R.id.flat_car_add);
		sure = (Button) findViewById(R.id.flat_car_sure);

		sub.setOnClickListener(this);
		add.setOnClickListener(this);
		sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.flat_car_sub:// 控制数量减
			if (num == 0) {
				enumber = "0";
				number.setText("0");
			} else {
				num--;
				enumber = num + "";
				number.setText(num + "");
			}
			break;
		case R.id.flat_car_add:// 控制数量增
			num++;
			enumber = num + "";
			number.setText(num + "");
			break;
		case R.id.flat_car_sure:
			submit();
			break;
		default:
			break;
		}
	}

	private void submit() {
		// TODO Auto-generated method stub
		eload = weight.getText().toString()+"10";
		ename = CompleteMaster.type;
		if(!TextUtils.isEmpty(SiginActivity.id) && !TextUtils.isEmpty(SiginActivity.type)){
			uid = SiginActivity.id;
			type = SiginActivity.type;
		}else{
			uid = LoginActivity.id;	 //要是登录进来，点"当前信息不完整，完善后有利于交友",就得用这个id了
			type = HomeActivity.utype;
		}

		Log.i("ename+type+uid", ename + "+" + type + "+" + uid);

		if (!TextUtils.isEmpty(eload) && !TextUtils.isEmpty(enumber)) {
			CompMasterFlat(uid, type, ename, eload, enumber);
		}
	}

	private void CompMasterFlat(String uid, final String type, String ename,
			final String eload, final String enumber) {

		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						JSONObject equ = obj.getJSONObject("equ");
						equid = equ.getString("id");//获得上传三张照片所需要的设备的id
						Toast.makeText(getApplicationContext(), "添加成功！",
								Toast.LENGTH_LONG).show();
						initList();
					} else {
						Toast.makeText(getApplicationContext(), "添加失败！",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error, String response) {
				// TODO Auto-generated method stub
				super.onFailure(error, response);
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.CompMasterFlat(uid, type, ename, eload, enumber, res);
	}

	private void initList() {
		// TODO Auto-generated method stub
		CompleteMaster.myAdapter = new MasterDviAdapter(
				getApplicationContext(), getList());
		CompleteMaster.myAdapter.notifyDataSetChanged();
		CompleteMaster.listview.setAdapter(CompleteMaster.myAdapter);
		CompleteMaster.listview.setSelection(CompleteMaster.listview.getCount() - 1);

	}

	private ArrayList<HashMap<String, Object>> getList() {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", equid);
		map.put("ename", ename);
		map.put("eload", eload);
		map.put("enumber", enumber);
		map.put("select_num", FootActivity1.select_num++);
		map.put("equipment", equipment);
		FootActivity1.listItem.add(map);
		// CompleteMaster.myAdapter.notifyDataSetChanged();
		 Log.i("FootActivity1.listItem", FootActivity1.listItem+"");
		return FootActivity1.listItem;

	}

}
