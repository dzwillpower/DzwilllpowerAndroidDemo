package com.wits.dzwillpower.android.view;

import com.wits.dzwillpower.android.R;
import com.wits.dzwillpower.android.utilites.MyLogger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;

/**
 * @author dzwillpower
 * @2013年8月14日下午8:53:31
 */
public class OnFocusChangeExample extends Activity implements OnFocusChangeListener {
	private Button button1, button2, button3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onfocuschangeexample);
		button1 = (Button) findViewById(R.id.btn_first);
		button2 = (Button) findViewById(R.id.btn_second);
		button3 = (Button) findViewById(R.id.btn_three);
		button1.setOnFocusChangeListener(this);
		button2.setOnFocusChangeListener(this);
		button3.setOnFocusChangeListener(this);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		MyLogger.dLog().e("enter onFocusChange");
		switch (v.getId()) {
		case R.id.btn_first:
			if(hasFocus)
				MyLogger.dLog().i("button1 foucus change");
			break;
		case R.id.btn_second:
			if(hasFocus)
				MyLogger.dLog().i("button2 focus change");
			break;
		case R.id.btn_three:
			if (hasFocus) 
				MyLogger.dLog().i("button3 focus change");
			break;

		default:
			break;
		}
	}

}
