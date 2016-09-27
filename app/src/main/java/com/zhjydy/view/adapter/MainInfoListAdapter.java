package com.zhjydy.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class MainInfoListAdapter extends ListViewAdapter<Infomation> {


    public MainInfoListAdapter(Context context, List<Infomation> datas) {
        super(context, datas, R.layout.listview_maininfo_item);
    }

    @Override
    public void convert(ViewHolder holder, Infomation info) {
       // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        ((TextView) holder.getView(R.id.title)).setText(info.getTitle());
        ((TextView) holder.getView(R.id.outline)).setText(info.getOutline());
        ((TextView) holder.getView(R.id.date)).setText(info.getData());
        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
    }
}
