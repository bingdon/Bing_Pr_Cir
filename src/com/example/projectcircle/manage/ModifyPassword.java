package com.example.projectcircle.manage;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 修改密码
 * 
 */
public class ModifyPassword extends Activity implements OnClickListener {
	Button back, submit;
	EditText pwd_past_txt, pwd_new_txt, pwd_again_txt;
	String pwd_past, pwd_new, pwd_again;
	String id;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_password);
		initView();
		id = LoginActivity.id;
		UserDetail(id);
		
	}

	/**
	 * 个人信息 请求和解析
	 * 
	 */
	private void UserDetail(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("修改密码", "返回:" + response);
				parseUserDetail(response);
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail(id, res);
	}

	public void parseUserDetail(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");
			password = obj.getString("password");
			// password从后台获取的密码 也就是原密码
			// password获取之后打印出来 发现密码被加密了，问问王丹如何解密，还是说不要原密码直接更改 看她接口怎么做了
			Log.i("password", "password=" + password);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.modify_pwd_left);
		submit = (Button) findViewById(R.id.modify_pwd_submit);
		pwd_past_txt = (EditText) findViewById(R.id.modify_pwd_past);
		pwd_new_txt = (EditText) findViewById(R.id.modify_pwd_new);
		pwd_again_txt = (EditText) findViewById(R.id.modify_pwd_again);

		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.modify_pwd_left:
			finish();
			break;
		case R.id.modify_pwd_submit:
			submit();
			break;
		default:
			break;
		}
	}

	private void submit() {
		// TODO Auto-generated method stub
		pwd_past = pwd_past_txt.getText().toString();
		pwd_new = pwd_new_txt.getText().toString();
		pwd_again = pwd_again_txt.getText().toString();

//		if (!pwd_past.equals(password)) {
//			ToastUtils.showShort(getApplicationContext(), "原密码有误，请重新输入！");
//			return;
//		}
		if (pwd_new.length() != 6) {
			ToastUtils.showShort(getApplicationContext(), "密码必须6位数字或字母！");
			return;
		}
		if (!pwd_again.equals(pwd_new)) {
			ToastUtils.showShort(getApplicationContext(), "新密码不一致，请重新输入！");
			return;
		}
		if (!TextUtils.isEmpty(pwd_past) && !TextUtils.isEmpty(pwd_new)
				&& !TextUtils.isEmpty(pwd_again)) {
			// 在这里调用接口 修改成功把下面代码放进去 否则修改失败
			//判断用户输入的密码是否正确
			PwdIsTrue(id,pwd_past,pwd_new);
		
			finish();
		}
	}
	//判断用户输入的密码是否正确
	private void PwdIsTrue(String id, String pwd_past,String pwd_new) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("pwd is true?", "返回:" + response);
			try {
				JSONObject obj= new JSONObject(response);
				if(obj.getInt("result") == 1){
					Log.i("原密码匹配", "原密码匹配");
					LoginActivity.savePwd(pwd_new_txt.getText().toString(), ModifyPassword.this);
				}else{
				ToastUtils.showShort(getApplicationContext(), "原密码错误，请重新输入！");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1, arg2, arg3);
				Log.i("failuer ", "failuer");
	
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.doPwdIsTrue(id,pwd_past,pwd_new, res);
	}
}
