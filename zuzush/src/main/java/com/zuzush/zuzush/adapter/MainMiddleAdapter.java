package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujun on 2017/9/20 0020.
 */

public class MainMiddleAdapter extends RecyclerView.Adapter<MainMiddleAdapter.MyViewHolder> implements View.OnClickListener{
    private List<MainBean.MiddleEntity> data;
    private Context context;
    private LayoutInflater inflater;
    private OnImageViewClickListener onImageViewClickListener;

    public MainMiddleAdapter(List<MainBean.MiddleEntity> data, Context context ,OnImageViewClickListener onImageViewClickListener) {
        this.data = data;
        this.context = context;
        this.onImageViewClickListener = onImageViewClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.main_middle_item,parent,false);
        return new MyViewHolder(view);
    }
    public interface OnImageViewClickListener{
        void onImageViewClick(String link);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.imageView.setTag(data.get(position).getLink());
        holder.imageView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if (onImageViewClickListener != null) onImageViewClickListener.onImageViewClick((String) v.getTag());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
