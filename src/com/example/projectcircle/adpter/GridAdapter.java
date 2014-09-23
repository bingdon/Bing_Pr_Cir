package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.projectcircle.R;
import com.example.projectcircle.adpter.MemberAdapter.ViewHolder;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.util.MyHttpClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class GridAdapter extends BaseAdapter {

	private List<String> list;
	
	private LayoutInflater inflater;
	
//	private ImageFetcher imageFetcher;
	
	private DisplayImageOptions options;
	
	private ImageLoader imageLoader;
	
	public GridAdapter(Context context,List<String> list){
		this.list=list;
		this.inflater=LayoutInflater.from(context);
//		this.imageFetcher=new ImageFetcher(context, 200,200);
//		imageFetcher.setLoadingImage(R.drawable.camera);
		this.imageLoader=ImageLoader.getInstance();
		this.options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.pic_loading_)
		.showImageForEmptyUri(R.drawable.pic_empty)
		.showImageOnFail(R.drawable.pic_failure)
		.cacheInMemory(true)
		.cacheOnDisc(true)
//		.considerExifParams(true)
//		.displayer(new RoundedBitmapDisplayer(20))
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
		ImageView mImageView;
		if (convertView==null) {
			if (list.size()>2) {
				convertView=inflater.inflate(R.layout.pic_gird, null);
			}else {
				convertView=inflater.inflate(R.layout.pic_grid_, null);
			}
			
			mImageView=(ImageView)convertView.findViewById(R.id.shai_pic_muilt);
			convertView.setTag(mImageView);
		}else {
			mImageView=(ImageView)convertView.getTag();
		}
		
		if (list.size()==2) {
//			imageFetcher.setImageSize(450, 450);
//			convertView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 100));
		}else if (list.size()>2) {
//			imageFetcher.setImageSize(52, 52);
		}
//		imageFetcher.loadImage(list.get(position), mImageView);
		AppLog.i(GridAdapter.class.getSimpleName(), "ͼƬ:"+MyHttpClient.IMAGE_URL+list.get(position));
		imageLoader.displayImage(MyHttpClient.IMAGE_URL+list.get(position), mImageView,options);
		
		return convertView;
	}

}
