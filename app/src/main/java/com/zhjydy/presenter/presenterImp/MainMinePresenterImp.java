package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.MainMineContract;
import com.zhjydy.presenter.contract.MainOrderContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainMinePresenterImp implements MainMineContract.MainMinePresenter {

    private MainMineContract.MainMineView mView;

    public MainMinePresenterImp(MainMineContract.MainMineView view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadOrders();
    }

    private void loadOrders() {
        List<Map<String,Object>> orders = new ArrayList<>();
        Map<String,Object> item = new HashMap<>();
        item.put("docName","赵医生");
        item.put("状态","退款中");item.put("serialNum","54413313");item.put("time","2016-09-11");item.put("patientName","王XX");item.put("patientHospital","潍坊人民医院");

        Map<String,Object> item1 = new HashMap<>();
        item1.put("docName","赵医生");
        item1.put("状态","退款中");item1.put("serialNum","54413313");item1.put("time","2016-09-11");item1.put("patientName","王XX");item1.put("patientHospital","潍坊人民医院");
        orders.add(item);
        orders.add(item1);
    }

    @Override
    public void finish() {

    }
}
