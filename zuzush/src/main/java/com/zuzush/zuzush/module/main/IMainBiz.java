package com.zuzush.zuzush.module.main;

import android.content.Context;

import com.zuzush.zuzush.module.BaseListener;

import org.xutils.common.Callback;

import java.io.File;
import java.util.Map;

/**
 * Created by liujun on 2017/7/30 0030.
 */
public interface IMainBiz {
    Callback.Cancelable sendCapther(String phone, String captcha, String is_voice, int flag, Context context, String url, BaseListener listener);
    Callback.Cancelable login(String phone,String captcha,Context context,String url,BaseListener listener);
    Callback.Cancelable upLoadPicture(String url, Context context, File uri, UpLoadListener listener);
    Callback.Cancelable publish(String url, Context context, Map<String,String> map,PublishListener listener);
    Callback.Cancelable getClassify(String url , Context context,String id,String isMain,boolean isFirst,PublishListener listener);
    Callback.Cancelable getPersonalData(String url, Context context,BaseListener listener);
    Callback.Cancelable modifyNickName(String url,Context context,String nickName,BaseListener listener);
    Callback.Cancelable getAddress(String url,Context context,BaseListener listener);
    Callback.Cancelable addAddress(String url,Context context,String name,String telephone,String area,String detail,String isDefault,String autoId,BaseListener listener);
    Callback.Cancelable deleteAddress(String url,Context context,String autoId,BaseListener listener);
    Callback.Cancelable getMyWallet(String url,Context context,BaseListener listener);
    Callback.Cancelable getCaptcha(String url,Context context,String isBind,String isVoice,BaseListener listener);
    Callback.Cancelable getBlackList(String url,Context context,BaseListener listener);
    Callback.Cancelable bindAccount(String url,Context context,String isBind,String captcha,String name,String account,BaseListener listener);
    Callback.Cancelable drawCash(String url,Context context,String amount,BaseListener listener);
    Callback.Cancelable unbindBlack(String url,String id,Context context,BaseListener listener);
    Callback.Cancelable modifyPersonData(String url,Context context,Map<String,String> map,BaseListener listener);
    Callback.Cancelable getDealRecord(String url,Context context,int flag,BaseListener listener);
    Callback.Cancelable getClassify(String url,Context context,BaseListener listener);
    Callback.Cancelable getMain(String url,Context context,BaseListener listener);
    Callback.Cancelable getMainRcommend(String url,Context context,int flag,String page,BaseListener listener);
}
