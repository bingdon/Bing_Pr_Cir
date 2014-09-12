package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projectcircle.R;

public class WorkAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	/* 构造函数 */
	public WorkAdapter(ArrayList<HashMap<String, Object>> listItem,
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
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.project_job_list, null);
			holder = new ViewHolder();
			holder.device = (TextView) convertView
					.findViewById(R.id.pj_list_device);
			holder.distance = (TextView) convertView
					.findViewById(R.id.pj_list_distance);
			holder.date = (TextView) convertView
					.findViewById(R.id.pj_list_date);		
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String title = listItem.get(position).get("title") + "";
		holder.device.setText(title);
		String date = listItem.get(position).get("date") + "";
		date = date.substring(5,10);//只显示月日
		holder.date.setText(date);		
		String distance = listItem.get(position).get("distance") + "";
		holder.distance.setText(distance+"km");
		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder {

	
		public TextView date;
		public TextView device;
		public TextView distance;

	}
}
