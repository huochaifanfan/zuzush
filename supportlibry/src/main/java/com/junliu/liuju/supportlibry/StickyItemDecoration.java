package com.junliu.liuju.supportlibry;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuju on 2018/3/28.
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {
    /**吸附的itemView*/
    private View mStickyItemView;
    /**吸附的itemView距离顶部距离*/
    private int mStickyItemViewMarginTop;
    /**吸附的itemView的高度*/
    private int mStickyItemViewHeight;
    /**通过它获取到需要吸附view的相关信息*/
    private StickyView mStickyView;
    /**滚动过程中当前的UI是否可以找到吸附的view*/
    private boolean mCurrentUIFindStickView;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    private RecyclerView.ViewHolder mViewHolder;
    private List<Integer> mStickyPositionList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    /**绑定数据的position*/
    private int mBindDataPosition = -1;
    private Paint mPaint;
    public StickyItemDecoration(){
        mStickyView = new ExampleStickyView();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
        mCurrentUIFindStickView = false;
        for (int i =0;i<parent.getChildCount();i++){
            View view = parent.getChildAt(i);
            /**判断是否是吸附view*/
            if (mStickyView.isStickyView(view)){
                mCurrentUIFindStickView = true;
                getStickyViewHolder(parent);
                cachStickyViewPosition(i);
                if (view.getTop() <=0){
                    /**绑定数据*/
                    bindDataForStickyView(linearLayoutManager.findFirstVisibleItemPosition(),parent.getMeasuredWidth());
                }else{
                    if (mStickyPositionList.size()>0){
                        if (mStickyPositionList.size() == 1){
                            bindDataForStickyView(mStickyPositionList.get(0),parent.getMeasuredWidth());
                        }else {
                            int currentPosition = linearLayoutManager.findFirstVisibleItemPosition()+i;
                            int indexOfCurrentPosition = mStickyPositionList.lastIndexOf(currentPosition);
                            bindDataForStickyView(mStickyPositionList.get(indexOfCurrentPosition-1),parent.getMeasuredWidth());
                        }
                    }
                }
                if (view.getTop() > 0 && view.getTop() <= mStickyItemViewHeight) {
                    mStickyItemViewMarginTop = mStickyItemViewHeight - view.getTop();
                } else {
                    mStickyItemViewMarginTop = 0;
                }

                drawStickyItemView(c);
                break;
            }
        }
        if (!mCurrentUIFindStickView) {
            mStickyItemViewMarginTop = 0;
            if (linearLayoutManager.findFirstVisibleItemPosition() + parent.getChildCount() == parent.getAdapter().getItemCount()) {
                bindDataForStickyView(mStickyPositionList.get(mStickyPositionList.size() - 1), parent.getMeasuredWidth());
            }
            drawStickyItemView(c);
        }
    }

    private void drawStickyItemView(Canvas c) {
        int saveCount = c.save();
        c.translate(0,-mStickyItemViewMarginTop);
        mStickyItemView.draw(c);
        c.restoreToCount(saveCount);
    }

    private void bindDataForStickyView(int position, int measuredWidth) {
        if (mBindDataPosition == position) return;
        mBindDataPosition = position;
        mAdapter.onBindViewHolder(mViewHolder,mBindDataPosition);
        measureLayoutStickyItemView(measuredWidth);
        mStickyItemViewHeight = mViewHolder.itemView.getBottom()-mViewHolder.itemView.getTop();
    }

    private void measureLayoutStickyItemView(int parentWidth) {
        if (!mStickyItemView.isLayoutRequested())return;
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parentWidth ,View.MeasureSpec.EXACTLY);
        int heightSpce;
        ViewGroup.LayoutParams layoutParams = mStickyItemView.getLayoutParams();
        if (layoutParams !=null && layoutParams.height>0){
            heightSpce = View.MeasureSpec.makeMeasureSpec(layoutParams.height , View.MeasureSpec.EXACTLY);
        }else {
            heightSpce = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        }
        mStickyItemView.measure(widthSpec,heightSpce);
        mStickyItemView.layout(0,0,mStickyItemView.getMeasuredWidth(),mStickyItemView.getMeasuredHeight());
    }

    /**
     * 缓存吸附view的position
     * @param i
     */
    private void cachStickyViewPosition(int i) {
        int position = linearLayoutManager.findFirstVisibleItemPosition()+i;
        if (!mStickyPositionList.contains(position)){
            mStickyPositionList.add(position);
        }
    }

    /**
     * 得到吸附viewHolder
     * @param recyclerView
     */
    private void getStickyViewHolder(RecyclerView recyclerView) {
        if (mAdapter != null) return;
        mAdapter = recyclerView.getAdapter();
        mViewHolder = mAdapter.onCreateViewHolder(recyclerView,mStickyView.getStikeyViewType());
        mStickyItemView = mViewHolder.itemView;
    }
}
