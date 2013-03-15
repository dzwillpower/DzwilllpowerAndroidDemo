package com.wits.dzwillpower.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.wits.dzwillpower.R;
/**
 * 
 * @author dzwillpower
 * @Time   2013-3-14 下午12:52:34
 */
public class ProgressBarStu extends Activity {
	private ProgressBar progressBar = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				int progressBarMax = progressBar.getMax();
				try {
					while (progressBarMax != progressBar.getProgress()) {

						int stepProgress = progressBarMax / 10;
						int currentprogress = progressBar.getProgress();
						progressBar.setProgress(currentprogress + stepProgress);
						Thread.sleep(1000);
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		thread.start();
	}
}
