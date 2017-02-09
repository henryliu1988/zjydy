package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.DicData;
import com.zhjydy.model.entity.NormalDicItem;
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
        loadOfficeDicItem();
    }




    private void loadBanner() {

        HashMap<String,Object> parasm = new HashMap<>();
        parasm.put("type",WebKey.TYPE_KEY);
        WebCall.getInstance().call(WebKey.func_banner,parasm).map(new Func1<WebResponse, List<Map<String,Object>>>() {
            @Override
            public List<Map<String,Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(data);
                return list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String,Object>>>() {
            @Override
            public void onNext(List<Map<String,Object>> strings) {
                if (mView != null) {
                    mView.updateBanner(strings);
                }
            }
        });
    }

    private void loadNewMsg() {
        WebCall.getInstance().call(WebKey.func_getRecommend, new HashMap<String, Object>()).subscribe(new BaseSubscriber<WebResponse>() {
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



    private void loadOfficeDicItem() {
        DicData.getInstance().getOfficeObserver().subscribe(new BaseSubscriber<List<NormalDicItem>>() {
            @Override
            public void onNext(List<NormalDicItem> normalDicItems) {

                /*
                List<NormalDicItem> items = new ArrayList<NormalDicItem>();
                NormalDicItem item1 = new NormalDicItem();
                item1.setId("1");
                item1.setName("眼科");

                NormalDicItem item2 = new NormalDicItem();
                item2.setId("2");
                item2.setName("泌尿内科");

                NormalDicItem item3 = new NormalDicItem();
                item3.setId("3");
                item3.setName("耳鼻喉科");
                NormalDicItem item4 = new NormalDicItem();
                item4.setId("1");
                item4.setName("心内科");
                NormalDicItem item5 = new NormalDicItem();
                item5.setId("1");
                item5.setName("眼科");

                NormalDicItem item6 = new NormalDicItem();
                item6.setId("1");
                item6.setName("泌尿内科");

                NormalDicItem item7 = new NormalDicItem();
                item7.setId("1");
                item7.setName("耳鼻喉科");
                NormalDicItem item8 = new NormalDicItem();
                item8.setId("1");
                item8.setName("心内科");

                items.add(item1);
                items.add(item2);
                items.add(item3);
                items.add(item4);
                items.add(item5);
                items.add(item6);
                items.add(item7);
                items.add(item8);
                items.add(item7);
                items.add(item8);
                */
                mView.updateOfficeList(normalDicItems);
            }
        });
    }
    @Override
    public void finish() {

    }
}
