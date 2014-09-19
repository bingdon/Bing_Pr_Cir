package com.example.projectcircle;

import com.example.projectcircle.app.MyApplication;

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
		try {
			yewu.setText(MyApplication.getMyPersonBean().getBusiness());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		yewu_neirong = yewu.getText().toString();
	}

}
