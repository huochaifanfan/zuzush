package com.junliu.liuju.supportlibry.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by liuju on 2018/4/3.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onSuccess(Object[] args) {}
    public void onFailer(String status, String info, String result) {
        String eror = status;
        String aoo = result;
    }

    public void onJsonError(String result) {}

    public void onError(String error) {}
}
