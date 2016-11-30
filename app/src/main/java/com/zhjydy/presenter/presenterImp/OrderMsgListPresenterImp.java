package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.data.MsgData;
import com.zhjydy.presenter.contract.OrderDetailContract;
import com.zhjydy.presenter.contract.OrderMsgListContract;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class OrderMsgListPresenterImp implements OrderMsgListContract.Presenter {

    private OrderMsgListContract.View mView;


    public OrderMsgListPresenterImp(OrderMsgListContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {

        loadOrderList();
    }

    private void loadOrderList() {
        List<Map<String,Object>> orderLit = MsgData.getInstance().getAllOrderMsgList();
        if(mView != null) {
            mView.updateOrderList(orderLit);
        }
    }

    @Override
    public void finish() {
    }


}
