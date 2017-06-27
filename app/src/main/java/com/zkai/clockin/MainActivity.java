package com.zkai.clockin;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zkai.clockin.broadcast.AlarmBroadcastReceiver;
import com.zkai.clockin.service.NotificationCollectorService;
import com.zkai.clockin.utils.QQConstant;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "MainActivity";

    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private Button btnStartService;
    private EditText etReceiveName;
    private Intent notificaionIntent;
    private TextView tvQQMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void setListener() {
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

    private void initView() {
        btnStartService = (Button) findViewById(R.id.btn_start_service);
        etReceiveName = (EditText) findViewById(R.id.et_receive_name);
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
            notificaionIntent = new Intent(MainActivity.this,
                    NotificationCollectorService.class);
            startService(notificaionIntent);
            Toast.makeText(MainActivity.this, "开启成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "手机的系统不支持此功能", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(App.getContext(), "销毁服务", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "kai ---- onDestroy  ----> 销毁服务");
        if (notificaionIntent != null) {
            stopService(notificaionIntent);
        }
        super.onDestroy();
    }
}
