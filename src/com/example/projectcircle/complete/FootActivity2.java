package com.example.projectcircle.complete;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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

/**
 * 自卸车
 */
public class FootActivity2 extends Activity implements OnClickListener {

	Button dumper_radio1, dumper_radio2, dumper_radio3, dumper_radio4,
			dumper_radio5;
	final String[] items = new String[] { "陕汽重卡", "依维柯红岩", "中国重汽", "北汽福田",
			"东风", "一气解放", "北奔", "安徽华菱", "其它" };
	TextView brand, number;
	EditText Emodel;
	Button sub, add, sure;
	int num = 0;
	int which1 = 0;	
	String emodel;
	String ebrand;
	String etype = "前二后四";
	String enumber;
	// 职业
	String type;
	// 设备名
	String ename;
	// userid
	String uid;
	public static String equid;
	String equipment = "自卸车";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foot_layout2);
		initView();
		initBtn();
	}

	private void initView() {
		// TODO Auto-generated method stub
		brand = (TextView) findViewById(R.id.dumper_brand);
		number = (TextView) findViewById(R.id.dumper_num);
		Emodel = (EditText) findViewById(R.id.dumper_type);
		sub = (Button) findViewById(R.id.dumper_sub);
		add = (Button) findViewById(R.id.dumper_add);
		sure = (Button) findViewById(R.id.dumper_sure);

		brand.setOnClickListener(this);
		sub.setOnClickListener(this);
		add.setOnClickListener(this);
		sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dumper_brand:
			MyDialog();
			break;
		case R.id.dumper_sub:// 控制数量减
			if (num == 0) {
				enumber = "0";
				number.setText("0");
			} else {
				num--;
				enumber = num + "";
				number.setText(num + "");
			}
			break;
		case R.id.dumper_add:// 控制数量增
			num++;
			enumber = num + "";
			number.setText(num + "");
			break;
		case R.id.dumper_sure:
			submit();
			break;
		default:
			break;
		}

	}

	private void initBtn() {
		// TODO Auto-generated method stub
		dumper_radio1 = (Button) findViewById(R.id.dumper_radiobtn1);
		dumper_radio2 = (Button) findViewById(R.id.dumper_radiobtn2);
		dumper_radio3 = (Button) findViewById(R.id.dumper_radiobtn3);
		dumper_radio4 = (Button) findViewById(R.id.dumper_radiobtn4);
		dumper_radio5 = (Button) findViewById(R.id.dumper_radiobtn5);

		dumper_radio1.setOnClickListener(carlistener);
		dumper_radio2.setOnClickListener(carlistener);
		dumper_radio3.setOnClickListener(carlistener);
		dumper_radio4.setOnClickListener(carlistener);
		dumper_radio5.setOnClickListener(carlistener);
	}

	private View.OnClickListener carlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.dumper_radiobtn1) {
				dumper_radio1.setBackgroundResource(R.drawable.onechoice_press);
				dumper_radio2.setBackgroundResource(R.drawable.onechoice);
				dumper_radio3.setBackgroundResource(R.drawable.onechoice);
				dumper_radio4.setBackgroundResource(R.drawable.onechoice);
				dumper_radio5.setBackgroundResource(R.drawable.onechoice);
				etype = "前二后四";
			}
			if (v.getId() == R.id.dumper_radiobtn2) {
				dumper_radio1.setBackgroundResource(R.drawable.onechoice);
				dumper_radio2.setBackgroundResource(R.drawable.onechoice_press);
				dumper_radio3.setBackgroundResource(R.drawable.onechoice);
				dumper_radio4.setBackgroundResource(R.drawable.onechoice);
				dumper_radio5.setBackgroundResource(R.drawable.onechoice);
				etype = "前二后八";
			}
			if (v.getId() == R.id.dumper_radiobtn3) {
				dumper_radio1.setBackgroundResource(R.drawable.onechoice);
				dumper_radio2.setBackgroundResource(R.drawable.onechoice);
				dumper_radio3.setBackgroundResource(R.drawable.onechoice_press);
				dumper_radio4.setBackgroundResource(R.drawable.onechoice);
				dumper_radio5.setBackgroundResource(R.drawable.onechoice);
				etype = "前四后四";
			}
			if (v.getId() == R.id.dumper_radiobtn4) {
				dumper_radio1.setBackgroundResource(R.drawable.onechoice);
				dumper_radio2.setBackgroundResource(R.drawable.onechoice);
				dumper_radio3.setBackgroundResource(R.drawable.onechoice);
				dumper_radio4.setBackgroundResource(R.drawable.onechoice_press);
				dumper_radio5.setBackgroundResource(R.drawable.onechoice);
				etype = "前四后八";
			}
			if (v.getId() == R.id.dumper_radiobtn5) {
				dumper_radio1.setBackgroundResource(R.drawable.onechoice);
				dumper_radio2.setBackgroundResource(R.drawable.onechoice);
				dumper_radio3.setBackgroundResource(R.drawable.onechoice);
				dumper_radio4.setBackgroundResource(R.drawable.onechoice);
				dumper_radio5.setBackgroundResource(R.drawable.onechoice_press);
				etype = "半挂";
			}
		}
	};

	private void submit() {
		// TODO Auto-generated method stub
		ebrand = brand.getText().toString();
		emodel = Emodel.getText().toString();
		ename = CompleteMaster.type;

		if(!TextUtils.isEmpty(SiginActivity.id) && !TextUtils.isEmpty(SiginActivity.type)){
			uid = SiginActivity.id;
			type = SiginActivity.type;
		}else{
			uid = LoginActivity.id;	 //要是登录进来，点"当前信息不完整，完善后有利于交友",就得用这个id了
			type = HomeActivity.utype;
		}


		Log.i("ename+type+uid", ename + "+" + type + "+" + uid);

		if (!TextUtils.isEmpty(ebrand) && !TextUtils.isEmpty(emodel)
				&& !TextUtils.isEmpty(enumber)) {
			CompMaster(uid, type, ename, ebrand, etype, emodel, enumber);
		}
	}

	private void CompMaster(String uid, final String type, String ename,
			final String ebrand, final String etype, final String emodel,
			final String enumber) {

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
		client.CompMasterLoad(uid, type, ename, ebrand, etype, emodel, enumber, res);
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
		map.put("ebrand", ebrand);
		map.put("emodel", emodel);
		map.put("etype", etype);
		map.put("enumber", enumber);
		map.put("select_num", FootActivity1.select_num++);
		map.put("equipment", equipment);
		FootActivity1.listItem.add(map);
	    Log.i("FootActivity1.listItem", FootActivity1.listItem+"");
		return FootActivity1.listItem;

	}

	/**
	 * 品牌Dialog
	 * 
	 */
	private void MyDialog() {

		// TODO Auto-generated method stub
		new AlertDialog.Builder(getParent())
				.setTitle("挖掘机品牌")
				.setSingleChoiceItems(items, which1,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								which1 = which;
								brand.setText(items[which]);
								if (which == 8) {
									TxtDialog();
								}
								dialog.dismiss();
							}
						}).setNegativeButton("取消", null).show();
	}

	/**
	 * 点击其它Dialog
	 * 
	 */
	private void TxtDialog() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) FootActivity2.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(R.layout.foot_dialog, null);

		/** 这里使用链式写法创建了一个AlertDialog对话框,并且把应用到得视图viwe放入到其中 */

		/** 添加AlertDialog的用户登录按钮,并且设置按钮响应事件 */
		new AlertDialog.Builder(FootActivity2.this)
				.setTitle("请输入")
				.setView(view)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						/** 获取用户名文本框组件 */
						EditText nameEditText = (EditText) view
								.findViewById(R.id.dialog_edit);
						/** 获取用户名文本框中输入的内容 */
						String username = nameEditText.getText().toString();
						brand.setText(username);
					}
					/** 添加对话框的退出按钮,并且设置按钮响应事件 */
				})
				.setNegativeButton("退出", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				}).show();
	}
}
