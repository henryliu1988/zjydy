package com.zhjydy.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class PatientCaseListAdapter extends ListViewAdapter<Map<String, Object>> {


    public PatientCaseListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_patient_case_item);
    }

    @Override
    public void convert(ViewHolder holder, Map<String, Object> comment) {
        // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        ((TextView) holder.getView(R.id.item_name_sex_age)).setText(Utils.toString(comment.get("name")));
        ((TextView) holder.getView(R.id.item_domain_hos_departs)).setText(Utils.toString(comment.get("content")));
        ((TextView) holder.getView(R.id.time)).setText(Utils.toString(comment.get("time")));
        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
    }
}
