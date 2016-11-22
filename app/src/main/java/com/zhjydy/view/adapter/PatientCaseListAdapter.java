package com.zhjydy.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class PatientCaseListAdapter extends ListViewAdapter<Map<String, Object>> {


    public PatientCaseListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_patient_case_item);
    }

    @Override
    public void convert(ViewHolder holder, final Map<String, Object> comment) {
        // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        String nameSexAge = Utils.toString(comment.get("name")) + " " + Utils.toString(comment.get("sex")) + " " + Utils.toString(comment.get("age"));
        String domain = Utils.toString(comment.get("domain")) + " " + Utils.toString(comment.get("hospital")) + " " + Utils.toString(comment.get("depart"));

        ((TextView) holder.getView(R.id.item_name_sex_age)).setText(nameSexAge);
        ((TextView) holder.getView(R.id.item_domain_hos_departs)).setText(domain);
        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
        View contentView = holder.getView(R.id.content);
        if (contentView != null) {
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.OnClick(comment);
                    }
                }
            });
        }
    }
    private OnClickListener mListener;
    public void setOnClickListener(OnClickListener listener) {
        this.mListener = listener;
    }
    public interface OnClickListener{
        public void OnClick(Map<String, Object> item);
    }
}
