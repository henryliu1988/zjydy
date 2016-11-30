package com.zhjydy.presenter.contract;

import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public interface ForgetPassWordContract {
    interface View extends BaseView<Presenter> {
        void resetOk(String msg);
    }

    interface Presenter extends BasePresenter {
        Observable<WebResponse> getConfirmCode(String phone);
        void resetPassWord(HashMap<String, Object> params);
    }
}
