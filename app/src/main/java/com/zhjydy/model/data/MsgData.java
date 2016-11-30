package com.zhjydy.model.data;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class MsgData {

    private static MsgData instance;

    private WebResponse mOrderMsgData;

    private List<Map<String, Object>> mOrderList = new ArrayList<>();

    public MsgData() {
    }

    public static MsgData getInstance() {
        if (instance == null) {
            instance = new MsgData();
        }
        return instance;
    }

    public void initData() {
      //  loadOrderMsgData();
    }


    public int getUnReadMsgCount() {
        int count = 0;
        if (mOrderList != null && mOrderList.size() > 0) {
            for (Map<String, Object> order : mOrderList) {
                int status = Utils.toInteger(order.get("status"));
                if (status == 0) {
                    count++;
                }
            }
        }
        return count;
    }


    public List<Map<String, Object>> getAllOrderMsgList() {
        return mOrderList;
    }


    public void updateOrderMsgData() {
        loadOrderMsgData();
    }

    private void loadOrderMsgData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_getOrdersMsg, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                mOrderList = Utils.parseObjectToListMapString(data);
                for (onDataCountChangeListener l:mListeners) {
                    l.onChange(getUnReadMsgCount());
                }
            }
        });
    }


    public void addOnCountChangeListener(onDataCountChangeListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    private List<onDataCountChangeListener> mListeners = new ArrayList<>();

    public interface onDataCountChangeListener {

        public void onChange(int count);
    }
}
