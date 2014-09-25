package com.example.projectcircle.friend;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.MainActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.personal.PersonalPage;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class AddFriend extends Activity {
	Button button;
	private Button search_friend;
	protected String context;
	private EditText search_editText;
	 String id;
	String name,address, equipment, username, headimage, place, type, info;
	String []a = new String [6];	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_friend);			
		searchFriends();
		back();	
	}
	private void searchFriends() {
		// TODO Auto-generated method stub
		//查找好友
		search_friend =  (Button)findViewById(R.id.search_friend);
		search_friend.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				search_editText = (EditText) findViewById(R.id.search_editText);
				context = search_editText.getText().toString();				
				if (TextUtils.isEmpty(context)) {
					ToastUtils.showShort(getApplicationContext(), "请输入工程号或者手机号");
					return;
				}
				
				if (!TextUtils.isEmpty(context)) {
					
					dosearchfriend(context);
					}
			}
		});	
	}
	protected void dosearchfriend(String context) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				System.out.println(response);
				parsefriend(response);
				JSONObject obj;				
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {				
					Intent intent = new Intent(AddFriend.this,
								PersonalPage.class);
					intent.putExtra("id",
							id); 
					intent.putExtra("address",
							address); 
					intent.putExtra("equipment",
							equipment); 
					intent.putExtra("headimage",
							headimage); 
					intent.putExtra("username",
							username); 
					intent.putExtra("place",
							place);
					intent.putExtra("type",
							type); 
					intent.putExtra("info",
							info); 
					startActivity(intent);
					finish();
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"未搜索到相应的好友！");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}
		};
		MyHttpClient myhttpclient = new MyHttpClient();
		myhttpclient.searchfriend(context, res);
	}
	private void parsefriend(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");
			id = obj.getString("id");//工程号
			address = obj.getString("address");//籍贯
			equipment = obj.getString("equipment");//设备
			username = obj.getString("username");//名字
			headimage = obj.getString("headimage");//头像
			place = obj.getString("place");//常出没的地方
			type = obj.getString("type");//机主			
			info = obj.getString("info");
			Log.i("response  username==", username);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	private void back() {
		// TODO Auto-generated method stub
		button = (Button) findViewById(R.id.n_group_left);
		button.setOnClickListener(new  OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AddFriend.this.setResult(RESULT_OK, getIntent());
				AddFriend.this.finish();
			}});
	}

}
