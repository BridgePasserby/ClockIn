package com.zkai.clockin;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zkai.clockin.broadcast.AlarmBroadcastReceiver;
import com.zkai.clockin.broadcast.CustomBroadcastAction;
import com.zkai.clockin.service.NotificationCollectorService;
import com.zkai.clockin.utils.CreateCmdUtils;
import com.zkai.clockin.utils.PackageName;
import com.zkai.clockin.utils.QQConstant;
import com.zkai.clockin.utils.RootShellCmdUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "MainActivity";

    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private Button btnStartService;
    private EditText etReceiveName;
    private Intent notificationIntent;
    private TextView tvQQMsg;
    private Button btnTestAdb;
    private StringBuffer sbReceiveMsg;
    private BroadcastReceiver qqReceiver;
    private BroadcastReceiver dingReceiver;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        initView();
        setListener();
        initReceiver();
        registerReceiver();
        sbReceiveMsg = new StringBuffer();
    }

    private void initReceiver() {
        qqReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                String title = (String) extras.get("title");
                String msg = (String) extras.get("msg");
                sbReceiveMsg.append("\nTitle:").append(title)
                        .append("\nMsg:").append(msg);
                tvQQMsg.setText(sbReceiveMsg.toString());
                RootShellCmdUtils.openApp(App.getContext(),PackageName.PN_DING_TALK);
            }
        };
        dingReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                String msg = extras.getString("msg");
                Log.i(TAG, "kai ---- onReceive msg.contains(\"打卡正常\") ----> " + msg.contains("打卡正常"));
                long l = System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = simpleDateFormat.format(new Date(l));
                msg = format + msg + "CLOCKINSUCCESSED";
                if (!TextUtils.isEmpty(msg) && msg.contains("打卡 正常") || msg.contains("考勤打卡")) {

                    RootShellCmdUtils.openApp(App.getContext(), PackageName.PN_MY);
                    final String finalMsg = msg;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String url = "mqqwpa://im/chat?chat_type=wpa&uin=1259583420";
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                            String[] cmds = new String[11];
                            cmds[0] = CreateCmdUtils.createEventTap(CreateCmdUtils.QQ_EDIT_TEXT);
                            cmds[1] = CreateCmdUtils.createSleep(1);
                            cmds[2] = CreateCmdUtils.createInputText(finalMsg);
                            cmds[3] = CreateCmdUtils.createSleep(1);
                            cmds[4] = CreateCmdUtils.createEventTap(CreateCmdUtils.QQ_SEND_BUTTON);
                            cmds[5] = CreateCmdUtils.createSleep(1);
                            cmds[6] = CreateCmdUtils.createEventKey(KeyEvent.KEYCODE_BACK);
                            cmds[7] = CreateCmdUtils.createSleep(1);
                            cmds[8] = CreateCmdUtils.createEventKey(KeyEvent.KEYCODE_BACK);
                            cmds[9] = CreateCmdUtils.createSleep(1);
                            cmds[10] = CreateCmdUtils.createEventKey(KeyEvent.KEYCODE_BACK);
                            Log.i(TAG, "kai ---- onReceive cmds ----> " + Arrays.toString(cmds));
                            RootShellCmdUtils.exec(cmds);
                        }
                    }, 3000);

                    
                   
                }
            }
        };
    }

    private void registerReceiver() {
        registerReceiver(qqReceiver, new IntentFilter(CustomBroadcastAction.ACTION_RECEIVE_QQ_MSG));
        registerReceiver(dingReceiver, new IntentFilter(CustomBroadcastAction.ACTION_RECEIVE_DING_TALK_MSG));

    }

    private void setListener() {
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean serviceRunning = isServiceRunning(MainActivity.this, "com.zkai.clockin.service.NotificationCollectorService");
                Log.i(TAG,"kai ---- onClick serviceRunning ----> " + serviceRunning);
                String receiveName = etReceiveName.getText().toString();
                if (TextUtils.isEmpty(receiveName)){
                    Toast.makeText(MainActivity.this, "名字不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean enabled = isEnabled();
                if (!enabled) {
                    Toast.makeText(MainActivity.this, "使用此功能前需要授权，5s后打开授权窗口,允许后重新启动", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openNotificationAccess();
                        }
                    }, 5000);
                } else {
                    QQConstant.setNickName(receiveName);
                    startNotificationListenService();
                }
            }
        });
        btnTestAdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = RootShellCmdUtils.execStartApp(PackageName.PN_DING_TALK);
                Log.i(TAG,"kai ---- onClick s ----> " + s);
            }
        });
    }

    private void initView() {
        btnStartService = (Button) findViewById(R.id.btn_start_service);
        etReceiveName = (EditText) findViewById(R.id.et_receive_name);
        btnTestAdb = (Button) findViewById(R.id.btn_test_adb);
        tvQQMsg = (TextView) findViewById(R.id.tv_qq_msg);
    }


    private void createClockInBroadCast() {
        AlarmBroadcastReceiver alarmBroadcastReceiver = new AlarmBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.deskclock.ALARM_ALERT");
        filter.addAction("com.android.deskclock.ALARM_DONE");
        registerReceiver(alarmBroadcastReceiver, filter);
    }
    
    private void applyAuth(){
        setContentView(R.layout.activity_main);
        String string = Settings.Secure.getString(getContentResolver(),"enabled_notification_listeners");
        if (TextUtils.isEmpty(string)||!string.contains(NotificationCollectorService.class.getName())) {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }

    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void openNotificationAccess() {
        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    private void startNotificationListenService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            notificationIntent = new Intent(MainActivity.this,NotificationCollectorService.class);
            startService(notificationIntent);
            toggleNotificationListenerService();
            Toast.makeText(MainActivity.this, "开启成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "手机的系统不支持此功能", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(App.getContext(), "销毁服务", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "kai ---- onDestroy  ----> 销毁服务");
        if (notificationIntent != null) {
            stopService(notificationIntent);
        }
        super.onDestroy();
    }

    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    private void toggleNotificationListenerService() {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(this, NotificationCollectorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(this, NotificationCollectorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

//    public void clip() {
//        // 从API11开始android推荐使用android.content.ClipboardManager
//        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
//        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//        // 将文本内容放到系统剪贴板里。
//        cm.setPrimaryClip(tvMsg.getText());
//        Toast.makeText(this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
//    }

}
