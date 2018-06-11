package com.tvnsoftware.drcare.activity.Alarm;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.tvnsoftware.drcare.model.Remind;

import static com.tvnsoftware.drcare.activity.AlarmActivity.ALARM_OFF;
import static com.tvnsoftware.drcare.activity.AlarmActivity.EXTRA;
import static com.tvnsoftware.drcare.activity.AlarmActivity.NEW_REMIND;

/**
 * Created by Samn on 02-Aug-17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //create intent to Ringtone SERVICE
        Intent ringtoneIntent = new Intent(context, AlarmService.class);

        //1. get EXTRA VALUE from "alarm_intent"
        String AlarmStatus = intent.getExtras().getString(EXTRA);
        Remind chosenRemind = (Remind) intent.getExtras().get(NEW_REMIND);

        Log.d("SAMN", "test: Alarm Status:: " + AlarmStatus);

        if(AlarmStatus.equals(ALARM_OFF)){
            /*set up Diable receiver*/
            //to "enable the receiver": override the manifest
            ComponentName receiver = new ComponentName(context, AlarmReceiver.class);
            PackageManager pm = context.getPackageManager();
            ///--- ///

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);

            Log.d("COMPONENT SETTING: ", String.valueOf(pm.getComponentEnabledSetting(receiver)));
        }
        /*else {
            Intent alarmIntent = new Intent(context, AlarmActivity.class);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(alarmIntent);
        }*/

        // 2. pass it to ringtoneIntent to send to RingtonePlayingService
        ringtoneIntent.putExtra(EXTRA, AlarmStatus);
        ringtoneIntent.putExtra(NEW_REMIND, chosenRemind);

        //start service ringtone
        context.startService(ringtoneIntent);
        //explain: ringtoneIntent will be sent to RingtonePlayingService => out Extra Value to notice if ALARM IS ON OR OFF?
    }
}
