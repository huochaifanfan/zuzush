package com.zuzush.zuzush.view.my;

import com.zuzush.zuzush.module.BaseListener;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public interface IBindView extends BaseListener {
    String getCaptchaUrl();
    String isVoice();
    String isBind();
    String getCaptcha();
    String bindUrl();
    String getName();
    String getAccount();
}
