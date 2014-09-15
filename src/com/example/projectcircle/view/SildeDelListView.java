package com.example.projectcircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * ListView横向删除
 * 
 * @author Walii
 * @version 2014.3.18
 */
public class SildeDelListView extends ListView {
	private float myLastX = -1;
	private float myLastY = -1;
	private boolean delete = false;
	// 自定义的滑动删除监听
	private SildeDeleteListener sildeDeleteListener;

	public SildeDelListView(Context context) {
		super(context);
	}

	public SildeDelListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 获得第一个点的x坐标
			myLastX = ev.getX(0);
			myLastY = ev.getY(0);
			break;

		case MotionEvent.ACTION_MOVE:
			// 得到最后一个点的坐标
			float deltaX = ev.getX(ev.getPointerCount() - 1) - myLastX;
			float deltaY = Math
					.abs(ev.getY(ev.getPointerCount() - 1) - myLastY);
			// 可以滑动删除的条件：横向滑动大于200，竖直差小于50
			if (deltaX > 200.0 && deltaY < 50) {
				delete = true;
			} else {
				delete = false;
			}
			break;

		case MotionEvent.ACTION_UP:
			if (delete && sildeDeleteListener != null) {
				sildeDeleteListener.filpperDelete(myLastX, myLastY);
			} else {
				sildeDeleteListener.filpperOnclick(myLastX, myLastY);
			}
			reset();
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void reset() {
		delete = false;
		myLastX = -1;
		myLastY = -1;
	}

	public void setFilpperDeleteListener(SildeDeleteListener f) {
		sildeDeleteListener = f;
	}

	// 自定义的接口
	public interface SildeDeleteListener {
		public void filpperDelete(float xPosition, float yPosition);

		public void filpperOnclick(float xPosition, float yPosition);
	}

}
