package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.AccountSafeContract;
import com.zhjydy.presenter.contract.PatientCaseContract;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

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

        HashMap<String,Object> params = new HashMap<>();
        params.put("id","2");
        WebCall.getInstance().call(WebKey.func_getPatientList,params).map(new Func1<WebResponse, List<Map<String,Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String,Object>> list = Utils.parseObjectToListMapString(data);
                return  list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> list) {
                mView.updatePatient(list);
            }
        });
        /*
        List<Map<String,Object>> list =new ArrayList<>();
        Map<String,Object> data = new HashMap<>();
        data.put("name","张三");
        data.put("name","张三");data.put("sex","男");data.put("age","49");data.put("domain","山東省青岛市市北区");data.put("hospital","青岛人民医院");data.put("depart","内分泌科");
        list.add(data);
     */
    }

    @Override
    public void finish() {

    }


}
