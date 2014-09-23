package com.example.projectcircle.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.util.ShareUtils;

public class CreateGroupSuccess extends Activity {
	TextView share, finish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_group_success);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		share = (TextView) findViewById(R.id.c_group_share);
		finish = (TextView) findViewById(R.id.c_group_finish);
		share.setOnClickListener(listener);
		finish.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.c_group_share:
				// Intent intent = new Intent(FriendPage.this,
				// MaybeFriend.class);
				// startActivity(intent);
				// finish();
				ShareUtils.share2Fre(CreateGroupSuccess.this, CreateGroup.g_name);
				break;
			case R.id.c_group_finish:
				Intent intent1 = new Intent(CreateGroupSuccess.this,
						MyGroup.class);
				startActivity(intent1);
				// 发送广播，让CreateGroup 这个activity finishdiao
				Intent intent = new Intent();
				intent.setAction("cn.abel.action.broadcast");
				// 要发送的内容
				intent.putExtra("author", "Abel");
				// 发送 一个无序广播
				CreateGroupSuccess.this.sendBroadcast(intent);
				finish();
				break;
			default:
				break;
			}
		}
	};
}
