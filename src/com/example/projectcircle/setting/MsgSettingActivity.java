package com.example.projectcircle.setting;

import com.example.projectcircle.R;
import com.example.projectcircle.view.WiperSwitch;
import com.example.projectcircle.view.WiperSwitch.OnChangedListener;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MsgSettingActivity extends Activity {
	private static final String TAG = MsgSettingActivity.class.getSimpleName();

	private static final String ISMSG="ismsg";
	
	private static final String IS_SOUND="issound";
	
	private static final String IS_VIR="isvir";
	
	private WiperSwitch msgwiperSwitch, soundSwitch, virSwitch;
	
	private Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
	}

	private void initView() {
		msgwiperSwitch = (WiperSwitch) findViewById(R.id.wiperSwitch1);
		soundSwitch = (WiperSwitch) findViewById(R.id.wiperSwitch2);
		virSwitch = (WiperSwitch) findViewById(R.id.wiperSwitch3);
		msgwiperSwitch.SetOnChangedListener(new OnChangedListener() {

			@Override
			public void OnChanged(boolean checkState) {
				// TODO Auto-generated method stub
				if (!checkState) {
					editor.putBoolean(ISMSG, checkState);
					editor.putBoolean(IS_SOUND, checkState);
					editor.putBoolean(IS_VIR, checkState);
					soundSwitch.setNowChoose(checkState);
					virSwitch.setNowChoose(checkState);
					soundSwitch.setEnabled(false);
					virSwitch.setEnabled(false);
				}else {
					editor.putBoolean(ISMSG, checkState);
					soundSwitch.setEnabled(true);
					virSwitch.setEnabled(true);
				}
				
				editor.commit();
			}
		});

		soundSwitch.SetOnChangedListener(new OnChangedListener() {

			@Override
			public void OnChanged(boolean checkState) {
				// TODO Auto-generated method stub
				editor.putBoolean(IS_SOUND, checkState);
				editor.commit();
			}
		});

		virSwitch.SetOnChangedListener(new OnChangedListener() {

			@Override
			public void OnChanged(boolean checkState) {
				// TODO Auto-generated method stub
				editor.putBoolean(IS_VIR, checkState);
				editor.commit();
			}
		});

		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		initPerference();
	}

	private void initPerference() {
		SharedPreferences preferences=getSharedPreferences(TAG, MODE_PRIVATE);
		boolean ismsg=preferences.getBoolean(ISMSG, true);
		boolean issound=preferences.getBoolean(IS_SOUND, true);
		boolean isvir=preferences.getBoolean(IS_VIR, false);
		msgwiperSwitch.setNowChoose(ismsg);
		soundSwitch.setNowChoose(issound);
		virSwitch.setNowChoose(isvir);
		editor=preferences.edit();
	}

	
	public static boolean  getIsmsg(Context context) {
		SharedPreferences preferences=context.getSharedPreferences(TAG, MODE_PRIVATE);
		boolean ismsg=preferences.getBoolean(ISMSG, true);
		return ismsg;
	}
	
	
	public static boolean getIssound(Context context){
		SharedPreferences preferences=context.getSharedPreferences(TAG, MODE_PRIVATE);
		boolean issound=preferences.getBoolean(IS_SOUND, true);
		return issound;
	}
	
	public static boolean getIsvir(Context context){
		SharedPreferences preferences=context.getSharedPreferences(TAG, MODE_PRIVATE);
		boolean isvir=preferences.getBoolean(IS_VIR, false);
		return isvir;
	}
	
}
