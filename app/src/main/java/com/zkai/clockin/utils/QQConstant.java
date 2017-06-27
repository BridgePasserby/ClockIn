package com.zkai.clockin.utils;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/27
 * description：
 */

public class QQConstant {
    public static String nickName = "凯风";
    public static final String CLOCK_IN = "1";// 打卡
    public static final String STOP_DING_TALK = "0";// 退出钉钉
    public static final String RE_CLOCK_IN = "2";// 重新打卡

    public static void setNickName(String nickName) {
        QQConstant.nickName = nickName;
    }

}
