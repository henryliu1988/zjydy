package com.zhjydy.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.data.DicData;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ScoreView;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class MainExpertListAdapter extends PageLoadListAdapter
{
    public MainExpertListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_main_expert_info_item);
    }

    @Override
    public void convert(ViewHolder holder, Map<String,Object> info) {
       // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        ((TextView) holder.getView(R.id.name)).setText(Utils.toString(info.get("realname")));
        ((TextView) holder.getView(R.id.depart)).setText(DicData.getInstance().getOfficeById(Utils.toString(info.get("office"))).getName());
        ((TextView) holder.getView(R.id.profession)).setText(DicData.getInstance().getOfficeById(Utils.toString(info.get("business"))).getName());
        ((TextView) holder.getView(R.id.hospital)).setText(DicData.getInstance().getHospitalById(Utils.toString(info.get("hospital"))).getHospital());

        ((TextView) holder.getView(R.id.special)).setText(Utils.toString(info.get("adept")));
        ((TextView) holder.getView(R.id.score)).setText("推荐分数：" );
        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
        ScoreView starView = (ScoreView)holder.getView(R.id.star);
        int score = Utils.toInteger(info.get("stars"));
        if (score > 100) {
            score = 100;
        }
        if (score < 0) {
            score = 0;
        }
        starView.setScore(score,100);

        ImageUtils.getInstance().displayFromRemote(Utils.toString(info.get("path")),(ImageView)holder.getView(R.id.photo));
    }

}
