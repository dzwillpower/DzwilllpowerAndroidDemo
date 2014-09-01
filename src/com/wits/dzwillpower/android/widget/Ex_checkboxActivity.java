package com.wits.dzwillpower.android.widget;
import java.util.ArrayList;

import com.wits.dzwillpower.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 转载自：http://www.cnblogs.com/noTice520/archive/2012/02/17/2355415.html
 * */
public class Ex_checkboxActivity extends Activity {
	private ListView lv;
    private MyAdapter mAdapter;
    private ArrayList<String> list;
    private Button bt_selectall;
    private Button bt_cancel;
    private Button bt_deselectall;
    private int checkNum; // 记录选中的条目数量
    private TextView tv_show;// 用于显示选中的条目数量
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ex_checkboxactivity_main);
		 /* 实例化各个控件 */
        lv = (ListView) findViewById(R.id.lv);
        bt_selectall = (Button) findViewById(R.id.bt_selectall);
        bt_cancel = (Button) findViewById(R.id.bt_cancelselectall);
        bt_deselectall = (Button) findViewById(R.id.bt_deselectall);
        tv_show = (TextView) findViewById(R.id.tv);
        list = new ArrayList<String>();
        // 为Adapter准备数据
        initDate();
        // 实例化自定义的MyAdapter
        mAdapter = new MyAdapter(list, this);
        // 绑定Adapter
        lv.setAdapter(mAdapter);

        // 全选按钮的回调接口
        bt_selectall.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 遍历list的长度，将MyAdapter中的map值全部设为true
                for (int i = 0; i < list.size(); i++) {
                    MyAdapter.getIsSelected().put(i, true);
                }
                // 数量设为list的长度
                checkNum = list.size();
                // 刷新listview和TextView的显示
                dataChanged();

            }
        });
        // 取消按钮的回调接口
        bt_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 遍历list的长度，将已选的按钮设为未选
                for (int i = 0; i < list.size(); i++) {
                    if (MyAdapter.getIsSelected().get(i)) {
                        MyAdapter.getIsSelected().put(i, false);
                        checkNum--;// 数量减1
                    }
                }
                // 刷新listview和TextView的显示
                dataChanged();

            }
        });

        // 反选按钮的回调接口
        bt_deselectall.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 遍历list的长度，将已选的设为未选，未选的设为已选
                for (int i = 0; i < list.size(); i++) {
                    if (MyAdapter.getIsSelected().get(i)) {
                        MyAdapter.getIsSelected().put(i, false);
                        checkNum--;
                    } else {
                        MyAdapter.getIsSelected().put(i, true);
                        checkNum++;
                    }

                }
                // 刷新listview和TextView的显示
                dataChanged();
            }
        });
        
        // 绑定listView的监听器
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {

                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
            	ViewHolder holder = (ViewHolder) arg1.getTag();
                // 改变CheckBox的状态
                holder.cb.toggle();
                // 将CheckBox的选中状况记录下来
                MyAdapter.getIsSelected().put(arg2, holder.cb.isChecked()); 
                // 调整选定条目
                if (holder.cb.isChecked() == true) {
                    checkNum++;
                } else {
                    checkNum--;
                }
                // 用TextView显示
                tv_show.setText("已选中"+checkNum+"项");
                
            }
        });
    }

    // 初始化数据
    private void initDate() {
        for (int i = 0; i < 15; i++) {
            list.add("data" + "   " + i);
        }
    }

    // 刷新listview和TextView的显示
    private void dataChanged() {
        // 通知listView刷新
        mAdapter.notifyDataSetChanged();
        // TextView显示最新的选中数目
        tv_show.setText("已选中" + checkNum + "项");
    }

    
}