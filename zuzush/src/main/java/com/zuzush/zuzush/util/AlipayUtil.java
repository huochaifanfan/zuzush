package com.zuzush.zuzush.util;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * Created by liujun on 2017/10/10 0010.
 * 支付宝支付
 */

public class AlipayUtil {
    /**
     * 调用支付宝接口
     * @param info
     */
    public static void startAlipay(String info, final Activity activity){
        final String orderInfo = info;   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo,true);

            }
        };
        /**必须异步调用*/
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
