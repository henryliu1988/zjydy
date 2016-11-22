package com.zhjydy.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ScoreView;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class MainExpertListAdapter extends ListViewAdapter<Map<String,Object>> {

    public MainExpertListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_main_expert_info_item);
    }

    @Override
    public void convert(ViewHolder holder, Map<String,Object> info) {
       // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        ((TextView) holder.getView(R.id.name)).setText(Utils.toString(info.get("realname")));
        ((TextView) holder.getView(R.id.depart)).setText(Utils.toString(info.get("office")));
        ((TextView) holder.getView(R.id.profession)).setText(Utils.toString(info.get("business")));
        ((TextView) holder.getView(R.id.hospital)).setText(Utils.toString(info.get("hospital")));

        ((TextView) holder.getView(R.id.special)).setText(Utils.toString(info.get("adept")));
        ((TextView) holder.getView(R.id.score)).setText("推荐分数：" );
        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
        ScoreView starView = (ScoreView)holder.getView(R.id.star);

        ImageUtils.getInstance().displayFromRemote(Utils.toString(info.get("path")),(ImageView)holder.getView(R.id.photo));
    }
}
