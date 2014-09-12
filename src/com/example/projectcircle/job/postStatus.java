package com.example.projectcircle.job;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.PublishAdapter;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.db.interfaces.PicClickListener;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.NoticeUtils;
import com.example.projectcircle.util.PhotoUtils;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class postStatus extends Activity implements PicClickListener {
	EditText mood_id;
	String context,userid,id,name;
	Button post_mood,button_back;
    ImageView add_photo;	
	private ImageView image_id;
	//private int    headimge;
    private static final int REQUEST_CAMERA = 1;//上传头像所需要的
	private static final String TAG = null;
    private byte[] mContent;
	 Bitmap myBitmap;
	private GridView photo_gridview;
	ArrayList<Bitmap> photo_array;
	private ImageView photo_post;
	private String image;
	private String myphoto;
	private String headimage;
	private ProgressDialog progressDialog;
	private GridView publishGridView;
	private PublishAdapter publishAdapter;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Button submit_button;
	private TextView contentText;
	private String content;//发表的内容
	String moodid = "";
	private View submintView;
	private RelativeLayout post_status_layout;
	

	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置头部title不让它显示
		setContentView(R.layout.post_status);
		progressDialog=new ProgressDialog(this);
		userid = LoginActivity.id;
		initView();
		//发布状态
       // postSatus();
        back();
	}

	private void initView() {
		// TODO Auto-generated method stub
		submintView = getLayoutInflater().inflate(R.layout.submit_layout, null);
		post_status_layout = (RelativeLayout) findViewById(R.id.wrapper);
		submit_button=(Button)findViewById(R.id.name_submit_btn);
		contentText =(TextView)findViewById(R.id.editText1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", R.drawable.publish_sec);
		list.add(map);
		publishGridView = (GridView) findViewById(R.id.publish_gridView1);
		publishAdapter = new PublishAdapter(this, list);
		publishGridView.setAdapter(publishAdapter);
		publishAdapter.setPicClickListerter(this);
		submit_button.setOnClickListener(listener);
	}
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		content = contentText.getText().toString();
        postMood(userid,content);    
		}
	};


	protected void postMood(String uid,String context) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {	
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				progressDialog.setMessage(getString(R.string.submiting));
				progressDialog.show();
		
			}


			public void onSuccess(String response) {
				System.out.println(response);
				parseUser(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {									
						JSONObject moodobj = obj.getJSONObject("mood");
						 moodid =moodobj.getString("id");
                        Log.i("moodid", moodid+"");
				        listCircle(moodid);
					   
					} else {
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();				
				} 
			}

			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
		

			}
	
		};
		//Log.i("response  mybmp==", mybmp);
		MyHttpClient myhttpclient = new MyHttpClient();
		myhttpclient.PostMood(userid, context, res);
	}
	protected void listCircle(final String moodid) {
		// TODO Auto-generated method stub
		if (list.size() < 2) {
//			confirShai();
			progressDialog.dismiss();
			finish();
			return;
		}
		// }
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
		
		       MyHttpClient.postMoodImg(moodid, bitmapNCutToString(list.get(0).get("url")
				.toString()), new UpPicHandler(0));
			}
			}).start();
	}
	public class UpPicHandler extends AsyncHttpResponseHandler {	
		private int index = 0;      
		public UpPicHandler(int index) {
			this.index = index;		
			Log.i("index-----", index+"");
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();			
			Log.i("index", "开始" + index);				
		}

		@Override
		public void onSuccess(String content) {
			// TODO Auto-generated method stub
			super.onSuccess(content);
			Log.i("content", "返回:" + content);
			Log.i("index", "完成" + index);
			Log.i("大小:", "大小:" + list.size());
//			NoticeUtils.showProgressPublish(postStatus.this, index, list.size() - 2, ContantS.PUBLISH_SHAI_ID);
			if (index < list.size() - 2) {
				index++;		
				MyHttpClient.postMoodImg(
						moodid,
						bitmapNCutToString(list.get(index)
								.get("url").toString()),
						new UpPicHandler(index));
				Log.i("-----moodid-----", moodid);
				Log.i("list.get(index).geturl.toString()", list.get(index).get("url").toString());			
			} else {				
				//发送广播
		    	Intent intent = new Intent(); 
				intent.setAction("refesh.CircleAdapter"); 
				intent.putExtra("msg", "you must refesh the CircleAdapter!!!!!!!!!!!!!!"); 
				postStatus.this.sendBroadcast(intent);
				progressDialog.dismiss();
				finish();
			}

		}

		@SuppressWarnings("deprecation")
		@Override
		public void onFailure(Throwable error, String content) {
			// TODO Auto-generated method stub
			super.onFailure(error, content);

		}

	}


	private void parseUser(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("mood");
			id = obj.getString("userid");
//			headimage = obj.getString("headimage");
			context = obj.getString("context");
			Log.i("response  id==", id);	
			Log.i("response  context==", context);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	private void back() {
		// TODO Auto-generated method stub
		button_back = (Button) findViewById(R.id.n_group_left);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				postStatus.this.setResult(RESULT_OK, getIntent());
				postStatus.this.finish();
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK) {
			return;
		}

		if (requestCode == 0) {
			try {

				final String str;
				Uri localUri = data.getData();
				String[] arrayOfString = new String[1];
				arrayOfString[0] = "_data";
				Cursor localCursor = getContentResolver().query(localUri,
						arrayOfString, null, null, null);
				if (localCursor == null)
					return;
				localCursor.moveToFirst();
				str = localCursor.getString(localCursor
						.getColumnIndex(arrayOfString[0]));
				localCursor.close();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("url", str);
				list.add(0, map);
				Log.i(TAG, "地址:" + str);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (requestCode == 1) {
			try {
				String path = PhotoUtils.getPicPathFromUri(
						PhotoUtils.imageFileUri, this);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("url", path);
				list.add(0, map);
				Log.i(TAG, "地址:" + path);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		publishAdapter.notifyDataSetChanged();

		super.onActivityResult(requestCode, resultCode, data);
		// submit();
	}

//	private ArrayList<HashMap<String, Object>> getList() {
//		// TODO Auto-generated method stub
//	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
//		Log.i(TAG, "返回:photo_array" + photo_array);
//		if(photo_array != null){
//		for (int i = 0; i < photo_array.size(); i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("photo_array", photo_array.get(i));
////			map.put("gaddress", groupList.get(i).getGaddress());
////			map.put("content", groupList.get(i).getContent());
////			map.put("headimage", groupList.get(i).getHeadimage());
////			map.put("people", people_num);
//			listItem.add(map);
//		}
//		}
//		return listItem;
//	}
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

	@Override
	public void onPicClick(int position) {
		// TODO Auto-generated method stub
		Log.i(TAG, "position:" + position + "::" + list.size());
		if (position == list.size() - 1 && position < 4) {
			PhotoUtils.secPic(postStatus.this);
		}
	}
	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static synchronized String bitmapNCutToString(String filePath) {

		Bitmap bm = getNoCutSmallBitmap(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}
	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getNoCutSmallBitmap(String filePath) {

//		Matrix matrix = new Matrix();
//		matrix.setRotate(ScanningActivity.angle);

	
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 400, 400);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		
		Bitmap mBitmap = BitmapFactory.decodeFile(filePath, options);
//		float width=mBitmap.getWidth();
//		float height=mBitmap.getHeight();
//		float ratio=width/height;
//		mBitmap = Bitmap.createBitmap(mBitmap, (int) (mBitmap.getWidth()/3),
//				(int) (mBitmap.getHeight()-mBitmap.getHeight()*ratio/3)/2, (int) (mBitmap.getWidth()/3),
//				(int) (mBitmap.getHeight() / 3*ratio), matrix, true);
		

		return mBitmap;
	}
	
	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}


}
