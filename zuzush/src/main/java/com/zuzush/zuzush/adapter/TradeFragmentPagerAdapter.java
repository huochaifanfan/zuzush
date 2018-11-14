package com.zuzush.zuzush.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class TradeFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	private List<String> title;
	public TradeFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> title) {
		super(fm);
		this.fragments = fragments;
		this.title = title;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (title != null){
			return title.get(position);
		}
		return null;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		/**页面切换时候不重新执行各个生命周期方法*/
//		super.destroyItem(container, position, object);
	}
}
