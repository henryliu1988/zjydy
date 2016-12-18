package com.zhjydy.presenter.presenterImp;

import android.content.Context;

import com.zhjydy.model.data.PatientData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.FileUpLoad;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.PatientCaseEditAttachContract;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class PatientCaseEditAttachPresenterImp implements PatientCaseEditAttachContract.Presenter {

    private PatientCaseEditAttachContract.View mView;

    public PatientCaseEditAttachPresenterImp(PatientCaseEditAttachContract.View view) {
        mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }

    @Override
    public void submitMsg(final HashMap<String, Object> params, List<Map<String, Object>> files, Context context, int type) {

        if (type == 1) {
            FileUpLoad.uploadFiles(files).flatMap(new Func1<List<Map<String, Object>>, Observable<WebResponse>>() {
                @Override
                public Observable<WebResponse> call(List<Map<String, Object>> files) {
                    params.put("case", Utils.getListStrsAdd(files, "id"));
                    return WebCall.getInstance().call(WebKey.func_updatePatient, params);
                }
            }).subscribe(new BaseSubscriber<WebResponse>(context, "正在保存数据") {
                @Override
                public void onNext(WebResponse webResponse) {
                    PatientData.getInstance().loadPatientData();
                    mView.sumbitOk();
                }
            });
        } else {
            FileUpLoad.uploadFiles(files).flatMap(new Func1<List<Map<String, Object>>, Observable<WebResponse>>() {
                @Override
                public Observable<WebResponse> call(List<Map<String, Object>> files) {
                    params.put("case", Utils.getListStrsAdd(files, "id"));
                    return WebCall.getInstance().call(WebKey.func_addPatient, params);
                }
            }).subscribe(new BaseSubscriber<WebResponse>(context, "正在保存数据") {
                @Override
                public void onNext(WebResponse webResponse) {
                    PatientData.getInstance().loadPatientData();
                    mView.sumbitOk();
                }
            });
        }

    }


}
