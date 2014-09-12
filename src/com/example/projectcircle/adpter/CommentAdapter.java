package com.example.projectcircle.adpter;

import java.util.List;

import com.example.projectcircle.R;
import com.example.projectcircle.bean.CommentBean;
import com.example.projectcircle.debug.AppLog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {
	
	private static final String TAG=CommentAdapter.class.getSimpleName();
	
	private List<CommentBean> list;
	
	private LayoutInflater inflater;
	public CommentAdapter (Context context,List<CommentBean> list){
		this.list=list;
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.comment_list_item, parent, false);
			holder.content=(TextView)convertView.findViewById(R.id.content);
			holder.name=(TextView)convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder=(ViewHolder)convertView.getTag();
		}
		
		holder.name.setVisibility(View.VISIBLE);
		holder.content.setVisibility(View.VISIBLE);
		holder.name.setText(list.get(position).getUser().getUsername());
		holder.content.setText(list.get(position).getContent());
		AppLog.i(TAG, "ÄØÈÙ:"+list.get(position).getContent()+"¿Ø¼þ:"+holder.content.isShown());
		
		return convertView;
	}

	
	public class ViewHolder{
		public TextView name;
		public TextView content;
	}
	
}
