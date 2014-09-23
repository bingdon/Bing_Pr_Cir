package com.example.projectcircle.friend.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FriendRequestUtils {

	public static int friendQuset = 0;

	public static void requestNum() {
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", response);
				friendQuset = parseFriendRequestList(response);
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.FriendRequestMessage(LoginActivity.id, res);

	}

	private static int parseFriendRequestList(String response) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			Log.i("解析好友请求的response", response + "");
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("friends");
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			count = length;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return count;

	}

}
