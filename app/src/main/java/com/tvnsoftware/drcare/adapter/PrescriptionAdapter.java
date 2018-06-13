package com.tvnsoftware.drcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.model.medicalrecord.Prescription;
import com.tvnsoftware.drcare.model.users.Medicine;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thieusike on 8/3/2017.
 */

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {
    private Context mContext;
    private List<Prescription> prescriptionList;

    public PrescriptionAdapter(Context c, List<Prescription> prescription) {
        this.mContext = c;
        this.prescriptionList = prescription;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prescription, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Prescription prescription = prescriptionList.get(position);
        holder.tvNameMed.setText(Medicine.getMedicineByKey(prescription.getMedicineKey()).getMedName());
        holder.tvQty.setText(prescription.getMedicineQty() + " ");
        holder.txtUnit.setText(Medicine.getMedicineByKey(prescription.getMedicineKey()).getUnit());
        holder.txtUsage.setText(prescription.getNote());
    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNameMed)
        TextView tvNameMed;
        @BindView(R.id.tvQtyMed)
        TextView tvQty;
        @BindView(R.id.tvUnitMed)
        TextView txtUnit;
        @BindView(R.id.tvUsage)
        TextView txtUsage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public Context getContext() {
        return mContext;
    }
}
