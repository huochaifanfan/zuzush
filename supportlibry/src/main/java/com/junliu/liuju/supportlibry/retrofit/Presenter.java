package com.junliu.liuju.supportlibry.retrofit;

/**
 * Created by liuju on 2018/4/3.
 */

public interface Presenter {
    void onCreat();
    void onStop();
    void attachView(View view);
    void onDestory();
}
