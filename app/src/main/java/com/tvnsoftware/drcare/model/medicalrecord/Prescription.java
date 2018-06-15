package com.tvnsoftware.drcare.model.medicalrecord;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static com.tvnsoftware.drcare.Utils.Constants.PRESCRIPTION_CHILD;
import static com.tvnsoftware.drcare.activity.LoginActivity.dbRefer;

/**
 * Created by Thieusike on 8/3/2017.
 */

public class Prescription implements Parcelable {

    private String key;
    private String MedRecKey;
    private String MedicineKey;
    private int MedicineQty;
    private int TimeTake;

    private static List<Prescription> prescriptionList;

    public Prescription() {
    }

    public Prescription(String medRecKey, String medicineKey, int medicineQty, int timeTake) {
        MedRecKey = medRecKey;
        MedicineKey = medicineKey;
        MedicineQty = medicineQty;
        TimeTake = timeTake;
    }

    protected Prescription(Parcel in) {
        key = in.readString();
        MedRecKey = in.readString();
        MedicineKey = in.readString();
        MedicineQty = in.readInt();
        TimeTake = in.readInt();
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

    public static List<Prescription> fetchPrescriptionByMedRecKey(final String MedRecKey){
        prescriptionList = new ArrayList<>();
        dbRefer.child(PRESCRIPTION_CHILD)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren() ){
                            Prescription prsc = child.getValue(Prescription.class);
                            prsc.setKey(child.getKey());

                            if(prsc.getMedRecKey().equals(MedRecKey)) /// TODO: lấy List MedRec của CURRENT_USER_KEY => get PrescriptionList theo MedRec
                                prescriptionList.add(prsc);
                        }
                        Log.d("TEST","prescription size: " + prescriptionList.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG,"Medical Record::onCancelled", databaseError.toException());
                    }
                });
        return prescriptionList;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("MedRecKey",MedRecKey);
        result.put("MedicineKey",MedicineKey);
        result.put("MedicineQty",MedicineQty);
        result.put("TimeTake",TimeTake);
        return result;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getMedRecKey() {
        return MedRecKey;
    }

    public String getMedicineKey() {
        return MedicineKey;
    }

    public int getMedicineQty() {
        return MedicineQty;
    }

    public int getTimeTake() {
        return TimeTake;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(MedRecKey);
        dest.writeString(MedicineKey);
        dest.writeInt(MedicineQty);
        dest.writeInt(TimeTake);
    }
}
