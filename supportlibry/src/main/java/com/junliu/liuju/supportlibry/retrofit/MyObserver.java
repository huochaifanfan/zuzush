package com.junliu.liuju.supportlibry.retrofit;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by liuju on 2018/4/3.
 */

public abstract class MyObserver extends DisposableObserver<String>{
    @Override
    public void onError(@NonNull Throwable e) {
        String error = e.toString();
        String a ="0";
    }
    @Override
    public void onComplete() {

    }
}
