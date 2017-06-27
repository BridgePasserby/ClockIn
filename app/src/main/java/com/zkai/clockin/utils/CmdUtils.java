package com.zkai.clockin.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/23
 * description：
 */

public class CmdUtils {
    private static final String TAG = "CmdUtils";


    public static void openDingTalk(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(PackageName.PN_DING_TALK);
        if (intent == null) {
            return;
        }
        context.startActivity(intent);
    }

    public static String execStartApp(String packageName) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            String adb = "adb shell am start -n \"%s\"";
            String command = String.format(adb, packageName);
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            process.waitFor();
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
