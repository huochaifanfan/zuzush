package com.zuzush.zuzush.view.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.BaseBean;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.RegesUtils;

import org.xutils.common.Callback;

/**
 * Created by liujun on 2017/7/30 0030.
 */
public class BaseFragment extends Fragment{
    protected Dialog msgDialog;
    protected Callback.Cancelable cancelable;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    protected Dialog showDialogFrame(int source,int gravity,int backgroundResource,int width){
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(source);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        if (backgroundResource != 0){
            window.setBackgroundDrawableResource(backgroundResource);
        }else {
            window.setBackgroundDrawable(null);
        }
        window.setGravity(gravity);
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = CommonUtil.getScreenInfo(getActivity())[0]-width;
        window.setAttributes(p);
        if (getActivity() != null && dialog != null && !dialog.isShowing()) dialog.show();
        return dialog;
    }
    protected TextView textToast;
    private ImageView imageViewDialog;
    protected String capther;
    /**
     * 显示验证码的弹框
     */
    protected void showMsgDialog(){
        int width = CommonUtil.dip2px(getActivity(),64);
        msgDialog = showDialogFrame(R.layout.verfily_msg_dialog , Gravity.CENTER , R.drawable.dialog_corner,width);
        msgDialog.setCanceledOnTouchOutside(false);
        final EditText etMsg = (EditText) msgDialog.findViewById(R.id.et_msg);
        final TextView textCancel = (TextView) msgDialog.findViewById(R.id.text_cancel);
        textToast = (TextView) msgDialog.findViewById(R.id.text_toast);
        imageViewDialog = (ImageView) msgDialog.findViewById(R.id.image);
        final TextView btnSure = (TextView) msgDialog.findViewById(R.id.button_sure);
        imageClick();
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msgDialog != null) {
                    msgDialog.cancel();
                }
            }
        });
        imageViewDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClick();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etMsg.getText())){
                    capther = etMsg.getText().toString();
                    msgDialogClick();
                }else {
                    CommonUtil.toastTip(getActivity(),"请输入验证码");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cancelable != null)cancelable.cancel();
    }

    protected void imageClick(){
        Glide.with(getActivity()).load(Constants.captcha+"&time="+System.currentTimeMillis()).into(imageViewDialog);
    }
    protected void msgDialogClick(){}
    protected void toast(Context context ,String hint){
        if (context != null)Toast.makeText(context,hint,Toast.LENGTH_SHORT).show();
    }
    public void analysisFailer(String info) {
        if (Constants.isDebug){
            showError(info);
        }else {
            toast(getActivity(),Constants.dataException);
        }
    }
    public void httpError(String error) {
        if (Constants.isDebug){
            showError(error);
        }else {
            toast(getActivity(),Constants.timeOut);
        }
    }
    public void getDataFailer(BaseBean bean){
        if (bean != null){
            String info = bean.getInfo();
            if (info != null && !"".equals(info)) toast(getActivity(),info);
        }
    }
    public void getDataSuccess(Object[] args){
        if (Constants.isDebug){
            if (args != null && args.length>0 && args[args.length-1] instanceof String){
                showError((String) args[args.length-1]);
            }
        }
    }
    private Dialog errorDialog;
    private void showError(String info){
        int width = CommonUtil.dip2px(getActivity(),60);
        if (errorDialog == null) errorDialog= showDialogFrame(R.layout.dialog_error,Gravity.CENTER,R.drawable.dialog_corner,width);
        TextView textContent = (TextView) errorDialog.findViewById(R.id.text_content);
        textContent.setText(RegesUtils.formatJson(info));
    }
}
