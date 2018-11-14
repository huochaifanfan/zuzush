package com.zuzush.zuzush.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.UpLoadBean;
import com.zuzush.zuzush.overrideview.ProcessImageView;
import com.zuzush.zuzush.util.CommonUtil;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * Created by liujun on 2017/8/10 0010.
 * 上传图片用的
 */
public class AddPicAdapter extends BaseAdapter {
    private List<UpLoadBean.UriPicBean> data;
    private LayoutInflater inflater;
    private Context context;
    private int count;
    private int width;
    public AddPicAdapter(List<UpLoadBean.UriPicBean> data, Context context,int count,Activity activity) {
        this.data = data;
        this.context = context;
        this.count = count;
        inflater = LayoutInflater.from(context);
        width = (CommonUtil.getScreenInfo(activity)[0] - CommonUtil.dip2px(context ,48))/4;
    }
    @Override
    public int getCount() {
        if (data.size() == count){
            return count;
        }
        return data.size()+1;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.add_picture_item , parent , false);
        ProcessImageView image = (ProcessImageView) convertView.findViewById(R.id.item_grida_image);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
        params.height = width;
        image.setLayoutParams(params);
        ImageView imageDelete = (ImageView) convertView.findViewById(R.id.image_delete);
        TextView textMain = (TextView) convertView.findViewById(R.id.text_main);
        if (position == data.size()){
            image.setProgress(100);
            imageDelete.setVisibility(View.GONE);
            Glide.with(context).load(R.mipmap.add_pic).into(image);
            if (position == count) image.setVisibility(View.GONE);

        }else {
            if (position == 0){
                textMain.setVisibility(View.VISIBLE);
            }else {
                textMain.setVisibility(View.GONE);
            }
            UpLoadBean.UriPicBean bean = data.get(position);
            Glide.with(context).load(bean.getUri()).thumbnail(0.1f).into(image);
            image.setProgress(bean.getProgress());
            imageDelete.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
    public void updateData(List<UpLoadBean.UriPicBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
}
