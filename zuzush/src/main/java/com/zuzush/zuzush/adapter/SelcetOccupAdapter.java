package com.zuzush.zuzush.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zuzush.zuzush.R;

import java.util.List;

/**
 * Created by liujun on 2017/9/25 0025.
 * 选择职业
 */

public class SelcetOccupAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public SelcetOccupAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setVisible(R.id.image_info,false);
        helper.setText(R.id.text_name,getData().get(helper.getLayoutPosition()));
    }
}
