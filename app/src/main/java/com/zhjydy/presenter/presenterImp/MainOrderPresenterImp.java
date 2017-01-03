package com.zhjydy.presenter.presenterImp;

import android.content.Context;

import com.zhjydy.model.data.UserData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.RefreshWithKey;
import com.zhjydy.presenter.contract.MainOrderContract;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainOrderPresenterImp implements MainOrderContract.MainOrderPresenter, RefreshWithKey {

    private MainOrderContract.MainOrderView mView;

    public MainOrderPresenterImp(MainOrderContract.MainOrderView view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.ORDET_LIST_CHANGE, this);
        RefreshManager.getInstance().addNewListener(RefreshKey.LOGIN_RESULT_BACK, this);
        start();
    }

    @Override
    public void start() {
        loadOrders(null);
    }

    private void loadOrders(Context context) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("memberid", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_getOrders, params).subscribe(new BaseSubscriber<WebResponse>(context,true) {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(data);
                mView.update(list);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.onNetError();
            }
        });
    }

    @Override
    public void finish() {

    }

    @Override
    public void reloadOrders() {
        loadOrders(mView.getContext());
    }

    @Override
    public void onRefreshWithKey(int key) {
        loadOrders(null);
    }
}
