package com.tvnsoftware.drcare.model.users;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 7/26/2017.
 */

public class Medicine implements Parcelable {
    private String medicineName;
    private String medicineQuantity;
    private String medicineTimesTaken;

    public Medicine(){}

    public Medicine(String medicineName, String medicineQuantity, String medicineTimesTaken) {
        this.medicineName = medicineName;
        this.medicineQuantity = medicineQuantity;
        this.medicineTimesTaken = medicineTimesTaken;
    }

    protected Medicine(Parcel in) {
        medicineName = in.readString();
        medicineQuantity = in.readString();
        medicineTimesTaken = in.readString();
    }

    public static final Creator<Medicine> CREATOR = new Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(String medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public String getMedicineTimesTaken() {
        return medicineTimesTaken;
    }

    public void setMedicineTimesTaken(String medicineTimesTaken) {
        this.medicineTimesTaken = medicineTimesTaken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(medicineName);
        dest.writeString(medicineQuantity);
        dest.writeString(medicineTimesTaken);
    }
}
