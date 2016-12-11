package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zhjydy.model.data.MsgData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.model.pageload.PageLoadDataSource;
import com.zhjydy.presenter.contract.OrderDetailContract;
import com.zhjydy.presenter.contract.OrderMsgListContract;
import com.zhjydy.util.ListMapComparator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class OrderMsgListPresenterImp extends PageLoadDataSource implements OrderMsgListContract.Presenter {

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
        MsgData.getInstance().getAllOrderMsgList().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> orderLit) {
                ListMapComparator comp = new ListMapComparator("addtime",0);
                Collections.sort(orderLit,comp);
                if (mView != null)
                mView.updateOrderList(orderLit);
            }
        });
    }

    @Override
    public void finish() {
    }


    @Override
    public void reLoadData() {
        loadOrderList();
    }

    @Override
    public void readOrder(String id) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("id",id);
        WebCall.getInstance().call(WebKey.func_updateOrdersMsg,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                if (WebUtils.getWebStatus(webResponse)) {
                    loadOrderList();
                }
            }
        });
    }

    @Override
    public RequestHandle loadListData(ResponseSender<List<Map<String, Object>>> sender, int page) {
        return null;
    }
}
