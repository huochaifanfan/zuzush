package com.zuzush.zuzush.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by liujun on 2017/9/14 0014.
 */
public class PersonalDataBean implements Serializable{
    private double progress;
    private String picture;
    private File file;
    private List<PersonalDataBottom> content;
    private String year;
    private String month;
    private String day;
    private String sex;
    private String occup;
    private String introduce;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOccup() {
        return occup;
    }

    public void setOccup(String occup) {
        this.occup = occup;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public static class PersonalDataBottom implements Serializable{
        private String name;
        private String value;
        public PersonalDataBottom(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
    public void setContent(List<PersonalDataBottom> content) {
        this.content = content;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    public List<PersonalDataBottom> getContent() {
        return content;
    }
}
