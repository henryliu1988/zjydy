package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.contract.MainHomeContract;
import com.zhjydy.view.fragment.FragmentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func3;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainExpertPresenterImp implements MainExpertContract.MainExpertPresenter {

    private MainExpertContract.MainExpertView mView;

    public MainExpertPresenterImp(MainExpertContract.MainExpertView view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadFilterDatas();
        loadExperts();
    }

    private void loadFilterDatas() {


        List<District> districts = new ArrayList<>();
        districts.add(new District("000", "北京"));
        districts.add(new District("000", "北京"));
        districts.add(new District("000", "北京"));
        districts.add(new District("000", "北京"));
        districts.add(new District("000", "北京"));
        districts.add(new District("000", "北京"));
        districts.add(new District("000", "北京"));


        List<NormalDicItem> offices = new ArrayList<>();
        offices.add(new NormalDicItem("000", "内科"));
        offices.add(new NormalDicItem("000", "内科"));
        offices.add(new NormalDicItem("000", "内科"));
        offices.add(new NormalDicItem("000", "内科"));
        offices.add(new NormalDicItem("000", "内科"));


        List<NormalDicItem> profess = new ArrayList<>();
        profess.add(new NormalDicItem("000", "教授"));
        profess.add(new NormalDicItem("000", "住院医师"));
        profess.add(new NormalDicItem("000", "住院医师"));
        profess.add(new NormalDicItem("000", "住院医师"));
        profess.add(new NormalDicItem("000", "住院医师"));

        Observable<List<District>> districob = Observable.just(districts);
        Observable<List<NormalDicItem>> officeob = Observable.just(offices);
        Observable<List<NormalDicItem>> professob = Observable.just(profess);

        Observable.zip(districob, officeob, professob, new Func3<List<District>, List<NormalDicItem>, List<NormalDicItem>, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(List<District> districts, List<NormalDicItem> normalDicItems, List<NormalDicItem> normalDicItems2) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("全部地区", districts);
                item.put("科室", normalDicItems);
                item.put("职称", normalDicItems2);
                return item;
            }
        }).subscribe(new Action1<Map<String, Object>>() {
            @Override
            public void call(Map<String, Object> map) {
                mView.updateFilters(map);
            }
        });
    }

    private void loadExperts() {
        List<DocTorInfo> docs = new ArrayList<>();
        docs.add(new DocTorInfo("das","王XX","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王dsa","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王fds","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王fds","北京人民医院","内科","教授","擅长","93","5"));
        docs.add(new DocTorInfo("das","王dfsad","北京人民医院","内科","教授","擅长","93","5"));
        Observable.just(docs).subscribe(new Action1<List<DocTorInfo>>() {
            @Override
            public void call(List<DocTorInfo> docTorInfos) {
                mView.updateExperts(docTorInfos);
            }
        });
    }

    @Override
    public void finish() {
    }
}
