package com.zhjydy.view.fragment;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.PatientCaseDetailContract;
import com.zhjydy.presenter.contract.PatientCaseEditContract;
import com.zhjydy.presenter.presenterImp.PatientCaseDetailPresenterImp;
import com.zhjydy.presenter.presenterImp.PatientCaseEditPresenterImp;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class PatientCaseEditFragment extends StatedFragment implements PatientCaseEditContract.View {


    private PatientCaseEditContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_case_new_edit;
    }

    @Override
    protected void afterViewCreate() {
        new PatientCaseEditPresenterImp(this);
    }

    @Override
    public void setPresenter(PatientCaseEditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }
}
