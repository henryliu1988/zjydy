package com.zhjydy.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class SimPleTextAdapter extends ListViewAdapter<NormalDicItem> {


    public SimPleTextAdapter(Context context, List<NormalDicItem> datas) {
        super(context, datas, R.layout.listview_simple_item);
    }

    @Override
    public void convert(ViewHolder holder, NormalDicItem normalDicItem) {
        ((TextView) holder.getView(R.id.text)).setText(normalDicItem.getName());
    }
}
