package com.zuzush.zuzush.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.util.RegesUtils;
import com.zuzush.zuzush.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/9/21 0021.
 * 身份验证
 */

public class VerifyIdentificationActivity extends BaseActivity implements TextWatcher,IBindView{
    private String isBind;
    @BindView(R.id.textPhone) TextView textPhone;
    @BindView(R.id.etCaptcha)EditText etCaptcha;
    @BindView(R.id.textCaptcha) TextView textCaptcha;
    @BindView(R.id.textIsVoice) TextView textIsVoice;
    @BindView(R.id.btnSure) Button btnSure;
    private TimeCounter timeCounter;
    private MainPresenter presenter;
    private String isVoice = "0";
    private String name;
    private String account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);
        ButterKnife.bind(this);
        isBind = getIntent().getStringExtra("isBind");
        name = getIntent().getStringExtra("name");
        account = getIntent().getStringExtra("account");
        init();
    }

    private void init() {
        btnSure.setEnabled(false);
        btnSure.setBackgroundResource(R.drawable.button_background);
        textPhone.setText(RegesUtils.makePhoneNunToStar(Constants.telephone));
        etCaptcha.addTextChangedListener(this);
        textIsVoice.setText(Html.fromHtml("收不到短信？<font color='#4285F4'>语音验证码</font>"));
    }
    @OnClick({R.id.image_topBack,R.id.textCaptcha,R.id.textIsVoice,R.id.btnSure})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.image_topBack:
                this.finish();
                break;

            case R.id.textCaptcha:
                if (presenter == null) presenter = new MainPresenter(this,this);
                isVoice = "0";
                cancelable = presenter.getBindCaptcha();
                break;

            case R.id.textIsVoice:/**语音验证码*/
                if (presenter == null) presenter = new MainPresenter(this,this);
                isVoice = "1";
                cancelable = presenter.getBindCaptcha();
                break;

            case R.id.btnSure:
                /**绑定或解绑账户*/
                bindAccount();
                break;
        }
    }
    private void bindAccount(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            toast(this,Constants.NETWORK_ERROR);
            return;
        }
       cancelable = presenter.bindAccount();
    }
    @Override
    public String getCaptchaUrl() {
        return Constants.bindCaptcha;
    }

    @Override
    public String isVoice() {
        return isVoice;
    }

    @Override
    public String isBind() {
        return isBind;
    }
    @Override
    public String getCaptcha() {
        return etCaptcha.getText().toString().trim();
    }

    @Override
    public String bindUrl() {
        return Constants.bindAliPay;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args.length>0){
            if (args[0] instanceof String){
                if ("captcha".equals(args[0])) {
                    if (timeCounter == null) timeCounter = new TimeCounter(1000 * 60, 1000);
                    timeCounter.start();
                    toast(this, "验证码发送成功，请注意查收");
                }else if ("bind".equals(args[0])){
                    /**绑定或者解绑账户*/
                    Intent intent = new Intent();
                    intent.putExtra("bind","bind");
                    this.setResult(300,intent);
                    this.finish();
                }
            }
        }
    }

    public class TimeCounter extends CountDownTimer{
        public TimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textCaptcha.setEnabled(false);
            textCaptcha.setTextColor(ContextCompat.getColor(VerifyIdentificationActivity.this,R.color.main_text_color));
            textCaptcha.setText(millisUntilFinished/1000+"S");
        }

        @Override
        public void onFinish() {
            textCaptcha.setEnabled(true);
            textCaptcha.setTextColor(ContextCompat.getColor(VerifyIdentificationActivity.this,R.color.main_blue_color));
            textCaptcha.setText("获取验证码");
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(etCaptcha.getText())){
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
    protected void onDestroy() {
        super.onDestroy();
        if (timeCounter != null) timeCounter.cancel();
    }
}
