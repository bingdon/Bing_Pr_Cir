package com.example.projectcircle.group;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.GroupNumAdapter;
import com.example.projectcircle.bean.GroupInfo;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.personal.PersonalPage;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GroupNum extends Activity {
	private static final String TAG = "GroupNum";
	ListView listview;
	GroupNumAdapter myAdapter;
	public ArrayList<UserInfo> memberList;
	/**
	 * 顶部Button
	 */
	Button back, right;

	String gid;
	String id;
	GroupInfo groupInfo;
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_member);
		Intent intent = getIntent();
		gid = intent.getStringExtra("gid");
		groupInfo = (GroupInfo) intent.getSerializableExtra("info");

		initBtn();
		findMember(gid);
	}

	/**
	 * 查找群成员 请求和解析
	 * 
	 */
	private void findMember(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "返回:" + response);
				parsefindMember(response);
				initList();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.findMember(id, res);
	}

	private void parsefindMember(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("members");
			memberList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				UserInfo member = new UserInfo();
				obj = json.getJSONObject(i);
				id = obj.getString("id");
				member.setId(obj.getString("id"));
				member.setTel(obj.getString("tel"));
				member.setUsername(obj.getString("username"));
				member.setType(obj.getString("type"));
				member.setAddress(obj.getString("address"));
				member.setEquipment(obj.getString("equipment"));
				member.setSign(obj.getString("sign"));
				member.setLastlogintime(obj.getString("lastlogintime"));
				member.setHeadimage(obj.getString("headimage"));
				member.setAccept(obj.getString("accept"));
				member.setLat(obj.getDouble("commercialLat"));
				member.setLon(obj.getDouble("commercialLon"));
				memberList.add(member);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.g_member_left);
		right = (Button) findViewById(R.id.g_member_right);

		back.setOnClickListener(listener);
		right.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.g_member_left:
				// Intent intent = new Intent(GroupNum.this, GroupDetail.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.g_member_right:

				break;

			}
		}
	};

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.group_num, menu);

	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		ToastUtils.showLong(GroupNum.this, "ID:" + item.getItemId());
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		ToastUtils.showLong(GroupNum.this, "ID:" + item.getItemId());
		switch (item.getItemId()) {
		case R.id.deletenum:
			ToastUtils.showLong(GroupNum.this, "ID:" + "");
			try {
				AppLog.i(TAG, "个人ID:" + listItem);
				AppLog.i(TAG, "个人ID:" + listItem.get(aInfo.position));
				String numid = listItem.get(aInfo.position).get("id") + "";
				AppLog.i(TAG, "个人ID:" + numid);
				ToastUtils.showLong(GroupNum.this, "ID:" + numid);
				if (!numid.equals(LoginActivity.id)) {
					MyHttpClient.deleteMember2Group(gid, numid, handler);
					listItem.remove(aInfo.position);
					myAdapter.notifyDataSetChanged();
				}

			} catch (Exception e) {
				// TODO: handle exception
				AppLog.e(TAG, e.getMessage());
			}

			return true;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@SuppressLint("ShowToast")
	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.g_member_listView);
		listItem = getList();
		myAdapter = new GroupNumAdapter(listItem, this);
		listview.setAdapter(myAdapter);
		if (MyGroup.isMyGroup && groupInfo.getUid().equals(LoginActivity.id)) {
			listview.setOnItemLongClickListener(longClickListener);
		}

		// listview.setFilpperDeleteListener(new SildeDeleteListener() {
		//
		// @SuppressWarnings("unused")
		// @Override
		// public void filpperOnclick(float xPosition, float yPosition) {
		// // TODO Auto-generated method stub
		//
		// if (listview.getChildCount() == 0)
		// return;
		// // 根据坐标获得滑动删除的item的index
		// final int index = listview.pointToPosition((int) xPosition,
		// (int) yPosition);
		// // Toast.makeText(GroupNum.this, index + "点击", 1000).show();
		// }
		//
		// @Override
		// public void filpperDelete(float xPosition, float yPosition) {
		// // TODO Auto-generated method stub
		// // listview中要有item，否则返回
		// if (listview.getChildCount() == 0)
		// return;
		// // 根据坐标获得滑动删除的item的index
		// final int index = listview.pointToPosition((int) xPosition,
		// (int) yPosition);
		// //
		// 一下两步是获得该条目在屏幕显示中的相对位置，直接根据index删除会空指針异常。因为listview中的child只有当前在屏幕中显示的才不会为空
		// int firstVisiblePostion = listview.getFirstVisiblePosition();
		// View view = listview.getChildAt(index - firstVisiblePostion);
		// view.findViewById(R.id.remove).setVisibility(View.VISIBLE);
		// view.findViewById(R.id.main_call).setVisibility(View.INVISIBLE);
		// view.findViewById(R.id.icon_address).setVisibility(
		// View.INVISIBLE);
		// view.findViewById(R.id.main_distance).setVisibility(
		// View.INVISIBLE);
		// // Toast.makeText(GroupNum.this, index + "横向滑动", 1000).show();
		//
		// }
		// });
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GroupNum.this, PersonalPage.class);
				intent.putExtra("id", memberList.get(arg2).getId());
				intent.putExtra("type", memberList.get(arg2).getType());
				intent.putExtra("time", memberList.get(arg2).getLastlogintime());
				intent.putExtra("lat", memberList.get(arg2).getLat());
				intent.putExtra("lon", memberList.get(arg2).getLon());
				startActivity(intent);
			}
		});
	}

	private ArrayList<HashMap<String, Object>> getList() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		if (memberList != null && memberList.size() > 0) {
			for (int i = 0; i < memberList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", memberList.get(i).getId());
				map.put("tel", memberList.get(i).getTel());
				map.put("name", memberList.get(i).getUsername());
				map.put("type", memberList.get(i).getType());
				map.put("address", memberList.get(i).getAddress());
				map.put("equipment", memberList.get(i).getEquipment());
				map.put("sign", memberList.get(i).getSign());
				map.put("headimage", memberList.get(i).getHeadimage());
				map.put("accept", memberList.get(i).getAccept());
				map.put("lat", memberList.get(i).getLat());
				map.put("lon", memberList.get(i).getLon());
				listItem.add(map);
			}
		}
		return listItem;
	}

	private JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			super.onFinish();
		}

	};

	private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			final int fposition = position;
			new AlertDialog.Builder(GroupNum.this)
					.setTitle("踢出此成员？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									// 按确定后删除

									try {
										String numid = listItem.get(fposition)
												.get("id") + "";
										AppLog.i(TAG, "个人ID:" + numid);
										ToastUtils.showLong(GroupNum.this,
												"ID:" + numid);
										if (!numid.equals(LoginActivity.id)) {
											MyHttpClient.deleteMember2Group(
													gid, numid, handler);
											listItem.remove(fposition);
											myAdapter.notifyDataSetChanged();
										}
									} catch (Exception e) {
										// TODO: handle exception
									}

								}
							}).setNegativeButton("取消", null).show();
			return true;
		}
	};

}
