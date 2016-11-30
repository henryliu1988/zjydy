package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface PatientCaseDetailContract {

    interface View extends BaseView<Presenter>
    {
        void updateInfo(Map<String,Object> info);
    }

    interface Presenter extends BasePresenter
    {
    }
}
