package com.zhjydy.presenter.presenterImp;

import com.zhjydy.R;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.presenter.contract.MainInfoContract;
import com.zhjydy.presenter.contract.MsgAllListContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MsgAllListPresenterImp implements MsgAllListContract.Presenter {

    private MsgAllListContract.View mView;

    public MsgAllListPresenterImp(MsgAllListContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        initOrderList();
        loadMsg();
    }

    private void loadMsg() {
        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> orderData = new HashMap<>();
        orderData.put("image", R.mipmap.msg_order_img);
        orderData.put("title", "订单");
        orderData.put("content", "暂无新消息");
        list.add(orderData);
        mView.updateChatList(list);
    }

    private void initOrderList() {
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(getOrderItemData());
        list.add(getSystemItemData());
        mView.updateOrderList(list);
    }

    private Map<String,Object> getOrderItemData() {
        Map<String,Object> orderData = new HashMap<>();
        orderData.put("image", R.mipmap.msg_order_img);
        orderData.put("title", "订单");
        orderData.put("content", "暂无新消息");
        return orderData;
    }
    private Map<String,Object> getSystemItemData() {
        Map<String,Object> systemData = new HashMap<>();
        systemData.put("image", R.mipmap.msg_system_img);
        systemData.put("title", "系统消息");
        systemData.put("content", "暂无新消息");
        return systemData;
    }
    @Override
    public void finish() {

    }
}
