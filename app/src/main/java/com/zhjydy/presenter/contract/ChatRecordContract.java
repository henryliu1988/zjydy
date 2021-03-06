package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface ChatRecordContract {

    interface View extends BaseView<Presenter> {
        void setChatMsgs(List<Map<String, Object>> msg, String expertPhoto);

        void updateExpertName(String name);
    }

    interface Presenter extends BasePresenter {
    }
}
