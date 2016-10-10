package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.presenter.contract.InfoDetailContract;
import com.zhjydy.presenter.contract.OrderDetailContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class OrderDetailPresenterImp implements OrderDetailContract.Presenter {

    private OrderDetailContract.View mView;

    private String orderId;
    public OrderDetailPresenterImp(OrderDetailContract.View view, String id) {
        this.mView = view;
        this.orderId = id;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
       if (TextUtils.isEmpty(orderId)) {
           return;
       }
        loadOrderContent(orderId);
    }

    private void loadOrderContent(String id) {

    }
    @Override
    public void finish() {
    }


}
