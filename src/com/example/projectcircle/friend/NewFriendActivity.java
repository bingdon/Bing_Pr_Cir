package com.example.projectcircle.friend;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.FriendRequestAdapter;
import com.example.projectcircle.adpter.NewContAdapter;
import com.example.projectcircle.adpter.NewContAdapter.HandlerListener;
import com.example.projectcircle.bean.NewConstactBean;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.db.utils.NewContactsUtily;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.other.MessagePage;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class NewFriendActivity extends Activity implements HandlerListener {

	private static final String TAG = NewFriendActivity.class.getSimpleName();
	private List<String> telStrings = new ArrayList<>();
	private String myid = "";
	private Context context;
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

	private List<NewConstactBean> rConstactBeans = new ArrayList<>();

	/** 联系人号码 **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	private String now_number;

	private List<String> regist_not_friend_name = new ArrayList<String>();

	JSONArray json1, json2, json3;

	private NewContAdapter newContAdapter;

	private ListView cListView;

	private NewContactsUtily newContactsUtily;

	private static final int TYPE_CONT = 0;

	private static final int TYPE_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.concat_friend_list);
		context = this;
		telStrings = getIntent().getStringArrayListExtra("tels");
		myid = LoginActivity.id;
		initView();
	}

	private void initView() {
		findViewById(R.id.g_list_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		initData();
	}

	private void initData() {
		getLastData();
		newContAdapter = new NewContAdapter(rConstactBeans, context);
		cListView = (ListView) findViewById(R.id.concat_list_listView);
		cListView.setAdapter(newContAdapter);
		getPhoneContacts();
		now_number = mContactsNumber.toString().substring(1,
				mContactsNumber.toString().length() - 1);// 去掉ArrayList中的【和】这个再开头和结尾有，要不后台识别不出来
		now_number = now_number.replace(" ", "");// 去掉所有空格，要不后台识别不出来
		isRegist(now_number, myid);
		FriendRequestList(myid);
		newContAdapter.setListener(this);
		registerForContextMenu(cListView);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.msglist, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.deletemsg:

			delete(aInfo.position);

			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}

	private void delete(int position) {
		newContactsUtily.delete(rConstactBeans.get(position));
		rConstactBeans.remove(position);
		newContAdapter.notifyDataSetChanged();
	}

	private void getPhoneContacts() {
		ContentResolver resolver = getContentResolver();

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
				// Bitmap contactPhoto = null;
				//
				// // photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
				// if (photoid > 0) {
				// Uri uri = ContentUris.withAppendedId(
				// ContactsContract.Contacts.CONTENT_URI, contactid);
				// InputStream input = ContactsContract.Contacts
				// .openContactPhotoInputStream(resolver, uri);
				// contactPhoto = BitmapFactory.decodeStream(input);
				// } else {
				// contactPhoto = BitmapFactory.decodeResource(getResources(),
				// R.drawable.header);
				// }
				String mphoneNumber = phoneNumber.replace(" ", ""); // 得到的电话号码有空格，去掉它
				if (mphoneNumber.length() > 11) {
					mphoneNumber = mphoneNumber.substring(
							mphoneNumber.length() - 11, mphoneNumber.length());
				}// 截取手机号的后11位，因为存的有的手机号开头带有+86，把它去掉
				NewConstactBean newConstactBean = new NewConstactBean();
				newConstactBean.setUsername(contactName);
				newConstactBean.setTel(mphoneNumber);
				mContactsNumber.add(mphoneNumber);
			}

			phoneCursor.close();
			// Log.i("判断传入后台的电话号码",mContactsNumber+"");

		}
	}

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
					AppLog.i(TAG, "大小:" + mContactsNumber.size());
					for (int i = 0; i < mContactsNumber.size(); i++) {
						AppLog.i(TAG,
								"大小:" + isIn(mContactsNumber.get(i), json3));
						if (isIn(mContactsNumber.get(i), json3)) {
							regist_not_friend_name.add(mContactsNumber.get(i));
						}

					}
					AppLog.i(TAG, "之后大小:" + regist_not_friend_name.size());
					for (int j = 0; j < regist_not_friend_name.size(); j++) {
						String num = regist_not_friend_name.get(j);
						AppLog.i(TAG, "是否存在:" + isExit(num) + "num::" + num
								+ "::");
						if (!isExit(num)) {
							findUserByTel(num, j);
						}
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
			AppLog.i(
					TAG,
					"号码:" + substring + "::" + aSource + "::"
							+ (aSource.equals(substring)));
			if (aSource.equals(substring)) {
				return true;
			}
		}
		return false;
	}

	private void getLastData() {
		newContactsUtily = new NewContactsUtily(context);
		rConstactBeans = newContactsUtily.queryData();
		if (rConstactBeans == null) {
			rConstactBeans = new ArrayList<>();
		}
		AppLog.i(TAG, "实际大小:" + rConstactBeans.size());
		for (int i = 0; i < rConstactBeans.size(); i++) {
			AppLog.i(TAG, "实际大小:" + rConstactBeans.get(i).getUsername());
		}
	}

	private void updateList() {
		rConstactBeans = newContactsUtily.queryData();
		if (rConstactBeans == null) {
			rConstactBeans = new ArrayList<>();
		}
		newContAdapter = new NewContAdapter(rConstactBeans, context);
		cListView.setAdapter(newContAdapter);
		newContAdapter.notifyDataSetChanged();
		newContAdapter.setListener(this);
	}

	private boolean isExit(String num) {
		for (int i = 0; i < rConstactBeans.size(); i++) {
			if (num.equals(rConstactBeans.get(i))) {
				return true;
			}
		}
		return false;
	}

	private void findUserByTel(String number, final int postion) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				AppLog.i("返回", "新的" + response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						JSONObject user = obj.getJSONObject("user");
						NewConstactBean newConstactBean = new Gson().fromJson(
								user.toString(), NewConstactBean.class);
						newContactsUtily.update(newConstactBean);
						updateList();
					} else {
						ToastUtils.showShort(getApplicationContext(), "没找到！");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.findUserByTel(number, res);

	}

	private void FriendRequestList(String bid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", response);
				parseFriendRequestList(response);
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.FriendRequestMessage(bid, res);
	}

	private void parseFriendRequestList(String response) {
		// TODO Auto-generated method stub
		try {
			Log.i("解析好友请求的response", "好友请求:"+response + "");
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("friends");
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			Log.i(TAG, "JSONArray:" + json);
			for (int i = 0; i < length; i++) {
				JSONObject objo = json.getJSONObject(i);
				Log.i(TAG, "objo:" + objo);
				NewConstactBean newConstactBean = new Gson().fromJson(
						objo.toString(), NewConstactBean.class);
				newConstactBean.setType_(1);
				newContactsUtily.update(newConstactBean);
			}
			updateList();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addPeople(int position) {
		// TODO Auto-generated method stub
		NewConstactBean newConstactBean = rConstactBeans.get(position);
		if (newConstactBean.getType_() == TYPE_CONT) {
			applyfriend(newConstactBean.getId(), position);
		} else if (newConstactBean.getType_() == TYPE_REQUEST) {
			befriend(newConstactBean.getId(), position);
		}
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
						newContactsUtily.update(rConstactBeans.get(position));
						newContAdapter.notifyDataSetChanged();
					} else {
						Toast.makeText(context, "添加失败！", Toast.LENGTH_SHORT)
								.show();
						onFinish();
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

	private void applyfriend(String bid, final int position) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回", response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						rConstactBeans.get(position).setIsAccpet(1);
						newContactsUtily.update(rConstactBeans.get(position));
						newContAdapter.notifyDataSetChanged();
						ToastUtils.showLong(getApplicationContext(),
								"申请完毕，等待好友通过验证");
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"死样，消息已发送，请勿重复添加~~");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastUtils.showShort(context, getString(R.string.erro_no));
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.VerifyFriend(myid, bid, getString(R.string.contacts_), res);
	}

}
