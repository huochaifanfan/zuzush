package com.zuzush.zuzush.bean;

import java.io.Serializable;

/**
 * Created by liujun on 2017/9/21 0021.
 * 我的钱包
 */

public class MyWalletBean implements Serializable{
    private int money;
    private String totalInCome;
    private String name;
    private String account;
    private String isValid;
    private String cridtScroe;

    public String getCridtScroe() {
        return cridtScroe;
    }

    public void setCridtScroe(String cridtScroe) {
        this.cridtScroe = cridtScroe;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getTotalInCome() {
        return totalInCome;
    }

    public void setTotalInCome(String totalInCome) {
        this.totalInCome = totalInCome;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
