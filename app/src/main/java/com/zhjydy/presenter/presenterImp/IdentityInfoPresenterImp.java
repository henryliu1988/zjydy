package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.AccountSafeContract;
import com.zhjydy.presenter.contract.IdentityInfoContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class IdentityInfoPresenterImp implements IdentityInfoContract.Presenter {

    private IdentityInfoContract.View mView;

    public IdentityInfoPresenterImp(IdentityInfoContract.View view) {
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
