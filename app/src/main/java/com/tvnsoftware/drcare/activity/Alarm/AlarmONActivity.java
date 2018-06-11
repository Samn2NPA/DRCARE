package com.tvnsoftware.drcare.activity.Alarm;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.tvnsoftware.drcare.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tvnsoftware.drcare.Application.alarmManager;
import static com.tvnsoftware.drcare.Application.alarm_intent;
import static com.tvnsoftware.drcare.Application.pendingIntent;
import static com.tvnsoftware.drcare.activity.AlarmActivity.ALARM_OFF;
import static com.tvnsoftware.drcare.activity.AlarmActivity.EXTRA;

public class AlarmONActivity extends AppCompatActivity {

    @BindView(R.id.btnAlarmOFF)
    Button btnAlarmOFF;
    static PowerManager.WakeLock fullWakeLock;
    PowerManager.WakeLock partialWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_on);

        ButterKnife.bind(this);
        initializeView();

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        fullWakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "DrCare -  FULL WAKE UP");
        partialWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DrCare - Partial Wake Lock");
    }

    private void initializeView() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_intent = new Intent(this, AlarmReceiver.class);
    }

    public static void wakeDevice() {
        fullWakeLock.acquire();

        /*KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();*/
    }

    @OnClick(R.id.btnAlarmOFF)
    public void onClickbtnAlarmOFF(){
        alarmManager.cancel(pendingIntent);
        Log.d("Alarm:: ", "off");

        //Extra VALUE to intent:: tell that Alarm is ON
        alarm_intent.putExtra(EXTRA, ALARM_OFF);

        //send signal to stop ringtone
        sendBroadcast(alarm_intent);

        finish();
        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
