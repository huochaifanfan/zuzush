package com.zuzush.zuzush.view.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/9/20 0020.
 * 提现
 */

public class DrawCashActivity extends BaseActivity implements TextWatcher ,IModifyNickView{
    @BindView(R.id.text_name) TextView textName;
    @BindView(R.id.text_account) TextView textAccount;
    @BindView(R.id.et_money) EditText etMoney;
    @BindView(R.id.text_left) TextView textLeft;
    @BindView(R.id.btnSure) Button btnSure;
    private String name;
    private String account;
    private int money;
    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawcash);
        ButterKnife.bind(this);
        name = getIntent().getStringExtra("name");
        account = getIntent().getStringExtra("account");
        money = getIntent().getIntExtra("money",0);
        init();
    }

    private void init() {
        btnSure.setEnabled(false);
        btnSure.setBackgroundResource(R.drawable.button_background);
        etMoney.addTextChangedListener(this);
        textName.setText(name);
        textAccount.setText(account);
        etMoney.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);/**只能输入数字和小数点*/
        textLeft.setText(Html.fromHtml("余额"+ CommonUtil.formatTosepara(money/100.0)+"，<font color='#575a62'>全部体现</font>"));
    }

    @OnClick({R.id.image_topBack,R.id.btnSure,R.id.text_left})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.image_topBack:
                this.finish();
                break;

            case R.id.btnSure:
                if (money == 0){
                    toast(this,"暂无可提现金额");
                    return;
                }
                if (presenter == null) presenter = new MainPresenter(this,this);
                drawCash();
                break;

            case R.id.text_left:
                if (money == 0){
                    toast(this,"暂无可提现金额");
                    return;
                }
                if (money %100 == 0){
                    etMoney.setText(money/100+"");
                }else {
                    etMoney.setText(money/100.0+"");
                }
                break;
        }
    }
    private void drawCash(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            toast(this,Constants.NETWORK_ERROR);
            return;
        }
        cancelable = presenter.drawCash();
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(etMoney.getText())){
            btnSure.setEnabled(true);
            btnSure.setBackgroundResource(R.drawable.button_click_background);
        }else {
            btnSure.setEnabled(false);
            btnSure.setBackgroundResource(R.drawable.button_background);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public String getUrl() {
        return Constants.drawCash;
    }

    @Override
    public String getNickName() {
        /**提现金额*/
        return etMoney.getText().toString();
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof String && "cash".equals(args[0])){
                toast(this,"提现成功");
                this.finish();
            }
        }
    }
}
