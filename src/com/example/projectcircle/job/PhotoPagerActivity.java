package com.example.projectcircle.job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.other.AnimationRect;
import com.example.projectcircle.other.PagerView;
import com.example.projectcircle.other.PhotoViewAttacher.OnPhotoTapListener;
import com.example.projectcircle.other.ZoomOutPageTransformer;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.Utility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;





public class PhotoPagerActivity extends Activity implements
		OnPageChangeListener {

	private ViewPager pager;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	private PhotoPagerAdapter mAdapter;

	private List<String> imgurList = new ArrayList<String>();
	AnimationRect rect;
	private TextView postionTextView;
	private TextView sumTextView;
	private HashSet<ViewGroup> unRecycledViews = new HashSet<ViewGroup>();
	private static final int STATUS_BAR_HEIGHT_DP_UNIT = 25;
	private static final int NAVIGATION_BAR_HEIGHT_DP_UNIT = 48;
	private ProgressBar loadingBar;
	
	private boolean loads2f=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_photo_pager);
		initSetting();
		initUI();
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

//		if (!loads2f) {
//			
//			try {
//				if (pager != null && unRecycledViews != null) {
//					Utility.recycleViewGroupAndChildViews(pager, true);
//					for (ViewGroup viewGroup : unRecycledViews) {
//						Utility.recycleViewGroupAndChildViews(viewGroup, true);
//					}
//
//					System.gc();
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			
//		}
		

	}

	private void initUI() {
		imgurList = getIntent().getStringArrayListExtra("imgurls");
		pager = (ViewPager) findViewById(R.id.picpager);
		sumTextView = (TextView) findViewById(R.id.sum_textV);
		postionTextView = (TextView) findViewById(R.id.index_textV);
		loadingBar = (ProgressBar) findViewById(R.id.pic_progressBar);

		sumTextView.setText("" + imgurList.size());
		int postion = getIntent().getIntExtra("postion", 0) + 1;
		postionTextView.setText("" + postion);

		mAdapter = new PhotoPagerAdapter(imgurList);
		pager.setAdapter(mAdapter);

		pager.setOnPageChangeListener(this);

		pager.setCurrentItem(getIntent().getIntExtra("postion", 0));

		pager.setOffscreenPageLimit(1);
		pager.setPageTransformer(true, new ZoomOutPageTransformer());
//		pager.setPadding(0, Utility.dip2px(STATUS_BAR_HEIGHT_DP_UNIT), 0, 0);

	}

	private void initSetting() {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.pic_failure)
				.cacheInMemory(true)
				.cacheOnDisc(true)
//				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
	}

	public class PhotoPagerAdapter extends PagerAdapter {

		private List<String> list;
		private LayoutInflater inflater;

		public PhotoPagerAdapter(List<String> list) {
			this.list = list;
			this.inflater = getLayoutInflater();
		}
       //获取要滑动的控件的数量
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
       //来判断显示的是否是同一张图片
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0.equals(arg1);
		}
       //pagerAdapter只缓存3张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			if (!loads2f) {
				if (object instanceof ViewGroup) {
					((ViewPager) container).removeView((View) object);
					unRecycledViews.remove(object);			

				}
			}
			

		}
      //当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入
	  //加入ViewGroup中，然后作为返回值返回即可
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			View imageLayout = inflater.inflate(R.layout.photo_pager_,
					container, false);
			assert imageLayout != null;

			handelImg(imageLayout, list, position);
			// container.addView(imageLayout, position);
			((ViewPager) container).addView(imageLayout, 0);
			unRecycledViews.add((ViewGroup) imageLayout);
			return imageLayout;
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
			// TODO Auto-generated method stub
			super.setPrimaryItem(container, position, object);
			View imageLayout = (View) object;
			if (imageLayout == null) {
				return;
			}

			ImageView imageView = (ImageView) imageLayout
					.findViewById(R.id.pagerView1);

			if (imageView.getDrawable() != null) {
				return;
			}

			handelImg(imageLayout, list, position);
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return super.saveState();
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		postionTextView.setText("" + (arg0 + 1));
	}

	private void handelImg(View imageLayout, List<String> list, int position) {
		final PagerView photoView = (PagerView) imageLayout
				.findViewById(R.id.pagerView1);

		photoView.setOnPhotoTapListener(new OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View view, float x, float y) {
				// TODO Auto-generated method stub
				if (photoView == null
						|| (!(photoView.getDrawable() instanceof BitmapDrawable))) {
					PhotoPagerActivity.this.finish();
					return;
				}
			}
		});

		imageLoader.displayImage(MyHttpClient.IMAGE_URL+list.get(position), photoView, options,
				new SimpleImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// TODO Auto-generated method stub
						super.onLoadingStarted(imageUri, view);
						loadingBar.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						// TODO Auto-generated method stub
						super.onLoadingFailed(imageUri, view, failReason);
						loadingBar.setVisibility(View.GONE);
//						photoView.setImageResource(R.drawable.pic_failure);
						loads2f=true;
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
						loadingBar.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						// TODO Auto-generated method stub
						super.onLoadingCancelled(imageUri, view);
						loadingBar.setVisibility(View.GONE);
//						photoView.setImageResource(R.drawable.pic_failure);
					}

				});

		// imageLoader.displayImage(list.get(position), animationView);
		//
		 if (Utility.doThisDeviceOwnNavigationBar(PhotoPagerActivity.this)) {
		 photoView.setPadding(0, 0, 0,
		 Utility.dip2px(NAVIGATION_BAR_HEIGHT_DP_UNIT));
		 }

	}

}
