package com.hahafather007.ringview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.hahafather007.ringview.utils.BitmapUtil;

/**
 * Created by chenpengxiang on 2018/6/13
 */
public class MyFrameLayout extends FrameLayout {
    private OnCreatedBitmapListener listener;

    public MyFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (listener != null) {
            listener.getBitmap(BitmapUtil.loadBitmapFromView(this));

            listener = null;
        }
    }

    public void setListener(OnCreatedBitmapListener listener) {
        this.listener = listener;
    }

    public interface OnCreatedBitmapListener {
        void getBitmap(Bitmap bitmap);
    }
}
