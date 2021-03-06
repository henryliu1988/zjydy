package com.zhjydy.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.data.DicData;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ViewHolder;
import com.zhjydy.view.zhview.ViewUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class OrderListAdapter extends ListViewAdapter<Map<String, Object>> {


    public static final int OPERATE_DETAIL = 0;
    public static final int OPERATE_CANCEL = 1;
    public static final int OPERATE_PAY = 2;
    public static final int OPERATE_BACK = 3;

    public OrderListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.order_list_item);
    }

    public void setData(List<Map<String, Object>> datas) {
        super.refreshData(datas);
    }

    private OperateListener mOperateListener;

    public void setOperateListener(OperateListener listener) {
        this.mOperateListener = listener;
    }

    public interface OperateListener {
        void onOperate(Map<String, Object> item, int operate);
    }

    @Override
    public void convert(ViewHolder holder, final Map<String, Object> map) {
        String photoUrl = Utils.toString(map.get("experturl"));
        if (!TextUtils.isEmpty(photoUrl)) {
            ImageUtils.getInstance().displayFromRemoteOver(photoUrl, (ImageView) holder.getView(R.id.photo));
        }
        ((TextView) holder.getView(R.id.doc_name)).setText(Utils.toString(map.get("expertname")));
        ((TextView) holder.getView(R.id.serialNum)).setText("预约单号：" + Utils.toString(map.get("orderid")));
        ((TextView) holder.getView(R.id.time)).setText("预约时间：" + DateUtil.getFullTimeDiffDayCurrent(Utils.toLong(map.get("showtime"))));

        ((TextView) holder.getView(R.id.patientName)).setText("患者：" + Utils.toString(map.get("patientname")));
        ((TextView) holder.getView(R.id.patientHospital)).setText("患者所在医院：" + DicData.getInstance().getHospitalById(Utils.toString(map.get("patienthospital"))).getHospital());

        int status = Utils.toInteger(Utils.toString(map.get("status")));
        RelativeLayout operateLayout = (RelativeLayout) holder.getView(R.id.operate_layout);
        TextView operateTv = (TextView) holder.getView(R.id.operate);
        TextView statusTv = (TextView) holder.getView(R.id.status);
        boolean isOperateVisible = true;
        String operateText = "查看详情";
        String backGroudColor = "#527EFA";
        String statusColor = "#383838";
        String statusText = "";
        int operateType = 0;
        switch (status) {
            case 1: //预约中，操作为取消预约
                isOperateVisible = true;
                operateText = "取消预约";
                backGroudColor = "#F8B500";
                statusColor = "#F8B500";
                statusText = "预约中";
                operateType = OPERATE_CANCEL;
                break;
            case 2:  //专家确认状态，可以马上支付
                isOperateVisible = true;
                operateText = "马上支付";
                backGroudColor = "#60D700";
                statusColor = "#60D700";
                statusText = "待支付";
                operateType = OPERATE_PAY;
                break;

            case 3:
                isOperateVisible = true;
                operateText = "退款";
                backGroudColor = "#F8B500";
                statusColor = "#F8B500";
                statusText = "支付成功";
                operateType = OPERATE_BACK;
                break;

            case 11: //
                isOperateVisible = false;
                statusColor = "#527EFA";
                statusText = "会诊中";
                operateType = OPERATE_DETAIL;

                break;

            case 4:
                isOperateVisible = true;
                backGroudColor = "#FF2500";
                statusColor = "#FF2500";
                statusText = "退款中";
                operateType = OPERATE_DETAIL;

                break;
            case 5:
                isOperateVisible = false;
                statusColor = "#6C00BF";
                statusText = "已完成";
                operateType = OPERATE_DETAIL;

                break;
            case 6:
                statusText = "预约取消";
                operateType = OPERATE_DETAIL;
                break;
            case 7:
                statusText = "预约取消";
                operateType = OPERATE_DETAIL;
                break;
            case 9:
                isOperateVisible = false;
                statusColor = "#383838";
                statusText = "订单退款失败";
                break;
            case 10:
                isOperateVisible = false;
                statusColor = "#60D700";
                statusText = "订单退款成功";
                break;
            case 12:
                isOperateVisible = false;
                statusColor = "#527EFA";
                statusText = "治疗中";
                break;
        }
        statusTv.setText(statusText);
        statusTv.setTextColor(Color.parseColor(statusColor));
        if (!isOperateVisible) {
            operateLayout.setVisibility(View.GONE);
        } else {
            operateLayout.setVisibility(View.VISIBLE);
            operateTv.setText(operateText);
            ViewUtil.setCornerViewDrawbleBg(operateTv, backGroudColor);
        }
        operateTv.setTag(operateType);

        operateTv.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {
                                if (mOperateListener != null) {
                                      mOperateListener.onOperate(map, Utils.toInteger(view.getTag()));
                                   }
                          }
        });

    }
}
