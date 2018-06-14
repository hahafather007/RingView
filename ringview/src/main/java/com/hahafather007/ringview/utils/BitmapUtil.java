package com.hahafather007.ringview.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by chenpengxiang on 2018/6/13
 */
public class BitmapUtil {
    /**
     * @param v 传入一个View对象
     * @return 返回对应View的Bitmap图像
     */
    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }

        Bitmap screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(screenshot);
        /*
        我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），
        如果想得到当前区域，需要重新定位到当前可显示的区域
         */
        canvas.translate(-v.getScrollX(), -v.getScrollY());
        // 将 view 画到画布上
        v.draw(canvas);
        return screenshot;
    }
}
