package com.wits.dzwillpower.android.test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.wits.dzwillpower.android.R;
import com.wits.dzwillpower.android.utilites.MyLog;

public class MainActivity extends Activity implements OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
	private TextView mTextViewUser;
	private ImageView imageView;
	private Bitmap bitmap = null;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.img);
		new Thread() {
			@Override
			public void run() {
				bitmap = getImageViewInputStream();
				handler.sendEmptyMessage(0);

			}
		}.start();
		getDisplayMetrics();

	}
	@Override
	public void onClick(View v) {
		mTextViewUser.setText(20);
	}

	public static Bitmap getImageViewInputStream() {
		InputStream inputStream = null;
		Bitmap bitmap = null;
		try {
			URL url = new URL("http://192.168.60.111/androidIPMS/pic/app/fbc2748ba08ffa7ccc725be32f7f6af6.jpg"); // 服务器地址
			if (url != null) {

				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setConnectTimeout(3000);// 设置网络连接超时的时间为3秒
				httpURLConnection.setRequestMethod("GET"); // 设置请求方法为GET
				httpURLConnection.setDoInput(true); // 打开输入流
				int responseCode = httpURLConnection.getResponseCode(); // 获取服务器响应值
				if (responseCode == HttpURLConnection.HTTP_OK) { // 正常连接
					inputStream = httpURLConnection.getInputStream(); // 获取输入流
				}
				bitmap = BitmapFactory.decodeStream(inputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			imageView.setImageBitmap(bitmap);
		};
	};
	/**
	 * 获取屏幕尺寸 单位是像素
	 * 测试机为华为c8813
	 */
	@SuppressLint("NewApi")
	public void getDisplayMetrics() {
		Display display = getWindowManager().getDefaultDisplay(); // Activity#getWindowManager()
		Point size = new Point();
		display.getSize(size);
		int width = size.x;//屏幕宽度 (像素 px) 480px
		int height = size.y; //屏幕高度(像素px)854px
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;//// 屏幕密度（0.75 / 1.0 / 1.5） 1.5
		float densityDpi = dm.densityDpi;// 屏幕密度DPI（120 / 160 / 240）240
		int widthPixels= dm.widthPixels;
		int heightPixels= dm.heightPixels;
		MyLog.e(TAG,"width: " + width + " height: " + height+" density: "+density+" densityDpi: "+densityDpi);
		MyLog.e(TAG,"widthPixels: "+widthPixels+" heightPixels: "+heightPixels);

	}
}