package com.zuzush.zuzush.view.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.SelcetOccupAdapter;
import com.zuzush.zuzush.bean.PersonalDataBean;
import com.zuzush.zuzush.overrideview.MyDecoration;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.view.base.BaseActivity;
import com.zuzush.zuzush.view.my.INormalView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/9/25 0025.
 * 修改性别 修改职业
 */

public class ModifySexActivity extends BaseActivity implements INormalView{
    @BindView(R.id.text_title) TextView textTitle;
    @BindView(R.id.linear_sex) LinearLayout linearSex;
    @BindView(R.id.checkbox) ImageView checkMan;
    @BindView(R.id.checkbox1) ImageView checkWoman;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    private String flag;
    private PersonalDataBean bean;
    private MainPresenter presenter;
    private Map<String,String> map;
    private RecyclerView.LayoutManager layoutManager;
    private MyDecoration myDecoration;
    private List<String> data;
    private Map<String,String> maps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_sex);
        ButterKnife.bind(this);
        flag = getIntent().getStringExtra("flag");
        bean = (PersonalDataBean) getIntent().getSerializableExtra("data");
        init();
    }

    private void init() {
        map = new HashMap<>();
        if (bean== null) bean = new PersonalDataBean();
        map.put("birth_year",bean.getYear());
        map.put("birth_month",bean.getMonth());
        map.put("birth_day",bean.getDay());
        if ("sex".equals(flag)){
            recyclerView.setVisibility(View.GONE);
            map.put("occup",bean.getOccup());
            if ("1".equals(bean.getSex())){
                checkMan.setImageResource(R.drawable.zuzu_open);
                checkWoman.setImageResource(R.drawable.zuzu_close);
            }else {
                checkMan.setImageResource(R.drawable.zuzu_close);
                checkWoman.setImageResource(R.drawable.zuzu_open);
            }
        }else {
            /**选择职业*/
            linearSex.setVisibility(View.GONE);
            textTitle.setText("选择职业");
            map.put("sex",bean.getSex());
            layoutManager = new LinearLayoutManager(this);
            myDecoration = new MyDecoration(this,R.color.divider_color,LinearLayoutManager.HORIZONTAL, CommonUtil.dip2px(this,1),0);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(myDecoration);
            maps = CommonUtil.setOccop();
            data = new ArrayList<>();
            for (String key:maps.keySet()){
                data.add(maps.get(key));
            }
            SelcetOccupAdapter adapter = new SelcetOccupAdapter(R.layout.normal_layout_item,data);
            recyclerView.setAdapter(adapter);
            adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
            adapter.setOnItemClickListener(new OnitemClickListener());
        }
    }

    @OnClick({R.id.image_topBack,R.id.checkbox,R.id.checkbox1})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.image_topBack:
                this.finish();
                break;

            case R.id.checkbox:
                checkMan.setImageResource(R.drawable.zuzu_open);
                checkWoman.setImageResource(R.drawable.zuzu_close);
                map.put("sex","1");
                modifyData();
                break;

            case R.id.checkbox1:
                checkMan.setImageResource(R.drawable.zuzu_close);
                checkWoman.setImageResource(R.drawable.zuzu_open);
                map.put("sex","0");
                modifyData();
                break;
        }
    }
    private void modifyData(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            toast(this,Constants.NETWORK_ERROR);
            return;
        }
        if (presenter == null) presenter = new MainPresenter(this,this);
        presenter.modifyPersonalData(map);
    }
    @Override
    public String getUrl() {
        return Constants.modifyPersonalData;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args.length>0){
            if (args[0] instanceof String && "success".equals(args[0])){
                toast(this,"修改成功");
                this.finish();
            }
        }
    }
    public class OnitemClickListener implements BaseQuickAdapter.OnItemClickListener{
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            String occup = null;
            String value="";
            if (data != null) value = data.get(position);
            for (String key:maps.keySet()){
                String a = key;
                if (value.equals(maps.get(key))) occup = key;
            }
            if (data != null) map.put("occup",occup);
            modifyData();
        }
    }
}
