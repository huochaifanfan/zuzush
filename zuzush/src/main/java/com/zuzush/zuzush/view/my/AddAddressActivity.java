package com.zuzush.zuzush.view.my;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.AddressBean;
import com.zuzush.zuzush.bean.JsonBean;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.GetJsonDataUtil;
import com.zuzush.zuzush.util.JsonUtils;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.view.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/9/15 0015.
 * 增加收货地址  编辑收货地址
 */
public class AddAddressActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener,TextWatcher,IAddAddressView{
    @BindView(R.id.text_title) TextView textTitle;
    @BindView(R.id.text_delete) TextView textDelete;
    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_phone) EditText etPhone;
    @BindView(R.id.text_address) TextView textAddress;
    @BindView(R.id.et_address) EditText etAddress;
    @BindView(R.id.checkbox) CheckBox checkBox;
    @BindView(R.id.text_sure) TextView textSure;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private String edit;
    private MainPresenter presenter;
    private String isDefault;
    private AddressBean bean;
    private String autoId = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        edit = getIntent().getStringExtra("edit");
        bean = (AddressBean) getIntent().getSerializableExtra("bean");
        init();
    }

    private void init() {
        checkBox.setOnCheckedChangeListener(this);
        etName.addTextChangedListener(this);
        etPhone.addTextChangedListener(this);
        etAddress.addTextChangedListener(this);
        textAddress.addTextChangedListener(this);
        textSure.setEnabled(false);
        textSure.setBackgroundColor(ContextCompat.getColor(this ,R.color.textHint_color));
        presenter = new MainPresenter(this ,this);
        if ("edit".equals(edit)){
            textDelete.setVisibility(View.VISIBLE);
            textTitle.setText("编辑收货地址");
            if (bean != null){
                etName.setText(bean.getName());
                etName.setSelection(etName.getText().length());
                etPhone.setText(bean.getTelephone());
                textAddress.setText(bean.getArera().toString().trim());
                etAddress.setText(bean.getAddress());
                if ("1".equals(bean.getIsDefault())){
                    checkBox.setChecked(true);
                }else {
                    checkBox.setChecked(false);
                }
                autoId = bean.getAutoId();
            }
        }
    }

    @OnClick({R.id.text_delete,R.id.text_cancel,R.id.text_sure,R.id.text_address,R.id.image_topBack})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.text_delete:
                /**删除收货地址*/
                showNormalDialog();
                break;

            case R.id.text_cancel:
                this.finish();
                break;

            case R.id.text_sure:
                add();
                break;

            case R.id.text_address:
                cancelKeybord();
                initJsonData();
                break;

            case R.id.image_topBack:
                this.finish();
                break;
        }
    }

    @Override
    protected void onNormalDialogClick() {
        delete();
    }

    @Override
    protected String setContent() {
        return "确定要删除收货地址吗？";
    }

    private void cancelKeybord() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void add(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            toast(this,Constants.NETWORK_ERROR);
            return;
        }
        cancelable = presenter.addAddress();
    }
    private void delete(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            toast(this,Constants.NETWORK_ERROR);
            return;
        }
        cancelable = presenter.deleteAddress();
    }
    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+ options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);
                textAddress.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(ContextCompat.getColor(AddAddressActivity.this , R.color.divider_color))
                .setTextColorCenter(ContextCompat.getColor(AddAddressActivity.this , R.color.top_bar_textColor)) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }
    private void initJsonData() {//解析数据
        String jsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = JsonUtils.getInstance().getProvince(jsonData);
        options1Items = jsonBean;
        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {
                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            options2Items.add(CityList);
            options3Items.add(Province_AreaList);
        }
        ShowPickerView();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            checkBox.setBackgroundResource(R.drawable.zuzu_open);
            isDefault = "1";
        }else {
            checkBox.setBackgroundResource(R.drawable.zuzu_close);
            isDefault = "0";
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(etName.getText())&& !TextUtils.isEmpty(etPhone.getText())&&
                !TextUtils.isEmpty(etAddress.getText())&& !TextUtils.isEmpty(textAddress.getText())){
            textSure.setEnabled(true);
            textSure.setBackgroundColor(ContextCompat.getColor(this , R.color.main_blue_color));
        }else {
            textSure.setEnabled(false);
            textSure.setBackgroundColor(ContextCompat.getColor(this , R.color.textHint_color));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public String getAddUrl() {
        if ("edit".equals(edit)) return Constants.editAddress;
        return Constants.addAddress;
    }

    @Override
    public String getName() {
        return etName.getText().toString();
    }

    @Override
    public String getTelephone() {
        return etPhone.getText().toString();
    }

    @Override
    public String getArea() {
        return textAddress.getText().toString();
    }

    @Override
    public String getDetailAddress() {
        return etAddress.getText().toString();
    }

    @Override
    public String isDefault() {
        return isDefault;
    }

    @Override
    public String getAddressId() {
        return autoId;
    }

    @Override
    public String deleteUrl() {
        return Constants.deleteAddress;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args[0] instanceof String){
            if ("add".equals(args[0])){
                toast(this , "收货地址添加成功");
            }else if ("edit".equals(args[0])){
                toast(this,"收货地址编辑成功");
            }else if ("delete".equals(args[0])){
                toast(this,"收货地址删除成功");
            }
            this.finish();
        }
    }
}
