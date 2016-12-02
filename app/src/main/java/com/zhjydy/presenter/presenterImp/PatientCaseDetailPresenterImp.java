package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.PatientCaseContract;
import com.zhjydy.presenter.contract.PatientCaseDetailContract;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PatientCaseDetailPresenterImp implements PatientCaseDetailContract.Presenter {

    private PatientCaseDetailContract.View mView;

    private String mCaseId;
    public PatientCaseDetailPresenterImp(PatientCaseDetailContract.View view,String id) {
        this.mView = view;
        view.setPresenter(this);
        this.mCaseId = id;
        start();
    }

    @Override
    public void start() {
        loadPatientCase();
    }


    private void loadPatientCase() {
        if(TextUtils.isEmpty(mCaseId)) {
            return;
        }
        HashMap<String,Object> params = new HashMap<>();
        params.put("id",mCaseId);
        WebCall.getInstance().call(WebKey.func_getPatientById,params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),true) {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                Map<String,Object> item = Utils.parseObjectToMapString(data);
                mView.updateInfo(item);
            }
        });
    }

    @Override
    public void finish() {

    }


    @Override
    public void refreshData()
    {
        loadPatientCase();
    }
}
