package com.wits.dzwillpower.android.util;

import com.wits.dzwillpower.android.utilites.MyLogger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

/**
 * 
 * @author dongzhi
 * @Time 2013-5-27 下午05:14:57
 */
public class DisplayMetricsActivity extends Activity {
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Display display = getWindowManager().getDefaultDisplay(); // Activity#getWindowManager()
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		MyLogger.dLog().e("width: "+width+" height: "+height);
	}

}
