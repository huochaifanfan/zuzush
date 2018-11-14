package com.zuzush.zuzush.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zuzush.zuzush.application.MainApplication;
import com.zuzush.zuzush.view.base.BaseActivity;

/**
 * Created by Administrator on 2017/7/28 0028.
 * 接受微信回调的类
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        try {
            MainApplication.iwxapi.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MainApplication.iwxapi.handleIntent(intent , this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
    /**接受微信返回的请求*/
    @Override
    public void onResp(BaseResp baseResp) {
        String result = null;
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                /**授权成功  获取code*/

                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
        }
//        Toast.makeText(this , result , Toast.LENGTH_SHORT).show();
//        this.finish();
    }
}
