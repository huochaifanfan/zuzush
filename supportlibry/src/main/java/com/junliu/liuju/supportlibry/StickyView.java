package com.junliu.liuju.supportlibry;

import android.view.View;

/**
 * Created by liuju on 2018/3/28.
 */

public interface StickyView {
    /**是否是吸附view*/
    boolean isStickyView(View view);
    /**得到吸附view的viewType*/
    int getStikeyViewType();
}
