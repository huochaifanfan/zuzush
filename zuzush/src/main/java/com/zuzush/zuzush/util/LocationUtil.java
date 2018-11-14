package com.zuzush.zuzush.util;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zuzush.zuzush.bean.LocationBean;

/**
 * Created by liujun on 2017/8/14 0014.
 * 获取当前定位
 */
public class LocationUtil {
    private volatile static LocationUtil INSTANCE = null;
    private Context context;
    private AMapLocationClient mLocationClient = null;
    private LocationResult locationResult;
    private AMapLocationClientOption mLocationOption = null;

    private LocationUtil(Context context,LocationResult locationResult){
        this.context = context;
        this.locationResult = locationResult;
    }
    public static LocationUtil getInstance(Context context,LocationResult locationResult){
        if (INSTANCE == null){
            /**线程同步 保证安全*/
            synchronized (LocationUtil.class){
                if (INSTANCE == null){
                    INSTANCE = new LocationUtil(context,locationResult);
                }
            }
        }
        return INSTANCE;
    }

    /***
     * 开始定位
     */
    public void startLocation(){
        mLocationClient = new AMapLocationClient(context);
        mLocationClient.setLocationListener(new MyLocationListener());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        /**获取单次定位结果*/
        mLocationOption.setOnceLocation(true);
        /**设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
         如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false*/
        mLocationOption.setOnceLocationLatest(true);
        /**设置定位间隔*/
        mLocationOption.setInterval(1000*3);
        /**设置返回地址描述*/
        mLocationOption.setNeedAddress(true);
        /**设置超时时间*/
        mLocationOption.setHttpTimeOut(1000*10);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }
    public interface LocationResult{
        void getLocation(LocationBean bean);
    }

    /**
     * 定位监听
     */
    public class MyLocationListener implements AMapLocationListener{
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0){
                /**定位成功*/
                LocationBean bean = new LocationBean();
                bean.setAddress(aMapLocation.getAddress());
                bean.setCountry(aMapLocation.getCountry());
                bean.setProvince(aMapLocation.getProvince());
                bean.setCity(aMapLocation.getCity());
                bean.setDistrict(aMapLocation.getDistrict());
                bean.setStreet(aMapLocation.getStreet());
                bean.setStreetNum(aMapLocation.getStreetNum());
                bean.setCityCode(aMapLocation.getCityCode());
                bean.setAdCode(aMapLocation.getAdCode());
                if (locationResult != null) locationResult.getLocation(bean);
            }
        }
    }

    /**
     * 停止定位
     */
    public void stopLocation(){
        if (mLocationClient != null){
            mLocationClient.startLocation();
            mLocationClient.onDestroy();
        }
    }
}
