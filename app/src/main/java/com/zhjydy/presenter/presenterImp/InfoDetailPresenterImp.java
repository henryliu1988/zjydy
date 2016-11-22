package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.ExpertDetailContract;
import com.zhjydy.presenter.contract.InfoDetailContract;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class InfoDetailPresenterImp implements InfoDetailContract.Presenter {

    private InfoDetailContract.View mView;

    private String infoId;

    public InfoDetailPresenterImp(InfoDetailContract.View view, String id) {
        this.mView = view;
        this.infoId = id;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        if (TextUtils.isEmpty(infoId)) {
            return;
        }
        loadInfoContent(infoId);
    }

    private void loadInfoContent(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", infoId);
        WebCall.getInstance().call(WebKey.func_getNewsById, params).map(new Func1<WebResponse, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(WebResponse webResponse) {
                String data = webResponse.getData();
                Map<String, Object> map = new HashMap<String, Object>();
                return map;
            }
        }).subscribe(new BaseSubscriber<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> map) {
            }
        });
    }

    @Override
    public void finish() {
    }

    @Override
    public void saveInfo(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("newsid", id);
        params.put("userid", "1");
        WebCall.getInstance().call(WebKey.func_collectNews, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {

            }
        });
    }

    @Override
    public void shareInfo(String url) {

    }
}
