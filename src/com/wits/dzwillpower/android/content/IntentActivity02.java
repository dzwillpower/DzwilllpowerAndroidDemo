package com.wits.dzwillpower.android.content;

import com.wits.dzwillpower.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class IntentActivity02 extends Activity {
	private Button button ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intentactivity01);
		button = (Button) findViewById(R.id.button);
		button.setText("这是第二个activity");
		
	}

}
