package com.tvnsoftware.drcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.model.Remind;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Samn on 29-Jul-17.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private static final String TAG = AlarmAdapter.class.getSimpleName();

    private List<Remind> remindList;
    private Context context;

    private Listener listener;
    private boolean isOnBind;

    public interface Listener{
        void onUpdateRemind(int clickedPosition, Remind remindToUpdate);
        void onStatusAlarmChange(boolean isActivate, Remind remindToUpdate);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public AlarmAdapter(Context context) {
        this.remindList = new ArrayList<>();
        this.context = context;
    }

    public void setData(List<Remind> _remindList) {
        this.remindList.clear();
        remindList.addAll(_remindList);
        notifyDataSetChanged();
    }

    public void appendList(List<Remind> newRmind) {
        int nextPos = remindList.size();
        remindList.addAll(nextPos, newRmind );
        notifyItemRangeChanged(nextPos,newRmind.size());
    }

    public void appendIndividual(Remind newRmind) {
        int nextPos = remindList.size();
        remindList.add(nextPos, newRmind );
        notifyItemChanged(nextPos);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(context).inflate(R.layout.item_alarm, parent, false);
        return new AlarmAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.d(TAG, "TEST " + remindList.size() + " | ");
        Remind remind = remindList.get(position);
        holder.tvNoteAlarm.setText(remind.getRemindLabel());
        holder.tvTimeAlarm.setText(remind.getRemindTime());

        isOnBind = true;
        holder.switchIsActivatedAlarm.setChecked(remind.getIsActivate());

        isOnBind = false;
    }

    @Override
    public int getItemCount() {
        return remindList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        @BindView(R.id.tvTimeAlarm)
        TextView tvTimeAlarm;
        @BindView(R.id.tvTimeUnitAlarm)
        TextView tvTimeUnitAlarm;
        @BindView(R.id.tvNoteAlarm)
        TextView tvNoteAlarm;
        @BindView(R.id.switchIsActivatedAlarm)
        SwitchButton switchIsActivatedAlarm;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Remind current_remind = remindList.get(pos);
                    listener.onUpdateRemind(pos, current_remind);
                    Log.d("Alarm", "Test: click on!");
                }
            });

            switchIsActivatedAlarm.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isActivate) {
            if(!isOnBind){
                int pos = getAdapterPosition();
                Remind current_remind = remindList.get(pos);
                current_remind.setIsActivate(isActivate);
                notifyItemChanged(pos);
                listener.onStatusAlarmChange(isActivate, current_remind);
            }
        }
    }
}
