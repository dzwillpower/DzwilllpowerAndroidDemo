package com.wits.dzwillpower.android.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import com.wits.dzwillpower.android.R;

public class FilmView extends ViewGroup {

	private int original_height = 195;// 图片默认高度
	private int original_width = 150;// 图片默认宽度
	private int animation_time=100;
	private int missing_drawable = R.drawable.poster_default;// 缺失图片ID
	private int color_of_word = Color.parseColor("#f0f0f0");
	private int color_of_bord_bg = Color.YELLOW;
	private int color_of_mark = Color.parseColor("#ffffff");
	private int mark_location = 0;

	private float reflection_spacing = 0.5f;// 倒影离图片的距离
	private float name_spacing = 5;// 影片名字的位置，整数代码在倒影上，负数代表在图片上
	private float name_font_size = 26f;// 影片名尺寸

	private float scale = 1f;//
	private float anim_scale = 1.2f;// 放大比例
	private float reflection_scale = 0.4f;// 倒影的比例
	private float progress = 0.0f;

	private boolean missing = true;// 图片是否缺失
	private boolean large = false;// 是否处于放大状态
	private boolean focus = false;// 是否处于焦点状态
	private boolean draw_focus_flag = false;// 是否只在焦点状态下绘制影片名
	private boolean draw_border_flag = false;// 是否绘制焦点边框

	private String film_text = "";// 影片名
	private String mark_text="";

	private Bitmap original_bitmap;// 图片
	private Bitmap mark_bitmap;
	private Bitmap miss_bitmap;// 缺失图片
	private Drawable miss_drawable;//
	private Paint mp = new Paint();// 排行画笔
	private Paint bp = new Paint();// 边框画笔
	private Paint pp = new Paint();// 进度画笔
	private Paint pbp = new Paint();// 进度背景画笔

	public Object data = new Object();

	public Object getData() {
		return data;
	}

	public RankImageView original_view = null;
	public TextView file_name = null;

	private BitmapChangeListener listener;
	private DataSetListener data_listener;
	private Context context;

	public FilmView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public FilmView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public void init() {
		mark_bitmap = null;
		progress = -1.0f;
		mp.setTextSize(22);
		mp.setAntiAlias(true);
		mp.setColor(color_of_mark);
		mp.setStrokeWidth(2);
		mp.setShadowLayer(2, 0, 0, Color.GRAY);
		mp.setFakeBoldText(true);
		mp.setTextAlign(Align.CENTER);

		bp.setAntiAlias(false);
		bp.setColor(Color.parseColor("#ff00ff"));
		bp.setStyle(Style.STROKE);
		bp.setStrokeWidth(4f);

		pp.setAntiAlias(false);
		pp.setColor(Color.GREEN);
		pp.setStyle(Style.FILL_AND_STROKE);
		pp.setStrokeWidth(4f);

		pbp.setAntiAlias(false);
		pbp.setColor(Color.parseColor("#AA000000"));
		pbp.setStyle(Style.FILL_AND_STROKE);
		pbp.setStrokeWidth(4f);

		miss_drawable = context.getResources().getDrawable(missing_drawable);
		miss_bitmap = BitmapFactory.decodeResource(context.getResources(), missing_drawable);
		original_view = new RankImageView(context);
		setBitmapChangeListener(new BitmapChangeListener() {

			@Override
			public void onBitmapChange(Bitmap bitmap, int code) {
				if (code == 1) {
					original_bitmap = bitmap;
					missing = false;
				} else {
					original_bitmap = miss_bitmap;
					missing = true;
				}
				if (large) {
					large(false);
				} else {
					postInvalidate();
				}
			}
		});
		this.addView(original_view);
		file_name = new TextView(context);
		file_name.setGravity(Gravity.CENTER);
		file_name.setHorizontallyScrolling(true);
		file_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		file_name.setSingleLine(true);
		file_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, name_font_size);
		file_name.setTextColor(color_of_word);
		file_name.setPadding(5, 0, 5, 0);
		file_name.setMarqueeRepeatLimit(Integer.MAX_VALUE);
		this.addView(file_name, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		this.setFocusable(true);
		this.setWillNotDraw(false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int count = canvas.saveLayer(0, 0, this.getWidth(), this.getHeight(), null, Canvas.ALL_SAVE_FLAG);
		canvas.translate(0, original_view.getBottom() + reflection_spacing);
		if (!missing) {
			canvas.save();
			canvas.translate(original_view.getLeft(), original_view.getHeight());
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			matrix.postScale(1, -1, -1, 1);
			canvas.drawBitmap(original_bitmap, matrix, null);
			canvas.restore();
			Paint paint = new Paint();
			paint.setAntiAlias(false);
			float line_scale = large ? 1f / anim_scale : 1f;
			LinearGradient shader = new LinearGradient(0, 0, 0, original_view.getHeight() * reflection_scale
					* line_scale, 0x70ffffff, 0x00ffffff, TileMode.MIRROR);
			// 设置阴影
			paint.setShader(shader);
			paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));
			// 用已经定义好的画笔构建一个矩形阴影渐变效果
			canvas.drawRect(original_view.getLeft(), original_view.getTop(),
					original_view.getWidth(), original_view.getHeight()
							* reflection_scale, paint);
		}
		canvas.restoreToCount(count);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int childTop = 0;
		int childLeft = 0;
		int childWidth = 0;
		int childHeight = 0;
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = this.getChildAt(i);
			if (child.getVisibility() == View.GONE)
				continue;
			if (child instanceof RankImageView) {
				childTop = 0;
				childLeft = 0;
				childWidth = (int) (original_width * scale);
				childHeight = (int) (original_height * scale);
			} else if (child instanceof TextView) {
				TextView tv = (TextView) child;
				childTop = (int) (original_height * scale + name_spacing);
				childLeft = (int) bp.getStrokeWidth();
				childWidth = (int) (original_width * scale - 2 * bp.getStrokeWidth());
				childHeight = (int) this.getFontHeight(tv.getPaint());
			} else {
				childTop = 0;
				childLeft = 0;
				childWidth = 0;
				childHeight = 0;
			}
			child.measure(childWidth, childHeight);
			child.layout(childLeft, childTop, childLeft + childWidth, childTop
					+ childHeight);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = (int) (original_width * scale);
		int height = (int) (original_height * scale * (1.0f + reflection_scale) + reflection_spacing);
		if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY) {
			width = View.MeasureSpec.getSize(widthMeasureSpec);
		}

		if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.EXACTLY) {
			height = View.MeasureSpec.getSize(heightMeasureSpec);
		}

		widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) width,
				View.MeasureSpec.EXACTLY);
		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) height,
				View.MeasureSpec.EXACTLY);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void large(boolean animation) {
		this.clearAnimation();
		this.setPivotY(this.getHeight()*(0.5f/(1+reflection_scale)));
		this.setPivotX(this.getWidth()*0.5f);
		this.animate().scaleX(anim_scale).scaleY(anim_scale).setDuration(animation?animation_time:0).start();
		large=true;
		changeFocus(true);

	}

	public void smalll(boolean animation) {
		this.clearAnimation();
		this.setPivotY(this.getHeight()*(0.5f/(1+reflection_scale)));
		this.setPivotX(this.getWidth()*0.5f);
		this.animate().scaleX(1f).scaleY(1).setDuration(animation?animation_time:0).start();
		large=false;
		changeFocus(false);
	}

	// 设置文本
	public void setTextAndDraw(String text) {
		this.film_text = text;
		this.postInvalidate();
	}

	public void setData(Object o) {
		if (o != null) {
			if (data_listener != null){
				data_listener.onDataSetting(o);
			}
			data = o;
			this.postInvalidate();
		}
	}

	// 设置文本
	public String getText() {
		return film_text;
	}

	// 设置图片
	public void setBitamp(Bitmap bitmap) {
		original_bitmap = bitmap;
		original_view.setImageBitmap(original_bitmap);
		missing = false;
		this.postInvalidate();
	}

	// 设置缺失图片
	public void setMissingDrawable(int res) {
		missing_drawable = res;
		if(original_view!=null){
			original_view.setBackgroundDrawable(getResources().getDrawable(missing_drawable));
			original_bitmap=BitmapFactory.decodeResource(context.getResources(), missing_drawable);
			missing = false;
			this.postInvalidate();
		}
	}

	// 设置倒影和原图片的距离
	public void setReflectionSpacing(float spacing) {
		reflection_spacing = spacing;
	}

	public void setAnimScale(float scale) {
		anim_scale = scale;
	}

	public void setScaleAndDraw(float scale) {
		this.scale = scale;
		this.requestLayout();
	}

	public void changeFocus(boolean focus) {
		this.focus = focus;
		if (draw_focus_flag) {
			if (focus) {
				file_name.setVisibility(View.VISIBLE);
			} else {
				file_name.setVisibility(View.INVISIBLE);
			}
		}
		file_name.setSelected(focus);
		original_view.postInvalidate();
		this.postInvalidate();
	}

	public void setScale(float scale, boolean anim) {
		if (this.scale == scale) {
			return;
		}
		this.clearAnimation();

		if (anim) {
			this.startAnimation(new FilmAnimation(this.scale, scale));
		} else {
			setScaleAndDraw(scale);
		}
	}

	public boolean isLarge() {
		return large;
	}

	class FilmAnimation extends Animation {
		float from = 0f;
		float to = 0f;

		public FilmAnimation(float from, float to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public void initialize(int width, int height, int parentWidth, int parentHeight) {
			super.initialize(width, height, parentWidth, parentHeight); // 初始化中间坐标值
			this.setDuration(400);
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation tt) {
			setScaleAndDraw(from + (to - from) * interpolatedTime);
		}
	}

	public class RankImageView extends ImageView {

		public RankImageView(Context context) {
			super(context);

			this.setBackgroundDrawable(miss_drawable);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void setImageDrawable(Drawable drawable) {

			if (drawable != null) {
				try {
					Bitmap bm = convertDrawable2BitmapByCanvas(drawable);
					if (bm != null) {
						drawable = new BitmapDrawable(bm);
						if (listener != null) {
							listener.onBitmapChange(bm, 1);
						}
					} else {
						if (listener != null) {
							listener.onBitmapChange(bm, -1);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				drawable = miss_drawable;
				if (listener != null) {
					listener.onBitmapChange(null, -1);
				}
			}
			super.setImageDrawable(drawable);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			if (mark_bitmap != null) {
				switch (mark_location) {
				case 0:
					canvas.drawBitmap(mark_bitmap, 0, 0, null);
					canvas.drawText(mark_text, mark_bitmap.getWidth() * 0.38f, mark_bitmap.getHeight() * 0.45f, mp);
					break;
				case 1:
					canvas.drawBitmap(mark_bitmap, this.getWidth() - mark_bitmap.getWidth(), 0, null);
					canvas.drawText(mark_text, this.getWidth() - mark_bitmap.getWidth() * 0.62f,
							mark_bitmap.getHeight() * 0.45f, mp);
					break;
				case 2:
					canvas.drawBitmap(mark_bitmap, 0, this.getHeight() - mark_bitmap.getHeight(), null);
					canvas.drawText(mark_text, mark_bitmap.getWidth() * 0.38f,
							this.getHeight() - mark_bitmap.getHeight() * 0.55f, mp);
					break;
				case 3:
					canvas.drawBitmap(mark_bitmap, this.getWidth() - mark_bitmap.getWidth(), this.getHeight()
							- mark_bitmap.getHeight(), null);
					canvas.drawText(mark_text, this.getWidth() - mark_bitmap.getWidth() * 0.62f, this.getHeight()
							- mark_bitmap.getHeight() * 0.55f, mp);
					break;
				}
			}

			if (progress > 0.0f) {
				RectF rectB = new RectF(0, this.getHeight() - 7, (this.getWidth()), this.getHeight());
				canvas.drawRect(rectB, pbp);
				RectF rectF = new RectF(3, this.getHeight() - 7, (this.getWidth() - 3) * progress, this.getHeight() - 3);
				canvas.drawRect(rectF, pp);
			}
			if (draw_border_flag && focus) {
				Rect rect = new Rect(0, 0, this.getWidth(), this.getHeight());
				canvas.drawRect(rect, bp);
			}
		}
	}

	@SuppressLint("DrawAllocation")
	private Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
		if (drawable == null) {
			return null;
		}

		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap BitmapOrg = bd.getBitmap();
		if(BitmapOrg==null){
			return null;
		}

		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = original_width;
		int newHeight = original_height;

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		return Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
	}

	public interface BitmapChangeListener {
		public void onBitmapChange(Bitmap bitmap, int code);
	}

	public interface DataSetListener {
		public void onDataSetting(Object o);
	}

	public float getFontHeight(Paint paint) {
		FontMetrics fm = paint.getFontMetrics();
		return (float) (Math.ceil(fm.descent - fm.top) + 2);
	}

	public void setBitmapChangeListener(BitmapChangeListener listener) {
		this.listener = listener;
	}

	public void setDataSetListener(DataSetListener listener) {
		this.data_listener = listener;
	}

	public RankImageView getImageView() {
		return original_view;
	}

	public void setText(String text) {
		this.film_text = text;
		file_name.setText(text);

	}

	public int getColor_of_word() {
		return color_of_word;
	}

	public void setColor_of_word(int color_of_word) {
		this.color_of_word = color_of_word;
		file_name.setTextColor(color_of_word);
	}

	public void setFile_name_bg(Drawable bg) {
		file_name.setBackgroundDrawable(bg);
	}

	public int getColor_of_bord_bg() {
		return color_of_bord_bg;
	}

	public void setColor_of_bord_bg(int color_of_bord_bg) {
		this.color_of_bord_bg = color_of_bord_bg;
		this.bp.setColor(color_of_bord_bg);

	}

	public int getColor_of_mark() {
		return color_of_mark;
	}

	public void setColor_of_mark(int color_of_mark) {
		this.color_of_mark = color_of_mark;
		this.mp.setColor(color_of_mark);
	}

	public boolean isDraw_focus_flag() {
		return draw_focus_flag;
	}

	public void setDraw_focus_flag(boolean draw_focus_flag) {
		this.draw_focus_flag = draw_focus_flag;
	}

	public boolean isDraw_border_flag() {
		return draw_border_flag;
	}

	public void setDraw_border_flag(boolean draw_border_flag) {
		this.draw_border_flag = draw_border_flag;
	}

	public float getName_spacing() {
		return name_spacing;
	}

	public float getName_font_size() {
		return name_font_size;
	}

	public void setName_font_size(float name_font_size) {
		this.name_font_size = name_font_size;
	}

	public void setName_spacing(float name_spacing) {
		this.name_spacing = name_spacing;
	}

	public void setMark(int mark_type, String mark_text, int mark_location) {
		this.mark_text = mark_text;
		this.mark_location = mark_location;
		calMark_bitmap(mark_type);
		this.postInvalidate();
	}

	public void setMark(Bitmap mark_bitmap, String mark_text, int mark_location) {
		this.mark_bitmap = mark_bitmap;
		this.mark_text = mark_text;
		this.mark_location = mark_location;
		this.postInvalidate();
	}
	public void setMarkRes(int mark_res,String mark_text,int mark_location){
		this.mark_bitmap= BitmapFactory.decodeResource(context.getResources(),
				mark_res);
		this.mark_text=mark_text;
		this.mark_location=mark_location;
		this.postInvalidate();
	}
	public void setProgress(float progress){
		if(progress<0.0f||progress>1.0f){
			this.progress=0.0f;
		}else{
			this.progress=progress;
		}
		this.postInvalidate();
	}

	private void calMark_bitmap(int mark_type) {
		switch (mark_type) {
		case -1:
			mark_bitmap = null;
			break;
		case 0:
			mark_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.subscript_hot_01);
			break;
		case 1:
			mark_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.subscript_hot_02);
			break;
		case 2:
			mark_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.subscript_hot_03);
			break;
		case 3:
			mark_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.subscript_hot_04);
			break;
		}

	}

	private String vodID;
	private String playType;	
	

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public String getVodID() {
		return vodID;
	}

	public void setVodID(String vodID) {
		this.vodID = vodID;
	}

}
