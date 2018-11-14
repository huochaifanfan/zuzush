package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.DealBean;
import com.zuzush.zuzush.util.CommonUtil;

import java.util.List;

/**
 * Created by liujun on 2017/9/27 0027.
 * 交易记录
 */

public class DealRecordAdapter extends BaseQuickAdapter<DealBean.DealRecord,BaseViewHolder> {
    private Context context;
    public DealRecordAdapter(@LayoutRes int layoutResId, @Nullable List<DealBean.DealRecord> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DealBean.DealRecord item) {
        DealBean.DealRecord dealRecord = getData().get(helper.getLayoutPosition());
        helper.setText(R.id.textTitle,dealRecord.getTitle());
        helper.setText(R.id.text_status,dealRecord.getDealStatus());
        helper.setText(R.id.text_dealTime,dealRecord.getDealTime());
        if (dealRecord.getAmount()>0){
            helper.setText(R.id.text_amount, "+"+CommonUtil.formatTosepara(dealRecord.getAmount()/100.0));
            helper.setTextColor(R.id.text_amount, ContextCompat.getColor(context,R.color.deal_green_color));
        }else {
            helper.setText(R.id.text_amount, CommonUtil.formatTosepara(dealRecord.getAmount()/100.0));
            helper.setTextColor(R.id.text_amount, ContextCompat.getColor(context,R.color.deal_red_color));
        }
    }
}
