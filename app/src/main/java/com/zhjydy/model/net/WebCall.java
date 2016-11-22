package com.zhjydy.model.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhjydy.model.preference.SPUtils;
import com.zhjydy.util.Log;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2016/7/29.
 */
public class WebCall {
    private static WebCall webCall;

    public WebCall() {
    }

    public static WebCall getInstance() {
        if (webCall == null) {
            webCall = new WebCall();
        }
        return webCall;
    }


    public Observable<WebResponse> call(int methodId,HashMap<String,Object> params) {
        if (!WebKey.WEBKEY_FUNC_COMMON_MAP.containsKey(methodId) && !WebKey.WEBKEY_FUNC_HUAN_MAP.containsKey(methodId) ) {
            WebResponse response = new WebResponse(1, "没有此接口", null);
            return Observable.just(response);
        }
        return callWebService(new WebService(methodId,
                params), null);
    }
    public Observable<WebResponse> callWebService(final WebService webService, final String key) {
        return Observable.create(new Observable.OnSubscribe<WebResponse>() {
            @Override
            public void call(Subscriber<? super WebResponse> subscriber) {
                //订阅者回调 onNext 和 onCompleted
                WebResponse result = webService.call();
                Log.d(this.getClass(), result.getError() + "");
                if (result != null && result.getError() == 0) {
                    if (!TextUtils.isEmpty(key) && !result.isEmptyData()) {
                        SPUtils.put(key, result.getData());
                    }
                    subscriber.onNext(result);
                } else {
                    subscriber.onError(new Throwable(result.getInfo()));
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


}

