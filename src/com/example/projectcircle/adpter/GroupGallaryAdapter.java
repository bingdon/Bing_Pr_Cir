package com.example.projectcircle.adpter;

import java.util.List;

import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;


import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import android.widget.ImageView;


public class GroupGallaryAdapter extends BaseAdapter {

	private Context context;  
    private List<String> list; 
    
	// 图片缓存
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();	
	ImageLoader imageLoader = ImageLoader.getInstance();
	private LayoutInflater mInflater;
	public GroupGallaryAdapter( Context context, List<String> list) {  
        this.context = context;  
        this.list = list;  
        this.mInflater = LayoutInflater.from(context);
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				 .showStubImage(R.drawable.icon_qunzu)// 设置图片在下载期间显示的图片
				 .showImageForEmptyUri(R.drawable.icon_qunzu)//
				// 设置图片Uri为空或是错误的时候显示的图片
				 .showImageOnFail(R.drawable.icon_qunzu)//
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
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InlinedApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		// TODO Auto-generated method stub
		 // 实例化  
        ImageView img = new ImageView(context);          
        // 设置边界对齐  
        img.setAdjustViewBounds(true);  
        // 设置布局参数  
        img.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT,  
                LayoutParams.WRAP_CONTENT)); 
        
		 // 实例化  
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.gallery_list1, null);
			holder = new ViewHolder();		
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.gallery_head_img_host);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+  list.get(position) + "",holder.headimg, options, animateFirstListener);
		Log.i("MyHttpClient.IMAGE_URL+ list", MyHttpClient.IMAGE_URL
				+ list);
	
		return convertView;
	}
	/* 存放控件 */
	public final class ViewHolder {
	   public ImageView headimg;	
	}
}
