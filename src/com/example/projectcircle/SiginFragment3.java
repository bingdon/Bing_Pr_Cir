package com.example.projectcircle;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.setting.ModifyInfoActivity;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * �̼� ѡ���豸
 */
public class SiginFragment3 extends Activity implements OnCheckedChangeListener {
	/**
	 * ѡ���豸
	 */
	public static CheckBox btn1, btn2, btn3, btn4, btn5;
	public static EditText d_content;
	public static String device1, device2, device3, device4, device5;
	/**
	 * ѡ��ҵ��Χ
	 */
	public static CheckBox btn_1, btn_2, btn_3, btn_4;
	public static String busi1, busi2, busi3, busi4;
	int index = 0;
	int index5 = 0;
	
	EditText companyname_txt, business_intro_txt;
	String companyname, business_intro;
	/**
	 * ����Button
	 */
	Button back, next;

	 String uid = LoginActivity.id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_fragment2);
		initFilter();
		initView();
		initView(0);
		initBtn();
		if (!ModifyInfoActivity.isModify()) {
			findViewById(R.id.commercial).setVisibility(View.GONE);
			
		}else {
			findViewById(R.id.commercial_next).setVisibility(View.GONE);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub

		d_content = (EditText) findViewById(R.id.sigin_car);

		btn1 = (CheckBox) findViewById(R.id.car_btn1);
		btn2 = (CheckBox) findViewById(R.id.car_btn2);
		btn3 = (CheckBox) findViewById(R.id.car_btn3);
		btn4 = (CheckBox) findViewById(R.id.car_btn4);
		btn5 = (CheckBox) findViewById(R.id.car_btn5);

		btn_1 = (CheckBox) findViewById(R.id.busi_btn_1);
		btn_2 = (CheckBox) findViewById(R.id.busi_btn_2);
		btn_3 = (CheckBox) findViewById(R.id.busi_btn_3);
		btn_4 = (CheckBox) findViewById(R.id.busi_btn_4);

		btn1.setOnCheckedChangeListener(this);
		btn2.setOnCheckedChangeListener(this);
		btn3.setOnCheckedChangeListener(this);
		btn4.setOnCheckedChangeListener(this);
		btn5.setOnCheckedChangeListener(this);

		btn_1.setOnCheckedChangeListener(listener);
		btn_2.setOnCheckedChangeListener(listener);
		btn_3.setOnCheckedChangeListener(listener);
		btn_4.setOnCheckedChangeListener(listener);
	}

	/**
	 * ѡ���豸
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.car_btn1:
			if (isChecked) {
				device1 = "�ھ��";
			}
			break;
		case R.id.car_btn2:
			if (isChecked) {
				device2 = "��ж��";
			}
			break;
		case R.id.car_btn3:
			if (isChecked) {
				device3 = "װ�ػ�";
			}
			break;
		case R.id.car_btn4:
			if (isChecked) {
				device4 = "ƽ�峵";
			}
			break;
		case R.id.car_btn5:
			if (isChecked) {
				d_content.setVisibility(View.VISIBLE);
			} else {
				d_content.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			break;
		}
	}

	
	private void initFilter() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish.before.regist.page");	
		filter.addAction(ContantS.ACTION_SEND_SUBMIT);
		registerReceiver(msgReceiver, filter);
	}
	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub		
			String action=intent.getAction();
			if (action.equals(ContantS.ACTION_SEND_SUBMIT)) {
				subimt();
			} else {
			}
				
		}
	};
	
	/**
	 * ѡ��ҵ��Χ
	 */
	private CompoundButton.OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			switch (buttonView.getId()) {
			case R.id.busi_btn_1:
				if (isChecked) {
					busi1 = "����";
				}
				break;
			case R.id.busi_btn_2:
				if (isChecked) {
					busi2 = "�����";
				}
				break;
			case R.id.busi_btn_3:
				if (isChecked) {
					busi3 = "ά��";
				}
				break;
			case R.id.busi_btn_4:
				if (isChecked) {
					busi4 = "����";
				}
				break;

			default:
				break;
			}
		}
	};
	

	private void initView(int m) {
		// TODO Auto-generated method stub
		companyname_txt = (EditText) findViewById(R.id.company_name);
		business_intro_txt = (EditText) findViewById(R.id.business_jianjie);
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.comercial_left);
		next = (Button) findViewById(R.id.commercial_next);
		back.setOnClickListener(listener1);
		next.setOnClickListener(listener1);
	}

	private View.OnClickListener listener1 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.comercial_left:
				// Intent intent = new Intent(CompleteCommercial.this,
				// CompleteInfo.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.commercial_next:
				subimt();
				break;
			default:
				break;
			}
		}
	};

	private void subimt() {
		// TODO Auto-generated method stub
		companyname = companyname_txt.getText().toString();
		business_intro = business_intro_txt.getText().toString();
		// id = SiginActivity.id;
		CompleteCompany(uid, companyname, business_intro);

	}

	private void CompleteCompany(String id, String companyname, String business) {

		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						Toast.makeText(getApplicationContext(), "��ӳɹ���",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), "���ʧ�ܣ�",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error, String response) {
				// TODO Auto-generated method stub
				super.onFailure(error, response);
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.CompleteCompany(id, companyname, business, res);
	}
	
	
}
