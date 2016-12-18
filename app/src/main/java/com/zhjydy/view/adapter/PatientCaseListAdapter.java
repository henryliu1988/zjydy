package com.zhjydy.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.data.DicData;
import com.zhjydy.model.entity.District;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class PatientCaseListAdapter extends ListViewAdapter<Map<String, Object>> {


    private Map<String, Object> mSelectItem;
    private boolean canSelect = false;

    public void setIsSelect(boolean canSelect) {
        canSelect = canSelect;
    }

    public PatientCaseListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_patient_case_item);
    }


    @Override
    public void convert(ViewHolder holder, final Map<String, Object> comment) {
        // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        String realName = Utils.toString(comment.get("realname"));
        String sexName = DicData.getInstance().getSexById(Utils.toString(comment.get("sex"))).getName();
        String sec = Utils.toString(comment.get("age"));
        long ageLong = DateUtil.getYearDiffBySeconds(Utils.toLong(comment.get("age"))) + 1;
        String age = "";
        if (ageLong >= 0) {
            age = ageLong + "岁";
        }
        String nameSexAge = realName + " " + sexName + " " + age;

        String distrcit = "";
        String hospital = "";
        String depart = "";
        String disCode = Utils.toString(comment.get("address"));
        String hosCode = Utils.toString(comment.get("hospital"));
        String depCode = Utils.toString(comment.get("office"));

        if (!TextUtils.isEmpty(disCode)) {
            List<District> list = DicData.getInstance().getDistrictById1(disCode);
            if (list.size() > 0) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    distrcit += list.get(i).getName() + " ";
                }
            }
        }
        if (!TextUtils.isEmpty(hosCode)) {
            hospital = DicData.getInstance().getHospitalById(hosCode).getHospital();
        }
        if (!TextUtils.isEmpty(depCode)) {
            depart = DicData.getInstance().getOfficeById(depCode).getName();
        }
        String domain = distrcit + " " + hospital + " " + depart;

        if (canSelect) {
            holder.getView(R.id.item_check).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.item_check).setVisibility(View.GONE);
        }
        ((TextView) holder.getView(R.id.item_name_sex_age)).setText(nameSexAge);
        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
        ((TextView) holder.getView(R.id.item_domain_hos_departs)).setText(domain);
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

    public interface OnClickListener {
        public void OnClick(Map<String, Object> item);
    }
}
