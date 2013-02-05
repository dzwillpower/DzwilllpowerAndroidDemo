package com.wits.dzwillpower.animation;

import com.wits.dzwillpower.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class Ex_Animation01 extends Activity {
	/** Called when the activity is first created. */
	private ImageView imageView = null;
	private Button rotateButton = null;
	private Button scaleButton = null;
	private Button alphaButton = null;
	private Button translateButton = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ex_animation01_main);
		imageView = (ImageView) findViewById(R.id.imageViewId);

		rotateButton = (Button) findViewById(R.id.rotateButtonId);
		rotateButton.setOnClickListener(new RotateButtonListener());

		scaleButton = (Button) findViewById(R.id.scaleButtonId);
		scaleButton.setOnClickListener(new ScaleButtonListener());

		alphaButton = (Button) findViewById(R.id.alphaButtonId);
		alphaButton.setOnClickListener(new AlphaButtonListener());

		translateButton = (Button) findViewById(R.id.translateButtonId);
		translateButton.setOnClickListener(new TranslateButtonListener());
	}

	private class RotateButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			AnimationSet animationSet = new AnimationSet(true);
			RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
					Animation.RELATIVE_TO_PARENT, 0.5f,
					Animation.RELATIVE_TO_PARENT, 0.5f);
			rotateAnimation.setDuration(5000);
			animationSet.addAnimation(rotateAnimation);
			imageView.startAnimation(animationSet);
		}
	}

	private class ScaleButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			AnimationSet animationSet = new AnimationSet(true);
			ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.1f, 1,
					0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			animationSet.addAnimation(scaleAnimation);
			animationSet.setStartOffset(1000);
			animationSet.setFillAfter(true);
			animationSet.setFillBefore(false);
			animationSet.setDuration(2000);
			imageView.startAnimation(animationSet);
		}

	}

	private class AlphaButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// ����һ��AnimationSet����
			AnimationSet animationSet = new AnimationSet(true);
			// ����һ��AlphaAnimation����
			AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
			// ���ö���ִ�е�ʱ�䣨��λ�����룩
			alphaAnimation.setDuration(1000);
			// ��AlphaAnimation������ӵ�AnimationSet����
			animationSet.addAnimation(alphaAnimation);
			// ʹ��ImageView��startAnimation������ʼִ�ж���
			imageView.startAnimation(animationSet);
		}
	}

	private class TranslateButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			AnimationSet animationSet = new AnimationSet(true);
			TranslateAnimation translateAnimation = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
					0.5f, Animation.RELATIVE_TO_SELF, 0f,
					Animation.RELATIVE_TO_SELF, 1.0f);
			translateAnimation.setDuration(1000);
			animationSet.addAnimation(translateAnimation);
			imageView.startAnimation(animationSet);
		}
	}
}