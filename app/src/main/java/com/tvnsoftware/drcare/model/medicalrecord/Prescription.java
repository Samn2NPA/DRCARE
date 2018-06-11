package com.tvnsoftware.drcare.model.medicalrecord;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thieusike on 8/3/2017.
 */

public class Prescription implements Parcelable {
    private String name;
    private Integer quantity;
    private String unit;
    private String usage;

    public Prescription() {
    }

    protected Prescription(Parcel in) {
        name = in.readString();
        unit = in.readString();
        usage = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<Prescription> CREATOR = new Creator<Prescription>() {
        @Override
        public Prescription createFromParcel(Parcel in) {
            return new Prescription(in);
        }

        @Override
        public Prescription[] newArray(int size) {
            return new Prescription[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(unit);
        dest.writeString(usage);
        dest.writeInt(quantity);
    }


}
