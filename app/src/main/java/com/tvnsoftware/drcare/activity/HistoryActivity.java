package com.tvnsoftware.drcare.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.adapter.HistoryAdapter;
import com.tvnsoftware.drcare.model.History;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.tvnsoftware.drcare.data.FakeData;

public class HistoryActivity extends AppCompatActivity {
    @BindView(R.id.rc_history)
    RecyclerView mRcHistory;
    private HistoryAdapter mHistoryAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<History> mHistorys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRcHistory();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void setRcHistory(){
        mLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        mHistoryAdapter = new HistoryAdapter(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mHistoryAdapter.getContext(),
                mLayoutManager.getOrientation());
        mRcHistory.addItemDecoration(dividerItemDecoration);
        mRcHistory.setLayoutManager(mLayoutManager);
        mRcHistory.setItemAnimator(new DefaultItemAnimator());
        mRcHistory.setAdapter(mHistoryAdapter);
        setData();
    }
    private void setData(){
//        mHistorys = FakeData.getHistorys();
//        mHistoryAdapter.setmHistories(mHistorys);
    }

}
