package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.MainBean;
import com.zuzush.zuzush.overrideview.BannerLayout;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.GlideImageLoader;
import com.zuzush.zuzush.util.GridDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujun on 2017/9/19 0019.
 * 首页adapter
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BannerLayout.OnBannerItemClickListener,
        View.OnClickListener,MainMiddleAdapter.OnImageViewClickListener{
    private MainBean bean;
    private Context context;
    private LayoutInflater inflater;
    private List<MainBean.BannerEntity> bannerData;
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;
    private OnAdapterViewClick onAdapterViewClick;

    public MainAdapter(MainBean bean, Context context ,OnAdapterViewClick onAdapterViewClick) {
        this.bean = bean;
        this.context = context;
        this.onAdapterViewClick = onAdapterViewClick;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return ITEM_TYPE1;
        if (position == 1) return ITEM_TYPE2;
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE1) return new TopViewHolder(inflater.inflate(R.layout.main_banner,parent,false));
        if (viewType == ITEM_TYPE2) return new MiddleViewHolder(inflater.inflate(R.layout.main_middle,parent,false));
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder){
            bindType1((TopViewHolder) holder);
        }else if (holder instanceof MiddleViewHolder){
            bindType2((MiddleViewHolder) holder);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onClick(View v) {
        if (onAdapterViewClick != null) onAdapterViewClick.onTopImageClick((String) v.getTag());
    }

    @Override
    public void onImageViewClick(String link) {
        if (onAdapterViewClick != null) onAdapterViewClick.onMiddleImageClick(link);
    }

    public interface OnAdapterViewClick{
        void onBannerClick(int position, String link);
        void onTopImageClick(String link);
        void onMiddleImageClick(String link);
    }
    @Override
    public void onItemClick(int position) {
        String link = "";
        if (bannerData!= null) link = bannerData.get(position).getLink();
        if (onAdapterViewClick != null) onAdapterViewClick.onBannerClick(position,link);
    }

    public class TopViewHolder extends RecyclerView.ViewHolder{
        public BannerLayout bannerLayout;
        public ImageView imageView;
        public TopViewHolder(View itemView) {
            super(itemView);
            bannerLayout = (BannerLayout) itemView.findViewById(R.id.banner_layout);
            imageView = (ImageView) itemView.findViewById(R.id.image_banner);
        }
    }

    private class MiddleViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public MiddleViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        }
    }
    private void bindType1(TopViewHolder holder){
        bannerData = new ArrayList<>();
        List<String> banners = new ArrayList<>();
        if (bean != null) {
            bannerData = bean.getBannerData();
            if (bannerData != null && bannerData.size() > 0) {
                if (banners != null && banners.size() > 0) banners.removeAll(banners);
                for (int i = 0; i < bannerData.size(); i++) {
                    banners.add(bannerData.get(i).getUrl());
                }
                holder.bannerLayout.setImageLoader(new GlideImageLoader());
                holder.bannerLayout.setViewUrls(banners);
                holder.bannerLayout.setOnBannerItemClickListener(this);

            }
            Glide.with(context).load(bean.getHotImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
            holder.imageView.setTag(bean.getHotImageLink());
            holder.imageView.setOnClickListener(this);
        }
    }
    private void bindType2(MiddleViewHolder holder){
        if (bean != null) {
            List<MainBean.MiddleEntity> data = bean.getMiddleData();
            if (data != null && data.size()>0){
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);
                GridDecoration decoration = new GridDecoration(context,R.color.background_white,CommonUtil.dip2px(context,5),CommonUtil.dip2px(context ,5));
                holder.recyclerView.setLayoutManager(layoutManager);
                holder.recyclerView.addItemDecoration(decoration);
                MainMiddleAdapter adapter = new MainMiddleAdapter(data,context,this);
                holder.recyclerView.setAdapter(adapter);
            }
        }
    }
}
