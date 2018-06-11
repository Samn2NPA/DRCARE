package com.tvnsoftware.drcare.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * Created by Samn on 29-Jul-17.
 */

public class Remind implements Parcelable {

    private int RemindID;
    private String RemindTime;
    private String RemindLabel;
    private boolean isActivate;
    private String repeatDay;
    private boolean isSnooze;

    public Remind(int remindID, String remindTime, String remindLabel) {
        this.RemindID = remindID;
        RemindTime = remindTime;
        RemindLabel = remindLabel;
        isActivate = true;
    }

    public Remind() {
    }

    protected Remind(Parcel in) {
        RemindID = in.readInt();
        RemindTime = in.readString();
        RemindLabel = in.readString();
        isActivate = in.readByte() != 0;
        repeatDay = in.readString();
        isSnooze = in.readByte() != 0;
    }

    public static final Creator<Remind> CREATOR = new Creator<Remind>() {
        @Override
        public Remind createFromParcel(Parcel in) {
            return new Remind(in);
        }

        @Override
        public Remind[] newArray(int size) {
            return new Remind[size];
        }
    };

    public static Remind updateRemind(Remind oldRemind,
                                          @Nullable String remindTime,
                                          @Nullable String remindLabel,
                                          @Nullable String repeatDay,
                                          boolean isSnooze,
                                          boolean isActivate){
        if (remindTime != null) oldRemind.setRemindTime(remindTime);
        if (remindLabel != null) oldRemind.setRemindLabel(remindLabel);
        if (repeatDay != null) oldRemind.setRepeatDay(repeatDay);
        oldRemind.setIsSnooze(isSnooze);
        oldRemind.setIsActivate(isActivate);
        return oldRemind;
    }

    public int getRemindID() {
        return RemindID;
    }

    public String getRemindTime() {
        return RemindTime;
    }

    public String getRemindLabel() {
        return RemindLabel;
    }

    private void setRemindID(int remindID) {
        RemindID = remindID;
    }

    public boolean getIsActivate() {
        return isActivate;
    }

    public void setIsActivate(boolean activate) {
        isActivate = activate;
    }

    public void setRemindTime(String remindTime) {
        RemindTime = remindTime;
    }

    public void setRemindLabel(String remindLabel) {
        RemindLabel = remindLabel;
    }

    public String getRepeatDay() {
        return repeatDay;
    }

    public void setRepeatDay(String repeatDay) {
        this.repeatDay = repeatDay;
    }

    public boolean getIsSnooze() {
        return isSnooze;
    }

    public void setIsSnooze(boolean snooze) {
        isSnooze = snooze;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(RemindID);
        parcel.writeString(RemindTime);
        parcel.writeString(RemindLabel);
        parcel.writeByte((byte) (isActivate ? 1 : 0));
        parcel.writeString(repeatDay);
        parcel.writeByte((byte) (isSnooze ? 1 : 0));
    }
}
