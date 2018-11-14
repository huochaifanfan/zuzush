package com.junliu.liuju.supportlibry;

import android.view.View;

/**
 * Created by liuju on 2018/3/28.
 */

public class ExampleStickyView implements StickyView {
    @Override
    public boolean isStickyView(View view) {
        return (boolean) view.getTag();
    }

    @Override
    public int getStikeyViewType() {
        return 11;
    }
}
