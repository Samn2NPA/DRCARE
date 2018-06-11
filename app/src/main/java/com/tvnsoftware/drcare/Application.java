package com.tvnsoftware.drcare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tvnsoftware.drcare.model.users.Medicine;
import com.tvnsoftware.drcare.model.users.User;

import java.util.ArrayList;
import java.util.List;

import static com.tvnsoftware.drcare.Utils.Constants.USER_CHILD;

/**
 * Created by Samn on 02-Aug-17.
 */

public class Application extends android.app.Application {

    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;
    public static Intent alarm_intent;

    public static int isUsedToLogin = 0;

    public static List<User> users;



}
