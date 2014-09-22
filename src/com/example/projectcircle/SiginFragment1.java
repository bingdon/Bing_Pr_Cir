package com.example.projectcircle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.projectcircle.adpter.MasterDviAdapter;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.complete.CompleteMaster;
import com.example.projectcircle.complete.FootActivity1;
import com.example.projectcircle.complete.FootActivity2;
import com.example.projectcircle.complete.FootActivity3;
import com.example.projectcircle.complete.FootActivity4;
import com.example.projectcircle.complete.FootActivity5;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.setting.ModifyInfoActivity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.EditText;

/**
 * ���� ѡ���豸
 */
public class SiginFragment1 extends TabActivity implements
		OnCheckedChangeListener, OnClickListener {
	/**
	 * ѡ���豸
	 */

	public static CheckBox btn1, btn2, btn3, btn4, btn5;
	public static EditText d_content;
	public static EditText f_content;
	public static String equipment;
	public static String device1, device2, device3, device4, device5;
	private TabHost tabhost;
	Button d_radio1, d_radio2, d_radio3, d_radio4, d_radio5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_fragment);
		initDevice();

		if (!ModifyInfoActivity.isModify()) {
			findViewById(R.id.master).setVisibility(View.GONE);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	/**
	 * ѡ���豸 ��ʼ�� ����ʵ�� ��ѡ
	 */
	private void initDevice() {
		// TODO Auto-generated method stub
		d_content = (EditText) findViewById(R.id.sigin_car);
		f_content = (EditText) findViewById(R.id.sigin_car_);

		btn1 = (CheckBox) findViewById(R.id.btn1);
		btn2 = (CheckBox) findViewById(R.id.btn2);
		btn3 = (CheckBox) findViewById(R.id.btn3);
		btn4 = (CheckBox) findViewById(R.id.btn4);
		btn5 = (CheckBox) findViewById(R.id.btn5);

		btn1.setOnCheckedChangeListener(this);
		btn2.setOnCheckedChangeListener(this);
		btn3.setOnCheckedChangeListener(this);
		btn4.setOnCheckedChangeListener(this);
		btn5.setOnCheckedChangeListener(this);

		findViewById(R.id.master_next).setVisibility(View.GONE);
		CompleteMaster.listview = (ListView) findViewById(R.id.master_listview);
		CompleteMaster.listview.setDividerHeight(0);// ��listView�ָ���ȥ��
		CompleteMaster.myAdapter = new MasterDviAdapter(
				getApplicationContext(),
				new ArrayList<HashMap<String, Object>>());
		CompleteMaster.listview.setAdapter(CompleteMaster.myAdapter);

		initTab();
		inittab0();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.btn1:
			if (isChecked) {
				device1 = "�ھ��";
			}
			break;
		case R.id.btn2:
			if (isChecked) {
				device2 = "��ж��";
			}
			break;
		case R.id.btn3:
			if (isChecked) {
				device3 = "װ�ػ�";
			}
			break;
		case R.id.btn4:
			if (isChecked) {
				device4 = "ƽ�峵";
			}
			break;
		case R.id.btn5:
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

	private void initTab() {
		// TODO Auto-generated method stub
		d_content = (EditText) findViewById(R.id.sigin_car);
		tabhost = this.getTabHost();

		TabSpec tabSpec1 = tabhost.newTabSpec("tab1").setIndicator("tab1")
				.setContent(new Intent(this, FootActivity1.class));
		TabSpec tabSpec2 = tabhost.newTabSpec("tab2").setIndicator("tab2")
				.setContent(new Intent(this, FootActivity2.class));
		TabSpec tabSpec3 = tabhost.newTabSpec("tab3").setIndicator("tab3")
				.setContent(new Intent(this, FootActivity3.class));
		TabSpec tabSpec4 = tabhost.newTabSpec("tab4").setIndicator("tab4")
				.setContent(new Intent(this, FootActivity4.class));
		TabSpec tabSpec5 = tabhost.newTabSpec("tab5").setIndicator("tab5")
				.setContent(new Intent(this, FootActivity5.class));
		tabhost.addTab(tabSpec1);
		tabhost.addTab(tabSpec2);
		tabhost.addTab(tabSpec3);
		tabhost.addTab(tabSpec4);
		tabhost.addTab(tabSpec5);

		d_radio1 = (Button) findViewById(R.id.car_radiobtn1);
		d_radio2 = (Button) findViewById(R.id.car_radiobtn2);
		d_radio3 = (Button) findViewById(R.id.car_radiobtn3);
		d_radio4 = (Button) findViewById(R.id.car_radiobtn4);
		d_radio5 = (Button) findViewById(R.id.car_radiobtn5);

		d_radio1.setOnClickListener(this);
		d_radio2.setOnClickListener(this);
		d_radio3.setOnClickListener(this);
		d_radio4.setOnClickListener(this);
		d_radio5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.car_radiobtn1) {
			d_radio1.setBackgroundResource(R.drawable.onechoice_press);
			d_radio2.setBackgroundResource(R.drawable.onechoice);
			d_radio3.setBackgroundResource(R.drawable.onechoice);
			d_radio4.setBackgroundResource(R.drawable.onechoice);
			d_radio5.setBackgroundResource(R.drawable.onechoice);
			d_content.setVisibility(View.INVISIBLE);
			tabhost.setCurrentTabByTag("tab1");
			CompleteMaster.type = "�ھ��";
			// next.setVisibility(View.VISIBLE);
		}
		if (v.getId() == R.id.car_radiobtn2) {
			d_radio1.setBackgroundResource(R.drawable.onechoice);
			d_radio2.setBackgroundResource(R.drawable.onechoice_press);
			d_radio3.setBackgroundResource(R.drawable.onechoice);
			d_radio4.setBackgroundResource(R.drawable.onechoice);
			d_radio5.setBackgroundResource(R.drawable.onechoice);
			d_content.setVisibility(View.INVISIBLE);
			tabhost.setCurrentTabByTag("tab2");
			// next.setVisibility(View.VISIBLE);
			CompleteMaster.type = "��ж��";
		}
		if (v.getId() == R.id.car_radiobtn3) {
			d_radio1.setBackgroundResource(R.drawable.onechoice);
			d_radio2.setBackgroundResource(R.drawable.onechoice);
			d_radio3.setBackgroundResource(R.drawable.onechoice_press);
			d_radio4.setBackgroundResource(R.drawable.onechoice);
			d_radio5.setBackgroundResource(R.drawable.onechoice);
			d_content.setVisibility(View.INVISIBLE);
			tabhost.setCurrentTabByTag("tab3");
			CompleteMaster.type = "װ�ػ�";
			// next.setVisibility(View.INVISIBLE);
		}
		if (v.getId() == R.id.car_radiobtn4) {
			d_radio1.setBackgroundResource(R.drawable.onechoice);
			d_radio2.setBackgroundResource(R.drawable.onechoice);
			d_radio3.setBackgroundResource(R.drawable.onechoice);
			d_radio4.setBackgroundResource(R.drawable.onechoice_press);
			d_radio5.setBackgroundResource(R.drawable.onechoice);
			d_content.setVisibility(View.INVISIBLE);
			tabhost.setCurrentTabByTag("tab4");
			// CompleteMaster.next.setVisibility(View.VISIBLE);
			CompleteMaster.type = "ƽ�峵";
		}
		if (v.getId() == R.id.car_radiobtn5) {
			d_radio1.setBackgroundResource(R.drawable.onechoice);
			d_radio2.setBackgroundResource(R.drawable.onechoice);
			d_radio3.setBackgroundResource(R.drawable.onechoice);
			d_radio4.setBackgroundResource(R.drawable.onechoice);
			d_radio5.setBackgroundResource(R.drawable.onechoice_press);
			d_content.setVisibility(View.VISIBLE);
			tabhost.setCurrentTabByTag("tab5");
			// next.setVisibility(View.VISIBLE);
			CompleteMaster.type = "����";
			
		}
	}

	private void inittab0() {
		if (MyApplication.getMyPersonBean()==null) {
			return;
		}
		if (MyApplication.getMyPersonBean().getType().equals("����")) {
			if (null != MyApplication.getMyPersonBean().getEquipment()) {
				String devicestrString = MyApplication.getMyPersonBean()
						.getEquipment();
				int length = devicestrString.indexOf(",");
				AppLog.i("�豸", "����1:"+devicestrString);
				AppLog.i("�豸", "����:"+length);
				if (length < 0) {
					initDevice(devicestrString);
				} else {
					String device[] = devicestrString.split(",");
					AppLog.i("�豸", "����2:"+device.length);
					for (int i = 0; i < device.length; i++) {
						initDevice(device[i]);
						AppLog.i("�豸", "����3:"+device[i]);
					}
				}

			}
		}
	}

	private void initDevice(String str) {
		switch (str) {
		case "�ھ��":
			btn1.setChecked(true);
			break;
		case "��ж��":
			btn2.setChecked(true);
			break;

		case "װ�ػ�":
			btn3.setChecked(true);
			break;

		case "ƽ�峵":
			btn4.setChecked(true);
			break;

		case "����":
			btn5.setChecked(true);
			break;

		default:
			btn5.setChecked(true);
			d_content.setText(""+str);
			break;
		}
	}

}
