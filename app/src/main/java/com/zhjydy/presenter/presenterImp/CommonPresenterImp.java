package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.CommonContract;
import com.zhjydy.presenter.contract.PayPasswordChangContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class CommonPresenterImp implements CommonContract.Presenter {

    private CommonContract.View mView;

    public CommonPresenterImp(CommonContract.View view) {
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
