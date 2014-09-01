package com.wits.dzwillpower.common;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wits.dzwillpower.android.R;

public class OpenPDF extends Activity {
	private Button openpdf;

	public Intent getPdfFileIntent(String path) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(path));
		i.setDataAndType(uri, "application/pdf");
		return i;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.openpdf);
		openpdf = (Button) findViewById(R.id.openpdf);
		openpdf.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.addCategory(Intent.CATEGORY_DEFAULT);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Uri uri = Uri.fromFile(new File("/mnt/sdcard/1.pdf"));
				i.setDataAndType(uri, "application/pdf");
				startActivity(i);

			}
		});

	}

}
