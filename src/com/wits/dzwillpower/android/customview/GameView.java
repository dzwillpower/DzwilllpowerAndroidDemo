package com.wits.dzwillpower.android.customview;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Runnable {

	/* 声明Paint对象 */
	private Paint mPaint = null;

	public GameView(Context context) {
		super(context);
		/* 构建对象 */
		mPaint = new Paint();

		/* 开启线程 */
		new Thread(this).start();
	}

	float arc;

	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (arc > 360)
			arc = 0;
		/* 设置画布的颜色 */
		canvas.drawColor(Color.BLACK);

		/* 设置取消锯齿效果 */
		mPaint.setAntiAlias(true);
		mPaint.setAlpha(255);
		if (arc > 180) {

			mPaint.setColor(Color.YELLOW);
		} else {

			mPaint.setColor(Color.GREEN);
		}
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(5);
		RectF rf = new RectF(10, 10, 90, 140);
		canvas.drawRoundRect(rf, 10, 10, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		mPaint.setAlpha(0);
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawArc(new RectF(-100, -75, 200, 225), 240, arc, true, mPaint);
		arc += 2.5;

	}

	// 触笔事件
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	// 按键按下事件
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}

	// 按键弹起事件
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return false;
	}

	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		return true;
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			// 使用postInvalidate可以直接在线程中更新界面
			postInvalidate();
		}
	}
}