package com.zkai.clockin.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/29
 * description：
 */

public class CreateCmdUtils {
    private static final String TAG = "CreateCmdUtils";
    public static final int[] QQ_EDIT_TEXT = {300, 1165};
    public static final int[] QQ_SEND_BUTTON = {662, 662};
    
    public static String createEventKey(int keycode) {
        return "input keyevent " + keycode + "\n";
    }

    public static String createEventTap(int x, int y) {
        return "input tap " + x + " " + y + "\n";
    }

    public static String createEventTap(int[] position) {
        if (position.length != 2) {
            return null;
        }
        int x = position[0];
        int y = position[1];
        return "input tap " + x + " " + y + "\n";
    }

    public static String createInputText(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        text = PinYin.getPinYin(text);
        text = dropChinese(text);
        text = text.replace(" ", "");
        Log.i(TAG, "createInputText: " + text);
        return "input text " + text + "\n";
    }
    public static String createSleep(int sec) {
        if (sec <= 0) {
            return null;
        }
        return "sleep " + sec + "\n";
    }

    public static String createStopApp(String packageName) {
        return "am force-stop " + packageName;
    }

    public static String createOpenQQ() {
        return "am start -n com.tencent.mobileqq/.activity.SplashActivity";
    }
    
    
    
    /**
     *  去除中文
     *  @param str
     *  @return
     * */
    public static String dropChinese(String str) {
        String result = "";
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!isChinese(c)) {
                result += c;
            }
        }
        return result;
    }
    
    /**
     * 判断是否为中文字符
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

}
