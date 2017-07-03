package com.zkai.clockin.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private static OutputStream os;

    /**
     * 执行shell指令
     *
     * @param cmd adb 命令，eg:input keyevent 4
     */
    public static boolean exec(String cmd) {
        Log.i(TAG, " ---- exec cmd ----> " + cmd);
        Process su = null;
        try {
            su = Runtime.getRuntime().exec("su");
            os = su.getOutputStream();
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
        // 处理结果
        boolean result = false;
        try {
            InputStream input;
            if (su != null) {
                input = su.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(input));
                StringBuffer stringBuffer = new StringBuffer();
                String content = "";
                while ((content = in.readLine()) != null) {
                    stringBuffer.append(content);
                }
                Log.d(TAG, "result content : " + stringBuffer.toString());
                int status = su.waitFor();
                if (status == 0) {
                    result = true;
                } else {
                    result = false;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "kai ---- exec IO异常 ----> ");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e(TAG, "kai ---- exec InterruptedException异常 ----> ");
            e.printStackTrace();
        }
        return result;
    }

    public static boolean exec(String[] commands) {
        Process su = null;
        try {
            su = Runtime.getRuntime().exec("su");
            os = su.getOutputStream();
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                Log.i(TAG, "---- exec command ----> " + command);
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
        // 处理结果
        boolean result = false;
        try {
            InputStream input = null;
            Log.i(TAG, "kai ---- exec su ----> " + su);
            if (su != null) {
                int status = su.waitFor();
                if (status == 0) {
                    result = true;
                } else {
                    result = false;
                }
            }
        } catch (InterruptedException e) {
            Log.i(TAG, "kai ---- exec InterruptedException异常 ----> ");
            e.printStackTrace();
        }
        return result;
    }

    public static boolean exec(ArrayList<String> commands) {
        Process su = null;
        try {
            su = Runtime.getRuntime().exec("su");
            os = su.getOutputStream();
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                Log.i(TAG, "---- exec command ----> " + command);
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

        // 处理结果
        boolean result = false;
        try {
            InputStream input = null;
            if (su != null) {
                int status = su.waitFor();
                if (status == 0) {
                    result = true;
                } else {
                    result = false;
                }
            }
        } catch (InterruptedException e) {
            Log.d(TAG, "kai ---- exec InterruptedException异常 ----> ");
            e.printStackTrace();
        }
        return result;
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

}
