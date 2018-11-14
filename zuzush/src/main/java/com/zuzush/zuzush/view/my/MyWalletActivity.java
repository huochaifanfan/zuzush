package com.zuzush.zuzush.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.MyWalletBean;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.util.UiUtils;
import com.zuzush.zuzush.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/9/20 0020.
 * 我的钱包
 */

public class MyWalletActivity extends BaseActivity implements INormalView {
    @BindView(R.id.text_top) TextView textTop;
    @BindView(R.id.text_amount) TextView textAmount;
    @BindView(R.id.text_income) TextView textIncome;
    @BindView(R.id.text_name) TextView textName;
    @BindView(R.id.text_account) TextView textAccount;
    private MainPresenter presenter;
    private String isValid;
    private MyWalletBean bean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.setTransluent(this);
        setContentView(R.layout.activity_mywallet);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        UiUtils.setParams(this,textTop,0);
        presenter = new MainPresenter(this,this);
    }
    private void getMyWallet(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            toast(this,Constants.NETWORK_ERROR);
            return;
        }
        cancelable = presenter.getMyWallet();
    }
    @OnClick({R.id.image_topBack,R.id.text_accountList,R.id.text_drawCash,R.id.rel_aipay})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.image_topBack:
                this.finish();
                break;

            case R.id.text_accountList:/**对账单*/
                Intent intent1 = new Intent(this,DealRecordActivity.class);
                startActivity(intent1);
                break;

            case R.id.text_drawCash:
                if (isValid != null && !"".equals(isValid)){
//                    if (bean.getMoney() >0) {
                    Intent intent = new Intent(this, DrawCashActivity.class);
                    intent.putExtra("name", bean.getName());
                    intent.putExtra("account", bean.getAccount());
                    intent.putExtra("money", bean.getMoney());
                    startActivity(intent);
//                    }else {
//                        toast(this,"暂无可提现金额");
//                    }
                }else {
                    toast(this,"请先绑定支付宝账户！");
                }
                break;

            case R.id.rel_aipay:/**绑定或者解绑支付宝*/
                Intent intent = new Intent(this,BindAliPayActivity.class);
                intent.putExtra("isValid",bean);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyWallet();
    }

    @Override
    public String getUrl() {
        return Constants.myWallet;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args.length>0){
            if (args[0] instanceof MyWalletBean) setData((MyWalletBean) args[0]);
        }
    }
    private void setData(MyWalletBean bean){
        this.bean = bean;
        if (bean != null){
            isValid = bean.getIsValid();
            textAmount.setText(CommonUtil.formatTosepara(bean.getMoney()/100.0));
            textIncome.setText("累计收入：￥"+bean.getTotalInCome());
            if (isValid != null && !"".equals(isValid)){
                textName.setText(bean.getName());
                textAccount.setText(bean.getAccount());
            }else {
                textName.setText("");
                textAccount.setText("");
            }
        }
    }
}
