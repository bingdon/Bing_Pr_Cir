package com.example.projectcircle.friend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.job.postStatus;
import com.example.projectcircle.personal.ApplyFriend;
import com.example.projectcircle.personal.PersonalPage;


public class DetailInformation extends Activity {
	private ImageView img;
	private TextView name,equipment,address,type,info;
	private ImageView add_button;
	private TextView Id;
	private Button button_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_information);		
        detail_info();
        apply_friend();
        back();
		}


	private void detail_info() {
		// TODO Auto-generated method stub
		Intent intent =getIntent();	
		String id = intent.getStringExtra("id");
		String addr = intent.getStringExtra("address");
		String equ = intent.getStringExtra("equipment");
		String uname = intent.getStringExtra("username");		
		//String headimg = intent.getStringExtra("headimage");
		String ty = intent.getStringExtra("type");
		String inf = intent.getStringExtra("info");
		Id = (TextView)findViewById(R.id.my_project_number_search);
	address = (TextView)findViewById(R.id.my_home_city_search);
	equipment = (TextView)findViewById(R.id.my_home_device_search);
	name = (TextView)findViewById(R.id.my_name_search);
	img	= (ImageView)findViewById(R.id.my_home_page_headimg_search);
	type = (TextView)findViewById(R.id.my_career_search);
	info =(TextView)findViewById(R.id.my_home_introduce_search);
	Id.setText(id);
    address.setText(addr);
    equipment.setText(equ);
    name.setText(uname);
    type.setText(ty);
    info.setText(inf);
  
	}

	private void apply_friend() {
		// TODO Auto-generated method stub
		add_button = (ImageView)findViewById(R.id.img_search);
		Intent intent =getIntent();	
		final String uid = intent.getStringExtra("id");
		add_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(DetailInformation.this,
						ApplyFriend.class);
				intent2.putExtra("id", uid);
				startActivity(intent2);
			}
		});
	}
   //·µ»Ø
	private void back() {
		// TODO Auto-generated method stub
		button_back = (Button) findViewById(R.id.my_home_page_back);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DetailInformation.this.setResult(RESULT_OK, getIntent());
				DetailInformation.this.finish();
			}
		});
	}
}
