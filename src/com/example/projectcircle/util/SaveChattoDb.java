package com.example.projectcircle.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.utils.FriendChatUtils;
import com.example.projectcircle.db.utils.GroupChatUtils;
import com.example.projectcircle.other.Chat;
import com.example.projectcircle.other.ChatMsgEntity;
import com.google.gson.Gson;

public class SaveChattoDb {

	/**
	 * 保存个人聊天
	 * 
	 * @param message
	 *            消息
	 * @param context
	 *            上下文
	 */
	public static void saveChat2db(String message, Context context) {

		String msg = getMsg(message);
		String id = getId(message);
		if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(msg)) {
//			SaveMsg2View(msg, context, id);
			_SaveMsg2View(message, context, id);
		}

	}

	/**
	 * 保存群组聊天
	 * 
	 * @param message
	 *            消息
	 * @param context
	 *            上下文
	 */
	public static void saveGroupChat2db(String message, Context context) {

		String msg = getMsg(message);
		String id = getgId(message);
		if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(msg)) {
			
			MyPersonBean myPersonBean=getPersonInfo(message);
			
			SaveMsg2View(msg, context,id, myPersonBean.getHeadimage(), myPersonBean.getId());
		}

	}

	private static String getMsg(String message) {
		String msg = "";
		try {
			JSONObject jsonObject = new JSONObject(message);
			JSONObject content = jsonObject.getJSONObject("content");
			msg = content.getString("content");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	public static String getId(String message) {
		String id = "";
		try {
			JSONObject jsonObject = new JSONObject(message);
			JSONObject content = jsonObject.getJSONObject("content");
			id = content.getString("senduid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;

	}

	public static String getgId(String message) {
		String id = "";
		try {
			JSONObject jsonObject = new JSONObject(message);
			JSONObject content = jsonObject.getJSONObject("content");
			id = content.getString("gid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;

	}

	public static MyPersonBean getPersonInfo(String message) {
		MyPersonBean myPersonBean = new MyPersonBean();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(message);
			JSONObject jsonObject2 = jsonObject.getJSONObject("user");
			Gson gson = new Gson();
			myPersonBean = gson.fromJson(jsonObject2.toString(),
					MyPersonBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Log.e(MyPersonBean.class.getSimpleName(), "数据解析错误");
			
		}

		return myPersonBean;

	}

	private static void SaveMsg2View(String msg, Context context, String id) {
		ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
		chatMsgEntity.setDate(Chat.getDate());
		chatMsgEntity.setMsgType(true);
		chatMsgEntity.setText(msg);
		save(chatMsgEntity, context, id);
	}

	
	private static void _SaveMsg2View(String message, Context context, String id) {
		String msg=getMsg(message);
		try {
			JSONObject jsonObject=new JSONObject(message);
			MyPersonBean myPersonBean=new Gson().fromJson(jsonObject.getString("user"), MyPersonBean.class);
			ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
			chatMsgEntity.setDate(Chat.getDate());
			chatMsgEntity.setMsgType(true);
			chatMsgEntity.setText(msg);
			chatMsgEntity.setHeadimgString(myPersonBean.getHeadimage());
			chatMsgEntity.setName(myPersonBean.getUsername());
			save(chatMsgEntity, context, id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	/**
	 * 保存群组消息
	 * @param msg
	 * @param context
	 * @param gid
	 * @param headimage
	 * @param uid
	 */
	public static void SaveMsg2View(String msg, Context context, String gid,
			String headimage, String uid) {
		ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
		chatMsgEntity.setDate(Chat.getDate());
		chatMsgEntity.setHeadimgString(headimage);
		chatMsgEntity.setTime(System.currentTimeMillis());
		chatMsgEntity.setUid(uid);
		chatMsgEntity.setMsgType(true);
		chatMsgEntity.setText(msg);
		saveGroupChat(chatMsgEntity, context, gid);
	}

	private static void save(ChatMsgEntity chatMsgEntity, Context context,
			String id) {
		ProJectDatebase.createFriTab(id, context);
		FriendChatUtils friendChatUtils = new FriendChatUtils(context, id);
		int iscom = 0;
		if (chatMsgEntity.getMsgType()) {
			iscom = 1;
		}
//		friendChatUtils.insert(chatMsgEntity.getText(),
//				chatMsgEntity.getDate(), chatMsgEntity.getDate(), iscom);
		friendChatUtils.insert(chatMsgEntity);

	}

	/**
	 * 保存群组聊天数据
	 * @param chatMsgEntity 数据
	 * @param context 上下文
	 * @param gid 群组ID
	 */
	private static void saveGroupChat(ChatMsgEntity chatMsgEntity,
			Context context, String gid) {
		ProJectDatebase.createGrouTable(gid, context);
		GroupChatUtils groupChatUtils = new GroupChatUtils(context, gid);
		int iscom = 0;
		if (chatMsgEntity.getMsgType()) {
			iscom = 1;
		}
		groupChatUtils.insert(chatMsgEntity.getUid(), chatMsgEntity.getUid(),
				chatMsgEntity.getHeadimgString(), chatMsgEntity.getText(),
				chatMsgEntity.getDate(), chatMsgEntity.getDate(), iscom);

	}

}
