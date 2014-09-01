package com.wits.dzwillpower.android.home;

//创建Activity
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;

public class TestHandler extends Activity {
	protected static final int GUIUPDATEIDENTIFIER = 0x101;

	Thread myRefreshThread = null;
	BounceView myBounceView = null;
	// 1.定义一个Handler(一般更新View)
	Handler myHandler = new Handler() {
		// 2.重写消息处理函数
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 判断发送的消息
			case TestHandler.GUIUPDATEIDENTIFIER:
				// 更新View
				myBounceView.invalidate();
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		this.myBounceView = new BounceView(this);
		this.setContentView(this.myBounceView);
		new Thread(new myThread()).start();
	}

	class myThread implements Runnable {
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				// 3.发送消息
				Message message = new Message();
				// 发送消息与处理函数里一致
				message.what = TestHandler.GUIUPDATEIDENTIFIER;
				// 内部类调用外部类的变量
				TestHandler.this.myHandler.sendMessage(message);

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}