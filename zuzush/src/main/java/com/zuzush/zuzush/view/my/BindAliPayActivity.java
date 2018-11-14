package com.zuzush.zuzush.view.my;

import android.content.Intent;
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
import com.zuzush.zuzush.bean.MyWalletBean;
import com.zuzush.zuzush.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/9/20 0020.
 * 绑定支付宝
 */

public class BindAliPayActivity extends BaseActivity implements TextWatcher{
    private MyWalletBean bean;
    @BindView(R.id.text_title) TextView textTitle;
    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etAccount) EditText etAccount;
    @BindView(R.id.textHint) TextView textHint;
    @BindView(R.id.btnBind) Button btnBind;
    private String isBand = "0";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_alipay);
        ButterKnife.bind(this);
        bean = (MyWalletBean) getIntent().getSerializableExtra("isValid");
        init();
    }

    private void init() {
        btnBind.setEnabled(false);
        btnBind.setBackgroundResource(R.drawable.button_background);
        etName.addTextChangedListener(this);
        etAccount.addTextChangedListener(this);
        if (bean != null){
            if (bean.getName() != null && !"".equals(bean.getName())){
                /**用户已经绑定了支付宝*/
                isBand = "0";
                etName.setText(bean.getName());
                etAccount.setText(bean.getAccount());
                textHint.setText("解绑后可重新绑定");
                textTitle.setText("解绑支付宝");
                btnBind.setText("解绑");
                etName.setEnabled(false);
                etAccount.setEnabled(false);
            }else {
                isBand = "1";
            }
        }
    }
    @OnClick({R.id.image_topBack,R.id.btnBind})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.image_topBack:
                this.finish();
                break;

            case R.id.btnBind:
                /**先进性身份验证*/
                Intent intent = new Intent(this,VerifyIdentificationActivity.class);
                intent.putExtra("isBind",isBand);
                intent.putExtra("name",etName.getText().toString());
                intent.putExtra("account",etAccount.getText().toString());
                startActivityForResult(intent,200);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 300){
            String action = data.getStringExtra("bind");
            if ("bind".equals(action))this.finish();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(etName.getText())&& !TextUtils.isEmpty(etAccount.getText())){
            btnBind.setEnabled(true);
            btnBind.setBackgroundResource(R.drawable.button_click_background);
        }else {
            btnBind.setEnabled(false);
            btnBind.setBackgroundResource(R.drawable.button_background);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
