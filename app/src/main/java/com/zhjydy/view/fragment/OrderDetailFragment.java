package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.data.DicData;
import com.zhjydy.presenter.contract.OrderDetailContract;
import com.zhjydy.presenter.presenterImp.OrderDetailPresenterImp;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.zhview.ViewUtil;
import com.zhjydy.view.zhview.zhToast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class OrderDetailFragment extends PageImpBaseFragment implements OrderDetailContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.patient_name)
    TextView patientName;
    @BindView(R.id.patient_hospital)
    TextView patientHospital;
    @BindView(R.id.patient_serialNum)
    TextView patientSerialNum;
    @BindView(R.id.patient_time)
    TextView patientTime;
    @BindView(R.id.doc_name)
    TextView docName;
    @BindView(R.id.doc_hospital)
    TextView docHospital;
    @BindView(R.id.doc_depart)
    TextView docDepart;
    @BindView(R.id.doc_profession)
    TextView docProfession;
    @BindView(R.id.dic_photo)
    ImageView dicPhoto;
    @BindView(R.id.expert_info_layout)
    LinearLayout expertInfoLayout;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.money_layout)
    RelativeLayout moneyLayout;
    protected OrderDetailContract.Presenter mPresenter;
    protected String mOrderId = "";
    @Override
    public void setPresenter(OrderDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_details;
    }

    @Override
    protected void afterViewCreate() {
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText("订单详情");
        String id = "";
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(IntentKey.FRAG_INFO))) {
            id = getArguments().getString(IntentKey.FRAG_INFO);
        }
        new OrderDetailPresenterImp(this, id);
        expertInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expertId = mPresenter.getExpertId();
                if (TextUtils.isEmpty(expertId)) {
                    zhToast.showToast("查找专家详情失败");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(IntentKey.FRAG_INFO, expertId);
                gotoFragment(FragKey.detail_expert_fragment, bundle);
            }
        });
        initOperateDetail();
    }

    public void initOperateDetail() {

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
        if (huiComment != null && huiComment.size() > 0 && !TextUtils.isEmpty(moneyValue)) {
            money.setText("￥" + moneyValue + "元");
            moneyLayout.setVisibility(View.VISIBLE);
        }
        if(status == 2) {
            titleCenterTv.setText("立即支付");
        }
    }

    @Override
    public void updateExpert(Map<String, Object> info) {
        String experturl = Utils.toString(info.get("path"));
        if (TextUtils.isEmpty(experturl)) {
            ImageUtils.getInstance().displayFromDrawableOver(R.mipmap.photo, dicPhoto);
        } else {
            ImageUtils.getInstance().displayFromRemoteOver(experturl, dicPhoto);
        }
        docName.setText(Utils.toString(info.get("realname")));
        String docHosId = Utils.toString(info.get("hospital"));
        String docOfficeId = Utils.toString(info.get("office"));
        String docBusId = Utils.toString(info.get("business"));

        docHospital.setText(DicData.getInstance().getHospitalById(docHosId).getHospital());
        docDepart.setText(Utils.toString(DicData.getInstance().getOfficeById(docOfficeId).getName()));
        docProfession.setText(Utils.toString(DicData.getInstance().getBusinessById(docBusId).getName()));
    }

    @Override
    public void updateSafeCom(Map<String, Object> safe) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
