package com.wits.dzwillpower.android.customview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {
	private final static String TAG = "MyViewGroup";

	private final static int VIEW_MARGIN = 2;

	public MyViewGroup(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d(TAG, "widthMeasureSpec = " + widthMeasureSpec + " heightMeasureSpec" + heightMeasureSpec);

		for (int index = 0; index < getChildCount(); index++) {
			final View child = getChildAt(index);
			// measure
			child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		Log.d(TAG, "changed = " + arg0 + " left = " + arg1 + " top = " + arg2 + " right = " + arg3 + " botom = " + arg4);
		final int count = getChildCount();
		int row = 0;// which row lay you view relative to parent
		int lengthX = arg1; // right position of child relative to parent
		int lengthY = arg2; // bottom position of child relative to parent
		for (int i = 0; i < count; i++) {

			final View child = this.getChildAt(i);
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();
			lengthX += width + VIEW_MARGIN;
			lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height + arg2;
			// if it can't drawing on a same line , skip to next line
			if (lengthX > arg3) {
				lengthX = width + VIEW_MARGIN + arg1;
				row++;
				lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height + arg2;

			}

			child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
		}

	}

	/*
	 * 这里有个地方要注意，那就要明白ViewGroup的绘图流程：ViewGroup绘制包括两个步骤：1.measure 2.layout
	 * 
	 * 　　在两个步骤中分别调用回调函数：1.onMeasure() 2.onLayout()
	 * 
	 * 　　1.onMeasure() 在这个函数中，ViewGroup会接受childView的请求的大小，然后通过childView的
	 * measure(newWidthMeasureSpec,
	 * heightMeasureSpec)函数存储到childView中，以便childView的getMeasuredWidth()
	 * andgetMeasuredHeight() 的值可以被后续工作得到。
	 * 
	 * 　　2.onLayout() 在这个函数中，ViewGroup会拿到childView的getMeasuredWidth()
	 * andgetMeasuredHeight()，用来布局所有的childView。
	 * 
	 * 　　3.View.MeasureSpec 与 LayoutParams
	 * 这两个类，是ViewGroup与childView协商大小用的。其中，View.MeasureSpec是ViewGroup用来部署
	 * childView用的， LayoutParams是childView告诉ViewGroup 我需要多大的地方。
	 * 
	 * 　　4.在View
	 * 的onMeasure的最后要调用setMeasuredDimension()这个方法存储View的大小，这个方法决定了当前View的大小。
	 */
}