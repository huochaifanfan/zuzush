package com.zuzush.zuzush.view.base;

import android.content.Context;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by liujun on 2017/7/28 0028.
 * QQ的回调类
 */
public class BaseUiListener implements IUiListener {
    private Context context;
    public BaseUiListener(Context context){
        this.context = context;
    }
    @Override
    public void onComplete(Object o) {
        Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onError(UiError uiError) {
        Toast.makeText(context,"登录错误",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCancel() {
        Toast.makeText(context,"登录取消",Toast.LENGTH_SHORT).show();
    }
}
