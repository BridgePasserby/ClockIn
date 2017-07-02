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


//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void dealDingMsg(StatusBarNotification sbn) {
//        String title = (String) sbn.getNotification().extras.get("android.title");
//        String msg = (String) sbn.getNotification().extras.get("android.text");
//        Log.i(TAG, "kai ---- dealDingMsg title ----> " + title);
//        Log.i(TAG, "kai ---- dealDingMsg msg ----> " + msg);
//        if (TextUtils.isEmpty(title)) {
//            return;
//        }
//        sendBroadcastWithArgs(CustomBroadcastAction.ACTION_RECEIVE_DING_TALK_MSG, title, msg);
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void dealQQMsg(StatusBarNotification sbn) {
//        String title = (String) sbn.getNotification().extras.get("android.title");
//        String msg = (String) sbn.getNotification().extras.get("android.text");
//        Log.i(TAG, "kai ---- dealQQMsg title ----> " + title);
//        Log.i(TAG, "kai ---- dealQQMsg msg ----> " + msg);
//        if (TextUtils.isEmpty(title)) {
//            return;
//        }
//        if (title.contains(MsgConstant.nickName)) {
//                sendBroadcastWithArgs(CustomBroadcastAction.ACTION_RECEIVE_QQ_MSG, title, msg);
//            if (MsgConstant.QQ_OPEN_DING_TALK.equals(msg)) {
//            } else if (MsgConstant.QQ_EXIT_DING_TALK.equals(msg)) {
//                RootShellCmdUtils.exec(CreateCmdUtils.createStopApp(PackageName.PN_DING_TALK));
//                // TODO: 2017/6/29 退出钉钉 by Z.kai
//
//            } else if (1 == 2) {
//                RootShellCmdUtils.exec(CreateCmdUtils.createEventTap(300, 300));
//            }
//        }
//    }

    private void sendBroadcastWithArgs(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

}
