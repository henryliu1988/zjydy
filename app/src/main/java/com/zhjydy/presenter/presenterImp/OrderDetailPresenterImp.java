package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.OrderDetailContract;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class OrderDetailPresenterImp implements OrderDetailContract.Presenter {

    private OrderDetailContract.View mView;

    private String orderId;

    private Map<String, Object> orderInfo = new HashMap<>();

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
        HashMap<String, Object> param1s = new HashMap<>();
        param1s.put("id", id);
        WebCall.getInstance().call(WebKey.func_getOrdersById, param1s).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), true) {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                Map<String, Object> order = Utils.parseObjectToMapString(data);
                orderInfo = order;
                mView.updateOrder(order);
                String expertId = getExpertId();
                HashMap<String, Object> params = new HashMap<>();
                params.put("expertid", expertId);
                WebCall.getInstance().call(WebKey.func_getExpert, params).map(new Func1<WebResponse, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> call(WebResponse webResponse) {
                        String data = webResponse.getData();
                        Map<String, Object> map = Utils.parseObjectToMapString(data);
                        return map;
                    }
                }).subscribe(new BaseSubscriber<Map<String, Object>>(mView.getContext(), true) {
                    @Override
                    public void onNext(Map<String, Object> map) {
                        mView.updateExpert(map);
                    }
                });

            }
        });
    }

    @Override
    public void finish() {
    }


    @Override
    public String getExpertId() {
        if (orderInfo != null && orderInfo.size() > 0) {
            String expertId = Utils.toString(orderInfo.get("expertid"));
            return expertId;
        }
        return null;
    }
}
