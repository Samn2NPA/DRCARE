package com.tvnsoftware.drcare.model.medicalrecord;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tvnsoftware.drcare.adapter.ROLE_STATE;

import java.util.ArrayList;
import java.util.List;

import static com.tvnsoftware.drcare.Utils.Constants.MEDICAL_RECORDS_CHILD;
import static com.tvnsoftware.drcare.activity.LoginActivity.dbRefer;

/**
 * Created by Admin on 7/26/2017.
 */

public class MedicalRecord implements Parcelable {
    private static final String TAG = MedicalRecord.class.getSimpleName();

    /**
     * SAMN .. 27-Jul-2017
     */

    private String Key;
    private int isTaken; ///for STATUS of Medical Record -- DOCTOR
    private String DoctorKey;
    private String PatientKey;
    private String DiseaseName;
    private String DayCreated;

    private static List<MedicalRecord> patientList;
    private static List<MedicalRecord> medicalList;

    private Listener listener;

    public interface Listener{
        void onFetchSuccess(List<MedicalRecord> resultList);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    protected MedicalRecord(Parcel in) {
        isTaken = in.readInt();
        DoctorKey = in.readString();
        PatientKey = in.readString();
        DiseaseName = in.readString();
        DayCreated = in.readString();
    }

    public static final Creator<MedicalRecord> CREATOR = new Creator<MedicalRecord>() {
        @Override
        public MedicalRecord createFromParcel(Parcel in) {
            return new MedicalRecord(in);
        }

        @Override
        public MedicalRecord[] newArray(int size) {
            return new MedicalRecord[size];
        }
    };

    public int getIsTaken() {
        return isTaken;
    }

    public void setIsTaken(int isTaken) {
        this.isTaken = isTaken;
    }

    public String getMedRecStatus(){
        return (this.isTaken == 0) ? "Waiting" : "Done";
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getKey() {
        return Key;
    }

    public MedicalRecord() {
    }

    public MedicalRecord(ROLE_STATE role, Listener listener){
        this.listener = listener;
        if(role == ROLE_STATE.DOCTOR)
            fetchRecordForDoctor();
        else
            fetchRecordForPatient();
    }

    public MedicalRecord(int isTaken, String doctorKey, String patientKey, String diseaseName, String dayCreated) {
        this.isTaken = isTaken;
        DoctorKey = doctorKey;
        PatientKey = patientKey;
        DiseaseName = diseaseName;
        DayCreated = dayCreated;
    }

    public String getDoctorKey() {
        return DoctorKey;
    }

    public String getPatientKey() {
        return PatientKey;
    }

    public String getDiseaseName() {
        return DiseaseName;
    }

    public String getDayCreated() {
        return DayCreated;
    }

    /*public static List<MedicalRecord> getPatientList() {
        patientList = new ArrayList<>();
        return fetchRecordForDoctor();
    }*/

    private void fetchRecordForDoctor(){
        patientList = new ArrayList<>();
        dbRefer.child(MEDICAL_RECORDS_CHILD)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren() ){
                            MedicalRecord medRec = child.getValue(MedicalRecord.class);
                            medRec.setKey(child.getKey());

                            if(medRec.getIsTaken() == 0)
                                patientList.add(medRec);
                        }

                        Log.d("TEST","patient sizxe: " + patientList.size());

                        listener.onFetchSuccess(patientList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG,"Medical Record::onCancelled", databaseError.toException());
                    }
                });
    }

    private void fetchRecordForPatient(){
        dbRefer.child(MEDICAL_RECORDS_CHILD)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren() ){
                            MedicalRecord medRec = child.getValue(MedicalRecord.class);
                            medRec.setKey(child.getKey());

                            if(medRec.getIsTaken() == 1)
                                medicalList.add(medRec);
                        }

                        listener.onFetchSuccess(medicalList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG,"Medical Record::onCancelled", databaseError.toException());
                    }
                });
    }

    private static List<Prescription> pre() {
        List<Prescription> res = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Prescription prescription = new Prescription();
            prescription.setName("Paracetamol " + i);
            prescription.setQuantity(10);
            prescription.setUnit("Gói");
            prescription.setUsage("2 times/day/1 unit");
            res.add(prescription);
        }
        return res;
    }



    /*public static List<MedicalRecord> getMRHistoryList() {
        medicalList = new ArrayList<>();
        fetchRecordForPatient();
        return medicalList;
    }*/


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(isTaken);
        dest.writeString(DoctorKey);
        dest.writeString(PatientKey);
        dest.writeString(DiseaseName);
        dest.writeString(DayCreated);
    }

    public List<Prescription> getPrescriptionList() {
        List<Prescription> list = new ArrayList<>();

        for(int i = 1; i<= 10; i++){
            //list.add(new Medicine("name"+ i, "brand" +i, "finish",i ));
        }
        return list;
    }
}