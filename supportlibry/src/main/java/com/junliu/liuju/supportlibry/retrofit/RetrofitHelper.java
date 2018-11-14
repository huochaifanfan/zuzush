package com.junliu.liuju.supportlibry.retrofit;

import android.content.Context;

import com.junliu.liuju.supportlibry.util.AddCookieInterceptor;
import com.junliu.liuju.supportlibry.util.SavaCookieInterceptor;
import com.junliu.liuju.supportlibry.util.UserAgentInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by liuju on 2018/4/3.
 */

public class RetrofitHelper {
    private Context context;
    private static RetrofitHelper instance;
    private Retrofit retrofit;
    private RetrofitHelper(Context context){
        this.context = context;
        init();
    }

    private void init() {
        retrofit = new Retrofit.Builder().baseUrl("https://www.zhuomn.cn/").
                client(okHttpClient()).addConverterFactory(ScalarsConverterFactory.create()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    public static RetrofitHelper getInstance(Context context){
        if (instance == null){
            synchronized (RetrofitHelper.class){
                if (instance == null) instance = new RetrofitHelper(context);
            }
        }
        return instance;
    }
    public RetrofitService getServer(){
        return retrofit.create(RetrofitService.class);
    }
    private OkHttpClient okHttpClient(){
        /**登录请求用的*/
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new UserAgentInterceptor()).
                addInterceptor(new SavaCookieInterceptor(context)).addInterceptor(new AddCookieInterceptor(context)).build();
        return okHttpClient;
    }
}
