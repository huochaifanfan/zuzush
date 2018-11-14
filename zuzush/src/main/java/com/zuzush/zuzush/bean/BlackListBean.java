package com.zuzush.zuzush.bean;

/**
 * Created by liujun on 2017/9/21 0021.
 * 黑名单
 */

public class BlackListBean {
    private String imageUrl;
    private String blackNick;
    private String blackId;
    private long time;
    private String autoId;

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBlackNick() {
        return blackNick;
    }

    public void setBlackNick(String blackNick) {
        this.blackNick = blackNick;
    }

    public String getBlackId() {
        return blackId;
    }

    public void setBlackId(String blackId) {
        this.blackId = blackId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
