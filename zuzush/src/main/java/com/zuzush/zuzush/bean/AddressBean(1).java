package com.zuzush.zuzush.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/14 0014.
 */
public class AddressBean extends BaseBean implements Serializable{
    private String name;
    private String telephone;
    private String address;
    private String autoId;
    private String isDefault;
    private String arera;

    public String getArera() {
        return arera;
    }

    public void setArera(String arera) {
        this.arera = arera;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
