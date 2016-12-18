package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.data.DicData;
import com.zhjydy.model.entity.District;
import com.zhjydy.presenter.contract.OrderConfirmContract;
import com.zhjydy.presenter.presenterImp.OrderConfirmPresenterImp;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.Utils;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.zhview.zhToast;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public class OrderConfirmFragment extends PageImpBaseFragment implements OrderConfirmContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.patient_sex_age)
    TextView patientSexAge;
    @BindView(R.id.patient_domain_hos_departs)
    TextView patientDomainHosDeparts;
    @BindView(R.id.expert_name)
    TextView expertName;
    @BindView(R.id.expert_domain_hos_departs)
    TextView expertDomainHosDeparts;
    @BindView(R.id.confirm)
    TextView confirm;
    private OrderConfirmContract.Presenter mPresener;

    @Override
    public void setPresenter(OrderConfirmContract.Presenter presenter) {
        mPresener = presenter;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_confirm;
    }

    @Override
    protected void afterViewCreate() {
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText("确认信息");
        if (getArguments() == null) {
            back();
            return;
        }
        String info = getArguments().getString(IntentKey.FRAG_INFO);
        new OrderConfirmPresenterImp(this, info);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresener.confirmSubmit();
            }
        });
    }

    @Override
    public void refreshView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void updatePatient(Map<String, Object> info) {
        String realName = Utils.toString(info.get("realname"));
        String sexName = DicData.getInstance().getSexById(Utils.toString(info.get("sex"))).getName();
        String sec = Utils.toString(info.get("age"));
        long ageLong = DateUtil.getYearDiffBySeconds(Utils.toLong(info.get("age")));
        String age = "";
        if (ageLong > 0) {
            age = ageLong + "岁";
        }
        String nameSexAge = realName + " " + sexName + " " + age;

        String distrcit = "";
        String hospital = "";
        String depart = "";
        String disCode = Utils.toString(info.get("address"));
        String hosCode = Utils.toString(info.get("hospital"));
        String depCode = Utils.toString(info.get("office"));

        if (!TextUtils.isEmpty(disCode)) {
            List<District> list = DicData.getInstance().getDistrictById(disCode);
            if (list.size() > 0) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    distrcit += list.get(i).getName() + " ";
                }
            }
        }
        if (!TextUtils.isEmpty(hosCode)) {
            hospital = DicData.getInstance().getHospitalById(hosCode).getHospital();
        }
        if (!TextUtils.isEmpty(depCode)) {
            depart = DicData.getInstance().getOfficeById(depCode).getName();
        }
        String domain = distrcit + " " + hospital + " " + depart;

        patientSexAge.setText(nameSexAge);
        patientDomainHosDeparts.setText(domain);
    }

    @Override
    public void updateExpert(Map<String, Object> info) {
        expertName.setText(Utils.toString(info.get("realname")));
        String hos = DicData.getInstance().getHospitalById(Utils.toString(info.get("hospital"))).getHospital();
        String office = DicData.getInstance().getOfficeById(Utils.toString(info.get("office"))).getName();
        String business = DicData.getInstance().getOfficeById(Utils.toString(info.get("business"))).getName();

        String msg = hos + "  " + office + "  " + business;
        expertDomainHosDeparts.setText(msg);
    }

    @Override
    public void subsribExpertResult(boolean result, String msg) {
        zhToast.showToast(msg);
        if (result) {
            int[] refreshFrag = {FragKey.detail_expert_fragment};
            back(2, refreshFrag);
        }
    }
}
