package com.zuzush.zuzush.util;

import android.os.Build;

import com.zuzush.zuzush.application.MainApplication;

import java.util.Map;

/**
 * Created by liujun on 2017/7/28 0028.
 * 常用常量类
 */
public class Constants {
    public static String APPID;
    public static String weiChatID;
    public static String userAgent = "#lz-app-android-"+ MainApplication.version+"-"+ Build.MODEL+"-"+Build.VERSION.RELEASE+"#";
    public static String timeOut = "网络不给力，请稍后再试！";
    public static String dataException = "数据解析失败！";
    public static String NETWORK_ERROR = "网络出问题了，请稍后重试！";
    public static String url = "http://zz.zhuomn.cn/v1";
    public static String picUrl = "http://zz.zhuomn.cn";
    public static String webUrl = "http://mzz.zhuomn.cn";
    public static boolean isDebug = true;
    public static String userId;
    public static String userNick;
    public static String telephone;
    public static String isIdentify;
    public static String isZhiMa;

    /**获取短信和语音验证码*/
    public static String sendCapcha = url+"/login/send_captcha";
    /**图片验证码*/
    public static String captcha = picUrl+"/authtoken/image?width=100&height=40&text=1";
    /**手机号登陆*/
    public static String login = url +"/login/captcha_login";
    /**发布技巧*/
    public static String publishSkill = webUrl+"/help/handbook/skill.html";
    /**防骗指南*/
    public static String publishCheat = webUrl + "/help/handbook/beware.html";
    /**担保交易*/
    public static String publishDeal = webUrl + "/help/handbook/ensure.html";
    /**上传图片*/
    public static String upLoadPicture = url + "/res/add/img_upload";
    /**发布物品*/
    public static String publishGoods = url + "/res/add/goods";
    /**分类页面数据*/
    public static String getClassify = url + "/res/category/get";
    /**获取个人资料*/
    public static String personalData = url + "/user/profile/index";
    /**修改昵称*/
    public static String modifyNickName = url + "/user/profile/update_nick";
    /**获取收货地址*/
    public static String getAddress = url + "/user/address/index";
    /**增加收货地址*/
    public static String addAddress = url + "/user/address/add";
    /**编辑收货地址*/
    public static String editAddress = url + "/user/address/edit";
    /**删除收货地址*/
    public static String deleteAddress = url + "/user/address/delete";
    /**个人中心首页*/
    public static String personCenter = url + "/user/my/index";
    /**修改图像*/
    public static String modifyPicture = url + "/user/profile/update_avatar";
    /**首页数据*/
    public static String mainIndex = url + "/index/index";
    /**首页推荐*/
    public static String mainRecommend = url + "/index/recommend";
    /**我的钱包*/
    public static String myWallet = url + "/user/wallet/cash/index";
    /**绑定或者解绑账户发送验证码*/
    public static String bindCaptcha = url + "/user/wallet/bank/send_captcha";
    /**支付宝账户绑定或解绑功能*/
    public static String bindAliPay = url + "/user/wallet/bank/bind";
    /**黑名单列表*/
    public static String blackList = url + "/user/blacklist/index";
    /**解绑黑名单*/
    public static String unbindBlack = url + "/user/blacklist/delete";
    /**提现*/
    public static String drawCash = url + "/user/wallet/cash/withdraw";
    /**交易记录*/
    public static String dealRecord = url + "/user/wallet/cash/record";
    /**修改个人资料图像*/
    public static String modifyIcon = url + "/user/profile/update_avatar";
    /**修改个人资料*/
    public static String modifyPersonalData = url + "/user/profile/update";
}
