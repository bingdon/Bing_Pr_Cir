package com.example.projectcircle.util;

import com.example.projectcircle.MainActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.setting.MsgSettingActivity;
import com.example.projectcircle.setting.SettingActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

public class NoticeUtils {

	private static NotificationManager notificationManager;

	private static Handler mHandler = new Handler();

	public static void noticeMsg(Context context, String message, String man,
			int id) {

		if (null == notificationManager) {
			notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		}

		PendingIntent pendingIntent = null;

		Notification notification = null;

		switch (id) {
		case ContantS.CHAT_MSG:

			pendingIntent = PendingIntent
					.getActivity(context, 0, new Intent(context,
							MainActivity.class), Intent.FLAG_ACTIVITY_NEW_TASK);
			if (!MsgSettingActivity.getIsmsg(context)) {
				return;
			}

			if (MsgSettingActivity.getIssound(context)
					&& MsgSettingActivity.getIsvir(context)) {
				notification = new NotificationCompat.Builder(context)
						.setTicker(man).setContentText(man)
						.setContentTitle(man).setContentIntent(pendingIntent)
						.setContentText(message).setSmallIcon(R.drawable.logo)
						.setDefaults(Notification.DEFAULT_ALL)
						.setAutoCancel(true).build();
			} else if (MsgSettingActivity.getIssound(context)) {
				notification = new NotificationCompat.Builder(context)
						.setTicker(man).setContentText(man)
						.setContentTitle(man).setContentIntent(pendingIntent)
						.setContentText(message).setSmallIcon(R.drawable.logo)
						.setDefaults(Notification.DEFAULT_SOUND)
						.setAutoCancel(true).build();
			} else if (MsgSettingActivity.getIsvir(context)) {
				notification = new NotificationCompat.Builder(context)
						.setTicker(man).setContentText(man)
						.setContentTitle(man).setContentIntent(pendingIntent)
						.setContentText(message).setSmallIcon(R.drawable.logo)
						.setDefaults(Notification.DEFAULT_VIBRATE)
						.setAutoCancel(true).build();
			} else {
				notification = new NotificationCompat.Builder(context)
						.setTicker(man).setContentText(man)
						.setContentTitle(man).setContentIntent(pendingIntent)
						.setContentText(message).setSmallIcon(R.drawable.logo)
						.setAutoCancel(true).build();
			}

			notificationManager.notify(ContantS.CHAT_MSG, notification);

			break;

		default:
			break;
		}

	}

	public static void removeNotice(int id, Context context) {
		if (null == notificationManager) {
			notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		}

		notificationManager.cancel(id);
	}

	public static void showSuccessfulNotification(Context context) {
		if (null == notificationManager) {
			notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context)
				.setTicker(context.getString(R.string.send_successfully))
				.setContentTitle(context.getString(R.string.send_successfully))
				.setOnlyAlertOnce(true).setAutoCancel(true)
				.setSmallIcon(R.drawable.send_successfully).setOngoing(false);
		Notification notification = builder.build();
		notificationManager.notify(100, notification);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				notificationManager.cancel(100);
			}
		}, 3000);
	}

	public static void showFailePublish(Context context) {

		if (null == notificationManager) {
			notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		}

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context).setTicker(context.getString(R.string.send_failed))
				.setContentTitle(context.getString(R.string.send_failed))
				.setOnlyAlertOnce(true).setAutoCancel(true)
				.setSmallIcon(R.drawable.send_failed).setOngoing(false);

		Notification notification = builder.build();
		notificationManager.notify(100, notification);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				notificationManager.cancel(100);
			}
		}, 3000);

	}

	/**
	 * 新通知
	 * 
	 * @param context
	 *            上下文
	 * @param message
	 *            消息
	 */
	@SuppressWarnings("deprecation")
	public static void notice(Context context, String message, int id) {
		if (null == notificationManager) {
			notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		}

		PendingIntent pendingIntent = null;

		Notification notification = null;

		switch (id) {
		case ContantS.PUBLISH_MOOD_ID:

			notification = new NotificationCompat.Builder(context)

			.setTicker(context.getString(R.string.sending))
					.setContentTitle(context.getString(R.string.sending))
					.setContentText(message).setContentIntent(pendingIntent)
					.setOnlyAlertOnce(true).setOngoing(true)
					.setSmallIcon(R.drawable.upload_white).build();
			notificationManager.notify(id, notification);
			break;

		case ContantS.PUBLISH_SHAI_ID:

			notification = new NotificationCompat.Builder(context)

			.setTicker(context.getString(R.string.sending))
					.setContentTitle(context.getString(R.string.sending))
					.setContentText(message).setContentIntent(pendingIntent)
					.setOnlyAlertOnce(true).setOngoing(true)
					.setSmallIcon(R.drawable.upload_white).build();
			notificationManager.notify(id, notification);
			break;

		default:
			break;
		}
	}

	public static void showProgressPublish(Context context, int progress,
			int max, int id) {
		if (null == notificationManager) {
			notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		}

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context).setTicker(context.getString(R.string.send_photo))
				.setContentTitle(context.getString(R.string.send_photo))
				.setOnlyAlertOnce(true).setAutoCancel(true)
				.setSmallIcon(R.drawable.upload_white)
				.setProgress(max, progress, false).setOngoing(false);
		Notification notification = builder.build();
		notificationManager.notify(id, notification);

	}
}
