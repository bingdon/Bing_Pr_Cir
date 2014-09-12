package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MsgAdapter extends BaseAdapter {

	private static final String TAG = MsgAdapter.class.getSimpleName();
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	final int TYPE_1 = 0;
	final int TYPE_2 = 1;

	private ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;

	/* 构造函数 */
	public MsgAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				// 加载图片时会在内存中加载缓存
				.cacheInMemory(true)
				// 是否緩存都內存中
				.cacheOnDisc(true)
				// 是否緩存到sd卡上
				.showStubImage(R.drawable.logo)
				.showImageOnFail(R.drawable.logo)
				.showImageForEmptyUri(R.drawable.logo).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		// Log.i("jjkkkk",
		// "ismood" + (Boolean) listItem.get(position).get("isMood"));
		if (!(Boolean) listItem.get(position).get("isPerson")) {
			return TYPE_1;
		} else {
			return TYPE_2;
		}
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
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
		ViewHolder1 holder1 = null;
		ViewHolder2 holder2 = null;
		int viewtype = getItemViewType(position);
		if (convertView == null) {
			switch (viewtype) {
			case TYPE_1:
				convertView = mInflater.inflate(R.layout.message_list, null);
				holder1 = new ViewHolder1();
				holder1.headimg = (ImageView) convertView
						.findViewById(R.id.msg_headimg);
				holder1.name = (TextView) convertView
						.findViewById(R.id.msg_name);
				holder1.content = (TextView) convertView
						.findViewById(R.id.msg_content);
				holder1.distence = (TextView) convertView
						.findViewById(R.id.msg_distence);
				holder1.time = (TextView) convertView
						.findViewById(R.id.msg_time);
				holder1.flag = (ImageView) convertView
						.findViewById(R.id.msg_flag);
				holder1.unreadTextView = (TextView) convertView
						.findViewById(R.id.msg_unread);
				convertView.setTag(holder1);// 绑定ViewHolder对象
				break;
			case TYPE_2:
				convertView = mInflater.inflate(R.layout.message_list, null);
				holder2 = new ViewHolder2();
				holder2.headimg = (ImageView) convertView
						.findViewById(R.id.msg_headimg);
				holder2.name = (TextView) convertView
						.findViewById(R.id.msg_name);
				holder2.content = (TextView) convertView
						.findViewById(R.id.msg_content);
				holder2.distence = (TextView) convertView
						.findViewById(R.id.msg_distence);
				holder2.time = (TextView) convertView
						.findViewById(R.id.msg_time);
				holder2.flag = (ImageView) convertView
						.findViewById(R.id.msg_flag);
				convertView.setTag(holder2);// 绑定ViewHolder对象
				break;
			}
		} else {
			// 有convertView，按样式，取得不用的布局
			switch (viewtype) {
			case TYPE_1:
				holder1 = (ViewHolder1) convertView.getTag();
				// Log.e("convertView !!!!!!= ", "NULL TYPE_1");
				break;
			case TYPE_2:
				holder2 = (ViewHolder2) convertView.getTag();

				// Log.e("convertView !!!!!!= ", "NULL TYPE_2");
				break;
			}
		}
		// 设置资源
		switch (viewtype) {
		case TYPE_1:

			if (listItem.get(position).containsKey("name")) {
				Log.d(TAG, listItem.get(position).get("name").toString());
				holder1.name.setText(listItem.get(position).get("name")
						.toString());
			}

			if (listItem.get(position).containsKey("content")) {
				Log.d(TAG, listItem.get(position).get("content").toString());
				holder1.content.setText(listItem.get(position).get("content")
						.toString());
			}

			if (listItem.get(position).containsKey("time")) {
				holder1.time.setText(listItem.get(position).get("time")
						.toString());
			}

			if (listItem.get(position).containsKey("headimg")) {
				Log.i(TAG, "img:" + holder1.headimg);
				imageLoader.displayImage(
						MyHttpClient.IMAGE_URL
								+ listItem.get(position).get("headimg"),
						holder1.headimg, options);
			}

			if (listItem.get(position).containsKey("noreadnumm")) {
				int unread = 0;
				try {
					unread = Integer.valueOf(listItem.get(position)
							.get("noreadnumm").toString());
				} catch (Exception e) {
					// TODO: handle exception
				}
				holder1.unreadTextView.setText(""
						+ listItem.get(position).get("noreadnumm"));
				if (unread > 0) {
					holder1.unreadTextView.setVisibility(View.VISIBLE);
				} else {
					holder1.unreadTextView.setVisibility(View.GONE);
				}

			}

			break;
		case TYPE_2:
			holder2.name.setText("工人协会");
			holder2.flag.setVisibility(View.INVISIBLE);

			if (listItem.get(position).containsKey("name")) {
				Log.d(TAG, listItem.get(position).get("name").toString());
				holder2.name.setText(listItem.get(position).get("name")
						.toString());
			}

			if (listItem.get(position).containsKey("content")) {
				Log.d(TAG, listItem.get(position).get("content").toString());
				holder2.content.setText(listItem.get(position).get("content")
						.toString());
			}

			if (listItem.get(position).containsKey("time")) {
				holder2.time.setText(listItem.get(position).get("time")
						.toString());
			}

			if (listItem.get(position).containsKey("headimg")) {
				imageLoader.displayImage(
						MyHttpClient.IMAGE_URL
								+ listItem.get(position).get("headimg")
										.toString(), holder2.headimg, options);
			}

			break;
		}
		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder1 {

		public ImageView flag;
		public TextView time;
		public TextView distence;
		public TextView content;
		public TextView name;
		public ImageView headimg;
		public TextView unreadTextView;

	}

	public final class ViewHolder2 {

		public ImageView flag;
		public TextView time;
		public TextView distence;
		public TextView content;
		public TextView name;
		public ImageView headimg;

	}
}
