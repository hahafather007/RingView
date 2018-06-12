package com.hahafather007.ringview.utils;

import android.util.Log;

import static com.hahafather007.ringview.BuildConfig.DEBUG;

/**
 * Created by chenpengxiang on 2018/6/11
 */
public class Logger {
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
}
