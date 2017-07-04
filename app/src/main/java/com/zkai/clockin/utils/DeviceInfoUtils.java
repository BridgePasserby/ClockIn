package com.zkai.clockin.utils;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/7/4
 * description：
 */

public class DeviceInfoUtils {
    private static final int defaultWidth = 720;// 设计尺寸
    private static final int defaultHeight = 1280;
    private static int mWidth =  720;// 实际尺寸
    private static int mHeight = 1280;

    public static void initScreen(float width, float height) {
        mWidth = (int) width;
        mHeight = (int) height;
    }

    public static int getWidth() {
        return mWidth;
    }

    public static int getHeight() {
        return mHeight;
    }

    public static float getXScale() {
        return ((float) mWidth) / defaultWidth;
    }

    public static float getYScale() {
        return ((float) mHeight) / defaultHeight;
    }
}
