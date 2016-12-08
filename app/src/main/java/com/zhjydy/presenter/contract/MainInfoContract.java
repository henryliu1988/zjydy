package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MainInfoContract {

    interface MainInfoView extends BaseView<MainInfoPresenter>
    {
        void updateFavInfoCount(int count);

    }

    interface MainInfoPresenter extends BasePresenter
    {
    }
}
