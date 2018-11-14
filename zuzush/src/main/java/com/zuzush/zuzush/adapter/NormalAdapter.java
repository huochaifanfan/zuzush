package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zuzush.zuzush.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujun on 2017/9/18 0018.
 * 通用的adapter
 */

public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.MyViewHolder> implements OnClickListener{
    private List<String> data;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public NormalAdapter(Context context, List<String> data,OnItemClickListener onItemClickListener) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.normal_layout_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textName.setText(data.get(position));
        holder.relativeLayout.setOnClickListener(this);
        holder.relativeLayout.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnItemClickListener{
        void onClick(int position);
    }
    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) onItemClickListener.onClick((Integer) v.getTag());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textName;
        public RelativeLayout relativeLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_name);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rel_total);
        }
    }
}
