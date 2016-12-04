package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.OrderCancelContract;
import com.zhjydy.presenter.contract.OrderConfirmContract;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class OrderCancelPresenterImp implements OrderCancelContract.Presenter {

    private OrderCancelContract.View mView;

    private String mOrderId;
    public OrderCancelPresenterImp(OrderCancelContract.View view, String info) {
        this.mView = view;
        this.mOrderId = info;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        initDatas();
    }

    private void initDatas() {
    }

    @Override
    public void finish() {

    }


    @Override
    public void confirmCancel(final String reason, String comment) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("id",mOrderId);
        params.put("comment",reason);
        WebCall.getInstance().call(WebKey.func_cancelOrder,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
              String  returnData = webResponse.getReturnData();
                Map<String,Object> map = Utils.parseObjectToMapString(returnData);
                boolean status = Utils.toBoolean(map.get("status"));
                mView.cancelResult(status);
            }
        });
    }
}
