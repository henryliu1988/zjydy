package com.zhjydy.model.data;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.util.Utils;

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
        params.put("id", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_getPatientList, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                mAllPatient = webResponse;
            }
        });
    }

    public Observable<List<Map<String, Object>>> getAllPatientList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", AppData.getInstance().getToken().getId());
        return WebCall.getInstance().callCache(WebKey.func_getPatientList, params, mAllPatient).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                mAllPatient = webResponse;
                return Utils.parseObjectToListMapString(webResponse.getData());
            }
        });
    }

}
