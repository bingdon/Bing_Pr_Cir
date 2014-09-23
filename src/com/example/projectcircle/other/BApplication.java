package com.example.projectcircle.other;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.example.projectcircle.bean.PersonalInfo;
import com.example.projectcircle.util.FileUtils;
import com.example.projectcircle.util.SdUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BApplication extends FrontiaApplication {

	private static BApplication mInstance = null;
	public boolean m_bKeyRight = true;
	BMapManager mBMapManager = null;

	// public static final String strKey = "ThX0TveZUUOFkv6pjG7w45MF";
	// public static final String strKey = "kX80TaX65sg2aTf6CeF1DEiN";
	// bing api key
	// public static final String strKey = "N0p7SXZ0oy7vi89590qwSYTQ";
	public static final String strKey = "mcGXuNClGvdQamQ7fHdaIouX";
	private static PersonalInfo info;
//	private static Foods foods;
	private static List<ImageInfo> headerImaList = new ArrayList<ImageInfo>();

	public static List<ImageInfo> getHeaderImaList() {
		return headerImaList;
	}

	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectAll().penaltyDeath().build());
		}

		super.onCreate();
		mInstance = this;
		initEngineManager(this);
		initImageLoader(getApplicationContext());
		if (SdUtils.ExistSDCard()) {
		LoadDate();
		}
		

	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// .memoryCache(new LruMemoryCache(maxSize))
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	public static void setPersonInfo(PersonalInfo info) {
		BApplication.info = info;
	}

	public static PersonalInfo getPersonInfo() {
		return BApplication.info;
	}




	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
			Toast.makeText(BApplication.getInstance().getApplicationContext(),
					"BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
		}
	}

	public static BApplication getInstance() {
		return mInstance;
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(
						BApplication.getInstance().getApplicationContext(),
						"您的网络出错啦！", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(
						BApplication.getInstance().getApplicationContext(),
						"输入正确的检索条件！", Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			// 非零值表示key验证未通过
			if (iError != 0) {
				// 授权Key错误：
				// Toast.makeText(
				// BApplication.getInstance().getApplicationContext(),
				// "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: "
				// + iError, Toast.LENGTH_LONG).show();
				BApplication.getInstance().m_bKeyRight = false;
			} else {
				BApplication.getInstance().m_bKeyRight = true;
				// Toast.makeText(
				// BApplication.getInstance().getApplicationContext(),
				// "key认证成功", Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * 加载数据
	 */
	public static void LoadDate() {
		FileUtils.createPath();
		File[] files = new File(FileUtils.HEALTH_IMAG).listFiles();
		File f;
		String name;
		String path;

		if (null==files) {
			return;
		}
		int length = files.length;
		if (length == 0) {
			return;
		}
		for (int i = 0; i < length; i++) {
			ImageInfo info = new ImageInfo();
			f = files[i];
			if (!f.canRead()) {
				return;
			}

			name = f.getName().substring(f.getName().indexOf("_") + 1,
					f.getName().length() - 4);
			path = FileUtils.HEALTH_IMAG + "/" + f.getName();
			info.setImaname(name);
			info.setImapath(path);
			headerImaList.add(info);
		}

	}

}
