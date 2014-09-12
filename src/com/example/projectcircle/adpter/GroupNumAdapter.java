package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.group.GroupDetail;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class GroupNumAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	private Button curDel_btn;
	String accept;
	String tel;
	String uid;
	String gid;
	Double mylat = HomeActivity.latitude;
	Double mylon = HomeActivity.longitude;
	// 图片缓存
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	/* 构造函数 */
	public GroupNumAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
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
			convertView = mInflater.inflate(R.layout.main_list5, null);
			holder = new ViewHolder();
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.group_main_head_img_host);
			holder.name = (TextView) convertView.findViewById(R.id.group_main_name_host);
			holder.time = (TextView) convertView.findViewById(R.id.group_main_time_host);
			holder.career = (TextView) convertView
					.findViewById(R.id.group_main_career_host);
			holder.content = (TextView) convertView
					.findViewById(R.id.group_main_content_host);
			holder.jiguan = (TextView) convertView
					.findViewById(R.id.group_main_native_host);
			holder.device = (TextView) convertView
					.findViewById(R.id.group_main_device_host);
			holder.distance = (TextView) convertView
					.findViewById(R.id.group_main_distance_host);
			holder.flag = (ImageView) convertView
					.findViewById(R.id.group_icon_address);
			holder.phone = (ImageView) convertView.findViewById(R.id.group_main_call_host);		
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		uid = LoginActivity.id;
		gid = GroupDetail.gid;
		Log.i("uid+gid", uid + "+" + gid);
		String name = listItem.get(position).get("name") + "";
		holder.name.setText(name);
		String type = listItem.get(position).get("type") + "";
		holder.career.setText(type);
		String content = listItem.get(position).get("sign") + "";
		holder.content.setText("(" + content + ")");
		String city = listItem.get(position).get("address") + "";
		holder.jiguan.setText("籍贯：" + city);
		String device = listItem.get(position).get("equipment") + "";
		holder.device.setText("设备：" + device);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+ listItem.get(position).get("headimage") + "", holder.headimg,
				options, animateFirstListener);
		// 计算距离
		Double nearlat = (Double) listItem.get(position).get("lat");
		Double nearlon = (Double) listItem.get(position).get("lon");

		Double distance = DistentsUtil.DistanceOfTwoPoints(nearlat, nearlon,
				mylat, mylon) / 1000;
		holder.distance.setText(distance + "km");
		accept = listItem.get(position).get("accept") + "";
		tel = listItem.get(position).get("tel") + "";
		//控制电话标志的图片显示与否
		if( accept.equals("1")){
			holder.phone.setVisibility(View.VISIBLE);
			}else{
			holder.phone.setVisibility(View.INVISIBLE);
		}
		holder.phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ((tel != null) && (!"".equals(tel.trim()))) {
					Uri uri = Uri.parse("tel:" + tel);
					Intent intent = new Intent(Intent.ACTION_CALL, uri);
					context.startActivity(intent);
				}
			}
		});
//		holder.delete.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (curDel_btn != null)
//					curDel_btn.setVisibility(View.GONE);
//				holder.delete.setVisibility(View.INVISIBLE);
//				holder.phone.setVisibility(View.VISIBLE);
//				holder.distance.setVisibility(View.VISIBLE);
//				holder.flag.setVisibility(View.VISIBLE);
//				listItem.remove(position);
//				deleteMember(uid, gid);
//				notifyDataSetChanged();
//			}
//		});
		return convertView;
	}

	private void deleteMember(String uid, String gid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				System.out.println(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						ToastUtils.showShort(context, "删除成功！");
					} else {
						ToastUtils.showShort(context, "删除失败！");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}
		};
		MyHttpClient myhttpclient = new MyHttpClient();
		myhttpclient.deleteMember(uid, gid, res);
	}

	/* 存放控件 */
	public final class ViewHolder {

		public TextView career;
		public ImageView flag;
		public Button delete;
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
