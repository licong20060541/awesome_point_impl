package com.point.impl.arouter;

import android.util.Log;

/**
 * Created by licong12 on 2018/7/11.
 */

public class Const {

    public static final String TAG = "licong12";

    public static final String ACTIVITY_URL_MAIN = "/app/RouterActivity";
    public static final String ACTIVITY_URL_SIMPLE = "/app/SimpleActivity";
    public static final String ACTIVITY_URL_PARSE = "/app/SimpleUriActivity";
    public static final String ACTIVITY_URL_FRAGMENT = "/app/Fragment";
    public static final String ACTIVITY_URL_INTERCEPTOR = "/app/Interceptor";

    public static void log(String content) {
        Log.d(TAG, content);
    }

}
