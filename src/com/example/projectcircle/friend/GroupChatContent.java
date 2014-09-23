package com.example.projectcircle.friend;

import com.example.projectcircle.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GroupChatContent extends Activity {
	private Button button_back;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_chat_window);	
		back();
	}
	private void back() {
		// TODO Auto-generated method stub
		button_back = (Button) findViewById(R.id.group_chat_now_back);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GroupChatContent.this.setResult(RESULT_OK, getIntent());
				GroupChatContent.this.finish();
			}
		});
	}

}
