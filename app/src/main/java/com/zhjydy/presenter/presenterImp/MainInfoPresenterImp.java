package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.contract.MainInfoContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
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
        List<Infomation> infos = new ArrayList<>();
        infos.add( new Infomation("怎么样通过饮食控制糖尿病1","2016/09/16","dasldsakjfhdsjkfhsdhfkdjsfh","www.baidu.com"));
        infos.add( new Infomation("怎么样通过饮食控制糖尿病2","2016/09/16","dasldsakjfhdsjkfhsdhfkdjsfh","www.baidu.com"));
        infos.add( new Infomation("怎么样通过饮食控制糖尿病3","2016/09/16","dasldsakjfhdsjkfhsdhfkdjsfh","www.baidu.com"));
        infos.add( new Infomation("怎么样通过饮食控制糖尿病4","2016/09/16","dasldsakjfhdsjkfhsdhfkdjsfh","www.baidu.com"));
        infos.add( new Infomation("怎么样通过饮食控制糖尿病5","2016/09/16","dasldsakjfhdsjkfhsdhfkdjsfh","www.baidu.com"));
        Observable.just(infos).subscribe(new Action1<List<Infomation>>() {
            @Override
            public void call(List<Infomation> infomations) {
                mView.updateInfoList(infomations);
            }
        });
    }

    @Override
    public void finish() {

    }
}
