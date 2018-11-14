package com.zuzush.zuzush.view.publish;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.overrideview.CustomerProgress;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.view.base.BaseActivity;

/**
 * Created by liujun on 2017/8/15 0015.
 * 发布技巧类webview
 */
public class PublishWebView extends BaseActivity implements View.OnClickListener{
    private WebView webView;
    private WebSettings settings;
    private TextView textTitle;
    private ImageView imageBack;
    private String url;
    private String title;
    private CustomerProgress progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_webview);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webView);
        textTitle = (TextView) findViewById(R.id.text_title);
        imageBack = (ImageView) findViewById(R.id.image_topBack);
        progressBar = (CustomerProgress) findViewById(R.id.progress);
        imageBack.setOnClickListener(this);
        settings = webView.getSettings();
        settings.setUserAgentString(Constants.userAgent);
        settings.setUseWideViewPort(true);
        settings.setSaveFormData(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        textTitle.setText(title);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.loadUrl(url);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_topBack:
                if (webView != null && webView.canGoBack()){
                    webView.goBack();
                }else {
                    this.finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (webView != null && webView.canGoBack()){
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
