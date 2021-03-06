package com.hahafather007.ringview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hahafather007.ringview.adapter.RingViewAdapter;
import com.hahafather007.ringview.adapter.SimplePageChangeListener;
import com.hahafather007.ringview.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chenpengxiang on 2018/6/11
 */
public class RingView extends ViewPager {
    /**
     * timer向handler传递是否显示动画时用的key
     */
    private final String ANIM_KEY = "anim_key";
    private Handler timerHandler;
    private Timer timer;
    /**
     * Object可为图片url或View
     */
    private List<Object> views;
    private RingViewAdapter adapter;
    /**
     * 轮播的间隔时间，默认1000毫秒
     */
    private int ringTime = 1000;
    private int stayTime = 0;
    /**
     * 标记是否设置了点击暂停轮播
     */
    private boolean touchToPause = true;
    /**
     * 标记是否设置了轮播时带有动画
     */
    private boolean ringWithAnim;
    /**
     * 标记是否设置了自动轮播
     */
    private boolean autoRing;

    public RingView(@NonNull Context context) {
        this(context, null);
    }

    public RingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (touchToPause) {
                    timer.cancel();
                    timer = null;
                }

                break;
            case MotionEvent.ACTION_UP:
                if (autoRing && timer == null) {
                    startRing(ringWithAnim);
                }

                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    private void init() {
        timer = new Timer();
        views = new ArrayList<>();
        adapter = new RingViewAdapter(getContext());
        timerHandler = new TimerHandler();

        adapter.attachTo(this);

        addOnPageChangeListener(new SimplePageChangeListener() {
            int lastPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (lastPosition != position) {
                    lastPosition = position;

                    stayTime = 0;
                }
            }
        });
    }

    public <T> void updateView(List<T> list) {
        stopRing();

        views.clear();

        if (list.size() >= 1) {
            Logger.i(list.get(0).getClass().getSimpleName());

            for (T t : list) {
                if (t instanceof String) {
                    views.add(t.toString());
                } else if (t instanceof View) {
                    views.add(t);
                }
            }

            if (views.isEmpty()) {
                throw new RuntimeException("List的类型只能为String和View，不能使用其他类型！");
            }
        }

        Logger.i("数据已更新！====" + "view数量：" + views.size());

        adapter.setViews(views);
    }

    /**
     * 调用该方法开始自动轮播
     *
     * @param withAnim 自动轮播是否带有切换动画
     */
    public void startRing(final boolean withAnim) {
        if (views.size() <= 1) return;

        ringWithAnim = withAnim;
        autoRing = true;

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Bundle bundle = new Bundle();
                Message msg = new Message();

                bundle.putBoolean(ANIM_KEY, withAnim);
                msg.setData(bundle);

                timerHandler.sendMessage(msg);
            }
        }, 500, 500);
    }

    /**
     * 当轮播的对象为图片资源时，设置的点击事件监听器
     */
    public void setPhotoTouchListener(OnPhotoTouchListener listener) {
        adapter.setPhotoTouchListener(listener);
    }

    /**
     * 调用该方法停止轮播
     */
    public void stopRing() {
        timer.cancel();

        autoRing = false;
    }

    /**
     * @return 获取轮播间隔时间
     */
    public int getRingTime() {
        return ringTime;
    }

    /**
     * @param ringTime 设置轮播间隔（单位：毫秒）
     */
    public void setRingTime(int ringTime) {
        this.ringTime = ringTime;
    }

    /**
     * @return 返回是否设置了点击图片时暂停轮播
     */
    public boolean isTouchToPause() {
        return touchToPause;
    }

    /**
     * @param touchToPause 传入bool值设置是否开启点击图片时暂停播放，默认开启
     */
    public void setTouchToPause(boolean touchToPause) {
        this.touchToPause = touchToPause;
    }

    public int getPosition() {
        return adapter.getPositionByCurrentItem(getCurrentItem());
    }

    /**
     * 调用该方法跳转下一张
     *
     * @param withAnim 跳转是否有动画效果
     */
    public void moveToNext(boolean withAnim) {
        if (views.size() <= 1) return;

        stayTime = 0;

        setCurrentItem(getCurrentItem() + 1, withAnim);
    }

    /**
     * 调用该方法回到上一张
     *
     * @param withAnim 跳转是否有动画效果
     */
    public void moveToLast(boolean withAnim) {
        if (views.size() <= 1) return;

        stayTime = 0;

        setCurrentItem(getCurrentItem() - 1, withAnim);
    }

    /**
     * 将画面重置到第一张
     *
     * @param withAnim 重置是否有动画（建议不添加动画效果，可能会产生卡顿）
     */
    public void resetToFirst(boolean withAnim) {
        setCurrentItem(views.size() == 1 ? 0 : views.size(), withAnim);
    }

    /**
     * 务必调用该方法释放资源，防止内存泄露
     */
    public void release() {
        timer.cancel();
        views.clear();
        adapter.release();
        timerHandler.removeCallbacksAndMessages(null);
        timerHandler = null;

        clearOnPageChangeListeners();
    }

    @SuppressLint("HandlerLeak")
    private class TimerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            boolean withAnim = msg.getData().getBoolean(ANIM_KEY);

            stayTime += 500;

            if (stayTime >= ringTime) {
                moveToNext(withAnim);
            }
        }
    }

    public interface OnPhotoTouchListener {
        void click(int position, String url);

        void touch(View v, MotionEvent event);
    }
}
