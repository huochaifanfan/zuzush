package com.zuzush.zuzush.module.main;

import com.zuzush.zuzush.bean.UpLoadBean;
import com.zuzush.zuzush.module.BaseListener;

/**
 * Created by Administrator on 2017/8/23 0023.
 */
public interface UpLoadListener extends BaseListener {
    void upLoadFinished();
    void loadProcess(UpLoadBean bean);
}
