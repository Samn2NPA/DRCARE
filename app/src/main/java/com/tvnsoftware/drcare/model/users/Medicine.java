package com.tvnsoftware.drcare.model.users;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.tvnsoftware.drcare.Utils.Constants.MEDICINE_CHILD;
import static com.tvnsoftware.drcare.activity.LoginActivity.dbRefer;

/**
 * Created by Admin on 7/26/2017.
 */

public class Medicine implements Parcelable {
    private final static String TAG = Medicine.class.getSimpleName();

    private String key;
    private String medName;
    private String unit;

    private static List<Medicine> medicineList;

    public Medicine(){}

    public static List<Medicine> getMedicineList(){
        medicineList = new ArrayList<>();
        fetchMedicine();
        return medicineList;
    }

    public static void getMedicine(){
        medicineList = new ArrayList<>();
        fetchMedicine();
    }

    protected Medicine(Parcel in) {
        key = in.readString();
        medName = in.readString();
        unit = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(medName);
        dest.writeString(unit);
    }

    private static void fetchMedicine(){
        dbRefer.child(MEDICINE_CHILD)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren() ){
                            Log.d("Test", "Medicine - child.getvalue:: " + child.getValue().toString());
                            Medicine med = child.getValue(Medicine.class);
                            med.setKey(child.getKey());
                            medicineList.add(med);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG,"Medicine::onCancelled", databaseError.toException());
                    }
                });
    }

    public static Medicine getMedicineByKey(String medicineKey) {
        for (Medicine item: medicineList) {
            if (item.getKey().equals(medicineKey))
                return item;
        }
        return new Medicine();
    }

    public static String getMedKeyByName(String medName){
        for(Medicine item : medicineList)
            if (item.getMedName().equalsIgnoreCase(medName))
                return item.getKey();
        return "KeyNull";
    }


    public String getKey() {
        return key;
    }

    public String getMedName() {
        return medName;
    }

    public String getUnit() {
        return unit;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
