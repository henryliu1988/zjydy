package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.AccountSafeContract;
import com.zhjydy.presenter.contract.PatientCaseContract;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PatientCasePresenterImp implements PatientCaseContract.Presenter {

    private PatientCaseContract.View mView;

    public PatientCasePresenterImp(PatientCaseContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadPatientCases();
    }


    private void loadPatientCases() {
        mView.updatePatient(new ArrayList<Map<String, Object>>());
    }

    @Override
    public void finish() {

    }


}
