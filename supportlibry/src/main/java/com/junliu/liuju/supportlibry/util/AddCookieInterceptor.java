package com.junliu.liuju.supportlibry.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuju on 2018/4/4.
 */

public class AddCookieInterceptor implements Interceptor {
    private Context context;
    public AddCookieInterceptor(Context context){
        this.context = context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (context != null){
            SharedPreferences sp = context.getSharedPreferences("cookie",Context.MODE_PRIVATE);
            String cookies = sp.getString("cookie","");
            builder.addHeader("Cookie",cookies);
        }
        return chain.proceed(builder.build());
    }
}
