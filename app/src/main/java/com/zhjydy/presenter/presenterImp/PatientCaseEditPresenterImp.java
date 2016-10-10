package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.PatientCaseDetailContract;
import com.zhjydy.presenter.contract.PatientCaseEditContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PatientCaseEditPresenterImp implements PatientCaseEditContract.Presenter {

    private PatientCaseEditContract.View mView;

    public PatientCaseEditPresenterImp(PatientCaseEditContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadPatientCase();
    }


    private void loadPatientCase() {
    }

    @Override
    public void finish() {

    }


}
