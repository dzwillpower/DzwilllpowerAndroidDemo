package com.wits.dzwillpower.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.Log;
import android.view.View;

public class DrawText extends View {

	public DrawText(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.GRAY);
		Paint textPaint = new Paint( Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(40);
		textPaint.setColor( Color.WHITE);

		// FontMetrics对象
		FontMetrics fontMetrics = textPaint.getFontMetrics();

		String text = "abcdefghijklmnopqrstu";

		// 计算每一个坐标
		float baseX = 20;
		float baseY = 300;
		Log.d("dzwillpower", "fontMetrics.top: "+fontMetrics.top+" fontMetrics.ascent: "+fontMetrics.ascent+" fontMetrics.descent: "+fontMetrics.descent+" fontMetrics.bottom: "+fontMetrics.bottom);
		float topY = baseY + fontMetrics.top;
		float ascentY = baseY + fontMetrics.ascent;
		float descentY = baseY + fontMetrics.descent;
		float bottomY = baseY + fontMetrics.bottom;


		// 绘制文本
		canvas.drawText( text, baseX, baseY, textPaint);


		// BaseLine描画
		Paint baseLinePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
		baseLinePaint.setColor( Color.RED);
		canvas.drawLine(0, baseY, getWidth(), baseY, baseLinePaint);


		// Base描画
		canvas.drawCircle( baseX, baseY, 5, baseLinePaint);


		// TopLine描画
		Paint topLinePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
		topLinePaint.setColor( Color.LTGRAY);
		canvas.drawLine(0, topY, getWidth(), topY, topLinePaint);


		// AscentLine描画
		Paint ascentLinePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
		ascentLinePaint.setColor( Color.GREEN);
		canvas.drawLine(0, ascentY, getWidth(), ascentY, ascentLinePaint);


		// DescentLine描画
		Paint descentLinePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
		descentLinePaint.setColor( Color.YELLOW);
		canvas.drawLine(0, descentY, getWidth(), descentY, descentLinePaint);


		// ButtomLine描画
		Paint bottomLinePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
		bottomLinePaint.setColor( Color.MAGENTA);
		canvas.drawLine(0, bottomY, getWidth(), bottomY, bottomLinePaint);
	}
}
