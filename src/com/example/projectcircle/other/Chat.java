package com.example.projectcircle.other;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.ChatAdapter;
import com.example.projectcircle.adpter.Utils;
import com.example.projectcircle.bean.FriendChatBean;
import com.example.projectcircle.bean.MsgDataBean;
import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.utils.FriendChatUtils;
import com.example.projectcircle.db.utils.MsgDataUtils;
import com.example.projectcircle.util.BingDateUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Chat extends Activity {

	private static final String TAG = Chat.class.getSimpleName();
	Button back, search, send;
	ImageView window;
	private String userid;
	private String sendid;
	EditText content1;
	private String content;
	private ArrayList<ChatMsgEntity> chatMsgList;
	private ListView chatMsgShowList;
	private String message;
	public static boolean isLive = false;

	String uname, utype, ucity, udevice, ucontent, uheadimg, accept, info, uid,
			age, group, gname;

	private TextView titleTextView;

	public static String chatId = "";

	private List<FriendChatBean> friendChatBeans = new ArrayList<FriendChatBean>();
	
	private static final long TIME_DISTANCE=6000*10;
	/**
	 * 好友信息
	 */
	private MyPersonBean myPersonBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isLive = true;
		setContentView(R.layout.chat_window);

		titleTextView = (TextView) findViewById(R.id.chat_now);//聊天窗口最顶部的，和谁聊天就显示谁的名字

		userid = LoginActivity.id;
		Intent intent = getIntent();
		sendid = intent.getStringExtra("id");
		uname = intent.getStringExtra("username");
		uheadimg = MyHttpClient.IMAGE_URL + intent.getStringExtra("headimg");
		
		content1 = (EditText) findViewById(R.id.chat_content);
		initBtn();
		chatMsgList = new ArrayList<ChatMsgEntity>();
		@SuppressWarnings("unused")
		ChatMsgEntity chatMsgEntity = new ChatMsgEntity();

		Intent intent1 = getIntent();

		Log.i(TAG, "intent=" + intent1);
		if (intent1 != null && !TextUtils.isEmpty(Utils.response)) {

			// String idcode = intent.getStringExtra("idcode");
			// System.out.println("idcode=======================!!!!!!!!!"
			// + idcode);
			// chatMsgEntity.setText(idcode);
			// chatMsgList = getLastChat();
			String date = getDate();
			String str = intent1.getStringExtra("msg");
			if (TextUtils.isEmpty(str)) {
				str = Utils.response;
			}
			int mlength = Utils.loginList.size() - 1;
			for (int i = mlength; i >= 0; i--) {
				ChatMsgEntity loginchatMsgEntity = new ChatMsgEntity();
				if (i == mlength) {
					loginchatMsgEntity.setDate(date);
				} else {
					loginchatMsgEntity.setDate("");
				}
				loginchatMsgEntity.setText(Utils.loginList.get(i));
				loginchatMsgEntity.setMsgType(true);
				chatMsgList.add(loginchatMsgEntity);
			}
			// chatMsgEntity.setDate(date);
			// chatMsgEntity.setText(str);
			// chatMsgEntity.setMsgType(true);
			// chatMsgList.add(chatMsgEntity);
			Utils.response = null;
			Utils.loginList.clear();
			saveMsg(str, date);
		} else {
			// initLastMsg(chatMsgEntity);
			// chatMsgList = getLastChat();
		}
		chatMsgShowList = (ListView) findViewById(R.id.msg_listView);
		chatAdapter = new ChatAdapter(this, chatMsgList);

		chatMsgShowList.setAdapter(chatAdapter);
		
		
		getLastChat();
		// Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
		// 这里把apikey存放于manifest文件中，只是一种存放方式，
		// 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
		// "api_key")
		// 通过share preference实现的绑定标志开关，如果已经成功绑定，就取消这次绑定

		// Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
		// 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
		// 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
		// CustomPushNotificationBuilder cBuilder = new
		// CustomPushNotificationBuilder(
		// getApplicationContext(), resource.getIdentifier(
		// "notification_custom_builder", "layout", pkgName),
		// resource.getIdentifier("notification_icon", "id", pkgName),
		// resource.getIdentifier("notification_title", "id", pkgName),
		// resource.getIdentifier("notification_text", "id", pkgName));
		// cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
		// cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND
		// | Notification.DEFAULT_VIBRATE);
		// cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
		// cBuilder.setLayoutDrawable(resource.getIdentifier(
		// "simple_notification_icon", "drawable", pkgName));
		// PushManager.setNotificationBuilder(this, 1, cBuilder);
		// 将百度推送过来的消息放到adapter中
		// receive();

		initFilter();

		if (TextUtils.isEmpty(sendid)) {
			getReceivie();

		} else {
			chatId = sendid;
			titleTextView.setText("" + uname);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isLive = false;
		chatId = "";
		unregisterReceiver(getMsgReceiver);
	}

	private void receive() {
		// TODO Auto-generated method stub
		Intent intent2 = getIntent();
		message = intent2.getStringExtra("message");
		if (message != null && message.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setMsgType(false);
			entity.setText(message);
			// HealthHttpClient.doHttpPostMessage(BApplication.getPersonInfo()
			// .getId(), contString, handler);
			chatMsgList.add(entity);
			chatAdapter.notifyDataSetChanged();

			content1.setText("");

			chatMsgShowList.setSelection(chatMsgShowList.getCount() - 1);
		}
	}

	/**
	 * 设置标签
	 * 
	 * @param tag
	 */
	// private void setTag(String tag) {
	// if (!Utils.hasBind(getApplicationContext())) {
	// PushManager.startWork(getApplicationContext(),
	// PushConstants.LOGIN_TYPE_API_KEY,
	// Utils.getMetaValue(LoginActivity.this, "api_key"));
	// // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
	// PushManager.enableLbs(getApplicationContext());
	// }
	// List<String> tags = Utils.getTagsList(tag);
	// PushManager.setTags(getApplicationContext(), tags);
	//
	// }
	private void saveMsg(String msg, String time) {
		// TODO Auto-generated method stub
		SharedPreferences sharedPreferences = getSharedPreferences(TAG,
				Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("msg", msg);
		editor.putString("time", time);
		editor.commit();
	}

	public static String getDate() {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(hour + ":" + mins);
		// return sbBuffer.toString();
		return BingDateUtils.getDateStr(c.getTime());
	}

	// private ArrayList<ChatMsgEntity> getLastChat() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.chat_now_back);
//		search = (Button) findViewById(R.id.chat_right);
		send = (Button) findViewById(R.id.chat_send);
		// window = (ImageView) findViewById(R.id.chat_qie);
		//
		back.setOnClickListener(listener);
//		search.setOnClickListener(listener);
		send.setOnClickListener(listener);
		// window.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.chat_now_back:
				// Intent intent = new Intent(Chat.this, MainActivity.class);
				// startActivity(intent);
				finish();
				break;
//			case R.id.chat_right:
//				Intent intent1 = new Intent(Chat.this, SearchPage.class);
//				startActivity(intent1);

//				break;
			case R.id.chat_send:
				content = content1.getText().toString();
				send();
				break;
			case R.id.chat_qie:

				break;
			}
		}
	};
	private ListView listview;
	private ChatAdapter chatAdapter;

	private void postMessage(String userid, String sendid, String content,
			final int postion) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", response);
				// parsefindfriend(response);
				// initList();
				chatMsgList.get(postion).setSend_state(true);
				save(chatMsgList.get(postion));
				chatAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
				chatMsgList.get(postion).setSend_state(true);
				chatMsgList.remove(postion);
				chatAdapter.notifyDataSetChanged();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.postMessages(userid, sendid, content, res);
	}

	// private void initList() {
	// // TODO Auto-generated method stub
	// listview = (ListView) findViewById(R.id.msg_listView);
	// chatAdapter = new ChatAdapter(null, chatMsgList);
	// listview.setAdapter(chatAdapter);
	// }

	private ArrayList<HashMap<String, Object>> getList_Message() {
		// TODO Auto-generated method stub
		return null;
	}

	private void send() {
		String contString = content1.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			
			
			if (chatMsgList.size()>0) {
				long time=chatMsgList.get(chatMsgList.size()-1).getTime();
				if (0<time&&(System.currentTimeMillis()-time)<TIME_DISTANCE) {
					entity.setDate("");
				}else {
					entity.setDate(getDate());
				}
			}else {
				entity.setDate(getDate());
			}
			
			
			
			entity.setMsgType(false);
			entity.setText(contString);
			entity.setTime(System.currentTimeMillis());
			// HealthHttpClient.doHttpPostMessage(BApplication.getPersonInfo()
			// .getId(), contString, handler);
			// postMessage(userid,sendid,contString);

			chatMsgList.add(entity);
			chatAdapter.notifyDataSetChanged();
			postMessage(sendid, userid, contString, chatMsgList.size() - 1);
			content1.setText("");

			chatMsgShowList.setSelection(chatMsgShowList.getCount() - 1);
		}
	}

	private BroadcastReceiver getMsgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String json = intent.getStringExtra("msg");
			String msg = parseJson(json);
			getMsg2View(msg);
		}
	};

	private void getReceivie() {
		String json = getIntent().getStringExtra("msg");
		if (json == null) {
			return;
		}
		String msg = parseJson(json);
		if (TextUtils.isEmpty(sendid)) {
			chatId = sendid;
			UserDetail(sendid, msg);
		}

	}

	/**
	 * 是否当前聊天
	 * 
	 * @param json
	 * @return
	 */
	public static int isSameId(String json) {
		int flag = 0;
		String id = parseJsonMsg(json);

		if (TextUtils.isEmpty(chatId)) {
			flag = 0;
		}

		if (!TextUtils.isEmpty(id)) {
			if (!id.equals(chatId)) {
				flag = 1;
				Log.i(TAG, "kk" + id);
			}
		} else {
			flag = 1;
		}

		return flag;

	}

	private static String parseJsonMsg(String json) {
		String msg = "";
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONObject content = jsonObject.getJSONObject("content");
			msg = content.getString("content");
			msg = content.getString("senduid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	private String parseJson(String json) {
		String msg = "";
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONObject content = jsonObject.getJSONObject("content");
			msg = content.getString("content");
			sendid = content.getString("senduid");
			myPersonBean=new Gson().fromJson(jsonObject.getString("user"), MyPersonBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	private void getMsg2View(String msg) {
		Log.i(TAG, "消息" + msg);
		if (myPersonBean!=null) {
			uheadimg=MyHttpClient.IMAGE_URL+myPersonBean.getHeadimage();
		}
		ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
		chatMsgEntity.setDate(getDate());
		chatMsgEntity.setMsgType(true);
		chatMsgEntity.setText(msg);
		chatMsgEntity.setHeadimgString(uheadimg);
		chatMsgEntity.setTime(System.currentTimeMillis());
		
		if (chatMsgList.size()>0) {
			long time=chatMsgList.get(chatMsgList.size()-1).getTime();
			Log.i(TAG, "时差:"+((System.currentTimeMillis()-time)/1000)+"s");
			if (0<time&&(System.currentTimeMillis()-time)<TIME_DISTANCE) {
				chatMsgEntity.setDate("");
			}else {
				chatMsgEntity.setDate(getDate());
			}
		}else {
			chatMsgEntity.setDate(getDate());
		}
		
		
		chatMsgList.add(chatMsgEntity);
		chatAdapter.notifyDataSetChanged();
		chatMsgShowList.setSelection(chatMsgShowList.getCount() - 1);
		save(chatMsgEntity);
	}

	private void initFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ContantS.ACTION_GET_MSG);
		registerReceiver(getMsgReceiver, filter);
	}

	/**
	 * 个人信息 请求和解析
	 * 
	 */
	private void UserDetail(String id, final String msg) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "返回:" + response);
				parseUserDetail(response);
				getMsg2View(msg);
				chatAdapter.notifyDataSetChanged();

			}
		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail(id, res);
	}

	public void parseUserDetail(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");
			uid = obj.getString("id");
			uname = obj.getString("username");
			utype = obj.getString("type");
			ucity = obj.getString("address");
			udevice = obj.getString("equipment");
			ucontent = obj.getString("sign");
			uheadimg = MyHttpClient.IMAGE_URL + obj.getString("headimage");
			accept = obj.getString("accept");
			// Log.i("accept", accept);
			info = obj.getString("info");
			age = obj.getString("age");

			titleTextView.setText("" + uname);

			// Log.i("read", uname);
			// Log.i("read", utype);
			// Log.i("read", ucity);
			// Log.i("read", udevice);
			// Log.i("read", uheadimg);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void parseUserDetailO(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");
			uid = obj.getString("id");

			if (!sendid.equals(uid)) {

			}

			// uname = obj.getString("username");
			// utype = obj.getString("type");
			// ucity = obj.getString("address");
			// udevice = obj.getString("equipment");
			// ucontent = obj.getString("sign");
			// uheadimg = MyHttpClient.IMAGE_URL +obj.getString("headimage");
			// accept = obj.getString("accept");
			// // Log.i("accept", accept);
			// info = obj.getString("info");
			// age = obj.getString("age");

			// titleTextView.setText(""+uname);

			// Log.i("read", uname);
			// Log.i("read", utype);
			// Log.i("read", ucity);
			// Log.i("read", udevice);
			// Log.i("read", uheadimg);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void getLastChat() {
		if (TextUtils.isEmpty(sendid)) {
			return;
		}
		ProJectDatebase.createFriTab(sendid, this);
		FriendChatUtils friendChatUtils = new FriendChatUtils(this, sendid);
		friendChatBeans = (List<FriendChatBean>) friendChatUtils.queryData();
		if (null != friendChatBeans) {
			int length = friendChatBeans.size();
			for (int i = length-1; i >-1; i--) {
				ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
				chatMsgEntity.setDate(friendChatBeans.get(i).getShowTime());
				chatMsgEntity.setHeadimgString(uheadimg);
				chatMsgEntity.setText(friendChatBeans.get(i).getContent());
				chatMsgEntity.setSend_state(true);
				switch (friendChatBeans.get(i).getIscom()) {
				case 0:
					chatMsgEntity.setMsgType(false);
					break;

				case 1:
					chatMsgEntity.setMsgType(true);
					break;

				default:
					break;
				}
				
				chatMsgList.add(chatMsgEntity);
			}
		}
		
		chatAdapter.notifyDataSetChanged();
		
	}
	
	
	private void save(ChatMsgEntity chatMsgEntity){
		ProJectDatebase.createFriTab(sendid, this);
		FriendChatUtils friendChatUtils = new FriendChatUtils(this, sendid);
		int iscom=0;
		if (chatMsgEntity.getMsgType()) {
			iscom=1;
		}
		friendChatUtils.insert(chatMsgEntity.getText(), chatMsgEntity.getDate(), chatMsgEntity.getDate(), iscom);
		
	}
	
	
	
	

}
