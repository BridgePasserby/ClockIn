package com.zkai.clockin.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
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
@SuppressLint("OverrideAbstract")
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationCollectorService extends NotificationListenerService {
    private static final String TAG = "NotificationCollectorService";
    private IQQMsg iqqMsg;

    public void setIqqMsgListener(IQQMsg iqqMsg) {
        this.iqqMsg = iqqMsg;
    }

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

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate() called");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand() called with: intent = [" + intent + "], flags = [" + flags + "], startId = [" + startId + "]");
        return super.onStartCommand(intent, flags, startId);
        
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind() called with: intent = [" + intent + "]");
        return new NotifiBinder();
    }

    public class NotifiBinder extends Binder {
        public NotificationCollectorService getMyService() {
            return NotificationCollectorService.this;
        }
    }

}
