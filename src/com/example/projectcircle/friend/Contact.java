package com.example.projectcircle.friend;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.friend.Contact.MyListAdapter;
import com.example.projectcircle.friend.Contact.ViewHolder;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
@Deprecated
public class Contact extends Activity {

	Context mContext = Contact.this;

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
	// 注册但不是好友的arraylist的电话号码
	private ArrayList<String> regist_not_friend_number = new ArrayList<String>();
	// 注册但不是好友的arraylist的名字
	private ArrayList<String> regist_not_friend_name = new ArrayList<String>();
	
	private List<MyPersonBean> liPersonBeans=new ArrayList<>();
	
	ListView mListView = null;
	MyListAdapter myAdapter = null;
	private ArrayList<String> telStrings;
	protected int sure;

	private String myid;

	public int zhi;
	ViewHolder holder;
	// 去掉空格之后的电话号码
	private String mphoneNumber;

	private String now_number;

	JSONArray json1, json2, json3;
	String aid = LoginActivity.id;
	String bid;// 已注册过的通讯录中联系人的id
	HashMap<String, Object> contactmap = new HashMap<String, Object>();
	private ArrayList<String> regist_and_friend_number = new ArrayList<String>();
	private ArrayList<String> regist_and_friend_name = new ArrayList<String>();

	private ListView listView;

	private Button button_back;

	private ArrayList<UserInfo> friendRequestList;
	private static final String TAG = Contact.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.concat_friend_list);
		telStrings = getIntent().getStringArrayListExtra("tels");
		myid = LoginActivity.id;// 获取我的登录id
		FriendRequestList(myid);
		/** 得到手机通讯录联系人信息 **/
		getPhoneContacts();
		// 判断是否注册
		// mContactsNumber=mContactsNumber.replace("[","");
		// //去掉字符串中的【和】这个再开头和结尾有，要不后台识别不出来
		// mContactsNumber=mContactsNumber.replace("]","");
		now_number = mContactsNumber.toString().substring(1,
				mContactsNumber.toString().length() - 1);// 去掉ArrayList中的【和】这个再开头和结尾有，要不后台识别不出来
		now_number = now_number.replace(" ", "");// 去掉所有空格，要不后台识别不出来
		isRegist(now_number, myid);
		back();
	}

	// 返回
	private void back() {
		// TODO Auto-generated method stub
		button_back = (Button) findViewById(R.id.g_list_left);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Contact.this.setResult(RESULT_OK, getIntent());
				Contact.this.finish();
			}
		});
	}

	/**
	 * 得到手机通讯录联系人信息
	 * 
	 * @return
	 **/
	public ArrayList<String> getPhoneContacts() {
		ContentResolver resolver = mContext.getContentResolver();

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
							R.drawable.header);
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
			// Log.i("判断传入后台的电话号码",mContactsNumber+"");

		}
		return mContactsNumber;
	}

	class MyListAdapter extends BaseAdapter {

		public MyListAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			// 设置绘制数量
			return regist_not_friend_name.size();
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("deprecation")
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final String number;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.contact, null);
				holder = new ViewHolder();
				holder.headimg = (ImageView) convertView
						.findViewById(R.id.newfriend_header);
				holder.image = (Button) convertView
						.findViewById(R.id.invitation);
				holder.username = (TextView) convertView
						.findViewById(R.id.username);
				holder.message = (TextView) convertView
						.findViewById(R.id.message);
				convertView.setTag(holder);// 绑定ViewHolder对象
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 绘制联系人名称
			holder.username.setText(regist_not_friend_name.get(position));
			// holder.image.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// // TODO Auto-generated method stub
			// Uri uri = Uri.parse("smsto:" + mContactsName.get(position));
			// Intent it = new Intent(Intent.ACTION_SENDTO, uri);
			// it.putExtra("sms_body", "我在用工程圈的软件，非常好用，你也注册用一下吧！");
			// startActivity(it);
			// }
			// });
			// 获取此电话号码,number必须定义成final String类型的，要理解position的意义
			number = regist_not_friend_number.get(position) + "";
			// 判断是否已经注册
			// if (contactmap.get(number) == "1") {
			// holder.image.setText("邀请");
			// holder.image.setBackgroundColor(R.color.transparent);
			// } else
			if (contactmap.get(number) == "2") {
				holder.image.setText("已添加");

			} else if (contactmap.get(number) == "3") {
				// holder.image.setText("添加");
				Resources resources = getBaseContext().getResources();
				Drawable btnDrawable = resources
						.getDrawable(R.drawable.add_new_contact_selector);
				holder.image.setBackgroundDrawable(btnDrawable);
				holder.image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// 根据电话号码查找用户信息，以获得其id
						findUserByTel(number);
					}
				});
			}
			return convertView;
		}

	}

	/* 存放控件 */
	public final class ViewHolder {

		public TextView message;
		public TextView username;// 用户名
		public ImageView headimg;// 头像
		public Button image;// 同意还是添加的图片

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
					Log.i(TAG, "大小:" + mContactsNumber.size());
					for (int i = 0; i < mContactsNumber.size(); i++) {
						if (isIn(mContactsNumber.get(i), json1)) {
							contactmap.put(mContactsNumber.get(i), "1");

						} else if (isIn(mContactsNumber.get(i), json2)) {
							contactmap.put(mContactsNumber.get(i), "2");

						} else if (isIn(mContactsNumber.get(i), json3)) {
							contactmap.put(mContactsNumber.get(i), "3");

						}
					}
					// 注册过但不是好友的提到前面来
					Log.i("contactmap在regist之后", contactmap + "");
					for (int i = 0, len = mContactsNumber.size(); i < len; ++i) {
						if (contactmap.get(mContactsNumber.get(i)) == "3") {
							regist_not_friend_number
									.add(mContactsNumber.get(i));
							regist_not_friend_name.add(mContactsName.get(i));
							// mContactsNumber.remove(i);
							// mContactsName.remove(i);
							// --len;//减少一个
							// --i;//多谢deny_guoshou指正，如果不加会出现评论1楼所说的情况。

						} else if (contactmap.get(mContactsNumber.get(i)) == "2") {
							regist_and_friend_number
									.add(mContactsNumber.get(i));
							regist_and_friend_name.add(mContactsName.get(i));
							// mContactsNumber.remove(i);
							// mContactsName.remove(i);
							// --len;//减少一个
							// --i;//多谢deny_guoshou指正，如果不加会出现评论1楼所说的情况。

						}
					}
					Log.i("regist_not_friend_number----前",
							regist_not_friend_number + "");
					Log.i("regist_not_friend_name----前", regist_not_friend_name
							+ "");
					regist_not_friend_number.addAll(regist_and_friend_number);// 两个数组拼接
					regist_not_friend_name.addAll(regist_and_friend_name);// 两个数组拼接
					Log.i("regist_not_friend_number----后",
							regist_not_friend_number + "");
					Log.i("regist_not_friend_name----后", regist_not_friend_name
							+ "");
					InitList();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.contactIsRegiest(now_number, id, res);

	}

	private void InitList() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.concat_list_listView);
		myAdapter = new MyListAdapter(this);
		listView.setAdapter(myAdapter);
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
			// Log.i(TAG,
			// "号码:"+substring+"::"+aSource+"::"+(aSource.equals(substring)));
			if (aSource.equals(substring)) {
				// Log.i(TAG, "asource:" + aSource);
				return true;
			}
		}
		return false;
	}

	private void applyfriend(String aid, String bid, String info) {
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
						ToastUtils.showLong(getApplicationContext(),
								"申请完毕，等待好友通过验证");
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"死样，消息已发送，请勿重复添加~~");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.VerifyFriend(aid, bid, info, res);
	}

	private void findUserByTel(String number) {
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
						JSONObject user = obj.getJSONObject("user");
						bid = user.getString("id");
						// 申请好友，上边findUserByTel方法找bid是异步的，得判断一下
						String info = "通讯录联系人";
						// 得到这个电话号码对应的Id后，开始申请好友
						applyfriend(aid, bid, info);// 得写到这里边

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
				// initList();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.FriendRequestMessage(bid, res);
	}

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
}