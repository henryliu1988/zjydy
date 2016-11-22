package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.FavExpertContract;
import com.zhjydy.presenter.contract.MainExpertContract;
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
public class FaveExpertPresenterImp implements FavExpertContract.Presenter {

    private FavExpertContract.View mView;

    public FaveExpertPresenterImp(FavExpertContract.View view) {
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

       HashMap<String,Object> params = new HashMap<>();
        params.put("collect",AppData.getInstance().getToken().getCollectExperts());
        WebCall.getInstance().call(WebKey.func_getCollectExpert,params).map(new Func1<WebResponse, List<Map<String,Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String,Object>> list = Utils.parseObjectToListMapString(data);
                return list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> list) {
                mView.updateExperts(list);
            }
        });
    }

    @Override
    public void finish() {
    }
}
