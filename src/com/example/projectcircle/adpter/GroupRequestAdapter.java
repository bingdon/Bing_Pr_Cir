package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
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
import com.example.projectcircle.friend.FriendPage;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class GroupRequestAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	View setIcon;
	boolean isIconChange;
	private  String accept_id;
	private String deny_id;
	String id_m ;
	private String gid;
	/* 构造函数 */
	public GroupRequestAdapter(ArrayList<HashMap<String, Object>> listItem,
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
		 id_m = listItem.get(position).get("uid") + "";
		 gid = listItem.get(position).get("gid") + "";//请求加入群组的id
		holder.id.setText(id_m);
		String info_m = listItem.get(position).get("info") + "";
		holder.info.setText(info_m);		
		holder.accept.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				accept_id = id_m;
				Log.i("accept_id",accept_id);
				beMember2GroupMem(accept_id,position);
			}

		
		});
		holder.deny.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				deny_id = id_m;
				deleteMember2Group(deny_id,gid,position);		
			}

		});
		return convertView;
	}

	protected void beMember2GroupMem(String accept_id,final int position) {
		// TODO Auto-generated method stub	
			AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					// TODO Auto-generated method stub
					Log.i("加群同意返回的response", response);
	
					try {
						JSONObject	obj = new JSONObject(response);
						if (obj.getInt("result") == 1) {
							 Toast.makeText(context,"加群成功！",
									 Toast.LENGTH_SHORT).show();
						 //添加成功后就把相应的listItem删掉
							 //删除成功后就把相应的listItem删掉
							 if(listItem.size() > 0){
							 listItem.remove(position); } 
							 Thread.sleep(1000);
							 GroupRequestAdapter.this.notifyDataSetChanged();								
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
			client.beMember2GroupMem(accept_id, res);	
	}


	private void deleteMember2Group(String deny_id,String gid,final int position) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						 Toast.makeText(context,"群主绝入群！",
								 Toast.LENGTH_SHORT).show();
						 //删除成功后就把相应的listItem删掉
						 if(listItem.size() > 0){
						 listItem.remove(position); }
						 Thread.sleep(1000);
						GroupRequestAdapter.this.notifyDataSetChanged(); 
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
		client.deleteMember2Group(deny_id,gid, res);
	}
	/* 存放控件 */
	public final class ViewHolder {
		public TextView id;	
		public TextView info;	
		public ImageView accept;
		public ImageView deny;
		
	}
	
}
