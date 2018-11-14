package com.junliu.liuju.supportlibry.retrofit;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuju on 2018/4/3.
 */

public class MainPresenter implements Presenter{
    private DataManager dataManager;
    private Context context;
    private BaseView baseView;
    private CompositeDisposable compositeDisposable;
    public MainPresenter (Context context){
        this.context = context;
    }
    @Override
    public void onCreat() {
        dataManager = new DataManager(context);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStop() {
    }
    @Override
    public void attachView(View view) {
        baseView = (BaseView) view;
    }

    @Override
    public void onDestory() {
        baseView = null;
        compositeDisposable.clear();/**解除订阅关系*/
    }
    public void getBookSearch(String name,String tag,int start,int count){
        compositeDisposable.add(dataManager.getBookSearch(name,tag,start,count).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new MyObserver() {
            @Override
            public void onNext(@NonNull String result) {
                /**请求成功*/
                if (baseView != null)baseView.onSuccess(result);
            }
        }));
    }
    public void getMainData(){
        compositeDisposable.add(dataManager.getMainData().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new MyObserver() {
            @Override
            public void onNext(@NonNull String s) {

                if (baseView != null) baseView.onSuccess(s,"main");
            }
        }));
    }
    public void getMsgCaptcha(String telephone){
        compositeDisposable.add(dataManager.getMsgCaptcha(telephone).subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread()).subscribeWith(new MyObserver() {
            @Override
            public void onNext(@NonNull String s) {
                if (baseView != null) baseView.onSuccess(s,"msg");
            }
        }));
    }
    public void login(String phone,String captcha){
        compositeDisposable.add(dataManager.login(phone,captcha).subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread()).subscribeWith(new MyObserver() {
            @Override
            public void onNext(@NonNull String s) {
                if (baseView != null) baseView.onSuccess(s,"login");
            }
        }));
    }
}
