package com.example.projectcircle.personal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.projectcircle.R;
import com.example.projectcircle.adpter.CircleAdapter;
import com.example.projectcircle.bean.MoodInfo;
import com.example.projectcircle.bean.StatusInfo;
import com.example.projectcircle.job.postStatus;
import com.example.projectcircle.other.Chat;
import com.example.projectcircle.util.BingDateUtils;
import com.example.projectcircle.util.MsgUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.TimeUtility;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.BingListView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HisDynamic extends Activity {
	private static final String TAG = null;
	private String id;
	private ArrayList<StatusInfo> mymoodList;
	private ListView listview;
	private CircleAdapter myAdapter;
	private ArrayList<StatusInfo> moodList;
	private Button project_circle_right;
	private Button project_circle_left;
	private TextView textView1;
	private String username;
	private BingListView bingListView;
	private String pingluncontent;//列出来的评论的内容
	private ArrayList<MoodInfo> usermoodsList = new ArrayList<MoodInfo>();
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_circle);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		username = intent.getStringExtra("username");
		bingListView = (BingListView) findViewById(R.id.m_listview);
		initButton();
		doListMyMood();
	}

	private void initButton() {
		// TODO Auto-generated method stub
	
		project_circle_right = (Button)findViewById(R.id.project_circle_right);
		project_circle_right.setVisibility(View.GONE);
		textView1 = (TextView)findViewById(R.id.textView1);
		textView1.setText(username);
		project_circle_left = (Button)findViewById(R.id.project_circle_left);
		project_circle_left.setOnClickListener(btnlistener);
	}
	private View.OnClickListener btnlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			HisDynamic.this.setResult(RESULT_OK, getIntent());
			HisDynamic.this.finish();
			}
};

	private void doListMyMood() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

					public void onSuccess(String response) {
						System.out.println(response);
						parseListMyMood(response);
						JSONObject obj;				
						try {
							obj = new JSONObject(response);
							if (obj.getInt("result") == 1) {			
							list_circle();
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
				myhttpclient.userMood(id,res);
	}

	protected void list_circle() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub	
		listItem.clear();
		Log.i("清空后的的listItem", listItem+"");
		listItem = getList_Mood();
		Log.i("重新获取的listItem", listItem+"");
		myAdapter = new CircleAdapter(listItem, this);
		myAdapter.notifyDataSetChanged();
		bingListView.setAdapter(myAdapter);
//		myAdapter.setOnClickListener(this);
		// 点击返回键，返回
//		back();
	}

	private void parseListMyMood(String response) {
		// TODO Auto-generated method stub
		 try {
				JSONObject objo = new JSONObject(response);
				JSONArray moods =	objo.getJSONArray("moods");
				for(int i = 0; i < moods.length() ; i++){
					MoodInfo moodinfo = new MoodInfo();
					moodinfo.setMoodid(moods.getJSONObject(i).getString("id"));
					moodinfo.setContext(moods.getJSONObject(i).getString("context"));
					moodinfo.setMoodid(moods.getJSONObject(i).getString("id"));
					moodinfo.setCommentcount(moods.getJSONObject(i).getString("commentcount"));
					moodinfo.setLaudcount(moods.getJSONObject(i).getString("laudcount"));
//					moodinfo.setCommentid(moods.getJSONObject(i).getJSONObject("user").getString("commentid"));
					moodinfo.setUsername(moods.getJSONObject(i).getJSONObject("user").getString("username"));
					moodinfo.setUheadimg(moods.getJSONObject(i).getJSONObject("user").getString("headimage"));
					moodinfo.setTime(moods.getJSONObject(i).getString("createtime"));
					JSONArray imgs = moods.getJSONObject(i).getJSONArray("img");
					List<String> list = new ArrayList<String>();
					for (int j = 0; j < imgs.length(); j++) {
						list.add(MyHttpClient.IMAGE_URL + imgs.get(j));
						Log.i("imageURL",MyHttpClient.IMAGE_URL + imgs.get(j));
					}
					
					moodinfo.setImgs(list);
					int commentlength = moods.getJSONObject(i).getJSONArray("comment").length();
					Log.i("commentlength", commentlength+"");
					Log.i("----comment----", moods.getJSONObject(i).getJSONArray("comment")+"");
					String comString = "";
					if(commentlength > 0){
					for (int j = 0; j < commentlength; j++) {
						JSONObject comment = moods.getJSONObject(i).getJSONArray("comment")
								.getJSONObject(j);
						    try{	
						    	
						pingluncontent = comment.getString("content");
						
						}catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					   JSONObject userobj=	comment.getJSONObject("user");
					   String  username =  userobj.getString("username");
									
					   comString = comString +username + ":" + pingluncontent + "\n";
					}
					moodinfo.setComment(comString);
					}
					usermoodsList.add(moodinfo);
				
		 }
			
			 } catch (JSONException e) {
			 e.printStackTrace();
			 }
	}
	private ArrayList<HashMap<String, Object>> getList_Mood() {
		Log.i("返回:usermoodsList", "返回:usermoodsList" + usermoodsList);
		
		if(usermoodsList != null){
		for (int i = 0; i < usermoodsList.size(); i++) {
			 MoodInfo moods = usermoodsList.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();		
			map.put("time", moods.getTime());
			map.put("moodid", moods.getMoodid());
			map.put("username", moods.getUsername());
			map.put("headimage", moods.getUheadimg());
			map.put("commentcount", moods.getCommentcount());
//			map.put("commentid", moods.getCommentid());
			map.put("laudcount", moods.getLaudcount());
			map.put("context", moods.getContext());
			map.put("username", moods.getUsername());
			map.put("comment", moods.getComment());
			List<String> imgurl = moods.getImgs();
			map.put("grid_pic", imgurl);
			listItem.add(map);
		}
		Log.i("----listItem------", listItem+"");
	}
		//时间排序
		bingSort(listItem);
		Log.i("按时间排序后的listItem", listItem+"");
		//时间转换成昨天几点、今天几点。。。
		getCnTime(listItem);
		//删除重复元素
		 removeDuplicate(listItem);
		Log.i(TAG, "listItem 新的"+listItem);
		return listItem;
		
	}
	/**
	 * 根据时间排序
	 * 
	 * @param list
	 */
	public static void bingSort(ArrayList<HashMap<String, Object>> list) {
		if (!list.isEmpty()) {
			Collections.sort(list, new Comparator<Map<String, Object>>() {

				@Override
				public int compare(Map<String, Object> lhs,
						Map<String, Object> rhs) {
					// TODO Auto-generated method stub
					return rhs.get("time").toString()
							.compareTo(lhs.get("time").toString());
				}
			});
		}
	}
	public static void getCnTime(ArrayList<HashMap<String, Object>> list) {
		int length = list.size();
		for (int i = 0; i < length; i++) {
			long time = BingDateUtils.getTime(list.get(i).get("time")
					.toString());
			list.get(i).put("new_time", TimeUtility.getListTime(time));
		}
	}
	//删除重复元素
	private void removeDuplicate(ArrayList<HashMap<String, Object>> listItem) {
		// TODO Auto-generated method stub
		  for  ( int  i  =   0 ; i  <  listItem.size()  -   1 ; i ++ )   {
			    for  ( int  j  =  listItem.size()  -   1 ; j  >  i; j -- )   {
			      if  (listItem.get(j).equals(listItem.get(i)))   {
			    	  listItem.remove(j);
			      } 
			    } 
			  } 
	}


}
