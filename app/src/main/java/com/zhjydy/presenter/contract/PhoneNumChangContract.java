package com.zhjydy.presenter.contract;

import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface PhoneNumChangContract {

    interface View extends BaseView<Presenter> {
        void submitResult(boolean result, String msg, String phone);
    }

    interface Presenter extends BasePresenter {
        Observable<WebResponse> getConfirmCode(String phone);

        void submitChangeConfirm(String phone, String confirmCode);
    }
}
