package com.zhjydy.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.data.DicData;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.entity.NormalItem;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class OrderMsgListAdapter extends ListViewAdapter<Map<String, Object>> {


    public OrderMsgListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_msg_order_layout);
    }

    @Override
    public void convert(ViewHolder holder, Map<String, Object> data) {
        ((TextView) holder.getView(R.id.time)).setText(DateUtil.getTimeDiffDayCurrent(Utils.toLong(data.get("addtime"))));
        String orderStatus = Utils.toString(data.get("orderstatus"));
        NormalItem status =  DicData.getInstance().getOrderStatuById(orderStatus);
        ((TextView) holder.getView(R.id.order_status)).setText(status.getName());
        ((TextView) holder.getView(R.id.content)).setText(Utils.toString(data.get("introduction")));
    }
}
