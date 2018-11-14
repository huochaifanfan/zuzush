package com.zuzush.zuzush.view.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
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
 * Created by liujun on 2017/7/28 0028.
 */
public class BaseActivity extends AppCompatActivity {
    private Dialog msgDialog;
    private int mRequestCode;
    private PermissionsResultListener mListener;
    protected Callback.Cancelable cancelable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected Dialog showDialogFrame(int source, int gravity, int backgroundResource,int width){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(source);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        if (backgroundResource != 0) {
            window.setBackgroundDrawableResource(backgroundResource);
        }else{
            window.setBackgroundDrawable(null);
        }
        window.setGravity(gravity);
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = CommonUtil.getScreenInfo(this)[0]-width;
        window.setAttributes(p);
        if (!this.isFinishing() && dialog != null) dialog.show();
        return dialog;
    }
    protected TextView textToast;
    private ImageView imageViewDialog;
    protected String capther;
    /**
     * 显示验证码的弹框
     */
    protected void showMsgDialog(){
        int width = CommonUtil.dip2px(this,64);
        msgDialog = showDialogFrame(R.layout.verfily_msg_dialog , Gravity.CENTER , R.drawable.dialog_corner,width);
        msgDialog.setCanceledOnTouchOutside(false);
        final EditText etMsg = (EditText) msgDialog.findViewById(R.id.et_msg);
        final TextView textCancel = (TextView) msgDialog.findViewById(R.id.text_cancel);
        textToast = (TextView) msgDialog.findViewById(R.id.text_toast);
        imageViewDialog = (ImageView) msgDialog.findViewById(R.id.image);
        final TextView btnSure = (TextView) msgDialog.findViewById(R.id.button_sure);
        Glide.with(this).load(Constants.captcha).into(imageViewDialog);
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
                    CommonUtil.toastTip(BaseActivity.this,"请输入验证码");
                }
            }
        });
    }
    protected void imageClick(){
        Glide.with(BaseActivity.this).load(Constants.captcha).into(imageViewDialog);
    }
    protected void msgDialogClick(){}
    /**
     *
     * @param descrption 首次权限申请被用户绝绝后的提示
     * @param perssions 要申请的权限数组
     * @param requestCode 请求码
     * @param listener 实现的接口
     */
    protected void performRequestPermissions(String descrption , String[] perssions , int requestCode , PermissionsResultListener listener){
        if (perssions == null || perssions.length == 0) return;
        mRequestCode = requestCode;
        mListener = listener;
        /**检查版本*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (!checkEachSelfPermission(perssions)){
                /**没有权限*/
                requestEachPermissions(descrption , perssions , requestCode);
            }else {
                /**已经获取了权限*/
                if (listener != null) listener.onPermissionGranted();
            }
        }else {
            if (listener != null) listener.onPermissionGranted();
        }
    }

    /**
     * 权限申请 申请之前判断是否需要再次申请权限
     * @param descrption
     * @param perssions
     * @param requestCode
     */
    private void requestEachPermissions(String descrption, String[] perssions, int requestCode) {
        if (shouldShowRequestPermissionRationale(perssions)){
            /**需要再次进行权限的申请  弹出权限申请的对话框  首次申请被拒绝之后再次申请的弹框*/
            showRationaleDialog(descrption , perssions , requestCode);
        }else {
            ActivityCompat.requestPermissions(this,perssions,requestCode);
        }
    }
    /**
     * 弹出权限申请的对话框
     * @param des
     * @param perssions
     * @param requestCode
     */
    private void showRationaleDialog(String des , final String[] perssions , final int requestCode){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限").setMessage(des).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(BaseActivity.this , perssions ,requestCode);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(false).show();
    }

    /**
     * 再次用到权限时候判断是否需要再次申请权限
     * @param perssions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] perssions){
        for (String perssion:perssions){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this , perssion)){
                return true;
            }
        }
        return false;
    }
    /**
     * 检查每个权限是否申请
     * @param permissions
     * @return true 需要申请权限  false 不要申请权限
     */
    private boolean checkEachSelfPermission(String[] permissions){
        for (String perssion:permissions){
            if (ContextCompat.checkSelfPermission(this , perssion)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    /**
     * 检查权限的回调结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode){
            if (!checkEachPermissionsGranted(grantResults)){
                if (mListener != null) mListener.onPermissionDenied();
            }else {
                if (mListener != null) mListener.onPermissionGranted();
            }
        }
    }
    /**
     * 检查权限授予结果
     * @param grantResults
     * @return
     */
    private boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (cancelable != null) cancelable.cancel();
        super.onDestroy();
    }

    protected void toast(Context context , String hint){
        if (context != null) Toast.makeText(context,hint,Toast.LENGTH_SHORT).show();
    }
    public void analysisFailer(String info) {
        if (Constants.isDebug){
            showError(info);
        }else {
            toast(this,Constants.dataException);
        }
    }
    public void httpError(String error) {
        if (Constants.isDebug){
            showError(error);
        }else {
            toast(this,Constants.timeOut);
        }
    }
    public void getDataFailer(BaseBean bean){
        if (bean != null){
            String info = bean.getInfo();
            if (info != null && !"".equals(info)) toast(this,info);
        }
    }
    private void showError(String info){
        int width = CommonUtil.dip2px(this,60);
        Dialog dialog = showDialogFrame(R.layout.dialog_error,Gravity.CENTER,R.drawable.dialog_corner,width);
        TextView textContent = (TextView) dialog.findViewById(R.id.text_content);
        textContent.setText(RegesUtils.formatJson(info));
    }
    public void getDataSuccess(Object[] args){
        if (Constants.isDebug){
            if (args != null && args.length>0 && args[args.length-1] instanceof String){
                showError((String) args[args.length-1]);
            }
        }
    }
    protected void showNormalDialog(){
        int width = CommonUtil.dip2px(this,50);
        final Dialog dialog = showDialogFrame(R.layout.normal_dialog,Gravity.CENTER,R.drawable.dialog_corner,width);
        TextView textContent = (TextView) dialog.findViewById(R.id.text_content);
        textContent.setText(setContent());
        TextView textCancel = (TextView) dialog.findViewById(R.id.textCancel);
        TextView textSure = (TextView) dialog.findViewById(R.id.textSure);
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.cancel();
            }
        });
        textSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNormalDialogClick();
            }
        });
    }
    protected void onNormalDialogClick(){}
    protected String setContent(){
        return null;
    }
}
