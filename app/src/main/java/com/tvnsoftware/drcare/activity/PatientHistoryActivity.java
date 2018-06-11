package com.tvnsoftware.drcare.activity;/*
package com.tvnsoftware.drcare.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.adapter.PatientHistoryAdapter;
import com.tvnsoftware.drcare.data.DummyData;
import com.tvnsoftware.drcare.model.medicineRecords.Medicine;

import java.io.Serializable;
import java.util.List;

public class PatientHistoryActivity extends AppCompatActivity {
    private RecyclerView mRC;
    private LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);
        mRC = (RecyclerView) findViewById(R.id.rc_patient_history);
        List<Medicine> medicineList = DummyData.getMedicine();
        PatientHistoryAdapter adapter = new PatientHistoryAdapter(PatientHistoryActivity.this, medicineList);

//        adapter.setmHistories(medicineList);
        mLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(adapter.getContext(),
                mLayoutManager.getOrientation());
//        dividerItemDecoration.setDrawable(adapter.getContext().getResources().getDrawable(R.drawable.sk_line_divider));
        mRC.addItemDecoration(dividerItemDecoration);
        mRC.setLayoutManager(mLayoutManager);
        mRC.setItemAnimator(new DefaultItemAnimator());
        mRC.setAdapter(adapter);
        adapter.setlistener(new PatientHistoryAdapter.HistoryListener() {
            @Override
            public void onClickHistory(Medicine history) {
                Intent i = new Intent(PatientHistoryActivity.this, PatientHistoryDetailActivity.class);
                i.putExtra("abc", (Serializable) history);
                startActivity(i);
            }
        });
    }
}
*/
