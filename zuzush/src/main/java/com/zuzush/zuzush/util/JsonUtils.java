package com.zuzush.zuzush.util;

import com.google.common.annotations.Beta;
import com.zuzush.zuzush.bean.AddressBean;
import com.zuzush.zuzush.bean.BlackListBean;
import com.zuzush.zuzush.bean.ClassifyBean;
import com.zuzush.zuzush.bean.DealBean;
import com.zuzush.zuzush.bean.JsonBean;
import com.zuzush.zuzush.bean.MainBean;
import com.zuzush.zuzush.bean.MyWalletBean;
import com.zuzush.zuzush.bean.PersonalDataBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14 0014.
 */
public class JsonUtils {
    private volatile static JsonUtils INSTANCE = null;
    private JsonUtils(){}
    public static JsonUtils getInstance(){
        if (INSTANCE == null){
            synchronized (JsonUtils.class){
                if (INSTANCE == null){
                    INSTANCE = new JsonUtils();
                }
            }
        }
        return INSTANCE;
    }
    /**
     * 获取个人资料
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public PersonalDataBean getPersonalData(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.getJSONObject("data");
        getUser(object.getJSONObject("_user"));
        JSONObject info  = null;
        if (object.get("info") instanceof JSONObject){
            info = object.getJSONObject("info");
        }
        PersonalDataBean bean = new PersonalDataBean();
        bean.setProgress(object.getDouble("score"));
        bean.setPicture(object.getJSONObject("_user").getString("avatar"));
        List<PersonalDataBean.PersonalDataBottom> data = new ArrayList<>();
        data.add(new PersonalDataBean.PersonalDataBottom("昵称",Constants.userNick));
        if (info != null) {
            data.add(new PersonalDataBean.PersonalDataBottom("性别", info.getInt("sex") == 1 ? "男" : "女"));
            if ("0".equals(info.getString("birth_year"))){
                data.add(new PersonalDataBean.PersonalDataBottom("出生", ""));
            }else {
                data.add(new PersonalDataBean.PersonalDataBottom("出生", info.getString("birth_year") + "年" + info.getString("birth_month") + "月" + info.getString("birth_day") + "日"));
            }
            data.add(new PersonalDataBean.PersonalDataBottom("职业", CommonUtil.setOccop().get(info.getString("occup"))));
            data.add(new PersonalDataBean.PersonalDataBottom("个性签名",info.getString("introduce")));
            bean.setSex(info.getString("sex"));
            bean.setYear(info.getString("birth_year"));
            bean.setMonth(info.getString("birth_month"));
            bean.setDay(info.getString("birth_day"));
            bean.setOccup(info.getString("occup"));
            bean.setIntroduce(info.getString("introduce"));
        }else {
            data.add(new PersonalDataBean.PersonalDataBottom("性别", ""));
            data.add(new PersonalDataBean.PersonalDataBottom("出生", ""));
            data.add(new PersonalDataBean.PersonalDataBottom("职业", ""));
            data.add(new PersonalDataBean.PersonalDataBottom("个性签名",""));
        }
        data.add(new PersonalDataBean.PersonalDataBottom("账户手机",CommonUtil.makePhoneNunToStar(Constants.telephone)));
        data.add(new PersonalDataBean.PersonalDataBottom("收货地址",""));
        data.add(new PersonalDataBean.PersonalDataBottom("其它设置",""));
        bean.setContent(data);
        return bean;
    }
    private void getUser(JSONObject jsonObject) throws JSONException {
        Constants.userId = jsonObject.getString("user_id");
        Constants.userNick = jsonObject.getString("user_nick");
        Constants.telephone = jsonObject.getString("telephone");
        Constants.isIdentify = jsonObject.getString("is_identity");
        Constants.isZhiMa = jsonObject.getString("is_zhima");
    }

    /**
     * 获取收货地址
     * @param jsonObject
     * @return
     */
    public List<AddressBean> getAddress(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.getJSONObject("data");
        JSONArray array = object.getJSONArray("address");
        List<AddressBean> data = new ArrayList<>();
        if (array != null && array.length()>0){
            for (int i =0;i <array.length();i++){
                AddressBean bean = new AddressBean();
                JSONObject obj = array.getJSONObject(i);
                bean.setAutoId(obj.getString("auto_id"));
                bean.setName(obj.getString("name"));
                bean.setTelephone(obj.getString("telephone"));
                bean.setArera(obj.getString("area"));
                bean.setAddress(obj.getString("detail"));
                bean.setIsDefault(obj.getString("is_def"));
                data.add(bean);
            }
        }
        return data;
    }

    /**
     * 获取身份数据
     * @param result
     * @return
     */
    public ArrayList<JsonBean> getProvince(String result){
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(result);
            if (array != null && array.length()>0) {
                for (int i = 0; i < array.length(); i++) {
                    JsonBean jsonBean = new JsonBean();
                    JSONObject jsonObject = array.getJSONObject(i);
                    jsonBean.setName(jsonObject.getString("name"));
                    JSONArray jsonArray = jsonObject.getJSONArray("city");
                    List<JsonBean.CityBean> cityData = new ArrayList<>();
                    if (jsonArray != null && jsonArray.length()>0){
                        for (int j =0;j< jsonArray.length();j++){
                            JsonBean.CityBean cityBean = new JsonBean.CityBean();
                            JSONObject object = jsonArray.getJSONObject(j);
                            cityBean.setName(object.getString("name"));
                            JSONArray array1 = object.getJSONArray("area");
                            List<String> area = new ArrayList<>();
                            for (int k = 0; k<array1.length();k++){
                                area.add(array1.getString(k));
                            }
                            cityBean.setArea(area);
                            cityData.add(cityBean);
                        }
                        jsonBean.setCityList(cityData);
                    }
                    detail.add(jsonBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 我的钱包
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public MyWalletBean getMyWallet(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.getJSONObject("data");
        getUser(object.getJSONObject("_user"));
        MyWalletBean bean = new MyWalletBean();
        JSONObject obj = object.getJSONObject("asset");
        bean.setMoney(obj.getInt("money"));
        if (obj.has("credit_score"))bean.setCridtScroe(obj.getString("credit_score"));
        bean.setTotalInCome(object.getString("totalIncome"));
        if (object.get("bank") instanceof JSONObject){
            JSONObject bank = object.getJSONObject("bank");
            bean.setName(bank.getString("name"));
            bean.setAccount(bank.getString("account"));
            bean.setIsValid(bank.getString("is_valid"));
        }
        return bean;
    }

    /**
     * 获取黑名单列表
     * @param jsonObject
     * @return
     */
    public List<BlackListBean> getBlackList(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.getJSONObject("data");
        List<BlackListBean> data = new ArrayList<>();
        if (object.get("list") instanceof JSONArray) {
            JSONArray array = object.getJSONArray("list");
            if (array != null && array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    BlackListBean bean = new BlackListBean();
                    JSONObject obj = array.getJSONObject(i);
                    bean.setImageUrl(obj.getString("avatar_url"));
                    bean.setBlackId(obj.getString("black_id"));
                    bean.setBlackNick(obj.getString("black_nick"));
                    bean.setTime(obj.getLong("add_time"));
                    bean.setAutoId(obj.getString("auto_id"));
                    data.add(bean);
                }
            }
        }
        return data;
    }

    /**
     * 获取交易记录
     * @param jsonObject
     * @return
     */
    public DealBean getDealRecord(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.getJSONObject("data");
        DealBean dealBean = new DealBean();
        JSONArray jsonArray = object.getJSONArray("recordTypes");
        List<DealBean.RecordType> typesData = new ArrayList<>();
        if (jsonArray != null && jsonArray.length() >0){
            for (int i =0;i<jsonArray.length();i++){
                DealBean.RecordType types = new DealBean.RecordType();
                JSONObject obj = jsonArray.getJSONObject(i);
                types.setKey(obj.getString("type"));
                types.setValue(obj.getString("name"));
                typesData.add(types);
            }
        }
        dealBean.setRecordTypes(typesData);
        List<DealBean.DealRecord> recordData = new ArrayList<>();
        JSONArray array = object.getJSONArray("record");
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                DealBean.DealRecord dealRecord = new DealBean.DealRecord();
                dealRecord.setAmount(obj.getInt("amount"));
                dealRecord.setAmountLeft(obj.getInt("update_after"));
                dealRecord.setTitle(obj.getString("detail"));
                dealRecord.setDealStatus(obj.getString("status_text"));
                dealRecord.setDealTime(obj.getString("add_time"));
                recordData.add(dealRecord);
            }
        }
        dealBean.setDealData(recordData);
        return dealBean;
    }

    /**
     * 获取分类数据
     * @param jsonObject
     * @return
     */
    public List<ClassifyBean> getClassify(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.getJSONObject("data");
        JSONArray jsonArray = object.getJSONArray("category");
        List<ClassifyBean> data = new ArrayList<>();
        if (jsonArray != null && jsonArray.length()>0){
            for (int i =0;i<jsonArray.length();i++) {
                ClassifyBean bean = new ClassifyBean();
                JSONObject obj = jsonArray.getJSONObject(i);
                bean.setLeftAutoId(obj.getString("auto_id"));
                bean.setLeftName(obj.getString("name"));
                JSONArray array = obj.getJSONArray("subset");
                List<ClassifyBean.ClassifyRightBean> rightData = new ArrayList<>();
                if (array != null && array.length()>0){
                    for (int j=0;j<array.length();j++){
                        ClassifyBean.ClassifyRightBean rightBean = new ClassifyBean.ClassifyRightBean();
                        JSONObject oj = array.getJSONObject(j);
                        rightBean.setRightAutoId(oj.getString("auto_id"));
                        rightBean.setRightName(oj.getString("name"));
                        rightData.add(rightBean);
                    }
                }
                bean.setRightData(rightData);
                data.add(bean);
            }
        }
        return data;
    }

    /**
     * 获取首页数据
     * @param jsonObject
     * @return
     */
    public MainBean getMain(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.getJSONObject("data");
        MainBean mainBean =new MainBean();
        JSONArray jsonArray = object.getJSONArray("banner");
        if (jsonArray != null && jsonArray.length()>0){
            List<MainBean.BannerEntity> bannerData = new ArrayList<>();
            for (int i = 0 ; i <jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                MainBean.BannerEntity bannerEntity = new MainBean.BannerEntity();
                bannerEntity.setUrl(obj.getString("img"));
                bannerEntity.setLink(obj.getString("url"));
                bannerData.add(bannerEntity);
            }
            mainBean.setBannerData(bannerData);
        }
        JSONObject hot = object.getJSONObject("hot");
        mainBean.setHotImageUrl(hot.getString("img"));
        mainBean.setHotImageLink(hot.getString("url"));
        JSONArray array = object.getJSONArray("topic");
        if (array != null && array.length()>0){
            List<MainBean.MiddleEntity> middleData = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                JSONObject obj = array.getJSONObject(i);
                MainBean.MiddleEntity middleEntity = new MainBean.MiddleEntity();
                middleEntity.setUrl(obj.getString("img"));
                middleEntity.setLink(obj.getString("url"));
                middleData.add(middleEntity);
            }
            mainBean.setMiddleData(middleData);
        }
        return mainBean;
    }

    /**
     * 首页底部数据
     * @param jsonObject
     * @return
     */
    public List<MainBean.MainBottomEntity> getMainBottom(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.getJSONObject("data");
        List<MainBean.MainBottomEntity> data = new ArrayList<>();
        JSONArray jsonArray = object.getJSONArray("res");
        if (jsonArray != null && jsonArray.length()>0){
            for (int i=0;i<jsonArray.length();i++){
                MainBean.MainBottomEntity entity = new MainBean.MainBottomEntity();
                JSONObject obj = jsonArray.getJSONObject(i);
                entity.setPersonPic(obj.getString("avatar_url"));
                entity.setPersonName(obj.getString("user_nick"));
                entity.setPersonFrom(obj.getString("location"));
                entity.setIsIdentify(obj.getString("is_identity"));
                entity.setWayText(obj.getString("way_text"));
                if (obj.has("imgs")){
                    /**这是多图的模式*/
                    entity.setIsOne("0");
                    JSONArray array = obj.getJSONArray("imgs");
                    List<String> images = new ArrayList<>();
                    for (int j =0;j <array.length();j++){
                        JSONObject o = array.getJSONObject(j);
                        images.add(o.getString("url"));
                    }
                    entity.setImages(images);
                }else {
                    entity.setIsOne("1");
                    entity.setMainImage(obj.getString("img_src"));
                }
                entity.setTitle(obj.getString("title"));
                entity.setWay(obj.getString("way"));
                entity.setPostAge(obj.getString("postage"));
                entity.setNewOld(obj.getString("new_old"));
                entity.setSellingPrice(obj.getString("selling_price"));/**押金*/
                entity.setPrice(obj.getInt("rent_price"));
                data.add(entity);
            }
        }
        return data;
    }
}
