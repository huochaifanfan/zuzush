package com.zuzush.zuzush.view.publish;

import com.zuzush.zuzush.module.BaseListener;

/**
 * Created by Administrator on 2017/8/24 0024.
 */
public interface IClassifyView extends BaseListener {
    void getClassifySuccess();
    String getClassifyUrl();
    String getId();
    String getIsmain();
    boolean isFirst();
}
