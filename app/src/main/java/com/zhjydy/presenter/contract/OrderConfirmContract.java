package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public interface OrderConfirmContract {
    interface View extends BaseView<Presenter> {
        void updatePatient(Map<String, Object> info);

        void updateExpert(Map<String, Object> info);

        void subsribExpertResult(boolean result, String msg);

    }

    interface Presenter extends BasePresenter {
        void confirmSubmit();
    }

}
