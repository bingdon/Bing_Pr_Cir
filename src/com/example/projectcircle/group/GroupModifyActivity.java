package com.example.projectcircle.group;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.json.JSONObject;

import com.example.projectcircle.R;
import com.example.projectcircle.bean.GroupInfo;
import com.example.projectcircle.setting.ModifyInfoActivity;
import com.example.projectcircle.util.ImageUtil;
import com.example.projectcircle.util.LoadImageUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class GroupModifyActivity extends Activity {

	private GroupInfo groupInfo;

	private ImageView headImageView;

	private EditText nameEditText;

	private EditText introEditText;

	private Context context;

	private byte[] mContent;

	private String groupName;

	private String groupIntro;

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_modify);
		context = this;
		initUI();
	}

	private void initUI() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("提交中...");
		headImageView = (ImageView) findViewById(R.id.head);
		nameEditText = (EditText) findViewById(R.id.group_name);
		introEditText = (EditText) findViewById(R.id.group_introduce);
		headImageView.setOnClickListener(listener);
		findViewById(R.id.modify_head_submit).setOnClickListener(listener);
		findViewById(R.id.g_detail_left).setOnClickListener(listener);
		initData();
	}

	private void initData() {
		groupInfo = (GroupInfo) getIntent().getSerializableExtra("info");
		LoadImageUtils.displayImg(
				MyHttpClient.IMAGE_URL + groupInfo.getHeadimage(),
				headImageView);
		nameEditText.setText(groupInfo.getGname());
		introEditText.setText(groupInfo.getContent());
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			switch (id) {
			case R.id.head:
				MyDialog();
				break;
			case R.id.modify_head_submit:
				submit();
				break;

			case R.id.g_detail_left:
				finish();
				break;
				
			default:
				break;
			}

		}
	};
	private Bitmap myBitmap;

	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		ContentResolver resolver = getContentResolver();
		if (requestCode == 0) {
			Uri originalUri = data.getData();
			// 将图片内容解析成字节数组
			try {
				mContent = ImageUtil.readStream(resolver.openInputStream(Uri
						.parse(originalUri.toString())));
				myBitmap = ImageUtil.getPicFromBytes(mContent, null);
				myBitmap = ModifyInfoActivity.comp(myBitmap);
				headImageView.setImageBitmap(myBitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Bundle extras = data.getExtras();
			myBitmap = (Bitmap) extras.get("data");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			mContent = baos.toByteArray();
			myBitmap = ImageUtil.toRoundCorner(myBitmap, 20);
			headImageView.setImageBitmap(myBitmap);
		}

	};

	private static final int REQUEST_CAMERA = 1;

	private void MyDialog() {
		final CharSequence[] items = { "相册", "拍照" };
		AlertDialog dlg = new AlertDialog.Builder(context).setTitle("选择图片")
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

	private void submit() {
		groupName = nameEditText.getText().toString();
		groupIntro = introEditText.getText().toString();
		if (TextUtils.isEmpty(groupName)) {
			nameEditText.setError("名字不能为空");
			return;
		}

		MyHttpClient.modifyGroupInfo(groupInfo.getId(), groupIntro, groupName,
				handler);
		if (myBitmap == null) {
			return;
		}
		new Thread() {
			public void run() {
				try {
					new MyHttpClient().postGroupHeadImage(groupInfo.getId(),
							ImageUtil.bitmaptoString(myBitmap),
							new AsyncHttpResponseHandler());
				} catch (Exception e) {
					// TODO: handle exception
				}

			};
		}.start();

	}

	private JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, response);
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			super.onFinish();
			progressDialog.dismiss();
			finish();
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			progressDialog.show();
		}

	};

}
