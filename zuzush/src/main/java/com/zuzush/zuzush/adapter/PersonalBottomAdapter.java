package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.PersonalDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/14 0014.
 */
public class PersonalBottomAdapter extends RecyclerView.Adapter<PersonalBottomAdapter.MyViewHolder> implements View.OnClickListener{
    private List<PersonalDataBean.PersonalDataBottom> data;
    private LayoutInflater inflater;
    private OnItemClick onItemClick;

    public PersonalBottomAdapter(List<PersonalDataBean.PersonalDataBottom> data, Context context,OnItemClick onItemClick) {
        this.data = data;
        this.onItemClick = onItemClick;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.personal_data_bottom,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PersonalDataBean.PersonalDataBottom bean = data.get(position);
        if (position == 5){
            holder.imageInfo.setVisibility(View.GONE);
        }else {
            holder.imageInfo.setVisibility(View.VISIBLE);
        }
        holder.textName.setText(bean.getName());
        holder.textValue.setText(bean.getValue());
        holder.relativeLayout.setOnClickListener(this);
        holder.relativeLayout.setTag(position);
    }
    public void reflashData(List<PersonalDataBean.PersonalDataBottom> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnItemClick{
        void onItemClick(int position);
    }
    @Override
    public void onClick(View v) {
        if (onItemClick != null) onItemClick.onItemClick((Integer) v.getTag());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text_content) TextView textValue;
        @BindView(R.id.text_name) TextView textName;
        @BindView(R.id.rel_total) LinearLayout relativeLayout;
        @BindView(R.id.more_info) ImageView imageInfo;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
