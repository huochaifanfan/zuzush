package com.junliu.liuju.supportlibry.retrofit;

import android.content.Context;

import io.reactivex.Observable;

/**
 * Created by liuju on 2018/4/3.
 */

public class DataManager {
    private RetrofitService mRetrofitService;
    public DataManager(Context context){
        mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<String> getBookSearch(String name,String tag,int start,int count){
        return mRetrofitService.getSearchBook(name,tag,start,count);
    }

    /**
     * 首页数据
     * @return
     */
    public Observable<String> getMainData(){
        return mRetrofitService.getMainData();
    }

    /**
     * 获取验证码
     * @param telephone
     * @return
     */
    public Observable<String> getMsgCaptcha(String telephone){
        return mRetrofitService.getMsgCaptcha(telephone);
    }
    public Observable<String> login(String phone,String captcha){
        return mRetrofitService.login(phone,captcha);
    }
}
