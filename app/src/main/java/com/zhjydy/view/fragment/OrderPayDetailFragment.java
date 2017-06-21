package com.zhjydy.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.zhjydy.R;
import com.zhjydy.model.data.DicData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.Log;
import com.zhjydy.util.Utils;
import com.zhjydy.util.alipay.PayResult;
import com.zhjydy.view.zhview.ViewUtil;
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
    TextView safeMoneyTv;
    @BindView(R.id.safe_money_layout)
    RelativeLayout safeMoneyLayout;
    @BindView(R.id.safe_info_name)
    TextView safeInfoName;
    @BindView(R.id.safe_info_companu)
    TextView safeInfoCompanu;
    @BindView(R.id.safe_info_check)
    CheckBox safeInfoCheck;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private double orderMoney = 0.0;
    private double safeMoney = 0.0;
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
    public void updateOrder(Map<String, Object> info) {
        int status = Utils.toInteger(info.get("status"));
        patientName.setText("患者：" + Utils.toString(info.get("patientname")));
        patientHospital.setText("患者所在医院：" + DicData.getInstance().getHospitalById(Utils.toString(info.get("patienthospital"))).getHospital());

        patientSerialNum.setText("预约单号：" + Utils.toString(info.get("orderid")));
        patientTime.setText("预约时间：" + DateUtil.getFullTimeDiffDayCurrent(Utils.toLong(info.get("showtime"))));
        mOrderId = Utils.toString(info.get("orderid"));
        String statuText = "";
        String textColorBg = "#FFFFFF";
        switch (status) {
            case 1:
                statuText = "预约中，等待专家确认预约";
                textColorBg = "#FFB81F";
                break;
            case 2:
                statuText = "专家已经确认";
                textColorBg = "#FFB81F";
                break;
            case 3:
                statuText = "订单已支付，进行中";
                textColorBg = "#FFB81F";
                break;
            case 4:
                statuText = "退款中";
                textColorBg = "#FFB81F";
                break;
            case 5:
                statuText = "订单已完成";
                textColorBg = "#FFB81F";
                break;
            case 6:
                statuText = "患者取消预约，订单已结束";
                textColorBg = "#FFB81F";
                break;
            case 7:
                statuText = "专家未接受预约，订单已结束";
                textColorBg = "#FFB81F";
                break;

            case 9:
                statuText = "退款失败，请重新申请";
                textColorBg = "#FFB81F";
                break;

            case 10:
                statuText = "退款成功，订单已结束";
                textColorBg = "#FFB81F";
                break;

            case 11:
                statuText = "会诊中";
                textColorBg = "#FFB81F";
                break;

            case 12:
                statuText = "治疗中";
                textColorBg = "#FFB81F";
                break;

        }
        this.status.setText(statuText);
        ViewUtil.setCornerViewDrawbleBg(this.status, textColorBg);
        moneyLayout.setVisibility(View.GONE);
        Map<String, Object> huiComment = Utils.parseObjectToMapString(info.get("hui_comment"));
        String moneyValue = Utils.toString(huiComment.get("money"));
        orderMoney = Utils.toDouble(moneyValue);

        if (huiComment != null && huiComment.size() > 0 && !TextUtils.isEmpty(moneyValue)) {
            money.setText("￥" + moneyValue + "元");
            moneyLayout.setVisibility(View.VISIBLE);
            moneyAll.setText("￥" + moneyValue + "元");
            moneyAll.setTag(orderMoney);
        }
        if(status == 2) {
            titleCenterTv.setText("立即支付");
            bottomLayout.setVisibility(View.VISIBLE);
            safeInfoLayout.setVisibility(View.VISIBLE);
        } else {
            titleCenterTv.setText("订单详情");
            bottomLayout.setVisibility(View.INVISIBLE);
            safeInfoLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void updateSafeCom(final Map<String, Object> safe) {
        safeInfoLayout.setVisibility(View.VISIBLE);
        String company = Utils.toString(safe.get("company"));
        String money = Utils.toString(safe.get("money"));
        String name = Utils.toString(safe.get("name"));
        safeInfoName.setText(name);
        safeInfoCompanu.setText("由" + company + "提供保障");
        safeMoneyTv.setText("￥" + money + "元");
        safeMoney = Utils.toDouble(money);
        payComfirm.setClickable(false);
        payComfirm.setBackgroundColor(Color.rgb(192, 192, 192));
        safeInfoCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    payComfirm.setClickable(true);
                    payComfirm.setBackgroundColor(Color.parseColor("#60D700"));
                    double all = orderMoney + safeMoney;
                    moneyAll.setText("￥" + all + "元");
                    moneyAll.setTag(all);
                } else {
                    payComfirm.setClickable(false);
                    payComfirm.setBackgroundColor(Color.rgb(192, 192, 192));
                    moneyAll.setText("￥" + orderMoney + "元");
                    moneyAll.setTag(orderMoney);

                }
            }
        });
    }

    @Override
    public void initOperateDetail() {
        payComfirm.setClickable(false);
        payComfirm.setBackgroundColor(Color.rgb(192, 192, 192));
        payComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAlipay();
            }
        });
    }

    private void confirmAlipay() {
        String money = Utils.toString(moneyAll.getTag());
        HashMap<String, Object> params = new HashMap<>();
        params.put("outtradeno", mOrderId);
        params.put("money", 0.01);
        WebCall.getInstance().call(WebKey.func_ydypay, params).subscribe(new BaseSubscriber<WebResponse>(getContext(), "") {
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
                    onPayResult(resultStatus);
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private void tryAlipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
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

    private void onPayResult(String resultStatus) {
        String status = "0";
        if (TextUtils.equals(resultStatus, "9000")) {
            zhToast.showToast("支付成功");
            status = "1";
        } else {
            // 判断resultStatus 为非"9000"则代表可能支付失败
            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
            if (TextUtils.equals(resultStatus, "8000")) {
                zhToast.showToast("支付结果确认中");
                status = "0";
            } else {
                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                zhToast.showToast("支付失败");
                status = "2";
            }
        }
        String money = moneyAll.getTag().toString();
        mPresenter.onPayResult(mOrderId,money,status);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
