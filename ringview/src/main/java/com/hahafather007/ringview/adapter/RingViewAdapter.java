package com.hahafather007.ringview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hahafather007.ringview.utils.Logger;
import com.hahafather007.ringview.view.RingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenpengxiang on 2018/6/11
 */
public class RingViewAdapter extends PagerAdapter {
    private List<Object> views = new ArrayList<>();
    private Context context;
    private ViewPager pager;
    private RingView.OnPhotoTouchListener listener;

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

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final int realPosition = position % views.size();
        final Object o = views.get(realPosition);

        if (o instanceof String) {
            ImageView image = new ImageView(context);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            if (listener != null) {
                image.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            listener.click(position % views.size(), o.toString());
                        }

                        listener.touch(v, event);

                        return true;
                    }
                });
            }

            Glide.with(image).load(o.toString()).into(image);

            container.addView(image);

            return image;
        } else {
            View view = (View) o;
            ViewGroup parent = (ViewGroup) view.getParent();

            if (parent != null) {
                parent.removeView(view);
            }

            Logger.i(view.getBackground() + " ");

            container.addView(view);

            return o;
        }
    }

    public void setViews(List<Object> viewList) {
        views = viewList;

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
            int thisPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                thisPosition = position;
            }

            /**
             * 在空闲状态才进行是否滚动页面的判断，是为了防止在滚动到最后一页时，由于页面还
             * 未滚动完毕就直接进行跳转，造成页面闪烁
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                //SCROLL_STATE_IDLE 表示目前没有滚动，处于空闲状态
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (thisPosition <= views.size() - 1) {
                        pager.setCurrentItem(thisPosition + views.size(), false);
                    } else if (thisPosition >= views.size() * 2) {
                        pager.setCurrentItem(thisPosition - views.size(), false);
                    }
                }
            }
        });
    }

    /**
     * 当轮播的对象为图片资源时，设置的点击事件监听器
     */
    public void setPhotoTouchListener(RingView.OnPhotoTouchListener listener) {
        this.listener = listener;
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
        listener = null;
    }
}
