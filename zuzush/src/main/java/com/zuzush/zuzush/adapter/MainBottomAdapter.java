package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.MainBean;
import com.zuzush.zuzush.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liujun on 2017/10/10 0010.
 */

public class MainBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MainBean.MainBottomEntity> data;
    private Context context;
    private LayoutInflater inflater;
    private static final int TYPE_ITEM1 = 1;
    private static final int TYPE_ITEM2 = 2;

    public MainBottomAdapter(List<MainBean.MainBottomEntity> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ITEM1:
                return new OneViewHolder(inflater.inflate(R.layout.main_bottom_item1,parent,false));
            case TYPE_ITEM2:
                return new MoreViewHolder(inflater.inflate(R.layout.main_bottom_item2,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OneViewHolder){
            bindType1((OneViewHolder) holder,position);
        }else if (holder instanceof MoreViewHolder){
            bindType2((MoreViewHolder) holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ("0".equals(data.get(position).getIsOne())){
            return TYPE_ITEM2;
        }
        return TYPE_ITEM1;
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data.size();
    }
    public class OneViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_icon) CircleImageView circleImageView;
        @BindView(R.id.text_userNick) TextView textNickName;
        @BindView(R.id.text_from) TextView textFrom;
        @BindView(R.id.text_identify) TextView textIdentify;
        @BindView(R.id.image_one) ImageView imageView;
        @BindView(R.id.text_way) TextView textWay;
        @BindView(R.id.text_title) TextView textTitle;
        @BindView(R.id.text_price) TextView textPrice;
        @BindView(R.id.text_newOld) TextView textNewOld;
        @BindView(R.id.text_postage) TextView textPostAge;
        @BindView(R.id.text_day) TextView  textDay;
        public OneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public class MoreViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_icon) CircleImageView circleImageView;
        @BindView(R.id.text_userNick) TextView textNickName;
        @BindView(R.id.text_from) TextView textFrom;
        @BindView(R.id.text_identify) TextView textIdentify;
        @BindView(R.id.linearLayout) LinearLayout linearLayout;
        @BindView(R.id.text_way) TextView textWay;
        @BindView(R.id.text_title) TextView textTitle;
        @BindView(R.id.text_price) TextView textPrice;
        @BindView(R.id.text_newOld) TextView textNewOld;
        @BindView(R.id.text_postage) TextView textPostAge;
        @BindView(R.id.text_day) TextView  textDay;
        public MoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private void bindType1(OneViewHolder holder,int position){
        MainBean.MainBottomEntity entity = data.get(position);
        Glide.with(context).load(entity.getPersonPic()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.circleImageView);
        holder.textNickName.setText(entity.getPersonName());
        holder.textFrom.setText("来自  "+entity.getPersonFrom());
        holder.textWay.setText(entity.getWayText());
        holder.textIdentify.setText("1".equals(entity.getIsIdentify())?"已实名":"未实名");
        Glide.with(context).load(entity.getMainImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.textTitle.setText(entity.getTitle());
        if ("RENT".equals(entity.getWay())){
            holder.textPrice.setText(CommonUtil.formatTosepara(entity.getPrice()/100.0));
            holder.textDay.setVisibility(View.VISIBLE);
        }else {
            holder.textDay.setVisibility(View.GONE);
        }
        holder.textNewOld.setText(entity.getNewOld());
        holder.textPostAge.setText("1".equals(entity.getPostAge())?"包邮":"不包邮");
    }
    private void bindType2(MoreViewHolder holder,int position){
        MainBean.MainBottomEntity entity = data.get(position);
        Glide.with(context).load(entity.getPersonPic()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.circleImageView);
        holder.textNickName.setText(entity.getPersonName());
        holder.textFrom.setText("来自  "+entity.getPersonFrom());
        holder.textWay.setText(entity.getWayText());
        holder.textIdentify.setText("1".equals(entity.getIsIdentify())?"已实名":"未实名");
        if (holder.linearLayout != null) holder.linearLayout.removeAllViews();
        List<String> lumbers = entity.getImages();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(CommonUtil.dip2px(context,110), ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < lumbers.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            params.leftMargin = CommonUtil.dip2px(context,10);
            imageView.setLayoutParams(params);
            Glide.with(context).load(lumbers.get(i)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            holder.linearLayout.addView(imageView);
        }
        holder.textTitle.setText(entity.getTitle());
        if ("RENT".equals(entity.getWay())){
            holder.textPrice.setText(CommonUtil.formatTosepara(entity.getPrice()/100.0));
            holder.textDay.setVisibility(View.VISIBLE);
        }else {
            holder.textDay.setVisibility(View.GONE);
        }
        holder.textNewOld.setText(entity.getNewOld());
        holder.textPostAge.setText("1".equals(entity.getPostAge())?"包邮":"不包邮");
    }
}
