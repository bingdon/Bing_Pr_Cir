package com.example.projectcircle.complete;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.adpter.ImageAdapter;
import com.example.projectcircle.util.ImageUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CompleteInfo extends Activity implements OnClickListener {
	/**
	 * 头像
	 */
	ImageView myhead;
	GridView gridview;
	ImageAdapter imageAdapter;
	// private static final int[] images = new int[2];
	// 上传头像所需要的
	private static final int REQUEST_CAMERA = 1;
	int current_index;// 标记当前是在存储哪个头像 设备？个人？
	private Bitmap myBitmap;
	private byte[] mContent;
	String returnString;
	String headimage;
	// 标记照的是第几次
	int index = 0;
	/**
	 * EditText 个人签名 常出没的地方 兴趣爱好 个人简介
	 */
	//设备三张图片的Bitmap
	private Bitmap myBitmap1;
	private Bitmap myBitmap2;
	private Bitmap myBitmap3;
	private String wajue_equid = FootActivity1.equid;//挖掘机的设备id
	private String zixie_equid = FootActivity2.equid;//自卸车的设备id
	private String pingban_equid = FootActivity3.equid;//平板车的设备id
	private String zhuangzai_equid = FootActivity4.equid;//装载的设备id
	private String other_equid = FootActivity5.equid;//其他的设备id
	EditText my_sign, my_intro, my_address, my_hobby;
	String sign, intro, address, hobby;

	/**
	 * 其它Button
	 */
	Button back, next;

	String id;
	private ProgressDialog progressDialog;
	private ImageView mydevice2;
	private ImageView mydevice3;
	private ImageView mydevice1;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_info);
		progressDialog=new ProgressDialog(this);
		initFilter();
		initBtn();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		myhead = (ImageView) findViewById(R.id.perfect_info_camera);
//		gridview = (GridView) findViewById(R.id.gridview);
//		imageAdapter = new ImageAdapter(getList(), this);
//		gridview.setAdapter(imageAdapter);
		mydevice1 = (ImageView) findViewById(R.id.perfect_info_camera1);
		mydevice2 = (ImageView) findViewById(R.id.perfect_info_camera2);
		mydevice3 = (ImageView) findViewById(R.id.perfect_info_camera3);
		my_sign = (EditText) findViewById(R.id.personalized_signature);
		my_intro = (EditText) findViewById(R.id.personalized_jianjie);
		my_address = (EditText) findViewById(R.id.personalized_address);
		my_hobby = (EditText) findViewById(R.id.personalized_hobby);

		myhead.setOnClickListener(this);
		mydevice1.setOnClickListener(this);
		mydevice2.setOnClickListener(this);
		mydevice3.setOnClickListener(this);
//		gridview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				current_index = 1;
//				MyDialog();
//			}
//		});
	}

//	private ArrayList<HashMap<String, Object>> getList() {
//		// TODO Auto-generated method stub
//		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
//		for (int i = 0; i < 3; i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("headimage", R.drawable.camera);
//
//			listItem.add(map);
//		}
//		return listItem;
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.perfect_info_camera:
			current_index = 0;
			MyDialog();
			break;
		case R.id.perfect_info_camera1:
			current_index = 1;
			MyDialog();
			break;
		case R.id.perfect_info_camera2:
			current_index = 2;
			MyDialog();
			break;
		case R.id.perfect_info_camera3:
			current_index =3;
			MyDialog();
			break;
		default:
			break;
		}
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.perfect_info_left);
		next = (Button) findViewById(R.id.perfect_info_next);
		back.setOnClickListener(listener);
		next.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.perfect_info_left:
				finish();
				break;
			case R.id.perfect_info_next:

				submit();
				break;
			default:
				break;
			}
		}
	};
	private String type;
	
	/**
	 * 上传数据
	 */
	@SuppressWarnings("deprecation")
	private void submit() {
		// TODO Auto-generated method stub
		id = SiginActivity.id;
		type = SiginActivity.type;		
		// id = "25";
		sign = my_sign.getText().toString();
		intro = my_intro.getText().toString();
		address = my_address.getText().toString();
		hobby = my_hobby.getText().toString();

		headimage = "";
		if (myBitmap != null) {
			headimage = URLEncoder.encode(ImageUtil.bitmaptoString(myBitmap));
			headimage = ImageUtil.bitmaptoString(myBitmap);
			// System.out.println(headimage);
			postHeadImage(id, headimage);
		}	
		 if (myBitmap1!= null) {			
//				headimage = URLEncoder.encode(ImageUtil.bitmaptoString(myBitmap1));
				headimage = ImageUtil.bitmaptoString(myBitmap1);
				// System.out.println(headimage);
				postEquHeadImage(id, headimage);
			}if (myBitmap2!= null) {			
//				headimage = URLEncoder.encode(ImageUtil.bitmaptoString(myBitmap2));
				headimage = ImageUtil.bitmaptoString(myBitmap2);
				// System.out.println(headimage);
				postEquHeadImage(id, headimage);
			}if (myBitmap3!= null) {			
//				headimage = URLEncoder.encode(ImageUtil.bitmaptoString(myBitmap3));
				headimage = ImageUtil.bitmaptoString(myBitmap3);
				// System.out.println(headimage);
				postEquHeadImage(id, headimage);
			}	
			baseInfo(id, sign, address, hobby, intro);		
	}
    //上传三张照片
	private void postEquHeadImage(String id, String headimage) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				Log.i("上传三张照片result","开始上传");	
			}
			public void onSuccess(String response) {
				// System.out.println(response);
				Log.i("上传三张照片result","上传三张照片result"+ response);	
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("上传三张照片result","上传三张照片result"+ obj.getInt("result") + "");				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.upLoadEquHeadImage(id, headimage, res);
	}

	private void baseInfo(String id, String sign, String place, String hobby,
			String info) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				progressDialog.setMessage(getString(R.string.submiting));
				progressDialog.show();
			}
			
			public void onSuccess(String response) {
				// System.out.println(response);
				// parseInfo(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						postfinish();
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				progressDialog.dismiss();
			}
			

		};
		MyHttpClient client = new MyHttpClient();
		client.BaseInfo(id, sign, place, hobby, info, res);
	}

	private void postHeadImage(String id, String headimage) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				// System.out.println(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.postHeadImage(id, headimage, res);
	}

	private void postfinish() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "添加成功！", Toast.LENGTH_LONG)
				.show();
		myhead.setImageBitmap(myBitmap);
		Intent intent = new Intent(CompleteInfo.this, CompleteFinish.class);
		startActivity(intent);
		//发广播，把前边注册的那几个界面finish掉
		Intent intent2 = new Intent();
		intent2.setAction("finish.before.regist.page");			
		CompleteInfo.this.sendBroadcast(intent);
		finish();
	}

	// 图片上传选择途径
	private void MyDialog() {
		final CharSequence[] items = { "相册", "拍照" };
		AlertDialog dlg = new AlertDialog.Builder(CompleteInfo.this)
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
				myBitmap = ImageUtil.toRoundCorner(myBitmap, 20);
				// //把得到的图片绑定在控件上显示
				if (current_index == 0) {
					myhead.setImageBitmap(myBitmap);
				} else if(current_index == 1){
					myBitmap1 = myBitmap;
					mydevice1.setImageBitmap(myBitmap1);
				} else if(current_index == 2){
					myBitmap2 = myBitmap;
					mydevice2.setImageBitmap(myBitmap2);
				} else if(current_index == 3){
					myBitmap3 = myBitmap;
					mydevice3.setImageBitmap(myBitmap3);
				}

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
				myBitmap = ImageUtil.toRoundCorner(myBitmap, 20);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 把拍摄的照片转成圆角显示在预览控件上
			if (current_index == 0) {
				myhead.setImageBitmap(myBitmap);
			}  else if(current_index == 1){
				myBitmap1 = myBitmap;
				mydevice1.setImageBitmap(myBitmap1);
			}  else if(current_index == 2){
				myBitmap2 = myBitmap;
				mydevice2.setImageBitmap(myBitmap2);
			} else if(current_index == 3){
				myBitmap3 = myBitmap;
				mydevice3.setImageBitmap(myBitmap3);
		}
		}
		if (myBitmap != null) {
			ImageUtil.bitmaptoString(myBitmap);
		}
		// submit();
	}

	// 压缩图片方法
	public static Bitmap comp(Bitmap image) {

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
	private void initFilter() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish.before.regist.page");		
		registerReceiver(msgReceiver, filter);
	}
	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub			
			finish();		
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}
}
