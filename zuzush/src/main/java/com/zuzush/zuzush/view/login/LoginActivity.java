package com.zuzush.zuzush.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.tauth.Tencent;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.application.MainApplication;
import com.zuzush.zuzush.view.base.BaseActivity;
import com.zuzush.zuzush.view.base.BaseUiListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/7/28 0028.
 * 登录页面
 */
public class LoginActivity extends BaseActivity{
    private BaseUiListener mListener;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    private FragmentPagerAdapter adapter;
    private String[] titles = {"快捷登录","手机登录"};
    private List<Fragment> fragments;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        fragments = new ArrayList<>();
        FastLoginFragment fastLoginFragment = new FastLoginFragment();
        PhoneLoginFragment phoneLoginFragment = new PhoneLoginFragment();
        fragments.add(fastLoginFragment);
        fragments.add(phoneLoginFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    @OnClick({R.id.image_cancel})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.image_cancel:
                this.finish();
                break;
        }
    }
    /**接受QQ的回调必须实现该方法*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,mListener);
    }
    private void qqLogin(){
        mListener = new BaseUiListener(this);
        if (!MainApplication.mTencent.isSessionValid()){
            /**all表示获取所有的权限*/
            MainApplication.mTencent.login(this,"all",mListener);
        }
    }
    private void weiChatLogin(){

    }
}
