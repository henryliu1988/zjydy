package com.zhjydy.view.fragment;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.AccountSafeContract;
import com.zhjydy.presenter.contract.PatientCaseDetailContract;
import com.zhjydy.presenter.presenterImp.AccountSafePresenterImp;
import com.zhjydy.presenter.presenterImp.PatientCaseDetailPresenterImp;
import com.zhjydy.presenter.presenterImp.PatientCasePresenterImp;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class PatientCaseDetailFragment extends StatedFragment implements PatientCaseDetailContract.View {


    private PatientCaseDetailContract.Presenter mPresenter;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_case_info;
    }

    @Override
    protected void afterViewCreate() {
        new PatientCaseDetailPresenterImp(this);
    }

    @Override
    public void setPresenter(PatientCaseDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }
}
