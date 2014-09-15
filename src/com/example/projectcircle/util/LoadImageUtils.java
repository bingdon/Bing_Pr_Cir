package com.example.projectcircle.util;

import com.example.projectcircle.app.MyApplication;

import android.widget.ImageView;

public class LoadImageUtils {

	public static void displayImg(String url, ImageView imageView) {
		try {
			MyApplication.getImageLoader().displayImage(url, imageView,
					MyApplication.getOptions());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
