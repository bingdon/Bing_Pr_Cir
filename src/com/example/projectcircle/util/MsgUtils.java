package com.example.projectcircle.util;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.projectcircle.bean.MsgDataBean;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.db.utils.MsgDataUtils;
import com.example.projectcircle.friend.FriendPage;

public class MsgUtils {

	private static final String TAG=MsgDataUtils.class.getSimpleName();
	
	/**
	 * 判断是否有记录
	 * @param id
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isExitMsgList(String id, Context context) {
		MsgDataUtils msgDataUtils = new MsgDataUtils(context);
		List<MsgDataBean> msgDataBeans = (List<MsgDataBean>) msgDataUtils
				.queryData();
		if (null != msgDataBeans) {
			int length = msgDataBeans.size();
			for (int i = 0; i < length; i++) {
				if (id.equals(msgDataBeans.get(i).getFriend_id())) {
					return true;
				}
			}

		}

		return false;

	}
	
	/**
	 * 保存聊天列表
	 * @param name
	 * @param content
	 * @param time
	 * @param id
	 * @param headimg
	 * @param unread
	 * @param context
	 */
	public static void saveMsgList(String name, String content, String time,
			String id, String headimg,int type,int unread,Context context) {
			MsgDataUtils msgDataUtils = new MsgDataUtils(context);

		long m = msgDataUtils.update(name, id, content, headimg, time,type,unread);
		Log.i(TAG, "更新:" + m);
		if (m < 1) {
			m = msgDataUtils.insert(name, id, content, headimg, time,type,unread);
			Log.i(TAG, "插入"+m);
		}

		
		Intent intent = new Intent();
		intent.setAction(ContantS.ACTION_GET_MSG_PER);
		if (type==0) {
			intent.putExtra("id", id);
		}else {
			intent.putExtra("gid", id);
		}
		
		context.sendBroadcast(intent);
		
	}
	
}
