package com.wits.dzwillpower.widget;

import com.wits.dzwillpower.R;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 
 * @author wh1107007 董治
 * 2012/2/20
 * 这个是监听ListView.onScrollStateChanged 方法看是否到了底部 
 * 在模拟器上 如果使用鼠标的滚轮来滚动时执行 onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)  方法，
 * 不执行 onScrollStateChanged(AbsListView view, int scrollState)  方法。
 * 只有触摸往下滑动时，才执行onScrollStateChanged(AbsListView view, int scrollState) 方法。有待考证。
 * 
 * 具体可以参考 package com.example.android.apis.view （ApiDemos里面的List13）
 */
public class Ex_ListViewOnScrollListener extends ListActivity implements
		OnScrollListener {
	private TextView mStatus; // 显示滚屏状态
	private boolean mBusy = false; // 标识是否存在滚屏操作

	/**
	 * 自定义Adapter，实现ListView中view的显示
	 * 
	 */
	private class SlowAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public SlowAdapter(Context context) {
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		/**
		 * 列表中元素个数取决于数据的个数
		 */
		public int getCount() {
			return mStrings.length;
		}

		/**
		 * 我们的模拟数据是从数组中获取的，因此这里直接返回索引值就可以获取相应的数据了
		 */
		public Object getItem(int position) {
			return position;
		}

		/**
		 * 使用数组的索引作为唯一的id
		 */
		public long getItemId(int position) {
			return position;
		}

		/**
		 * 获取List中每一行的view
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView text;

			// 给text赋值
			if (null == convertView) {
				text = (TextView) mInflater.inflate(
						android.R.layout.simple_list_item_1, parent, false);
			} else {
				text = (TextView) convertView;
			}

			if (!mBusy) {
				// 当前不处于加载数据的忙碌时期（没有滚屏），则显示数据
				text.setText(mStrings[position]);
				// 这里约定将tag设置为null说明这个view已经有了正确的数据
				text.setTag(null);
			} else {
				// 当前处于滚屏阶段，不加载数据，直接显示数据加载中提示
				text.setText("Loading...");
				// tag非空说明这个view仍然需要进行数据加载并显示
				text.setTag(this);
			}

			return text;
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ex_listviewonscrolllistener_main);

		mStatus = (TextView) findViewById(R.id.status);
		mStatus.setText("Idle");

		// 使用自定义的ListAdapter将数据映射到TextView中
		setListAdapter(new SlowAdapter(this));

		// 设置滚动监听器
		getListView().setOnScrollListener(this);
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE: // Idle态，进行实际数据的加载显示
			mBusy = false;

			int first = view.getFirstVisiblePosition();
			int count = view.getChildCount();
			for (int i = 0; i < count; i++) {
				TextView tv = (TextView) view.getChildAt(i);
				if (tv.getTag() != null) { // 非null说明需要加载数据
					tv.setText(mStrings[first + i]);
					tv.setTag(null);
				}
			}

			mStatus.setText("Idle");
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			mBusy = true;
			mStatus.setText("Touch Scroll");
			break;
		case OnScrollListener.SCROLL_STATE_FLING:
			mBusy = true;
			mStatus.setText("Fling");
			break;
		default:
			mStatus.setText("Are you kidding me!");
			break;
		}
	}

	private String[] mStrings = { "Abbaye de Belloc",
			"Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu",
			"Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler",
			"Alverca", "Ambert", "American Cheese", "Ami du Chambertin",
			"Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
			"Aragon", "Ardi Gasna", "Ardrahan", "Armenian String",
			"Aromes au Gene de Marc", "Asadero", "Asiago", "Aubisque Pyrenees",
			"Autun", "Avaxtskyr", "Baby Swiss", "Babybel",
			"Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal",
			"Banon", "Barry's Bay Cheddar", "Basing", "Basket Cheese",
			"Bath Cheese", "Bavarian Bergkase", "Baylough", "Beaufort",
			"Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
			"Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir",
			"Bierkase", "Bishop Kennedy", "Blarney", "Bleu d'Auvergne",
			"Bleu de Gex", "Bleu de Laqueuille", "Bleu de Septmoncel",
			"Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
			"Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini",
			"Bocconcini (Australian)", "Boeren Leidenkaas", "Bonchester",
			"Bosworth" };

}