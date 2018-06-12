package com.hahafather007.ringview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenpengxiang on 2018/6/11
 */
public class RingViewAdapter extends PagerAdapter {
    private List<Object> views = new ArrayList<>();
    private Context context;
    private ViewPager pager;

    public RingViewAdapter(Context context) {
        this.context = context;
    }

    /**
     * @return 如果只有一条数据，则不可滚动。如果有多条数据，则可以循环滚动
     */
    @Override
    public int getCount() {
        if (views.size() <= 1) {
            return views.size();
        } else {
            return views.size() * 3;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((View) object));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object o = views.get(position % views.size());

        if (o instanceof String) {
            ImageView image = new ImageView(context);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setBackgroundColor(Color.GREEN);

            Glide.with(image).load(o.toString()).into(image);

            container.addView(image);

            return image;
        } else {
            container.addView(((View) o));

            return o;
        }
    }

    public void setViews(List<String> imgs, List<View> viewList) {
        views.clear();
        views.addAll(imgs);
        views.addAll(viewList);

        notifyDataSetChanged();

        pager.setCurrentItem(views.size(), false);
    }

    /**
     * @param pager 调用该方法传入ViewPager与adapter进行关联
     */
    public void attachTo(final ViewPager pager) {
        this.pager = pager;

        pager.setAdapter(this);
        pager.setOffscreenPageLimit(3);
        pager.addOnPageChangeListener(new SimplePageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position <= views.size() - 1) {
                    pager.setCurrentItem(position + views.size(), false);
                } else if (position >= views.size() * 2) {
                    pager.setCurrentItem(position - views.size(), false);
                }
            }
        });
    }

    /**
     * @param currentItem 传入真实的位置，返回图像数据对应位置
     */
    public int getPositionByCurrentItem(int currentItem) {
        return currentItem % 3;
    }

    /**
     * 调用该方法释放资源
     */
    public void release() {
        views.clear();
    }
}
