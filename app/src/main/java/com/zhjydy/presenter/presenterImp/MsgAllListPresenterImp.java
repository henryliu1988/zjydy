package com.zhjydy.presenter.presenterImp;

import com.zhjydy.R;
import com.zhjydy.model.data.MsgData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.presenter.contract.MsgAllListContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

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
        initCommentList();
    }

    private void initCommentList() {
        MsgData.getInstance().getAllCommentNewList().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> maps) {
                mView.updateChatList(maps);
            }
        });
    }
    private void initOrderList() {
        Observable.zip(getOrderItemData(), getSystemItemData(), new Func2<Map<String,Object>, Map<String,Object>, List<Map<String,Object>>>() {
            @Override
            public List<Map<String, Object>> call(Map<String, Object> order, Map<String, Object> sys) {
                List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
                list.add(order);
                list.add(sys);
                return  list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> list) {
                mView.updateOrderList(list);
            }
        });

    }

    private Observable<Map<String, Object>> getOrderItemData() {
        return MsgData.getInstance().getAllOrderMsgList().map(new Func1<List<Map<String, Object>>, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(List<Map<String, Object>> orderList) {
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("image", R.mipmap.msg_order_img);
                orderData.put("title", "订单");
                orderData.put("content", "暂无新消息");
                orderData.put("count", "0");
                if (orderList != null && orderList.size() > 0) {
                    Map<String, Object> order = orderList.get(0);
                    orderData.put("content", order.get("introduction"));
                    orderData.put("count", orderList.size());
                    orderData.put("time", order.get("addtime"));
                }
                orderData.put("type", 0);
                return orderData;
            }
        });
    }

    private Observable<Map<String, Object>> getSystemItemData() {
        return MsgData.getInstance().getAllSystemMsgList().map(new Func1<List<Map<String, Object>>, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(List<Map<String, Object>> sysList) {
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("image", R.mipmap.msg_system_img);
                orderData.put("title", "系统消息");
                orderData.put("content", "暂无新消息");
                orderData.put("count", "0");
                if (sysList != null && sysList.size() > 0) {
                    Map<String, Object> order = sysList.get(0);
                    orderData.put("content", order.get("introduction"));
                    orderData.put("count", sysList.size());
                    orderData.put("time", order.get("addtime"));
                }
                orderData.put("type", 1);
                return orderData;
            }
        });
    }

    @Override
    public void finish() {

    }
}
