package com.wits.dzwillpower.ui;

import com.wits.dzwillpower.android.R;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class GetTranslationXDemo extends Activity implements OnClickListener {
	private static final String TAG = "MainActivity";
	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gettranslationxdemo);
		iv = (ImageView) findViewById(R.id.iv);
		iv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Log.d(TAG, "translationX:" + iv.getTranslationX() + ",x:" + iv.getX());
		ObjectAnimator.ofFloat(iv, "translationX", 100f).setDuration(5000).start();
	}

}
