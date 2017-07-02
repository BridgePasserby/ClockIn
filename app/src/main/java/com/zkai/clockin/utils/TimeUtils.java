package com.zkai.clockin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/30
 * description：
 */

public class TimeUtils {
    public static String getCurrentTimeStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[yyyy-MM-dd/HH:mm:ss]");
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static long getCurrentTimeLong() {
        return System.currentTimeMillis();
    }

}
