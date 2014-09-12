package com.example.projectcircle.home;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;







import com.example.projectcircle.R;
import com.example.projectcircle.personal.ModifyMySelf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class HomeSecActivity extends Activity implements OnWheelChangedListener {


	/**
	 * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
	 */
	private JSONObject mJsonObj;
	/**
	 * 省的WheelView控件
	 */
	private WheelView mProvince;
	/**
	 * 市的WheelView控件
	 */
	private WheelView mCity;
	/**
	 * 区的WheelView控件
	 */
	private WheelView mArea;

	/**
	 * 所有省
	 */
	private String[] mProvinceDatas;
	/**
	 * key - 省 value - 市s
	 */
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区s
	 */
	private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();

	/**
	 * 当前省的名称
	 */
	private String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	private String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	private String mCurrentAreaName ="";

	private static final String TAG=HomeSecActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home_sec);

		initJsonData();

		mProvince = (WheelView) findViewById(R.id.id_province);
		mCity = (WheelView) findViewById(R.id.id_city);
		mArea = (WheelView) findViewById(R.id.id_area);

		initDatas();

		mProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
		// 添加change事件
		mProvince.addChangingListener(this);
		// 添加change事件
		mCity.addChangingListener(this);
		// 添加change事件
		mArea.addChangingListener(this);

		mProvince.setVisibleItems(5);
		mCity.setVisibleItems(5);
		mArea.setVisibleItems(5);
		updateCities();
		updateAreas();

	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas()
	{
		int pCurrent = mCity.getCurrentItem();
		if (null==mCitisDatasMap.get(mCurrentProviceName)) {
			return;
		}
		Log.i(TAG, "更新:"+mCitisDatasMap.get(mCurrentProviceName));
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mAreaDatasMap.get(mCurrentCityName);
	
		if (areas == null)
		{
			areas = new String[] { "" };
		}	
		mCurrentAreaName = areas[0];		
		mArea.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mArea.setCurrentItem(0);		
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities()
	{
		int pCurrent = mProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null)
		{
			cities = new String[] { "" };
		}
		mCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mCity.setCurrentItem(0);		
		updateAreas();
	}

	/**
	 * 解析整个Json对象，完成后释放Json对象的内存
	 */
	private void initDatas()
	{
		try
		{
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			mProvinceDatas = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
				String province = jsonP.getString("p");// 省名字

				mProvinceDatas[i] = province;

				JSONArray jsonCs = null;
				try
				{
					/**
					 * Throws JSONException if the mapping doesn't exist or is
					 * not a JSONArray.
					 */
					jsonCs = jsonP.getJSONArray("c");
				} catch (Exception e1)
				{
					continue;
				}
				String[] mCitiesDatas = new String[jsonCs.length()];
				for (int j = 0; j < jsonCs.length(); j++)
				{
					JSONObject jsonCity = jsonCs.getJSONObject(j);
					String city = jsonCity.getString("n");// 市名字
					mCitiesDatas[j] = city;
					JSONArray jsonAreas = null;
					try
					{
						/**
						 * Throws JSONException if the mapping doesn't exist or
						 * is not a JSONArray.
						 */
						jsonAreas = jsonCity.getJSONArray("a");
					} catch (Exception e)
					{
						continue;
					}

					String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
					for (int k = 0; k < jsonAreas.length(); k++)
					{
						String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
						mAreasDatas[k] = area;
					}
					mAreaDatasMap.put(city, mAreasDatas);
				}

				mCitisDatasMap.put(province, mCitiesDatas);
			}

		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		mJsonObj = null;
	}

	/**
	 * 从assert文件夹中读取省市区的json文件，然后转化为json对象
	 */
	private void initJsonData()
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			// assets文件夹里面的文件都是保持原始的文件格式，需要用AssetManager以字节流的形式读取文件。
		    // 1. 先在Activity里面调用getAssets() 来获取AssetManager引用。
		    //2. 再用AssetManager的open(String fileName, int accessMode) 方法则指定读取的文件以及访问模式就能得到输入流InputStream。
			InputStream is = getAssets().open("city.json");
			int len = -1;
			byte[] buf = new byte[1024];
//			从输入流中读取一定数量的字节，并将其存储在缓冲区数组 b 中。
//			以整数形式返回实际读取的字节数。在输入数据可用、检测到文件末尾或者抛出异常前，此方法一直阻塞。
//			如果 b 的长度为 0，则不读取任何字节并返回 0；否则，尝试读取至少一个字节。
//			如果因为流位于文件末尾而没有可用的字节，则返回值 -1；否则，至少读取一个字节并将其存储在 b 中。将读取的第一个字节存储在元素 b[0] 中，下一个存储在 b[1] 中，依次类推。
//			读取的字节数最多等于 b 的长度。设 k 为实际读取的字节数；这些字节将存储在 b[0] 到 b[k-1] 的元素中，不影响 b[k] 到 b[b.length-1] 的元素。 
			while ((len = is.read(buf)) != -1)
			{
				sb.append(new String(buf, 0, len, "gbk"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * change事件的处理
	 */
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue)
	{
		if (wheel == mProvince)
		{
			updateCities();				  
	
		} else if (wheel == mCity)
		{
			updateAreas();		
		
		} else if (wheel == mArea)
		{
			
		mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
		Log.i("mCurrentAreaName2", mCurrentAreaName);
		}
	}

	public void showChoose(View view)
	{
		
		 Intent intent = new Intent(HomeSecActivity.this, ModifyMySelf.class);  
         String passString = mCurrentProviceName +" "+ mCurrentCityName + " "+mCurrentAreaName;  
         intent.putExtra("home", passString);  
         setResult(RESULT_OK, intent);  
         finish();
		
		Toast.makeText(this, mCurrentProviceName + mCurrentCityName + mCurrentAreaName, 1).show();
	}

	
}
