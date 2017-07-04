package com.zkai.clockin.utils;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/27
 * description：
 */

public class Constant {
    public static String nickName = "凯风";
    public static final String MSG_QQ_OPEN_DING_TALK = "打开钉钉";
    public static final String MSG_QQ_EXIT_DING_TALK = "退出钉钉";
    public static final String MSG_QQ_GET_FOCUSED_ACTIVITY = "查看前台应用";// 慎用
    public static final String MSG_DT_CLOCKIN_SUCCEED = "打卡成功";
    public static final String MSG_DT_CLOCKING_IN = "考勤打卡";
    public static final String MSG_DT_TEST = "";
    private static int[] tapPositionQqMsgText = {300, 1165};
    private static int[] tapPositionQqSendButton = {662, 662};

    public static int[] getTapPositionQqMsgText() {
        tapPositionQqMsgText[0] *= DeviceInfoUtils.getXScale();
        tapPositionQqMsgText[1] *= DeviceInfoUtils.getYScale();
        return tapPositionQqMsgText;
    }

    public static int[] getTapPositionQqSendButton() {
        tapPositionQqSendButton[0] *= DeviceInfoUtils.getXScale();
        tapPositionQqSendButton[1] *= DeviceInfoUtils.getYScale();
        return tapPositionQqSendButton;
    }

    public static final String ACTIVITY_PHONE_HOME = "com.huawei.android.launcher.Launcher";// 手机launcher
    public static final String ACTIVITY_MYSELF = "com.zkai.clockin.MainActivity";

    public static void setNickName(String nickName) {
        Constant.nickName = nickName;
    }

}
