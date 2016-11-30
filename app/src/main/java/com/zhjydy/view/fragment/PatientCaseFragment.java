package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.PatientCaseContract;
import com.zhjydy.presenter.presenterImp.PatientCasePresenterImp;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.PatientCaseListAdapter;
import com.zhjydy.view.zhview.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class PatientCaseFragment extends PageImpBaseFragment implements PatientCaseContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.m_case_list)
    ListViewForScrollView mCaseList;
    @BindView(R.id.m_case_add_button)
    TextView mCaseAddButton;
    @BindView(R.id.empty_patient_tv)
    TextView emptyPatientTv;
    private PatientCaseContract.Presenter mPresenter;

    private PatientCaseListAdapter mPatientCaseListAdapter;

    @Override
    protected void initData() {
        titleCenterTv.setText("患者病例");
        mPatientCaseListAdapter = new PatientCaseListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        mCaseList.setAdapter(mPatientCaseListAdapter);
        mPatientCaseListAdapter.setOnClickListener(new PatientCaseListAdapter.OnClickListener() {
            @Override
            public void OnClick(Map<String, Object> item) {
                Bundle bundle = new Bundle();
                bundle.putString("id", Utils.toString(item.get("id")));
                gotoFragment(FragKey.patient_case_detail_fragment, bundle);

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_patient_case_layout;
    }

    @Override
    protected void afterViewCreate() {
        new PatientCasePresenterImp(this);
    }

    @Override
    public void setPresenter(PatientCaseContract.Presenter presenter) {
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

    private void addNewPatienteCase() {
        gotoFragment(FragKey.patient_case_edit_fragment);
    }

    @OnClick({R.id.title_back, R.id.m_case_add_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
            case R.id.m_case_add_button:
                addNewPatienteCase();
                break;
        }
    }

    @Override
    public void updatePatient(List<Map<String, Object>> list) {
        if (list.size() >0) {
            emptyPatientTv.setVisibility(View.GONE);
            mCaseList.setVisibility(View.VISIBLE);
        } else {
            emptyPatientTv.setVisibility(View.VISIBLE);
            mCaseList.setVisibility(View.GONE);
        }
        mPatientCaseListAdapter.refreshData(list);
    }
}
