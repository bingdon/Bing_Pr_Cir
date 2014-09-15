package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.friend.FriendSelectListenter;
import com.example.projectcircle.friend.GroupChat;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * Friend的Adapter
 * 
 */
public class GroupChatAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	private int []type;
	View setIcon;
	boolean isIconChange;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();	
	ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private FriendSelectListenter groupchatListenter;
	/* 构造函数 */
	public GroupChatAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				 .showStubImage(R.drawable.icon_qunzu)// 设置图片在下载期间显示的图片
				 .showImageForEmptyUri(R.drawable.icon_qunzu)//
				// 设置图片Uri为空或是错误的时候显示的图片
				 .showImageOnFail(R.drawable.icon_qunzu)//
				// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				// .displayer(new RoundedBitmapDisplayer(20)) //
				// 设置用户加载图片task(这里是圆角图片显示)
				.build();

		
	}
	public void setFriendSelectListener(FriendSelectListenter groupchatListenter) {
		this.groupchatListenter = groupchatListenter;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 final ViewHolder holder;
		 final int groupbuttonposition = position;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.main_list4, null);
			holder = new ViewHolder();
			holder.selector = (ImageView) convertView
					.findViewById(R.id.select_img);
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.select_head_img);
			holder.name = (TextView) convertView.findViewById(R.id.main_name);
			holder.time = (TextView) convertView.findViewById(R.id.main_time);
			holder.content = (TextView) convertView
					.findViewById(R.id.main_content);
			holder.jiguan = (TextView) convertView
					.findViewById(R.id.main_native);
			holder.device = (TextView) convertView
					.findViewById(R.id.main_device);
			holder.distance = (TextView) convertView
					.findViewById(R.id.main_distance);
			holder.type = (TextView) convertView
					.findViewById(R.id.main_career);
			holder.phone = (ImageView) convertView.findViewById(R.id.main_call);
			convertView.setTag(holder);// 绑定ViewHolder对象
		}
		
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		String username = listItem.get(position).get("username") + "";
		holder.name.setText(username);
		String equipment = listItem.get(position).get("equipment") + "";
		holder.device.setText(equipment);
		String address = listItem.get(position).get("address") + "";
		holder.jiguan.setText(address);
		String type = listItem.get(position).get("type") + "";
		holder.type.setText(type);
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+ listItem.get(position).get("headimage") + "",
				holder.headimg, options, animateFirstListener);
		//好友选中和不选中
		isIconChange = false;
		holder.selector.setOnClickListener(new OnClickListener() {
			
		
			@Override
			public void onClick(View arg0) {
				
				// TODO Auto-generated method stub
				
				     if(isIconChange){
				    	 holder.selector.setBackgroundResource(R.drawable.radiobtn);
				           isIconChange = false;				               
				     }else{
				    	 holder.selector.setBackgroundResource(R.drawable.radiobtn_pressed);
				           isIconChange = true;				    
						}
				      //将选择的人的对应头像放到下边的Gallery中 
//				     public void onClick(View v) {
							// TODO Auto-generated method stub
				     if(isIconChange){
							 groupchatListenter.onFriendSelectListenter(groupbuttonposition); 
				     }else if(!isIconChange)
				     {
				    	 groupchatListenter.onFriendNotSelectListenter(groupbuttonposition); 
				     }
				        
				       
			}
		});
		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder {

		public TextView type;
		public ImageView phone;
		public TextView distance;
		public TextView jiguan;
		public TextView device;
		public TextView content;
		public TextView time;
		public TextView name;
		public ImageView headimg;
		public ImageView selector;

	}
}
