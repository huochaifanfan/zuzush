package com.zuzush.zuzush.bean;

/**
 * Created by Administrator on 2017/8/24 0024.
 * 选择物品的新旧程度
 */
public class OldNewBean  {
    private String content;
    private String oldId;

    public OldNewBean(String content, String oldId) {
        this.content = content;
        this.oldId = oldId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }
}
