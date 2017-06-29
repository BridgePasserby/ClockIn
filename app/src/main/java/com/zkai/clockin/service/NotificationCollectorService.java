package com.zkai.clockin.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import com.zkai.clockin.App;
import com.zkai.clockin.broadcast.CustomBroadcastAction;
import com.zkai.clockin.utils.CreateCmdUtils;
import com.zkai.clockin.utils.PackageName;
import com.zkai.clockin.utils.QQConstant;
import com.zkai.clockin.utils.RootShellCmdUtils;

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
        Log.i(TAG, "kai ---- onNotificationPosted packageName ----> " + packageName);
        if (PackageName.PN_QQ.equals(packageName)) {
            dealQQMsg(sbn);
        } else if (PackageName.PN_DING_TALK.equals(packageName)) {
            dealDingMsg(sbn);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("kai", "remove" + "-----" + sbn.getPackageName());

    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void dealDingMsg(StatusBarNotification sbn) {
        String title = (String) sbn.getNotification().extras.get("android.title");
        String msg = (String) sbn.getNotification().extras.get("android.text");
        Log.i(TAG, "kai ---- dealDingMsg title ----> " + title);
        Log.i(TAG, "kai ---- dealDingMsg msg ----> " + msg);
        if (TextUtils.isEmpty(title)) {
            return;
        }
        sendBroadcastWithArgs(CustomBroadcastAction.ACTION_RECEIVE_DING_TALK_MSG, title, msg);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void dealQQMsg(StatusBarNotification sbn) {
        String title = (String) sbn.getNotification().extras.get("android.title");
        String msg = (String) sbn.getNotification().extras.get("android.text");
        Log.i(TAG, "kai ---- dealQQMsg title ----> " + title);
        Log.i(TAG, "kai ---- dealQQMsg msg ----> " + msg);
        if (TextUtils.isEmpty(title)) {
            return;
        }
        if (title.contains(QQConstant.nickName)) {
            if (QQConstant.CLOCK_IN.equals(msg)) {
                sendBroadcastWithArgs(CustomBroadcastAction.ACTION_RECEIVE_QQ_MSG, title, msg);
            } else if (QQConstant.STOP_DING_TALK.equals(msg)) {
                // TODO: 2017/6/29 退出钉钉 by Z.kai

            } else if (1 == 2) {
                RootShellCmdUtils.exec(CreateCmdUtils.createEventTap(300, 300));
            }
        }
    }

    private void sendBroadcastWithArgs(String action, String title, String msg) {
        Intent intent = new Intent();
        intent.setAction(action);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("msg", msg);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

}
