package com.example.projectcircle.manage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.projectcircle.R;
import com.example.projectcircle.util.ToastUtils;

public class About extends Activity {

	Button back, update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		initBtn();
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.about_left);
		update = (Button) findViewById(R.id.about_update);

		back.setOnClickListener(listener);
		update.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.about_left:
				// Intent intent = new Intent(About.this, ManagePage.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.about_update:
				ToastUtils.showLong(getApplicationContext(),
						getString(R.string.update));
				break;
			default:
				break;
			}
		}
	};
}
