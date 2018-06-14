package com.tvnsoftware.drcare.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.Utils.GlideCircleTransformation;
import com.tvnsoftware.drcare.adapter.PrescriptionAdapter;
import com.tvnsoftware.drcare.model.medicalrecord.MedicalRecord;
import com.tvnsoftware.drcare.model.medicalrecord.Prescription;
import com.tvnsoftware.drcare.model.users.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tvnsoftware.drcare.Utils.Constants.EXTRA_MED_REC;
import static com.tvnsoftware.drcare.Utils.Constants.EXTRA_PATIENT;

public class DiagnosisDetailActivity extends AppCompatActivity {



    private PrescriptionAdapter prescriptionAdapter;
    private LinearLayoutManager mLayoutManager;

    private MedicalRecord medicalRecord;

    @BindView(R.id.rc_prescription)
    RecyclerView mRcPrescription;
    @BindView(R.id.tvDiagnosis_patient)
    TextView tvDiagnosis_patient;
    @BindView(R.id.tvDoctorName)
    TextView tvDoctorName;
    @BindView(R.id.tvDoctorEspecial)
    TextView tvDoctorEspecial;
    @BindView(R.id.ivDoctorCover)
    ImageView ivDoctorCover;
    /*@BindView(R.id.ivSetAlarm_patient)
    ImageView ivSetAlarm_patient;*/
    @BindView(R.id.fabBookDate)
    FloatingActionButton fabBookDate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        applyFontForToolbarTitle(toolbar);

        medicalRecord = getIntent().getExtras().getParcelable(EXTRA_MED_REC);

        initialize();
        setUpView();
    }

    private void initialize(){
        prescriptionAdapter = new PrescriptionAdapter(this,  Prescription.fetchPrescriptionByMedRecKey(medicalRecord.getKey()));
        mLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(prescriptionAdapter.getContext(),
                mLayoutManager.getOrientation());
        mRcPrescription.addItemDecoration(dividerItemDecoration);
        mRcPrescription.setLayoutManager(mLayoutManager);
        mRcPrescription.setItemAnimator(new DefaultItemAnimator());
        mRcPrescription.setAdapter(prescriptionAdapter);
    }

    private void setUpView() {
        tvDiagnosis_patient.setText(medicalRecord.getDiseaseName());
        tvDoctorName.setText(User.getUserByKey(medicalRecord.getDoctorKey()).getUserName());
        tvDoctorEspecial.setText(User.getUserByKey(medicalRecord.getDoctorKey()).getSpecial());

        Glide.with(this).load(User.getUserByKey(medicalRecord.getDoctorKey()).getUserImage())
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new GlideCircleTransformation(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivDoctorCover);
    }

   /* @OnClick(R.id.ivSetAlarm_patient)
    public void onClickSetAlarm(){
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }
*/
    @OnClick(R.id.fabBookDate)
    public void onClickBookDate(){
        Toast.makeText(this, "You can pick a day to Meet doctor in next version", Toast.LENGTH_LONG).show();
    }

    /*set menu (button) on toolbar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diagnosis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_alarm:
                onClickSetAlarm();
        }
        return  true;
    }

    private void onClickSetAlarm(){
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
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
}
