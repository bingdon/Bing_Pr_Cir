package com.example.projectcircle.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

public class PhoneUtlis {

	/**
	 * 鎶奲itmap杞崲鎴怱tring
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
	 * 鎶奲itmap杞崲鎴怱tring
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToString(Bitmap bitmap) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}

	/**
	 * 鎶奲itmap杞崲鎴怱tring骞跺帇缂╁浘鐗囧ぇ灏�
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
	 * 鎶奲itmap杞崲鎴怱tring
	 * 
	 * @param filePath
	 * @return
	 */
	public static synchronized String bitmapNCutToString(String filePath) {

		Bitmap bm = getNoCutSmallBitmap(filePath);
		bm=comp(bm);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}

	/**
	 * bitmap杞琒tring
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
		if (baos.toByteArray().length / 1024 > 1024) {// 鍒ゆ柇濡傛灉鍥剧墖澶т簬1M,杩涜鍘嬬缉閬垮厤鍦ㄧ敓鎴愬浘鐗囷紙BitmapFactory.decodeStream锛夋椂婧㈠嚭
			baos.reset();// 閲嶇疆baos鍗虫竻绌篵aos
			image.compress(Bitmap.CompressFormat.JPEG, 20, baos);// 杩欓噷鍘嬬缉50%锛屾妸鍘嬬缉鍚庣殑鏁版嵁瀛樻斁鍒癰aos锟�
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 锟�锟斤拷璇诲叆鍥剧墖锛屾鏃舵妸options.inJustDecodeBounds 璁惧洖true锟�
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 鐜板湪涓绘祦鎵嬫満姣旇緝澶氭槸800*480鍒嗚鲸鐜囷紝锟�锟斤拷楂樺拰瀹芥垜浠缃负
		float hh = 800f;// 杩欓噷璁剧疆楂樺害锟�00f
		float ww = 480;// 杩欓噷璁剧疆瀹藉害锟�80f
		// 缂╂斁姣旓拷?鐢变簬鏄浐瀹氭瘮渚嬬缉鏀撅紝鍙敤楂樻垨鑰呭鍏朵腑锟�锟斤拷鏁版嵁杩涜璁＄畻鍗冲彲
		int be = 1;// be=1琛ㄧず涓嶇缉锟�
		if (w > h && w > ww) {// 濡傛灉瀹藉害澶х殑璇濇牴鎹搴﹀浐瀹氬ぇ灏忕缉锟�
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 濡傛灉楂樺害楂樼殑璇濇牴鎹搴﹀浐瀹氬ぇ灏忕缉锟�
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 璁剧疆缂╂斁姣斾緥

		newOpts.inPreferredConfig = Config.ARGB_8888;

		newOpts.inPurgeable = true;// 鍏佽鍙竻闄�

		newOpts.inInputShareable = true;// 浠ヤ笂options鐨勪袱涓睘鎬у繀椤昏仈鍚堜娇鐢ㄦ墠浼氭湁鏁堟灉

		// 閲嶆柊璇诲叆鍥剧墖锛屾敞鎰忔鏃跺凡缁忔妸options.inJustDecodeBounds 璁惧洖false锟�
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;// 鍘嬬缉濂芥瘮渚嬪ぇ灏忓悗鍐嶈繘琛岃川閲忓帇锟�
	}

	/**
	 * 璁＄畻鍥剧墖鐨勭缉鏀惧��
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
	 * 鏍规嵁璺緞鑾峰緱绐佺牬骞跺帇缂╄繑鍥瀊itmap鐢ㄤ簬鏄剧ず
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
		float width = mBitmap.getWidth();
		float height = mBitmap.getHeight();
		float ratio = width / height;
		mBitmap = Bitmap
				.createBitmap(mBitmap, (int) (mBitmap.getWidth() / 3),
						(int) (mBitmap.getHeight() - mBitmap.getHeight()
								* ratio / 3) / 2,
						(int) (mBitmap.getWidth() / 3),
						(int) (mBitmap.getHeight() / 3 * ratio), matrix, true);

		
		ExifInterface exif = null;
		 try {  
            exif = new ExifInterface(filePath);  
        } catch (IOException e) {  
            e.printStackTrace();  
            exif = null;  
        } 
		 
		 int digree=0;
		 if (exif != null) {  
            // 读取图片中相机方向信息  
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,  
                    ExifInterface.ORIENTATION_UNDEFINED);  
            // 计算旋转角度  
            switch (ori) {  
            case ExifInterface.ORIENTATION_ROTATE_90:  
                digree = 90;  
                break;  
            case ExifInterface.ORIENTATION_ROTATE_180:  
                digree = 180;  
                break;  
            case ExifInterface.ORIENTATION_ROTATE_270:  
                digree = 270;  
                break;  
            default:  
                digree = 0;  
                break;  
            }  
        }  
		
		 if (digree != 0) {  
             // 旋转图片  
             Matrix m = new Matrix();  
             m.postRotate(digree);  
             mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),  
            		 mBitmap.getHeight(), m, true);  
         }  
		 
//		saveFoodPic2Example(mBitmap);
		return mBitmap;
	}

	/**
	 * 淇濆瓨鍥剧墖绀轰緥
	 * 
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
	 * 鏍规嵁璺緞鑾峰緱绐佺牬骞跺帇缂╄繑鍥瀊itmap鐢ㄤ簬鏄剧ず
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getNoCutSmallBitmap(String filePath) {

		// Matrix matrix = new Matrix();
		// matrix.setRotate(ScanningActivity.angle);

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		ExifInterface exif = null;
		 try {  
             exif = new ExifInterface(filePath);  
         } catch (IOException e) {  
             e.printStackTrace();  
             exif = null;  
         } 
		 
		 int digree=0;
		 if (exif != null) {  
             // 读取图片中相机方向信息  
             int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,  
                     ExifInterface.ORIENTATION_UNDEFINED);  
             // 计算旋转角度  
             switch (ori) {  
             case ExifInterface.ORIENTATION_ROTATE_90:  
                 digree = 90;  
                 break;  
             case ExifInterface.ORIENTATION_ROTATE_180:  
                 digree = 180;  
                 break;  
             case ExifInterface.ORIENTATION_ROTATE_270:  
                 digree = 270;  
                 break;  
             default:  
                 digree = 0;  
                 break;  
             }  
         }  
		 
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 400, 400);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		Bitmap mBitmap = BitmapFactory.decodeFile(filePath, options);

		 if (digree != 0) {  
             // 旋转图片  
             Matrix m = new Matrix();  
             m.postRotate(digree);  
             mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),  
            		 mBitmap.getHeight(), m, true);  
         }  
		
		// float width=mBitmap.getWidth();
		// float height=mBitmap.getHeight();
		// float ratio=width/height;
		// mBitmap = Bitmap.createBitmap(mBitmap, (int) (mBitmap.getWidth()/3),
		// (int) (mBitmap.getHeight()-mBitmap.getHeight()*ratio/3)/2, (int)
		// (mBitmap.getWidth()/3),
		// (int) (mBitmap.getHeight() / 3*ratio), matrix, true);

		return mBitmap;
	}

	/**
	 * 鏍规嵁璺緞鑾峰緱绐佺牬骞跺帇缂╄繑鍥瀊itmap鐢ㄤ簬鏄剧ず涓斿帇缂╁ぇ灏忎负50x50
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
		float width = mBitmap.getWidth();
		float height = mBitmap.getHeight();
		float ratio = width / height;
		mBitmap = Bitmap
				.createBitmap(mBitmap, (int) (mBitmap.getWidth() / 3),
						(int) (mBitmap.getHeight() - mBitmap.getHeight()
								* ratio / 3) / 2,
						(int) (mBitmap.getWidth() / 3),
						(int) (mBitmap.getHeight() / 3 * ratio), matrix, true);

		mBitmap = zoomImage(mBitmap, 30, 30);

		// SavePic.saveFoodPic2Example(mBitmap);

		return mBitmap;
	}

	/**
	 * 浠庢墜鏈哄唴瀛樿幏寰楀浘鐗�
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
	 * 鑾峰彇澶村儚
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
	 * 鏍规嵁璺緞鍒犻櫎鍥剧墖
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
	 * 娣诲姞鍒板浘搴�
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
	 * 鑾峰彇淇濆瓨鍥剧墖鐨勭洰褰�
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
	 * 鑾峰彇淇濆瓨 闅愭偅妫�鏌ョ殑鍥剧墖鏂囦欢澶瑰悕绉�
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
	 * 鍥剧墖鐨勭缉鏀炬柟娉�
	 * 
	 * @param bgimage
	 *            锛氭簮鍥剧墖璧勬簮
	 * @param newWidth
	 *            锛氱缉鏀惧悗瀹藉害
	 * @param newHeight
	 *            锛氱缉鏀惧悗楂樺害
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// 鑾峰彇杩欎釜鍥剧墖鐨勫鍜岄珮
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 鍒涘缓鎿嶄綔鍥剧墖鐢ㄧ殑matrix瀵硅薄
		Matrix matrix = new Matrix();
		// 璁＄畻瀹介珮缂╂斁鐜�
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缂╂斁鍥剧墖鍔ㄤ綔
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

}
