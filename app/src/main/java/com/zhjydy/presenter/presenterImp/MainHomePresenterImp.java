package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.contract.MainHomeContract;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainHomePresenterImp implements MainHomeContract.MainHomePresenter {

    private MainHomeContract.MainHomeView mView;

    public MainHomePresenterImp(MainHomeContract.MainHomeView view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadBanner();
        loadExpert();
        loadNewMsg();
        loadInfomation();
    }

    private void loadBanner() {

        WebCall.getInstance().call(WebKey.func_banner, new HashMap<String, Object>()).flatMap(new Func1<WebResponse, Observable<List<String>>>() {
            @Override
            public Observable<List<String>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(data);
                return Observable.from(list).map(new Func1<Map<String, Object>, String>() {
                    @Override
                    public String call(Map<String, Object> map) {
                        String image = Utils.toString(map.get("path"));
                        return image;
                    }
                }).buffer(list.size());
            }
        }).subscribe(new BaseSubscriber<List<String>>() {
            @Override
            public void onNext(List<String> strings) {
                if (mView != null) {
                    mView.updateBanner(strings);
                }
            }
        });
    }

    private void loadNewMsg() {
        WebCall.getInstance().call(WebKey.func_getRecommend,new HashMap<String, Object>()).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (mView != null && status) {
                    List<String> news = Utils.parseObjectToListString(webResponse.getData());
                    mView.updateMsg(news);
                }
            }
        });
    }

    private void loadExpert() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("num", 9);
        WebCall.getInstance().call(WebKey.func_getRecommendZhuan, params).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(data);
                return list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> maps) {
                mView.updateExpert(maps);
            }
        });
        /*
        List<DocTorInfo> docs = new ArrayList<>();
        docs.add(new DocTorInfo("das","王XX","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王dsa","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王fds","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王fds","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王dfsad","北京人民医院","内科","教授","擅长","93","5"));
        Observable.just(docs).subscribe(new Action1<List<DocTorInfo>>() {
            @Override
            public void call(List<DocTorInfo> docTorInfos) {
            }
        });
        */
    }

    private void loadInfomation() {

        WebCall.getInstance().call(WebKey.func_getNews, new HashMap<String, Object>()).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                list = Utils.parseObjectToListMapString(webResponse.getData());
                return list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> infos) {
                mView.updateInfo(infos);
            }
        });
    }

    @Override
    public void finish() {

    }
}
