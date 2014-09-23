package com.example.projectcircle;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.friend.FriendPage;
import com.example.projectcircle.job.JobPage;
import com.example.projectcircle.manage.ManagePage;
import com.example.projectcircle.other.MessagePage;

/**
 * 主界面底部菜单栏
 * 
 * @author Walii
 * @version 2014.3.18
 */
@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	private TabHost tabhost;

	String current;

	ImageView tabIcon0;
	ImageView tabIcon1;
	ImageView tabIcon2;
	ImageView tabIcon3;
	ImageView tabIcon4;
	TextView tabName0;
	TextView tabName1;
	TextView tabName2;
	TextView tabName3;
	TextView tabName4;
	
	
	public static int msgcount=0;
	
	public static TextView msgTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainfragment);
		
		
		tabhost = this.getTabHost();
		// ts1 = tableHost.newTabSpec("tabOne");//实例化一个分页
		// ts1.setIndicator("Tab1");//设置此分页显示的标题
		// ts1.setContent(R.id.btn);//设置此分页的资源id
		// TabSpec homeSpec = tabhost.newTabSpec("home").setIndicator("home")
		// .setContent(new Intent(this, HomeActivity.class));
		// TabSpec friendSpec = tabhost.newTabSpec("friend")
		// .setIndicator("friend")
		// .setContent(new Intent(this, FriendPage.class));
		// TabSpec msgSpec =
		// tabhost.newTabSpec("message").setIndicator("message")
		// .setContent(new Intent(this, MessagePage.class));
		// TabSpec jobSpec = tabhost.newTabSpec("job").setIndicator("job")
		// .setContent(new Intent(this, JobPage.class));
		// TabSpec magSpec =
		// tabhost.newTabSpec("mannage").setIndicator("mannage")
		// .setContent(new Intent(this, ManagePage.class));
		//
		// tabhost.addTab(homeSpec);
		// tabhost.addTab(friendSpec);
		// tabhost.addTab(msgSpec);
		// tabhost.addTab(jobSpec);
		// tabhost.addTab(magSpec);
		//
		// RadioGroup rg = (RadioGroup) this.findViewById(R.id.buttonGroup);
		//
		// rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId) {
		// switch (checkedId) {
		// case R.id.belowbar_1:
		// tabhost.setCurrentTabByTag("home");
		// break;
		// case R.id.belowbar_2:
		// tabhost.setCurrentTabByTag("friend");
		// break;
		// case R.id.belowbar_3:
		// tabhost.setCurrentTabByTag("message");
		// break;
		// case R.id.belowbar_4:
		// tabhost.setCurrentTabByTag("job");
		// break;
		// case R.id.belowbar_5:
		// tabhost.setCurrentTabByTag("mannage");
		// break;
		// default:
		// break;
		// }
		// }
		// });

		initUI();
		
		tabhost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				update(tabhost);
			}
		});
		
		
		tabhost.setCurrentTab(0);
		update(tabhost);

		initFilter();
		
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}

	private void initUI() {

		LayoutInflater inflater = getLayoutInflater();

		View tab0 = inflater.inflate(R.layout.tab_bottom, null);
		tabIcon0 = (ImageView) tab0.findViewById(R.id.tab_imageV);
		tabName0 = (TextView) tab0.findViewById(R.id.tab_textV);
		tabName0.setText(R.string.tabat);
		tabIcon0.setImageResource(R.drawable.tabbar_08);
		TabSpec tabSpec0 = tabhost.newTabSpec("home");
		Intent tabIntent0 = new Intent();
		tabIntent0.setClass(MainActivity.this, HomeActivity.class);

		tabSpec0.setContent(tabIntent0);
		tabSpec0.setIndicator(tab0);
		tabhost.addTab(tabSpec0);

		View tab1 = inflater.inflate(R.layout.tab_bottom, null);
		tabIcon1 = (ImageView) tab1.findViewById(R.id.tab_imageV);
		tabName1 = (TextView) tab1.findViewById(R.id.tab_textV);
		tabName1.setText(R.string.tabbt);
		tabIcon1.setImageResource(R.drawable.tabbar_10);
		TabSpec tabSpec1 = tabhost.newTabSpec("friend");
		Intent tabIntent1 = new Intent();
		tabIntent1.setClass(MainActivity.this, FriendPage.class);

		tabSpec1.setContent(tabIntent1);
		tabSpec1.setIndicator(tab1);
		tabhost.addTab(tabSpec1);

		View tab2 = inflater.inflate(R.layout.tab_bottom, null);
		tabIcon2 = (ImageView) tab2.findViewById(R.id.tab_imageV);
		tabName2 = (TextView) tab2.findViewById(R.id.tab_textV);
		tabName2.setText(R.string.tabct);
		tabIcon2.setImageResource(R.drawable.tabbar_12);
		TabSpec tabSpec2 = tabhost.newTabSpec("message");
		Intent tabIntent2 = new Intent();
		tabIntent2.setClass(MainActivity.this, MessagePage.class);

		tabSpec2.setContent(tabIntent2);
		tabSpec2.setIndicator(tab2);
		tabhost.addTab(tabSpec2);
		
		msgTextView=(TextView)tab2.findViewById(R.id.msg_notice);

		View tab3 = inflater.inflate(R.layout.tab_bottom, null);
		tabIcon3 = (ImageView) tab3.findViewById(R.id.tab_imageV);
		tabName3 = (TextView) tab3.findViewById(R.id.tab_textV);
		tabName3.setText(R.string.tabet);
		tabIcon0.setImageResource(R.drawable.tabbar_03);
		TabSpec tabSpec3 = tabhost.newTabSpec("job");
		Intent tabIntent3 = new Intent();
		tabIntent3.setClass(MainActivity.this, JobPage.class);

		tabSpec3.setContent(tabIntent3);
		tabSpec3.setIndicator(tab3);
		tabhost.addTab(tabSpec3);

		View tab4 = inflater.inflate(R.layout.tab_bottom, null);
		tabIcon4 = (ImageView) tab4.findViewById(R.id.tab_imageV);
		tabName4 = (TextView) tab4.findViewById(R.id.tab_textV);
		tabName4.setText(R.string.tabft);
		tabIcon4.setImageResource(R.drawable.tabbar_05);
		TabSpec tabSpec4 = tabhost.newTabSpec("mannage");
		Intent tabIntent4 = new Intent();
		tabIntent4.setClass(MainActivity.this, ManagePage.class);

		tabSpec4.setContent(tabIntent4);
		tabSpec4.setIndicator(tab4);
		tabhost.addTab(tabSpec4);

	}

	private void update(final TabHost tabHost) {
		AppLog.i("jjj", "设备:"+MyApplication.getMyPersonBean().getEquipment());
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			View v = tabHost.getTabWidget().getChildAt(i);
			ImageView tabIcon = (ImageView) v.findViewById(R.id.tab_imageV);
			TextView tabName = (TextView) v.findViewById(R.id.tab_textV);
			if (tabHost.getCurrentTab() == i) {
				tabName.setTextColor(Color.GREEN);

				switch (i) {
				case 0:

					tabIcon.setImageResource(R.drawable.tabbar_press_08);

					break;

				case 1:

					tabIcon.setImageResource(R.drawable.tabbar_press_10);

					break;

				case 2:

					tabIcon.setImageResource(R.drawable.tabbar_press_12);
					
					msgTextView.setVisibility(View.GONE);
					msgcount=0;
					
					break;

				case 3:

					tabIcon.setImageResource(R.drawable.tabbar_press_03);

					break;

				case 4:

					tabIcon.setImageResource(R.drawable.tabbar_press_05);

					break;

				default:
					break;
				}

			} else {

				tabName.setTextColor(Color.WHITE);

				switch (i) {
				case 0:

					tabIcon.setImageResource(R.drawable.tabbar_08);

					break;

				case 1:

					tabIcon.setImageResource(R.drawable.tabbar_10);

					break;

				case 2:

					tabIcon.setImageResource(R.drawable.tabbar_12);

					break;

				case 3:

					tabIcon.setImageResource(R.drawable.tabbar_03);

					break;

				case 4:

					tabIcon.setImageResource(R.drawable.tabbar_05);

					break;

				default:
					break;
				}

			}

		}
	}

	
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);           
			startActivity(intent);
			return true;
		}

		return super.dispatchKeyEvent(event);
	}

	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_UP) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);           
			startActivity(intent);
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	private void initFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ContantS.ACTION_GET_MSG_FRI);
		registerReceiver(msgReceiver, filter);
	}
	
	
	private BroadcastReceiver msgReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
				msgcount++;
				msgTextView.setVisibility(View.VISIBLE);
				msgTextView.setText(""+msgcount);
		}
	};
	
	
	
	public static void removeNotice(){
		msgTextView.setVisibility(View.GONE);
		msgcount=0;
	}
	
	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// Intent intent = getIntent();
	// current = intent.getStringExtra("current");
	// tabhost.setCurrentTabByTag(current);
	// RadioButton currentbtn = (RadioButton) findViewById(R.id.belowbar_3);
	// currentbtn.isChecked();
	// }
}
