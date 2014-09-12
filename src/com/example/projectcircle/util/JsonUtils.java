package com.example.projectcircle.util;

import org.json.JSONObject;

import com.example.projectcircle.bean.MoodBean;
import com.example.projectcircle.debug.AppLog;
import com.google.gson.Gson;

public class JsonUtils {

	private static final String TAG = JsonUtils.class.getSimpleName();

	public static MoodBean getMoodBean(JSONObject object) {
		MoodBean moodBean = new MoodBean();
		try {
			moodBean = new Gson().fromJson(object.toString(), MoodBean.class);
		} catch (Exception e) {
			// TODO: handle exception
			AppLog.d(TAG, "½âÎö³ö´í");

		}

		return moodBean;
	}

}
