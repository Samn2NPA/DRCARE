package com.tvnsoftware.drcare.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Preconditions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.Utils.DividerItemDecoration;
import com.tvnsoftware.drcare.adapter.DiagnosisAdapter;
import com.tvnsoftware.drcare.model.PRESCRIPTION_STATUS;
import com.tvnsoftware.drcare.model.medicalrecord.MedicalRecord;
import com.tvnsoftware.drcare.model.medicalrecord.Prescription;
import com.tvnsoftware.drcare.model.users.Medicine;
import com.tvnsoftware.drcare.model.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.tvnsoftware.drcare.Utils.Constants.ALREADY_TAKEN;
import static com.tvnsoftware.drcare.Utils.Constants.EXTRA_MED_REC;
import static com.tvnsoftware.drcare.Utils.Constants.EXTRA_PATIENT;
import static com.tvnsoftware.drcare.Utils.Constants.KEY_NULL;
import static com.tvnsoftware.drcare.Utils.Constants.MEDICAL_RECORDS_CHILD;
import static com.tvnsoftware.drcare.Utils.Constants.PRESCRIPTION_CHILD;
import static com.tvnsoftware.drcare.activity.LoginActivity.dbRefer;
import static com.tvnsoftware.drcare.model.PRESCRIPTION_STATUS.ALL_RIGHT;
import static com.tvnsoftware.drcare.model.PRESCRIPTION_STATUS.ALL_WRONG;
import static com.tvnsoftware.drcare.model.PRESCRIPTION_STATUS.WRONG_MEDICINE;
import static com.tvnsoftware.drcare.model.PRESCRIPTION_STATUS.WRONG_QUANTITY;
import static com.tvnsoftware.drcare.model.PRESCRIPTION_STATUS.WRONG_TIMES;

/**
 * This activity allow DOCTOR make diagnosis for patient.
 * When DOCTOR click btnDone on Toolbar. There are 2 updates:
 * 1. update diagnosis
 * 2. add All item in Prescription to database. (each item will be added locally)
 *
 * Created by Admin on 7/26/2017.
 */

public class DiagnosisActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DiagnosisAdapter diagnosisAdapter;
    private MedicalRecord medicalRecord;

    private List<Medicine> medicineList;

    private List<Prescription> prescList;

    private boolean SUBMIT_MedRec_RESULT = false;
    private boolean SUBMIT_PRESCRIPTION_RESULT = false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.et_medicine)
    EditText etMedicine;
    @BindView(R.id.et_times_taken)
    EditText etTimes;
    @BindView(R.id.et_quantity)
    EditText etQuantity;
    @BindView(R.id.tv_dia_patientName)
    TextView tvPatientName;
    @BindView(R.id.etDiseaseName)
    EditText etDiseaseName;
    @BindView(R.id.etNote)
    EditText etNote;
    @BindView(R.id.etDayQty)
    EditText etDayQty;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_diagnosis_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        applyFontForToolbarTitle(toolbar);

        setUpView();

        setUpRecyclerView();
        prepareData();
        checkEmptyPrescription();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NexaLight.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setupWindowAnimations();
    }

    @OnClick(R.id.btnAdd)
    public void onCLickBtnAdd(){
        recyclerView.setVisibility(View.VISIBLE);

        String medicineKey = Medicine.getMedKeyByName(etMedicine.getText().toString());
        int inputTimes = Integer.parseInt(etTimes.getText().toString());
        int inputQuantity = Integer.parseInt(etQuantity.getText().toString());


        String checkInput = getResponseMessage(checkPreAddPrescription(medicineKey, inputTimes, inputQuantity));

        if(checkInput == null){
            addPrescription(medicineKey, inputQuantity, inputTimes);

            resetTextField();
        }
        else
            Toast.makeText(getBaseContext(), checkInput, Toast.LENGTH_SHORT).show();

    }

    private PRESCRIPTION_STATUS checkPreAddPrescription(String medicineKey, int timeTakeMed, int quantity){
        int modQty = quantity%timeTakeMed;
        PRESCRIPTION_STATUS result = ALL_RIGHT;

        if(medicineKey.equals(KEY_NULL) && timeTakeMed > 10 && modQty != 0){
            result = ALL_WRONG;
        }
        else {
            if(medicineKey.equals(KEY_NULL)) //check get Medicine Key by medName
                result = WRONG_MEDICINE;
            else
            if(timeTakeMed > 10)    //check time to Take medicine
                result = WRONG_TIMES;
            else
            {
                Log.d("Test:: ", "modQty = quantity%timeTakeMed = " + quantity + " % " + timeTakeMed + " = " + modQty);
                if(modQty != 0)     //check quantity
                    result = WRONG_QUANTITY;
            }
        };
        return result;
    }

    private String getResponseMessage(PRESCRIPTION_STATUS status){
        String result;
        switch (status){
            case ALL_WRONG: result = getResources().getString(R.string.ALL_WRONG_response); break;
            case WRONG_MEDICINE: result = getResources().getString(R.string.WRONG_MEDICINE_response); break;
            case WRONG_TIMES: result = getResources().getString(R.string.WRONG_TIMES_response); break;
            case WRONG_QUANTITY: result = getResources().getString(R.string.WRONG_QUANTITY_response); break;
            default: result = null;
        }
        return result;
    }

    private void resetTextField(){
        etMedicine.setText("");
        etTimes.setText("");
        etQuantity.setText("");
    }

    private void setUpView(){
        etMedicine.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etDayQty.getText().toString().isEmpty()){
                    NoticeDialog("Attention", "Enter Day Quantity First!");
                    etDayQty.requestFocus();
                }
            }
        });

        etTimes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etMedicine.getText().toString().isEmpty()){
                    NoticeDialog("Attention", "Enter Medicine Name First!");
                    etMedicine.requestFocus();
                }
            }
        });
        etTimes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(etMedicine.getText().toString().isEmpty()){
                    NoticeDialog("Attention", "Enter Medicine Name First!");
                    etMedicine.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int quantity =  Integer.parseInt(etDayQty.getText().toString()) * Integer.parseInt(etQuantity.getText().toString());
                etQuantity.setText(String.valueOf(quantity));
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void prepareData(){
        //get Medical Record
        medicalRecord = getIntent().getParcelableExtra(EXTRA_MED_REC);

        //get Medicine list
        medicineList = Medicine.getMedicineList();

        //initialize Prescription list
        prescList = new ArrayList<>();

        tvPatientName.setText(User.getUserByKey(medicalRecord.getPatientKey()).getUserName());//medicalRecord.getPatientName());
        etMedicine.addTextChangedListener(mTextWatcher);
        etTimes.addTextChangedListener(mTextWatcher);
        etQuantity.addTextChangedListener(mTextWatcher);
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }

    private void NoticeDialog(String title, String content){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
        sweetAlertDialog.show();
    }

    private void setUpRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        diagnosisAdapter = new DiagnosisAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(diagnosisAdapter);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkEmptyPrescription();
        }
    };

    private void checkEmptyPrescription() {
        String medicineName = etMedicine.getText().toString();
        String medicineTimes = etTimes.getText().toString();
        String medicineQuantity = etQuantity.getText().toString();

        if (medicineName.isEmpty() || medicineQuantity.isEmpty() || medicineTimes.isEmpty()){{
            btnAdd.setEnabled(false);
        }
        } else {
            btnAdd.setEnabled(true);
        }
    }

    /**
     * thêm từng dòng trong Đơn thuốc hiện tại vào local List<Prescrition>
     * @param medicineKey
     * @param medicineQty
     * @param timeTake
     */
    private void addPrescription(String medicineKey, int medicineQty, int timeTake){
        Prescription presc = new Prescription(medicalRecord.getKey(), medicineKey, medicineQty, timeTake);

        prescList.add(presc);
        diagnosisAdapter.add(presc);
        Toast.makeText(getBaseContext(), "Added successfully", Toast.LENGTH_SHORT).show();
    }

    private void writeNewPrescription(Prescription newPresc){

        Map<String, Object> postValue = newPresc.toMap();

        dbRefer.child(PRESCRIPTION_CHILD).push().setValue(postValue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SUBMIT_PRESCRIPTION_RESULT = true;
                        Log.d("Test","OnSuccess create Prescription");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        SUBMIT_PRESCRIPTION_RESULT = false;
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /***
     * update thêm Diagnosis (chẩn đoán bệnh) vào Medical Record
     * update isTaken = 1 (đã khám xong)
     * update Note
     * @param diseaseName
     */
    private void SubmitMedicalRecord(String diseaseName, String note){
        medicalRecord.setDiseaseName(diseaseName);
        medicalRecord.setIsTaken(ALREADY_TAKEN);
        medicalRecord.setMedRecNote(note);

        Map<String, Object> updateValue = medicalRecord.toMap();

        Map<String, Object> childUpdate = new HashMap<>();
        childUpdate.put(medicalRecord.getKey(), updateValue);

        dbRefer.child(MEDICAL_RECORDS_CHILD).updateChildren(childUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "OnSuccess update CURRENT medicalRecord", Toast.LENGTH_SHORT).show();
                        SUBMIT_MedRec_RESULT = true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void SubmitPrescription(List<Prescription> prescList){
        for (Prescription presc : prescList){
            writeNewPrescription(presc);
        }
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

    /*set menu (button) on toolbar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_done:
                onClickDone();
        }
        return  true;
    }

    private void onClickDone() {
        //Todo: update Medical Records here : Diagnosis + Prescription (Add All)
        SubmitMedicalRecord(etDiseaseName.getText().toString(), etNote.getText().toString());
        SubmitPrescription(prescList);

        Intent dataParse = new Intent();
        dataParse.putExtra(EXTRA_MED_REC, medicalRecord);
        if(SUBMIT_MedRec_RESULT && SUBMIT_PRESCRIPTION_RESULT)
            setResult(RESULT_OK, dataParse);
        else
            setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
