package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.MsgData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.OrderBackContract;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Func1;

/**
 * Created by Administrator on 2017/6/21.
 */

public class OrderBackPresenterImp implements OrderBackContract.Presenter {

    private String mId;
    private Map<String,Object> mOrderInfo = new HashMap<>();
    private OrderBackContract.View mView;
    public OrderBackPresenterImp(OrderBackContract.View view,String orderId ) {
        mView = view;
        this.mId = orderId;
        mView.setPresenter(this);
        start();
    }
    @Override
    public void start() {
        loadOrder(mId);
    }

    @Override
    public void finish() {

    }



    private void loadOrder(String id) {
        HashMap<String, Object> param1s = new HashMap<>();
        param1s.put("id", id);
        WebCall.getInstance().call(WebKey.func_getOrdersById, param1s).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), true) {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                Map<String, Object> order = Utils.parseObjectToMapString(data);
                mOrderInfo = order;
                mView.updateOrder(order);
            }
        });
    }
    @Override
    public void confirmBack(String reason, String comment) {
        Map<String,Object> hui = Utils.parseObjectToMapString(mOrderInfo.get("hui_comment"));

        String money = Utils.toString(hui.get("money"));
        HashMap<String,Object> param = new HashMap<>();
        param.put("id",mId);
        HashMap<String,Object> rebackReason = new HashMap<>();
        rebackReason.put("reason",reason);
        rebackReason.put("moeny",money);
        rebackReason.put("comment",comment);
        param.put("reback_reason",rebackReason);
        WebCall.getInstance().call(WebKey.func_backOrder,param).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"请稍后，正在提交数据") {
            @Override
            public void onNext(WebResponse webResponse) {
                String returnData = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(returnData);
                boolean status = Utils.toBoolean(map.get("status"));
                if (status) {
                    MsgData.getInstance().loadOrderMsgData();
                }
                mView.backResult(status);

            }
        });

    }
}
