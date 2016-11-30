package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.data.AppData;
import com.zhjydy.presenter.contract.AccountSafeContract;
import com.zhjydy.presenter.contract.ExpertDetailContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class AccountSafePresenterImp implements AccountSafeContract.Presenter {

    private AccountSafeContract.View mView;

    public AccountSafePresenterImp(AccountSafeContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        mView.updatePhoneNum(AppData.getInstance().getToken().getMobile());
    }




    @Override
    public void finish() {

    }


}
