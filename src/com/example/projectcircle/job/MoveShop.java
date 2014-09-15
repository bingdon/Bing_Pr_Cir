package com.example.projectcircle.job;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import com.example.projectcircle.R;


/**
 * 移动商城
 */
@SuppressWarnings("deprecation")
public class MoveShop extends TabActivity {
	private TabHost tabhost;
	/**
	 * 其它Button
	 */
	Button back;
	TextView search;
	private  String equ = "全部";//设备

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_info);		
		initTab();
		initBtn();
	}

	private void initTab() {
		// TODO Auto-generated method stub
		tabhost = this.getTabHost();
		TabSpec tabSpec1 = tabhost.newTabSpec("tab1").setIndicator("tab1")
				.setContent(new Intent(this, TabActivity1.class));
		TabSpec tabSpec2 = tabhost.newTabSpec("tab2").setIndicator("tab2")
				.setContent(new Intent(this, TabActivity2.class));
		TabSpec tabSpec3 = tabhost.newTabSpec("tab3").setIndicator("tab3")
				.setContent(new Intent(this, TabActivity3.class));
		TabSpec tabSpec4 = tabhost.newTabSpec("tab4").setIndicator("tab4")
				.setContent(new Intent(this, TabActivity4.class));		
		TabSpec tabSpec5 = tabhost.newTabSpec("tab5").setIndicator("tab5")
				.setContent(new Intent(this, TabActivity5.class));

		tabhost.addTab(tabSpec1);
		tabhost.addTab(tabSpec2);
		tabhost.addTab(tabSpec3);
		tabhost.addTab(tabSpec4);
		tabhost.addTab(tabSpec5);		
		RadioGroup rg = (RadioGroup) this.findViewById(R.id.job_radiogroup);		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
//
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.job_belowbar_1:								
					tabhost.setCurrentTabByTag("tab1");
					equ ="全部";
					break;
				case R.id.job_belowbar_2:					
					tabhost.setCurrentTabByTag("tab2");
					equ ="挖掘机";
					break;
				case R.id.job_belowbar_3:					
					tabhost.setCurrentTabByTag("tab3");
					equ ="自卸车";
					break;
				case R.id.job_belowbar_4:				
					tabhost.setCurrentTabByTag("tab4");
					equ ="装载机";
					break;
				case R.id.job_belowbar_5:				
					tabhost.setCurrentTab(4);
					equ ="其它";
					break;
					
				default:
					break;
				}
			}
		});
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.job_info_left);
		search = (TextView) findViewById(R.id.job_info_right);
		back.setOnClickListener(listener);
		search.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.job_info_left:
				finish();
				break;
			case R.id.job_info_right:		
				Log.i("Moveshop 中equ",equ+"");
				if(equ.equals("全部")){
					Intent intent = new Intent(); 
					intent.setAction("TabActivity1"); 
					intent.putExtra("msg", equ); 
					MoveShop.this.sendBroadcast(intent);
					}
				else if(equ.equals("挖掘机")){
					Intent intent = new Intent(); 
					intent.setAction("TabActivity2"); 
					intent.putExtra("msg", equ); 
					MoveShop.this.sendBroadcast(intent);
					}
				else if(equ.equals("自卸车")){
					Intent intent = new Intent(); 
					intent.setAction("TabActivity3"); 
					intent.putExtra("msg", equ); 
					MoveShop.this.sendBroadcast(intent);
					}
				else if(equ.equals("装载机")){
					Intent intent = new Intent(); 
					intent.setAction("TabActivity4"); 
					intent.putExtra("msg", equ); 
					MoveShop.this.sendBroadcast(intent);
					}
				else if(equ.equals("其它")){
					Intent intent = new Intent(); 
					intent.setAction("TabActivity5"); 
					intent.putExtra("msg", equ); 
					MoveShop.this.sendBroadcast(intent);
					}
				break;
			default:
				break;
			}
		}


	};

}
