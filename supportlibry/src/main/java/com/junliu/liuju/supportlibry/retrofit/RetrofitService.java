package com.junliu.liuju.supportlibry.retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by liuju on 2018/4/3.
 */

public interface RetrofitService {
    @GET("book/search")
    Observable<String> getSearchBook(@Query("q") String name, @Query("tag") String tag, @Query("start") int start,
                                     @Query("count") int count);
    @GET("index/index")
    Observable<String> getMainData();
    @FormUrlEncoded
    @POST("login/send_captcha?_ajax_request=1")
    Observable<String> getMsgCaptcha(@Field("telephone")String phone);
    @FormUrlEncoded
    @POST("login/index?sms=1")
    Observable<String> login(@Field("telephone") String telephone,@Field("captcha") String captcha);
}
