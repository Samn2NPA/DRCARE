package com.tvnsoftware.drcare.adapter;//package com.tvnsoftware.drcare.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.tvnsoftware.drcare.R;
//import com.tvnsoftware.drcare.activity.DiagnosisActivity;
//import com.tvnsoftware.drcare.model.medicalrecord.MedicalRecord;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//import static com.tvnsoftware.drcare.activity.DiagnosisActivity.PATIENT;
//
///**
// * Created by Admin on 7/24/2017.
// */
//
//public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {
//    private static final String TAG = GeneralAdapter.class.getSimpleName();
//
//    private final List<MedicalRecord> medicalRecords;
//    private Context context;
//    private ArrayList<MedicalRecord> arrayList;
//
//    private static ROLE_STATE stateByRole;
//
//    public GeneralAdapter(Context context) {
//        this.medicalRecords = new ArrayList<>();
//        this.context = context;
//    }
//
//    /**
//     * by Samn at 2:11AM 28-Jul-2017
//     */
//    public void setData(List<MedicalRecord> medical_records){
//        medicalRecords.clear();
//        medicalRecords.addAll(medical_records);
//        notifyDataSetChanged();
//    }
//
//    public void setState(int roleID) {
//        stateByRole = roleID == 1 ? ROLE_STATE.PATIENT : ROLE_STATE.DOCTOR;
//    }
//
//    /* dùng cho Endless swipe (Swipe to load more) */
//    public void appendData(List<MedicalRecord> newMedicalRecords) {
//        int nextPos = medicalRecords.size();
//        medicalRecords.addAll(nextPos, newMedicalRecords);
//        notifyItemRangeChanged(nextPos,newMedicalRecords.size());
//    }
//
//    public void addPatient(MedicalRecord medicalRecord){
//        this.medicalRecords.add(medicalRecord);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item_layout, parent, false);
//        return new GeneralAdapter.ViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        final MedicalRecord medicalRecord = medicalRecords.get(position);
//
//        if(stateByRole == ROLE_STATE.DOCTOR){
//           BindView_DoctorScreen(holder, medicalRecord);
//        }
//        else //PATIENT
//        {
//           BindView_PatientScreen(holder, medicalRecord);
//        }
//    }
//
//    private void BindView_DoctorScreen(ViewHolder holder, final MedicalRecord medicalRecord){
//        holder.tvName.setText(medicalRecord.getPatientName());
//        holder.tvDateCreated.setVisibility(View.GONE);
//        holder.tvNote_Status.setText(medicalRecord.getPatientStatus());
//        holder.tvNote_Status.setTypeface(holder.tvNote_Status.getTypeface(), Typeface.ITALIC);
//        holder.ivCover.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), DiagnosisActivity.class);
//                intent.putExtra("patient", medicalRecord);
//                v.getContext().startActivity(intent);
//            }
//        });
//    }
//
//    private void BindView_PatientScreen(ViewHolder holder, final MedicalRecord medicalRecord){
//        holder.tvName.setText(medicalRecord.getDiseaseName());
//        holder.tvDateCreated.setVisibility(View.VISIBLE);
//        holder.tvDateCreated.setText(medicalRecord.getDayCreated());
//        holder.tvNote_Status.setText("Doctor: " + medicalRecord.getDoctorName());
//    }
//
//    public void filter(String charText) {
//        arrayList = new ArrayList<>();
//        arrayList.addAll(medicalRecords);
//        charText = charText.toLowerCase(Locale.getDefault());
//
//        medicalRecords.clear();
//        if (charText.length() == 0) {
//            medicalRecords.addAll(arrayList);
//
//        } else {
//            for (MedicalRecord medicalRecord : arrayList) {
//                if (charText.length() != 0 && medicalRecord.getPatientName().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    medicalRecords.add(medicalRecord);
//                } else if (charText.length() != 0 && medicalRecord.getPatientCode().contains(charText)) {
//                    medicalRecords.add(medicalRecord);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemCount() {
//        return medicalRecords.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tvName)
//        TextView tvName;
//        @BindView(R.id.tvNote_Status)
//        TextView tvNote_Status;
//        @BindView(R.id.tvDateCreated)
//        TextView tvDateCreated;
//        @BindView(R.id.iv_cover)
//        ImageView ivCover;
//
//        public ViewHolder(final View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//
//            //// TODO: 28-Jul-17 : onItemClick
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int pos = getAdapterPosition();
//                    onClickItemView_startIntent(pos);
//                }
//            });
//        }
//    }
//
//    private void onClickItemView_startIntent(int position){
//        MedicalRecord medicalRecord = medicalRecords.get(position);
//        if(stateByRole == ROLE_STATE.DOCTOR){
//            Intent intent = new Intent(context, DiagnosisActivity.class);
//            intent.putExtra(PATIENT, medicalRecord);
//            context.startActivity(intent);
//        } else{
//            //// TODO: 28-Jul-17 : Diagnosis SHOW (read only)
//            Toast.makeText(context, medicalRecord.getDiseaseName(), Toast.LENGTH_SHORT).show();
//        }
//    }
//}
