package com.zuzush.zuzush.bean;

import java.util.List;

/**
 * Created by liujun on 2017/9/27 0027.
 * 分类
 */

public class ClassifyBean {
    private String leftAutoId;
    private String leftName;
    private List<ClassifyRightBean> rightData;

    public String getLeftAutoId() {
        return leftAutoId;
    }

    public void setLeftAutoId(String leftAutoId) {
        this.leftAutoId = leftAutoId;
    }

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public List<ClassifyRightBean> getRightData() {
        return rightData;
    }

    public void setRightData(List<ClassifyRightBean> rightData) {
        this.rightData = rightData;
    }

    public static class ClassifyRightBean{
        private String rightAutoId;
        private String rightName;

        public String getRightAutoId() {
            return rightAutoId;
        }

        public void setRightAutoId(String rightAutoId) {
            this.rightAutoId = rightAutoId;
        }

        public String getRightName() {
            return rightName;
        }

        public void setRightName(String rightName) {
            this.rightName = rightName;
        }
    }
}
