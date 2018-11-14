package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.ClassifyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujun on 2017/9/27 0027.
 * 分类
 */

public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater inflater;
    private List<ClassifyBean> data;
    private OnitemClickListener onitemClickListener;
    private List<ClassifyBean.ClassifyRightBean> rightData;
    private Context context;
    private int flag;
    private List<Boolean> isLeftClicks;
    public ClassifyAdapter(List<ClassifyBean> data, Context context,OnitemClickListener onitemClickListener,int flag,
                           List<ClassifyBean.ClassifyRightBean> rightData){
        this.data = data;
        this.onitemClickListener = onitemClickListener;
        this.flag = flag;
        this.rightData = rightData;
        this.context = context;
        inflater = LayoutInflater.from(context);
        isLeftClicks = new ArrayList<>();
        if (flag == 0){
            for (int i=0;i<data.size();i++){
                isLeftClicks.add(false);
            }
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.classify_item,parent,false);
        return new MyViewHolder(view);
    }
    public void notifyData(int flag,List<ClassifyBean.ClassifyRightBean> rightData){
        this.flag = flag;
        this.rightData = rightData;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (flag == 0) {
            ClassifyBean bean = data.get(position);
            holder.textView.setText(bean.getLeftName());
            holder.textView.setOnClickListener(this);
            holder.textView.setTag(R.id.left_id,position);
            if (isLeftClicks.get(position)){
                holder.textView.setBackgroundColor(ContextCompat.getColor(context,R.color.background_grey));
            }else {
                holder.textView.setBackgroundColor(ContextCompat.getColor(context,R.color.background_white));
            }
        }else {
            ClassifyBean.ClassifyRightBean bean = rightData.get(position);
            holder.textView.setText(bean.getRightName());
            holder.textView.setOnClickListener(this);
            holder.textView.setTag(R.id.right_id,position);
        }
    }
    @Override
    public int getItemCount() {
        if (flag == 0) {
            if (data == null) data = new ArrayList<>();
            return data.size();
        }
        if (rightData == null) rightData = new ArrayList<>();
        return rightData.size();
    }

    @Override
    public void onClick(View v) {
        if (onitemClickListener != null) {
            if (flag == 0){
                onitemClickListener.onitemClick((Integer) v.getTag(R.id.left_id));
                for (int i =0;i<isLeftClicks.size();i++){
                    isLeftClicks.set(i,false);
                }
                isLeftClicks.set((Integer) v.getTag(R.id.left_id),true);
                notifyDataSetChanged();
            }else {
                onitemClickListener.onRightClick((Integer) v.getTag(R.id.right_id));
            }
        }
    }
    public interface OnitemClickListener{
        void onitemClick(int position);
        void onRightClick(int position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_content);
        }
    }
}
