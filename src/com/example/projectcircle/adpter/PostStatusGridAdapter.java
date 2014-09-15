package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.projectcircle.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class PostStatusGridAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItems;
	Context context;
	DisplayImageOptions options;

	/* 构造函数 */
	public PostStatusGridAdapter(ArrayList<HashMap<String, Object>> listItems,
			Context context) {
		this.context = context;
		this.listItems = listItems;
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
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listItems.get(arg0);
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
		
			// ImageLoader imageLoader = ImageLoader.getInstance();
			// imageLoader.displayImage(
			// MyHttpClient.IMAGE_URL
			// + listItem.get(position).get("headimage") + "",
			// holder.headimg, options, animateFirstListener);
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Bitmap id = (Bitmap) listItems.get(position).get("photo_array");
		holder.photo.setImageBitmap(id);
		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder {

		public ImageView photo;

	}

}
