package com.hahafather007.ringview.adapter;

import android.support.v4.view.ViewPager;

import com.hahafather007.ringview.utils.Logger;

/**
 * Created by chenpengxiang on 2018/6/11
 */
public class SimplePageChangeListener implements ViewPager.OnPageChangeListener {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Logger.i("onPageScrolled==" + "position：" + position + " positionOffset："
                + positionOffset + " positionOffsetPixels：" + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        Logger.i("onPageSelected==" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Logger.i("onPageScrollStateChanged==" + state);
    }
}
