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
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.WiperSwitch;
import com.example.projectcircle.view.WiperSwitch.OnChangedListener;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 挖掘机
 * 
 * @author Walii
 * @version 2014.3.18
 */
public class FootActivity1 extends Activity implements OnClickListener {
	final String[] items = new String[] { "斗山", "小松", "沃尔沃", "日立", "卡特", "三一",
			"神钢", "现代", "玉柴", "其它" };
	TextView brand, number;
	EditText Emodel;
	Button sub, add;
	public static Button sure;
	String emodel;
	String ebrand;
	String etype = "履带";;
	String enumber;
	int num = 0;
	int which1 = 0;
	public static int select_num = 1;;//筛选的挖掘机数量
	/**
	 * 样式
	 */
	WiperSwitch wiperSwitch;

	// 职业
	String type;
	// 设备名
	String ename;
	// userid
	String uid;
	public static String equid;
	String equipment = "挖掘机";
	public static ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foot_layout1);

		initView();
		initType();
	}

	private void initView() {
		// TODO Auto-generated method stub
		brand = (TextView) findViewById(R.id.master_brand);
		number = (TextView) findViewById(R.id.master_num);
		Emodel = (EditText) findViewById(R.id.master_type);
		sub = (Button) findViewById(R.id.master_sub);
		add = (Button) findViewById(R.id.master_add);
		sure = (Button) findViewById(R.id.master_sure);
		// number.setText(num + "");
		brand.setOnClickListener(this);
		sub.setOnClickListener(this);
		add.setOnClickListener(this);
		sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.master_brand:
			MyDialog();
			break;
		case R.id.master_sub:// 控制数量减n
			if (num == 0) {
				enumber = "0";
				number.setText("0");
			} else {
				num--;
				enumber = num + "";
				number.setText(num + "");
			}
			break;
		case R.id.master_add:// 控制数量增
			num++;
			enumber = num + "";
			number.setText(num + "");
			break;
		case R.id.master_sure:
			submit();
			break;
		default:
			break;
		}
	}

	private void initType() {
		// TODO Auto-generated method stub
		wiperSwitch = (WiperSwitch) findViewById(R.id.wiperSwitch);
		wiperSwitch.SetOnChangedListener(new OnChangedListener() {

			@Override
			public void OnChanged(boolean checkState) {
				// TODO Auto-generated method stub
				if (checkState) {
					etype = "履带";
					ToastUtils.showShort(getApplicationContext(), "履带");
				} else {
					etype = "轮式";
					ToastUtils.showShort(getApplicationContext(), "轮式");
				}
			}
		});
	}

	/**
	 * 添加数据
	 * 
	 */
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
					Log.i("updateEqu-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						JSONObject equ = obj.getJSONObject("equ");
						Log.i("机主equ",equ+"");
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
		map.put("select_num", select_num++);
		map.put("equipment", equipment);
		listItem.add(map);	
		Log.i("listItem", listItem+"");
		return listItem;

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
								if (which == 9) {
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
		LayoutInflater inflater = (LayoutInflater) FootActivity1.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(R.layout.foot_dialog, null);

		/** 这里使用链式写法创建了一个AlertDialog对话框,并且把应用到得视图viwe放入到其中 */

		/** 添加AlertDialog的用户登录按钮,并且设置按钮响应事件 */
		new AlertDialog.Builder(FootActivity1.this)
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
