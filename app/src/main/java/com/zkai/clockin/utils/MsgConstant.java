package com.zkai.clockin.utils;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/27
 * description：
 */

public class MsgConstant {
    public static String nickName = "凯风";
    public static final String QQ_OPEN_DING_TALK = "打开钉钉";
    public static final String QQ_EXIT_DING_TALK = "退出钉钉";
    public static final String QQ_GET_FOCUSED_ACTIVITY = "查看前台应用";// 慎用
    public static final String QQ_RE_CLOCK_IN = "2";// 重新打卡

    public static final String DT_CLOCKIN_SUCCEED = "打卡成功";
    public static final String DT_CLOCKING_IN = "考勤打卡";
    public static final String DT_TEST = "";

    public static final String ACTIVITY_PHONE_HOME = "com.huawei.android.launcher.Launcher";// 手机launcher
    public static final String ACTIVITY_MYSELF = "com.zkai.clockin.MainActivity";

    public static void setNickName(String nickName) {
        MsgConstant.nickName = nickName;
    }

}
