package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface PatientCaseContract {

    interface View extends BaseView<Presenter>
    {
        void updatePatient(List<Map<String,Object>> list);
    }

    interface Presenter extends BasePresenter
    {
    }
}
