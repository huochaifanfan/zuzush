package com.zuzush.zuzush.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.MainBottomAdapter;
import com.zuzush.zuzush.bean.MainBean;
import com.zuzush.zuzush.overrideview.MyDecoration;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.view.base.BaseFragment;

import java.util.List;

/**
 * Created by liujun on 2017/9/20 0020.
 */

public class MainBottomFragment extends BaseFragment implements IMainBottomView{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyDecoration myDecoration;
    private MainPresenter presenter;
    private int page=1;
    private int flag = 0;
    private MainBottomAdapter adatper;
    private String type;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_bottom,container,false);
        Bundle args = getArguments();
        type = args!=null?args.getString("type"):"";
        init(view);
        presenter.getMainBottom();
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_bottom);
        layoutManager = new LinearLayoutManager(getActivity());
        myDecoration = new MyDecoration(getActivity(),R.color.divider_color,LinearLayoutManager.HORIZONTAL, CommonUtil.dip2px(getActivity(),1),0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(myDecoration);
        presenter = new MainPresenter(getActivity(),this);
    }

    @Override
    public String getUrl() {
        return Constants.mainRecommend;
    }

    @Override
    public String getPage() {
        return page+"";
    }

    @Override
    public int getFlag() {
        return flag;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args.length>0){
            if (args[0] instanceof List){
                List<MainBean.MainBottomEntity> data = (List<MainBean.MainBottomEntity>) args[0];
                setData(data);
            }
        }
    }

    private void setData(List<MainBean.MainBottomEntity> data) {
        if (!"推荐的".equals(type)) return;
        if (adatper == null){
            adatper = new MainBottomAdapter(data,getActivity());
            recyclerView.setAdapter(adatper);
        }
    }
}
