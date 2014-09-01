package com.wits.dzwillpower.android.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wits.dzwillpower.android.R;

import android.app.Activity;
import android.os.Bundle;

public class TestReturnXMl extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SimpleDateFormat sdfhms = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		String errorStr;
		errorStr = String.format(getString(R.string.error_requestInfo),
				sdfhms.format(new Date()), "1.11.13.001",
				getString(R.string.error_connectionServer_code),
				getString(R.string.error_connectionServer_description),
				getString(R.string.error_source_lms));
		System.out.println(errorStr);
	}

}
