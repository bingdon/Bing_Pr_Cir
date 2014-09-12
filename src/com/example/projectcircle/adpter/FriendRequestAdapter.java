package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcircle.R;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.friend.FriendPage;
import com.example.projectcircle.job.postStatus;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FriendRequestAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	private int []type;
	View setIcon;
	boolean isIconChange;
	private  String accept_id;
	private String deny_id;
	private HashMap<String, Object> weizhi;
	String id_m ;
	/* 构造函数 */
	public FriendRequestAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listItem.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.friend_request_message, null);
			holder = new ViewHolder();
			holder.id = (TextView) convertView.findViewById(R.id.request_id);
			holder.info = (TextView) convertView.findViewById(R.id.request_info);
			holder.accept =(ImageView)convertView.findViewById(R.id.request_sure);
			holder.deny = (ImageView)convertView.findViewById(R.id.request_deny);
			convertView.setTag(holder);// 绑定ViewHolder对象
		}
		
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		 id_m = listItem.get(position).get("cid") + "";
		holder.id.setText(id_m);
		String info_m = listItem.get(position).get("info") + "";
		holder.info.setText(info_m);		
		holder.accept.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				accept_id = id_m;
				befriend(accept_id,position);
				//
				Intent intent = new Intent(); 
				intent.setAction("add.a.new.friend"); 
				intent.putExtra("msg", "add a new friend"); 
				context.sendBroadcast(intent);
			}

		
		});
		holder.deny.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				deny_id = id_m;
				denyfriend(deny_id,position);		
			}

		});
		return convertView;
	}

	protected void befriend(String accept_id,final int position) {
		// TODO Auto-generated method stub	
		AppLog.i("FriendRequestAdapter", "接收:"+accept_id);
			AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
				private FriendPage receiver2;

				@Override
				public void onSuccess(String response) {
					// TODO Auto-generated method stub
					Log.i("返回：", "同意结果"+response);
//					parseFriendRequestList(response);
//					initList();		
					JSONObject obj;
					try {
						obj = new JSONObject(response);
						if (obj.getInt("result") == 1) {
							 Toast.makeText(context,"添加成功，对方已经是您的好友！",
									 Toast.LENGTH_SHORT).show();
						 //添加成功后就把相应的listItem删掉
							 //删除成功后就把相应的listItem删掉
							 if(listItem.size() > 0){
							 listItem.remove(position); } 
							 Thread.sleep(1000);
							 FriendRequestAdapter.this.notifyDataSetChanged();
//						        IntentFilter filter = new IntentFilter();
//						        filter.addAction("com.example.projectcircle.friend.FriendPage");
//						        receiver2 = new FriendPage();
//						        this.registerReceiver(receiver2, filter);
//						    	Intent intent = new Intent(); 
//								intent.setAction("com.eoe.broadcast2"); 
//								intent.putExtra("msg", "this is the second broadcast"); 
//								FriendRequestAdapter.this.sendBroadcast(intent);  
								
						}
						else{
							 Toast.makeText(context,"添加失败！",
									 Toast.LENGTH_SHORT).show();								
								 onFinish();
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}

			};
			MyHttpClient client = new MyHttpClient();
			client.beFriends(accept_id, res);	
	}


	private void denyfriend(String deny_id,final int position) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", response);
//				parseFriendRequestList(response);
//				initList();		
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						 Toast.makeText(context,"已拒绝添加对方为好友！",
								 Toast.LENGTH_SHORT).show();
						 //删除成功后就把相应的listItem删掉
						 if(listItem.size() > 0){
						 listItem.remove(position); }
						 Thread.sleep(1000);
						FriendRequestAdapter.this.notifyDataSetChanged(); 
					}
					else{
//						 Toast.makeText(context,"添加失败！",
//								 Toast.LENGTH_SHORT).show();
											
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.denyFriends(deny_id,res);
	}
	/* 存放控件 */
	public final class ViewHolder {
		public TextView id;	
		public TextView info;	
		public ImageView accept;
		public ImageView deny;
		
	}
	
}
