package com.junliu.liuju.androidtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalScrollViewEX extends ViewGroup {
    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;
    /**分别记录上次滑动的坐标*/
    private int mLastX,mLastY;
    private int mLastInterruptX,getmLastInterruptY;
    private Scroller mScroller;
    private VelocityTracker velocityTracker;


    public HorizontalScrollViewEX(Context context) {
        this(context,null);
    }

    public HorizontalScrollViewEX(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalScrollViewEX(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mScroller == null){
            mScroller = new Scroller(getContext());
            velocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int detlaX = x - mLastInterruptX;
                int deltaY = y - getmLastInterruptY;
                if (Math.abs(detlaX)>Math.abs(deltaY)){
                    intercepted = true;
                }else {
                    intercepted = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;

                default:
                    break;
        }
        mLastX = x;
        mLastX = y;
        mLastInterruptX = x;
        getmLastInterruptY = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                
                break;


        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
