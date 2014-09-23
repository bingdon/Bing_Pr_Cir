package com.example.projectcircle.friend;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.FriendAdapter;
import com.example.projectcircle.bean.FriendDataBean;
import com.example.projectcircle.bean.GroupChatBean;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.db.utils.FriendDatabaseUtils;
import com.example.projectcircle.db.utils.GroupChatUtils;
import com.example.projectcircle.db.utils.NewContactsUtily;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.friend.utils.FriendRequestUtils;
import com.example.projectcircle.group.GroupPage;
import com.example.projectcircle.group.MyGroup;
import com.example.projectcircle.other.Chat;
import com.example.projectcircle.personal.PersonalPage;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ShareUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class FriendPage extends Activity {
	private static final String TAG = FriendPage.class.getSimpleName();
	// private static final String TAG = "FriendPage";
	ListView listview, listview2;
	FriendAdapter myAdapter;
	FriendAdapter myAdapter2;

	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };

	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;

	/** 头像ID **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;

	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;

	/** 联系人名称 **/
	private ArrayList<String> mContactsName = new ArrayList<String>();

	/** 联系人号码 **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	/** 联系人头像 **/
	private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();
	/**
	 * 顶部Button
	 */
	Button add, new_, maybe, group;
	/**
	 * 搜索Button
	 */
	Button search;
	LinearLayout groupchat, addfriend, tellfriend;

	boolean isRefreshing = false;
	View headview2;
	LinearLayout group2;
	LinearLayout friend;
	protected Activity context;
	protected LayoutInflater anchor;
	public ArrayList<UserInfo> friendList;
	LayoutInflater inflater = null;
	PopupWindow mPop;
	String id;

	public ArrayList<UserInfo> usersList;
	// 对话框参数
	Context self = FriendPage.this;
	private friendBroadcastReceiver receiver;
	ArrayList<HashMap<String, Object>> listItem;
	private Object deny_id;
	private Object cid;

	private ArrayList<String> tel;
	String msg_id;// 刚注册成功时，进入登录界面，广播收到的id
	private String now_number;
	public static List<FriendDataBean> friendDataBeans = new ArrayList<FriendDataBean>();
	private JSONArray json1;
	private JSONArray json2;
	private JSONArray json3;
	private TextView tipNotice;
	private int new_friend_tip_count = 0;// 通讯录中刚注册并不是好友的个数
	private String mphoneNumber;
	/**
	 * 好友请求数量
	 */
	private int totalrecord = 0;

	// private IWXAPI api;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_page);
		context = this;

		// api = WXAPIFactory.createWXAPI(this, ContantS.WX_APP_ID,false);
		// api.registerApp(ContantS.WX_APP_ID);
		// AppLog.i(TAG, "注册:"+api.registerApp(ContantS.WX_APP_ID));
		// 删除好友、加好友的监听广播
		initFilter();
		id = LoginActivity.id;
		initList();
		initSocial();
		initPopWindow();
		// 获取通讯录联系人，并判断是否为好友，以便像微信那样出来那种提示
		FriendRequestUtils.requestNum();
		getContactInNumber();
		if (null != myAdapter) {
			findfriend(id);
		}
		// api.handleIntent(getIntent(), this);
	}

	
	
	private void getContactInNumber() {
		// TODO Auto-generated method stub
		ContentResolver resolver = FriendPage.this.getContentResolver();

		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				// 得到联系人ID
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
				// 得到联系人头像ID
				Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
				// 得到联系人头像Bitamp
				Bitmap contactPhoto = null;
				// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
				if (photoid > 0) {
					Uri uri = ContentUris.withAppendedId(
							ContactsContract.Contacts.CONTENT_URI, contactid);
					InputStream input = ContactsContract.Contacts
							.openContactPhotoInputStream(resolver, uri);
					contactPhoto = BitmapFactory.decodeStream(input);
				} else {
					contactPhoto = BitmapFactory.decodeResource(getResources(),
							R.drawable.driver);
				}
				mphoneNumber = phoneNumber.replace(" ", ""); // 得到的电话号码有空格，去掉它
				if (mphoneNumber.length() > 11) {
					mphoneNumber = mphoneNumber.substring(
							mphoneNumber.length() - 11, mphoneNumber.length());
				}// 截取手机号的后11位，因为存的有的手机号开头带有+86，把它去掉
				mContactsName.add(contactName);
				mContactsNumber.add(mphoneNumber);
				mContactsPhonto.add(contactPhoto);
			}

			phoneCursor.close();
		}
		now_number = mContactsNumber.toString().substring(1,
				mContactsNumber.toString().length() - 1);// 去掉ArrayList中的【和】这个再开头和结尾有，要不后台识别不出来
		now_number = now_number.replace(" ", "");// 去掉所有空格，要不后台识别不出来
		isRegist(now_number, id);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		findfriend(id);
		getFriendRequest();
	}

	// 判断通讯录中联系人是否注册
	private void isRegist(String now_number, String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				// Log.i("电话号码是否注册response---------", response);
				try {
					JSONObject obj = new JSONObject(response);
					json1 = obj.getJSONArray("1");// 键值是1的，没有注册的
					json2 = obj.getJSONArray("2");// 键值是2的，注册并且是好友
					json3 = obj.getJSONArray("3");// 键值是3的，注册但不是好友
					Log.i(TAG, "大小:" + mContactsNumber.size());
					for (int i = 0; i < mContactsNumber.size(); i++) {
						// Log.i(TAG, "键值是1的，没有注册的"+isIn(mContactsNumber.get(i),
						// json1));
						// Log.i(TAG,
						// "键值是2的，注册并且是好友"+isIn(mContactsNumber.get(i), json2));
						// Log.i(TAG,
						// "键值是3的，注册但不是好友"+isIn(mContactsNumber.get(i), json3));
						if (isIn(mContactsNumber.get(i), json3)) {
							new_friend_tip_count++;
						}
					}
					int size = 0;
					try {
						size = getContactFriendNum();
					} catch (Exception e) {
						// TODO: handle exception
					}
					int m = (totalrecord-getRequestFriendNum()) + new_friend_tip_count
							- size;
					setContactFriendNum(new_friend_tip_count);
					if (m > 0) {
						tipNotice.setText(m + "");// 有几个
						tipNotice.setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.contactIsRegiest(now_number, id, res);

	}

	// 判断字符串数组中是否含有某一字符串
	private static boolean isIn(String substring, JSONArray source)
			throws JSONException {
		if (source == null || source.length() == 0) {
			return false;
		}
		int length = source.length();
		for (int i = 0; i < length; i++) {
			String aSource = (String) source.get(i);
			Log.i(TAG,
					"号码:" + substring + "::" + aSource + "::"
							+ (aSource.equals(substring)));
			if (aSource.equals(substring)) {
				// Log.i(TAG, "asource:" + aSource);
				return true;
			}
		}
		return false;
	}

	private void findfriend(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("我的好友列表response", response);
				parsefindfriend(response);
				listItem.clear();
				listItem = getList_friend();

				myAdapter = new FriendAdapter(listItem, FriendPage.this);
				myAdapter.notifyDataSetChanged();
				listview.setAdapter(myAdapter);
				Log.i(TAG, "更新:" + myAdapter.getCount());
				getFriInfo(FriendPage.this);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1, arg2, arg3);
				Log.i("failuer ", "failuer");
				getFriInfo(FriendPage.this);
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.findfriend(id, res);
	}

	private void parsefindfriend(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("friends");
			friendList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			Log.i(TAG, "JSONArray:" + json);
//			new FriendDatabaseUtils(FriendPage.this).deleteAll();
			for (int i = 0; i < length; i++) {
				UserInfo user = new UserInfo();
				JSONObject objo = json.getJSONObject(i);
				Log.i("好友列表", "objo:" + objo);
				user.setId(objo.getString("id"));
				user.setTel(objo.getString("tel"));
				user.setUsername(objo.getString("username"));
				user.setType(objo.getString("type"));
				user.setAddress(objo.getString("address"));
				user.setCcid(objo.getString("ccid"));
				user.setEquipment(objo.getString("equipment"));
				user.setSign(objo.getString("sign"));
				user.setHeadimage(objo.getString("headimage"));
				user.setAccept(objo.getString("accept"));
				user.setLat(Double.valueOf(objo.getString("commercialLat")));
				user.setLon(Double.valueOf(objo.getString("commercialLon")));
				user.setLastlogintime(objo.getString("lastlogintime"));
				friendList.add(user);
				saveFriendinfo(user.getUsername(), user.getId(),
						user.getHeadimage());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initPopWindow() {
		// TODO Auto-generated method stub
		search = (Button) findViewById(R.id.f_page_right);
		search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

				ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
						R.layout.popwindow, null, true);
				mPop = new PopupWindow(menuView, LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, true);
				mPop.setAnimationStyle(R.style.AnimationPreview);
				mPop.setOutsideTouchable(true);
				mPop.setFocusable(true);
				// mPop.setWidth(200);
				// mPop.setHeight(300);
				ColorDrawable dr = new ColorDrawable(getResources().getColor(
						R.color.transparent));
				mPop.setBackgroundDrawable(dr);
				RelativeLayout upfriend = (RelativeLayout) findViewById(R.id.add_friend_Layout1);
				mPop.showAsDropDown(upfriend, 420, 0);// 设置显示PopupWindow的位置位于View的左下方，x,y表示坐标偏移量
				mPop.showAtLocation(findViewById(R.id.textView1), Gravity.LEFT,
						0, 0);// （以某个View为参考）,表示弹出窗口以parent组件为参考，位于左侧，偏移-90。
				groupchat = (LinearLayout) menuView
						.findViewById(R.id.groupchat);
				addfriend = (LinearLayout) menuView
						.findViewById(R.id.addfriend);
				tellfriend = (LinearLayout) menuView
						.findViewById(R.id.tellfriend);
				groupchat.setVisibility(View.GONE);
				// groupchat.setOnClickListener(listener3);
				addfriend.setOnClickListener(listener3);
				tellfriend.setOnClickListener(listener3);
			}
		});

	}

	private View.OnClickListener listener3 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// case R.id.groupchat:
			// Intent intent = new Intent(FriendPage.this, GroupChat.class);
			// startActivity(intent);
			// mPop.dismiss();
			// break;
			case R.id.addfriend:
				Intent intent1 = new Intent(FriendPage.this, AddFriend.class);
				startActivity(intent1);
				mPop.dismiss();
				break;
			case R.id.tellfriend:
				// ShareUtils.shareWx(FriendPage.this, api);
				addWXPlatform();
				// Intent intent11 = new Intent(Intent.ACTION_SEND);
				// intent11.setType("text/plain");
				// intent11.putExtra(Intent.EXTRA_SUBJECT, "分享");
				// intent11.putExtra(
				// Intent.EXTRA_TEXT,
				// "我在使用“工程圈”结交全国同行好友，“工程圈”专为工程机械行业机主、司机、商家设计的手机社交软件，我的工程号是XXX，记得注册后加我为好友。下载地址：www.gcquan.cn");
				// startActivity(Intent.createChooser(intent11, getTitle()));
				mPop.dismiss();
				break;
			}
		}
	};

	// 位置计算

	// private void locationCal() {
	// // TODO Auto-generated method stub
	// Double distance = DistentsUtil.DistanceOfTwoPoints(friendlat, friendlon,
	// mylat, mylon) / 1000;
	// }
	private void initList() {
		// TODO Auto-generated method stub
		getList_friend();
		listview = (ListView) findViewById(R.id.f_page_listView);
		if (headview2 == null) {
			headview2 = this.getLayoutInflater().inflate(R.layout.headview2,
					null);
			listview.addHeaderView(headview2);
		}
		tipNotice = (TextView) findViewById(R.id.new_friend_notice);
		friend = (LinearLayout) findViewById(R.id.newfriend_layout);
		friend.setOnClickListener(listener1);
		group2 = (LinearLayout) findViewById(R.id.neargroup_layout);
		group2.setOnClickListener(listener2);

		myAdapter = new FriendAdapter(listItem, this);
		listview.setAdapter(myAdapter);
		// 短按listView
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FriendPage.this, PersonalPage.class);
				intent.putExtra("id", friendList.get(arg2 - 1).getId());
				intent.putExtra("type", friendList.get(arg2 - 1).getType());
				intent.putExtra("ccid", friendList.get(arg2 - 1).getCcid());
				intent.putExtra("time", friendList.get(arg2 - 1)
						.getLastlogintime());
				intent.putExtra("lat", friendList.get(arg2 - 1).getLat());
				intent.putExtra("lon", friendList.get(arg2 - 1).getLon());
				startActivity(intent);
			}
		});
		// 长按listview
//		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view,
//					final int position, long id) {
//				// TODO Auto-generated method stub
//				new AlertDialog.Builder(self)
//						.setTitle("确认要删除此好友吗？")
//						.setIcon(android.R.drawable.ic_dialog_info)
//						.setPositiveButton("确定",
//								new DialogInterface.OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										// TODO Auto-generated method stub
//										// 按确定后删除
//										cid = listItem.get(position - 1).get(
//												"ccid");
//										denyfriend(cid, position);
//									}
//								}).setNegativeButton("取消", null).show();
//				return true;
//			}
//
//		});
	}

	// 调用删除好友的接口，和好友请求列表里边的拒绝好友是一个接口
	private void denyfriend(Object cid, final int position) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", response);
				// parseFriendRequestList(response);
				// initList();
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						Toast.makeText(FriendPage.this, "删除成功！",
								Toast.LENGTH_SHORT).show();
						// //删除成功后就把相应的listItem删掉
						if (listItem != null && listItem.size() > 0) {
							listItem.remove(position - 1);
						}
						myAdapter.notifyDataSetChanged();
					} else {
						Toast.makeText(context, "删除失败！", Toast.LENGTH_SHORT)
								.show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		MyHttpClient client = new MyHttpClient();
		client.denyFriends(cid, res);
	}

	private View.OnClickListener listener1 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent(FriendPage.this, NewFriendActivity.class);
			tipNotice.setVisibility(View.INVISIBLE);
			setContactFriendNum(new_friend_tip_count);
			setRequestFriendNum(totalrecord);
			getTel();
			intent.putStringArrayListExtra("tels", tel);
			startActivity(intent);
			// finish();

		}
	};
	private View.OnClickListener listener2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent(FriendPage.this, MyGroup.class);
			startActivity(intent);
			// finish();

		}
	};

	private ArrayList<HashMap<String, Object>> getList_friend() {
		listItem = new ArrayList<HashMap<String, Object>>();

		// TODO Auto-generated method stub
		Log.i("返回:friendList", "返回:friendList" + friendList);
		if (friendList != null) {
			for (int i = 0; i < friendList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("friendID", friendList.get(i).getId());
				map.put("address", friendList.get(i).getAddress());
				map.put("type", friendList.get(i).getType());
				map.put("headimage", friendList.get(i).getHeadimage());
				map.put("username", friendList.get(i).getUsername());
				map.put("tel", friendList.get(i).getTel());
				map.put("accept", friendList.get(i).getAccept());
				map.put("equipment", friendList.get(i).getEquipment());
				map.put("ccid", friendList.get(i).getCcid());
				map.put("sign", friendList.get(i).getSign());
				map.put("lastlogintime", friendList.get(i).getLastlogintime());
				map.put("commercialLat", friendList.get(i).getLat());
				map.put("commercialLon", friendList.get(i).getLon());
				listItem.add(map);
			}
		}
		return listItem;
	}

	// 广播
	private class friendBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			msg_id = arg1.getExtras().getString("id");
			Log.i("看看广播有没有成功啦", "msg:" + msg_id);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
		unregisterReceiver(msgReceiver);
		if (null != friendDatabaseUtils) {
			friendDatabaseUtils.close();
		}

	}

	private void getTel() {
		if (friendList != null) {
			tel = new ArrayList<String>();
			for (int i = 0; i < friendList.size(); i++) {
				tel.add(friendList.get(i).getTel());
			}
		}
	}

	FriendDatabaseUtils friendDatabaseUtils = null;

	private void saveFriendinfo(String name, String friid, String headimg) {
		if (friendDatabaseUtils == null) {
			friendDatabaseUtils = new FriendDatabaseUtils(this);
		}

		long m = friendDatabaseUtils.update(name, friid, headimg, friid);
		Log.i(TAG, "更新:" + m);
		if (m < 1) {
			m = friendDatabaseUtils.insert(name, friid, headimg);
			Log.i(TAG, "插入:" + m);
		}

	}

	@SuppressWarnings("unchecked")
	public static void getFriInfo(Context context) {
		friendDataBeans = (List<FriendDataBean>) new FriendDatabaseUtils(
				context).queryData();
		Log.i("friendDataBeans", "" + friendDataBeans);
	}

	/**
	 * 判断是否为好友
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isFriend(String id) {
		boolean a = false;
		if (null == friendDataBeans) {
			return a;
		}
		for (int i = 0; i < friendDataBeans.size(); i++) {
			if (friendDataBeans.get(i).getFriendid().equals(id)) {
				a = true;
				break;
			}
		}

		return a;
	}

	public static String getUsername(String friid) {
		String name = "工程圈";
		if (null == friendDataBeans) {
			return name;
		}
		for (int i = 0; i < friendDataBeans.size(); i++) {
			if (friendDataBeans.get(i).getFriendid().equals(friid)) {
				name = friendDataBeans.get(i).getFriendname();
				break;
			}
		}

		return name;

	}

	public static FriendDataBean getUserdata(String friid) {
		FriendDataBean friendDataBean = new FriendDataBean();
		if (null == friendDataBeans) {
			return null;
		}
		for (int i = 0; i < friendDataBeans.size(); i++) {
			if (friendDataBeans.get(i).getFriendid().equals(friid)) {
				friendDataBean = friendDataBeans.get(i);
				break;
			}
		}

		return friendDataBean;

	}

	private void initFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("shan.chu.hao.you");
		filter.addAction("add.a.new.friend");
		registerReceiver(msgReceiver, filter);
		Log.i("initFilter", "initFilter()");
	}

	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i("广播开始", "msgReceiver");
			findfriend(id);
			Log.i("listItem", listItem + "");
			myAdapter.notifyDataSetChanged();
		}
	};

	private UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.example.projectcircle");

	// UMSocialService umSocialService;
	private void initSocial() {
		com.umeng.socialize.utils.Log.LOG = true;
		SocializeConstants.SHOW_ERROR_CODE = true;
		configSso();

	}

	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform() {
		AppLog.i(TAG, "分享:" + mController);
		String appId = ContantS.WX_APP_ID;
		// 微信图文分享,音乐必须设置一个url
		// String contentUrl = "http://www.umeng.com/social";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appId);
		wxHandler.addToSocialSDK();
		wxHandler.setTitle("工程圈分享");
		wxHandler.setTargetUrl("http://www.gcquan.com/");

		UMImage mUMImgBitmap = new UMImage(this,
				"http://www.gcquan.com/common/dimages/ppg.png");

		// 设置分享图片
		// mController.setShareMedia(mUMImgBitmap);
		// 支持微信朋友圈
		// 朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appId);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		wxCircleHandler.setTitle("工程圈分享");
		wxCircleHandler.setTargetUrl("http://www.gcquan.com/");

		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468",
				"c7394704798a158208a74ab60104f0ba");
		qqSsoHandler.setTargetUrl("http://www.hao123.com");
		qqSsoHandler.addToSocialSDK();

		mController.setShareContent(getString(R.string.share_content_to));
		mController.setShareImage(mUMImgBitmap);
		mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN_CIRCLE,
				SHARE_MEDIA.WEIXIN, SHARE_MEDIA.TENCENT);
		mController.openShare(context, new SnsPostListener() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1,
					SocializeEntity arg2) {
				// TODO Auto-generated method stub
				// Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
			}
		});

	}

	private void configSso() {

		// 配置SSO
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		mController.getConfig().removeSsoHandler(SHARE_MEDIA.SINA);

		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(context,
				"100424468", "c7394704798a158208a74ab60104f0ba");
		qZoneSsoHandler.addToSocialSDK();
		mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能");
		UMImage urlImage = new UMImage(context,
				"http://www.gcquan.com/common/dimages/ppg.png");
		// UMImage resImage = new UMImage(context, R.drawable.icon);

		UMusic uMusic = new UMusic(
				"http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
		uMusic.setAuthor("umeng");
		uMusic.setTitle("天籁之音");
		uMusic.setThumb(urlImage);
		// uMusic.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(getString(R.string.share_content_to));
		weixinContent.setTitle("工程圈分享");
		weixinContent.setTargetUrl("http://www.gcquan.com/");
		weixinContent.setShareImage(urlImage);
		mController.setShareMedia(weixinContent);

		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(getString(R.string.share_content_to));
		circleMedia.setTitle("工程圈分享");
		circleMedia.setShareImage(urlImage);
		circleMedia.setTargetUrl("http://www.gcquan.com/");
		mController.setShareMedia(circleMedia);

		UMImage image = new UMImage(context, BitmapFactory.decodeResource(
				getResources(), R.drawable.ic_comment));
		image.setTitle("工程圈分享");
		image.setThumb("http://www.gcquan.com/common/dimages/ppg.png");

		UMImage qzoneImage = new UMImage(context,
				"http://www.gcquan.com/common/dimages/ppg.png");
		qzoneImage.setTargetUrl("http://www.gcquan.com/common/dimages/ppg.png");

		// 设置QQ空间分享内容
		QZoneShareContent qzone = new QZoneShareContent();
		qzone.setShareContent(getString(R.string.share_content_to));
		qzone.setTargetUrl("http://www.gcquan.com/");
		qzone.setTitle("工程圈分享");
		qzone.setShareImage(urlImage);
		mController.setShareMedia(qzone);

		TencentWbShareContent tencent = new TencentWbShareContent();
		tencent.setShareContent(getString(R.string.share_content_to));
		// 设置tencent分享内容
		mController.setShareMedia(tencent);
		mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
				SHARE_MEDIA.DOUBAN, SHARE_MEDIA.SINA);
		mController.setShareMedia(new UMImage(context,
				"http://www.gcquan.com/common/dimages/ppg.png"));

	}

	private void getFriendRequest() {
		new MyHttpClient().FriendRequestMessage(LoginActivity.id, handler);
	}

	JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(response);
			getFriend_RequestCount(response);
		}
	};

	private void getFriend_RequestCount(JSONObject response) {
		try {
			AppLog.i(TAG, "数量:"+response);
			totalrecord = response.getJSONObject("friends").getInt("totalrecord");
			int size = 0;
			try {
				size = getContactFriendNum();
			} catch (Exception e) {
				// TODO: handle exception
			}
			AppLog.i(TAG, "数量:"+totalrecord+"::"+new_friend_tip_count+"::"+size);
			int m = totalrecord-getRequestFriendNum() + new_friend_tip_count - size;
			if (m > 0) {
				tipNotice.setText(m + "");// 有几个
				tipNotice.setVisibility(View.VISIBLE);
			}else {
				tipNotice.setVisibility(View.GONE);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	private void setContactFriendNum(int count){
		SharedPreferences sharedPreferences=getSharedPreferences(TAG, MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putInt("contact", count);
		editor.commit();
	}
	
	private void setRequestFriendNum(int count){
		SharedPreferences sharedPreferences=getSharedPreferences(TAG, MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putInt("request", count);
		editor.commit();
	}
	
	private int getContactFriendNum(){
		int count=0;
		count=getSharedPreferences(TAG, MODE_PRIVATE).getInt("contact", 0);
		return count;
	}
	
	private int getRequestFriendNum(){
		int count=0;
		count=getSharedPreferences(TAG, MODE_PRIVATE).getInt("request", 0);
		return count;
	}
}
