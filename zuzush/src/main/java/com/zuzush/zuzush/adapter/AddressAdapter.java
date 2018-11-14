package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.AddressBean;
import com.zuzush.zuzush.util.RegesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liujun on 2017/9/14 0014.
 * 收货地址管理
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> implements View.OnClickListener{
    private List<AddressBean> data;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public AddressAdapter(Context context,List<AddressBean> data , OnItemClickListener onItemClickListener) {
        this.data = data;
        this.onItemClickListener = onItemClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.address_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AddressBean bean = data.get(position);
        holder.textName.setText(bean.getName());
        holder.textPhone.setText(RegesUtils.makePhoneNunToStar(bean.getTelephone()));
        holder.textAddress.setText(bean.getArera()+"  "+bean.getAddress());
        holder.imageEdit.setOnClickListener(this);
        holder.imageEdit.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnItemClickListener{
        void onEditImageClick(int position);
    }
    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) onItemClickListener.onEditImageClick((Integer) v.getTag());
    }
    public void reflahData(List<AddressBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_edit) ImageView imageEdit;
        @BindView(R.id.text_name) TextView textName;
        @BindView(R.id.text_phone) TextView textPhone;
        @BindView(R.id.text_address) TextView textAddress;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
}
