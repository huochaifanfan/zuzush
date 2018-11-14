package com.zuzush.zuzush.view.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.view.base.BaseActivity;

/**
 * Created by liujun on 2017/9/14 0014.
 */
public class ModifyNickNameActivity extends BaseActivity implements View.OnClickListener,IModifyNickView{
    private ImageView imageBack;
    private EditText etNick;
    private Button btnModify;
    private MainPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nickname);
        init();
    }

    private void init() {
        imageBack = (ImageView) findViewById(R.id.image_topBack);
        etNick = (EditText) findViewById(R.id.et_nick);
        btnModify = (Button) findViewById(R.id.btn_modify);
        imageBack.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        presenter = new MainPresenter(this,this);
        etNick.setText(Constants.userNick);
        if (Constants.userNick !=null) etNick.setSelection(Constants.userNick.length());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_topBack:
                this.finish();
                break;

            case R.id.btn_modify:
                if (!NetworkUtil.isNetWorkConnected(this)){
                    toast(this , Constants.NETWORK_ERROR);
                    return;
                }
               cancelable= presenter.modifyNickName();
                break;
        }
    }
    @Override
    public String getUrl() {
        return Constants.modifyNickName;
    }

    @Override
    public String getNickName() {
        return etNick.getText().toString();
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args.length>0){
            if ("200".equals(args[0])){
                toast(this,"修改成功");
                this.finish();
            }
        }
    }
}
