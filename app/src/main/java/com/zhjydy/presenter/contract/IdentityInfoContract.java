package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface IdentityInfoContract {

    interface View extends BaseView<Presenter>
    {
        void updateIdentifyInfo(int status,List<String> path);
    }

    interface Presenter extends BasePresenter
    {
        void uploadImageFiles(List<String> urls);
    }
}
