package com.junliu.liuju.androidtest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomTabView extends LinearLayout implements View.OnClickListener {
    private List<View> mTabViews;
    private List<Tab> mTabs;
    private OnTabCheckListener onTabCheckListener;

    public void setOnTabCheckListener(OnTabCheckListener onTabCheckListener){
        this.onTabCheckListener = onTabCheckListener;
    }

    public CustomTabView(Context context) {
        this(context , null);
    }

    public CustomTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public CustomTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        mTabViews = new ArrayList<>();
        mTabs = new ArrayList<>();
    }

    public void addTab(Tab tab){

    }
    @Override
    public void onClick(View v) {

    }
    public static class Tab{
        private int mIconNormalResId;
        private int mIconPressedResId;
        private int mNormalColor;
        private int mSelectColor;
        private String mText;

        public Tab setText(String text){
            mText = text;
            return this;
        }

        public Tab setNormalIcon(int res){
            mIconNormalResId = res;
            return this;
        }

        public Tab setPressedIcon(int res){
            mIconPressedResId = res;
            return this;
        }

        public Tab setColor(int color){
            mNormalColor = color;
            return this;
        }

        public Tab setCheckedColor(int color){
            mSelectColor = color;
            return this;
        }
    }
    public interface  OnTabCheckListener{
        void onTabSelected(View v,int position);
    }
}
