package com.junliu.liuju.supportlibry.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by liuju on 2018/4/4.
 */

public class SavaCookieInterceptor implements Interceptor {
    private Context context;
    public SavaCookieInterceptor(Context context){
        this.context = context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response != null) {
            List<String> headers = response.headers("Set-Cookie");
            if (headers != null && headers.size()>0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String header:headers){
                    stringBuilder.append(header+";");
                }
                String result = stringBuilder.substring(0,stringBuilder.length()-1);
                if (context != null){
                    SharedPreferences sp = context.getSharedPreferences("cookie",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("cookie",result);
                    editor.commit();
                }
            }
        }
        return response;
    }
}
