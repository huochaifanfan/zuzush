package com.zuzush.zuzush.module;

import com.zuzush.zuzush.bean.BaseBean;

/**
 * Created by Administrator on 2017/8/5 0005.
 */
public interface BaseListener<T> {
    void analysisFailer(String info);
    void httpError(String error);
    void getDataFailer(BaseBean bean);
    void getDataSuccess(T... args);
}
