package com.zkai.clockin.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.zkai.clockin.broadcast.CustomBroadcastAction;
import com.zkai.clockin.utils.PackageName;

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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        Log.i(TAG, "kai ---- onNotificationPosted packageName ----> " + packageName);
        if (PackageName.PN_QQ.equals(packageName)) {
            sendBroadcastWithArgs(CustomBroadcastAction.ACTION_RECEIVE_QQ_MSG, sbn.getNotification().extras);
        } else if (PackageName.PN_DING_TALK.equals(packageName)) {
            sendBroadcastWithArgs(CustomBroadcastAction.ACTION_RECEIVE_DING_TALK_MSG, sbn.getNotification().extras);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("kai", "remove" + "-----" + sbn.getPackageName());
    }

    private void sendBroadcastWithArgs(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

}
