package com.zuzush.zuzush.bean;

import android.net.Uri;

/**
 * Created by Administrator on 2017/8/24 0024.
 * 上传图片
 */
public class UpLoadBean extends BaseBean {
    private String imageId;
    private String imageUrl;
    private long totalLength;
    private long currentLength;
    public static class UriPicBean{
        private String uri;
        private int progress;
        private String imageId;
        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }
    }
    public long getTotalLength() {
        return totalLength;
    }
    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }
    public long getCurrentLength() {
        return currentLength;
    }
    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }
    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
