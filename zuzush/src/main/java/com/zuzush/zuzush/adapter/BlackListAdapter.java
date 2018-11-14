package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.BlackListBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liujun on 2017/9/21 0021.
 * 黑名单列表
 */

public class BlackListAdapter extends BaseQuickAdapter<BlackListBean,BaseViewHolder>{
    private Context context;
    private SimpleDateFormat sdf;
    public BlackListAdapter(@LayoutRes int layoutResId, @Nullable List<BlackListBean> data,Context context) {
        super(layoutResId, data);
        this.context = context;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    @Override
    protected void convert(BaseViewHolder helper, BlackListBean item) {
        BlackListBean bean = getData().get(helper.getLayoutPosition());
        Glide.with(context).load(bean.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into((ImageView) helper.getView(R.id.image_icon));
        helper.setText(R.id.textName,bean.getBlackNick());
        helper.setText(R.id.textTime,sdf.format(new Date(bean.getTime()*1000)));
        helper.addOnClickListener(R.id.text_unbind);/**点击事件*/
    }
}
