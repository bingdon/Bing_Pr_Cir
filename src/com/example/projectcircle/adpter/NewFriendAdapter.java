package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.R;

public class NewFriendAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	private Button curDel_btn;

	/* 构造函数 */
	public NewFriendAdapter(ArrayList<HashMap<String, Object>> listItem,
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.new_friend_list, null);
			holder = new ViewHolder();
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.new_friend_head_img);
			holder.name = (TextView) convertView
					.findViewById(R.id.new_friend_name);
			holder.time = (TextView) convertView
					.findViewById(R.id.new_friend_time);
			holder.content = (TextView) convertView
					.findViewById(R.id.new_friend_content);
			holder.jiguan = (TextView) convertView
					.findViewById(R.id.new_friend_native);
			holder.device = (TextView) convertView
					.findViewById(R.id.new_friend_device);
			holder.pass = (ImageView) convertView
					.findViewById(R.id.new_friend_pass);
			holder.delete = (Button) convertView.findViewById(R.id.delete);
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (curDel_btn != null)
					curDel_btn.setVisibility(View.GONE);
				holder.delete.setVisibility(View.INVISIBLE);
				holder.pass.setVisibility(View.VISIBLE);
				listItem.remove(position);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder {

		public Button delete;
		public ImageView pass;
		public TextView jiguan;
		public TextView device;
		public TextView content;
		public TextView time;
		public TextView name;
		public ImageView headimg;

	}
}
