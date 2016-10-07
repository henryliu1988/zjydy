package com.zhjydy.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class MsgListAdapter extends ListViewAdapter<Map<String,Object>> {


    public MsgListAdapter(Context context, List<Map<String,Object>> datas) {
        super(context, datas, R.layout.listview_msg_item_layout);
    }

    @Override
    public void convert(ViewHolder holder, Map<String,Object> data) {
    }
}
