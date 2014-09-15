package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class NearGpAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;

	// 图片缓存
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	/* 构造函数 */
	public NearGpAdapter(ArrayList<HashMap<String, Object>> listItem,
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

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.near_list, null);
			holder = new ViewHolder();
			holder.groupimg = (ImageView) convertView
					.findViewById(R.id.n_group_headimg);
			holder.name = (TextView) convertView
					.findViewById(R.id.n_group_name);
			holder.location = (TextView) convertView
					.findViewById(R.id.n_group_near);
			holder.people = (TextView) convertView
					.findViewById(R.id.n_group_people);
			holder.content = (TextView) convertView
					.findViewById(R.id.n_group_introduce);
			holder.distance = (TextView) convertView
					.findViewById(R.id.near_group_distance_host);
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String name = listItem.get(position).get("gname") + "";
		holder.name.setText(name);
		String address = listItem.get(position).get("gaddress") + "";
		holder.location.setText(address);
		String content = listItem.get(position).get("content") + "";
		holder.content.setText(content);
		String distance = listItem.get(position).get("distance") + "";		
		holder.distance.setText(distance+"km");
		String gaddress = listItem.get(position).get("gaddress") + "";		
		holder.location.setText(gaddress);
		try {
			holder.people.setText(listItem.get(position).get("count")+"/50");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+ listItem.get(position).get("headimage") + "",
				holder.groupimg, options, animateFirstListener);
		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder {

		public TextView distance;
		public TextView content;
		public TextView people;
		public TextView location;
		public TextView name;
		public ImageView groupimg;

	}
}
