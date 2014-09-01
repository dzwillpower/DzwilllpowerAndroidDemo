package com.wits.dzwillpower.android.os;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wits.dzwillpower.android.R;

public class AsyncTaskExample extends Activity {
	private ImageView mImageView;
	private Button mButton;
	private ProgressDialog progressDialog;
	private TextView tvprogress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asynctaskexample_main);

		mImageView = (ImageView) findViewById(R.id.imageView);
		mButton = (Button) findViewById(R.id.button);
		progressDialog = new ProgressDialog(this);
		tvprogress = (TextView) findViewById(R.id.tvProgress);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GetCSDNLogoTask task = new GetCSDNLogoTask();
				 task.execute("http://img4.cache.netease.com/photo/0001/2013-04-05/8RN5DUC119BR0001.jpg");
//				task.execute("http://upload.wikimedia.org/wikipedia/commons/c/c0/Djupfjorden%2C_2010_09.jpg");
			}
		});
	}

	// 第一个参数定义的是doInBackground这个方法所接受参数的类型
	// 第二个参数定义的是onProgressUpdate所接受参数的类型
	// 第三个参数定义的是doInBackground这个函数返回值类型和onPostExecute这个方法所接受参数的类型
	class GetCSDNLogoTask extends AsyncTask<String, Integer, Bitmap> {// 继承AsyncTask

		@Override
		protected Bitmap doInBackground(String... params) {// 处理后台执行的任务，在后台线程执行
			HttpClient hc = new DefaultHttpClient();
			HttpGet hg = new HttpGet(params[0]);//
			URL url = null;
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			final Bitmap bitmap;
			try {
				/*
				 * HttpResponse hr = hc.execute(hg); bm =
				 * BitmapFactory.decodeStream(hr.getEntity().getContent());
				 */
				url = new URL(params[0]);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setDoInput(true);
				httpURLConnection.connect();
				// 获取文件的大小
				int contentLength = httpURLConnection.getContentLength();
				InputStream inputStream = httpURLConnection.getInputStream();
				OutputStream output = new FileOutputStream("/sdcard/picture.jpg");
				int currentSize = 0;
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = inputStream.read(buffer)) != -1) {
					byteArrayOutputStream.write(buffer, 0, len);
					output.write(buffer, 0, len);
					byteArrayOutputStream.flush();
					currentSize += len;
					publishProgress(contentLength, currentSize);
				}
				inputStream.close();
				httpURLConnection.disconnect();
				byte imageBytes[] = byteArrayOutputStream.toByteArray();
				bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			// mImageView.setImageBitmap(result); 不能在后台线程操作ui
			return bitmap;
		}

		protected void onProgressUpdate(Integer... progress) {// 在调用publishProgress之后被调用，在ui线程执行
			progressDialog.setMax(progress[0]);
			tvprogress.setText("已下载： " + (progress[1] * 100) / progress[0] + "%");
			progressDialog.setProgress(progress[1]);
		}

		protected void onPostExecute(Bitmap result) {// 后台任务执行完之后被调用，在ui线程执行
			if (result != null) {
				Toast.makeText(AsyncTaskExample.this, "成功获取图片", Toast.LENGTH_LONG).show();
				mImageView.setImageBitmap(result);
				tvprogress.setText("图片下载成功");
				progressDialog.dismiss();
			} else {
				Toast.makeText(AsyncTaskExample.this, "获取图片失败", Toast.LENGTH_LONG).show();
			}
		}

		protected void onPreExecute() {// 在
										// doInBackground(Params...)之前被调用，在ui线程执行
			mImageView.setImageBitmap(null);
			progressDialog.setTitle("图片下载");
			progressDialog.setMessage("正在下载图片 喝杯茶……");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setCancelable(false);
			progressDialog.show();
			tvprogress.setText("开始下载图片");
		}

		protected void onCancelled() {// 在ui线程执行
		}

	}

}