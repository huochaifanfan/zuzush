package com.zuzush.zuzush.view.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.PersonalDataBean;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.view.base.BaseActivity;
import com.zuzush.zuzush.view.my.INormalView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/9/25 0025.
 * 个人简介
 */

public class PersonalIntroduceActivity extends BaseActivity implements TextWatcher,INormalView{
    @BindView(R.id.btnSave) Button btnSave;
    @BindView(R.id.et_introduce) EditText etIndroduce;
    @BindView(R.id.text_length) TextView textLength;
    private PersonalDataBean bean;
    private Map<String,String> map;
    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(this);
        bean = (PersonalDataBean) getIntent().getSerializableExtra("data");
        init();
    }

    private void init() {
        btnSave.setEnabled(false);
        btnSave.setBackgroundResource(R.drawable.button_background);
        etIndroduce.addTextChangedListener(this);
        map = new HashMap<>();
        if (bean != null){
            map.put("birth_year",bean.getYear());
            map.put("birth_month",bean.getMonth());
            map.put("birth_day",bean.getDay());
            map.put("sex",bean.getSex());
            map.put("occup",bean.getOccup());
            etIndroduce.setText(bean.getIntroduce());
            if (!TextUtils.isEmpty(etIndroduce.getText())) {
                etIndroduce.setSelection(etIndroduce.getText().length());
                textLength.setText(etIndroduce.getText().length()+"/200");
            }
        }
    }

    @OnClick({R.id.image_topBack,R.id.btnSave})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.image_topBack:
                this.finish();
                break;

            case R.id.btnSave:
                if (presenter == null) presenter = new MainPresenter(this,this);
                map.put("introduce",etIndroduce.getText().toString());
                cancelable = presenter.modifyPersonalData(map);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(etIndroduce.getText())){
            btnSave.setEnabled(true);
            btnSave.setBackgroundResource(R.drawable.button_click_background);
            textLength.setText(etIndroduce.getText().length()+"/200");
        }else {
            btnSave.setEnabled(false);
            btnSave.setBackgroundResource(R.drawable.button_background);
            textLength.setText("0/200");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

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
}
