package com.zuzush.zuzush.view.setting;

import com.zuzush.zuzush.module.BaseListener;

/**
 * Created by Administrator on 2017/9/22 0022.
 */

public interface IBlackListView extends BaseListener {
    String getBlackUrl();
    String unbindBlackUrl();
    String id();
}
