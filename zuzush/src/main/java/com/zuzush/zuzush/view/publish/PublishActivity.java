package com.zuzush.zuzush.view.publish;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.AddPicAdapter;
import com.zuzush.zuzush.adapter.PopOldAdapter;
import com.zuzush.zuzush.bean.LocationBean;
import com.zuzush.zuzush.bean.OldNewBean;
import com.zuzush.zuzush.bean.UpLoadBean;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.LocationUtil;
import com.zuzush.zuzush.util.compress.Junliu;
import com.zuzush.zuzush.util.compress.OnCompressListener;
import com.zuzush.zuzush.view.base.BaseActivity;
import com.zuzush.zuzush.view.base.PermissionsResultListener;
import com.zuzush.zuzush.view.my.PersonalDataActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/8/9 0009.
 * 发布物品
 */
public class PublishActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        PermissionsResultListener,LocationUtil.LocationResult,CompoundButton.OnCheckedChangeListener,IUpLoadView{
    @BindView(R.id.et_name) EditText edName;
    @BindView(R.id.et_des) TextView textDes;
    @BindView(R.id.noScrollgridview) GridView gridView;
    @BindView(R.id.tv_location) TextView textLocation;
    @BindView(R.id.text_warn) TextView textWarn;
    @BindView(R.id.text_rent_only) TextView textRent;
    @BindView(R.id.text_sold_only) TextView textSold;
    @BindView(R.id.text_rent_sold) TextView textRentSold;
    @BindView(R.id.text_sold_price) TextView textSoldPrice;
    @BindView(R.id.text_price) TextView textPrice;
    @BindView(R.id.rel_sold_price) RelativeLayout relSoldPrice;
    @BindView(R.id.rel_price_rent) RelativeLayout relRentPrice;
    @BindView(R.id.rel_desposit) RelativeLayout relDesposit;
    @BindView(R.id.rel_deadline) RelativeLayout relDeadLine;
    @BindView(R.id.rel_classify) RelativeLayout relClassify;
    @BindView(R.id.rel_continue) RelativeLayout relContinue;
    @BindView(R.id.checkbox) CheckBox checkBox;
    @BindView(R.id.linar) LinearLayout linearLayout;
    @BindView(R.id.text_new_old) TextView textNewOld;
    private View headerView;
    private AddPicAdapter adapter;
    private List<String> uriData;
    private List<String> tempData;
    private final int REQUEST_CODE_CHOOSE = 200;
    private PopOldAdapter popOldAdapter;
    private List<OldNewBean> popData = new ArrayList<>();
    private MainPresenter upLoadPresenter;
    private String uri;
    private List<UpLoadBean.UriPicBean> picData;
    private String oldNewId;
    private int uriPosition = 0;
    private String way;
    private Map<String,String> map;
    private String description;
    private String attention;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        gridView.setOnItemClickListener(this);
        uriData = new ArrayList<>();
        tempData = new ArrayList<>();
        picData = new ArrayList<>();
        headerView = LayoutInflater.from(this).inflate(R.layout.pop_headview , null);
        textWarn.setText(Html.fromHtml("重要提醒：出租物品一定要在图片和描述里说清楚配置清单、零件清单，避免纠纷。<font color='#4285F4'>查看出租流程</font>"));
        LocationUtil.getInstance(this,this).startLocation();
        textRent.setBackgroundResource(R.drawable.zuzu_button_background);
        way = "RENT";
        relSoldPrice.setVisibility(View.GONE);
        checkBox.setOnCheckedChangeListener(this);
        setData();
        upLoadPresenter = new MainPresenter(this ,this);
        popData.add(new OldNewBean("全新","100"));
        popData.add(new OldNewBean("99新","99"));
        popData.add(new OldNewBean("98新","98"));
        popData.add(new OldNewBean("95新","95"));
        popData.add(new OldNewBean("90新","90"));
        map = new HashMap<>();
    }
    @Override
    protected void onDestroy() {
        LocationUtil.getInstance(this,this).stopLocation();
        super.onDestroy();
    }
    @OnClick({R.id.image_topBack,R.id.et_des,R.id.rel_old,R.id.text_rent_only,R.id.text_sold_only,R.id.text_rent_sold,
            R.id.text_publish,R.id.rel_classify})
    public void onViewClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.image_topBack:
                this.finish();
                break;

            case R.id.et_des:
                String des;
                if (TextUtils.isEmpty(textDes.getText())){
                    des = "";
                }else {
                    des = textDes.getText().toString();
                }
                intent.setClass(this , GoodsDescription.class);
                intent.putExtra("des",des);
                intent.putExtra("attention",attention);
                startActivityForResult(intent , 200);
                break;

            case R.id.rel_old:
                /**选择新旧度的对话框*/
                showPopwindow();
                break;

            case R.id.text_rent_only:/**只租*/
                setBackground(textRent);
                relSoldPrice.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                relClassify.setVisibility(View.VISIBLE);
                relContinue.setVisibility(View.VISIBLE);
                way = "RENT";
                break;

            case R.id.text_sold_only:/**只售*/
                setBackground(textSold);
                relSoldPrice.setVisibility(View.VISIBLE);
                textSoldPrice.setText("价格");
                textPrice.setText("输入一口价");
                linearLayout.setVisibility(View.GONE);
                relClassify.setVisibility(View.VISIBLE);
                relContinue.setVisibility(View.GONE);
                way = "SELL";
                break;

            case R.id.text_rent_sold:/**可租可售*/
                setBackground(textRentSold);
                relSoldPrice.setVisibility(View.VISIBLE);
                textSoldPrice.setText("出售价格");
                textPrice.setText("一口价");
                linearLayout.setVisibility(View.VISIBLE);
                relClassify.setVisibility(View.VISIBLE);
                relContinue.setVisibility(View.VISIBLE);
                way = "RENT_SELL";
                break;

            case R.id.text_publish:/**确认发布*/
                publishGoods();
                break;

            case R.id.rel_classify:/**选择分类*/
                intent.setClass(this , ClassifyActivity.class);
                startActivityForResult(intent,200);
                break;
        }
    }
    /**
     * 发布物品
     */
    private void publishGoods(){
        map.put("way",way);
        if (TextUtils.isEmpty(edName.getText())){
            toast(this,"请输入物品名称");
            return;
        }else if (attention == null || "".equals(attention)){
            toast(this,"请输入物品清单和注意事项");
            return;
        }
        map.put("title",edName.getText().toString());
        map.put("describe",description);
        map.put("notice",attention);
        /**改地方先写死 便于测试*/
        map.put("mainImgID",picId.get(0));
        map.put("imgs[]",picId.get(1));
        map.put("cate_id","1");/**一级分类id*/
        map.put("cate_subset_id","2");/**二级分类id*/

    }
    private void setBackground(TextView textView){
        textRent.setTextColor(ContextCompat.getColor(this,R.color.top_bar_textColor));
        textSold.setTextColor(ContextCompat.getColor(this,R.color.top_bar_textColor));
        textRentSold.setTextColor(ContextCompat.getColor(this,R.color.top_bar_textColor));
        textRent.setBackgroundResource(0);
        textSold.setBackgroundResource(0);
        textRentSold.setBackgroundResource(0);
        textView.setTextColor(ContextCompat.getColor(this,R.color.background_white));
        textView.setBackgroundResource(R.drawable.zuzu_button_background);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (picData != null && position == picData.size()){
            /**首先获取读写SD卡权限*/
            performRequestPermissions("租租想要获取您的储存空间",new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_CHOOSE,this);
        }else {
            /**删除图片*/
            if (picData != null){
                picData.remove(position);
                if (picId != null) picId.remove(position);
                setData();
            }
        }
    }
    @Override
    public void onPermissionGranted() {
        selectedPicture();
    }
    private void selectedPicture(){
        int count=0;
        if (uriData == null){
            count = 8;
        }else if (uriData != null && uriData.size()<8){
            count = 8- uriData.size();
        }
        Matisse.from(this).choose(MimeType.ofImage()).countable(false).
                maxSelectable(count).gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.image_size)).capture(true).
                captureStrategy(new CaptureStrategy(true,"com.zuzush.zuzush.fileprovider")).
                restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).thumbnailScale(1.0f).theme(R.style.Matisse_Zhihu).
                imageEngine(new GlideEngine()).forResult(REQUEST_CODE_CHOOSE);
    }
    @Override
    public void onPermissionDenied() {}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK){
            if (tempData == null) tempData = new ArrayList<>();
            if (tempData != null && tempData.size()>0) tempData.removeAll(tempData);
            tempData = Matisse.obtainPathResult(data);
            uriData.addAll(tempData);
            if (uriData != null && uriData.size()>0){
                if (picData != null) picData.removeAll(picData);
                for (int i =0;i < uriData.size();i++){
                    UpLoadBean.UriPicBean bean = new UpLoadBean.UriPicBean();
                    bean.setUri(uriData.get(i));
                    bean.setProgress(0);
                    picData.add(bean);
                }
            }
            setData();
            /**上传图片到服务器*/
        upLoadPicture();
        }else if (requestCode == 200 && resultCode == 300){
            description = data.getStringExtra("des");
            attention = data.getStringExtra("attention");
            textDes.setText(description);
        }else  if (requestCode == 200 && resultCode == 500){
            textDes.setText(null);
            textDes.setHint("描述你的宝贝");
            attention = null;
        }else if (requestCode == 200 && requestCode ==1){
            /**选择分类*/
        }
    }
    /**
     * 上传图片到服务器
     */
    private void upLoadPicture(){
        if (uriData == null) return;
        if (uriData.size() == 0) return;
        if (uriPosition < uriData.size()){
            uri = uriData.get(uriPosition);
            /**压缩图片*/
            Junliu.with(this).load(uri).ignoreBy(50).setCompressListener(new CompressListener()).launch();
        }
    }
    private File file;
    public class CompressListener implements OnCompressListener {
        @Override
        public void onStart() {}
        @Override
        public void onSuccess(File file) {
            PublishActivity.this.file = file;
            upLoadPresenter.upLoadPic();
        }
        @Override
        public void onError(Throwable e) {}
    }
    private void setData(){
        if (uriData == null){
            uriData = new ArrayList<>();
            if (adapter == null){
                adapter = new AddPicAdapter(picData ,this,8,this);
                gridView.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }
        }else {
            if (adapter == null){
                adapter = new AddPicAdapter(picData ,this,8,this);
                gridView.setAdapter(adapter);
            }else {
                adapter.updateData(picData);
            }
        }
    }
    @Override
    public void getLocation(LocationBean bean) {
        if (bean != null){
            textLocation.setText(bean.getCity()+"\t"+bean.getDistrict());
            toast(this,bean.getCity()+"\t"+bean.getDistrict());
        }
    }
    /**
     * 显示新旧程度的对话框
     */
    private void showPopwindow(){
        View view = LayoutInflater.from(this).inflate(R.layout.pop_old_item,null);
        final PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        ListView listView = (ListView) view.findViewById(R.id.pop_listView);
        listView.addHeaderView(headerView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >0){
                    textNewOld.setText(popData.get(position-1).getContent());
                    oldNewId = popData.get(position-1).getOldId();
                    if (popupWindow != null) popupWindow.dismiss();
                }
            }
        });
        if (popOldAdapter == null){
            popOldAdapter = new PopOldAdapter(this, popData);
            listView.setAdapter(popOldAdapter);
        }else {
            popOldAdapter = null;
            popOldAdapter = new PopOldAdapter(this, popData);
            listView.setAdapter(popOldAdapter);
        }
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(findViewById(R.id.rel_old),0,-CommonUtil.dip2px(this ,30));
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            checkBox.setBackgroundResource(R.drawable.zuzu_open);
        }else {
            checkBox.setBackgroundResource(R.drawable.zuzu_close);
        }
    }
    @Override
    public String getUpLoadUrl() {
        return Constants.upLoadPicture;
    }
    private List<String> picId = new ArrayList<>();
    @Override
    public File getUri() {
        return file;
    }
    @Override
    public void upLoadFinished() {
        uriPosition ++;
        upLoadPicture();
    }
    @Override
    public void loadProcess(UpLoadBean bean) {
        if (bean != null){
            int progress = (int) (bean.getCurrentLength()*100.0/bean.getTotalLength());
            if (picData != null){
                if (uriPosition >0){
                    for (int i = 0; i < uriPosition; i++) {
                        picData.get(i).setProgress(100);
                    }
                }
                picData.get(uriPosition).setProgress(progress);
                adapter.updateData(picData);
            }
            Log.i("progress",progress+"");
        }
    }
    @Override
    public void getDataSuccess(Object[] args) {
        /**上传图片成功*/
        if (args != null && args.length>0){
            if (args[0] instanceof UpLoadBean && args[0]!=null){
                UpLoadBean bean = (UpLoadBean) args[0];
                picId.add(bean.getImageId());
                toast(this,"上传成功");
                Log.i("picid",picId.size()+picId.toString());
            }
        }
    }
}
