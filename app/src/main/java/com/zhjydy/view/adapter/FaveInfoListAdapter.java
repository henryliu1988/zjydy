package com.zhjydy.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class FaveInfoListAdapter extends ListViewAdapter<Map<String,Object>> {


    public FaveInfoListAdapter(Context context, List<Map<String,Object>> datas) {
        super(context, datas, R.layout.listview_fave_info_item);
    }

    @Override
    public void convert(ViewHolder holder, Map<String,Object> info) {
       // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        ((TextView) holder.getView(R.id.title)).setText(Utils.toString(info.get("title")));
        ((TextView) holder.getView(R.id.outline)).setText(Utils.toString(info.get("introduction")));
        ((TextView) holder.getView(R.id.date)).setText(Utils.toString(info.get("time")));
        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
    }
}
