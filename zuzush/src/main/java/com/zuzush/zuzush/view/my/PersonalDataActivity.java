package com.zuzush.zuzush.view.my;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bigkoo.pickerview.TimePickerView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.PersonalDataAdapter;
import com.zuzush.zuzush.bean.BaseBean;
import com.zuzush.zuzush.bean.PersonalDataBean;
import com.zuzush.zuzush.bean.UpLoadBean;
import com.zuzush.zuzush.overrideview.MyDecoration;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.util.NetworkUtil;
import com.zuzush.zuzush.util.compress.Junliu;
import com.zuzush.zuzush.util.compress.OnCompressListener;
import com.zuzush.zuzush.view.base.BaseActivity;
import com.zuzush.zuzush.view.publish.IUpLoadView;
import com.zuzush.zuzush.view.setting.ModifySexActivity;
import com.zuzush.zuzush.view.setting.PersonalIntroduceActivity;
import com.zuzush.zuzush.view.setting.SettingActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liujun on 2017/9/14 0014.
 * 个人资料
 */
public class PersonalDataActivity extends BaseActivity implements INormalView,OnClickListener,PersonalDataAdapter.OnitemClick{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyDecoration myDecoration;
    private PersonalDataAdapter adapter;
    private MainPresenter presenter;
    private ImageView imageBack;
    private MainPresenter upLoadPresenter;
    private PersonalDataBean bean;
    private String url;
    private Map<String,String> map;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        init();
    }

    private void init() {
        presenter = new MainPresenter(this ,this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myDecoration = new MyDecoration(this,R.color.divider_color,LinearLayoutManager.HORIZONTAL, CommonUtil.dip2px(this,1),0);
        recyclerView.addItemDecoration(myDecoration);
        imageBack = (ImageView) findViewById(R.id.image_topBack);
        imageBack.setOnClickListener(this);
        map = new HashMap<>();
    }
    private void getPersonal(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            toast(this,Constants.NETWORK_ERROR);
            return;
        }
        cancelable = presenter.getPersonal();
    }
    @Override
    public String getUrl() {
        return url;
    }

    @Override
    protected void onResume() {
        super.onResume();
        url = Constants.personalData;
        getPersonal();
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
        if (args != null && args.length>0){
            if (args[0] instanceof PersonalDataBean){
                PersonalDataBean bean = (PersonalDataBean) args[0];
                this.bean = bean;
                if (adapter == null){
                    adapter = new PersonalDataAdapter(bean , this ,this);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter.reflashData(bean,0);
                }
            }else if (args[0] instanceof String && "success".equals(args[0])){
                bean.getContent().get(2).setValue(selectValue);
                if (adapter != null) adapter.reflashData(bean,0);
                toast(this,"修改成功");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_topBack:
                this.finish();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        switch (position){
            case 0:/**修改昵称*/
                intent.setClass(this , ModifyNickNameActivity.class);
                startActivity(intent);
                break;

            case 1:/**修改性别*/
                intent.setClass(this, ModifySexActivity.class);
                intent.putExtra("flag","sex");
                intent.putExtra("data",bean);
                startActivity(intent);
                break;

            case 2:/**修改出生*/
                showPickerView();
                break;

            case 3:/**修改性别 选择职业*/
                intent.setClass(this, ModifySexActivity.class);
                intent.putExtra("flag","occup");
                intent.putExtra("data",bean);
                startActivity(intent);
                break;

            case 4:/**个人简介*/
                intent.setClass(this, PersonalIntroduceActivity.class);
                intent.putExtra("data",bean);
                startActivity(intent);
                break;

            case 6:/**收货地址*/
                intent.setClass(this , AddressManageActivity.class);
                startActivity(intent);
                break;

            case 7:
                intent.setClass(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
    /**
     * 修改图像
     * @param view
     */
    @Override
    public void onPicClick(View view) {
        Matisse.from(this).choose(MimeType.ofImage()).countable(false).
                maxSelectable(1).gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.image_size)).capture(true).
                captureStrategy(new CaptureStrategy(true,"com.zuzush.zuzush.fileprovider")).
                restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).thumbnailScale(1.0f).theme(R.style.Matisse_Zhihu).
                imageEngine(new GlideEngine()).forResult(200);
        if (upLoadPresenter == null) upLoadPresenter = new MainPresenter(this,new UpLoad());
    }
    private File file;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK){
            /**修改图像*/
            String uri = Matisse.obtainPathResult(data).get(0);
            Junliu.with(this).load(uri).ignoreBy(50).setCompressListener(new CompressListener()).launch();
        }
    }
    public class CompressListener implements OnCompressListener {
        @Override
        public void onStart() {}
        @Override
        public void onSuccess(File file) {
            PersonalDataActivity.this.file = file;
            upLoadPresenter.upLoadPic();
        }
        @Override
        public void onError(Throwable e) {}
    }
    /**
     * 修改图像接口
     */
    public class UpLoad implements IUpLoadView{
        @Override
        public void analysisFailer(String info) {
            toast(PersonalDataActivity.this,info);
        }
        @Override
        public void httpError(String error) {
            toast(PersonalDataActivity.this,Constants.timeOut);
        }
        @Override
        public void getDataFailer(BaseBean bean) {
            if (bean != null) toast(PersonalDataActivity.this,bean.getInfo());
        }
        @Override
        public void getDataSuccess(Object[] args) {
            bean.setFile(file);
            if (adapter != null)adapter.reflashData(bean,1);
            toast(PersonalDataActivity.this,"图像修改成功");
        }
        @Override
        public String getUpLoadUrl() {
            return Constants.modifyIcon;
        }
        @Override
        public File getUri() {
            return file;
        }
        @Override
        public void upLoadFinished() {}
        @Override
        public void loadProcess(UpLoadBean bean) {}
    }
    private String selectValue;
    /**
     * 选择出生时间
     */
    private void showPickerView(){
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1900,0,1);
        endDate.set(endDate.get(Calendar.YEAR),endDate.get(Calendar.MONTH),endDate.get(Calendar.DAY_OF_MONTH));
        TimePickerView timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String year = new SimpleDateFormat("yyyy").format(date);
                String month = new SimpleDateFormat("MM").format(date);
                String day = new SimpleDateFormat("dd").format(date);
                selectValue = year+"年"+month+"月"+day+"日";
                if (bean != null){
                    map.put("sex",bean.getSex());
                    map.put("birth_year",year);
                    map.put("birth_month",month);
                    map.put("birth_day",day);
                    map.put("occup",bean.getOccup());
                    map.put("introduce",bean.getIntroduce());
                    url = Constants.modifyPersonalData;
                    cancelable = presenter.modifyPersonalData(map);
                }
            }
        }).setCancelText("取消").setSubmitText("确定").setTitleText("日期选择").
                setTitleColor(ContextCompat.getColor(this,R.color.top_bar_textColor)).setSubmitColor(ContextCompat.getColor(this,R.color.main_blue_color)).
                setCancelColor(ContextCompat.getColor(this,R.color.top_bar_textColor)).setContentSize(20).setType(new boolean[]{true,true,true,false,false,false}).
                setRangDate(startDate,endDate).isDialog(true).build();
        timePickerView.setDate(Calendar.getInstance());
        timePickerView.show();
    }
}
