package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/12 0012.
 */
public class OrderPayDetailFragment extends OrderDetailFragment {
    @BindView(R.id.money_all)
    TextView moneyAll;
    @BindView(R.id.pay_comfirm)
    TextView payComfirm;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.safe_info_layout)
    LinearLayout safeInfoLayout;
    @BindView(R.id.safe_money)
    TextView safeMoney;
    @BindView(R.id.safe_money_layout)
    RelativeLayout safeMoneyLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_pay_details;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void initOperateDetail() {
        payComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAlipay();
            }
        });
    }

    private void confirmAlipay() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("out_trade_no", mOrderId);
        params.put("money", "0.1");
        WebCall.getInstance().call(WebKey.func_ydypay, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getReturnData();
                Map<String, Object> rsesultData = Utils.parseObjectToMapString(webResponse.getReturnData());
                String orderData = Utils.toString(resultData.get("result"));


            }
        });
    }

    private void onPayOk() {

    }
}
