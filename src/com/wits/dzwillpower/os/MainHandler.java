package com.wits.dzwillpower.os;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wits.dzwillpower.R;
/**
 * 
 * @author wh1107007
 * @Time   2013-1-22下午4:54:43
 */
public class MainHandler extends Activity {
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainhandler);
		Log.e("MainHandler","fdksaljdljasljflasjdfljaskldjflksajfdlk");
		handler.sendMessageDelayed(Message.obtain(), 6000);
//		finish();
	}

}
