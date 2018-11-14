package com.junliu.liuju.supportlibry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.junliu.liuju.supportlibry.greendao.GreenDaoActivity;
import com.junliu.liuju.supportlibry.retrofit.BaseActivity;
import com.junliu.liuju.supportlibry.retrofit.BaseView;
import com.junliu.liuju.supportlibry.retrofit.MainPresenter;
import com.junliu.liuju.supportlibry.retrofit.MainWebView;
import com.junliu.liuju.supportlibry.video.VideoActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements BaseView{
    private ImageView imageView;
    private Bitmap bitmap;
    private MainPresenter presenter;
    private EditText etMsg;
    private String msg;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200){
                imageView.setImageBitmap(bitmap);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        Button btnClick = (Button) findViewById(R.id.btn_click);
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        Button btnMain = (Button) findViewById(R.id.btn_main);
        Button btnWeb = (Button) findViewById(R.id.btn_web);
        Button btnVideo = (Button) findViewById(R.id.btn_video);
        Button btnGreenDao = (Button) findViewById(R.id.btnGreenDao);
        etMsg = (EditText) findViewById(R.id.et_msg);
        etMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(etMsg.getText())){
                    msg = etMsg.getText().toString().trim();
                }
            }
        });
        presenter = new MainPresenter(this);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                presenter.getBookSearch("金瓶梅",null,0,1);
//                presenter.getMainData();
                presenter.getMsgCaptcha("15000000022");
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login("15000000022",msg);
            }
        });
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getMainData();
            }
        });
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainWebView.class);
                intent.putExtra("url","https://m.zhuomn.cn/special/friend/index.html");
                startActivity(intent);
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , VideoActivity.class);
                startActivity(intent);
            }
        });
        btnGreenDao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GreenDaoActivity.class);
                startActivity(intent);
            }
        });
        presenter.onCreat();
        presenter.attachView(this);
    }
    private void getPicture(String url) throws Exception {
        URL getUrl = new URL(url);
        HttpURLConnection httpsURLConnection = (HttpURLConnection) getUrl.openConnection();
        httpsURLConnection.setConnectTimeout(15*1000);
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.connect();
        if (httpsURLConnection.getResponseCode() == 200){
            Map<String, List<String>> headerFields = httpsURLConnection.getHeaderFields();
            if (headerFields != null && headerFields.size()>0){
                List<String> cookies = headerFields.get("Set-Cookie");
                if (cookies != null && cookies.size()>0){
                    for (int i = 0;i<cookies.size();i++){
                        String cookieResult = cookies.get(i);
                        String a=cookieResult;
                    }
                }
            }
            InputStream inputStream = httpsURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer))!= -1){
                byteArrayOutputStream.write(buffer,0,len);
                byteArrayOutputStream.flush();
            }
            String result = byteArrayOutputStream.toString("utf-8");
            handler.sendEmptyMessage(200);
        }
    }

    @Override
    public void onSuccess(Object[] args) {
        super.onSuccess(args);
        if (args != null && args.length ==2){
            String result = (String) args[0];
            String msg = (String) args[1];
            if ("msg".equals(msg)){
                Toast.makeText(this ,"验证码发送成功，请注意查收",Toast.LENGTH_SHORT).show();
            }else if ("login".equals(msg)){
                Toast.makeText(this ,"登录成功!",Toast.LENGTH_SHORT).show();
            }else if ("main".equals(msg)){
                Toast.makeText(this ,"首页数据获取成功!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
    }
}
