package com.wits.dzwillpower.android.utilites;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 解决toast 连续弹出 和
 */
import com.wits.dzwillpower.android.R;
	public class ToastUtil {
	    private static final String TAG = ToastUtil.class.getSimpleName();

	    private ToastUtil() {}

	    private static Toast mToast = null;

	    public static final void showToast(Context context, String content) {
	        LayoutInflater inflater =
	                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//屏蔽了布局文件
//	        View view = inflater.inflate(R.layout.toast, null);
//	        TextView textview = (TextView) view.findViewById(R.id.toast_content);
	        
            TextView textview = null;
            View view = null;
	        if (!TextUtils.isEmpty(content)) {
	            textview.setText(content);
	        }

	        if (mToast == null) {
	            mToast = new Toast(context);
	            mToast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
	            mToast.setDuration(Toast.LENGTH_SHORT);
	        }

	        mToast.setView(view);
	        mToast.show();
	    }
	}
