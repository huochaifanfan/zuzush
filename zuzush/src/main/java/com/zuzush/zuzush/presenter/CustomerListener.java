package com.zuzush.zuzush.presenter;

import com.zuzush.zuzush.bean.BaseBean;
import com.zuzush.zuzush.module.BaseListener;

/**
 * Created by Administrator on 2017/9/4 0004.
 */
public class CustomerListener implements BaseListener {
    private BaseListener listener;
    public CustomerListener(BaseListener listener){
        this.listener = listener;
    }
    @Override
    public void analysisFailer(String info) {
        if (listener != null) listener.analysisFailer(info);
    }
    @Override
    public void httpError(String error) {
        if (listener != null) listener.httpError(error);
    }
    @Override
    public void getDataFailer(BaseBean bean) {
        if (listener != null) listener.getDataFailer(bean);
    }
    @Override
    public void getDataSuccess(Object[] args) {
        if (listener != null) listener.getDataSuccess(args);
    }
}
