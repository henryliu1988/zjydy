package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.PatientData;
import com.zhjydy.model.data.UserData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.presenter.contract.PatientCaseContract;

import java.util.HashMap;
import java.util.List;
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
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", UserData.getInstance().getToken().getId());
        PatientData.getInstance().getAllPatientList().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> list) {
                mView.updatePatient(list);
            }
        });
    }

    @Override
    public void finish() {

    }


}
