package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class HisCreateGroupGridAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();	
	/* 构造函数 */
	public HisCreateGroupGridAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				// .showStubImage(R.drawable.ic_launcher)// 设置图片在下载期间显示的图片
				// .showImageForEmptyUri(R.drawable.ic_launcher)//
				// 设置图片Uri为空或是错误的时候显示的图片
				// .showImageOnFail(R.drawable.ic_launcher)//
				// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				// .displayer(new RoundedBitmapDisplayer(20)) //
				// 设置用户加载图片task(这里是圆角图片显示)
				.build();

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
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.member_image, null);
			holder = new ViewHolder();
			holder.photo = (ImageView) convertView
					.findViewById(R.id.member_headimages);
		
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//String username = listItems.get(position).get("id") + "";
		String headimage = listItem.get(position).get("headimage") + "";	
		System.out.println("我的群组的头像"+headimage);
		 imageLoader.displayImage(
		 MyHttpClient.IMAGE_URL
		 + listItem.get(position).get("headimage"),
		 holder.photo, options, animateFirstListener);
		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder {

		public ImageView photo;

	}

}
