package com.zhjydy.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.data.DicData;
import com.zhjydy.model.entity.NormalItem;
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

    private OperateListener mOperateListener;

    public void setOperateListener(OperateListener listener) {
        this.mOperateListener = listener;
    }
    public interface OperateListener{
        void onOperate(Map<String,Object> item,String operate);
    }
    @Override
    public void convert(ViewHolder holder, final Map<String, Object> map) {
        String url = Utils.toString(map.get("url"));
        NormalItem statusItem = DicData.getInstance().getOrderStatuById(Utils.toString(map.get("status")));
     //   ImageUtils.getInstance().displayFromRemote(url,(ImageView)holder.getView(R.id.photo));
        ( (TextView)holder.getView(R.id.doc_name)).setText(Utils.toString(map.get("expertname")));
        ( (TextView)holder.getView(R.id.status)).setText(statusItem.getName());
        ( (TextView)holder.getView(R.id.serialNum)).setText("预约单号：" +Utils.toString(map.get("orderid")));
        ( (TextView)holder.getView(R.id.time)).setText("预约时间：" +Utils.toString(map.get("showtime")));

        ( (TextView)holder.getView(R.id.patientName)).setText("患者：" +Utils.toString(map.get("patientname")));
        ( (TextView)holder.getView(R.id.patientHospital)).setText("患者所在医院：" +Utils.toString(map.get("patienthospital")));

        int status = Utils.toInteger(Utils.toString(map.get("status")));
        RelativeLayout operateLayout = (RelativeLayout)holder.getView(R.id.operate_layout);
        TextView operateTv = (TextView)holder.getView(R.id.operate);
        switch (status){
            case 1:
                operateLayout.setVisibility(View.VISIBLE);
                operateTv.setText("取消预约");
                operateTv.setBackgroundColor(Color.rgb(248,181,0));
                break;
            case 2:
                operateLayout.setVisibility(View.VISIBLE);
                operateTv.setText("查看详情");
                operateTv.setBackgroundColor(Color.rgb(82,126,250));
                break;

            case 3:
                operateLayout.setVisibility(View.VISIBLE);
                operateTv.setText("马上支付");
                operateTv.setBackgroundColor(Color.rgb(96,215,0));
                break;

            case 4:
                operateLayout.setVisibility(View.VISIBLE);
                operateTv.setText("查看详情");
                operateTv.setBackgroundColor(Color.rgb(255,37,0));
                break;
            case 5:
                operateLayout.setVisibility(View.GONE);
                break;
            case 6:
                operateLayout.setVisibility(View.VISIBLE);
                operateTv.setText("查看详情");
                operateTv.setBackgroundColor(Color.rgb(82,126,250));
                break;

            case 7:
                operateLayout.setVisibility(View.VISIBLE);
                operateTv.setText("查看详情");
                operateTv.setBackgroundColor(Color.rgb(82,126,250));
                break;

        }
        operateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOperateListener != null) {
                    TextView tv = (TextView)view;
                    mOperateListener.onOperate(map,tv.getText().toString());
                }
            }
        });

    }
}
