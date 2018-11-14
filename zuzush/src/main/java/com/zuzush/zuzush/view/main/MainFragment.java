package com.zuzush.zuzush.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zuzush.zuzush.R;
import com.zuzush.zuzush.adapter.MainAdapter;
import com.zuzush.zuzush.adapter.MainBottomAdapter;
import com.zuzush.zuzush.adapter.TradeFragmentPagerAdapter;
import com.zuzush.zuzush.bean.BaseBean;
import com.zuzush.zuzush.bean.MainBean;
import com.zuzush.zuzush.overrideview.MyDecoration;
import com.zuzush.zuzush.presenter.MainPresenter;
import com.zuzush.zuzush.util.CommonUtil;
import com.zuzush.zuzush.util.Constants;
import com.zuzush.zuzush.view.base.BaseFragment;
import com.zuzush.zuzush.view.my.DealRecordFragment;
import com.zuzush.zuzush.view.my.INormalView;
import com.zuzush.zuzush.view.publish.PublishWebView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liujun on 2017/9/13 0013.
 */
public class MainFragment extends BaseFragment implements INormalView,MainAdapter.OnAdapterViewClick {
    @BindView(R.id.recyclerView_top) RecyclerView recyclerViewTop;
//    @BindView(R.id.tabLayout) TabLayout tabLayout;
//    @BindView(R.id.linear_topbar) LinearLayout linearLayoutBar;
//    @BindView(R.id.viewPager_bottom) ViewPager viewPager;
    @BindView(R.id.recyclerview_bottom) RecyclerView recyclerViewBottom;
    private RecyclerView.LayoutManager layoutManager;
    private MainAdapter topAdapter;
    private MainPresenter topPresenter;
    private String[] titles= {"推荐的","新鲜的"};
    private List<Fragment> fragments;
    private MainPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this , view);
        init();
        topPresenter.getMain();
        return view;
    }

    private void init() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTop.setLayoutManager(layoutManager);
        topPresenter = new MainPresenter(getActivity(),this);
        presenter = new MainPresenter(getActivity(),new A());
        fragments = new ArrayList<>();
        recyclerViewBottom.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewBottom.addItemDecoration(new MyDecoration(getActivity(),R.color.divider_color,LinearLayoutManager.HORIZONTAL, CommonUtil.dip2px(getActivity(),1),0));
//        for (int i=0;i<2;i++){
//            Bundle bundle = new Bundle();
//            bundle.putString("type" , titles[i]);
//            MainBottomFragment fragment = new MainBottomFragment();
//            fragment.setArguments(bundle);
//            fragments.add(fragment);
//        }
//        TradeFragmentPagerAdapter adapter = new TradeFragmentPagerAdapter(getChildFragmentManager(),fragments, Arrays.asList(titles));
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
    }
    public class A implements IMainBottomView{
        @Override
        public void analysisFailer(String info) {

        }

        @Override
        public void httpError(String error) {

        }

        @Override
        public void getDataFailer(BaseBean bean) {

        }

        @Override
        public void getDataSuccess(Object[] args) {
            MainBottomAdapter adatper =null;
            if (args != null && args.length>0){
                if (args[0] instanceof List){
                    List<MainBean.MainBottomEntity> data = (List<MainBean.MainBottomEntity>) args[0];
                    if (adatper == null){
                        adatper = new MainBottomAdapter(data,getActivity());
                        recyclerViewBottom.setAdapter(adatper);
                    }
                }
            }
        }

        @Override
        public String getUrl() {
            return Constants.mainRecommend;
        }

        @Override
        public String getPage() {
            return "1";
        }

        @Override
        public int getFlag() {
            return 0;
        }
    }
    @Override
    public String getUrl() {
        return Constants.mainIndex;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        super.getDataSuccess(args);
//        presenter.getMainBottom();
        if (args != null && args.length>0){
            if (args[0] instanceof MainBean){
                MainBean bean = (MainBean) args[0];
                setData(bean);
            }
        }
    }

    private void setData(MainBean bean) {
        if (bean == null) return;
        if (topAdapter == null){
            topAdapter = new MainAdapter(bean,getActivity(),this);
            recyclerViewTop.setAdapter(topAdapter);
        }
        presenter.getMainBottom();
    }

    @Override
    public void onBannerClick(int position, String link) {
        Intent intent = new Intent(getActivity(), PublishWebView.class);
        intent.putExtra("url",link);
        startActivity(intent);
    }

    @Override
    public void onTopImageClick(String link) {
        Intent intent = new Intent(getActivity(), PublishWebView.class);
        intent.putExtra("url",link);
        startActivity(intent);
    }

    @Override
    public void onMiddleImageClick(String link) {
        Intent intent = new Intent(getActivity(), PublishWebView.class);
        intent.putExtra("url",link);
        startActivity(intent);
    }
}
