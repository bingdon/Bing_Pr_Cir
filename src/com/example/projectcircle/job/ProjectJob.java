package com.example.projectcircle.job;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.WorkAdapter;
import com.example.projectcircle.bean.JobInfo;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 工程作业列表
 */
public class ProjectJob extends Activity implements OnClickListener {
	private static final String TAG = "ProjectJob";
	ListView listview;
	WorkAdapter myAdapter;
	/**
	 * 顶部Button
	 */
	Button back, add_job;

	String type;
	public ArrayList<JobInfo> jobsList;
	Double mylat = HomeActivity.latitude;
	Double mylon = HomeActivity.longitude;
	double distance ;
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_job);
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		initView();
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();	
		JobList(type);
	}

	private void JobList(String type) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "返回:" + response);
				parseJobList(response);
				initList();
			}
		};
		MyHttpClient client = new MyHttpClient();
//		client.JobList(type, res);
		client.JobList_(HomeActivity.getLat(), HomeActivity.getLon(), type, res);
	}

	private void parseJobList(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("jtypes");
			jobsList = new ArrayList<JobInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			Log.i("工程作业列表json", json+"");			
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				JobInfo job = new JobInfo();
				obj = json.getJSONObject(i);				
				job.setTitle(obj.getString("title"));
				job.setDate(obj.getString("date"));	
				job.setId(obj.getString("id"));	
				try {
					job.setLat(obj.getDouble("jlat"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					job.setLon(obj.getDouble("jlon"));
				} catch (Exception e) {
					// TODO: handle exception
				}			
				jobsList.add(job);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.project_job_left);
		add_job = (Button) findViewById(R.id.project_job_right);
		back.setOnClickListener(this);
		add_job.setOnClickListener(this);
	}

	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.project_job_listView);
		listItem.clear();
		listItem = getList();
		myAdapter = new WorkAdapter(listItem, this);
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("arg2", arg2+"");
				Log.i("jid", jobsList.get(arg2).getId()+"");
				Intent intent = new Intent(ProjectJob.this, WorkDetail.class);
				intent.putExtra("jid", jobsList.get(arg2).getId());
				intent.putExtra("distance", listItem.get(arg2).get("distance")+"");
				intent.putExtra("job", "1");//1代表工程作业，Title显示工程作业信息详情界面；2.代表司机需求，Title显示司机需求信息详情
				startActivity(intent);

			}
		});
	}

	private ArrayList<HashMap<String, Object>> getList() {
		// TODO Auto-generated method stub
		Log.i("jobsList",jobsList+"");
		if (jobsList==null) {
			return new ArrayList<>();
		}
		for (int i = 0; i < jobsList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("title", jobsList.get(i).getTitle());		
			map.put("date", jobsList.get(i).getDate());
//			map.put("commercialLat", jobsList.get(i).getLat());
//			map.put("commercialLon", jobsList.get(i).getLon());
			Double commercialLat = jobsList.get(i).getLat();
			Double commercialLon = jobsList.get(i).getLon();
			//计算我和好友之间的距离
			distance = DistentsUtil.GetDistance(commercialLat,commercialLon,
					mylat,mylon);
			//distance = DistentsUtil.changep2(distance);
			map.put("distance", distance);
			listItem.add(map);
		}
		//将距离大于150的去掉，只显示150Km以内的信息
		for(int i = 0,len = listItem.size();i<len; i++){
			if((Double)(listItem.get(i).get("distance")) > 150){
			listItem.remove(i);
			  --len;//减少一个  
		       --i;//多谢deny_guoshou指正，如果不加会出现评论1楼所说的情况。  
			}
		Log.i("listItem工程作业列表", listItem+"");		
		}
		return listItem;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.project_job_left:

			finish();
			break;
		case R.id.project_job_right:
			Intent intent2 = new Intent(ProjectJob.this, AddJob.class);
			intent2.putExtra("type", type);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}
}
