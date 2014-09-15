package com.example.projectcircle.adpter;

import java.util.List;

import com.example.projectcircle.R;
import com.example.projectcircle.bean.NewConstactBean;
import com.example.projectcircle.util.LoadImageUtils;
import com.example.projectcircle.util.MyHttpClient;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewContAdapter extends BaseAdapter {

	private List<NewConstactBean> list;

	private Context context;

	private LayoutInflater inflater;

	private static final int TYPE_CONT = 0;

	private static final int TYPE_REQUEST = 1;

	private HandlerListener listener;

	public void setListener(HandlerListener listener) {
		this.listener = listener;
	}

	public NewContAdapter(List<NewConstactBean> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
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
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return list.get(position).getType_();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int adaPosition = position;
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.new_contact_item, parent,
					false);
			holder.head = (ImageView) convertView.findViewById(R.id.headimg);
			holder.name = (TextView) convertView.findViewById(R.id.username);
			holder.type = (TextView) convertView.findViewById(R.id.type);
			holder.option = (Button) convertView.findViewById(R.id.add);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		LoadImageUtils.displayImg(MyHttpClient.IMAGE_URL
				+ list.get(position).getHeadimage(), holder.head);
		try {
			holder.name.setText(list.get(position).getUsername());
		} catch (Exception e) {
			// TODO: handle exception
		}

		holder.option.setFocusable(false);
		holder.option.setFocusableInTouchMode(false);

		switch (getItemViewType(position)) {
		case TYPE_CONT:
			holder.type.setText(R.string.phone_conts);
			holder.option.setBackgroundResource(R.drawable.add_sec);
			holder.option.setText(R.string.add);
			break;

		case TYPE_REQUEST:
			holder.type.setText(R.string.request_fri);
			holder.option.setBackgroundResource(R.drawable.accpet_sec);
			holder.option.setText(R.string.accpet);
			break;

		default:
			break;
		}

		switch (list.get(position).getIsAccpet()) {
		case 0:
			holder.option.setVisibility(View.VISIBLE);
			break;

		case 1:
			holder.type.setText(R.string.added);
			holder.option.setBackgroundColor(context.getResources().getColor(
					R.color.transparent));
			break;

		default:
			break;
		}

		holder.option.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listener != null) {

					listener.addPeople(adaPosition);
				}
			}
		});

		return convertView;
	}

	public class ViewHolder {
		public ImageView head;
		public TextView name;
		public TextView type;
		public Button option;
	}

	public interface HandlerListener {

		public void addPeople(int position);

	}

}
