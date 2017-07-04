package com.zkai.clockin;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.zkai.clockin.utils.DeviceInfoUtils;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/23
 * description：
 */

public class App extends Application {
    private static final String TAG = "App";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        init();
    }

    private void init() {
        WindowManager windowManager = (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        float width = dm.widthPixels;
        float height = dm.heightPixels;
        DeviceInfoUtils.initScreen(width, height);
    }

    public static Context getContext() {
        return context;
    }
}
