package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.MainInfoContract;
import com.zhjydy.presenter.contract.MainOrderContract;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainOrderPresenterImp implements MainOrderContract.MainOrderPresenter {

    private MainOrderContract.MainOrderView mView;

    public MainOrderPresenterImp(MainOrderContract.MainOrderView view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadOrders();
    }

    private void loadOrders() {
        HashMap<String,Object> params = new HashMap<>();
        params.put("memberid", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_getOrders,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String,Object>> list = Utils.parseObjectToListMapString(data);
                mView.update(list);
            }
        });
    }

    @Override
    public void finish() {

    }

    @Override
    public void reloadOrders() {
        loadOrders();
    }
}
