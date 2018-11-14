package com.zuzush.zuzush.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liujun on 2017/7/30 0030.
 * 快捷登录
 */
public class FastLoginFragment extends BaseFragment {
    @BindView(R.id.text_login) TextView textLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fast_login , container  ,false);
        ButterKnife.bind(this ,view);
        init();
        return view;
    }
    private void init() {
        textLogin.setText(Html.fromHtml("登录代表您已阅读并同意<font color='#11B7F4'>《用户服务协议》</font>"));
    }
}
