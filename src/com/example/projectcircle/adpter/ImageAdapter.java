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

public class ImageAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;

	/* 构造函数 */
	public ImageAdapter(ArrayList<HashMap<String, Object>> listItem,
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
			convertView = mInflater.inflate(R.layout.gridview_image, null);
			holder = new ViewHolder();
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.perfect_device_camera);

			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();
		}		
		holder.headimg.setImageBitmap((Bitmap) listItem.get(position).get("myBitmap"));
		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder {

		public ImageView headimg;

	}
}
