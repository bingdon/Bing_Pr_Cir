package com.example.projectcircle.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.FriendRequestAdapter;
import com.example.projectcircle.adpter.MsgAdapter;
import com.example.projectcircle.adpter.NewContAdapter;
import com.example.projectcircle.adpter.NewContAdapter.HandlerListener;
import com.example.projectcircle.bean.NewConstactBean;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.friend.GroupChat;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FriendRequest extends Activity implements HandlerListener {
	private static final String TAG = null;
	private String bid;
	private ArrayList<UserInfo> friendRequestList;
	private ListView listview;
	private FriendRequestAdapter myAdapter;
	private Button button_back;
	private List<NewConstactBean> rConstactBeans = new ArrayList<>();
	private NewContAdapter newContAdapter;
	private Context context;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.request_message);
		context = this;
		bid = LoginActivity.id;
		newContAdapter = new NewContAdapter(rConstactBeans, context);
		listview = (ListView) findViewById(R.id.message_listView);
		listview.setAdapter(newContAdapter);
		FriendRequestList(bid);
		back();
		newContAdapter.setListener(this);
	}

	private void FriendRequestList(String bid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", "好友请求:" + response);
				// parseFriendRequestList(response);
				parseFriendRequestList2(response);
				// initList();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.FriendRequestMessage(bid, res);
	}

	/**
	 * @deprecated
	 * @param response
	 */
	private void parseFriendRequestList(String response) {
		// TODO Auto-generated method stub
		try {
			Log.i("解析好友请求的response", response + "");
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("friends");
			friendRequestList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			Log.i(TAG, "JSONArray:" + json);
			for (int i = 0; i < length; i++) {
				UserInfo user = new UserInfo();
				JSONObject objo = json.getJSONObject(i);
				Log.i(TAG, "objo:" + objo);
				user.setId(objo.getString("cid"));
				user.setUsername(objo.getString("username"));
				user.setInfo(objo.getString("info"));
				friendRequestList.add(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void parseFriendRequestList2(String response) {
		// TODO Auto-generated method stub
		try {
			Log.i("解析好友请求的response", response + "");
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("friends");
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			Log.i(TAG, "JSONArray:" + json);
			for (int i = 0; i < length; i++) {
				try {
					JSONObject objo = json.getJSONObject(i);
					NewConstactBean constactBean = new Gson().fromJson(
							objo.toString(), NewConstactBean.class);
					constactBean.setType_(1);
					rConstactBeans.add(constactBean);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			newContAdapter.notifyDataSetChanged();

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.message_listView);
		myAdapter = new FriendRequestAdapter(getList(), this);
		listview.setAdapter(myAdapter);
	}

	private ArrayList<HashMap<String, Object>> getList() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		Log.i(TAG, "返回:friendList" + friendRequestList);
		if (friendRequestList != null) {
			for (int i = 0; i < friendRequestList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("friendID", friendList.get(i).getId());
				map.put("cid", friendRequestList.get(i).getId());
				map.put("info", friendRequestList.get(i).getInfo());
				// map.put("headimage", friendList.get(i).getHeadimage());
				map.put("username", friendRequestList.get(i).getUsername());
				// map.put("tel", friendList.get(i).getTel());
				// map.put("accept", friendList.get(i).getAccept());
				listItem.add(map);
			}
		}
		return listItem;
	}

	private void back() {
		// TODO Auto-generated method stub
		button_back = (Button) findViewById(R.id.friend_request_back);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				FriendRequest.this.setResult(RESULT_OK, getIntent());
				FriendRequest.this.finish();
			}
		});
	}

	@Override
	public void addPeople(int position) {
		// TODO Auto-generated method stub
		NewConstactBean newConstactBean = rConstactBeans.get(position);
		befriend(newConstactBean.getId(), position);
	}

	private void befriend(String accept_id, final int position) {
		// TODO Auto-generated method stub
		AppLog.i("FriendRequestAdapter", "接收:" + accept_id);
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", "同意结果" + response);
				// parseFriendRequestList(response);
				// initList();
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						Toast.makeText(context, "添加成功，对方已经是您的好友！",
								Toast.LENGTH_SHORT).show();
						rConstactBeans.get(position).setIsAccpet(1);
						newContAdapter.notifyDataSetChanged();
					} else {
						Toast.makeText(context, "添加失败！", Toast.LENGTH_SHORT)
								.show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastUtils.showShort(context, getString(R.string.erro_no));
				}

			}

		};
		MyHttpClient client = new MyHttpClient();
		client.beFriends(accept_id, res);
	}

}
