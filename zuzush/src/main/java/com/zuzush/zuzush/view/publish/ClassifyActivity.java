package com.zuzush.zuzush.view.publish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.ClassifyAdapter;
import com.zuzush.zuzush.bean.ClassifyBean;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.view.base.BaseActivity;
import com.zuzush.zuzush.view.my.INormalView;

import java.util.List;

/**
 * Created by liujun on 2017/8/24 0024.
 * 物品分类
 */
public class ClassifyActivity extends BaseActivity implements INormalView,ClassifyAdapter.OnitemClickListener{
    private RecyclerView recyclerViewLeft;
    private RecyclerView recyclerViewRight;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager rightLayoutManager;
    private MainPresenter presenter;
    private List<ClassifyBean> data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        init();
    }

    private void init() {
        recyclerViewLeft = (RecyclerView) findViewById(R.id.list_left);
        recyclerViewRight = (RecyclerView) findViewById(R.id.list_right);
        layoutManager = new LinearLayoutManager(this);
        rightLayoutManager = new LinearLayoutManager(this);
        presenter = new MainPresenter(this,this);
        recyclerViewLeft.setLayoutManager(layoutManager);
        recyclerViewRight.setLayoutManager(rightLayoutManager);
       cancelable = presenter.getClassify();
    }

    @Override
    public String getUrl() {
        return Constants.getClassify;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args.length>0){
            if (args[0] instanceof List){
                List<ClassifyBean> data = (List<ClassifyBean>) args[0];
                setData(data);
            }
        }
    }

    public void setData(List<ClassifyBean> data) {
        this.data = data;
        if (data == null) return;
        ClassifyAdapter adapter = new ClassifyAdapter(data,this,this,0,null);
        recyclerViewLeft.setAdapter(adapter);
        onitemClick(0);
    }
    private ClassifyAdapter rightAdapter;
    @Override
    public void onitemClick(int position) {
        if (data == null) return;
        if (rightAdapter == null){
            rightAdapter = new ClassifyAdapter(null,this,this,1,data.get(position).getRightData());
            recyclerViewRight.setAdapter(rightAdapter);
        }else {
            rightAdapter.notifyData(1,data.get(position).getRightData());
        }
    }

    @Override
    public void onRightClick(int position) {
        toast(this,"选择了："+position);
    }
}
