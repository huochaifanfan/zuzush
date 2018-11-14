package com.zuzush.zuzush.presenter;

import android.content.Context;

import com.zuzush.zuzush.bean.BaseBean;
import com.zuzush.zuzush.bean.UpLoadBean;
import com.zuzush.zuzush.module.main.MainBiz;
import com.zuzush.zuzush.module.main.PublishListener;
import com.zuzush.zuzush.module.main.UpLoadListener;
import com.zuzush.zuzush.view.login.ISendCaptchaView;
import com.zuzush.zuzush.view.main.IMainBottomView;
import com.zuzush.zuzush.view.my.IAddAddressView;
import com.zuzush.zuzush.view.my.IBindView;
import com.zuzush.zuzush.view.my.IModifyNickView;
import com.zuzush.zuzush.view.my.INormalView;
import com.zuzush.zuzush.view.publish.IClassifyView;
import com.zuzush.zuzush.view.publish.IUpLoadView;
import com.zuzush.zuzush.view.setting.IBlackListView;

import org.xutils.common.Callback;

import java.util.Map;

/**
 * Created by liujun on 2017/7/30 0030.
 */
public class MainPresenter extends BasePresenter{
    private Context context;
    private MainBiz mainBiz;
    private ISendCaptchaView sendCaptchaView;
    private IUpLoadView upLoadView;
    private IClassifyView classifyView;
    private INormalView normalView;
    private IModifyNickView modifyNickView;
    private IAddAddressView addAddressView;
    private IBindView bindView;
    private IBlackListView blackListView;
    private IMainBottomView mainBottomView;

    public MainPresenter(Context context, ISendCaptchaView sendCaptchaView) {
        this.context = context;
        this.sendCaptchaView = sendCaptchaView;
        mainBiz = MainBiz.getInstance();
    }

    public MainPresenter(Context context, IUpLoadView upLoadView) {
        this.context = context;
        this.upLoadView = upLoadView;
        mainBiz = MainBiz.getInstance();
    }

    public MainPresenter(Context context, IClassifyView classifyView) {
        this.context = context;
        this.classifyView = classifyView;
        mainBiz = MainBiz.getInstance();
    }
    public MainPresenter(Context context, INormalView normalView) {
        this.context = context;
        this.normalView = normalView;
        mainBiz = MainBiz.getInstance();
    }
    public MainPresenter(Context context, IModifyNickView modifyNickView) {
        this.context = context;
        this.modifyNickView = modifyNickView;
        mainBiz = MainBiz.getInstance();
    }
    public MainPresenter(Context context, IAddAddressView addAddressView) {
        this.context = context;
        this.addAddressView = addAddressView;
        mainBiz = MainBiz.getInstance();
    }
    public MainPresenter(Context context, IBindView bindView) {
        this.context = context;
        this.bindView = bindView;
        mainBiz = MainBiz.getInstance();
    }
    public MainPresenter(Context context, IBlackListView blackListView) {
        this.context = context;
        this.blackListView = blackListView;
        mainBiz = MainBiz.getInstance();
    }
    public MainPresenter(Context context, IMainBottomView mainBottomView) {
        this.context = context;
        this.mainBottomView = mainBottomView;
        mainBiz = MainBiz.getInstance();
    }
    /**
     * 发送验证码
     * */
    public Callback.Cancelable sendCaptcha(int flag){
        Callback.Cancelable cancelable = mainBiz.sendCapther(sendCaptchaView.getPhone(), sendCaptchaView.getCaptcha(),
                sendCaptchaView.isVoice(), flag, context, sendCaptchaView.sendUrl(), new CustomerListener(sendCaptchaView));
        return cancelable;
    }
    /**
     * 手机号登陆
     */
    public Callback.Cancelable login(){
        Callback.Cancelable cancelable = mainBiz.login(sendCaptchaView.getPhone(), sendCaptchaView.getLoginCaptcha(), context,
                sendCaptchaView.loginUrl(), new CustomerListener(sendCaptchaView));
        return cancelable;
    }

    /**
     * 获取个人资料
     * @return
     */
    public Callback.Cancelable getPersonal(){
        Callback.Cancelable cancelable = mainBiz.getPersonalData(normalView.getUrl(),context,new CustomerListener(normalView));
        return  cancelable;
    }

    /**
     * 修改昵称
     * @return
     */
    public Callback.Cancelable modifyNickName(){
        Callback.Cancelable cancelable = mainBiz.modifyNickName(modifyNickView.getUrl(),context,modifyNickView.getNickName(),new CustomerListener(modifyNickView));
        return cancelable;
    }

    /**
     * 获取收货地址
     * @return
     */
    public Callback.Cancelable getAddress(){
        Callback.Cancelable cancelable = mainBiz.getAddress(normalView.getUrl(),context,new CustomerListener(normalView));
        return cancelable;
    }

    /**
     * 添加 编辑 收货地址
     * @return
     */
    public Callback.Cancelable addAddress(){
        Callback.Cancelable cancelable = mainBiz.addAddress(addAddressView.getAddUrl(),context,addAddressView.getName(),
                addAddressView.getTelephone(),addAddressView.getArea(),addAddressView.getDetailAddress(),
                addAddressView.isDefault(),addAddressView.getAddressId(),new CustomerListener(addAddressView));
        return cancelable;
    }

    /**
     * 删除收货地址
     * @return
     */
    public Callback.Cancelable deleteAddress(){
        Callback.Cancelable cancelable = mainBiz.deleteAddress(addAddressView.deleteUrl(),context,addAddressView.getAddressId(),new CustomerListener(addAddressView));
        return cancelable;
    }

    /**
     * 我的钱包
     * @return
     */
    public Callback.Cancelable getMyWallet(){
        Callback.Cancelable cancelable = mainBiz.getMyWallet(normalView.getUrl(),context,new CustomerListener(normalView));
        return cancelable;
    }

    /**
     * 获取绑定或者解绑的验证码
     * @return
     */
    public Callback.Cancelable getBindCaptcha(){
        Callback.Cancelable cancelable = mainBiz.getCaptcha(bindView.getCaptchaUrl(),context,bindView.isBind(),bindView.isVoice(),new CustomerListener(bindView));
        return cancelable;
    }

    /**
     * 获取黑名单
     * @return
     */
    public Callback.Cancelable getBlackList(){
        Callback.Cancelable cancelable = mainBiz.getBlackList(blackListView.getBlackUrl(),context,new CustomerListener(blackListView));
        return cancelable;
    }

    /**
     * 绑定或解绑账户
     * @return
     */
    public Callback.Cancelable bindAccount(){
        Callback.Cancelable cancelable = mainBiz.bindAccount(bindView.bindUrl(),context,bindView.isBind(),bindView.getCaptcha(),
                bindView.getName(),bindView.getAccount(),new CustomerListener(bindView));
        return cancelable;
    }

    /**
     * 提现
     * @return
     */
    public Callback.Cancelable drawCash(){
        Callback.Cancelable cancelable = mainBiz.drawCash(modifyNickView.getUrl(),context,modifyNickView.getNickName(),new CustomerListener(modifyNickView));
        return cancelable;
    }

    /**
     * 解除黑名单限制
     * @return
     */
    public Callback.Cancelable unbindBlack(){
        Callback.Cancelable cancelable = mainBiz.unbindBlack(blackListView.unbindBlackUrl(),blackListView.id(),context,new CustomerListener(blackListView));
        return cancelable;
    }

    /**
     * 修改个人资料
     * @param map
     * @return
     */
    public Callback.Cancelable modifyPersonalData(Map<String,String> map){
        Callback.Cancelable cancelable = mainBiz.modifyPersonData(normalView.getUrl(),context,map,new CustomerListener(normalView));
        return cancelable;
    }

    /**
     * 获取交易记录
     * @param flag
     * @return
     */
    public Callback.Cancelable getDealRecord(int flag){
        Callback.Cancelable cancelable = mainBiz.getDealRecord(normalView.getUrl(),context,flag,new CustomerListener(normalView));
        return cancelable;
    }

    /**
     * 获取分类
     * @return
     */
    public Callback.Cancelable getClassify(){
        Callback.Cancelable cancelable = mainBiz.getClassify(normalView.getUrl(),context,new CustomerListener(normalView));
        return cancelable;
    }

    /**
     * 获取首页数据
     * @return
     */
    public Callback.Cancelable getMain(){
        Callback.Cancelable cancelable = mainBiz.getMain(normalView.getUrl(),context,new CustomerListener(normalView));
        return cancelable;
    }

    /**
     * 首页推荐数据
     * @return
     */
    public Callback.Cancelable getMainBottom(){
        Callback.Cancelable cancelable = mainBiz.getMainRcommend(mainBottomView.getUrl(),context,mainBottomView.getFlag(),
                mainBottomView.getPage(),new CustomerListener(mainBottomView));
        return cancelable;
    }
    /**
     * 上传图片
     */
    public Callback.Cancelable upLoadPic(){
        Callback.Cancelable cancelable = mainBiz.upLoadPicture(upLoadView.getUpLoadUrl(), context, upLoadView.getUri(), new UpLoadListener() {
            @Override
            public void upLoadFinished() {
                upLoadView.upLoadFinished();
            }
            @Override
            public void loadProcess(UpLoadBean bean) {
                upLoadView.loadProcess(bean);
            }
            @Override
            public void analysisFailer(String info) {
                upLoadView.analysisFailer(info);
            }
            @Override
            public void httpError(String error) {
                upLoadView.httpError(error);
            }
            @Override
            public void getDataFailer(BaseBean bean) {
                upLoadView.getDataFailer(bean);
            }
            @Override
            public void getDataSuccess(Object[] args) {
                upLoadView.getDataSuccess(args);
            }
        });
        return cancelable;
    }

    /**
     * 获取物品的分类
     */
    public Callback.Cancelable getFirstClassify(){
        Callback.Cancelable cancelable = mainBiz.getClassify(classifyView.getClassifyUrl(), context,
                classifyView.getId(), classifyView.getIsmain(), classifyView.isFirst(), new PublishListener() {
            @Override
            public void publishSuccess() {
                classifyView.getClassifySuccess();
            }
            @Override
            public void analysisFailer(String info) {
                classifyView.analysisFailer(info);
            }
            @Override
            public void httpError(String error) {
                classifyView.httpError(error);
            }
            @Override
            public void getDataFailer(BaseBean bean) {
                classifyView.getDataFailer(bean);
            }
            @Override
            public void getDataSuccess(Object[] args) {
            }
        });
        return cancelable;
    }
}
