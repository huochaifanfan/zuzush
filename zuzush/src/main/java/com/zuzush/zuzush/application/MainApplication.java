package com.zuzush.zuzush.application;

import android.app.Application;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.ExitApp;

import org.xutils.x;

/**
 * Created by liujun on 2017/7/28 0028.
 */
public class MainApplication extends Application {
    public static Tencent mTencent;
    public static IWXAPI iwxapi;
    public static int version;
    @Override
    public void onCreate() {
        super.onCreate();
        /**初始化热修复*/
//        initHotFix();
        /**初始化第三方登录*/
        initLoginInfo();
        registerActivityLifecycleCallbacks(new ExitApp());
    }

    private void initLoginInfo() {
        /**初始化xutils*/
        x.Ext.init(this);
        mTencent = Tencent.createInstance(Constants.APPID , getApplicationContext());
        /**初始化微信*/
        iwxapi = WXAPIFactory.createWXAPI(this , Constants.weiChatID , true);
        iwxapi.registerApp(Constants.weiChatID);
        version = CommonUtil.getVersion(this);
    }

    /**
     * 初始化热修复 必须在程序的入口 否则可能出现崩溃
     */
    private void initHotFix(){
        SophixManager.getInstance().setContext(this).setAppVersion(CommonUtil.getVersonName(this)).setAesKey(null).
                setEnableDebug(true).setPatchLoadStatusStub(new PatchLoadStatusListener() {
            @Override
            public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                if (code == PatchStatus.CODE_LOAD_SUCCESS){
                    Log.i("hotfix","补丁加载成功");
                }else if (code == PatchStatus.CODE_LOAD_RELAUNCH){
                    Log.i("hotfix","表明新补丁生效需要重启. 开发者可提示用户或者强制重启");
                }else {
                    Log.i("hotfix","其它错误信息"+code);
                }
            }
        }).initialize();
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
