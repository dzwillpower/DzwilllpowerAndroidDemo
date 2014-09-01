package com.wits.dzwillpower.android.adapter;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Stack;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.VerticalViewPager2;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import cn.ipanel.android.net.imgcache.SharedImageFetcher;

import com.wits.dzwillpower.android.R;
import com.wits.dzwillpower.android.customview.FilmView;
import com.wits.dzwillpower.android.customview.FilmView.DataSetListener;
import com.wits.dzwillpower.android.util.FilmViewBean;

public class FilmViewPageAdapter extends PagerAdapter {

	private Context context;
	private List<FilmViewBean> listfilmBeans;
	Stack<SoftReference<View>> views;

	public FilmViewPageAdapter(Context context, List<FilmViewBean> list) {
		this.context = context;
		this.listfilmBeans = list;
		views = new Stack<SoftReference<View>>();
	}

	@Override
	public int getCount() {
		return listfilmBeans.size() % 6 == 0 ? listfilmBeans.size() / 6 : listfilmBeans.size() / 6 + 1;
	}

	@Override
	public float getPageWidth(int position) {
		return 0.5f;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ViewGroup viewGroup = (ViewGroup) object;
		if (viewGroup.getChildCount() == 6) {
			views.push(new SoftReference<View>(viewGroup));
		}
		container.removeView(viewGroup);
	}

	@Override
	public Object instantiateItem(View arg0, int position) {
		LinearLayout llLinearLayout = null;
		int total = listfilmBeans.size();
		int j = total % 6 != 0 && position == total / 6 ? total % 6 : 6;
		if (j == 6) {
			llLinearLayout = (LinearLayout) getViewFromSoftReference();
		}
		if (llLinearLayout != null) {
			for (int i = 0; i < 6; i++) {
				View convertView = llLinearLayout.getChildAt(i);
				final FilmView new_film = (FilmView) convertView.findViewById(R.id.film_element);
				if (new_film != null) {
					new_film.setDataSetListener(new DataSetListener() {

						@Override
						public void onDataSetting(Object o) {
							FilmViewBean filmView = (FilmViewBean) o;
							new_film.setText(filmView.getFilmName());
							SharedImageFetcher.getSharedFetcher(context).loadImage(filmView.getFilmUrl(),
									new_film.getImageView());

						}
					});
					new_film.setData(listfilmBeans.get(position * 6 + i));
					new_film.setTag(position * 6 + i);
					new_film.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							FilmViewBean filmViewBean = (FilmViewBean) new_film.getData();

						}
					});
				}
			}
		} else {
			llLinearLayout = new LinearLayout(context);
			llLinearLayout.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			for (int i = 0; i < j; i++) {
				View convertView = View.inflate(context, R.layout.film_item, null);
				final FilmView new_film = (FilmView) convertView.findViewById(R.id.film_element);
				if (new_film != null) {
					new_film.setDataSetListener(new DataSetListener() {

						@Override
						public void onDataSetting(Object o) {
							FilmViewBean filmView = (FilmViewBean) o;
							new_film.setText(filmView.getFilmName());
							SharedImageFetcher.getSharedFetcher(context).loadImage(filmView.getFilmUrl(),
									new_film.getImageView());

						}
					});
					new_film.setData(listfilmBeans.get(position * 6 + i));
					new_film.setTag(position * 6 + i);
					new_film.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							FilmViewBean filmViewBean = (FilmViewBean) new_film.getData();

						}
					});
				}
				llLinearLayout.addView(convertView);
			}
		}

		try {
			((VerticalViewPager2) arg0).addView(llLinearLayout, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return llLinearLayout;
	}

	private View getViewFromSoftReference() {
		View view = null;
		while (!views.empty()) {
			view = views.pop().get();
			if (view != null)
				return view;
		}
		return null;
	}

}
