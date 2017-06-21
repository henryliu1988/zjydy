package com.zhjydy.presenter.contract;

import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public interface OrderBackContract {
    interface View extends BaseView<Presenter> {
        void updateOrder(Map<String,Object> orderinfo);
        void backResult(boolean result);
    }

    interface Presenter extends BasePresenter {
        void confirmBack(String reason, String comment);
    }

}
