package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.PayPasswordChangContract;
import com.zhjydy.presenter.contract.PhoneNumChangContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PayPasswordChangePresenterImp implements PayPasswordChangContract.Presenter {

    private PayPasswordChangContract.View mView;

    public PayPasswordChangePresenterImp(PayPasswordChangContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {

    }



    @Override
    public void finish() {

    }


}
