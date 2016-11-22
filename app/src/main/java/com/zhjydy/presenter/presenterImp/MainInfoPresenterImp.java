package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.contract.MainInfoContract;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainInfoPresenterImp implements MainInfoContract.MainInfoPresenter {

    private MainInfoContract.MainInfoView mView;

    public MainInfoPresenterImp(MainInfoContract.MainInfoView view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadMsg();
    }

    private void loadMsg() {
        HashMap<String,Object> params = new HashMap<>();
        WebCall.getInstance().call(WebKey.func_getNewsList,params).map(new Func1<WebResponse, List<Map<String,Object>>>() {
            @Override
            public List<Map<String,Object>> call(WebResponse webResponse) {
                List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
                list = Utils.parseObjectToListMapString(webResponse.getData());
                return  list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> maps) {
                mView.updateInfoList(maps);
            }
        });
    }

    @Override
    public void finish() {

    }
}
