package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.PatientCaseContract;
import com.zhjydy.presenter.contract.PatientCaseDetailContract;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PatientCaseDetailPresenterImp implements PatientCaseDetailContract.Presenter {

    private PatientCaseDetailContract.View mView;

    public PatientCaseDetailPresenterImp(PatientCaseDetailContract.View view) {
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
