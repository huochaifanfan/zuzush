package com.zuzush.zuzush.view.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.BlackListAdapter;
import com.zuzush.zuzush.bean.BlackListBean;
import com.zuzush.zuzush.overrideview.MyDecoration;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.view.base.BaseActivity;
import com.zuzush.zuzush.view.my.INormalView;

import java.util.List;

/**
 * Created by liujun on 2017/9/21 0021.
 * 黑名单
 */

public class BlackListActivity extends BaseActivity implements OnClickListener,IBlackListView{
    private RecyclerView recyclerView;
    private ImageView imageBack;
    private BlackListAdapter adapter;
    private MainPresenter presenter;
    private RecyclerView.LayoutManager layoutManager;
    private MyDecoration myDecoration;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        imageBack = (ImageView) findViewById(R.id.image_topBack);
        imageBack.setOnClickListener(this);
        presenter = new MainPresenter(this,this);
        layoutManager = new LinearLayoutManager(this);
        myDecoration = new MyDecoration(this,R.color.divider_color,LinearLayoutManager.HORIZONTAL, CommonUtil.dip2px(this,1),0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(myDecoration);
        getBlacklist(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_topBack:
                this.finish();
                break;
        }
    }
    private void getBlacklist(int flag){
        if (!NetworkUtil.isNetWorkConnected(this)){
            toast(this,Constants.NETWORK_ERROR);
            return;
        }
        if (flag == 0){
            cancelable = presenter.getBlackList();
        }else {
            cancelable = presenter.unbindBlack();
        }
    }
    private void setData(final List<BlackListBean> data){
        if (data != null && data.size()>0){
            if (adapter == null){
                adapter = new BlackListAdapter(R.layout.black_list_item,data,this);
                recyclerView.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    /**解除绑定的接口*/
                    id = data.get(position).getAutoId();
                    BlackListActivity.this.position = position;
                    getBlacklist(1);
                }
            });
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        }
    }
    private int position;
    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args.length>0){
            if (args[0] instanceof List) setData((List<BlackListBean>) args[0]);
            if (args[0] instanceof String && "unbind".equals(args[0])){
                toast(this,"黑名单解除成功");
                adapter.getData().remove(position);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public String getBlackUrl() {
        return Constants.blackList;
    }

    @Override
    public String unbindBlackUrl() {
        return Constants.unbindBlack;
    }

    @Override
    public String id() {
        return id;
    }
}
