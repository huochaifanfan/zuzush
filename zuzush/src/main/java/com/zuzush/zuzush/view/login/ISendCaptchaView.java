package com.zuzush.zuzush.view.login;

import com.zuzush.zuzush.module.BaseListener;

/**
 * Created by liujun on 2017/7/30 0030.
 */
public interface ISendCaptchaView extends BaseListener{
    String getPhone();
    String getCaptcha();
    String isVoice();
    String sendUrl();
    String loginUrl();
    String getLoginCaptcha();
}
