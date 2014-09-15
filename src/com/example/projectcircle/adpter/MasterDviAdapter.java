package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.complete.CompleteMaster;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * MasterDevice的Adapter
 * 
 */
public class MasterDviAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	private Object equipment;

	public MasterDviAdapter(Context context,
			ArrayList<HashMap<String, Object>> listItem) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.listItem = listItem;
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

	public void removeList(int position) {
		// TODO Auto-generated method stub
		try {
			Log.i(MasterDviAdapter.class.getSimpleName(), "id:"+listItem.get(position).get("id"));
			MyHttpClient.deleteEqu("" + listItem.get(position).get("id"),
					new JsonHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							Log.i(MasterDviAdapter.class.getSimpleName(),
									"删除返回:" + response);
						}

					});
		} catch (Exception e) {
			// TODO: handle exception
		}
		listItem.remove(position);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		final int adposition = position;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.master_device_list, parent,false);
			holder = new ViewHolder();
			holder.select_number = (TextView) convertView
					.findViewById(R.id.master_device_devicenumber);// listView列车来的数量
			holder.name = (TextView) convertView
					.findViewById(R.id.master_device_devicename);
			holder.brand = (TextView) convertView
					.findViewById(R.id.master_device_brand);
			holder.model = (TextView) convertView
					.findViewById(R.id.master_device_model);
			holder.num = (TextView) convertView
					.findViewById(R.id.master_device_num);
			holder.type = (TextView) convertView
					.findViewById(R.id.master_device_type);
			holder.delete = (ImageView) convertView.findViewById(R.id.delete);
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		equipment = listItem.get(position).get("equipment");
		if (equipment.equals("挖掘机")) {
			String name = listItem.get(position).get("ename") + " :";
			holder.name.setText(name);
			String ebrand = listItem.get(position).get("ebrand") + "";
			holder.brand.setText(ebrand);
			String emodel = listItem.get(position).get("emodel") + "";
			holder.model.setText(emodel);
			String enumber = listItem.get(position).get("enumber") + "";
			holder.num.setText(enumber + "台");
			String select_number = listItem.get(position).get("select_num")
					+ "";
			holder.select_number.setText(select_number);
			holder.type.setVisibility(View.GONE);
			holder.model.setVisibility(View.VISIBLE);
			holder.num.setVisibility(View.VISIBLE);
		} else if (equipment.equals("自卸车")) {
			String name = listItem.get(position).get("ename") + " :";
			holder.name.setText(name);
			String ebrand = listItem.get(position).get("ebrand") + "";
			holder.brand.setText(ebrand);
			String emodel = listItem.get(position).get("emodel") + "";
			holder.model.setText(emodel);
			String etype = listItem.get(position).get("etype") + "";
			holder.type.setText(etype);
			String enumber = listItem.get(position).get("enumber") + "";
			holder.num.setText(enumber + "台");
			String select_number = listItem.get(position).get("select_num")
					+ "";
			holder.select_number.setText(select_number);
			holder.type.setVisibility(View.VISIBLE);
			holder.model.setVisibility(View.VISIBLE);
			holder.num.setVisibility(View.VISIBLE);
		} else if (equipment.equals("装载机")) {
			String name = listItem.get(position).get("ename") + " :";
			holder.name.setText(name);
			String ebrand = listItem.get(position).get("ebrand") + "";
			holder.brand.setText(ebrand);
			String emodel = listItem.get(position).get("emodel") + "";
			holder.model.setText(emodel);
			String eweight = listItem.get(position).get("eweight") + "";
			holder.type.setText(eweight);
			String enumber = listItem.get(position).get("enumber") + "";
			holder.num.setText(enumber + "台");
			String select_number = listItem.get(position).get("select_num")
					+ "";
			holder.select_number.setText(select_number);
			holder.type.setVisibility(View.VISIBLE);
			holder.model.setVisibility(View.VISIBLE);
			holder.num.setVisibility(View.VISIBLE);
		} else if (equipment.equals("其它")) {
			String name = listItem.get(position).get("ename") + " :";
			holder.name.setText(name);
			String ebrand = listItem.get(position).get("ebrand") + "";
			holder.brand.setText(ebrand);
			String emodel = listItem.get(position).get("emodel") + "";
			holder.model.setText(emodel);
			// String eweight = listItem.get(position).get("eweight") + "";
			// holder.type.setText(eweight);
			String enumber = listItem.get(position).get("enumber") + "";
			holder.num.setText(enumber + "台");
			String select_number = listItem.get(position).get("select_num")
					+ "";
			holder.select_number.setText(select_number);
			holder.type.setVisibility(View.GONE);
			holder.model.setVisibility(View.VISIBLE);
			holder.num.setVisibility(View.VISIBLE);
		} else if (equipment.equals("平板车")) {
			String name = listItem.get(position).get("ename") + " :";
			holder.name.setText(name);
//			String eload = listItem.get(position).get("eload") + "";
//			holder.brand.setText(eload + "吨");
			String enumber = listItem.get(position).get("enumber") + "";
			holder.num.setText(enumber + "台");
			holder.brand.setText("");
			String select_num = listItem.get(position).get("select_num") + "";
			holder.select_number.setText(select_num);
			holder.model.setVisibility(View.GONE);
			holder.type.setVisibility(View.GONE);
		}

		holder.select_number.setText(""+(adposition+1));
		
		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				removeList(adposition);
			}
		});

		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder {

		public TextView shu3;
		public TextView shu2;
		public TextView shu1;
		public TextView model;
		public TextView num;
		public TextView type;
		public TextView brand;
		public TextView name;
		public TextView select_number;// listView列车来的数量
		public ImageView delete;

	}
}
