package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.presenter.contract.MainHomeContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainHomePresenterImp implements MainHomeContract.MainHomePresenter {

    private MainHomeContract.MainHomeView mView;
    public  MainHomePresenterImp(MainHomeContract.MainHomeView view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }
    @Override
    public void start() {
        loadBanner();
        loadExpert();
        loadNewMsg();
    }

    private void loadBanner() {
        List<String> image = new ArrayList<>();
        image.add("http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg");
        image.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg");
        image.add("http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg");

        Observable.just(image).subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> images) {
                if (mView != null){
                    mView.updateBanner(images);
                }
            }
        });
    }
    private void loadNewMsg() {
        Map<String,Object> mes = new HashMap<>();
        mes.put("url","http://sports.sina.com.cn/");
        mes.put("title","新浪新闻");
        Observable.just(mes).subscribe(new Action1<Map<String, Object>>() {
            @Override
            public void call(Map<String, Object> stringStringMap) {
                if (mView != null){
                    mView.updateMsg(stringStringMap);
                }
            }
        });
    }
    private void loadExpert() {
        List<DocTorInfo> docs = new ArrayList<>();
        docs.add(new DocTorInfo("das","王XX","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王dsa","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王fds","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王fds","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王dfsad","北京人民医院","内科","教授","擅长","93","5"));
        Observable.just(docs).subscribe(new Action1<List<DocTorInfo>>() {
            @Override
            public void call(List<DocTorInfo> docTorInfos) {
                mView.updateMsg(docTorInfos);
            }
        });

    }
    @Override
    public void finish() {

    }
}
