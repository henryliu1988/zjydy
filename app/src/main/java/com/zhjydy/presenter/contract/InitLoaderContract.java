package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

/**
 * Created by Administrator on 2016/12/17 0017.
 */
public interface InitLoaderContract {
    interface View extends BaseView<Presenter> {
        void gotoMainTabs();
    }

    interface Presenter extends BasePresenter {
    }
}
