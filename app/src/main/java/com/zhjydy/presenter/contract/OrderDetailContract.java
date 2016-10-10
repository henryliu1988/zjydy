package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface OrderDetailContract {

    interface View extends BaseView<Presenter>
    {
        void update(Map<String, Object> info);
    }

    interface Presenter extends BasePresenter
    {
    }
}
