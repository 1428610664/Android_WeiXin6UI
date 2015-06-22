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
 * �Զ���ײ�VIEW
 * 
 * @author gzc
 *
 */
public class ChangeColorIconWithText extends View {
	
	/** Ĭ��Ϊ��ɫ��0xFF45C01A�� */
	private final int DEF_COLOR_VALUE = 0xFF45C01A;
	private int mColor = DEF_COLOR_VALUE;
	
	private Bitmap mIconBitmap = null;
	private String mText = "΢��";
	
	/** Ĭ��Ϊ�ı���Сֵ */
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
	

	// ==============���캯��==============
	public ChangeColorIconWithText(Context context) {
		this(context, null);
	}

	// ����Ŀؼ����Ҫ��xml�ж����Ҫ��Ӵ������Ĺ��캯��������Ҫ���ø���Ĺ��캯��
	// �Զ���View�������Ե����ַ�������2�ַ���ͨ��XMLΪViewע�����ԡ���Android�ṩ�ı�׼����д��һ��
	
	public ChangeColorIconWithText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * ��ȡ�Զ������Ե�ֵ
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
			// ��ȡ�Զ������Ե�ֵ
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
		// ������Դ
		a.recycle();
		
		mTextBound = new Rect();
		mTextPaint = new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0Xff555555);		// ��ɫ
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
	}
	// ==============���캯��==============
	
	// ���View�������������Ĵ�С
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
	
	// �������Ҫ������������ʱ�ص�����������U3D��OnGUI
	@Override
	protected void onDraw(Canvas canvas) {		
		canvas.drawBitmap(mIconBitmap, null, this.mIconRect, null);
		
		int alpha = (int)Math.ceil(255 * this.mAlpha);
		
		// ����ͼ��
		// �ڴ���ȥ׼��mBitmap��setAlpha����ɫ��xfermode��ͼ�ꡣ
		setupTargetBitmap(alpha);
		
		// ��������
		// 1������ԭ�ı���2�����Ʊ�ɫ�ı���
		drawSourceText(canvas, alpha);
		drawTargetText(canvas, alpha);
		
		canvas.drawBitmap(mBitmap, 0, 0, null);
	}
	
	/** ���ڴ��л��ƿɱ�ɫ��icon */
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

	/** ����ԭ�ı�  */
	private void drawSourceText(Canvas canvas, int alpha){
		mTextPaint.setColor(0xff333333);
		mTextPaint.setAlpha(255 - alpha);
		int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height();
		canvas.drawText(mText, x, y, mTextPaint);
	}
	
	/** ���Ʊ�ɫ�ı�  */
	private void drawTargetText(Canvas canvas, int alpha){
		mTextPaint.setColor(mColor);
		Log.i("���Ʊ�ɫ�ı�", "color = " + mColor);		
		
		mTextPaint.setAlpha(alpha);
		int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height();
		canvas.drawText(mText, x, y, mTextPaint);
	}
	
	/** ����ͼ���Alpha */
	public void setIconAlpha(float alpha){
		this.mAlpha = alpha;
		invalidateView();
	}
	
	/** �ػ���ͼ */
	private void  invalidateView(){
		// �ж��Ƿ�ΪUI�̣߳����̣߳����õĸú���
		if(Looper.getMainLooper() == Looper.myLooper()){
			this.invalidate();
		}else{
			this.postInvalidate();
		}
	}
	
}
