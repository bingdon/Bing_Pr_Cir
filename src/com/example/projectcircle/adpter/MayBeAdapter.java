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

import com.example.projectcircle.R;

public class MayBeAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;

	/* 构造函数 */
	public MayBeAdapter(ArrayList<HashMap<String, Object>> listItem,
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
			convertView = mInflater.inflate(R.layout.main_list1, null);
			holder = new ViewHolder();
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.main_head_img);
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
			holder.phone = (ImageView) convertView.findViewById(R.id.main_call);
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder {

		public ImageView phone;
		public TextView distance;
		public TextView jiguan;
		public TextView device;
		public TextView content;
		public TextView time;
		public TextView name;
		public ImageView headimg;

	}
}
