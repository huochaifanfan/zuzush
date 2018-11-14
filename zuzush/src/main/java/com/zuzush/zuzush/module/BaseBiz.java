package com.zuzush.zuzush.module;

import com.zuzush.zuzush.bean.BaseBean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/5 0005.
 */
public class BaseBiz {
    protected void getFailer(JSONObject object,BaseListener listener){
        BaseBean bean = new BaseBean();
        try{
            bean.setStatus(object.getString("status"));
            bean.setInfo(object.getString("info"));
            if (listener != null) listener.getDataFailer(bean);
        }catch (Exception exception){}
    }
    protected void analysisFailer(BaseListener listener,String result,String exception){
        if (listener != null) listener.analysisFailer("result："+result+"错误原因："+exception);
    }
}
