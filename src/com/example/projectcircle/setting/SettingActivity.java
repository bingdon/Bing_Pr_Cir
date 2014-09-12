package com.example.projectcircle.setting;

import com.example.projectcircle.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingActivity extends PreferenceActivity {

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);
	}
	
}
