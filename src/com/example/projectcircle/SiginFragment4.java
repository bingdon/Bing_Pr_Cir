package com.example.projectcircle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class SiginFragment4 extends Activity {

	public static EditText yewu;
	private static String yewu_neirong;//其他中的业务内容

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_fragment3);
		yewu = (EditText) findViewById(R.id.yewufanwei);
		yewu_neirong = yewu.getText().toString();
	}

}
