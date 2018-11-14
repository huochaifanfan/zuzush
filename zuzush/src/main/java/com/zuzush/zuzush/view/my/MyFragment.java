package com.zuzush.zuzush.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.util.UiUtils;
import com.zuzush.zuzush.view.base.BaseFragment;
import com.zuzush.zuzush.view.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/13 0013.
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.text_top) TextView textTop;
    @BindView(R.id.center_name) TextView textName;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my , container , false);
        ButterKnife.bind(this ,view);
        init();
        return view;
    }

    private void init() {
        UiUtils.setParams(getActivity(),textTop,1);
    }
    @OnClick({R.id.image_account,R.id.image_setting,R.id.image_edit,R.id.rel_myPublish,R.id.rel_jiedan,R.id.rel_xiadan,R.id.rel_shoucang,
            R.id.rel_wallet,R.id.rel_red,R.id.rel_zuzu,R.id.rel_zuji,R.id.rel_invite_friend,R.id.rel_help,R.id.rel_xinyong})
    public void onViewClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.image_account:
                intent.setClass(getActivity(),PersonalDataActivity.class);
                startActivity(intent);
                break;

            case R.id.image_setting:
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.image_edit:
                break;

            case R.id.rel_myPublish:
                break;

            case R.id.rel_jiedan:
                break;

            case R.id.rel_xiadan:
                break;

            case R.id.rel_shoucang:
                break;

            case R.id.rel_wallet:
                intent.setClass(getActivity() , MyWalletActivity.class);
                startActivity(intent);
                break;

            case R.id.rel_red:
                break;

            case R.id.rel_zuzu:
                break;

            case R.id.rel_zuji:
                break;

            case R.id.rel_invite_friend:
                break;

            case R.id.rel_help:
                break;

            case R.id.rel_xinyong:
                break;
        }
    }
}
