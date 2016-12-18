package com.zhjydy.presenter.contract;

import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        void registerOK(String msg);
    }

    interface Presenter extends BasePresenter {
        Observable<WebResponse> getConfirmCode(String phone);

        void register(HashMap<String, Object> params);
    }
}
