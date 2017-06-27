package com.zkai.clockin.broadcast;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/23
 * description：
 */

public class CallBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG = "CallBroadCastReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "[Broadcast]" + action);

        //如果是拨打电话  
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i(TAG, "call OUT:" + phoneNumber);
        } else {
            //如果是来电  
            TelephonyManager tm =
                    (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i(TAG, "kai ---- onReceive 响铃 ----> ");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i(TAG, "kai ---- onReceive 挂断 ----> ");
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i(TAG, "kai ---- onReceive 通话中 ----> ");
                    break;
            }
        }
    }

}
