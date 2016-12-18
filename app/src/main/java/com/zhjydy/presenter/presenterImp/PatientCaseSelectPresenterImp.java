package com.zhjydy.presenter.presenterImp;

import com.alibaba.fastjson.JSONObject;
import com.zhjydy.model.data.PatientData;
import com.zhjydy.model.data.UserData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.presenter.contract.PatientCaseSelectContract;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PatientCaseSelectPresenterImp implements PatientCaseSelectContract.Presenter {

    private PatientCaseSelectContract.View mView;

    private String expertInfo = "";

    public PatientCaseSelectPresenterImp(PatientCaseSelectContract.View view, String expertInfo) {
        this.mView = view;
        this.expertInfo = expertInfo;
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


    private void submitConfirm() {

    }

    @Override
    public void finish() {

    }


    @Override
    public String getConfirmInfo(Map<String, Object> patientCase) {
        Map<String, Object> confirmInfo = new HashMap<>();
        Map<String, Object> expertInfoMap = Utils.parseObjectToMapString(expertInfo);
        confirmInfo.put("expert", expertInfoMap);
        confirmInfo.put("patient", patientCase);
        return JSONObject.toJSONString(confirmInfo);
    }
}
