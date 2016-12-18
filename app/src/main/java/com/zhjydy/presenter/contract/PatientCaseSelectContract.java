package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public interface PatientCaseSelectContract {
    interface View extends BaseView<Presenter> {
        public void updatePatient(List<Map<String, Object>> list);
    }

    interface Presenter extends BasePresenter {
        public String getConfirmInfo(Map<String, Object> patientCase);
    }

}
