package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public interface OrderCancelContract {
    interface View extends BaseView<Presenter> {

        void cancelResult(boolean result);
    }

    interface Presenter extends BasePresenter {
        void confirmCancel(String reason,String comment);
    }

}
