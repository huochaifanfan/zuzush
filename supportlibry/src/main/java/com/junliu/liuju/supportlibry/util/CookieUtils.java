package com.junliu.liuju.supportlibry.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.util.Set;

/**
 * @author Administrator
 */
public class CookieUtils {
	/**
	 * 设置cookie
	 * @param context
	 * @param url
	 */
	public static void getCookie(Context context ,String url){
		SharedPreferences sp = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
		String cookies = sp.getString("cookie","");
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		//加载url之前先设置cookie
		cookieManager.setCookie(url, cookies);
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			CookieSyncManager.getInstance().sync();
		} else {
			CookieManager.getInstance().flush();
		}
	}
}
