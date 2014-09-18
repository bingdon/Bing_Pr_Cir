package com.example.projectcircle.util;

import java.io.ByteArrayOutputStream;


import com.example.projectcircle.R;
import com.example.projectcircle.group.CreateGroup;
import com.tencent.mm.sdk.openapi.IWXAPI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

public class ShareUtils {
	private static final int MMAlertSelect1 = 0;
	private static final int MMAlertSelect2 = 1;
	private static final int MMAlertSelect3 = 2;

	public static void share2Fre(Context context, String msg) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(
				Intent.EXTRA_TEXT,
				context.getString(R.string.share_content) + "["+msg+"],"
						+ context.getString(R.string.share_content_)
						+ CreateGroup.getGid()
						+ context.getString(R.string.share_content_tail));
		shareIntent.setType("text/plain");
		context.startActivity(Intent.createChooser(shareIntent,
				context.getString(R.string.tell_other)));
	}

//	public static void shareWx(final Context context, final IWXAPI api) {
//		MMAlert.showAlert(context, context.getString(R.string.send_webpage),
//				context.getResources()
//						.getStringArray(R.array.send_webpage_item), null,
//				new MMAlert.OnAlertSelectId() {
//
//					@Override
//					public void onClick(int whichButton) {
//						// TODO Auto-generated method stub
//						WXWebpageObject webpage = new WXWebpageObject();
//						webpage.webpageUrl = "http://www.baidu.com";
//						WXMediaMessage msg = new WXMediaMessage(webpage);
//						msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
//						msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
//						Bitmap thumb = BitmapFactory.decodeResource(
//								context.getResources(),
//								R.drawable.send_music_thumb);
//						msg.thumbData = bmpToByteArray(thumb, true);
//
//						SendMessageToWX.Req req = new SendMessageToWX.Req();
//						req.transaction = buildTransaction("webpage");
//						req.message = msg;
//						req.scene = SendMessageToWX.Req.WXSceneTimeline;
//						api.sendReq(req);
//					}
//				});
//	}

	public static String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
