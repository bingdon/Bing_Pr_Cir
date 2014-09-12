package com.example.projectcircle.adpter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Home的Adapter
 * 
 */
public class HomeAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;

	Double mylat = HomeActivity.latitude;
	Double mylon = HomeActivity.longitude;

	// 图片缓存
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private String tag;
	private double distance;
	private String lastlogintime;
	private String lastlogintime_new;
	private String timecompute;
	private static final String TAG=HomeAdapter.class.getSimpleName();
	/* 构造函数 */
	public HomeAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);

		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				 .showStubImage(R.drawable.header)// 设置图片在下载期间显示的图片
				 .showImageForEmptyUri(R.drawable.header)//
				// 设置图片Uri为空或是错误的时候显示的图片
				 .showImageOnFail(R.drawable.header)//
				// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				 .displayer(new RoundedBitmapDisplayer(20)) //
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
		ViewHolder1 holder1 = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.main_list1, null);
			holder1 = new ViewHolder1();
			holder1.headimg = (ImageView) convertView
					.findViewById(R.id.main_head_img_host);
			holder1.name = (TextView) convertView.findViewById(R.id.main_name_host);
			holder1.sign =(TextView) convertView
					.findViewById(R.id.main_content_host);
			holder1.career = (TextView) convertView
					.findViewById(R.id.main_career_host);
			holder1.content = (TextView) convertView
					.findViewById(R.id.main_content_host);
			holder1.jiguan = (TextView) convertView
					.findViewById(R.id.main_address_host);
			holder1.device = (TextView) convertView
					.findViewById(R.id.main_device_host);
			holder1.distance = (TextView) convertView
					.findViewById(R.id.main_distance_host);
			holder1.phone = (ImageView) convertView
					.findViewById(R.id.main_call_host);
			holder1.type =(TextView) convertView
					.findViewById(R.id.main_career_host);	
			holder1.address = (TextView) convertView.findViewById(R.id.main_address_host);
			convertView.setTag(holder1);// 绑定ViewHolder对象
		} else {
			holder1 = (ViewHolder1) convertView.getTag();
		}
		
		String username = listItem.get(position).get("name") + "";
		String headimage = listItem.get(position).get("headimage") + "";
		final String phonenum = listItem.get(position).get("tel") + "";
		final String accept = listItem.get(position).get("accept") + "";
		String type = listItem.get(position).get("type") + "";
		String address = listItem.get(position).get("address") + "";
		String sign = listItem.get(position).get("sign") + "";
		String distance =  listItem.get(position).get("distance") + "";
		String equipment =  listItem.get(position).get("equipment") + "";
//		Double commercialLat = (Double) listItem.get(position).get("commercialLat");
//		Double commercialLon = (Double) listItem.get(position).get("commercialLon");
		
		Log.i(tag, "MyHttpClient.IMAGE_URL"+MyHttpClient.IMAGE_URL);
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+ listItem.get(position).get("headimage") + "",
				holder1.headimg, options, animateFirstListener);
		holder1.name.setText(username);
		//holder.headimg.setRight(headimage);	
		holder1.type.setText(type);
		//地址只截取省，因为省市是由空格分开的，所以截取第一个空格前的内容即为省
		if(!address.equals("null")){
		String arrays[] = address.split(" ");		
		if(arrays.length >= 2 && !TextUtils.isEmpty(arrays[0]) && !TextUtils.isEmpty(arrays[1])){
		holder1.address.setText(arrays[0]+" "+arrays[1]);
		}
		}
        //防止个性签名那不填的话出现null 
		if(!TextUtils.isEmpty(sign) && !sign.equals("null"))
		 {
			holder1.sign.setText(sign);
		 }else if(TextUtils.isEmpty(sign) || sign.equals("null"))
		 {
			holder1.sign.setText("");
		 }
		holder1.device.setText(equipment);
     	holder1.distance.setText(distance+"km");
		//获取当前时间
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss"); 
		 Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
		 String  now   =   formatter.format(curDate);
		 lastlogintime =  listItem.get(position).get("lastlogintime")+"";//从服务器那得到的时间，但是不能转换，少一个空格，加一个空格才能转换成格林尼治时间	
		 Log.i("now----------", now);
		 Log.i("lastlogintime----------", lastlogintime);		
		//计算当前时间与服务器返回的用户上次登录的时间的时间差，如果大于1天，显示1天前，大于1小时显示X小时前，否则，显示X分钟前
		 AppLog.i(TAG, "最后登录:"+lastlogintime);
		 if(lastlogintime != null && !lastlogintime.equals("null")){
			try
			{ 	//时间转换成格林尼治时间	
			   lastlogintime_new = lastlogintime.substring(0,4)+"年"+lastlogintime.substring(5,7)+"月"+lastlogintime.substring(8,10)+"日   "+lastlogintime.substring(11,lastlogintime.length());
               Log.i("lastlogintime_new", lastlogintime_new);
			   java.util.Date d1 = (formatter).parse(now);			   
			   java.util.Date d2 = (formatter).parse(lastlogintime_new);		
			   long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别					
			   long days = diff / (1000 * 60 * 60 * 24);
			   long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			   long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);					
			   System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
			   if(days>=1){
				   timecompute = "1天前";
			   }else if(hours>=1)
			   {
				   timecompute = hours+"小时前";
			   }else{
				   timecompute = minutes+"分钟前";
			   }
			  		  
			}
			catch (Exception e)
			{
			}
			holder1.distance.setText(distance+"km|"+timecompute);
		 }else{
		//距离和上次登录的时间，显示到控件上	
		holder1.distance.setText(distance+"km|"+"无");
		}
		//控制电话标志的图片显示与否
		if( accept.equals("1")){
			holder1.phone.setVisibility(View.VISIBLE);
		}else{
			holder1.phone.setVisibility(View.INVISIBLE);
		}
		holder1.phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//如果输入不为空创建打电话的Intent
				if(phonenum.trim().length()!=0 && accept.equals("1"))
				  {
                   Intent phoneIntent = new Intent("android.intent.action.CALL",
				   Uri.parse("tel:" + phonenum));
			       //启动
                   context.startActivity(phoneIntent);
                   }

			}

		});
		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder1 {

		public TextView career;
		public ImageView phone;
		public TextView distance;
		public TextView jiguan;
		public TextView device;
		public TextView content;
		public TextView time;
		public TextView name;
		public ImageView headimg;
		public TextView type;
		public TextView sign;
		public TextView address;
	}
}
