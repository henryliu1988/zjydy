package com.zhjydy.model.data;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class PatientData {


    private WebResponse mAllPatient;
    private static PatientData mInstance = new PatientData();

    public PatientData() {

    }

    public static PatientData getInstance() {
        return mInstance;
    }

    public void loadPatientData() {
        mAllPatient = null;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_getPatientList, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                mAllPatient = webResponse;
                RefreshManager.getInstance().refreshData(RefreshKey.PATIENT_CASE_LIST_CHANGE);
            }
        });
    }

    public Observable<List<Map<String, Object>>> getAllPatientList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", UserData.getInstance().getToken().getId());
        return WebCall.getInstance().callCache(WebKey.func_getPatientList, params, mAllPatient).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    mAllPatient = webResponse;
                    return Utils.parseObjectToListMapString(webResponse.getData());
                } else {
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    return list;
                }
            }
        });
    }

}
