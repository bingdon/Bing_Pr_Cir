package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.util.TextViewUtils;
import com.example.projectcircle.view.ChaoGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;






/**
 * Friend的Adapter
 * 
 */
@SuppressLint("NewApi")
public class CircleAdapter extends BaseAdapter {
	private static DisplayImageOptions options;
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	GridAdapter gridadapter;
	private ImageLoadingListener animateFirstListener = (ImageLoadingListener) new AnimateFirstDisplayListener();
	private ImageLoaderConfiguration config;
	// private ImageFetcher foodpicFetcher;
	private String tag;
	ImageLoader imageLoader = ImageLoader.getInstance();
	private ShaiItemOnclickListener listener;


	/* 构造函数 */
	public CircleAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);
		// this.foodpicFetcher = new ImageFetcher(context, 40);
		// this.foodpicFetcher.setLoadingImage(R.drawable.camera);
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
		return listItem.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listItem.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public void setOnClickListener(ShaiItemOnclickListener listener) {
		this.listener = listener;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		final int newposition = position;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.new_projectcircle, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.shai_username_txt);
			holder.content = (TextView) convertView
					.findViewById(R.id.shai_content_txt);			
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.shai_head_img);
			
			holder.time = (TextView) convertView
			.findViewById(R.id.shai_time_txt);
			// gridView
			holder.picGridView = (ChaoGridView) convertView
					.findViewById(R.id.shai_gridView1);
			holder.commencounTextView = (TextView) convertView
					.findViewById(R.id.shai_pinglun_count_txt);
			holder.reasonTextView = (TextView) convertView
					.findViewById(R.id.shai_reason_txt);
			holder.commeninfoTextView = (TextView) convertView
					.findViewById(R.id.shai_comment_txt);
			holder.commenImageView = (ImageView) convertView
					.findViewById(R.id.shai_pinglun_img);
			holder.zanImageView = (ImageView) convertView
					.findViewById(R.id.shai_zan_img);			
			holder.zanTextView = (TextView) convertView
					.findViewById(R.id.shai_zan_count_txt);
			convertView.setTag(holder);// 绑定ViewHolder对象
		}

		else {
			holder = (ViewHolder) convertView.getTag();
		}
		Log.i("headimage",listItem.get(position).get("headimage")+"");
		Log.i("grid_pic",listItem.get(position).get("grid_pic")+"");
		Log.i("context",listItem.get(position).get("context")+"");
		if (listItem.get(position).containsKey("headimage")
				&& !TextUtils.isEmpty(listItem.get(position).get("headimage")
						.toString())) {
			// headImageFetcher.loadImage(list.get(position).get("headimage"),
			// holder.userheadImageView);
			imageLoader.displayImage(listItem.get(position).get("headimage")
					+"", holder.headimg, options);
		}
		if (listItem.get(position).containsKey("username")) {
			holder.name.setText(""
					+ listItem.get(position).get("username"));
		}
		if (listItem.get(position).containsKey("time")) {
			holder.time.setText(""
					+ listItem.get(position).get("new_time"));
		}
		if (listItem.get(position).containsKey("laudcount")) {
			holder.zanTextView.setText(""
					+ listItem.get(position).get("laudcount"));
		}
		if (listItem.get(position).containsKey("commentcount") && !(listItem.get(position).get("commentcount")+"").equals("null")) {
			holder.commencounTextView.setText(""
					+ listItem.get(position).get("commentcount"));
		}
		if (listItem.get(position).containsKey("comment") && !((listItem.get(position).get("comment")+"").equals("null"))) {
//			holder.commeninfoTextView.setText(""
//					+ listItem.get(position).get("comment"));
			holder.commeninfoTextView.setText(TextViewUtils
					.getSpannableStringBuilder(""
							+ listItem.get(position).get("comment")));
			holder.commeninfoTextView.setVisibility(View.VISIBLE);
			holder.reasonTextView.setVisibility(View.VISIBLE);
		} else {
			holder.commeninfoTextView.setVisibility(View.GONE);
			holder.reasonTextView.setVisibility(View.GONE);
		}
		if (listItem.get(position).containsKey("context")) {
			holder.content.setText(""
					+ listItem.get(position).get("context"));		
			holder.content.setVisibility(View.VISIBLE);
		} else {
			holder.content.setVisibility(View.GONE);
		}
		if (listItem.get(position).containsKey("grid_pic")) {
			List<String> picList = new ArrayList<String>();
			picList = (List<String>) listItem.get(position).get("grid_pic");
			if (null != picList) {
				if (picList.size() > 0) {				
					setGridView(holder.picGridView, picList.size());
					holder.gridAdapter = new GridAdapter(context, picList);
					holder.picGridView.setAdapter(holder.gridAdapter);
					holder.picGridView.setVisibility(View.VISIBLE);
				} else {
					holder.picGridView.setVisibility(View.GONE);
				}
			} else {
				holder.picGridView.setVisibility(View.GONE);
			}

		} else {
			holder.picGridView.setVisibility(View.GONE);
		}
		holder.picGridView.setFocusable(false);
		holder.picGridView.setFocusableInTouchMode(false);
		holder.picGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (listener!=null) {
					listener.onPicClick(newposition, position);
				}
				

			}
		});

		holder.commenImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listener!=null) {
					listener.onCommentClick(newposition);
				}
				
			}
		});


		holder.zanImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listener!=null) {
					listener.onZanClick(newposition);
				}
				
			}
		});

		holder.headimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listener!=null) {
					listener.onUserPicClick(newposition);
				}
				
			}
		});
		return convertView;

	}

	

	/* 存放控件 */
	public final class ViewHolder {

		public TextView commeninfoTextView;
		public TextView reasonTextView;
		public TextView commencounTextView;
		public ChaoGridView picGridView;
		public TextView content;
		public TextView name;
		public ImageView headimg;
		public ImageView photo;
		public GridAdapter gridAdapter;
		public ImageView zanImageView;
		public TextView zanTextView;
		public ImageView commenImageView;
		public TextView time;

	}

	private class LoaderListener extends AnimateFirstDisplayListener {
		ImageView imageview;

		public LoaderListener(ImageView imageView) {
			this.imageview = imageView;
		}

		public void onLoadingCancelled(String imageUri, View view) {
			// TODO Auto-generated method stub
			super.onLoadingCancelled(imageUri, view);
			this.imageview.setVisibility(View.GONE);
		}

		@Override
		public void onLoadingFailed(String imageUri, View view,
				FailReason failReason) {
			// TODO Auto-generated method stub
			super.onLoadingFailed(imageUri, view, failReason);
			this.imageview.setVisibility(View.GONE);
		}

	}
	/**
	 * 点击接口
	 * 
	 * @author lyl
	 * 
	 */
	public interface ShaiItemOnclickListener {
		/**
		 * 食物点击方法
		 */
		public void onUserPicClick(int position);

		/**
		 * 评论点击
		 * 
		 * @param position
		 *            位置
		 */
		public void onCommentClick(int position);

		/**
		 * 赞点击
		 * 
		 * @param position
		 *            点击位置
		 */
		public void onZanClick(int position);


		/**
		 * 图片点击
		 * 
		 * @param listPostino
		 *            list位置
		 * @param picPostion
		 *            图片位置
		 */
		public void onPicClick(int listPostino, int picPostion);

	}

	private void setGridView(GridView gridView, int m) {
		switch (m) {
		case 1:

			gridView.setNumColumns(1);//设置列数为1
			
			break;

		case 2:

			gridView.setNumColumns(2);

			break;

		case 3:

			gridView.setNumColumns(2);
			gridView.setHorizontalSpacing(2);

			break;

		case 4:

			gridView.setNumColumns(2);
			gridView.setHorizontalSpacing(2);

			break;

		default:
			break;
		}
	}

}
