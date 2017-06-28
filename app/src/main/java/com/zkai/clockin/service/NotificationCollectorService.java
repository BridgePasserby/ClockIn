package com.zkai.clockin.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.zkai.clockin.App;
import com.zkai.clockin.utils.CmdUtils;
import com.zkai.clockin.utils.PackageName;
import com.zkai.clockin.utils.QQConstant;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/26
 * description：
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
@SuppressLint("OverrideAbstract")
public class NotificationCollectorService extends NotificationListenerService {
    private static final String TAG = "NotificationCollectorService";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
//        Toast.makeText(NotificationCollectorService.this, packageName, Toast.LENGTH_LONG).show();
        Log.i(TAG, "kai ---- onNotificationPosted packageName ----> " + packageName);
        boolean equals = PackageName.PN_QQ.equals(packageName);
        Log.i(TAG,"kai ---- onNotificationPosted equals ----> " + equals);
        if (PackageName.PN_QQ.equals(packageName)) {
            dealQQMsg(sbn);
        } else if (PackageName.PN_DING_TALK.equals(packageName)) {
            dealDingMsg(sbn);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void dealDingMsg(StatusBarNotification sbn) {
        String nickName = (String) sbn.getNotification().extras.get("android.title");
        String msg = (String) sbn.getNotification().extras.get("android.text");
        Log.i(TAG, "kai ---- dealDingMsg nickName ----> " + nickName);
        Log.i(TAG, "kai ---- dealDingMsg msg ----> " + msg);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void dealQQMsg(StatusBarNotification sbn) {
        String nickName = (String) sbn.getNotification().extras.get("android.title");
        String msg = (String) sbn.getNotification().extras.get("android.text");
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("title", nickName);
//        hashMap.put("msg", msg);
//        iqqMsg.onFetch(hashMap);
        Log.i(TAG, "kai ---- dealQQMsg nickName ----> " + nickName);
        Log.i(TAG, "kai ---- dealQQMsg msg ----> " + msg);
        if (TextUtils.isEmpty(nickName)) {
            return;
        }
        if (nickName.contains(QQConstant.nickName)) {
            if (QQConstant.CLOCK_IN.equals(msg)) {
//                CmdUtils.execStartApp(PackageName.PN_DING_TALK);
                CmdUtils.openDingTalk(App.getContext());
            } else if (QQConstant.STOP_DING_TALK.equals(msg)) {
                ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
////                am.killBackgroundProcesses(PackageName.PN_DING_TALK);
//                try {
//                    Class<?> aClass = Class.forName("android.app.ActivityManager");
//                    Method method = aClass.getMethod("forceStopPackage", String.class);
//                    method.invoke(am, PackageName.PN_DING_TALK);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("kai", "remove" + "-----" + sbn.getPackageName());

    }
    
}
