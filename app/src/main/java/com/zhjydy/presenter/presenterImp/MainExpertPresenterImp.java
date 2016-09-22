package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.MainExpertContract;
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
public class MainExpertPresenterImp implements MainExpertContract.MainExpertPresenter {

    private MainExpertContract.MainExpertView mView;
    public MainExpertPresenterImp(MainExpertContract.MainExpertView view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }
    @Override
    public void start() {

    }


    @Override
    public void finish() {

    }
}
