package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.PatientCaseSelectContract;
import com.zhjydy.presenter.presenterImp.PatientCaseSelectPresenterImp;
import com.zhjydy.view.adapter.PatientCaseSelectListAdapter;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.zhview.ListViewForScrollView;
import com.zhjydy.view.zhview.zhToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class PatientCaseSelectFragment extends PageImpBaseFragment implements PatientCaseSelectContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.m_case_list)
    ListViewForScrollView mCaseList;
    @BindView(R.id.empty_patient_tv)
    TextView emptyPatientTv;
    @BindView(R.id.m_case_select_confirm)
    TextView mCaseSelectConfirm;
    private PatientCaseSelectContract.Presenter mPresenter;

    private PatientCaseSelectListAdapter mPatientCaseListAdapter;

    @Override
    protected void initData() {
        titleCenterTv.setText("患者病例");
        mPatientCaseListAdapter = new PatientCaseSelectListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        mCaseList.setAdapter(mPatientCaseListAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_case_select_layout;
    }

    @Override
    protected void afterViewCreate() {
        if (getArguments() == null) {
            back();
            return;
        }
        String expertInfo = getArguments().getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(expertInfo)) {
            back();
            return;
        }
        new PatientCaseSelectPresenterImp(this, expertInfo);
    }

    @Override
    public void setPresenter(PatientCaseSelectContract.Presenter presenter) {
        mPresenter = presenter;
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


    @OnClick({R.id.title_back, R.id.m_case_select_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
            case R.id.m_case_select_confirm:
                confirmSelect();
                break;
        }
    }

    private void confirmSelect() {
        Map<String, Object> patientCase = mPatientCaseListAdapter.getSelectItem();
        if (patientCase == null) {
            zhToast.showToast("请选择患者病例！");
            return;
        }
        String confirmInfo = mPresenter.getConfirmInfo(patientCase);
        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.FRAG_INFO, confirmInfo);
        gotoFragment(FragKey.order_confirm_fragment, bundle);

    }

    @Override
    public void updatePatient(List<Map<String, Object>> list) {
        if (list.size() > 0) {
            emptyPatientTv.setVisibility(View.GONE);
            mCaseList.setVisibility(View.VISIBLE);
        } else {
            emptyPatientTv.setVisibility(View.VISIBLE);
            mCaseList.setVisibility(View.GONE);
        }
        mPatientCaseListAdapter.refreshData(list);
    }
}
