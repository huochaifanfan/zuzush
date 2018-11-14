package com.zuzush.zuzush.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.bean.PersonalDataBean;
import com.zuzush.zuzush.overrideview.MyDecoration;
import com.zuzush.zuzush.util.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/14 0014.
 */
public class PersonalDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements PersonalBottomAdapter.OnItemClick,
        View.OnClickListener{
    private static final int TYPE_TIEM1 = 1;
    private static final int TYPE_TIEM2 = 2;
    private PersonalDataBean bean;
    private Context context;
    private LayoutInflater inflater;
    private OnitemClick onitemClick;
    private int flag;

    public PersonalDataAdapter(PersonalDataBean bean, Context context,OnitemClick onitemClick) {
        this.bean = bean;
        this.context = context;
        this.onitemClick = onitemClick;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TIEM1:
                return new TopViewHolder(inflater.inflate(R.layout.personal_data_top , parent , false));
            case TYPE_TIEM2:
                return new BottomViewHolder(inflater.inflate(R.layout.personal_bottom_item ,parent , false));
            default:return null;
        }
    }
    public void reflashData(PersonalDataBean bean,int flag){
        this.bean = bean;
        this.flag = flag;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder){
            bindTop((TopViewHolder) holder);
        }else if (holder instanceof BottomViewHolder){
            bindBottom((BottomViewHolder) holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_TIEM1;
        if (position == 1) return TYPE_TIEM2;
        return 0;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onClick(View v) {
        if (onitemClick != null) onitemClick.onPicClick(v);
    }

    public interface OnitemClick{
        void onItemClick(int position);
        void onPicClick(View view);
    }
    @Override
    public void onItemClick(int position) {
        if (onitemClick != null) onitemClick.onItemClick(position);
    }
    public class TopViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text_progress) TextView textProgress;
        @BindView(R.id.progressBar) ProgressBar progressBar;
        @BindView(R.id.image_pic) CircleImageView imagePic;
        @BindView(R.id.rel_picture) RelativeLayout relativeLayout;
        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    public class BottomViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public BottomViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        }
    }
    private void bindTop(TopViewHolder holder){
        if (bean != null){
            holder.textProgress.setText((int) (bean.getProgress()*100)+"");
            holder.progressBar.setProgress((int) (bean.getProgress()*100));
            if (flag == 1){
                Glide.with(context).load(bean.getFile()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imagePic);
            }else {
                Glide.with(context).load(bean.getPicture()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imagePic);
            }
            holder.relativeLayout.setOnClickListener(this);
        }
    }
    private PersonalBottomAdapter adapter;
    private void bindBottom(BottomViewHolder holder){
        if (bean != null){
            if (adapter == null) {
                adapter = new PersonalBottomAdapter(bean.getContent(), context, this);
                holder.recyclerView.setFocusable(false);
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                holder.recyclerView.addItemDecoration(new MyDecoration(context, R.color.divider_color, LinearLayoutManager.HORIZONTAL,
                        CommonUtil.dip2px(context, 1), CommonUtil.dip2px(context, 11)));
                holder.recyclerView.setAdapter(adapter);
            }else {
                adapter.reflashData(bean.getContent());
            }
        }
    }
}
