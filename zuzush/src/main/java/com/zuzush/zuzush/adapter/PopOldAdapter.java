package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.OldNewBean;

import java.util.List;

/**
 * Created by liujun on 2017/8/14 0014.
 * 选择新旧程度的弹窗adapter
 */
public class PopOldAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<OldNewBean> data;

    public PopOldAdapter(Context context, List<OldNewBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }
    public void updateData(List<OldNewBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data.size();
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
        convertView = inflater.inflate(R.layout.pop_adapter_item , parent , false);
        TextView textView = (TextView) convertView.findViewById(R.id.text_content);
        textView.setText(data.get(position).getContent());
        return convertView;
    }
}
