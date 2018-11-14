package com.zuzush.zuzush.view.publish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/8/14 0014.
 * 发布物品首页
 */
public class PublishMainActivity extends BaseActivity {
    @BindView(R.id.image_rent_goods) ImageView rentGoods;
    @BindView(R.id.image_sol_goods) ImageView solGoods;
    @BindView(R.id.image_publish_service) ImageView publishService;
    @BindView(R.id.image_publish_help) ImageView publishHelp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_publish);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.image_topBack,R.id.publish_skill,R.id.publish_cheat,R.id.publish_deal,R.id.image_rent_goods,
            R.id.image_sol_goods,R.id.image_publish_service,R.id.image_publish_help})
    public void onViewClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.image_topBack:
                this.finish();
                break;

            /**发布技巧*/
            case R.id.publish_skill:
                intent.setClass(this , PublishWebView.class);
                intent.putExtra("url", Constants.publishSkill);
                intent.putExtra("title","发布技巧");
                startActivity(intent);
                break;

            /**防骗秘笈*/
            case R.id.publish_cheat:
                intent.setClass(this , PublishWebView.class);
                intent.putExtra("url", Constants.publishCheat);
                intent.putExtra("title","防骗指南");
                startActivity(intent);
                break;

            /**担保交易*/
            case R.id.publish_deal:
                intent.setClass(this , PublishWebView.class);
                intent.putExtra("url", Constants.publishDeal);
                intent.putExtra("title","担保交易");
                startActivity(intent);
                break;

            /**出租物品*/
            case R.id.image_rent_goods:
                intent.setClass(this , PublishActivity.class);
                startActivity(intent);
                break;

            /**出售物品*/
            case R.id.image_sol_goods:
                break;

            /**发布服务*/
            case R.id.image_publish_service:
                break;

            /**求助*/
            case R.id.image_publish_help:
                break;
        }
    }
}
