package com.example.projectcircle.setting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.SiginFragment1;
import com.example.projectcircle.SiginFragment2;
import com.example.projectcircle.SiginFragment3;
import com.example.projectcircle.SiginFragment4;
import com.example.projectcircle.adpter.HisDeviceGridAdapter;
import com.example.projectcircle.adpter.MasterDviAdapter;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.bean.EquInfo;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.complete.CompleteCommercial;
import com.example.projectcircle.complete.CompleteDriver;
import com.example.projectcircle.complete.CompleteInfo;
import com.example.projectcircle.complete.CompleteMaster;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.home.HomeSecActivity;
import com.example.projectcircle.personal.ModifyMySelf;
import com.example.projectcircle.personal.PersonalPage;
import com.example.projectcircle.personal.PictureShow;
import com.example.projectcircle.util.ImageUtil;
import com.example.projectcircle.util.LoadImageUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;

public class ModifyInfoActivity extends TabActivity {

	private static final String TAG = "ModifyInfoActivity";
	UserInfo info;
	String equipment;
	String business;
	/**
	 * ������Ϣ
	 */
	EditText realname_edit, age_edit, sign_edit, intro_edit, place_edit,
			my_hobby;
	TextView hometown_edit;
	ImageView myhead;

	String realname, hometown, age, sign, intro;
	// �ϴ�ͷ������Ҫ��
	private static final int REQUEST_CAMERA = 1;
	private Bitmap myBitmap;
	private byte[] mContent;
	String returnString;
	String headimage;
	/**
	 * ѡ��ְҵ
	 */
	private TabHost tabhost;
	EditText c_content;
	String crr_content;
	// ְҵ��ѡ��
	public static String type = "����";
	public static String type_content;
	/**
	 * ���ܵ绰
	 */
	RadioGroup call_group;
	RadioButton call_radio1, call_radio2;
	String accept = "0";
	/**
	 * ����Button
	 */
	Button back, submit;
	/**
	 * ѡ�����
	 */
	private ArrayAdapter<String> adapter1;
	private ArrayAdapter<String> adapter2;
	private ArrayAdapter<String> adapter3;
	private static final String[] m = { "����", "�ӱ�", "���", "�Ϻ�", "�Ĵ�" };
	String ht_1, ht_2, ht_3;
	private String id;

	private ProgressDialog progressDialog;
	private String uname;
	private String utype;
	private String uinfo;
	private String ucity;
	private String uheadimg;
	private String uaccept;
	private String uage;
	private String persign;
	private String address;
	private String uplace;
	private Context context;

	private ScrollView mScrollView;

	private ImageView deviceView;

	private ImageView deviceView1;

	private ImageView deviceView2;

	private boolean isHeadImge = false;

	private int picFalg = 0;

	private Bitmap deviceBitmap;

	private Bitmap deviceBitmap1;

	private Bitmap deviceBitmap2;

	private static boolean isModify = false;

	public static boolean isModify() {
		return isModify;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_edit);
		context = this;
		isModify = true;
		initView();
		try {
			EquDetail(MyApplication.getMyPersonBean().getId());
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isModify = false;
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		mScrollView = (ScrollView) findViewById(R.id.scrollView1);
		// scrollTop();
		myhead = (ImageView) findViewById(R.id.user_head);
		deviceView = (ImageView) findViewById(R.id.perfect_info_camera1);
		deviceView1 = (ImageView) findViewById(R.id.perfect_info_camera2);
		deviceView2 = (ImageView) findViewById(R.id.perfect_info_camera3);
		myhead.setFocusable(true);
		myhead.setFocusableInTouchMode(true);
		myhead.requestFocus();
		realname_edit = (EditText) findViewById(R.id.modify_realname);
		age_edit = (EditText) findViewById(R.id.modify_age);
		sign_edit = (EditText) findViewById(R.id.modify_qianming);
		intro_edit = (EditText) findViewById(R.id.modify_content);
		hometown_edit = (TextView) findViewById(R.id.modify_hometown);
		place_edit = (EditText) findViewById(R.id.modify_my_place);
		my_hobby = (EditText) findViewById(R.id.modify_my_hobby);

		c_content = (EditText) findViewById(R.id.sigin_carrer);
		// tabhost = (TabHost)findViewById(R.id.bing);
		tabhost = getTabHost();

		TabSpec tabSpec1 = tabhost.newTabSpec("tab1").setIndicator("tab1")
				.setContent(new Intent(this, SiginFragment1.class));
		TabSpec tabSpec2 = tabhost.newTabSpec("tab2").setIndicator("tab2")
				.setContent(new Intent(this, SiginFragment2.class));
		TabSpec tabSpec3 = tabhost.newTabSpec("tab3").setIndicator("tab3")
				.setContent(new Intent(this, SiginFragment3.class));
		TabSpec tabSpec4 = tabhost.newTabSpec("tab4").setIndicator("tab4")
				.setContent(new Intent(this, SiginFragment4.class));

		tabhost.addTab(tabSpec1);
		tabhost.addTab(tabSpec2);
		tabhost.addTab(tabSpec3);
		tabhost.addTab(tabSpec4);

		RadioGroup rg = (RadioGroup) this.findViewById(R.id.sigin_radiogroup);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.sigin_radiobtn1:
					type = "����";
					c_content.setVisibility(View.INVISIBLE);
					tabhost.setCurrentTabByTag("tab1");
					break;
				case R.id.sigin_radiobtn2:
					type = "˾��";
					c_content.setVisibility(View.INVISIBLE);
					tabhost.setCurrentTabByTag("tab2");
					break;
				case R.id.sigin_radiobtn3:
					type = "�̼�";
					c_content.setVisibility(View.INVISIBLE);
					tabhost.setCurrentTabByTag("tab3");
					break;
				case R.id.sigin_radiobtn4:
					type = "����";
					c_content.setVisibility(View.VISIBLE);
					type_content = c_content.getText().toString().trim();
					tabhost.setCurrentTabByTag("tab4");
					break;

				}
			}
		});

		initTab(rg);
		call_group = (RadioGroup) findViewById(R.id.call_radiogroup);
		call_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int radioButtonid = group.getCheckedRadioButtonId();
				switch (radioButtonid) {
				case R.id.call_radiobtn1:
					accept = "0";
					break;
				case R.id.call_radiobtn2:
					accept = "1";
					break;
				default:
					break;
				}
			}
		});

		back = (Button) findViewById(R.id.modify_head_left);
		submit = (Button) findViewById(R.id.modify_head_submit);
		back.setOnClickListener(Btnlistener);
		submit.setOnClickListener(Btnlistener);

		age_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Datedialog();
			}
		});

		initData();

	}

	private void initData() {
		id = LoginActivity.id;
		Intent intent = getIntent();
		uname = intent.getStringExtra("uname");
		utype = intent.getStringExtra("utype");
		ucity = intent.getStringExtra("ucity");
		uinfo = intent.getStringExtra("info");
		address = intent.getStringExtra("address");
		uheadimg = intent.getStringExtra("uheadimg");
		uage = intent.getStringExtra("age");
		uaccept = intent.getStringExtra("accept");
		persign = intent.getStringExtra("persign");
		accept = MyApplication.getMyPersonBean().getAccept();
		progressDialog = new ProgressDialog(this);
		initInfo();
	}

	private void initInfo() {
		// TODO Auto-generated method stub
		realname_edit.setText(uname);
		age_edit.setText(uage);
		sign_edit.setText(persign);
		intro_edit.setText(uinfo);
		AppLog.i(TAG, "����:" + MyApplication.getMyPersonBean().getAddress());
		if (!TextUtils.isEmpty(MyApplication.getMyPersonBean().getAddress())) {
			hometown_edit.setText(""
					+ MyApplication.getMyPersonBean().getAddress());
		}
		uplace = MyApplication.getMyPersonBean().getPlace();
		place_edit.setText(MyApplication.getMyPersonBean().getPlace());
		LoadImageUtils.displayImg(MyHttpClient.IMAGE_URL + uheadimg, myhead);
		try {
			my_hobby.setText("" + MyApplication.getMyPersonBean().getHobby());
		} catch (Exception e) {
			// TODO: handle exception
		}

		switch (accept) {
		case "0":
			call_group.check(R.id.call_radiobtn1);
			break;
		case "1":
			call_group.check(R.id.call_radiobtn2);
			break;

		default:
			break;
		}

		myhead.setOnClickListener(listener);
		deviceView.setOnClickListener(listener);
		deviceView1.setOnClickListener(listener);
		deviceView2.setOnClickListener(listener);
		hometown_edit.setOnClickListener(listener);

	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.modify_hometown:
				Intent intent = new Intent();
				intent.setClass(context, HomeSecActivity.class);
				startActivityForResult(intent, 10);
				break;
			case R.id.user_head:
				MyDialog();
				isHeadImge = true;
				break;
			case R.id.perfect_info_camera1:
				MyDialog();
				isHeadImge = false;
				picFalg = 0;
				break;

			case R.id.perfect_info_camera2:
				MyDialog();
				isHeadImge = false;
				picFalg = 1;
				break;

			case R.id.perfect_info_camera3:
				MyDialog();
				isHeadImge = false;
				picFalg = 2;
				break;

			default:
				break;
			}
		}

	};

	private View.OnClickListener Btnlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.modify_head_left:
				finish();
				break;
			case R.id.modify_head_submit:
				submit();
				submitHead();
				break;
			default:
				break;
			}
		}

	};

	private void MyDialog() {
		final CharSequence[] items = { "���", "����" };
		AlertDialog dlg = new AlertDialog.Builder(context).setTitle("ѡ��ͼƬ")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// ����item�Ǹ���ѡ��ķ�ʽ��
						// ��items�������涨�������ַ�ʽ�����յ��±�Ϊ1���Ծ͵������շ���
						if (item == 1) {
							Intent getImageByCamera = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							startActivityForResult(getImageByCamera,
									REQUEST_CAMERA);
						} else {
							Intent getImage = new Intent(
									Intent.ACTION_GET_CONTENT);
							getImage.addCategory(Intent.CATEGORY_OPENABLE);
							getImage.setType("image/jpeg");
							startActivityForResult(getImage, 0);
						}
					}
				}).create();
		dlg.show();
	}

	/**
	 * �ύ״̬
	 */
	private void submit() {
		// TODO Auto-generated method stub
		initData(0);
		realname = realname_edit.getText().toString().trim();
		age = age_edit.getText().toString().trim();
		sign = sign_edit.getText().toString().trim();
		intro = intro_edit.getText().toString().trim();
		hometown = hometown_edit.getText().toString().trim();
		uplace = place_edit.getText().toString();
		if (TextUtils.isEmpty(age)) {
			ToastUtils.showShort(getApplicationContext(), "���䲻��Ϊ��!");
			return;
		}
		if (TextUtils.isEmpty(realname)) {
			ToastUtils.showShort(getApplicationContext(), "��ʵ��������Ϊ��!");
			return;
		}
		if (realname_edit.length() < 2 && realname_edit.length() > 6) {
			ToastUtils.showShort(getApplicationContext(), "��ʵ����������2-6λ����!");
			return;
		}
		if (TextUtils.isEmpty(hometown)) {
			ToastUtils.showShort(getApplicationContext(), "���粻��Ϊ��!");
			return;
		}

		if (TextUtils.isEmpty(equipment) && !type.equals("����")) {
			ToastUtils.showShort(context, "��ѡ���豸");
			return;
		} else if (type.equals("����")) {
			type = c_content.getText().toString();
			business = SiginFragment4.yewu.getText().toString();
			equipment="";
			new MyHttpClient().CompleteCompany(LoginActivity.id, "", "", business, new AsyncHttpResponseHandler());
		}

		doSubMit(id, realname, age, sign, intro, type, equipment, accept,
				hometown);
		sendSubmit();
		postEquHeadImage();
		// }
	}

	// �ύͷ��
	private void submitHead() {
		// TODO Auto-generated method stub

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				headimage = "";
				if (myBitmap != null) {
					headimage = URLEncoder.encode(ImageUtil
							.bitmaptoString(myBitmap));
					headimage = ImageUtil.bitmaptoString(myBitmap);
					postHeadImage(id, headimage);
				}
			}
		}.start();

	}

	private void postHeadImage(String id2, String headimage2) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				// System.out.println(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						// myhead.setImageBitmap(myBitmap);
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.postHeadImage(id, headimage, res);
	}

	private void doSubMit(String id, String username, String age, String sign,
			String info, String type, String equipment, String accept,
			String address) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler resj = new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				Log.i("on start", "start");
				progressDialog.setMessage(getString(R.string.submiting));
				progressDialog.show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				progressDialog.dismiss();
			}

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("���أ�Submit", "���أ�Submit" + response);
				Toast.makeText(getApplicationContext(), "������Ϣ���³ɹ���",
						Toast.LENGTH_SHORT).show();
				LoginActivity.parseUserInfo(response, context);
				sendSubmitChange();
				finish();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.updateMyInfo(id, username, age, sign, info, type, equipment,
				accept, address, uplace, my_hobby.getText().toString(), resj);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		ContentResolver resolver = getContentResolver();
		/**
		 * ��Ϊ���ַ�ʽ���õ���startActivityForResult������ �������ִ����󶼻�ִ��onActivityResult������
		 * ����Ϊ�����𵽵�ѡ�����Ǹ���ʽ��ȡͼƬҪ�����жϣ�
		 * �����requestCode��startActivityForResult����ڶ���������Ӧ
		 */
		if (requestCode == 0) {
			try {
				// ���ͼƬ��uri
				Uri originalUri = data.getData();
				// ��ͼƬ���ݽ������ֽ�����
				mContent = ImageUtil.readStream(resolver.openInputStream(Uri
						.parse(originalUri.toString())));
				// ���ֽ�����ת��ΪImageView�ɵ��õ�Bitmap����
				if (isHeadImge) {
					myBitmap = ImageUtil.getPicFromBytes(mContent, null);
					myBitmap = comp(myBitmap);
					myBitmap = ImageUtil.toRoundCorner(myBitmap, 20);
					// //�ѵõ���ͼƬ���ڿؼ�����ʾ
					myhead.setImageBitmap(myBitmap);
				} else {
					switch (picFalg) {
					case 0:
						deviceBitmap = ImageUtil
								.getPicFromBytes(mContent, null);
						deviceBitmap = comp(deviceBitmap);
						deviceBitmap = ImageUtil
								.toRoundCorner(deviceBitmap, 20);
						// //�ѵõ���ͼƬ���ڿؼ�����ʾ
						deviceView.setImageBitmap(deviceBitmap);
						break;
					case 1:
						deviceBitmap1 = ImageUtil.getPicFromBytes(mContent,
								null);
						deviceBitmap1 = comp(deviceBitmap1);
						deviceBitmap1 = ImageUtil.toRoundCorner(deviceBitmap1,
								20);
						// //�ѵõ���ͼƬ���ڿؼ�����ʾ
						deviceView1.setImageBitmap(deviceBitmap1);
						break;
					case 2:
						deviceBitmap2 = ImageUtil.getPicFromBytes(mContent,
								null);
						deviceBitmap2 = comp(deviceBitmap2);
						deviceBitmap2 = ImageUtil.toRoundCorner(deviceBitmap2,
								20);
						// //�ѵõ���ͼƬ���ڿؼ�����ʾ
						deviceView2.setImageBitmap(deviceBitmap2);
						break;

					default:
						break;
					}

				}

			} catch (Exception e) {
				// System.out.println(e.getMessage());
			}

		} else if (requestCode == REQUEST_CAMERA) {
			try {
				super.onActivityResult(requestCode, resultCode, data);
				Bundle extras = data.getExtras();
				if (isHeadImge) {
					myBitmap = (Bitmap) extras.get("data");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					mContent = baos.toByteArray();
					myBitmap = ImageUtil.toRoundCorner(myBitmap, 20);
					myhead.setImageBitmap(myBitmap);
				} else {
					switch (picFalg) {
					case 0:
						deviceBitmap = (Bitmap) extras.get("data");
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						deviceBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
								baos);
						mContent = baos.toByteArray();
						deviceBitmap = ImageUtil
								.toRoundCorner(deviceBitmap, 20);
						deviceView.setImageBitmap(deviceBitmap);
						break;
					case 1:
						deviceBitmap1 = (Bitmap) extras.get("data");
						ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
						deviceBitmap1.compress(Bitmap.CompressFormat.JPEG, 100,
								baos1);
						mContent = baos1.toByteArray();
						deviceBitmap1 = ImageUtil.toRoundCorner(deviceBitmap1,
								20);
						deviceView1.setImageBitmap(deviceBitmap1);
						break;
					case 2:
						deviceBitmap2 = (Bitmap) extras.get("data");
						ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
						deviceBitmap2.compress(Bitmap.CompressFormat.JPEG, 100,
								baos2);
						mContent = baos2.toByteArray();
						deviceBitmap2 = ImageUtil.toRoundCorner(deviceBitmap2,
								20);
						deviceView2.setImageBitmap(deviceBitmap2);
						break;

					default:
						break;
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			// ���������Ƭת��Բ����ʾ��Ԥ���ؼ���

		} else if (requestCode == 10) {
			try {
				Bundle extras = data.getExtras();
				String home = extras.getString("home", "");
				hometown = home;
				hometown_edit.setText(hometown);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}

		}
		// if (myBitmap != null) {
		// ImageUtil.bitmaptoString(myBitmap);
		// }
		// submit();
	}

	public static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// �ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, 20, baos);// ����ѹ��50%����ѹ��������ݴ�ŵ�baos��
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// ���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ
		float hh = 800f;// �������ø߶�Ϊ800f
		float ww = 480f;// �������ÿ��Ϊ480f
		// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;// be=1��ʾ������
		if (w > h && w > ww) {// �����ȴ�Ļ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// ����߶ȸߵĻ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// �������ű���
		// ���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;// ѹ���ñ�����С���ٽ�������ѹ��
	}

	private void initData(int m) {
		// TODO Auto-generated method stub
		if (type.equals("����")) {
			String s = "";
			if (SiginFragment1.btn1.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device1;
				} else {
					s = s + "," + SiginFragment1.device1;
				}
			}
			if (SiginFragment1.btn2.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device2;
				} else {
					s = s + "," + SiginFragment1.device2;
				}
			}
			if (SiginFragment1.btn3.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device3;
				} else {
					s = s + "," + SiginFragment1.device3;
				}
			}
			if (SiginFragment1.btn4.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device4;
				} else {
					s = s + "," + SiginFragment1.device4;
				}
			}
			if (SiginFragment1.btn5.isChecked()) {
				SiginFragment1.device5 = SiginFragment1.d_content.getText()
						.toString().trim();
				if (s.isEmpty()) {
					s = s + SiginFragment1.device5;
				} else {
					s = s + "," + SiginFragment1.device5;
				}
			}
			equipment = s;
			Log.i("equipment", equipment);

		} else if (type.equals("˾��")) {
			equipment = SiginFragment2.equipment;

		} else if (type.equals("�̼�")) {
			String b = "";
			if (SiginFragment3.btn1.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device1;
				} else {
					b = b + "," + SiginFragment3.device1;
				}
			}
			if (SiginFragment3.btn2.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device2;
				} else {
					b = b + "," + SiginFragment3.device2;
				}
			}
			if (SiginFragment3.btn3.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device3;
				} else {
					b = b + "," + SiginFragment3.device3;
				}
			}
			if (SiginFragment3.btn4.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device4;
				} else {
					b = b + "," + SiginFragment3.device4;
				}
			}
			if (SiginFragment3.btn5.isChecked()) {
				SiginFragment3.device5 = SiginFragment3.d_content.getText()
						.toString().trim();
				if (b.isEmpty()) {
					b = b + SiginFragment3.device5;
				} else {
					b = b + "," + SiginFragment3.device5;
				}
			}
			equipment = b;
			Log.i("equipment", equipment);

			String c = "";
			if (SiginFragment3.btn_1.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi1;
				} else {
					c = c + "," + SiginFragment3.busi1;
				}
			}
			if (SiginFragment3.btn_2.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi2;
				} else {
					c = c + "," + SiginFragment3.busi2;
				}
			}
			if (SiginFragment3.btn_3.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi3;
				} else {
					c = c + "," + SiginFragment3.busi3;
				}
			}
			if (SiginFragment3.btn_4.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi4;
				} else {
					b = b + "," + SiginFragment3.busi4;
				}
			}
			business = c;
			Log.i("business", business);
		} else {
			business = SiginFragment4.yewu.getText().toString();
			Log.i("business", business);
		}
	}

	private void scrollTop() {
		mScrollView.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mScrollView.fullScroll(ScrollView.FOCUS_UP);
			}
		});
	}

	// �豸ͼƬ
	private void postEquHeadImage() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (deviceBitmap != null) {
					new MyHttpClient().upLoadEquHeadImage(id,
							ImageUtil.bitmaptoString(deviceBitmap),
							postDeviceHandler);
				}
				if (deviceBitmap1 != null) {
					new MyHttpClient().upLoadEquHeadImage(id,
							ImageUtil.bitmaptoString(deviceBitmap1),
							postDeviceHandler);
				}
				if (deviceBitmap2 != null) {
					new MyHttpClient().upLoadEquHeadImage(id,
							ImageUtil.bitmaptoString(deviceBitmap2),
							postDeviceHandler);
				}

			}
		}).start();

	}

	private AsyncHttpResponseHandler postDeviceHandler = new AsyncHttpResponseHandler() {
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			Log.i("�ϴ�������Ƭresult", "��ʼ�ϴ�");
		}

		public void onSuccess(String response) {
			// System.out.println(response);
			Log.i("�ϴ��豸ͼƬ", "�ϴ��豸ͼƬ" + response);
			JSONObject obj;
			try {
				obj = new JSONObject(response);
				Log.i("�ϴ��豸ͼƬ", "�ϴ��豸ͼƬ" + obj.getInt("result") + "");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	private ArrayList<EquInfo> deviceList = new ArrayList<>();

	private void EquDetail(String uid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				try {
					deviceList = new ArrayList<>();
					JSONObject result = new JSONObject(response);
					JSONObject equ = result.getJSONObject("equ");
					JSONArray array = equ.getJSONArray("resultlist");
					for (int i = 0; i < array.length(); i++) {
						EquInfo device = new EquInfo();
						JSONArray array1 = array.getJSONArray(i);
						JSONObject obj = array1.getJSONObject(0);
						device = new Gson().fromJson(obj.toString(),
								EquInfo.class);
						deviceList.add(device);
					}
					initHisDevice();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.EquDetail(uid, res);
	}

	private void initHisDevice() {
		// TODO Auto-generated method stub
		if (deviceList != null) {
			boolean a = false;
			boolean b = false;
			boolean c = false;
			for (int i = 0; i < deviceList.size(); i++) {
				String imgurl = deviceList.get(i).getHeadimage();
				if (!TextUtils.isEmpty(imgurl)) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("headimage", deviceList.get(i).getHeadimage());
					if (!a) {
						LoadImageUtils.displayImg(MyHttpClient.IMAGE_URL
								+ imgurl, deviceView);
						a = true;
					} else if (!b) {
						LoadImageUtils.displayImg(MyHttpClient.IMAGE_URL
								+ imgurl, deviceView1);
						b = true;
					} else if (!c) {
						LoadImageUtils.displayImg(MyHttpClient.IMAGE_URL
								+ imgurl, deviceView2);
						c = true;
					}

				}
			}
		}

		CompleteMaster.myAdapter = new MasterDviAdapter(
				getApplicationContext(), getDeviceList());
		CompleteMaster.myAdapter.notifyDataSetChanged();
		CompleteMaster.listview.setAdapter(CompleteMaster.myAdapter);
		CompleteMaster.listview
				.setSelection(CompleteMaster.listview.getCount() - 1);
	}

	int stryear, strmonth, strday;
	String birthday = "";

	private void Datedialog() {
		DatePickerDialog datePickerDialog = new DatePickerDialog(context,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							final int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						birthday = year + "-" + (monthOfYear + 1) + "-"
								+ dayOfMonth;
						stryear = year;
						strmonth = monthOfYear + 1;
						strday = dayOfMonth;
						age_edit.setText(birthday);
						getage();
					}

				}, 1986, 0, 1);
		datePickerDialog.setTitle("���ó�������");
		datePickerDialog.show();
	}

	private void getage() {
		// TODO Auto-generated method stub
		Time t = new Time();
		t.setToNow();
		int currentyear = t.year;
		Log.i("currentyear", currentyear + "");
		int year_index = currentyear - stryear;
		Log.i("year_index", year_index + "");
		int currentmonth = t.month + year_index * 12;
		Log.i("currentmonth", currentmonth + "");
		int month = currentmonth - strmonth;
		Log.i("month", month + "");
		age = month / 12 + "";
	}

	private ArrayList<HashMap<String, Object>> getDeviceList() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
		if (deviceList != null) {
			for (int i = 0; i < deviceList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				EquInfo equInfo = deviceList.get(i);
				map.put("id", equInfo.getId());
				map.put("ename", equInfo.getEname());
				map.put("ebrand", equInfo.getEbrand());
				map.put("emodel", equInfo.getEmodel());
				map.put("etype", equInfo.getEstyle());
				map.put("enumber", equInfo.getEnumber());
				map.put("select_num", i + 1);
				map.put("equipment", equInfo.getEname());
				listItem.add(map);
				Log.i("listItem", listItem + "");
			}

		}

		return listItem;
	}

	private void initTab(RadioGroup rg) {
		switch (MyApplication.getMyPersonBean().getType()) {
		case "����":
			tabhost.setCurrentTab(0);
			break;

		case "˾��":
			tabhost.setCurrentTab(1);
			rg.check(R.id.sigin_radiobtn2);
			break;

		case "�̼�":
			tabhost.setCurrentTab(2);
			rg.check(R.id.sigin_radiobtn3);
			break;

		case "����":
			tabhost.setCurrentTab(3);
			rg.check(R.id.sigin_radiobtn4);
			break;

		default:
			tabhost.setCurrentTab(3);
			rg.check(R.id.sigin_radiobtn4);
			try {
				c_content.setText(MyApplication.getMyPersonBean().getType());
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		}
	}

	private void sendSubmit() {
		sendBroadcast(new Intent(ContantS.ACTION_SEND_SUBMIT));
	}

	private void sendSubmitChange() {
		sendBroadcast(new Intent(ContantS.ACTION_GET_USER_INFO));
	}

}
