package com.zuzush.zuzush.bean;

import java.util.List;

/**
 * Created by liujun on 2017/9/19 0019.
 * 首页实体类
 */

public class MainBean {
    private List<BannerEntity> bannerData;
    private List<MiddleEntity> middleData;
    private List<MainBottomEntity> mainBottomData;
    private String hotImageUrl;
    private String hotImageLink;

    public List<BannerEntity> getBannerData() {
        return bannerData;
    }

    public void setBannerData(List<BannerEntity> bannerData) {
        this.bannerData = bannerData;
    }

    public List<MiddleEntity> getMiddleData() {
        return middleData;
    }

    public void setMiddleData(List<MiddleEntity> middleData) {
        this.middleData = middleData;
    }

    public List<MainBottomEntity> getMainBottomData() {
        return mainBottomData;
    }

    public void setMainBottomData(List<MainBottomEntity> mainBottomData) {
        this.mainBottomData = mainBottomData;
    }

    public String getHotImageUrl() {
        return hotImageUrl;
    }

    public void setHotImageUrl(String hotImageUrl) {
        this.hotImageUrl = hotImageUrl;
    }

    public String getHotImageLink() {
        return hotImageLink;
    }

    public void setHotImageLink(String hotImageLink) {
        this.hotImageLink = hotImageLink;
    }

    public static class MainBottomEntity{
        private String personPic;
        private String personName;
        private String personFrom;
        private String isIdentify;
        private String newOld;
        private String postAge;
        private String wayText;
        private String title;
        private String way;
        private String mainImage;
        private List<String> images;
        private int price;
        private String sellingPrice;//押金
        private String isOne;

        public String getIsOne() {
            return isOne;
        }

        public void setIsOne(String isOne) {
            this.isOne = isOne;
        }

        public String getPersonPic() {
            return personPic;
        }

        public void setPersonPic(String personPic) {
            this.personPic = personPic;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getPersonFrom() {
            return personFrom;
        }

        public void setPersonFrom(String personFrom) {
            this.personFrom = personFrom;
        }

        public String getIsIdentify() {
            return isIdentify;
        }

        public void setIsIdentify(String isIdentify) {
            this.isIdentify = isIdentify;
        }

        public String getNewOld() {
            return newOld;
        }

        public void setNewOld(String newOld) {
            this.newOld = newOld;
        }

        public String getPostAge() {
            return postAge;
        }

        public void setPostAge(String postAge) {
            this.postAge = postAge;
        }

        public String getWayText() {
            return wayText;
        }

        public void setWayText(String wayText) {
            this.wayText = wayText;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(String sellingPrice) {
            this.sellingPrice = sellingPrice;
        }
    }
    public static class MiddleEntity{
        private String url;
        private String link;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
    public static class BannerEntity{
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        private String url;
        private String link;
    }
}
