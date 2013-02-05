package com.wits.dzwillpower.home;

//创建简单的View
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;

public class BounceView extends View {
	float x = 40;

	public BounceView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		x += 10;
		Paint mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.GREEN);
		canvas.drawCircle(x, 40, 40, mPaint);
	}
}