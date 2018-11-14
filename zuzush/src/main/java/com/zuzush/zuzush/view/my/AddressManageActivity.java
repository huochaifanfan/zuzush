package com.zuzush.zuzush.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.AddressAdapter;
import com.zuzush.zuzush.bean.AddressBean;
import com.zuzush.zuzush.overrideview.MyDecoration;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.view.base.BaseActivity;

import java.util.List;

/**
 * Created by liujun on 2017/9/14 0014.
 */
public class AddressManageActivity extends BaseActivity implements INormalView,AddressAdapter.OnItemClickListener,View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyDecoration decoration;
    private AddressAdapter adapter;
    private MainPresenter presenter;
    private RelativeLayout relAdd;
    private ImageView imageBack;
    private List<AddressBean> data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        decoration = new MyDecoration(this,R.color.divider_color,LinearLayoutManager.HORIZONTAL, CommonUtil.dip2px(this,1),0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        relAdd = (RelativeLayout) findViewById(R.id.rel_add_address);
        relAdd.setOnClickListener(this);
        imageBack = (ImageView) findViewById(R.id.image_topBack);
        imageBack.setOnClickListener(this);
        presenter = new MainPresenter(this ,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    private void getAddress(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            toast(this,Constants.NETWORK_ERROR);
            return;
        }
        cancelable = presenter.getAddress();
    }
    @Override
    public String getUrl() {
        return Constants.getAddress;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args.length>0){
            if (args[0] instanceof List){
                List<AddressBean> data = (List<AddressBean>) args[0];
                this.data = data;
                if (adapter == null){
                    adapter = new AddressAdapter(this , data,this);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter.reflahData(data);
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_add_address:
                Intent intent = new Intent(this , AddAddressActivity.class);
                startActivity(intent);
                break;

            case R.id.image_topBack:
                this.finish();
                break;
        }
    }
    @Override
    public void onEditImageClick(int position) {
        AddressBean bean = null;
        if (data != null) bean = data.get(position);
        Intent intent = new Intent(this , AddAddressActivity.class);
        intent.putExtra("edit","edit");
        intent.putExtra("bean",bean);
        startActivity(intent);
    }
}
