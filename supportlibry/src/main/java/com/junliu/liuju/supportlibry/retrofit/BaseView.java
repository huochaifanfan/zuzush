package com.junliu.liuju.supportlibry.retrofit;

/**
 * Created by liuju on 2018/4/3.
 */

public interface BaseView<T> extends View {
    void onSuccess(T...args);
    void onFailer(String status,String info,String result);
    void onJsonError(String result);
    void onError(String error);
}
