package com.zuzush.zuzush.util;

import com.zuzush.zuzush.module.BaseListener;

import org.xutils.common.Callback;

/**
 * Created by liujun on 2017/7/30 0030.
 * 请求回调类
 */
public abstract class CallBack<T> implements Callback.CommonCallback<T> {
    private BaseListener listener;
    public CallBack(BaseListener listener) {
        this.listener = listener;
    }
    @Override
    public void onCancelled(CancelledException cex) {
        /**请求取消*/
    }
    @Override
    public void onFinished() {
        /**请求完成*/
    }
    @Override
    public void onError(Throwable throwable, boolean b) {
        if (listener != null) listener.httpError(throwable.toString());
    }
}
