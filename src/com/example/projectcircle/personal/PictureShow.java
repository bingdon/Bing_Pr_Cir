package com.example.projectcircle.personal;

import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class PictureShow extends Activity{
	
	ImageLoader imageLoader = ImageLoader.getInstance();	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private String image;	
	DisplayImageOptions options;
	private ImageView picture_show;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_show);
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
		picture_show =(ImageView)findViewById(R.id.picture_show);
		Intent intent = getIntent();
		image = intent.getStringExtra("image");
		 imageLoader.displayImage(
		 MyHttpClient.IMAGE_URL
		 + image,
		 picture_show, options, animateFirstListener);
		}
}
