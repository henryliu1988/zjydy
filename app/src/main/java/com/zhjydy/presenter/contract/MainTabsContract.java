package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public interface MainTabsContract {
    interface View extends BaseView<Presenter> {
        void updateMsgCount(int count);

        void refreshOrderList();

    }

    interface Presenter extends BasePresenter {
    }

}
