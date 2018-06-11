package com.tvnsoftware.drcare.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.Utils.SpaceItemDecoration;
import com.tvnsoftware.drcare.activity.Alarm.AlarmDetailsActivity;
import com.tvnsoftware.drcare.activity.Alarm.AlarmReceiver;
import com.tvnsoftware.drcare.adapter.AlarmAdapter;
import com.tvnsoftware.drcare.model.Remind;
import com.tvnsoftware.drcare.model.Repeat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tvnsoftware.drcare.Application.alarmManager;
import static com.tvnsoftware.drcare.Application.alarm_intent;
import static com.tvnsoftware.drcare.Application.pendingIntent;
import static com.tvnsoftware.drcare.activity.Alarm.AlarmDetailsActivity.getHour_Mins;

public class AlarmActivity extends AppCompatActivity {

    private static final String TAG = AlarmActivity.class.getSimpleName();
    public static final String EXTRA_IS_UPDATE = "EXTRA_IS_UPDATE";
    public static final String EXTRA_ALARM_UPDATE = "EXTRA_ALARM_UPDATE";
    private static final int REQUEST_CODE = 20;
    public static final String NEW_REMIND = "NEW_REMIND";

    //for Alarm Manager
    public final static String EXTRA = "EXTRA";
    public final static String ALARM_ON = "ALARM_ON";
    public final static String ALARM_OFF = "ALARM_OFF";
    //-- end Alarm Manager/

    private Context context;
    private Calendar calendar;

    @BindView(R.id.rlAlarmLayout)
    RelativeLayout rlAlarmLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fabAddAlarm)
    FloatingActionButton fabAddAlarm;
    @BindView(R.id.rvlistAlarm)
    RecyclerView rvlistAlarm;

    private AlarmAdapter alarmAdapter;
    private List<Remind> remindList;
    private static boolean IS_UPDATE_ALARM;
    private static int REMIND_POSITION;
    private List<Repeat> repeatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        this.context = this;
        applyFontForToolbarTitle(toolbar);
        setUpView();

        initializeAlarm();

        prepareData();

        prepareData_RepeatList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            Remind newRemind = (Remind) data.getExtras().get(NEW_REMIND);

            if(!IS_UPDATE_ALARM){ //new
                alarmAdapter.appendIndividual(newRemind);
            }
            else {
                remindList.remove(REMIND_POSITION);
                remindList.add(REMIND_POSITION - 1, newRemind);
            }

            alarmStartOrCancel(true, newRemind); //set alarm cho newRemind!
        }
        else
            Toast.makeText(this, "Fail to update/add remind", Toast.LENGTH_SHORT).show();
    }

    private void initializeAlarm() {
        this.context = this;
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        alarm_intent = new Intent(this.context, AlarmReceiver.class);
    }

    private Calendar setAlarm(Remind remind){
        int hour = getHour_Mins(remind.getRemindTime()).first;
        int mins = getHour_Mins(remind.getRemindTime()).second;
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE, mins);
        return calendar;
    }

    /***
     * @param isStart: startAlarm = true || cancelAlarm = false
     * @param chosenRemind :  the remind that need to set alarm
     */
    private void alarmStartOrCancel(boolean isStart, Remind chosenRemind){
        Calendar calendar = setAlarm(chosenRemind);
        if(isStart){
            //Extra VALUE to intent:: tell that Alarm is ON
            alarm_intent.putExtra(EXTRA, ALARM_ON);
            alarm_intent.putExtra(NEW_REMIND, chosenRemind);

            Log.d("Alarm :: ", "ON = " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                                        + calendar.get(Calendar.MINUTE));

            //pendingIntent -> send to AlarmReceiver => EXTRA VALUE is sent to AlarmReceiver
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, chosenRemind.getRemindID(), alarm_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "ALARM ON", Toast.LENGTH_SHORT).show();
        }
        else {
            //Extra VALUE to intent:: tell that Alarm is ON
            alarm_intent.putExtra(EXTRA, ALARM_OFF);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, chosenRemind.getRemindID(), alarm_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpView(){
        alarmAdapter = new AlarmAdapter(context);
        rvlistAlarm.setAdapter(alarmAdapter);
        remindList = new ArrayList<>();
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(
                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvlistAlarm.setLayoutManager(staggeredGridLayoutManager);
        SpaceItemDecoration decoration = new SpaceItemDecoration(10);
        rvlistAlarm.addItemDecoration(decoration);

        alarmAdapter.setListener(new AlarmAdapter.Listener() {
            @Override
            public void onUpdateRemind(int clickedPosition, Remind remindToUpdate) {
                IS_UPDATE_ALARM = true;
                REMIND_POSITION = clickedPosition;
                intentToAlarm_Details(IS_UPDATE_ALARM, remindToUpdate);
            }

            @Override
            public void onStatusAlarmChange(boolean isActivate, Remind remindToUpdate) {
                alarmStartOrCancel(isActivate, remindToUpdate);
            }
        });
    }

    private void prepareData(){
        Remind remind = new Remind(1, "19:29", "Alarm AFternoon");
        remindList.add(remind);
        Remind remind1 = new Remind(2, "9:32", "Alarm Morning");
        remindList.add(remind1);
        Log.d(TAG, "TEST " + remindList.size() + " | ");
        alarmAdapter.setData(remindList);

        alarmStartOrCancel(true, remind);
        alarmStartOrCancel(true, remind1);
    }

    private void prepareData_RepeatList(){
        // TODO: 31-Jul-17 : get LIST REPEAT NAME
        repeatList = new ArrayList<>();
        repeatList.add(new Repeat(1, "Never"));
        repeatList.add(new Repeat(2, "Every Day"));
    }

    @OnClick(R.id.fabAddAlarm)
    public void onClickAddAlarm(){
        IS_UPDATE_ALARM = false;
        intentToAlarm_Details(IS_UPDATE_ALARM, null);
    }

    private void intentToAlarm_Details(boolean isUpdateAlarm, Remind remindToUpdate){
        Intent intent = new Intent(this, AlarmDetailsActivity.class);
        intent.putExtra(EXTRA_IS_UPDATE, isUpdateAlarm);
        intent.putExtra(EXTRA_ALARM_UPDATE, remindToUpdate);
        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void applyFontForToolbarTitle(Toolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setTextSize(24);
                Typeface titleFont = Typeface.
                        createFromAsset(getAssets(), "fonts/NexaBold.otf");
                if (tv.getText().equals(toolbar.getTitle())) {
                    tv.setTypeface(titleFont);
                    break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}