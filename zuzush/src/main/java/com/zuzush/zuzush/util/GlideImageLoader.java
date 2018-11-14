package com.zuzush.zuzush.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zuzush.zuzush.R;
import com.zuzush.zuzush.overrideview.BannerLayout;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public class GlideImageLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        /**placeholder 在图片没有加载好的时候显示一个占位图*/
        Glide.with(context).load(path).placeholder(R.mipmap.banner_no).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }
    @Override
    public void disPlayLocalResouce(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId).placeholder(R.mipmap.banner_no).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }
}
