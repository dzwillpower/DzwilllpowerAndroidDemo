package com.wits.dzwillpower.android.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.VerticalViewPager2;
import android.support.v4.view.VerticalViewPager2.OnPageChangeListener;
import android.support.v4.view.VerticalViewPager2.OnPageScrollListener;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import android.view.Window;
import cn.ipanel.android.widget.ViewFrameIndicator;

import com.wits.dzwillpower.android.R;
import com.wits.dzwillpower.android.adapter.FilmViewPageAdapter;
import com.wits.dzwillpower.android.customview.FilmView;
import com.wits.dzwillpower.android.util.FilmViewBean;
import com.wits.dzwillpower.android.utilites.MyLogger;

/**
 * 
 * @author dzwillpower
 * @time 2013年10月18日 下午2:26:36
 */
public class FilmViewActivity extends Activity {
	private List<FilmViewBean> listFilmBeans;
	private VerticalViewPager2 verticalViewPager2;
	ViewFrameIndicator indicator;
	private static final int MOVEFOCUS = 1;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MOVEFOCUS:
				View view = FilmViewActivity.this.getCurrentFocus();
				if (view instanceof FilmView) {
					final FilmView filmView = (FilmView) view;
//					indicator.moveFrameTo(filmView.getImageView(), true, false, 0, 200, 2, 0);
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.filmviewactivity_main);
		verticalViewPager2 = (VerticalViewPager2) findViewById(R.id.verticalviewpager);
		indicator = new ViewFrameIndicator(this);
		indicator.setFrameResouce(R.drawable.selector);
		listFilmBeans = makeFilmListData();
		verticalViewPager2.setAdapter(new FilmViewPageAdapter(this, listFilmBeans));
		verticalViewPager2.setOffscreenPageLimit(2);
		verticalViewPager2.setOnPageScrollListener(new OnPageScrollListener() {

			@Override
			public void onPageScroll(int dy, int time) {
//				indicator.moveFrameTo(FilmViewActivity.this.getCurrentFocus(), true, false, dy, (int) (time * 0.6f),
//						10, (int) (234));
			}
		});
		verticalViewPager2.getViewTreeObserver().addOnGlobalFocusChangeListener(new OnGlobalFocusChangeListener() {

			@Override
			public void onGlobalFocusChanged(View oldFocus, View newFocus) {

				if (oldFocus != null) {
					MyLogger.dLog().e("old :" + oldFocus.getClass().getName());
					if (oldFocus instanceof FilmView) {
						FilmView view = (FilmView) oldFocus;
						view.smalll(true);
					}
				}
				if (newFocus != null) {
					MyLogger.dLog().e("new :" + newFocus.getClass().getName());
					if (newFocus instanceof FilmView) {
						MyLogger.dLog().e(newFocus.getTag() + "");
						final FilmView view = (FilmView) newFocus;
						view.large(true);
//						indicator.moveFrameTo(view.getImageView(), true, false, 0, 200, 2, 0);

					}
				}
			}
		});
		verticalViewPager2.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {
//				if (state == VerticalViewPager2.SCROLL_STATE_IDLE) {
//					handler.removeMessages(MOVEFOCUS);
//					handler.sendEmptyMessageDelayed(MOVEFOCUS, 50);
//				}
			}
		});

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
			}
		}, 100);
	}

	public List<FilmViewBean> makeFilmListData() {
		List<FilmViewBean> listFilmViewBeans = new ArrayList<FilmViewBean>();
		for (int i = 0; i < 100; i++) {
			FilmViewBean filmViewBean = new FilmViewBean();
			filmViewBean.setFilmName("当幸福来敲门" + i);
			switch (i % 6) {
			case 0:
				filmViewBean.setFilmUrl("http://img31.mtime.cn/pi/2013/10/17/231514.79473657.jpg");
				break;
			case 1:
				filmViewBean.setFilmUrl("http://img31.mtime.cn/pi/2013/10/17/232816.19609407.jpg");
				break;
			case 2:
				filmViewBean.setFilmUrl("http://img31.mtime.cn/pi/2013/10/17/231947.33184098.jpg");
				break;
			case 3:
				filmViewBean.setFilmUrl("http://img31.mtime.cn/pi/2013/10/17/220034.89254798.jpg");
				break;
			case 4:
				filmViewBean.setFilmUrl("http://img31.mtime.cn/pi/2013/10/17/154259.67204343.jpg");
				break;
			case 5:
				filmViewBean.setFilmUrl("http://img31.mtime.cn/pi/2013/10/17/150503.20342280.jpg");
				break;
			default:
				break;
			}
			listFilmViewBeans.add(filmViewBean);
		}
		return listFilmViewBeans;
	}
}
