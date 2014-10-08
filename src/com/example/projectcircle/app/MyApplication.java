package com.example.projectcircle.app;

import io.rong.imkit.RongIM;

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
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.WelcomeActivity;
import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.bean.PersonalInfo;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.other.ImageInfo;
import com.example.projectcircle.util.FileUtils;
import com.example.projectcircle.util.SdUtils;
import com.example.projectcircle.util.UserInfoUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


/*
 * 如果您的工程中实现了Application的继承类，那么，您需要将父类改为com.baidu.frontia.FrontiaApplication。
 * 如果您没有实现Application的继承类，那么，请在AndroidManifest.xml的Application标签中增加属性： 
 * <application android:name="com.baidu.frontia.FrontiaApplication"
 * 。。。
 */
public class MyApplication  extends FrontiaApplication  {
	private static UserInfo info;
	public static final String strKey = "xerN6Ac7GdD3iAVwVRguCVlQ";
	
	private static MyPersonBean myPersonBean;
	private static MyApplication mInstance = null;
	public boolean m_bKeyRight = true;
	BMapManager mBMapManager = null;

	// public static final String strKey = "ThX0TveZUUOFkv6pjG7w45MF";
	// public static final String strKey = "kX80TaX65sg2aTf6CeF1DEiN";
	// bing api key
	// public static final String strKey = "N0p7SXZ0oy7vi89590qwSYTQ";
//	private static Foods foods;
	private static List<ImageInfo> headerImaList = new ArrayList<ImageInfo>();

	private static ImageLoader imageLoader=ImageLoader.getInstance();
	
	public static ImageLoader getImageLoader() {
		return imageLoader;
	}

	private static DisplayImageOptions options;
	
	public static DisplayImageOptions getOptions() {
		return options;
	}

	public static MyPersonBean getMyPersonBean() {
		if (!WelcomeActivity.isWel) {
			if (myPersonBean==null) {
				try {
					UserInfoUtils.getPersonInfo(getInstance());
					LoginActivity.id=myPersonBean.getId();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		}
		return myPersonBean;
	}

	public static void setMyPersonBean(MyPersonBean myPersonBean) {
		MyApplication.myPersonBean = myPersonBean;
	}
	public static List<ImageInfo> getHeaderImaList() {
		return headerImaList;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoader(getApplicationContext());
//		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//					.detectAll().penaltyDialog().build());
//			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//					.detectAll().penaltyDeath().build());
//		}

//		super.onCreate();
		mInstance = this;
		RongIM.init(mInstance, ContantS.RONG_APP_ID, R.drawable.logo);
//		initEngineManager(this);
//		initImageLoader(getApplicationContext());
//		if (SdUtils.ExistSDCard()) {
//		LoadDate();
//		}

	}

	/** 初始化图片加载类配置信息 **/
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)// 加载图片的线程数
				.denyCacheImageMultipleSizesInMemory() // 解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置磁盘缓存文件名称
				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置加载显示图片队列进程
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.showStubImage(R.drawable.icon_qunzu)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_qunzu)//
				.showImageOnFail(R.drawable.icon_qunzu)//
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.build();

	}

	public static void setPersonInfo(UserInfo info) {
		MyApplication.info = info;
	}
	public static  UserInfo getPersonInfo() {
		return info;
	}

//	public void initEngineManager(Context context) {
//		if (mBMapManager == null) {
//			mBMapManager = new BMapManager(context);
//		}
//
//		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
//			Toast.makeText(MyApplication.getInstance().getApplicationContext(),
//					"BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
//		}
//	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(
						MyApplication.getInstance().getApplicationContext(),
						"您的网络出错啦！", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(
						MyApplication.getInstance().getApplicationContext(),
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
				// MyApplication.getInstance().getApplicationContext(),
				// "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: "
				// + iError, Toast.LENGTH_LONG).show();
				MyApplication.getInstance().m_bKeyRight = false;
			} else {
				MyApplication.getInstance().m_bKeyRight = true;
				// Toast.makeText(
				// MyApplication.getInstance().getApplicationContext(),
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
