package com.tvnsoftware.drcare.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.api.RetrofitManager;
import com.tvnsoftware.drcare.manager.CoreManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    long wait = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*RealmConfiguration realmConfig
                = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);*/

        FirebaseApp.initializeApp(this);


        Log.v(TAG, "onCreate " + SplashActivity.class.getName());
        onNewIntent(getIntent());

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        RetrofitManager.getInstance().config(getApplicationContext());
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i;
                if (CoreManager.getInstance().getUserData() == null) {
                    i = new Intent(SplashActivity.this, GuidanceActivity.class);
                } else {
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                }


                startActivity(i);

                // close this activity
                finish();
            }
        }, wait);
    }
}
