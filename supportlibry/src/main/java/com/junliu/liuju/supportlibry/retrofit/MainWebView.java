package com.junliu.liuju.supportlibry.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.junliu.liuju.supportlibry.R;
import com.junliu.liuju.supportlibry.util.CookieUtils;

/**
 * Created by liuju on 2018/4/8.
 */

public class MainWebView extends BaseActivity {
    private WebView webView;
    private WebSettings settings;
    private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main_webview);
        url = getIntent().getStringExtra("url");
        webView = (WebView) findViewById(R.id.webView);
        settings = webView.getSettings();
        settings.setUserAgentString("#imt-app-android-29-Redmi Note 4X-6.0.1#");
        settings.setUseWideViewPort(true);
        settings.setSaveFormData(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        CookieUtils.getCookie(this , url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                CookieUtils.getCookie(MainWebView.this , url);
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
}
