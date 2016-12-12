package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhjydy.model.data.AppData;
import com.zhjydy.model.data.DicData;
import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.preference.SPUtils;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.contract.OrderCancelContract;
import com.zhjydy.presenter.contract.OrderConfirmContract;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        loadCancelResons();
    }


    @Override
    public void finish() {

    }


    private void loadCancelResons() {
        List<NormalDicItem> list ;
        list  =  DicData.getInstance().getOrderReasons();
        ArrayList<NormalPickViewData> pickViewDatas = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (NormalDicItem item:list) {
                NormalPickViewData pickViewData = new NormalPickViewData(item);
                pickViewDatas.add(pickViewData);
            }
            mView.updateCancelResonList(pickViewDatas);
        } else {
            WebCall.getInstance().call(WebKey.func_getCancelReason,new HashMap<String, Object>()).subscribe(new BaseSubscriber<WebResponse>() {
                @Override
                public void onNext(WebResponse webResponse) {
                    String data = webResponse.getData();
                    List<NormalDicItem> list  = JSON.parseObject(data, new TypeReference<List<NormalDicItem>>() {
                    });
                    ArrayList<NormalPickViewData> pickViewDatas = new ArrayList<>();
                    for (NormalDicItem item:list) {
                        NormalPickViewData pickViewData = new NormalPickViewData(item);
                        pickViewDatas.add(pickViewData);
                    }
                    mView.updateCancelResonList(pickViewDatas);
                }
            });

        }
    }
    @Override
    public void confirmCancel(final String reason, String comment) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("id",mOrderId);
        params.put("reason",reason);
        params.put("comment",reason);
        WebCall.getInstance().call(WebKey.func_cancelOrder,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
              String  returnData = webResponse.getReturnData();
                Map<String,Object> map = Utils.parseObjectToMapString(returnData);
                boolean status = Utils.toBoolean(map.get("status"));
                if (status) {
                    RefreshManager.getInstance().refreshData(RefreshKey.ORDET_LIST_CHANGE);
                }
                mView.cancelResult(status);
            }
        });
    }
}
