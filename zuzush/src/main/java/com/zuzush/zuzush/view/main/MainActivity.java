package com.zuzush.zuzush.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.util.ExitApp;
import com.zuzush.zuzush.util.UiUtils;
import com.zuzush.zuzush.view.base.BaseActivity;
import com.zuzush.zuzush.view.classify.ClassifyFragment;
import com.zuzush.zuzush.view.message.MsgFragment;
import com.zuzush.zuzush.view.my.MyFragment;
import com.zuzush.zuzush.view.publish.PublishMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.image_main) ImageView imageMain;
    @BindView(R.id.image_classify) ImageView imageClassify;
    @BindView(R.id.image_msg) ImageView imageMsg;
    @BindView(R.id.image_my) ImageView imageMy;
    @BindView(R.id.text_main) TextView textMain;
    @BindView(R.id.text_classify) TextView textClassify;
    @BindView(R.id.text_msg) TextView textMsg;
    @BindView(R.id.text_my) TextView textMy;
    @BindView(R.id.image_publish) FloatingActionButton btnPublish;
    private int selectedColor;
    private int unSelctedColor;
    private Fragment[] fragments;
    private int currentIndex = 0;
    private int index = 0;
    private int back = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.setTransluent(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            selectedColor = ContextCompat.getColor(this, R.color.main_blue_color);
            unSelctedColor = ContextCompat.getColor(this, R.color.main_text_color);
            btnPublish.setOnClickListener(this);
            fragments = new Fragment[4];
            fragments[0] = new MainFragment();
            fragments[1] = new ClassifyFragment();
            fragments[2] = new MsgFragment();
            fragments[3] = new MyFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frame_content, fragments[0]);
            fragmentTransaction.show(fragments[0]);
            fragmentTransaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }
    /**
     * 切换页面显示
     * @param index
     */
    private void changePage(int index){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if (currentIndex != index){
            fragmentTransaction.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) fragmentTransaction.add(R.id.frame_content,fragments[index]);
            fragmentTransaction.show(fragments[index]);
            fragmentTransaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        currentIndex = index;
    }
    @OnClick({R.id.rel_main,R.id.rel_classify,R.id.rel_msg,R.id.rel_my})
    public void onViewClick(View view){
        resetColor();
        switch (view.getId()){
            case R.id.rel_main:
                index = 0;
                imageMain.setImageResource(R.mipmap.main_selected);
                textMain.setTextColor(selectedColor);
                break;

            case R.id.rel_classify:
                imageClassify.setImageResource(R.mipmap.classify_slected);
                textClassify.setTextColor(selectedColor);
                index = 1;
                break;

            case R.id.rel_msg:
                imageMsg.setImageResource(R.mipmap.msg_selected);
                textMsg.setTextColor(selectedColor);
                index = 2;
                break;

            case R.id.rel_my:
                imageMy.setImageResource(R.mipmap.my_selected);
                textMy.setTextColor(selectedColor);
                index = 3;
                break;
        }
        changePage(index);
    }
    private void resetColor(){
        imageMain.setImageResource(R.mipmap.main_unselected);
        imageClassify.setImageResource(R.mipmap.classify_unslected);
        imageMsg.setImageResource(R.mipmap.msg_unselected);
        imageMy.setImageResource(R.mipmap.my_unselected);
        textMain.setTextColor(unSelctedColor);
        textClassify.setTextColor(unSelctedColor);
        textMsg.setTextColor(unSelctedColor);
        textMy.setTextColor(unSelctedColor);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            back ++;
            if (back == 1) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        back = 0;
                    }
                }, 2000);
            }
            if (back ==2) ExitApp.exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.image_publish){
            Intent intent = new Intent(this, PublishMainActivity.class);
            startActivity(intent);
        }
    }
}
