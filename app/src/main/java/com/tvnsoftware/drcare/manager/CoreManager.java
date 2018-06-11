package com.tvnsoftware.drcare.manager;

import android.content.Context;

import com.google.gson.Gson;
import com.tvnsoftware.drcare.Utils.Constants;
import com.tvnsoftware.drcare.Utils.PreferenceUtils;
import com.tvnsoftware.drcare.model.users.User;

/**
 * Created by Thieusike on 7/31/2017.
 */

public class CoreManager {
    private static CoreManager sDataManager;
    private boolean isLogedIn;
    private User userData;
    private String token;
    private boolean isFirstTime;

    public static CoreManager getInstance(){
        if(sDataManager == null){
            sDataManager = new CoreManager();
        }

        return sDataManager;
    }

    public void initConfig(Context context){
        initData(context);
    }

    public User getUserData() {
        if (this.userData == null){
            this.userData = null;
        }
        return this.userData;
    }

    public void setUserData(Context context, User userData) {
        this.userData = userData;

        Gson gson = new Gson();
        PreferenceUtils.saveStringPref(context, Constants.PREF_USER_DATA, gson.toJson(userData));
    }

    private void initData(Context context){
        if (this.userData == null) {
            Gson gson = new Gson();
            String json = PreferenceUtils.getStringPref(context, Constants.PREF_USER_DATA,   "");
            if (json.equals("")) {
                PreferenceUtils.saveBoolPref(context, Constants.PREF_LOGGED_IN, false);
            }else{
                this.userData = gson.fromJson(json, User.class);
            }
        }

        this.isFirstTime = PreferenceUtils.getBoolPref(context, Constants.KEY_FIRST_TIME, true);
        this.token = PreferenceUtils.getStringPref(context, Constants.PREF_TOKEN, "");
        this.isLogedIn = PreferenceUtils.getBoolPref(context, Constants.PREF_LOGGED_IN, false);
    }

    public boolean isLogedIn() {
        return isLogedIn;
    }

    public void setLogedIn(Context context, boolean logedIn) {
        isLogedIn = logedIn;
        PreferenceUtils.saveBoolPref(context, Constants.PREF_LOGGED_IN, isLogedIn);
    }

    public String getToken() {
        return token;
    }

    public void setToken(Context context, String token) {
        this.token = token;
        PreferenceUtils.saveStringPref(context, Constants.PREF_TOKEN, token);
    }

    public void setFirstTime(Context context, boolean firstTime) {
        isFirstTime = firstTime;
        PreferenceUtils.saveBoolPref(context, Constants.KEY_FIRST_TIME, false);
    }
}
