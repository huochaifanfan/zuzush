package com.zuzush.zuzush.overrideview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.zuzush.zuzush.R;


/**
 * 自定义进度条
 * @author liujun
 *
 */
public class CustomerProgress extends ProgressBar {
	//定义相关默认属性
	public  static final int DETAULT_TEXT_SIZE = 10;
	public  static final int DETAULT_TEXT_COLOR = Color.parseColor("#007AFF");
	public  static final int DETAULT_COLOR_UNREACH = Color.parseColor("#D3D6DA");
	public  static final int DETAULT_HEIGHT_UNREACH = 3;
	public  static final int DETAULT_COLOR_REACH = DETAULT_TEXT_COLOR;
	public  static final int DETAULT_HEIGHT_REACH = 3;
	public  static final int DETAULT_TEXT_OFFSET = 10;

	private int mTextSize = sp2px(DETAULT_TEXT_SIZE);
	private int mTextColor = DETAULT_TEXT_COLOR;
	private int unReachColor = DETAULT_COLOR_UNREACH;
	private  int unReachHeight = dp2px(DETAULT_HEIGHT_UNREACH);
	private int reachColor = DETAULT_COLOR_REACH;
	private int reachHeiht = dp2px(DETAULT_HEIGHT_REACH);
	private  int offSet = dp2px(DETAULT_TEXT_OFFSET);

	private Paint mPaint = new Paint();
	private int mRealWidth;

	public CustomerProgress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getStyledAttrs(attrs);
	}

	public CustomerProgress(Context context, AttributeSet attrs) {
		this(context, attrs , 0);
	}

	public CustomerProgress(Context context) {
		this(context , null);
	}
	/**
	 * 获取自定义属性
	 * @param attrs
	 */
	private void getStyledAttrs(AttributeSet attrs) {
		TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomerProgress);
		mTextSize = (int) typeArray.getDimension(R.styleable.CustomerProgress_progressTextSize , mTextSize);
		mTextColor = typeArray.getColor(R.styleable.CustomerProgress_progressReachColor ,mTextColor);
		unReachColor = typeArray.getColor(R.styleable.CustomerProgress_progressUnreachColor , unReachColor);
		unReachHeight = (int) typeArray.getDimension(R.styleable.CustomerProgress_progressUnreachHeight , unReachHeight);
		reachColor = typeArray.getColor(R.styleable.CustomerProgress_progressReachColor ,reachColor);
		reachHeiht = (int) typeArray.getDimension(R.styleable.CustomerProgress_progressReachHeight ,reachHeiht);
		offSet = (int) typeArray.getDimension(R.styleable.CustomerProgress_progressTextOffset , offSet);
		mPaint.setTextSize(mTextSize);
		typeArray.recycle();
	}
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width , height);
		mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
	}
	private int measureHeight(int heightMeasureSpec) {
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int size = MeasureSpec.getSize(heightMeasureSpec);
		int result = 0;
		if (heightMode == MeasureSpec.EXACTLY) {
			result = size;
		}else {
			int textHeight = (int) (mPaint.descent() - mPaint.ascent());
			result = getPaddingTop() + getPaddingBottom() + 
					Math.max(Math.max(reachHeiht , unReachHeight) ,
							Math.abs(textHeight));

		}
		if (heightMode == MeasureSpec.AT_MOST) {
			result = Math.min(result, size);
		}
		return result;
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.translate(getPaddingLeft(), getHeight() / 2);
		boolean needDraw  = false;
		String text = getProgress() + "%";
		int textWidth = (int)(mPaint.measureText(text));
		float progress = getProgress()*1.0f/getMax();
		int progressX = (int) (progress * mRealWidth);
		if (progressX + textWidth > mRealWidth){
			//不需要再绘制
			progressX = mRealWidth - textWidth;
			needDraw = true;
		}
		float endX = progress*mRealWidth - offSet/2;
		if (endX > 0) {
			//开始绘制
			mPaint.setColor(reachColor);
			mPaint.setStrokeWidth(reachHeiht);
			canvas.drawLine(0, 0, endX, 0, mPaint);
		}
		//绘制文字
		mPaint.setColor(mTextColor);
		int y = (int) -((mPaint.descent() + mPaint.ascent())/2);
		canvas.drawText(text , progressX , y , mPaint);
		//绘制unreachBar
		mPaint.setColor(unReachColor);
		if ( !needDraw){
			//如果需要绘制
			float start = progressX + offSet / 2 + textWidth;
			mPaint.setStrokeWidth(unReachHeight);
			canvas.drawLine(start  ,0 ,mRealWidth , 0 , mPaint);
		}
		canvas.restore();
	}

	private int dp2px(int dp){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
				dp, getResources().getDisplayMetrics());
	}
	private int sp2px(int sp){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, 
				getResources().getDisplayMetrics());
	}
}
