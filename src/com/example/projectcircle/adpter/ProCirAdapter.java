package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.List;

import com.example.projectcircle.R;
import com.example.projectcircle.bean.CommentBean;
import com.example.projectcircle.bean.MoodBean;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.ChaoGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ProCirAdapter extends BaseAdapter {

	private List<MoodBean> list;

	private LayoutInflater inflater;

	ImageLoader imageLoader = ImageLoader.getInstance();

	DisplayImageOptions options;
	
	private ShaiItemOnclickListener listener;
	
	private Context context;
	
	private int comPostion=0;

	public void setListener(ShaiItemOnclickListener listener) {
		this.listener = listener;
	}

	public ProCirAdapter(Context context, List<MoodBean> list) {
		this.list = list;
		this.context=context;
		inflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.showStubImage(R.drawable.icon_qunzu)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_qunzu)
				.showImageOnFail(R.drawable.icon_qunzu)
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
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
		ViewHolder holder;
		final int newposition = position;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.pro_item, parent,false);
			holder.name = (TextView) convertView
					.findViewById(R.id.shai_username_txt);
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
			holder.commeninfoTextView = (ListView) convertView
					.findViewById(R.id.commentlist);
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
		
			// headImageFetcher.loadImage(list.get(position).get("headimage"),
			// holder.userheadImageView);
			imageLoader.displayImage(MyHttpClient.IMAGE_URL+list.get(position).getUser().getHeadimage()
					+"", holder.headimg, options);
			holder.name.setText(""
					+ list.get(position).getUser().getUsername());
			holder.time.setText(""
					+ list.get(position).getCn_time());
			holder.zanTextView.setText(""
					+ list.get(position).getLaudcount());
			holder.commencounTextView.setText(""
					+ list.get(position).getCommentcount());
			List<CommentBean> commentBeans=list.get(position).getComment();
			AppLog.i(ProCirAdapter.class.getSimpleName(), "消息:"+commentBeans.size());
			if (commentBeans!=null&&commentBeans.size()>0) {
				holder.commentAdapter=new CommentAdapter(context, commentBeans);
				holder.commeninfoTextView.setAdapter(holder.commentAdapter);
				holder.commeninfoTextView.setVisibility(View.VISIBLE);
				holder.reasonTextView.setVisibility(View.VISIBLE);
			}
			
		if (!TextUtils.isEmpty(list.get(position).getContext())) {
			holder.content.setText(""
					+ list.get(position).getContext());		
			holder.content.setVisibility(View.VISIBLE);
		} else {
			holder.content.setVisibility(View.GONE);
		}
		if (list.get(position).getImg()!=null&&list.get(position).getImg().size()>0) {
			List<String> picList = new ArrayList<String>();
			picList =  list.get(position).getImg();
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
		holder.commeninfoTextView.setFocusable(false);
		holder.commencounTextView.setFocusableInTouchMode(false);
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
					comPostion=newposition;
					listener.onCommentClick(comPostion);
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

		
		return convertView;
	}

	/**
	 * 点击接口
	 * 
	 * @author lyl
	 * 
	 */
	public interface ShaiItemOnclickListener {
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
	
	/* 存放控件 */
	public final class ViewHolder {

		public ListView commeninfoTextView;
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
		public CommentAdapter commentAdapter;

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
