package com.zuzush.zuzush.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.NormalAdapter;
import com.zuzush.zuzush.overrideview.MyDecoration;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity implements NormalAdapter.OnItemClickListener,View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyDecoration myDecoration;
    private NormalAdapter adapter;
    private ImageView imageBack;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        myDecoration = new MyDecoration(this,R.color.divider_color,LinearLayoutManager.HORIZONTAL, CommonUtil.dip2px(this,1),0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(myDecoration);
        imageBack = (ImageView) findViewById(R.id.image_topBack);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        imageBack.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        if (adapter == null){
            adapter = new NormalAdapter(this,initData(),this);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    private List<String> initData() {
        List<String> data = new ArrayList<>();
        data.add("个人资料");
        data.add("信用管理");
        data.add("屏蔽名单");
        data.add("消息通知");
        data.add("清除缓存");
        data.add("关于我们");
        return data;
    }

    @Override
    public void onClick(int position) {
        switch (position){
            case 0:/**个人资料*/
                break;

            case 1:/**信用管理*/
                break;

            case 2:/**屏蔽名单*/
                Intent intent = new Intent(this,BlackListActivity.class);
                startActivity(intent);
                break;

            case 3:/**消息通知*/
                break;

            case 4:/**清除缓存*/
                break;

            case 5:/**关于我们*/
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_topBack:
                this.finish();
                break;

            case R.id.btn_logout:
                /**退出登录接口*/
                break;
        }
    }
}
