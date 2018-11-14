package com.zuzush.zuzush.bean;

import java.util.List;

/**
 * Created by liujun on 2017/9/26 0026.
 * 交易记录
 */

public class DealBean {
    private List<DealRecord> dealData;
    private List<RecordType> recordTypes;

    public List<DealRecord> getDealData() {
        return dealData;
    }

    public void setDealData(List<DealRecord> dealData) {
        this.dealData = dealData;
    }

    public List<RecordType> getRecordTypes() {
        return recordTypes;
    }

    public void setRecordTypes(List<RecordType> recordTypes) {
        this.recordTypes = recordTypes;
    }

    public static class DealRecord{
        private int amount;
        private int amountLeft;
        private String title;
        private String dealTime;
        private String dealStatus;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getAmountLeft() {
            return amountLeft;
        }

        public void setAmountLeft(int amountLeft) {
            this.amountLeft = amountLeft;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDealTime() {
            return dealTime;
        }

        public void setDealTime(String dealTime) {
            this.dealTime = dealTime;
        }

        public String getDealStatus() {
            return dealStatus;
        }

        public void setDealStatus(String dealStatus) {
            this.dealStatus = dealStatus;
        }
    }
    public static class RecordType{
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
