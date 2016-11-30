package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface IdentityInfoNewContract {

    interface View extends BaseView<Presenter>
    {
        void onSubmitSuccess();
    }

    interface Presenter extends BasePresenter
    {
        void submitIdentifymsg(Map<Integer,String> urls);
    }
}
