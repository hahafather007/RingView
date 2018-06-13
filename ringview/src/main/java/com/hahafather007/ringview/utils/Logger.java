package com.hahafather007.ringview.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;


/**
 * Created by chenpengxiang on 2018/6/11
 */
public class Logger {
    private static boolean DEBUG;
    private static boolean hasInited;
    private static String TAG = "RingView-------->";

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            Log.v(TAG, msg);
        }
    }

    /**
     * 初始化DEBUG的值
     */
    public static void init(Context context) {
        if (!hasInited) {
            DEBUG = context.getApplicationInfo() != null &&
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;

            hasInited = true;
        }
    }
}
