package com.tvnsoftware.drcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.model.users.Medicine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 7/26/2017.
 */

public class DiagnosisAdapter extends RecyclerView.Adapter<DiagnosisAdapter.ViewHolder> {

    private List<Medicine> medicines;
    private Context context;

    public DiagnosisAdapter(Context context) {
        this.medicines = new ArrayList<>();
        this.context = context;
    }

    public void add(Medicine medicine){
        this.medicines.add(medicine);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item_prescription, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Medicine medicine = medicines.get(position);
        holder.tvPrescriptionMedicine.setText(medicine.getMedicineName());
        holder.tvPrescriptionQuantity.setText(medicine.getMedicineQuantity());
        holder.tvPrescriptionTimes.setText(medicine.getMedicineTimesTaken());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                confirmOnRemove(holder, position);
                return true;
            }
        });
    }

    private void confirmOnRemove(ViewHolder holder, final int position){
//        AlertDialog.Builder alert = new AlertDialog.Builder(
//                holder.tvPrescriptionMedicine.getContext());
//        alert.setTitle("Confirmation!!");
//        alert.setMessage("Are you sure to delete this record ?");
//        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                medicines.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeRemoved(position, getItemCount());
//                dialog.dismiss();
//            }
//        });
//        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        alert.show();

        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        medicines.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeRemoved(position, getItemCount());
                        sDialog.dismissWithAnimation();
                    }
                });
        sweetAlertDialog.show();
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_prescription_medicine)
        TextView tvPrescriptionMedicine;
        @BindView(R.id.tv_prescription_quantity)
        TextView tvPrescriptionQuantity;
        @BindView(R.id.tv_prescription_times)
        TextView tvPrescriptionTimes;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
