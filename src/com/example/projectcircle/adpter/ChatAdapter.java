package com.example.projectcircle.adpter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.other.ChatMsgEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 聊天内容容器
 * 
 * @author lyl
 * 
 */
public class ChatAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}

	private List<ChatMsgEntity> coll;

	@SuppressWarnings("unused")
	private Context ctx;

	private LayoutInflater mInflater;

	private Bitmap mBitmap;

	DisplayImageOptions options;
	private ImageLoader imageLoader=ImageLoader.getInstance();

	public ChatAdapter(Context context, List<ChatMsgEntity> coll) {
		ctx = context;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.showStubImage(R.drawable.logo)
				.showImageOnFail(R.drawable.logo)
				.showImageForEmptyUri(R.drawable.logo)
				.build();
	}

	@Override
	public int getCount() {
		return coll.size();
	}

	@Override
	public Object getItem(int position) {
		return coll.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		ChatMsgEntity entity = coll.get(position);

		if (entity.getMsgType()) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}

	}

	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getMsgType();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg) {

				convertView = mInflater.inflate(
						R.layout.chatting_msg_text_left, null);
				viewHolder = new ViewHolder();
				viewHolder.userHeadIma = (ImageView) convertView
						.findViewById(R.id.iv_head);
				viewHolder.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_cometime);
				viewHolder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_content);
				viewHolder.isComMsg = isComMsg;
				convertView.setTag(viewHolder);
			} else {
				convertView = mInflater.inflate(
						R.layout.chatting_msg_text_right, null);
				viewHolder = new ViewHolder();
				viewHolder.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_sendtime);
				viewHolder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_chatcontent);
				viewHolder.isComMsg = isComMsg;
				convertView.setTag(viewHolder);
				viewHolder.userHeadIma = (ImageView) convertView
						.findViewById(R.id.iv_userhead);
				viewHolder.wait_send = (ProgressBar) convertView
						.findViewById(R.id.chat_pro);

			}

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tvSendTime.setText(entity.getDate());
		viewHolder.tvContent.setText(entity.getText());

		if (!isComMsg) {
			if (entity.isSend_state()) {
				viewHolder.wait_send.setVisibility(View.INVISIBLE);
			}

			imageLoader.displayImage(HomeActivity.myUserhaedurl, viewHolder.userHeadIma, options);
			
		}else {
			imageLoader.displayImage(entity.getHeadimgString(), viewHolder.userHeadIma, options);
		}

		// if (!isComMsg) {
		//
		// if (null != mBitmap) {
		// viewHolder.userHeadIma.setImageBitmap(mBitmap);
		// // Log.i("聊天", "mBitmap:" + mBitmap);
		// } else {
		//
		// if (BApplication.getHeaderImaList().size() > 0) {
		// for (int i = 0; i < BApplication.getHeaderImaList().size(); i++) {
		// if (BApplication
		// .getHeaderImaList()
		// .get(i)
		// .getImaname()
		// .equals(BApplication.getPersonInfo()
		// .getUsername())) {
		// mBitmap = BitmapFactory.decodeFile(BApplication
		// .getHeaderImaList().get(i).getImapath());
		// viewHolder.userHeadIma.setImageBitmap(mBitmap);
		// }
		// }
		// } else {
		//
		// String headimage = MyHttpClient.IMAGE_URL
		// + BApplication.getPersonInfo().getHeadimage();
		// Log.i("头像", "头像:"+headimage);
		// try {
		// Bitmap mbitmap = ImageUtil.getBitmapFromUrl(headimage);
		// mbitmap = Bmprcy.toRoundBitmap(mbitmap);
		// viewHolder.userHeadIma.setImageBitmap(mbitmap);
		// File file = new File(FileUtils.HEALTH_IMAG,
		// BApplication.getPersonInfo().getUsername()
		// + ".png");
		// BufferedOutputStream bos = new BufferedOutputStream(
		// new FileOutputStream(file));
		// mbitmap.compress(CompressFormat.PNG, 100, bos);
		// bos.flush();
		// bos.close();
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		//
		// }
		//
		// if (coll.get(position).isSend_state()) {
		// viewHolder.wait_send.setVisibility(View.INVISIBLE);
		// } else {
		// viewHolder.wait_send.setVisibility(View.VISIBLE);
		// }
		//
		// }

		return convertView;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvContent;
		public ImageView userHeadIma;
		public boolean isComMsg = true;
		public ProgressBar wait_send;
	}

}
