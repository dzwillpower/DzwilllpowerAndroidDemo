package com.wits.dzwillpower.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeTextView extends TextView {

    /** 是否停止滚动 */
    private boolean mStopMarquee;
    private String mText;
    private float mCoordinateX;
    private float mTextWidth;

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(String text) {
        this.mText = text;
        mTextWidth = getPaint().measureText(mText);
        if (mHandler.hasMessages(0))
            mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void onAttachedToWindow() {
        mStopMarquee = false;
        if (!TextUtils.isEmpty(mText))
            mHandler.sendEmptyMessageDelayed(0, 2000);
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        mStopMarquee = true;
        if (mHandler.hasMessages(0))
            mHandler.removeMessages(0);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(mText))
            canvas.drawText(mText, mCoordinateX, 15, getPaint());
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 0:
                if (Math.abs(mCoordinateX) > (mTextWidth + 100)) {
                    mCoordinateX = 0;
                    invalidate();
                    if (!mStopMarquee) {
                        sendEmptyMessageDelayed(0, 2000);
                    }
                } else {
                    mCoordinateX -= 1;
                    invalidate();
                    if (!mStopMarquee) {
                        sendEmptyMessageDelayed(0, 30);
                    }
                }

                break;
            }
            super.handleMessage(msg);
        }
    };

}

/*1、2000表示延迟2秒开始跑马灯效果

2、mTextWidth + 100 表示跑出屏幕100像素再重新开始跑 

3、每30毫秒移动1像素

4、原理很简单，就是定时刷，用法很简单，直接setText就行，和用系统的一样，但是不能通过设置xml的值来直接跑，这个可以自己修改。

5、注意onDraw时判定一下text是否为空，这里StringUtils.isEmpty替换成自己的判定方法即可。
http://www.cnblogs.com/over140/p/3687952.html 
*/