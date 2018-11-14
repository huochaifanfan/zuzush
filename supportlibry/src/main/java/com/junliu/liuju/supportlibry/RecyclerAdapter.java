package com.junliu.liuju.supportlibry;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junliu.liuju.supportlibry.bean.RecyclerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuju on 2018/3/28.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<RecyclerBean> data;
    private LayoutInflater inflater;

    public RecyclerAdapter(List<RecyclerBean> data , Context context) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 10){
            return new CountViewHolder(inflater.inflate(R.layout.layout_adapter_content,parent,false));
        }
        return new TitleViewHolder(inflater.inflate(R.layout.layout_adapter_header,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getItemType();
    }

    public class CountViewHolder extends RecyclerView.ViewHolder{
        public CountViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class TitleViewHolder extends RecyclerView.ViewHolder{
        public TitleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
