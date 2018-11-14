package com.zuzush.zuzush.view.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.TradeFragmentPagerAdapter;
import com.zuzush.zuzush.bean.DealBean;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/9/26 0026.
 * 交易记录
 */

public class DealRecordActivity extends BaseActivity implements INormalView{
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.text_tab) TextView textTab;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private MainPresenter presenter;
    private List<String> topTitle;
    private List<Fragment> fragments;
    private List<DealBean.RecordType> recordType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_record);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        presenter = new MainPresenter(this,this);
        topTitle = new ArrayList<>();
        fragments = new ArrayList<>();
       cancelable = presenter.getDealRecord(0);
    }

    @Override
    public String getUrl() {
        return Constants.dealRecord;
    }
    @OnClick({R.id.image_topBack})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.image_topBack:
                this.finish();
                break;
        }
    }
    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof DealBean) {
                DealBean bean = (DealBean) args[0];
                List<DealBean.RecordType> types = bean.getRecordTypes();
                if (types == null) return;
                /**设置标题栏的宽度*/
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textTab.getLayoutParams();
                params.width = CommonUtil.getScreenInfo(this)[0] - (CommonUtil.dip2px(this,63))*types.size();
                textTab.setLayoutParams(params);
                this.recordType = types;
                if (types != null && types.size()>0){
                    for (int i =0;i<types.size();i++){
                        topTitle.add(types.get(i).getValue());
                    }
                    initFragments();
                }
            }
        }
    }

    private void initFragments() {
        if (topTitle != null && topTitle.size()>0){
            for (int i = 0;i<topTitle.size();i++){
                Bundle bundle = new Bundle();
                bundle.putString("type" , recordType.get(i).getKey());
                DealRecordFragment fragment = new DealRecordFragment();
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
            TradeFragmentPagerAdapter adapter = new TradeFragmentPagerAdapter(getSupportFragmentManager(),fragments,topTitle);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
