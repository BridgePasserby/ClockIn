package com.zkai.clockin.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/6/23
 * description：用root权限执行Linux下的Shell指令
 */

public class RootShellCmdUtils {
    private static final String TAG = "RootShellCmdUtils";
    private static RootShellCmdUtils rootShellCmd;
    private static OutputStream os;

    /**
     * 执行shell指令
     *
     * @param cmd adb 命令，eg:input keyevent 4
     */
    public static void exec(String cmd) {
        Log.i(TAG,"kai ---- exec cmd ----> " + cmd);
        try {
            os = Runtime.getRuntime().exec("su").getOutputStream();
            os.write(cmd.getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                    os = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    
    
    public static void exec(String[] commands) {
        Log.i(TAG,"kai ---- exec commands ----> " + commands);
        try {
            os = Runtime.getRuntime().exec("su").getOutputStream();
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                os.write(command.getBytes());
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                    os = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exec(ArrayList<String> commands) {
        Log.i(TAG, "kai ---- exec commands ----> " + commands);
        try {
            os = Runtime.getRuntime().exec("su").getOutputStream();
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                os.write(command.getBytes());
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                    os = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final void checkFocusActivity() {
        String command = "dumpsys activity | findstr \"mFocusedActivity\"";
    }


    public static void openApp(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return;
        }
        context.startActivity(intent);
    }

    public static String execStartApp(String packageName) {
//        try {
            if (rootShellCmd == null) {
                rootShellCmd = new RootShellCmdUtils();
            }
//            rootShellCmd.simulateKey(KeyEvent.KEYCODE_BACK);
//            simulateTap(300,300);
        
//            Process p = Runtime.getRuntime().exec("su");
//            String adb = "adb shell am start -n \"%s\"";
////            String command = String.format(adb, packageName);
////            String command = "am start -n com.alibaba.android.rimet/com.alibaba.lightapp.runtime.activity.CommonWebViewActivity";
//            String command = "dumpsys activity | findstr \"mFocusedActivity\"";
//            Process process = Runtime.getRuntime().exec(command);
//            
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            int read;
//            char[] buffer = new char[4096];
//            StringBuffer output = new StringBuffer();
//            while ((read = reader.read(buffer)) > 0) {
//                output.append(buffer, 0, read);
//            }
//            reader.close();
//            process.waitFor();
//            return output.toString();
            return "";
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

}
