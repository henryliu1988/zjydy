package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public interface OrderMsgListContract {

    interface View extends BaseView<Presenter> {
        void updateOrderList(List<Map<String, Object>> list);
    }

    interface Presenter extends BasePresenter {
        void reLoadData();

        void readOrder(String id);
    }
}
