package com.gzc.weixin6ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * 自定义底部VIEW
 * 
 * @author gzc
 *
 */
public class ChangeColorIconWithText extends View {
	
	/** 默认为绿色（0xFF45C01A） */
	private final int DEF_COLOR_VALUE = 0xFF45C01A;
	private int mColor = DEF_COLOR_VALUE;
	
	private Bitmap mIconBitmap = null;
	private String mText = "微信";
	
	/** 默认为文本大小值 */
	private final int DEF_TEXT_SIZE_VALUE = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
	
	private int mTextSize = DEF_TEXT_SIZE_VALUE;	
	
	private Canvas mCanvas;
	private Bitmap mBitmap;
	private Paint mPaint;

	private float mAlpha = 0F;

	private Rect mIconRect;
	private Rect mTextBound;
	private Paint mTextPaint;
	

	// ==============构造函数==============
	public ChangeColorIconWithText(Context context) {
		this(context, null);
	}

	// 定义的控件如果要在xml中定义就要添加带参数的构造函数，并且要调用父类的构造函数
	// 自定义View增加属性的两种方法，第2种方法通过XML为View注册属性。与Android提供的标准属性写法一样
	
	public ChangeColorIconWithText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * 获取自定义属性的值
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public ChangeColorIconWithText(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ChangeColorIconWithText);

		int n = a.getIndexCount();

		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			// 获取自定义属性的值
			switch (attr) {
			case R.styleable.ChangeColorIconWithText_icon:
				BitmapDrawable drawable = (BitmapDrawable)a.getDrawable(attr);
				mIconBitmap = drawable.getBitmap();
				break;
			case R.styleable.ChangeColorIconWithText_color:
				this.mColor = a.getColor(attr, DEF_COLOR_VALUE);
				break;
			case R.styleable.ChangeColorIconWithText_text:
				this.mText = a.getString(attr);
				break;
			case R.styleable.ChangeColorIconWithText_text_size:
				this.mColor = (int)a.getDimension(attr, DEF_TEXT_SIZE_VALUE);
				break;
			default:
				break;
			}
		}
		// 回收资源
		a.recycle();
		
		mTextBound = new Rect();
		mTextPaint = new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0Xff555555);		// 灰色
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
	}
	// ==============构造函数==============
	
	// 检测View组件及其子组件的大小
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom() - mTextBound.height());

		int left = getMeasuredWidth() / 2 - iconWidth / 2;
		int top = getMeasuredHeight() / 2 - (mTextBound.height() + iconWidth)
				/ 2;
		mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
	}
	
	// 当组件将要绘制它的内容时回调，可以理解成U3D的OnGUI
	@Override
	protected void onDraw(Canvas canvas) {		
		canvas.drawBitmap(mIconBitmap, null, this.mIconRect, null);
		
		int alpha = (int)Math.ceil(255 * this.mAlpha);
		
		// 绘制图标
		// 内存中去准备mBitmap，setAlpha，纯色，xfermode，图标。
		setupTargetBitmap(alpha);
		
		// 绘制文字
		// 1、绘制原文本。2、绘制变色文本。
		drawSourceText(canvas, alpha);
		drawTargetText(canvas, alpha);
		
		canvas.drawBitmap(mBitmap, 0, 0, null);
	}
	
	/** 在内存中绘制可变色的icon */
	private void setupTargetBitmap(int alpha){
		this.mBitmap = Bitmap.createBitmap(this.getMeasuredWidth(), this.getMeasuredHeight(),Config.ARGB_8888);
		this.mCanvas = new Canvas(mBitmap);		
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		
		this.mCanvas.drawRect(mIconRect, mPaint);
		
		this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));		
		this.mPaint.setAlpha(255);
		
		this.mCanvas.drawBitmap(mIconBitmap, null, this.mIconRect,this.mPaint);
	}

	/** 绘制原文本  */
	private void drawSourceText(Canvas canvas, int alpha){
		mTextPaint.setColor(0xff333333);
		mTextPaint.setAlpha(255 - alpha);
		int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height();
		canvas.drawText(mText, x, y, mTextPaint);
	}
	
	/** 绘制变色文本  */
	private void drawTargetText(Canvas canvas, int alpha){
		mTextPaint.setColor(mColor);
		Log.i("绘制变色文本", "color = " + mColor);		
		
		mTextPaint.setAlpha(alpha);
		int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height();
		canvas.drawText(mText, x, y, mTextPaint);
	}
	
	/** 设置图标的Alpha */
	public void setIconAlpha(float alpha){
		this.mAlpha = alpha;
		invalidateView();
	}
	
	/** 重绘视图 */
	private void  invalidateView(){
		// 判断是否为UI线程（主线程）调用的该函数
		if(Looper.getMainLooper() == Looper.myLooper()){
			this.invalidate();
		}else{
			this.postInvalidate();
		}
	}
	
}
