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

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class MsgData {

    private static MsgData instance;


    private List<Map<String, Object>> mOrderList = new ArrayList<>();
    private List<Map<String,Object>> mNewCommentList = new ArrayList<>();


    private WebResponse mOrderMsgData;
    private WebResponse mCommentNewData;

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
        loadSystemList();
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
        int a= count;
        return count;
    }


    public Observable<List<Map<String,Object>>> getAllOrderMsgList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", AppData.getInstance().getToken().getId());
        mOrderMsgData = null;
        return WebCall.getInstance().callCache(WebKey.func_getOrdersMsg,params,mOrderMsgData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                mOrderMsgData = webResponse;
                String data = webResponse.getData();
                mOrderList = Utils.parseObjectToListMapString(data);
                for (onDataCountChangeListener l:mListeners) {
                    l.onChange(getUnReadMsgCount());
                }
                return  mOrderList;
            }
        });
    }
    public Observable<List<Map<String,Object>>> getAllCommentNewList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", AppData.getInstance().getToken().getId());
        params.put("pagesize", 30);
       return WebCall.getInstance().callCache(WebKey.func_getNewCommentList, params,mCommentNewData).map(new Func1<WebResponse, List<Map<String,Object>>>() {
           @Override
           public List<Map<String, Object>> call(WebResponse webResponse) {
               String data = webResponse.getData();
               mCommentNewData = webResponse;
               mNewCommentList = Utils.parseObjectToListMapString(data);
               for (onDataCountChangeListener l:mListeners) {
                   l.onChange(getUnReadMsgCount());
               }
               return mNewCommentList;
           }
       });
    }

    public Observable<List<Map<String,Object>>> getAllSystemMsgList() {
          List<Map<String,Object>> list  = new ArrayList<>();
        return Observable.just(list);
    }



    private void loadOrderMsgData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_getOrdersMsg, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                mOrderMsgData = webResponse;
                mOrderList = Utils.parseObjectToListMapString(data);
                for (onDataCountChangeListener l:mListeners) {
                    l.onChange(getUnReadMsgCount());
                }
            }
        });
    }
    private void loadNewCommentList(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", AppData.getInstance().getToken().getId());
        params.put("pagesize", 30);
        WebCall.getInstance().call(WebKey.func_getNewCommentList, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                mCommentNewData = webResponse;
                mNewCommentList = Utils.parseObjectToListMapString(data);
                for (onDataCountChangeListener l:mListeners) {
                    l.onChange(getUnReadMsgCount());
                }
            }
        });

    }

    private void loadSystemList() {

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
