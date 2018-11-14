package com.zuzush.zuzush.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.BaseBean;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.util.RegesUtils;
import com.zuzush.zuzush.view.base.BaseFragment;
import com.zuzush.zuzush.view.publish.PublishMainActivity;

import org.xutils.common.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/7/30 0030.
 * 手机号码登录
 */
public class PhoneLoginFragment extends BaseFragment implements TextWatcher,ISendCaptchaView{
    @BindView(R.id.text_tip) TextView textTip;
    @BindView(R.id.text_voiceCapther) TextView voiceCapther;
    @BindView(R.id.et_phone) EditText etPhone;
    @BindView(R.id.et_captcha) EditText etCaptcha;
    @BindView(R.id.tv_send) TextView tvSend;
    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.phone_cancel) ImageView phoneCancel;
    @BindView(R.id.captcha_cancel) ImageView captchaCancel;
    private TimeCount timeCount;
    private MainPresenter sendPresenter;
    private String isVoice;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_login,container,false);
        ButterKnife.bind(this , view);
        init();
        return view;
    }
    private void init() {
        textTip.setText(Html.fromHtml("温馨提示：未注册的手机号将在登录时自动注册且代表您已同意<font color='#11B7F4'>《用户服务协议》</font>"));
        voiceCapther.setText(Html.fromHtml("收不到短信？<font color='#4285F4'>语音验证码</font>"));
        etPhone.addTextChangedListener(this);
        etCaptcha.addTextChangedListener(this);
        timeCount = new TimeCount(60*1000 , 1000);
        sendPresenter = new MainPresenter(getActivity(),this);
        btnLogin.setEnabled(false);
        btnLogin.setBackgroundResource(R.drawable.button_background);
    }
    @OnClick({R.id.text_tip,R.id.text_voiceCapther,R.id.tv_send,R.id.btn_login,R.id.phone_cancel,R.id.captcha_cancel})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.text_tip:
                break;

            case R.id.text_voiceCapther:
                isVoice = "1";
                sendCaptcha(0);
                break;

            case R.id.tv_send:
                isVoice = "0";
                sendCaptcha(0);
                break;

            case R.id.btn_login:
                if (!NetworkUtil.isNetWorkConnected(getActivity())){
                    CommonUtil.toastTip(getActivity(),Constants.NETWORK_ERROR);
                    return;
                }
                cancelable = sendPresenter.login();
                break;

            case R.id.phone_cancel:
                etPhone.setText(null);
                break;

            case R.id.captcha_cancel:
                etCaptcha.setText(null);
                break;
        }
    }
    private void sendCaptcha(int flag){
        if (TextUtils.isEmpty(etPhone.getText())){
            CommonUtil.toastTip(getActivity(),"请输入正确的手机号码");
            return;
        }
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            CommonUtil.toastTip(getActivity(),Constants.NETWORK_ERROR);
            return;
        }
        if (!RegesUtils.IsPhone(etPhone.getText().toString())){
            CommonUtil.toastTip(getActivity(),"请输入正确的手机号码");
            return;
        }
        cancelable = sendPresenter.sendCaptcha(flag);
    }
    @Override
    public void getDataFailer(BaseBean bean) {
        if (bean != null) {
            if ("LOGIN_IMGCAPTCHA".equals(bean.getStatus())){
                /**输入图形验证码*/
                showMsgDialog();
            }else if ("450".equals(bean.getStatus())){
                /**图形验证码输入错误*/
                if (textToast != null && msgDialog != null && msgDialog.isShowing()){
                    textToast.setText("验证码输入错误，请重新输入");
                    textToast.setTextColor(ContextCompat.getColor(getActivity() , R.color.error_color));
                    imageClick();
                }else if (textToast != null){
                    textToast.setText("您的操作过于频繁，请先输入验证码");
                    textToast.setTextColor(ContextCompat.getColor(getActivity() , R.color.main_text_color));
                }
                if (msgDialog != null && !msgDialog.isShowing()) showMsgDialog();
            }else {
                toast(getActivity(),bean.getInfo());
            }
        }
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length >0){
            if (args[0] instanceof String && "success".equals(args[0])){
                /**登录成功*/
                toast(getActivity(),"登陆成功");
                Intent intent = new Intent(getActivity() , PublishMainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }else if (args[0] instanceof String && "200".equals(args[0])){
                /**发送验证码成功*/
                toast(getActivity(),"验证码发送成功，请注意查收！");
                if (msgDialog != null && msgDialog.isShowing()) msgDialog.cancel();
                if (timeCount != null) timeCount.start();
            }
        }
    }
    /**点击弹框确认按钮时候 带上图片验证码 直接请求接口*/
    @Override
    protected void msgDialogClick() {
        sendCaptcha(1);
    }
    @Override
    public String getPhone() {
        return etPhone.getText().toString();
    }
    @Override
    public String getCaptcha() {
        /**这个获取的是图片验证码*/
        return capther;
    }
    @Override
    public String isVoice() {
        return isVoice;
    }
    @Override
    public String sendUrl() {
       return Constants.sendCapcha;
    }

    @Override
    public String loginUrl() {
        return Constants.login;
    }
    @Override
    public String getLoginCaptcha() {
        return etCaptcha.getText().toString();
    }
    public class TimeCount extends CountDownTimer{
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            tvSend.setEnabled(false);
            tvSend.setTextColor(ContextCompat.getColor(getActivity() , R.color.main_text_color));
            tvSend.setText(millisUntilFinished/1000+"S");
        }
        @Override
        public void onFinish() {
            tvSend.setEnabled(true);
            tvSend.setTextColor(ContextCompat.getColor(getActivity(),R.color.main_blue_color));
            tvSend.setText("获取验证码");
        }
    }
    @Override
    public void onDestroy() {
        if (timeCount != null) timeCount.cancel();
        super.onDestroy();
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(etPhone.getText()) && !TextUtils.isEmpty(etCaptcha.getText())){
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.button_click_background);
        }else {
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundResource(R.drawable.button_background);
        }
        if (!TextUtils.isEmpty(etPhone.getText())){
            phoneCancel.setVisibility(View.VISIBLE);
        }else {
            phoneCancel.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(etCaptcha.getText())){
            captchaCancel.setVisibility(View.VISIBLE);
        }else {
            captchaCancel.setVisibility(View.GONE);
        }
    }
    @Override
    public void afterTextChanged(Editable s) {}
}
