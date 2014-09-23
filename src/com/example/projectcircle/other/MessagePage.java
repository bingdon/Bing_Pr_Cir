package com.example.projectcircle.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.a.a.c.c;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.MainActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.MsgAdapter;
import com.example.projectcircle.bean.FriendChatBean;
import com.example.projectcircle.bean.FriendDataBean;
import com.example.projectcircle.bean.GroupChatBean;
import com.example.projectcircle.bean.GroupDataBean;
import com.example.projectcircle.bean.MsgDataBean;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.utils.FriendChatUtils;
import com.example.projectcircle.db.utils.GroupChatUtils;
import com.example.projectcircle.db.utils.MsgDataUtils;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.friend.FriendPage;
import com.example.projectcircle.group.MyGroup;
import com.example.projectcircle.personal.MyPersonal;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MessagePage extends Activity {
	Button right;
	private static final String TAG = "MessagePage";
	ListView listview;
	MsgAdapter myAdapter;
	private View headview;
	private LinearLayout friendRequest, groupRequest;
	private ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	private List<MsgDataBean> msgDataBeans = new ArrayList<MsgDataBean>();
	private TextView addgroup_Notice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		initList();
		initFilter();
		getLastMsgList();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getGroupRequestNum();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}

	// 上下文菜单，本例会通过长按条目激活上下文菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = new MenuInflater(MessagePage.this);
		inflater.inflate(R.menu.msglist, menu);

	}

	// 菜单单机响应
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.deletemsg:
			try {
				delMsgList(listItem.get(aInfo.position - 1).get("_id")
						.toString());
				listItem.remove(aInfo.position - 1);
				myAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				// TODO: handle exception
			}

			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}

	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.message_listView);
		registerForContextMenu(listview);// 为 ListView 的所有 item 注册 ContextMenu
		
		headview = this.getLayoutInflater().inflate(R.layout.message_headview,
				null);
		listview.addHeaderView(headview);
		addgroup_Notice = (TextView) findViewById(R.id.group_notice);
		myAdapter = new MsgAdapter(listItem, this);
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = null;
				if (listItem.get(arg2 - 1).containsKey("type")) {

					String typestr = listItem.get(arg2 - 1).get("type") + "";
					int type = 0;
					try {
						type = Integer.valueOf(typestr);
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (type == 0) {
						intent = new Intent(MessagePage.this, Chat.class);
						intent.putExtra("id", listItem.get(arg2 - 1).get("id")
								.toString());
						intent.putExtra("username",
								listItem.get(arg2 - 1).get("name").toString());
						intent.putExtra("headimg", ""
								+ listItem.get(arg2 - 1).get("headimg"));
					} else {
						intent = new Intent(MessagePage.this,
								GroupChatOther.class);
						intent.putExtra("gid", listItem.get(arg2 - 1).get("id")
								.toString());
						intent.putExtra("gname",
								listItem.get(arg2 - 1).get("name").toString());
					}

					startActivity(intent);
					saveMsgList(arg2 - 1);
					MainActivity.removeNotice();

				}

			}
		});

		groupRequest = (LinearLayout) findViewById(R.id.group_request);

		friendRequest = (LinearLayout) findViewById(R.id.friend_request);
		friendRequest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MessagePage.this,
						FriendRequest.class);
				// intent.putExtra("type", 0);//0表示好友请求，1表示群组请求
				startActivity(intent);
			}
		});

		// 请求加入我这个群的请求消息

		groupRequest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addgroup_Notice.setVisibility(View.GONE);
				Intent intent = new Intent(MessagePage.this, GroupRequest.class);
				startActivity(intent);
			}
		});

	}

	// private ArrayList<HashMap<String, Object>> getList() {
	// ArrayList<HashMap<String, Object>> listItem = new
	// ArrayList<HashMap<String, Object>>();
	// // TODO Auto-generated method stub
	// for (int i = 0; i < 6; i++) {
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// map.put("isPerson", false);
	// map.put("name", "张洋");
	// listItem.add(map);
	// }
	// for (int i = 0; i < 3; i++) {
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// map.put("isPerson", true);
	// map.put("name", "工人协会");
	// listItem.add(map);
	// }
	// return listItem;
	// }

	private void initFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ContantS.ACTION_GET_MSG_FRI);
		filter.addAction(ContantS.ACTION_GET_MSG_PER);
		registerReceiver(msgReceiver, filter);
	}

	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String friid = intent.getStringExtra("id");
			String action = intent.getAction();

			if (TextUtils.isEmpty(friid)) {

				String gid = intent.getStringExtra("gid");
				onHandlerMsgGroup(action, gid);

			} else {
				onHandlerMsg(action, friid);
			}

		}
	};

	/**
	 * 处理消息
	 * 
	 * @param action
	 *            动作类型
	 * @param id
	 *            保存ID
	 */
	private void onHandlerMsg(String action, String id) {
		if (action.equals(ContantS.ACTION_GET_MSG_FRI)) {
			if (!isexit(id)) {
				addMsgList(id);
			} else {
				repalcel(getindex(id));
			}
		} else {
			if (!isexit(id)) {
				addMsg2List(id);
			} else {
				repalcel(getindex(id));
			}
		}
	}

	private void onHandlerMsgGroup(String action, String gid) {
		if (action.equals(ContantS.ACTION_GET_MSG_FRI)) {
			if (!isexit(gid)) {
				addMsgListGro(gid);
			} else {
				repalcelGroup(getindex(gid));
			}
		} else {
			if (!isexit(gid)) {
				addMsg2ListGroup(gid);
			} else {
				repalcelGroup(getindex(gid));
			}
		}
	}

	private boolean isexit(String id) {
		if (TextUtils.isEmpty(id)) {
			return false;
		}
		if (null != listItem) {
			int length = listItem.size();
			for (int i = 0; i < length; i++) {
				if (id.equals(listItem.get(i).get("id").toString())) {
					return true;
				}
			}
		}

		return false;

	}

	private int getindex(String id) {
		if (TextUtils.isEmpty(id)) {
			return 0;
		}
		if (null != listItem) {
			int length = listItem.size();
			for (int i = 0; i < length; i++) {
				if (id.equals(listItem.get(i).get("id").toString())) {
					return i;
				}
			}
		}

		return 0;

	}

	private void repalcel(int index) {
		String id = listItem.get(index).get("id").toString();
		FriendDataBean friendDataBean = FriendPage.getUserdata(id);
		String name = friendDataBean.getFriendname();
		String content = getLastChat(id);
		String headimg = friendDataBean.getFriendhead();
		String time = Chat.getDate();

		if (TextUtils.isEmpty(name)) {
			try {
				List<FriendChatBean> list = (List<FriendChatBean>) new FriendChatUtils(
						MessagePage.this, id).queryData();
				for (int i = 0; i < list.size(); i++) {
					name = list.get(i).getName();
					if (!TextUtils.isEmpty(name)) {
						break;
					}
				}

				for (int i = 0; i < list.size(); i++) {
					headimg = list.get(i).getHeadimg();
					if (!TextUtils.isEmpty(headimg)) {
						break;
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		Log.i(TAG, "名字" + name);
		Log.i(TAG, "content" + content);
		Log.i(TAG, "headimg" + headimg);
		Log.i(TAG, "time" + time);

		listItem.get(index).put("isPerson", false);
		listItem.get(index).put("name", "" + name);
		listItem.get(index).put("content", "" + content);
		listItem.get(index).put("time", "" + time);
		listItem.get(index).put("headimg", headimg);
		listItem.get(index).put("type", 0);
		int unread = 0;
		try {
			unread = Integer.valueOf(listItem.get(index).get("noreadnumm")
					.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		unread++;

		listItem.get(index).put("noreadnumm", unread);

		saveMsgList(name, content, time, id, headimg, 0, unread);
		myAdapter.notifyDataSetChanged();
	}

	private void repalcelGroup(int index) {
		String id = listItem.get(index).get("id").toString();
		GroupDataBean groupDataBean = MyGroup.getGroupData(id);
		String name = groupDataBean.getGname();
		Log.i(TAG, "名字" + name);
		String content = getLastGroupChat(id);
		Log.i(TAG, "content" + content);
		String headimg = groupDataBean.getHeadimage();
		Log.i(TAG, "headimg" + headimg);
		String time = Chat.getDate();
		Log.i(TAG, "time" + time);

		listItem.get(index).put("isPerson", false);
		listItem.get(index).put("name", "" + name);
		listItem.get(index).put("content", "" + content);
		listItem.get(index).put("time", "" + time);
		listItem.get(index).put("headimg", headimg);
		listItem.get(index).put("type", 1);
		int unread = 0;
		try {
			unread = Integer.valueOf(listItem.get(index).get("noreadnumm")
					.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		unread++;

		listItem.get(index).put("noreadnumm", unread);

		saveMsgList(name, content, time, id, headimg, 1, unread);
		myAdapter.notifyDataSetChanged();
	}

	@SuppressWarnings("unchecked")
	private void getLastMsgList() {
		MsgDataUtils msgDataUtils = new MsgDataUtils(this);
		msgDataBeans = (List<MsgDataBean>) msgDataUtils.queryData();
		if (null != msgDataBeans) {
			int length = msgDataBeans.size();
			for (int i = 0; i < length; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("isPerson", false);
				map.put("name", "" + msgDataBeans.get(i).getName());
				map.put("id", "" + msgDataBeans.get(i).getFriend_id());
				map.put("content", "" + msgDataBeans.get(i).getContent());
				map.put("time", "" + msgDataBeans.get(i).getTime());
				map.put("headimg", "" + msgDataBeans.get(i).getHead_img());
				Log.i(TAG, "头像:" + msgDataBeans.get(i).getHead_img());
				map.put("noreadnumm", msgDataBeans.get(i).getUnreadnum());
				map.put("_id", msgDataBeans.get(i).get_id());
				map.put("type", msgDataBeans.get(i).getType());
				listItem.add(map);
			}

			myAdapter.notifyDataSetChanged();

		}

	}

	private MsgDataUtils msgDataUtils = null;

	private void saveMsgList(String name, String content, String time,
			String id, String headimg, int type, int unread) {
		if (null == msgDataUtils) {
			msgDataUtils = new MsgDataUtils(this);
		}

		long m = msgDataUtils.update(name, id, content, headimg, time, type,
				unread);
		Log.i(TAG, "更新:" + m);
		if (m < 1) {
			m = msgDataUtils.insert(name, id, content, headimg, time, type,
					unread);
			Log.i(TAG, "插入" + m);
		}

	}

	private void delMsgList(String id) {
		if (null == msgDataUtils) {
			msgDataUtils = new MsgDataUtils(this);
		}

		long m = msgDataUtils.delete(id);
		Log.i(TAG, "删除:" + m);

	}

	private void saveMsgList(int index) {
		listItem.get(index).put("noreadnumm", 0);
		String name = listItem.get(index).get("name").toString();
		String content = listItem.get(index).get("name").toString();
		String headimg = listItem.get(index).get("name").toString();
		String time = listItem.get(index).get("name").toString();
		String id = listItem.get(index).get("id").toString();
		String typestr = listItem.get(index).get("type").toString();
		int type = 0;
		try {
			type = Integer.valueOf(typestr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (null == msgDataUtils) {
			msgDataUtils = new MsgDataUtils(this);
		}
		long m = msgDataUtils.update(name, id, content, headimg, time, type, 0);
		if (m < 1) {
			m = msgDataUtils.insert(name, id, content, headimg, time, type, 0);
		}

		myAdapter.notifyDataSetChanged();
	}

	private void addMsgList(String id) {
		FriendDataBean friendDataBean = FriendPage.getUserdata(id);
		String name = friendDataBean.getFriendname();

		Log.i(TAG, "名字" + name);
		String content = getLastChat(id);
		Log.i(TAG, "content" + content);
		String headimg = friendDataBean.getFriendhead();
		Log.i(TAG, "headimg" + headimg);
		String time = Chat.getDate();
		Log.i(TAG, "time" + time);

		if (TextUtils.isEmpty(name)) {
			try {
				List<FriendChatBean> list = (List<FriendChatBean>) new FriendChatUtils(
						MessagePage.this, id).queryData();
				for (int i = 0; i < list.size(); i++) {
					name = list.get(i).getName();
					if (!TextUtils.isEmpty(name)) {
						break;
					}
				}

				for (int i = 0; i < list.size(); i++) {
					headimg = list.get(i).getHeadimg();
					if (!TextUtils.isEmpty(headimg)) {
						break;
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isPerson", false);
		map.put("name", "" + name);
		map.put("id", "" + id);
		map.put("content", "" + content);
		map.put("time", "" + time);
		map.put("headimg", headimg);
		map.put("noreadnumm", 1);
		map.put("type", 0);
		listItem.add(map);
		saveMsgList(name, content, time, id, headimg, 0, 1);
		myAdapter.notifyDataSetChanged();

	}

	private void addMsg2List(String id) {
		FriendDataBean friendDataBean = FriendPage.getUserdata(id);
		String name = friendDataBean.getFriendname();

		Log.i(TAG, "名字" + name);
		String content = getLastChat(id);
		Log.i(TAG, "content" + content);
		String headimg = friendDataBean.getFriendhead();
		Log.i(TAG, "headimg" + headimg);
		String time = Chat.getDate();
		if (TextUtils.isEmpty(name)) {
			try {
				List<FriendChatBean> list = (List<FriendChatBean>) new FriendChatUtils(
						MessagePage.this, id).queryData();
				for (int i = 0; i < list.size(); i++) {
					name = list.get(i).getName();
					if (!TextUtils.isEmpty(name)) {
						break;
					}
				}
				for (int i = 0; i < list.size(); i++) {
					headimg = list.get(i).getHeadimg();
					if (!TextUtils.isEmpty(headimg)) {
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		Log.i(TAG, "time" + time);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isPerson", false);
		map.put("name", "" + name);
		map.put("id", "" + id);
		map.put("content", "" + content);
		map.put("time", "" + time);
		map.put("headimg", headimg);
		map.put("noreadnumm", 0);
		map.put("type", 0);
		listItem.add(map);
		saveMsgList(name, content, time, id, headimg, 0, 0);
		myAdapter.notifyDataSetChanged();

	}

	private void addMsgListGro(String gid) {
		GroupDataBean groupDataBean = MyGroup.getGroupData(gid);
		String name = groupDataBean.getGname();
		Log.i(TAG, "名字" + name);
		String content = getLastGroupChat(gid);
		Log.i(TAG, "content" + content);
		String headimg = groupDataBean.getHeadimage();
		Log.i(TAG, "headimg" + headimg);
		String time = Chat.getDate();
		Log.i(TAG, "time" + time);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isPerson", false);
		map.put("name", "" + name);
		map.put("id", "" + gid);
		map.put("content", "" + content);
		map.put("time", "" + time);
		map.put("headimg", headimg);
		map.put("noreadnumm", 1);
		map.put("type", 1);
		listItem.add(map);
		saveMsgList(name, content, time, gid, headimg, 1, 1);
		myAdapter.notifyDataSetChanged();

	}

	private void addMsg2ListGroup(String gid) {
		GroupDataBean groupDataBean = MyGroup.getGroupData(gid);
		String name = groupDataBean.getGname();
		Log.i(TAG, "名字" + name);
		String content = getLastGroupChat(gid);
		Log.i(TAG, "content" + content);
		String headimg = groupDataBean.getHeadimage();
		Log.i(TAG, "headimg" + headimg);
		String time = Chat.getDate();
		Log.i(TAG, "time" + time);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isPerson", false);
		map.put("name", "" + name);
		map.put("id", "" + gid);
		map.put("content", "" + content);
		map.put("time", "" + time);
		map.put("headimg", headimg);
		map.put("noreadnumm", 0);
		map.put("type", 1);
		listItem.add(map);
		saveMsgList(name, content, time, gid, headimg, 1, 0);
		myAdapter.notifyDataSetChanged();
	}

	@SuppressWarnings("unchecked")
	private String getLastChat(String sendid) {
		if (TextUtils.isEmpty(sendid)) {
			return "";
		}
		ProJectDatebase.createFriTab(sendid, this);
		FriendChatUtils friendChatUtils = new FriendChatUtils(this, sendid);
		List<FriendChatBean> friendChatBeans = (List<FriendChatBean>) friendChatUtils
				.queryData();
		if (null != friendChatBeans && friendChatBeans.size() > 0) {
			return friendChatBeans.get(0).getContent();
		}
		return "";

	}

	private String getLastGroupChat(String gid) {
		if (TextUtils.isEmpty(gid)) {
			return "";
		}

		ProJectDatebase.createGrouTable(gid, this);
		GroupChatUtils groupChatUtils = new GroupChatUtils(this, gid);
		@SuppressWarnings("unchecked")
		List<GroupChatBean> groupChatBeans = (List<GroupChatBean>) groupChatUtils
				.queryData();
		if (null == groupChatBeans || groupChatBeans.size() == 0) {
			return "";
		}
		return groupChatBeans.get(0).getContent();
	}

	private void getGroupRequestNum() {

		MyHttpClient.verifyMember2Group_(LoginActivity.id, handler);
	}

	JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(response);
			AppLog.i(TAG, "群组返回:"+response);
			getgroupCount(response);
		}
	};

	private void getgroupCount(JSONObject response) {
		try {
			int count = response.getJSONObject("gu").getInt("totalrecord");
			addgroup_Notice.setText("" + count);
			AppLog.i(TAG, "群组返回:"+count);
			if (count > 0) {
				addgroup_Notice.setVisibility(View.VISIBLE);
			} else {
				addgroup_Notice.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			AppLog.w(TAG, "群组返回:"+e.getStackTrace());
		}
	}

}
