package com.wits.dzwillpower.android.content;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wits.dzwillpower.android.R;

/**
 * 
 * @author dzwillpower 2012-12-14下午08:50:16
 */
public class IntentActivity01 extends Activity {
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.intentactivity01);
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent intent = new Intent("android.intent.action.PICK");
				 intent.setDataAndType(Uri.EMPTY, "vnd.android.cursor.dir/video1");
				 startActivity(intent);
			}
		});

	}

}
