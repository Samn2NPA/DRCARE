package com.tvnsoftware.drcare.adapter;/*
package com.tvnsoftware.drcare.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.model.medicineRecords.Medicine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

*/
/**
 * Created by Thieusike on 7/24/2017.
 *//*


public class PatientHistoryAdapter extends RecyclerView.Adapter<PatientHistoryAdapter.ViewHolder> {
    private Context mContext;
    private List<Medicine> mHistories;
    private HistoryListener mHistoryListener;

    public interface HistoryListener {
        public void onClickHistory(Medicine history);
    }
    public PatientHistoryAdapter(Context context, List<Medicine> histories) {
        this.mContext = context;
        mHistories = histories;
    }
    public void setlistener(HistoryListener listener) {
        mHistoryListener = listener;
    }

    public void setmHistories(List<Medicine> histories) {
        this.mHistories = histories;
        notifyDataSetChanged();
    }

    public void appendHistories(List<Medicine> histories) {
        this.mHistories.addAll(histories);
        notifyDataSetChanged();
    }
    @Override
    public PatientHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PatientHistoryAdapter.ViewHolder holder, int position) {
        final Medicine history = mHistories.get(position);
//        holder.txtDate.setText(history.getDate());
        holder.txtDiagnose.setText(history.getDiseaseCode() + "");
        holder.txtDoctorName.setText(history.getUserCode() +"");
        holder.txtDecsription.setText(history.getMedicalSymptoms());
        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistoryListener.onClickHistory(history);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mHistories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_diagnose)
        TextView txtDiagnose;
        @BindView(R.id.txt_doctor_name)
        TextView txtDoctorName;
        @BindView(R.id.txt_decsription)
        TextView txtDecsription;
        @BindView(R.id.cv_main)
        CardView cvMain;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public Context getContext(){
        return this.mContext;
    }
}
*/
