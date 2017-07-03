package com.zkai.clockin.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zkai.clockin.App;
import com.zkai.clockin.utils.PackageName;
import com.zkai.clockin.utils.RootShellCmdUtils;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/23
 * description：
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG,"kai ---- onReceive action ----> " + action);
        RootShellCmdUtils.openApp(App.getContext(), PackageName.PN_DING_TALK);
    }
    
}
