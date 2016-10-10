package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.PasswordChangContract;
import com.zhjydy.presenter.contract.PhoneNumChangContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PhoneNumChangePresenterImp implements PhoneNumChangContract.Presenter {

    private PhoneNumChangContract.View mView;

    public PhoneNumChangePresenterImp(PhoneNumChangContract.View view) {
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
