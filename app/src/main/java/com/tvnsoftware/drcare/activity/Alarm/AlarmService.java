package com.tvnsoftware.drcare.activity.Alarm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.activity.MainActivity;
import com.tvnsoftware.drcare.model.Remind;

import static android.support.v4.app.NotificationCompat.PRIORITY_MAX;
import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;
import static com.tvnsoftware.drcare.activity.AlarmActivity.ALARM_OFF;
import static com.tvnsoftware.drcare.activity.AlarmActivity.ALARM_ON;
import static com.tvnsoftware.drcare.activity.AlarmActivity.EXTRA;
import static com.tvnsoftware.drcare.activity.AlarmActivity.NEW_REMIND;

/**
 * Created by Samn on 02-Aug-17.
 */

public class AlarmService extends Service {
    private final static String TAG = AlarmService.class.getSimpleName();

    MediaPlayer ringtone;
    private int stateID; //alarm status::: 1 = ON, 0 = OFF
    private boolean isRunning;
    private static int notificationID;
    Context context;
    private Remind chosenRemind;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.context = this;
        Log.d(TAG, "Received start ID: " + startId + ": " + intent);

        //get Extra Value to know: Alarm is ON or OFF?
        String state = intent.getExtras().getString(EXTRA);
        chosenRemind = (Remind) intent.getExtras().get(NEW_REMIND);

        Log.d("State ALARM: ", state);

        switch (state) {
            case ALARM_ON:
                stateID = 1;
                break;
            case ALARM_OFF:
                stateID = 0;
                break;
            default:
                stateID = 0;
                break;
        }

        //if ringtone isn't playing && Alarm is ON => Start ringtone
        if(!this.isRunning && stateID == 1){
            //AlarmActivity.wakeDevice();
            Notification(this);

            //create instance of media player
            ringtone = MediaPlayer.create(this, R.raw.whistle);
            ringtone.start();

            isRunning = true; //ringtone IS playing
            stateID = 0; //Alarm status OFF is set
        }

        //if ringtone is playing && Alarm is OFF => stop ringtone
        else if(this.isRunning && stateID == 0){
            ringtone.stop();
            ringtone.reset();

            isRunning = false;
            stateID = 0;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "on Destroy called", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        this.isRunning = false;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void Notification(Context mcontext){
        notificationID += 1;

        Log.e("NOTIFICATION ID: ", String.valueOf(notificationID));

        //set up Notification service
        NotificationManager notificationMng = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        /**
         * PendingIntent when CLICK on Notification
         */
        //set up intent that goes to Main Activity
        Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);

        //set up pending Intent
        PendingIntent pendingIntentMain = PendingIntent.getActivity(this,
                                    chosenRemind.getRemindID(), intentMainActivity, 0);

        /**
         * PendingIntent when ALARM goes on
         */
        Intent intentAlarmOn = new Intent(this.getApplicationContext(), AlarmONActivity.class);
        PendingIntent pendingIntentAlarm = PendingIntent.getActivity(this,
                                        chosenRemind.getRemindID(), intentAlarmOn, 0);

        //make the Notification Params
        Notification notificationPop = new NotificationCompat.Builder(mcontext)
                .setContentTitle("DrCare")
                .setContentText("Remember to take Medicine")
                .setContentIntent(pendingIntentMain)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_alarm)
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setVisibility(VISIBILITY_PUBLIC)
                .setColor(0xffffff)
                .setPriority(PRIORITY_MAX)
                .setFullScreenIntent(pendingIntentAlarm, true)
                .build();

        notificationMng.notify(notificationID, notificationPop);
    }
}
