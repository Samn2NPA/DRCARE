package com.tvnsoftware.drcare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tvnsoftware.drcare.R;

/**
 * Created by Thieusike on 7/10/2017.
 */

public class CustomSpinerAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mStrDatas;

    @Override
    public int getCount() {
        return mStrDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return mStrDatas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomSpinerViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new CustomSpinerViewHolder();

            convertView = inflater.inflate(R.layout.item_custom_spiner, parent, false);

            viewHolder.mOptionCustom = (TextView) convertView.findViewById(R.id.option_custom_spiner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomSpinerViewHolder) convertView.getTag();
        }
        String optionItem = mStrDatas[position];
        if (optionItem != null) {
            viewHolder.mOptionCustom.setText(optionItem);
        }
        return convertView;
    }

    public CustomSpinerAdapter(Context context, String[] strings) {
        this.mContext = context;
        this.mStrDatas = strings;
    }

    public static class CustomSpinerViewHolder {
        private TextView mOptionCustom;
    }
}
