package com.junliu.liuju.supportlibry.bean;

/**
 * Created by liuju on 2018/3/28.
 */

public class RecyclerBean {
    private String name;
    private int itemType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public RecyclerBean(String name, int itemType){
        this.name = name;
        this.itemType = itemType;
    }
    public RecyclerBean(String name){
        this(name ,11);
    }
}
