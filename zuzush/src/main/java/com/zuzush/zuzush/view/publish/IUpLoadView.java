package com.zuzush.zuzush.view.publish;

import com.zuzush.zuzush.bean.UpLoadBean;
import com.zuzush.zuzush.module.BaseListener;

import java.io.File;

/**
 * Created by Administrator on 2017/8/23 0023.
 */
public interface IUpLoadView extends BaseListener {
    String getUpLoadUrl();
    File getUri();
    void upLoadFinished();
    void loadProcess(UpLoadBean bean);
}
