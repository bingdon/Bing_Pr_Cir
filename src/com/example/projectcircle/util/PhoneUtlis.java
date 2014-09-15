package com.example.projectcircle.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

public class PhoneUtlis {

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}
	
	
	/**
	 * 把bitmap转换成String
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToString(Bitmap bitmap) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}
	
	
	
	/**
	 * 把bitmap转换成String并压缩图片大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapzoomToString(String filePath) {

		Bitmap bm = getSmall2ZoomBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}
	
	
	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static synchronized String bitmapNCutToString(String filePath) {

		Bitmap bm = getNoCutSmallBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}
	

	/**
	 * bitmap转String
	 * 
	 * @param context
	 * @return
	 */
	public static String bitmapToString(Context context) {
		Bitmap bm = comp1(getBitmap(context));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);
	}
	public static Bitmap comp1(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 20, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 20, baos);// 这里压缩50%，把压缩后的数据存放到baos�?
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// �?��读入图片，此时把options.inJustDecodeBounds 设回true�?
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，�?��高和宽我们设置为
		float hh = 800f;// 这里设置高度�?00f
		float ww = 480;// 这里设置宽度�?80f
		// 缩放比�?由于是固定比例缩放，只用高或者宽其中�?��数据进行计算即可
		int be = 1;// be=1表示不缩�?
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩�?
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩�?
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例

		newOpts.inPreferredConfig = Config.ARGB_8888;

		newOpts.inPurgeable = true;// 允许可清除

		newOpts.inInputShareable = true;// 以上options的两个属性必须联合使用才会有效果

		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false�?
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;// 压缩好比例大小后再进行质量压�?
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {

		Matrix matrix = new Matrix();
		matrix.setRotate(0);
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		
		Bitmap mBitmap = BitmapFactory.decodeFile(filePath, options);
		float width=mBitmap.getWidth();
		float height=mBitmap.getHeight();
		float ratio=width/height;
		mBitmap = Bitmap.createBitmap(mBitmap, (int) (mBitmap.getWidth()/3),
				(int) (mBitmap.getHeight()-mBitmap.getHeight()*ratio/3)/2, (int) (mBitmap.getWidth()/3),
				(int) (mBitmap.getHeight() / 3*ratio), matrix, true);
		
		saveFoodPic2Example(mBitmap);
		return mBitmap;
	}
	
	/**
	 * 保存图片示例
	 * @param mBitmap
	 */
	public static void saveFoodPic2Example(Bitmap mBitmap) {
		File file = new File(FileUtils.HEALTH_IMAG, "llllllllllll" + ".png");
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			mBitmap.compress(CompressFormat.PNG, 100, bos);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getNoCutSmallBitmap(String filePath) {

//		Matrix matrix = new Matrix();
//		matrix.setRotate(ScanningActivity.angle);

	
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 400, 400);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		
		Bitmap mBitmap = BitmapFactory.decodeFile(filePath, options);
//		float width=mBitmap.getWidth();
//		float height=mBitmap.getHeight();
//		float ratio=width/height;
//		mBitmap = Bitmap.createBitmap(mBitmap, (int) (mBitmap.getWidth()/3),
//				(int) (mBitmap.getHeight()-mBitmap.getHeight()*ratio/3)/2, (int) (mBitmap.getWidth()/3),
//				(int) (mBitmap.getHeight() / 3*ratio), matrix, true);
		

		return mBitmap;
	}
	

	
	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示且压缩大小为50x50
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmall2ZoomBitmap(String filePath) {

		Matrix matrix = new Matrix();
		matrix.setRotate(0);

		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		
		Bitmap mBitmap = BitmapFactory.decodeFile(filePath, options);
		float width=mBitmap.getWidth();
		float height=mBitmap.getHeight();
		float ratio=width/height;
		mBitmap = Bitmap.createBitmap(mBitmap, (int) (mBitmap.getWidth()/3),
				(int) (mBitmap.getHeight()-mBitmap.getHeight()*ratio/3)/2, (int) (mBitmap.getWidth()/3),
				(int) (mBitmap.getHeight() / 3*ratio), matrix, true);

		mBitmap=zoomImage(mBitmap, 30, 30);
		
//		SavePic.saveFoodPic2Example(mBitmap);
		
		return mBitmap;
	}
	
	
	/**
	 * 从手机内存获得图片
	 * 
	 * @param context
	 * @return
	 */
	public static Bitmap getBitmap(Context context) {
		Matrix matrix = new Matrix();
		matrix.setRotate(0);
		byte[] data;
		Bitmap mBitmap;
		InputStream ies;
		try {
			ies = context.openFileInput(FileUtils.WYY_PIC);
			ObjectInputStream obi = new ObjectInputStream(ies);
			data = (byte[]) obi.readObject();
			obi.close();
			ies.close();
			mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			mBitmap = Bitmap.createBitmap(mBitmap, mBitmap.getWidth() / 3,
					mBitmap.getHeight() / 3, mBitmap.getWidth() / 3,
					mBitmap.getHeight() / 3, matrix, true);
			return mBitmap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 获取头像
	 * 
	 * @param context
	 * @return
	 */
	public static Bitmap getHeadBitmap(Context context) {
		Matrix matrix = new Matrix();
		matrix.setRotate(0);
		Bitmap mBitmap;
		InputStream ies;
		try {
			ies = context.openFileInput(FileUtils.HEAD_PATH);
			ObjectInputStream obi = new ObjectInputStream(ies);
			mBitmap = (Bitmap) obi.readObject();
			obi.close();
			ies.close();
			mBitmap = Bitmap.createBitmap(mBitmap, mBitmap.getWidth() / 3,
					mBitmap.getHeight() / 3, mBitmap.getWidth() / 3,
					mBitmap.getHeight() / 3, matrix, true);
			return mBitmap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 根据路径删除图片
	 * 
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 添加到图库
	 */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	/**
	 * 获取保存图片的目录
	 * 
	 * @return
	 */
	public static File getAlbumDir() {
		File dir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				getAlbumName());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 获取保存 隐患检查的图片文件夹名称
	 * 
	 * @return
	 */
	public static String getAlbumName() {
		return "sheguantong";
	}

	
	public static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {
			baos.reset();
			image.compress(Bitmap.CompressFormat.JPEG, 20, baos);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 400f;
		float ww = 300f;
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;

		newOpts.inPreferredConfig = Config.ARGB_8888;

		newOpts.inPurgeable = true;

		newOpts.inInputShareable = true;

		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;
	}
	
	
	/***
     * 图片的缩放方法
     * 
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                    double newHeight) {
            // 获取这个图片的宽和高
            float width = bgimage.getWidth();
            float height = bgimage.getHeight();
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // 缩放图片动作
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                            (int) height, matrix, true);
            return bitmap;
    }
	
}
