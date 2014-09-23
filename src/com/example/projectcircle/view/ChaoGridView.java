package com.example.projectcircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ChaoGridView extends GridView {
	
	
	public ChaoGridView(Context context){
		super(context);
	}

	public ChaoGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
