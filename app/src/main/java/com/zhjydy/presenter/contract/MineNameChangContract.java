package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface MineNameChangContract {

    interface View extends BaseView<Presenter> {
        void submitResult(boolean result, String msg);
    }

    interface Presenter extends BasePresenter {
        void submitChangeConfirm(String name);
    }
}
