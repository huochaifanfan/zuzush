package com.junliu.liuju.supportlibry.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuju on 2018/4/4.
 */

public class UserAgentInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("User-Agent","#imt-app-android-29-Redmi Note 4X-6.0.1#");
        return chain.proceed(builder.build());
    }
}
