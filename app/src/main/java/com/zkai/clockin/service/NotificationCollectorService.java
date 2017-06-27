package com.zkai.clockin.service;

import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.zkai.clockin.App;
import com.zkai.clockin.utils.CmdUtils;
import com.zkai.clockin.utils.PackageName;
import com.zkai.clockin.utils.QQConstant;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/26
 * description：
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationCollectorService extends NotificationListenerService {
    private static final String TAG = "NotificationCollectorService";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        String packageName = sbn.getPackageName();
        Toast.makeText(NotificationCollectorService.this, packageName, Toast.LENGTH_LONG).show();

        Log.i(TAG, "kai ---- onNotificationPosted packageName ----> " + packageName);
        if (PackageName.PN_QQ.endsWith(packageName)) {
            dealQQMsg(sbn);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void dealQQMsg(StatusBarNotification sbn) {
        String nickName = (String) sbn.getNotification().extras.get("android.title");
        String msg = (String) sbn.getNotification().extras.get("android.text");
        Log.i(TAG, "kai ---- dealQQMsg nickName ----> " + nickName);
        Log.i(TAG, "kai ---- dealQQMsg msg ----> " + msg);
        if (TextUtils.isEmpty(nickName)) {
            return;
        }
        if (nickName.contains(QQConstant.nickName)) {
            if (QQConstant.CLOCK_IN.equals(msg)) {
                CmdUtils.openDingTalk(App.getContext());
            } else if (QQConstant.STOP_DING_TALK.equals(msg)) {
//                ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
//                try {
//                    Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);
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
    
    public interface IQQMsg {
        void onFetch(StatusBarNotification sbn);
    }

}
