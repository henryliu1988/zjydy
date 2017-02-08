package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.OrderPayContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class OrderPayPresenterImp implements OrderPayContract.Presenter {

    private OrderPayContract.View mView;

    public OrderPayPresenterImp(OrderPayContract.View view) {
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
