package com.zkai.clockin.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.zkai.clockin.App;
import com.zkai.clockin.R;
import com.zkai.clockin.utils.CmdUtils;

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
//        CmdUtils.execStartApp(CmdUtils.DING_TALK_PACKAGE_NAME);
        Log.i(TAG,"kai ---- onReceive action ----> " + action);
        CmdUtils.openDingTalk(App.getContext());
        
    }
    
}
