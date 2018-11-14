package com.zuzush.zuzush.view.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.DealRecordAdapter;
import com.zuzush.zuzush.bean.DealBean;
import com.zuzush.zuzush.overrideview.MyDecoration;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.view.base.BaseFragment;

import java.util.List;

/**
 * Created by liujun on 2017/9/26 0026.
 * 交易记录
 */

public class DealRecordFragment extends BaseFragment implements INormalView{
    private RecyclerView recyclerView;
    private DealRecordAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MyDecoration myDecoration;
    private String type;
    private MainPresenter presenter;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deal_record,container,false);
        Bundle args = getArguments();
        type = args!=null?args.getString("type"):"";
        init(view);
        return view;
    }

    private void init(View view) {
        presenter = new MainPresenter(getActivity(),this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        myDecoration = new MyDecoration(getActivity(),R.color.divider_color,LinearLayoutManager.HORIZONTAL, CommonUtil.dip2px(getActivity(),1),0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(myDecoration);
        getDealRecord(0);
    }
    private void getDealRecord(int flag){
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            toast(getActivity(),Constants.NETWORK_ERROR);
            return;
        }
        cancelable = presenter.getDealRecord(flag);
    }
    @Override
    public String getUrl() {
        return Constants.dealRecord+"?type="+type+"&page="+page;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args.length>2){
            if (args[0] instanceof DealBean && args[1] instanceof Integer){
                DealBean bean = (DealBean) args[0];
                int flag = (int) args[1];
                if (bean == null) return;
                setData(bean.getDealData(),flag);
            }
        }
    }

    private void setData(List<DealBean.DealRecord> dealData, int flag) {
        if (dealData != null && dealData.size()>0){
            if (adapter == null){
                adapter = new DealRecordAdapter(R.layout.dealrecord_item,dealData,getActivity());
                recyclerView.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }
        }
    }
}
