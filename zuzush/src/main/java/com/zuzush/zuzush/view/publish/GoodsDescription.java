package com.zuzush.zuzush.view.publish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liujun on 2017/8/14 0014.
 * 物品描述
 */
public class GoodsDescription extends BaseActivity implements TextWatcher{
    @BindView(R.id.et_des) EditText etDes;
    @BindView(R.id.et_attention) EditText etAttention;
    @BindView(R.id.text_count) TextView textDesCount;
    @BindView(R.id.text_count1) TextView textAttentionCount;
    private String description;
    private String attention;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_descripton);
        ButterKnife.bind(this);
        description = getIntent().getStringExtra("des");
        attention = getIntent().getStringExtra("attention");
        init();
    }
    private void init(){
        etDes.addTextChangedListener(this);
        etAttention.addTextChangedListener(this);
        if (!"".equals(description) && description != null) {
            etDes.setText(description);
            etDes.setSelection(description.length());
        }else if (!"".equals(attention) && attention != null){
            etAttention.setText(attention);
            etAttention.setSelection(attention.length());
        }
    }
    @OnClick({R.id.image_topBack,R.id.text_finish})
    public void onViewClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.image_topBack:
                this.setResult(500 , intent);
                this.finish();
                break;

            /**完成*/
            case R.id.text_finish:
                if (TextUtils.isEmpty(etDes.getText())){
                    toast(this,"请输入物品描述");
                    return;
                }
                if (TextUtils.isEmpty(etAttention.getText())){
                    toast(this,"请输入物品清单和注意事项");
                    return;
                }
                intent.putExtra("des",etDes.getText().toString());
                intent.putExtra("attention",etAttention.getText().toString());
                this.setResult(300 ,intent);
                this.finish();
                break;
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(etDes.getText())){
            textDesCount.setText(etDes.getText().length()+"/1000");
        }else {
            textDesCount.setText("0/1000");
        }
        if (!TextUtils.isEmpty(etAttention.getText())){
            textAttentionCount.setText(etAttention.getText().length()+"/1000");
        }else {
            textAttentionCount.setText("0/1000");
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            this.setResult(500 , intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
