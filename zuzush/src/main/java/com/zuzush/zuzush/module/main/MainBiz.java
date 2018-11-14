package com.zuzush.zuzush.module.main;

import android.content.Context;

import com.zuzush.zuzush.bean.BlackListBean;
import com.zuzush.zuzush.bean.ClassifyBean;
import com.zuzush.zuzush.bean.DealBean;
import com.zuzush.zuzush.bean.MainBean;
import com.zuzush.zuzush.bean.MyWalletBean;
import com.zuzush.zuzush.bean.PersonalDataBean;
import com.zuzush.zuzush.bean.UpLoadBean;
import com.zuzush.zuzush.module.BaseBiz;
import com.zuzush.zuzush.module.BaseListener;
import com.zuzush.zuzush.util.CallBack;
import com.zuzush.zuzush.util.HttpUtils;
import com.zuzush.zuzush.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liujun on 2017/7/30 0030.
 */
public class MainBiz extends BaseBiz implements IMainBiz {
    private volatile static MainBiz INSTANCE = null;
    private MainBiz(){
    }
    public static MainBiz getInstance(){
        if (INSTANCE == null) {
            synchronized (MainBiz.class){
                if (INSTANCE == null){
                    INSTANCE = new MainBiz();
                }
            }
        }
        return INSTANCE;
    }
    /**
     * 获取验证码
     * @param phone 电话号码
     * @param captcha 验证码
     * @param isVoice 是否语音验证码
     * @param context
     * @param url
     * @param listener
     */
    @Override
    public Callback.Cancelable sendCapther(String phone, String captcha, String isVoice, int flag, final Context context, String url, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("telephone",phone);
        params.addBodyParameter("is_voice",isVoice);
        if (flag == 1) params.addBodyParameter("captcha",captcha);
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess(status,result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callBack;
    }
    /**
     * 手机号登陆
     * @param phone
     * @param captcha
     * @param context
     * @param url
     * @param listener
     */
    @Override
    public Callback.Cancelable login(String phone, String captcha, final Context context, String url, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("telephone",phone);
        params.addBodyParameter("captcha",captcha);
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("success",result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callBack;
    }
    /**
     * 上传图片
     * @param url
     * @param context
     * @param uri
     * @param listener
     */
    @Override
    public Callback.Cancelable upLoadPicture(String url, final Context context, File uri, final UpLoadListener listener) {
        RequestParams params = new RequestParams(url);
        params.setMultipart(true);/**表单提交*/
        params.addBodyParameter("img",uri);
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.POST,context,params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        UpLoadBean bean = new UpLoadBean();
                        if (jsonObject.get("data") instanceof JSONObject) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            bean.setImageId(object.getString("id"));
                            bean.setImageUrl(object.getString("url"));
                        }
                        if (listener != null) listener.getDataSuccess(bean);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                if (listener != null && throwable != null) listener.httpError(throwable.toString());
            }
            @Override
            public void onCancelled(CancelledException e) {}
            @Override
            public void onFinished() {
                if (listener != null) listener.upLoadFinished();
            }
            @Override
            public void onWaiting() {}
            @Override
            public void onStarted() {}
            @Override
            public void onLoading(long total, long current, boolean b) {
                UpLoadBean bean = new UpLoadBean();
                bean.setTotalLength(total);
                bean.setCurrentLength(current);
                if (listener != null) listener.loadProcess(bean);
            }
        });
        return callBack;
    }
    /**
     * 发布物品和求助
     * @param url
     * @param context
     * @param map
     * @param listener
     */
    @Override
    public Callback.Cancelable publish(String url, Context context, Map<String, String> map, final PublishListener listener) {
        RequestParams params = new RequestParams(url);
        if (map != null && map.size() > 0){
            Set<String> keys = map.keySet();
            if (keys == null) return null;
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()){
                String key = iterator.next().toString();
                String value = map.get(key);
                params.addBodyParameter(key,value);
            }
        }
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.publishSuccess();
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callBack;
    }
    /**
     * 获取物品一级分类
     * @param url
     * @param context
     * @param id
     * @param isMain
     * @param listener
     */
    @Override
    public Callback.Cancelable getClassify(String url, Context context, String id, String isMain,boolean isFirst, final PublishListener listener) {
        RequestParams params = new RequestParams(url);
        if (!isFirst)params.addBodyParameter("id",id);
        params.addBodyParameter("is_home",isMain);
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callBack;
    }
    /**
     * 获取个人资料
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getPersonalData(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        PersonalDataBean bean = JsonUtils.getInstance().getPersonalData(jsonObject);
                        if (listener != null) listener.getDataSuccess(bean,result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callBack;
    }
    /**
     * 修改昵称
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable modifyNickName(String url, Context context, String nickName , final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("nick",nickName);
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess(status,result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callBack;
    }
    /**
     * 获取收货地址
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getAddress(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess(JsonUtils.getInstance().getAddress(jsonObject),result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callBack;
    }
    /**
     * 添加收货地址
     * @param url
     * @param context
     * @param name
     * @param telephone
     * @param area
     * @param detail
     * @param isDefault
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable addAddress(String url, Context context, String name, String telephone, String area, String detail,
                                          String isDefault, final String autoId, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("name",name);
        params.addBodyParameter("telephone",telephone);
        params.addBodyParameter("area",area);
        params.addBodyParameter("detail",detail);
        params.addBodyParameter("is_def",isDefault);
        params.addBodyParameter("id",autoId);
        Callback.Cancelable callback = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if("200".equals(status)){
                        if (listener != null){
                            if (!"0".equals(autoId)){
                                listener.getDataSuccess("edit",result);
                            }else {
                                listener.getDataSuccess("add",result);
                            }
                        }
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callback;
    }
    /**
     * 删除收货地址
     * @param url
     * @param context
     * @param autoId
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable deleteAddress(String url, Context context, String autoId, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",autoId);
        Callback.Cancelable callback = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("delete",result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callback;
    }
    /**
     * 获取我的钱包相关信息
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getMyWallet(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable callback = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        MyWalletBean bean = JsonUtils.getInstance().getMyWallet(jsonObject);
                        if (listener != null) listener.getDataSuccess(bean,result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callback;
    }
    /**
     * 绑定或者解绑账户发送验证码
     * @param url
     * @param context
     * @param isBind
     * @param isVoice
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getCaptcha(String url, Context context, String isBind, String isVoice, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("is_bind",isBind);
        params.addBodyParameter("is_voice",isVoice);
        Callback.Cancelable callback = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("captcha",result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callback;
    }
    /**
     * 黑名单
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getBlackList(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable callback = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        List<BlackListBean> data = JsonUtils.getInstance().getBlackList(jsonObject);
                        if (listener != null) listener.getDataSuccess(data,result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callback;
    }

    /**
     * 绑定或者解绑账户
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable bindAccount(String url, Context context, String isBind, String captcha ,String name,String account ,final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("is_bind",isBind);
        params.addBodyParameter("captcha",captcha);
        params.addBodyParameter("name",name);
        params.addBodyParameter("account",account);
        Callback.Cancelable callback = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("bind");
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callback;
    }

    /**
     * 提现
     * @param url
     * @param context
     * @param amount
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable drawCash(String url, Context context, String amount, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("amount",amount);
        Callback.Cancelable callback = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("cash");
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callback;
    }

    /**
     * 解除黑名单
     * @param url
     * @param id
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable unbindBlack(String url, String id, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",id);
        Callback.Cancelable callback = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("unbind",result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callback;
    }
    /**
     * 修改个人资料
     * @param url
     * @param context
     * @param map
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable modifyPersonData(String url, Context context, Map<String, String> map, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        if (map != null){
            for (String key:map.keySet()){
                params.addBodyParameter(key,map.get(key));
            }
        }
        Callback.Cancelable callback = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null)listener.getDataSuccess("success",result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callback;
    }

    /**
     * 获取交易记录
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getDealRecord(String url, Context context, final int flag, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable callback = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        DealBean bean = JsonUtils.getInstance().getDealRecord(jsonObject);
                        if (listener != null) listener.getDataSuccess(bean,flag,result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callback;
    }

    /**
     * 获取物品分类
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getClassify(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        List<ClassifyBean> data = JsonUtils.getInstance().getClassify(jsonObject);
                        if (listener !=null) listener.getDataSuccess(data,result);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 首页数据
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getMain(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        MainBean bean = JsonUtils.getInstance().getMain(jsonObject);
                        if (listener != null) listener.getDataSuccess(bean);
                    }else {
                        getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }
    /**
     * 首页推荐
     * @param url
     * @param context
     * @param flag
     * @param page
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getMainRcommend(String url, Context context, final int flag, String page, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("page",page);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        List<MainBean.MainBottomEntity> data = JsonUtils.getInstance().getMainBottom(jsonObject);
                        if (listener != null) listener.getDataSuccess(data,flag);
                    }else {
                        if (listener != null) getFailer(jsonObject,listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }
}
