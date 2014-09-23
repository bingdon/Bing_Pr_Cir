package com.example.projectcircle.group;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.util.ImageUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CreateGroup extends Activity {
	private static final String TAG = "CreateGroup";
	/**
	 * 其它Button
	 */
	Button back, next;

	/**
	 * 头像和基本信息
	 */
	ImageView group_head;
	EditText group_name, group_intro;
	TextView group_place;
	String name, intro;
	String place;
	// 上传头像所需要的
	private static final int REQUEST_CAMERA = 1;
	private Bitmap myBitmap;
	private byte[] mContent;
	String returnString;
	String uid;
	String headimage;
	String limit = "10";
	/**
	 * 定位
	 * 
	 */
	double latitude;
	double longitude;
	private LocationClient mLocationClient;
	private MyBDLocationListener mBDLocationListener;
	private friendBroadcastReceiver receiver;

	private static String gid;

	public static String getGid() {
		return gid;
	}

	// 获取省份、区县、城市信息
	private String province;
	private String city;
	private String district;

	private ProgressDialog progressDialog;

	public static String g_name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_group);
		initView();
		initBtn();
		initLoc();
		progressDialog = new ProgressDialog(this);
		// 注册广播,添加请求信息里一点击同意，我这边好友列表里就增加相应的好友
		IntentFilter filter = new IntentFilter();
		filter.addAction("cn.abel.action.broadcast");
		receiver = new friendBroadcastReceiver();
		registerReceiver(receiver, filter);
	}

	/**
	 * 初始化定位 配置定位
	 * 
	 */
	private void initLoc() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(this.getApplicationContext());

		mBDLocationListener = new MyBDLocationListener();
		mLocationClient.registerLocationListener(mBDLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps

		option.setAddrType("all");
		// 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
		option.setPoiExtraInfo(true);
		// 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		option.setProdName("通过GPS定位我当前的位置");
		// 禁用启用缓存定位数据
		option.disableCache(true);
		// 坐标系类型，百度手机地图对外接口中的坐标系默认是bd09ll
		option.setCoorType("bd09ll");
		// 设置最多可返回的POI个数，默认值为3。由于POI查询比较耗费流量，设置最多返回的POI个数，以便节省流量。
		option.setPoiNumber(3);
		// 设置定位方式的优先级。
		// 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
		option.setPriority(LocationClientOption.GpsFirst);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	/**
	 * 编写BDLocationListener接口的实现类，接收异步返回的定位结果和异步返回的POI查询结果。
	 * 
	 */
	final class MyBDLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			province = location.getProvince(); // 获取省份信息
			city = location.getCity(); // 获取城市信息
			district = location.getDistrict(); // 获取区县信息
			group_place.setText("" + location.getAddrStr());
			Log.i("创建群组所在的省", province);
			Log.i("创建群组所在的市", city);
			Log.i("创建群组所在的县", district);
			latitude = location.getLatitude();
			longitude = location.getLongitude();

			Log.i(TAG, "latitude = " + latitude);
			Log.i(TAG, "longitude = " + longitude);

			StringBuffer sb = new StringBuffer(256);
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			Log.i("群组地址", sb + "");
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {

			// 将在下个版本中去除poi功能
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			Log.i("群组地址", sb + "");

		}
	}

	/**
	 * 初始化控件
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		group_head = (ImageView) findViewById(R.id.c_group_camera);
		group_name = (EditText) findViewById(R.id.c_group_name);
		group_intro = (EditText) findViewById(R.id.c_group_jianjie);
		group_place = (TextView) findViewById(R.id.group_add_name);
		group_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyDialog();
			}
		});
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.c_group_left);
		next = (Button) findViewById(R.id.c_group_next);
		back.setOnClickListener(listener);
		next.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.c_group_left:
				// Intent intent = new Intent(CreateGroup.this,
				// GroupPage.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.c_group_next:
				next();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 提交状态
	 * 
	 */
	private void next() {
		// TODO Auto-generated method stub
		uid = LoginActivity.id;
		name = group_name.getText().toString().trim();
		intro = group_intro.getText().toString().trim();
		place = group_place.getText().toString().trim();

		headimage = "";
		if (null==myBitmap) {
			ToastUtils.showLong(getApplicationContext(), "请添加群组头像");
			return;
		}
		if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(intro)
				&& !TextUtils.isEmpty(place)) {
			postGroup(uid, name, place, intro, limit, latitude, longitude);
		}

	}

	/**
	 * 上传
	 * 
	 */
	private void postGroupHeadImage(String uid, String headimage) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				progressDialog.dismiss();
			}

			public void onSuccess(String response) {
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("这是创建群组的，看看头像上传成功没有", response + "");
					if (obj.getInt("result") == 1) {
						postfinish();
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.postGroupHeadImage(uid, headimage, res);
	}

	private void postGroup(String uid, final String name, String place,
			String intro, String limit, double latitude, double longitude) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();

				progressDialog.setMessage(getString(R.string.submiting));
				progressDialog.show();

			}

			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
				progressDialog.dismiss();
			}

			public void onSuccess(String response) {
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						// 开始上传头像，参数传group的id
						JSONObject result = obj.getJSONObject("group");

						gid = result.getString("id");
						postHeadStart(gid);
						g_name = name;
					} else {
						progressDialog.dismiss();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					progressDialog.dismiss();
				}
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.postGroup(uid, name, place, intro, limit, latitude, longitude,
				res);
	}

	@SuppressWarnings("deprecation")
	protected void postHeadStart(String gid) {
		// TODO Auto-generated method stub
		if (myBitmap != null) {
			headimage = ImageUtil.bitmaptoString(myBitmap);
			// System.out.println(headimage);
			postGroupHeadImage(gid, headimage);
		} else {
			progressDialog.dismiss();
			postfinish();
		}
	}

	private void postfinish() {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "添加成功！", Toast.LENGTH_LONG)
		// .show();
		// group_head.setImageBitmap(myBitmap);
		Intent intent1 = new Intent(CreateGroup.this, CreateGroupSuccess.class);
		startActivity(intent1);
	}

	// 图片上传选择途径
	private void MyDialog() {
		final CharSequence[] items = { "相册", "拍照" };
		AlertDialog dlg = new AlertDialog.Builder(CreateGroup.this)
				.setTitle("选择图片")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 这里item是根据选择的方式，
						// 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		ContentResolver resolver = getContentResolver();
		/**
		 * 因为两种方式都用到了startActivityForResult方法， 这个方法执行完后都会执行onActivityResult方法，
		 * 所以为了区别到底选择了那个方式获取图片要进行判断，
		 * 这里的requestCode跟startActivityForResult里面第二个参数对应
		 */
		if (requestCode == 0) {
			try {
				// 获得图片的uri
				Uri originalUri = data.getData();
				// 将图片内容解析成字节数组
				mContent = ImageUtil.readStream(resolver.openInputStream(Uri
						.parse(originalUri.toString())));
				// 将字节数组转换为ImageView可调用的Bitmap对象
				myBitmap = ImageUtil.getPicFromBytes(mContent, null);
				myBitmap = comp(myBitmap);
				// //把得到的图片绑定在控件上显示
				group_head.setImageBitmap(myBitmap);

			} catch (Exception e) {
				// System.out.println(e.getMessage());
			}

		} else if (requestCode == REQUEST_CAMERA) {
			try {
				super.onActivityResult(requestCode, resultCode, data);
				Bundle extras = data.getExtras();
				myBitmap = (Bitmap) extras.get("data");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				mContent = baos.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 把得到的图片绑定在控件上显示
			group_head.setImageBitmap(myBitmap);// 把拍摄的照片转成圆角显示在预览控件上
		}
		if (myBitmap != null) {
			ImageUtil.bitmaptoString(myBitmap);
		}
		// submit();
	}

	// 压缩图片方法
	private Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 20, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;// 压缩好比例大小后再进行质量压缩
	}

	// 广播
	private class friendBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			// 重新发一次请求，好更新friend 列表
			finish();
		}

	}

	@Override
	protected void onDestroy() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			if (mBDLocationListener != null) {
				mLocationClient.unRegisterLocationListener(mBDLocationListener);
			}

			mLocationClient.stop();
			mLocationClient = null;
		}
		unregisterReceiver(receiver);
		super.onDestroy();
	}
}
