package com.zhjydy.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class MsgSystemListAdapter extends PageLoadListAdapter {


    public MsgSystemListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_msg_system_item_layout);
    }

    @Override
    public void convert(ViewHolder holder, Map<String, Object> data) {
        String title = Utils.toString(data.get("title"));
        String content = Utils.toString(data.get("content"));
        if (!TextUtils.isEmpty(Utils.toString(data.get("addtime")))) {
            String time = DateUtil.getTimeDiffDayCurrent(Utils.toLong(data.get("addtime")));
            ((TextView) holder.getView(R.id.msg_time)).setText(time);
        }
        ((TextView) holder.getView(R.id.msg_title)).setText(title);
        ((TextView) holder.getView(R.id.msg_content)).setText(content);
    }
}
