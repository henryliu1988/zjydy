package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.zhjydy.R;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.util.Log;
import com.zhjydy.util.Utils;
import com.zhjydy.util.alipay.PayResult;
import com.zhjydy.view.zhview.zhToast;

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

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
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
        params.put("outtradeno", mOrderId);
        params.put("money", "0.1");
        WebCall.getInstance().call(WebKey.func_ydypay, params).subscribe(new BaseSubscriber<WebResponse>(getContext(),"") {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getReturnData();
                Map<String, Object> resultData = Utils.parseObjectToMapString(webResponse.getReturnData());
                String orderData = Utils.toString(resultData.get("result"));
                tryAlipay(orderData);
            }
        });
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        zhToast.showToast("支付成功");
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            zhToast.showToast("支付结果确认中");


                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            zhToast.showToast("支付失败");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private void tryAlipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String,String> result = alipay.payV2(orderInfo,true);
                Log.i(this.getClass(), result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }
    private void onPayOk() {

    }
}
