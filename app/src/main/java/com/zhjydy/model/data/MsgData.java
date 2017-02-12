package com.zhjydy.model.data;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.util.ListMapComparator;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class MsgData {

    private static MsgData instance;


    private List<Map<String, Object>> mOrderList = new ArrayList<>();
    private List<Map<String, Object>> mNewCommentList = new ArrayList<>();
    private List<Map<String, Object>> mNewSystemList = new ArrayList<>();

    private WebResponse mOrderMsgData;
    private WebResponse mCommentNewData;
    private WebResponse mNewSystemData;

    public MsgData() {
    }

    public static MsgData getInstance() {
        if (instance == null) {
            instance = new MsgData();
        }
        return instance;
    }

    public void loadData() {
        loadOrderMsgData();
        loadNewSystemList();
        loadNewCommentList();
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
        if (mNewCommentList != null && mNewCommentList.size() > 0) {
            for (Map<String, Object> comment : mNewCommentList) {
                int status = Utils.toInteger(comment.get("status"));
                if (status == 0) {
                    count++;
                }
            }
        }
        return count;
    }


    public Observable<List<Map<String, Object>>> getAllOrderMsgList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", UserData.getInstance().getToken().getId());
        return WebCall.getInstance().callCache(WebKey.func_getOrdersMsg, params, mOrderMsgData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                mOrderMsgData = webResponse;
                mOrderList = parseOrderDataToNewMsg(webResponse.getData());
                return mOrderList;
            }
        });
    }

    public Observable<List<Map<String, Object>>> getAllCommentNewList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", UserData.getInstance().getToken().getId());
        params.put("pagesize", 30);
        return WebCall.getInstance().callCache(WebKey.func_getNewCommentList, params, mCommentNewData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                mCommentNewData = webResponse;
                mNewCommentList = Utils.parseObjectToListMapString(data);
                return mNewCommentList;
            }
        });
    }

    public Observable<List<Map<String, Object>>> getNewSystemMsgList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("pagesize", 1);
        return WebCall.getInstance().callCache(WebKey.func_getSysMsg, params, mNewSystemData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                mNewSystemData = webResponse;
                String data = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("data"));
                RefreshManager.getInstance().refreshData(RefreshKey.SYTEM_DATA_LIST);

                return list;
            }
        });


    }


    public void loadOrderMsgData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_getOrdersMsg, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                mOrderMsgData = webResponse;
                mOrderList = parseOrderDataToNewMsg(data);
                RefreshManager.getInstance().refreshData(RefreshKey.ORDET_MSG_CHANGE);
            }
        });
    }

    public List<Map<String, Object>> parseOrderDataToNewMsg(String data) {
        List<Map<String, Object>> msgList = new ArrayList<>();
        List<Map<String, Object>> allList = Utils.parseObjectToListMapString(data);
        Integer statusGroup[] = {2, 5, 7, 8, 9, 10, 11, 12};
        if (allList.size() < 1) {
            return msgList;
        }
        ListMapComparator comp = new ListMapComparator("addtime", 0);
        Collections.sort(allList, comp);
        List<Integer> statusList = Arrays.asList(statusGroup);
        for (Map<String, Object> msgData : allList)
            if (statusList.contains(Utils.toInteger(msgData.get("orderstatus")))) {
                msgList.add(msgData);
            }
        return msgList;
    }

    public void loadNewCommentList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", UserData.getInstance().getToken().getId());
        params.put("pagesize", 30);
        WebCall.getInstance().call(WebKey.func_getNewCommentList, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                mCommentNewData = webResponse;
                mNewCommentList = Utils.parseObjectToListMapString(data);
                RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_DATA_LIST);
                RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_DATA_READ);

            }
        });

    }

    private void loadNewSystemList() {
    }

}
