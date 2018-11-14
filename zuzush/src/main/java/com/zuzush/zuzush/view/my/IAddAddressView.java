package com.zuzush.zuzush.view.my;

import com.zuzush.zuzush.module.BaseListener;

/**
 * Created by Administrator on 2017/9/15 0015.
 */
public interface IAddAddressView extends BaseListener{
    String getAddUrl();
    String getName();
    String getTelephone();
    String getArea();
    String getDetailAddress();
    String isDefault();
    String getAddressId();
    String deleteUrl();
}
