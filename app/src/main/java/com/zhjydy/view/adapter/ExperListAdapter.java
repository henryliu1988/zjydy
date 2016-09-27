package com.zhjydy.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class ExperListAdapter extends ListViewAdapter<DocTorInfo> {


    public ExperListAdapter(Context context, List<DocTorInfo> datas) {
        super(context, datas, R.layout.listview_expert_info_item);
    }

    @Override
    public void convert(ViewHolder holder, DocTorInfo info) {
       // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        ((TextView) holder.getView(R.id.name)).setText(info.getName());
        ((TextView) holder.getView(R.id.depart)).setText(info.getOffice());
        ((TextView) holder.getView(R.id.profession)).setText(info.getProfess());
        ((TextView) holder.getView(R.id.special)).setText(info.getSpecial());
        ((TextView) holder.getView(R.id.score)).setText(info.getScore());
        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
    }
}
