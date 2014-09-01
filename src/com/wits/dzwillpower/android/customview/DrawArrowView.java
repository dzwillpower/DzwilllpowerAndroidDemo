package com.wits.dzwillpower.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawArrowView extends View {

	public DrawArrowView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint paint1 = new Paint();
		Paint paint2 = new Paint();
		paint1.setColor(Color.RED);
		paint2.setColor(Color.GREEN);
		canvas.drawRect(100, 100, 150, 150, paint1);
		canvas.translate(100, 0);;
		canvas.drawRect(100, 100, 150, 150, paint2);
		canvas.save();
		canvas.translate(100, 0);
		canvas.drawRect(100, 100, 150, 150, paint1);
	}

}
