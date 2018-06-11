package com.tvnsoftware.drcare.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.Utils.DividerItemDecoration;
import com.tvnsoftware.drcare.adapter.DiagnosisAdapter;
import com.tvnsoftware.drcare.model.medicalrecord.MedicalRecord;
import com.tvnsoftware.drcare.model.users.Medicine;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Admin on 7/26/2017.
 */

public class DiagnosisActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DiagnosisAdapter diagnosisAdapter;
    private MedicalRecord medicalRecord;

    public static final String EXTRA_DOCTOR = "EXTRA_DOCTOR";

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_diagnosis_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        applyFontForToolbarTitle(toolbar);

        setUpRecyclerView();
        prepareData();
        checkEmptyPrescription();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                addPrescription();
                etMedicine.setText("");
                etTimes.setText("");
                etQuantity.setText("");
            }
        });

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NexaLight.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setupWindowAnimations();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void prepareData(){
        medicalRecord = getIntent().getParcelableExtra("patient");
        tvPatientName.setText("Tesst name");//medicalRecord.getPatientName());
        etMedicine.addTextChangedListener(mTextWatcher);
        etTimes.addTextChangedListener(mTextWatcher);
        etQuantity.addTextChangedListener(mTextWatcher);
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
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

        if (medicineName.isEmpty() || medicineQuantity.isEmpty() || medicineTimes.isEmpty()){
            btnAdd.setEnabled(false);
        } else {
            btnAdd.setEnabled(true);
        }
    }

    private void addPrescription(){
        Medicine medicine = new Medicine(etMedicine.getText().toString().trim(), etQuantity.getText().toString().trim() + " pills",
                etTimes.getText().toString().trim() + " time(s)/day");
        diagnosisAdapter.add(medicine);
        Toast.makeText(getBaseContext(), "Added successfully", Toast.LENGTH_SHORT).show();
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
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
