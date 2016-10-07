package com.zhjydy.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class OrderListAdapter extends  ListViewAdapter<Map<String,Object>> {

    public OrderListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.order_list_item);
    }
    public void setData( List<Map<String, Object>> datas) {
        super.refreshData(datas);
    }
    @Override
    public void convert(ViewHolder holder, Map<String, Object> map) {
        String url = Utils.toString(map.get("url"));
        ImageUtils.getInstance().displayFromRemote(url,(ImageView)holder.getView(R.id.photo));
        ( (TextView)holder.getView(R.id.doc_name)).setText(Utils.toString(map.get("docName")));
        ( (TextView)holder.getView(R.id.status)).setText(Utils.toString(map.get("status")));
        ( (TextView)holder.getView(R.id.serialNum)).setText("预约单号：" +Utils.toString(map.get("serialNum")));
        ( (TextView)holder.getView(R.id.time)).setText("预约时间：" +Utils.toString(map.get("time")));

        ( (TextView)holder.getView(R.id.patientName)).setText("患者：" +Utils.toString(map.get("patientName")));
        ( (TextView)holder.getView(R.id.patientHospital)).setText("患者所在医院：" +Utils.toString(map.get("patientHospital")));
    }
}
